import React,{Component} from 'react';
import PropTypes from 'prop-types';

//MUI stuff
import Button from '@material-ui/core/Button';
import DialogContent from '@material-ui/core/DialogContent';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import withStyles from '@material-ui/core/styles/withStyles';
import InputLabel from '@material-ui/core/InputLabel';
import AddCircleIcon from '@material-ui/icons/AddCircle';

//FIREBASE stuff
import firebase from "../firebaseConfig/firebase";
import {storage} from "../firebaseConfig/firebase";

//Components
import {addVehiculo, loadTiposVehiculos} from '../redux/actions/dataActions';
import { connect } from 'react-redux';

const styles = theme => ({

    upload: {
        '& > *': {
          margin: theme.spacing(1),
        },
      },
    input: {
        display: 'none',
      },

    tipoVehiculo: {
        width: 250,
    },

    selectVehiculo: {
        width: '100%',
        display: 'flex',
        justifyContent: 'center',
        alignItems:'center',
    },

    matriculaCapacidad: {
        display: 'flex',
        justifyContent: 'space-between',
        paddingBottom: 30,
        paddingTop: 30
    },

    mmaImagen: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
    }, 

    addBoton: {
        marginTop: 30,
        width: '100%',
        display: 'flex',
        justifyContent: 'center',
        alignItems:'center',
    }, 

    uploadBoton: {
        marginRight: 15
    },

    titulo: {
        marginRight: 10
    }

});

const initialState = {
    matricula: '',
    imagen: '',
    capacidad: 1,
    tipoVehiculo: 'CAMION',
    mma: 1,
    loading:true,
    enviar: false,
    error: {matricula: [],
            capacidad: [],
            mma: []
            }
}

export class FormNuevoVehiculo extends Component {
    constructor(props){
        super(props);
        this.state = initialState;

        this.handleOnChange = this.handleOnChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateMatricula(matricula) {
        var re = '';
        
        if(this.state.tipoVehiculo === "CARRETILLA_ELEVADORA"){
            re = /^[E]\d{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$/;
        }

        else {
            re = /^\d{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$/;
        }

        return re.test(String(matricula));
    }

    handleSubmit = (event) => {
        event.preventDefault();

        this.setState({
            enviar:true
        })
        
    }

    handleOnChangeNumber = (event) => {
        this.setState(state=>({
            error:{
                ...state.error,
                [event.target.name]:[]
            },
            [event.target.name]: parseFloat(event.target.value)
        }))
    };


    handleOnChange = (event) => {
        this.setState(state=>({
            error:{
                ...state.error,
                [event.target.name]:[]
            },
            [event.target.name]: event.target.value
        }))
    };

    handleChangeImg = (event) => {
        event.preventDefault();
        console.log('Comienzo de upload')
        //Aqui hacemos el upload a Firebase
        const file = event.target.files[0]
        const storageRef = firebase.storage().ref(`pictures/${file.name}`)
        const task = storageRef.put(file)
        //Ya esta subida en Firebase
  
        //Cogemos el url de Firebase para guardar el URL 
        task.on('state_changed',(snapshot) => {
         snapshot.ref.getDownloadURL().then(function(downloadURL) {
            this.setState({
              imagen:downloadURL
            })
         }.bind(this));
          console.log(snapshot)    
        },(err) => {
          console.log(err)
        })
      }

    componentDidMount() {
        this.props.loadTiposVehiculos();
    }

    componentDidUpdate(prevProps,prevState) {
        if(prevState.imagen != this.state.imagen){
            this.setState({
                loading:false
            })
        }
        console.log(this.state.capacidad)

        if(prevState.enviar != this.state.enviar && this.state.enviar){
            var valid = true;

            if(!this.validateMatricula(this.state.matricula)) {
                valid=false;

                this.setState(state=>({
                    error:{
                        ...state.error,
                        matricula:['Formato incorrecto']
                    }
                }))

            }

            if(this.state.capacidad<=0 || Number.isNaN(this.state.capacidad)){
                valid=false;

                this.setState(state=>({
                    error:{
                        ...state.error,
                        capacidad:['El valor debe ser mayor que 0']
                    }
                }))
            }

            if(this.state.mma<=0 || Number.isNaN(this.state.mma)){
                valid=false;

                this.setState(state=>({
                    error:{
                        ...state.error,
                        mma:['El valor debe ser mayor que 0']
                    }
                }))
            }

            if(valid){

                const vehiculo = {
                    matricula: this.state.matricula,
                    imagen: this.state.imagen,
                    capacidad: this.state.capacidad,
                    tipoVehiculo: this.state.tipoVehiculo,
                    mma: this.state.mma
                }

                this.props.addVehiculo(vehiculo);
                this.props.cerrar();
            }

            this.setState({
                enviar: false
            })

        }
    }

    render() {
        const {classes}= this.props;
        const tiposVehiculos = this.props.data.tiposVehiculos;

        return (
            
            <div>
                <div className= {classes.selectVehiculo}>
                    <InputLabel className={classes.titulo} id="demo-simple-label">Tipo de Vehiculo:</InputLabel>

                    <Select
                    autoFocus
                    className={classes.tipoVehiculo}
                    labelId="demo-simple-label"
                    label="Tipo de Vehiculo"
                    id="demo-simple-select"
                    name="tipoVehiculo"
                    value={this.state.tipoVehiculo}
                    onChange={this.handleOnChange}
                    >
                    
                    {Object.entries(tiposVehiculos).map((row) => 
                        <MenuItem value={row[1]}>{row[1]}</MenuItem>
                    )}
                    </Select>
                </div>
                

                <div style= {{padding:'0 60px 0 60px'}}>
                    <div className={classes.matriculaCapacidad}>

                        <TextField id="matricula" label="Matricula" type="text" variant="outlined" name="matricula"
                            error={this.state.error.matricula.length>0} helperText={this.state.error.matricula[0]}
                            required value={this.state.matricula} onChange={this.handleOnChange} /> 
                        
                        <TextField id="capacidad" label="Capacidad" type="number" variant="outlined" name="capacidad" inputProps={{min: 1, type: 'number'}}
                            error={this.state.error.capacidad.length>0} helperText={this.state.error.capacidad[0]}
                            required value={this.state.capacidad} onChange={this.handleOnChangeNumber} /> 

                    </div>

                    <div className={classes.mmaImagen}>
                        <TextField id="mma" label="MMA" type="number" variant="outlined" name="mma" inputProps={{min: 1, type: 'number'}}
                            error={this.state.error.mma.length>0} helperText={this.state.error.mma[0]}
                            required value={this.state.mma} onChange={this.handleOnChangeNumber} /> 


                            <input
                                type="file"
                                onChange={this.handleChangeImg.bind(this)}
                                className={classes.input}
                                id="contained-button-file"
                                name="imagen"
                            />
                            <label htmlFor="contained-button-file">
                            <Button className={classes.uploadBoton} variant="contained" color="primary" component="span"  startIcon={<CloudUploadIcon />} >
                                Subir imagen
                            </Button>
                            </label>
                    </div>
                </div>
                
                <div className={classes.addBoton}>
                    <Button disabled={this.state.loading} onClick={this.handleSubmit} size="large" type ="submit" color="primary" 
                        variant="contained" startIcon={<AddCircleIcon />}>
                        Añadir
                    </Button>
                </div>

            </div>
        )
    }

}

FormNuevoVehiculo.propTypes={
    classes: PropTypes.object.isRequired,
    addVehiculo: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    addVehiculo,
    loadTiposVehiculos
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(FormNuevoVehiculo))
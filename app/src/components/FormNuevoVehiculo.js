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
    capacidad: '',
    tipoVehiculo: '',
    mma: ''
}

export class FormNuevoVehiculo extends Component {
    constructor(props){
        super(props);
        this.state = initialState;

        this.handleOnChange = this.handleOnChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit = (event) => {
        event.preventDefault();

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

    handleOnChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
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
                            required value={this.state.matricula} onChange={this.handleOnChange} /> 
                        
                        <TextField id="capacidad" label="Capacidad" type="text" variant="outlined" name="capacidad" 
                            required value={this.state.capacidad} onChange={this.handleOnChange} /> 

                    </div>

                    <div className={classes.mmaImagen}>
                        <TextField id="mma" label="MMA" type="text" variant="outlined" name="mma" 
                            required value={this.state.mma} onChange={this.handleOnChange} /> 


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
                    <Button onClick={this.handleSubmit} size="large" type ="submit" color="primary" 
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
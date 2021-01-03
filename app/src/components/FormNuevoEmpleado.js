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

//REDUX stuff
import { addPersonal } from '../redux/actions/dataActions';
import { connect } from 'react-redux';

  const styles = theme => ({
    root: {
      '& .MuiTextField-root': {
        margin: theme.spacing(1),
      },
    },
    selectEmpty: {
      marginTop: theme.spacing(2),
    },
    formControl: {
      margin: theme.spacing(2),
      minWidth: 220,
    }, 
    upload: {
      '& > *': {
        margin: theme.spacing(1),
      },
    },
    input: {
      display: 'none',
    },
    cancel:{
      colorPrimary:"red"
    },
    formControl:{
      margin: theme.spacing(1),
      minWidth: 300,
      marginLeft: theme.spacing(15)

    },
    formSpace:{
      marginRight: theme.spacing(2),
      marginLeft: theme.spacing(5)
    },
    formLarge:{
      marginLeft: theme.spacing(5),
      minWidth: 465
    },
    uploadBoton:{
      marginTop: theme.spacing(1),
      marginLeft: theme.spacing(4),
    },
    addBoton:{
      marginLeft: theme.spacing(30),
      marginBottom: theme.spacing(2)
    },
    separador:{
      marginTop: theme.spacing(4),
      marginLeft: theme.spacing(7),
      marginBottom : theme.spacing(2)
      
    }
  });


  const initialState = {
            name:'',
            email:'',
            tlf:'',
            direccion:'',
            nss:'',
            nif:'',
            rol:'',
            open:false,
            image:'',
            step:0,
            errors:null
  }

class FormNuevoEmpleado extends Component{

    constructor(){
        super();
        this.state = initialState;
    }

    handleClose = () => {
        this.setState({
            open:false
        })
      };

    handleChange = (event) => {
        this.setState({
            rol:event.target.value
        })
      };
    handleChangeInput = (event) => {
        this.setState({
            [event.target.name]:event.target.value
        })

      };

    handleSubmit = (event) =>  {
        event.preventDefault();
        //Valores del nuevo empleado rellenado en el formulario
         const empleado = {
             name: this.state.name,
             email: this.state.email,
             tlf: this.state.tlf,
             direccion: this.state.direccion,
             nss: this.state.nss,
             nif: this.state.nif,
             image: this.state.picture,
             step: this.state.step
         };
        //Rol del nuevo empleado
         const rolEmpleado = {
            rol: this.state.rol
         }
         //step + 1
         this.props.onNextStep(empleado.step)
        //Se realiza el post
        this.props.addPersonal(rolEmpleado,empleado);
        //Se realiza para pasar el nif de este formulario 
        //al formulario de contrato para que se lo añada al empleado especifico añadido
        this.props.onSubmitNif(empleado.nif);
        //Se limpia el formulario una vez añadido el empleado
        this.setState(initialState)
     }

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
            picture:downloadURL
          })
       }.bind(this));
        console.log(snapshot)    
      },(err) => {
        console.log(err)
      })
    }
  
     render(){
      const { classes,data } = this.props;

       return(
           <div>
            <DialogContent>
            <form  id="nuevoEmpleado"noValidate onSubmit={this.handleSubmit}>
                <div>
                <FormControl className={classes.formControl}>
                <InputLabel id="demo-simple-select-label">Rol</InputLabel>

                    <Select
                    labelId="demo-simple-select-label"
                    label="Rol"
                    id="demo-simple-select"
                    value={this.state.rol}
                    onChange={this.handleChange}
                    
                    >
                    <MenuItem value="dependiente">Dependiente</MenuItem>
                    <MenuItem value="encargado">Encargado de almacén</MenuItem>
                    <MenuItem value="transportista">Transportista</MenuItem>
                    <MenuItem value="administrador">Administrador</MenuItem>

                    </Select>
                </FormControl>
                </div>
                <div>
                    <TextField 
                        autofocus
                        id="name"
                        label="Nombre"
                        type="text"
                        variant="outlined"
                        name = "name"
                        margin="normal"
                        marginRight ="normal"
                        required
                        value={this.state.name}
                        onChange={this.handleChangeInput}
                        className={classes.formSpace}
                    
                />
                    <TextField 
                        id="tlf-number"
                        id="tlf"
                        label="Teléfono"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "tlf"
                        required
                        value={this.state.tlf}
                        onChange={this.handleChangeInput}

                />
                    <TextField 
                        id="email"
                        label="Email"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        required
                        name = "email"
                        value={this.state.email}
                        onChange={this.handleChangeInput}
                        className={classes.formSpace}

                />

                    <TextField 
                        id="direccion"
                        label="Dirección"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "direccion"
                        required
                        value={this.state.direccion}
                        onChange={this.handleChangeInput}
                />
                <TextField
                        id="standard-number"
                        required
                        id="nif"
                        label="NIF"
                        variant="outlined"
                        type="text"
                        margin="normal"
                        name = "nif"
                        value={this.state.nif}
                        onChange={this.handleChangeInput}
                        className={classes.formLarge}

                />
                    <TextField
                        id="standard-number"
                        id="nss"
                        label="NSS"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "nss"
                        required
                        value={this.state.nss}
                        onChange={this.handleChangeInput}
                        className={classes.formLarge}

                />
                    <div className={classes.upload}>
                        <input
                            type="file"
                            onChange={this.handleChangeImg.bind(this)}
                            className={classes.input}
                            id="contained-button-file"
                        />
                        <label htmlFor="contained-button-file">
                        <Button className={classes.uploadBoton} variant="contained" color="primary" component="span"  startIcon={<CloudUploadIcon />} >
                            Subir
                        </Button>
                        </label>
                    </div>

        
                </div>
            
            </form>

            </DialogContent>
            <div>
        <Button className={classes.addBoton} size="large"form="nuevoEmpleado" type ="submit" color="primary" variant="contained" startIcon={<AddCircleIcon />}>
            Añadir
        </Button>
        </div>
        </div>
        )   
     }
}
FormNuevoEmpleado.propTypes={
    classes: PropTypes.object.isRequired,
    addPersonal: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    addPersonal,
    
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(FormNuevoEmpleado))

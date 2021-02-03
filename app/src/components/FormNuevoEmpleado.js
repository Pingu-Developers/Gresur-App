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
import { addPersonal,loadAlmacen,clear,loadAlmacenDisponible } from '../redux/actions/dataActions';
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
            almacen:null,
            id:null,
            enviar:false,
            errors: {name : [], nif: [], direccion: [], tlf: [] , nss: [], email:[],rol:[],almacen:[]},
            loadFoto: true,
            trabajador:{}

  }

class FormNuevoEmpleado extends Component{

    constructor(){
        super();
        this.state = initialState;

    }
    componentDidMount(){
      this.props.loadAlmacenDisponible();
    }
     ///VALIDACIONES///
     validateEmail(email) {
      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(String(email).toLowerCase());
  }

  validateNif(nif) {   
      const re = /^\d{8}[A-Z]$/;
      return re.test(String(nif));
  }

  validateTlf(tlf) {   
      const re = /^\d{9}$/;
      return re.test(String(tlf).toLowerCase());
  }

  validateNss(nss) {   
      const re = /^[0-9]{2}\s?[0-9]{10}$/;
      return re.test(String(nss).toLowerCase());
  }
 ///////////////////////////////////////////
    componentDidUpdate(prevProps,prevState){
      console.log(this.state)
      if(this.state.open != prevState.open && !this.state.open){
          this.setState({
              name:'', 
              email:'',
              tlf:'',
              direccion:'',
              nss:'',
              nif:'',
              rol:'',
              errors: {name : [], nif: [], direccion: [], tlf: [] , nss: [], email:[],rol:[],almacen:[]},
              enviar:false,
              trabajador:{}
          })
      }
      if(this.state.enviar != prevState.enviar && this.state.enviar ){
          let errores = false;
            if(this.state.rol === '' && this.state.errors.rol.length === 0){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      rol:[...state.errors.rol,'Debe seleccionar una']
                  }
              }))
              errores = true
          }else if(this.state.rol !== ''){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      rol:[]
                  }
              }))
          }else{
              errores = true
          }
          if((this.state.name.length<3 || this.state.name.length>50) && this.state.errors.name.length === 0){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      name:[...state.errors.name,'Tamaño invalido']
                  }
              }))
              errores = true
          }else if(this.state.name.length>=3 && this.state.name.length<=50){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      name:[]
                  }
              }))
          }else{
              errores = true
          }

          if(!this.validateEmail(this.state.email) && this.state.errors.email.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    email:[...state.errors.email,'Formato Email no valido']
                }
            }))
            errores = true
        }else if(this.validateEmail(this.state.email)){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    email:[]
                }
            }))
        }else{
            errores = true
        }

        if(!this.validateTlf(this.state.tlf) && this.state.errors.tlf.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    tlf:[...state.errors.tlf,'Formato Tlf no valido']
                }
            }))
            errores = true
        }else if(this.validateTlf(this.state.tlf)){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    tlf:[]
                }
            }))
        }else{
            errores = true
        }

        if((this.state.direccion.length<3 || this.state.direccion.length>100) && this.state.errors.direccion.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    direccion:[...state.errors.direccion,'Esta vacio']
                }
            }))
            errores = true
        }else if(this.state.direccion.length>1){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    direccion:[]
                }
            }))
        }else{
            errores = true
        }

        if(!this.validateNss(this.state.nss) && this.state.errors.nss.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    nss:[...state.errors.nss,'Formato Iban no valido']
                }
            }))
            errores = true
        }else if(this.validateNss(this.state.nss)){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    nss:[]
                }
            }))
        }else{
            errores = true
        }

        if(!this.validateNif(this.state.nif) && this.state.errors.nif.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    nif:[...state.errors.nif,'Formato Nif no valido']
                }
            }))
            errores = true
        }else if(this.validateNif(this.state.nif)){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    nif:[]
                }
            }))
        }else{
            errores = true
        }
        if((this.state.almacen === null ||this.state.almacen === 'No disponible') && this.state.errors.almacen.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    almacen:[...state.errors.almacen,'Debe seleccionar una']
                }
            }))
            errores = true
        }else if(this.state.almacen !== ''){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    almacen:[]
                }
            }))
        }else{
            errores = true
        }

          if(!errores){
            this.props.onNextStep(this.state.step)
            this.props.addPersonal(this.state.rol,this.state.trabajador)
           
        }
          this.setState({
              enviar:false
          })
          
      }

    }
    handleClose = () => {
        this.setState({
            open:false
        })
      };

    handleChange = (event) => {
        this.setState({
            rol:event.target.value,
        })
      };
      handleChangeAlmacen = (event) => {
        this.setState({
            almacen:event.target.value
        })
      };
    handleChangeInput = (event) => {
      this.setState(state =>({
        [event.target.name]: event.target.value,
        errors :{
            ...state.errors,
            [event.target.name]:[]
        }
    }))

      };
   
    handleSubmit = (event) =>  {
        event.preventDefault();
        //Valores del nuevo empleado rellenado en el formulario
        const almacenEmpleado = {
            almacen:{id:this.state.almacen}
        }
        const empleado = {
          name: this.state.name,
          email: this.state.email,
          tlf: this.state.tlf,
          direccion: this.state.direccion,
          nss: this.state.nss,
          nif: this.state.nif,
          image: this.state.image,
          step: this.state.step,
          almacen: almacenEmpleado.almacen
          
        }

         //step + 1
     //    this.props.onNextStep(this.state.step)
        //Se realiza el post
   //     this.props.addPersonal(rolEmpleado,empleado);
        //Se realiza para pasar el nif de este formulario 
        //al formulario de contrato para que se lo añada al empleado especifico añadido
        this.props.onSubmitNif(empleado.nif);
        //Se limpia el formulario una vez añadido el empleado
        this.setState({
          trabajador:empleado,
          enviar:true,
        })
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
            image:downloadURL,
            loadFoto:false
          })
       }.bind(this));
        console.log(snapshot)    
      },(err) => {
        console.log(err)
      })
    }


     render(){
      const { classes, handleClose , open,data } = this.props;
       return(
           <div>
            <DialogContent>
            <form  id="nuevoEmpleado" novalidate onSubmit={this.handleSubmit}>
                <div>
                <FormControl className={classes.formControl}>
                <InputLabel id="demo-simple-select-label">Rol</InputLabel>

                    <Select
                    labelId="demo-simple-select-label"
                    label="Rol"
                    id="demo-simple-select"
                    value={this.state.rol}
                    onChange={this.handleChange}
                    error={this.state.errors.rol.length>0}
                    helperText={this.state.errors.rol[0]}
                    >-
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
                        error={this.state.errors.name.length>0}
                        helperText={this.state.errors.name[0]}
                />
                    <TextField 
                        id="tlf"
                        label="Teléfono"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "tlf"
                        required
                        value={this.state.tlf}
                        onChange={this.handleChangeInput}
                        error={this.state.errors.tlf.length>0}
                        helperText={this.state.errors.tlf[0]}   
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
                        error={this.state.errors.email.length>0}
                        helperText={this.state.errors.email[0]}
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
                        error={this.state.errors.direccion.length>0}
                        helperText={this.state.errors.direccion[0]}
                />
                <TextField
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
                        error={this.state.errors.nif.length>0}
                        helperText={this.state.errors.nif[0]}
                />
                    <TextField
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
                        error={this.state.errors.nss.length>0}
                        helperText={this.state.errors.nss[0]}
                />
                {this.state.rol!='encargado'?null:
                
                <FormControl className={classes.formControl}>
                <InputLabel id="demo-simple-label">Almacen</InputLabel>

                    <Select
                    labelId="demo-simple-label"
                    label="Almacen"
                    id="demo-simple-select"
                    value={this.state.almacen}
                    onChange={this.handleChangeAlmacen}
                    error={this.state.errors.almacen.length>0}
                    helperText={this.state.errors.almacen[0]}
                    >
                   {data.almacen ===undefined?null:data.almacen.length===0?<MenuItem style={{color:'red'}}value={null}>No disponible</MenuItem>:data.almacen.map((alm)=>
                       <MenuItem value={alm.id}>{alm.direccion}</MenuItem>
                    )}

                    </Select>
                </FormControl>
                }
                    <div className={classes.upload}>
                        <input
                            type="file"
                            onChange={this.handleChangeImg.bind(this)}
                            className={classes.input}
                            id="contained-button-file"
                        />
                        <label htmlFor="contained-button-file">
                        <Button className={classes.uploadBoton} variant="contained" color="primary" component="span"  startIcon={<CloudUploadIcon />}  >
                            Subir
                        </Button>
                        </label>
                    </div>
                </div>
            
            </form>

            </DialogContent>
            <div>
              
        <Button className={classes.addBoton} size="large"form="nuevoEmpleado" onClick={this.handleSubmit} type="submit" color="primary" variant="contained" startIcon={<AddCircleIcon />}
         disabled={this.state.loadFoto}>
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
    loadAlmacen: PropTypes.func.isRequired,
    loadAlmacenDisponible: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    addPersonal,
    loadAlmacen,
    loadAlmacenDisponible,
    clear
    
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(FormNuevoEmpleado))

import React,{Component} from 'react';
import { makeStyles } from '@material-ui/core/styles';
//MUI
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import ListItemText from '@material-ui/core/ListItemText';
import ListItem from '@material-ui/core/ListItem';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';
import withStyles from '@material-ui/core/styles/withStyles';
import TextField from '@material-ui/core/TextField';
import EditTwoToneIcon from '@material-ui/icons/EditTwoTone';
import PopUpNuevoPassword from '../Dialogs/PopUpNuevoPassword'
import PropTypes from 'prop-types';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import PhoneIcon from '@material-ui/icons/Phone';
import EmailRoundedIcon from '@material-ui/icons/EmailRounded';
import RecentActorsIcon from '@material-ui/icons/RecentActors';
import WorkIcon from '@material-ui/icons/Work';
import PhotoLibraryIcon from '@material-ui/icons/PhotoLibrary';
import RoomIcon from '@material-ui/icons/Room';
//FIREBASE stuff
import firebase from "../../firebaseConfig/firebase";
//REDUX stuff
import {putPersonalProfile } from '../../redux/actions/dataActions';
import { connect } from 'react-redux';

const useStyles = makeStyles((theme) => ({
    root: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    margin: {
      margin: theme.spacing(1),
    },
    withoutLabel: {
      marginTop: theme.spacing(3),
    },
    textField: {
      width: '25ch',
    }, 
    upload: {
        '& > *': {
          margin: theme.spacing(1),
        },
      },
      uploadBoton:{
        marginTop: theme.spacing(1),
        marginLeft: theme.spacing(4),
      },
      bloque:{
          display:'inline-block'
      },
      appBar: {
        position: 'relative',
      },
      title: {
        marginLeft: theme.spacing(2),
        flex: 1,
      },
  }));
  const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
  });
  class FormUserSettings extends Component{
   
    constructor(props){
        super(props);
        let initialState = {
            name: this.props.usuario.name,
            direccion: this.props.usuario.direccion,
            nif: this.props.usuario.nif,
            nss: this.props.usuario.nss,
            tlf: this.props.usuario.tlf,
            email: this.props.usuario.email,
            image: this.props.usuario.image,
            errors: {name:[],direccion:[],password:[],nif:[],nss:[],tlf:[],email:[]},
            desactivadoName: true,
            desactivadoTlf: true,
            desactivadoDireccion: true,
            desactivadoEmail: true,
            enviar:false,
            empleado:{}
        }

        this.state = initialState;
      }
      
      handleChange = (event) => {
        this.setState(state =>({
            [event.target.name]: event.target.value,
            errors :{
                ...state.errors,
                [event.target.name]:[]
            }
        }))
        };

      handleClickEditTlf = (event) => {
        this.setState({
          desactivadoTlf:!this.state.desactivadoTlf
        })}
      handleClickEditDireccion = (event) => {
        this.setState({
          desactivadoDireccion:!this.state.desactivadoDireccion
        })} 
    handleClickEditEmail = (event) => {
        this.setState({
            desactivadoEmail:!this.state.desactivadoEmail
        })} 
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
          handleSubmit = (event)=>{
            event.preventDefault();
            const empleado = {
              version: this.props.usuario.version,
              name: this.state.name,
              direccion: this.state.direccion,
              nif: this.state.nif,
              nss: this.state.nss,
              tlf: this.state.tlf,
              email: this.state.email,
              image: this.state.image,
           };
           this.setState({
            empleado:empleado,
            enviar:true,
          })

          }
                /*********************************************VALIDACIONES ***************************************************************************************/
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
      if(this.state.open != prevState.open && !this.state.open){
          this.setState({
            name: '',
            direccion: '',
            nif: '',
            nss: '',
            tlf: '',
            email:'',
            image: '',
            errors: {name:[],direccion:[],password:[],nif:[],nss:[],tlf:[],email:[]},
              enviar:false,
              empleado:{}
          })
      }
      if(this.state.enviar != prevState.enviar && this.state.enviar ){
          let errores = false;
            
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


          if(!errores){
            this.props.putPersonalProfile(this.state.empleado)   
            this.props.onClose()
        }
          this.setState({
              enviar:false
          })
          
      }

    }
      /*********************************************VALIDACIONES ***************************************************************************************/

      render(){
        const { classes,data } = this.props;
        return(
            <Dialog fullScreen open={this.props.open} onClose={this.props.onClose} TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
            <Toolbar >
                <IconButton edge="start" color="inherit" onClick={this.props.onClose} aria-label="close">
                <CloseIcon />
                </IconButton>
                <Typography variant="h6" className={classes.title}>
                    Ajustes de usuario
                </Typography>
                <Button autoFocus color="inherit" onClick={this.handleSubmit} style={{fontWeight:'bold'}}>
                Guardar cambios
                </Button>
            </Toolbar>
            </AppBar>
            <List>
            <ListItem >
                <ListItemText primary="Ajustes de cuenta" secondary="   " />
            </ListItem>
                        <ListItem>
                            <ListItemText >
                            <div  style={{marginLeft:'32px'}}>
                            <img src={this.state.image} style={{width:'150px', height:'150px',border:'solid',borderColor:'#f5b041'}}></img>
                           
                                <input
                                    type="file"
                                    onChange={this.handleChangeImg.bind(this)}
                                    className={classes.input}
                                    style={{display:'none'}}
                                    id="contained-button-file"
                                />
                                    <label htmlFor="contained-button-file">
                                        <Button className={classes.uploadBoton} style={{bottom:'75px', marginLeft:'30px'}} variant="contained" color="primary" component="span"  endIcon={<PhotoLibraryIcon />}  >
                                            Cambiar
                                        </Button>
                                    </label>
                            </div>
                            </ListItemText>
                        </ListItem>
            <Divider />
                        <ListItem >
                        <AccountCircleIcon style={{marginRight:'10px'}}/>
                            <ListItemText >
                            <TextField style={{width:'300px'}}
                                autofocus
                                id="name"
                                type="text"
                                variant="outlined"
                                name = "name"
                                margin="normal"
                                label = "Nombre"
                                required
                                value={this.state.name}
                                onChange={this.handleChange}
                                disabled
                            />
                            </ListItemText>
                        </ListItem>
            <Divider/>
                        <ListItem>
                        <PhoneIcon  style={{marginRight:'10px'}}/>
                            <ListItemText>
                                <TextField 
                                    style={{width:'300px'}}
                                    autofocus
                                    id="tlf"
                                    label="Telefono"
                                    type="text"
                                    variant="outlined"
                                    name = "tlf"
                                    margin="normal"
                                    label = "Telefono"
                                    required
                                    value={this.state.tlf}
                                    onChange={this.handleChange}
                                    disabled={this.state.desactivadoTlf}
                                    error={this.state.errors.tlf.length>0}
                                    helperText={this.state.errors.tlf[0]}   
                            
                                />
                                <IconButton  onClick={this.handleClickEditTlf} style={{top:'15px'}}>
                                    <EditTwoToneIcon />
                                </IconButton>
                            </ListItemText>
                        </ListItem>
                <Divider/>
                <ListItem>
                            <RoomIcon  style={{marginRight:'10px'}}/>
                            <ListItemText >
                                <TextField 
                                    style={{width:'300px'}}
                                    autofocus
                                    id="direccion"
                                    label="Direccion"
                                    type="text"
                                    variant="outlined"
                                    name = "direccion"
                                    margin="normal"
                                    label = "Direccion"
                                    required
                                    value={this.state.direccion}
                                    onChange={this.handleChange}
                                    disabled={this.state.desactivadoDireccion}
                                    error={this.state.errors.direccion.length>0}
                                    helperText={this.state.errors.direccion[0]}
                                />           
                                <IconButton onClick={this.handleClickEditDireccion} style={{top:'15px'}}>
                                    <EditTwoToneIcon  />
                                </IconButton>
                            </ListItemText>
                        </ListItem>
                <Divider/>
                        <ListItem>
                            <EmailRoundedIcon  style={{marginRight:'10px'}}/>
                            <ListItemText >
                                <TextField 
                                    style={{width:'300px'}}
                                    autofocus
                                    id="email"
                                    label="Email"
                                    type="text"
                                    variant="outlined"
                                    name = "email"
                                    margin="normal"
                                    label = "Email"
                                    required
                                    value={this.state.email}
                                    onChange={this.handleChange}
                                    disabled={this.state.desactivadoEmail}
                                    error={this.state.errors.email.length>0}
                                    helperText={this.state.errors.email[0]}
                                />           
                                <IconButton onClick={this.handleClickEditEmail} style={{top:'15px'}}>
                                    <EditTwoToneIcon  />
                                </IconButton>
                            </ListItemText>
                        </ListItem>
                <Divider/>
                        <ListItem>
                            <RecentActorsIcon  style={{marginRight:'10px'}}/>
                            <ListItemText >
                                <TextField 
                                    style={{width:'300px'}}
                                    autofocus
                                    id="nif"
                                    label="NIF"
                                    type="text"
                                    variant="outlined"
                                    name = "nif"
                                    margin="normal"
                                    label = "NIF"
                                    required
                                    defaultValue={this.state.nif}
                                    disabled
                                />
                            </ListItemText>
                        </ListItem>
                <Divider/>
                        <ListItem>
                            <WorkIcon  style={{marginRight:'10px'}}/>                            
                            <ListItemText > 
                                <TextField 
                                    style={{width:'300px'}}
                                    autofocus
                                    id="nss"
                                    label="NSS"
                                    type="text"
                                    variant="outlined"
                                    name = "nss"
                                    margin="normal"
                                    label = "NSS"
                                    required
                                    defaultValue={this.state.nss}
                                    disabled
                                />
                            </ListItemText>
                        </ListItem>
                        <Divider/>
                        <ListItem>
                            <ListItemText style={{display:'flex',justifyContent:'center'}}>
                                <PopUpNuevoPassword/>
                            </ListItemText>
                        </ListItem>

            </List>
        </Dialog>
          )
      }}
      FormUserSettings.propTypes={
        classes: PropTypes.object.isRequired,
        putPersonalProfile: PropTypes.func.isRequired,
    }
    
    const mapStateToProps = (state) => ({
      data: state.data,
    });
    
    const mapActionsToProps = {
        putPersonalProfile,
    }
    
    
    export default connect(mapStateToProps,mapActionsToProps)(withStyles(useStyles)(FormUserSettings))
    
       
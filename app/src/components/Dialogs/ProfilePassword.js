import React,{Component} from 'react';

import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import withStyles from '@material-ui/core/styles/withStyles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import DialogContent from '@material-ui/core/DialogContent';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Snackbar from '../Other/SnackBar'
//Logout
import { withRouter } from "react-router";
import {logoutUser} from '../../redux/actions/userActions';

//REDUX stuff
import {putPersonalProfilePassword,clear } from '../../redux/actions/dataActions';
import { connect } from 'react-redux';
import SnackCallController from '../Other/SnackCallController';

const useStyles = makeStyles((theme) => ({
  
  }));

 class ProfilePassword extends Component{
   
    constructor(props){
        super(props);
        this.state = {
          passwordOld: '',
          passwordNew:'',
          pwds:{},
          enviar: false,
          errorsPwd: '',
          errors:{passwordOld:[],passwordNew:[]},
          open:false,
        }

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

      handleSubmit = (event)=>{
        event.preventDefault();
        const pwds = {
          "e1": this.state.passwordOld,
          "e2": this.state.passwordNew,
      };
      this.setState({
        pwds:pwds,
        enviar:true,
        errorsPwd:this.props.UI.errors
      })
      
      }

      componentDidUpdate(prevProps,prevState){
        console.log("UPDATE---"+this.state.errorsPwd)
        if(prevProps.UI.enviado != this.props.UI.enviado && this.props.UI.enviado){
          this.props.FinalizarhandleClose();
          this.props.logoutUser();
          this.props.history.push('/login');
        }
        if(this.state.open != prevState.open && !this.state.open){
            this.setState({
              passwordOld: '',
              passwordNew:'',
              pwds:{},
              enviar: false,  
              errors: {passwordOld:[],passwordNew:[]},
              errorsPwd:null

            })
        }
        if(this.state.enviar != prevState.enviar && this.state.enviar ){
            let errores = false;
              if((this.state.passwordOld === '' || this.state.passwordOld.length<6) && this.state.errors.passwordOld.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        passwordOld:[...state.errors.passwordOld,'Tamaño incorrecto']
                    }
                }))
                errores = true
            }else if( this.state.passwordOld !== '' ||this.state.passwordOld.length>=6){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        passwordOld:[]
                    }
                }))
            }else{
                errores = true
            } 
            if( (this.state.passwordNew === '' || this.state.passwordNew.length<6)&& this.state.errors.passwordNew.length === 0){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      passwordNew:[...state.errors.passwordNew,'Tamaño incorrecto']
                  }
              }))
              errores = true
          }else if( this.state.passwordNew !== '' ||this.state.passwordNew.length>=6){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      passwordNew:[]
                  }
              }))
          }else{
              errores = true
          }   

            if(!errores){
              this.props.putPersonalProfilePassword(this.state.pwds);  
              console.log("VALIDADO---"+this.state.errorsPwd)
                          
          }
            this.setState({
                enviar:false,
                errorsPwd:null
            })
        }
  
      }
        /*********************************************VALIDACIONES ***************************************************************************************/
  
  render(){
    const { classes,data, UI:{loading,errors} } = this.props;
    console.log(this.props.UI.errors)
  return (
    <div>
      <Snackbar type = "error" open = {errors==='Contraseña invalida'} truco ={true} message = {errors}></Snackbar>
                {errors==='Contraseña invalida' ? document.getElementById("botonSnack").click():null}
      <SnackCallController  message = {"Operacion realizada correctamente"} errors={errors} />
    <DialogContent>
    <form  id="nuevoContrato"noValidate onSubmit={this.handleSubmit}>
       <div style={{display:'flex', justifyContent:'center', margin:'5%'}}>
            <TextField 
                autofocus
                id="passwordOld"
                label="Contraseña actual"
                type="text"
                variant="outlined"
                name = "passwordOld"
                margin="normal"
                required
                value={this.state.passwordOld}
                onChange={this.handleChange}
                error={this.state.errors.passwordOld.length>0}
                helperText={this.state.errors.passwordOld[0]}
        />
      
        </div>
        <div style={{display:'flex', justifyContent:'center', margin:'5%'}}>
            <TextField 
                    id="passwordNew"
                    label="Nueva Contraseña"
                    type="text"
                    variant="outlined"
                    margin="normal"
                    name = "passwordNew"
                    required
                    value={this.state.passwordNew}
                    onChange={this.handleChange}     
                    error={this.state.errors.passwordNew.length>0}
                    helperText={this.state.errors.passwordNew[0]} 
            />  
        </div>
             
        </form>
        <div style={{display:'flex', justifyContent:'center'}}>
        <Button variant="contained"  color="primary" onClick={this.handleSubmit} startIcon={<LockOutlinedIcon />} className={classes.separador}>
                Guardar
        </Button>
        </div>
    </DialogContent>
    </div>

    )
    }
}
ProfilePassword.propTypes={
    classes: PropTypes.object.isRequired,
    putPersonalProfilePassword: PropTypes.func.isRequired,
    UI:PropTypes.object.isRequired,
    clear: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
  data: state.data,
  logoutUser: PropTypes.func.isRequired,
  UI: state.UI
});

const mapActionsToProps = {
    putPersonalProfilePassword,
    logoutUser,
    clear
    
}


export default withRouter(connect(mapStateToProps,mapActionsToProps)(withStyles(useStyles)(ProfilePassword)))

   
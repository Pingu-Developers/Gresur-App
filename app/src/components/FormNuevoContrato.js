import React,{Component} from 'react';
import PropTypes from 'prop-types';

//MUI stuff
import 'date-fns';
import Button from '@material-ui/core/Button';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import DialogContent from '@material-ui/core/DialogContent';
import TextField from '@material-ui/core/TextField';
import withStyles from '@material-ui/core/styles/withStyles';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import InputLabel from '@material-ui/core/InputLabel';
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from '@material-ui/pickers';
import FormControl from '@material-ui/core/FormControl';

//REDUX stuff
import { addContrato } from '../redux/actions/dataActions';
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
    container: {
        display: 'flex',
        flexWrap: 'wrap',
      },
      textField: {
        marginRight: theme.spacing(2),
        marginLeft: theme.spacing(5),
        width: 220,
      },toRight:{
          marginLeft: theme.spacing(35),
          marginRight: theme.spacing(2),
          width: 220,

      }, addBoton:{
        marginLeft: theme.spacing(30),
        marginBottom: theme.spacing(2)
      },dates:{
        marginRight: theme.spacing(32),
        width: 200
      }, formControl: {
        marginRight: theme.spacing(3),
      }, fechaInicio:{
          display:'inline-block'
      },fechaFin:{
          display:'inline-block'
      },fechaLeft:{
          marginLeft:theme.spacing(5),
          width:225
      },fechaRight:{
          marginLeft:theme.spacing(2),
          width:220
      },
      formControl:{
        margin: theme.spacing(1),
        minWidth: 250,
        marginLeft: theme.spacing(17)
  
      }
  });


  const initialState = {
            nomina:'',
            entidadBancaria:'',
            fechaInicio: new Date(),
            fechaFin: new Date(),
            tipoJornada:'',
            observaciones:'',
            errors:null,
            step:1,
            contrato:{},
            enviar:false,
            errors:{nomina:[],entidadBancaria:[],fechaInicio:[],fechaFin:[],observaciones:[],tipoJornada:[]}
          }

class FormNuevoContrato extends Component{

    constructor(props){
        super(props);
        this.state = initialState;

    }

    componentDidUpdate(prevProps,prevState){
      console.log(this.state)
      if(this.state.open != prevState.open && !this.state.open){
          this.setState({
            nomina:'', 
            entidadBancaria:'',
            fechaInicio:'',
            fechaFin:'',
            observaciones:'',
            tipoJornada:'',
              errors:{nomina:[],entidadBancaria:[],fechaInicio:[],fechaFin:[],observaciones:[],tipoJornada:[]},
              enviar:false,
              trabajador:{}
          })
      }
      if(this.state.enviar != prevState.enviar && this.state.enviar ){
          let errores = false;
          
          if(this.state.tipoJornada === '' && this.state.errors.tipoJornada.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    tipoJornada:[...state.errors.tipoJornada,'Debe seleccionar una']
                }
            }))
            errores = true
        }else if(this.state.tipoJornada !== ''){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    tipoJornada:[]
                }
            }))
        }else{
            errores = true
        }
          
          
          if(this.state.nomina === '' && this.state.errors.nomina.length === 0){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      nomina:[...state.errors.nomina,'No vacio']
                  }
              }))
              errores = true
          }else if(this.state.nomina !==''){
              this.setState(state=>({
                  errors :{
                      ...state.errors,
                      nomina:[]
                  }
              }))
          }else{
              errores = true
          }

          if(this.state.entidadBancaria === '' && this.state.errors.entidadBancaria.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    entidadBancaria:[...state.errors.entidadBancaria,'No vacio']
                }
            }))
            errores = true
        }else if(this.state.entidadBancaria !== ''){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    entidadBancaria:[]
                }
            }))
        }else{
            errores = true
        }

        if(this.dateInPast(this.state.fechaInicio) && this.state.errors.fechaInicio.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    fechaInicio:[...state.errors.fechaInicio,'Fecha no valida']
                }
            }))
            errores = true
        }else if(!this.dateInPast(this.state.fechaInicio)){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    fechaInicio:[]
                }
            }))
        }else{
            errores = true
        }
        
        if(this.dateInFuture(this.state.fechaFin) && this.state.errors.fechaFin.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    fechaFin:[...state.errors.fechaFin,'Fecha no valida']
                }
            }))
            errores = true
        }else if(!this.dateInFuture(this.state.fechaFin)){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    fechaFin:[]
                }
            }))
        }else{
            errores = true
        }

        if( this.state.observaciones.length>255 && this.state.errors.observaciones.length === 0){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    observaciones:[...state.errors.observaciones,'Tamaño invalido']
                }
            }))
            errores = true
        }else if(this.state.observaciones.length<255){
            this.setState(state=>({
                errors :{
                    ...state.errors,
                    observaciones:[]
                }
            }))
        }else{
            errores = true
        }
      

          if(!errores){
            this.props.onNextStep(this.state.step)
            this.props.addContrato(this.props.dni,this.state.contrato);
           
        }
          this.setState({
              enviar:false
          })
          
      }

    }



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
        //Valores del nuevo contrato rellenado en el formulario
         const contrato = {
            nomina: this.state.nomina,
            entidadBancaria: this.state.entidadBancaria,
            fechaInicio: this.state.fechaInicio,
            fechaFin: this.state.fechaFin,
            tipoJornada: this.state.tipoJornada,
            observaciones: this.state.observaciones,
            step: this.state.step

         };
         console.log(contrato)
        //step + 1
      //  this.props.onNextStep(contrato.step)
      //  this.props.addContrato(this.props.dni,contrato);
      this.setState({
        contrato:contrato,
        enviar:true,
      })
     }

     handleChange = (event) => {
        this.setState(state =>({
            tipoJornada:event.target.value,
            errors :{
              ...state.errors,
              [event.target.name]:[]
          }
        }))
      };
      handleFechaInicioChange = (date) => {
           this.setState(state =>({
            fechaInicio:date,
            errors :{
              ...state.errors,
          //    [fechaInicio]:[]
          }
        }))
      };
      handleFechaFinChange = (date) => {
        this.setState(state =>({
         fechaFin:date,
         errors :{
          ...state.errors,
       //   [fechaFin]:[]
      }
     }))
   };

    dateInPast = function(fecha) {
      const firstDate = new Date()
      if (firstDate.setHours(0, 0, 0, 0) < fecha.setHours(0, 0, 0, 0)) {
        return true;
      }

      return false;
  };
    dateInFuture = function(fecha) {
      const firstDate = new Date()
      if (firstDate.setHours(0, 0, 0, 0) > fecha.setHours(0, 0, 0, 0)) {
        return true;
      }

      return false;
    };
  
     render(){
      const { classes,data } = this.props;

       return(
           <div>
            <DialogContent>
            <form  id="nuevoContrato"noValidate onSubmit={this.handleSubmit}>
                <div>
                <FormControl  className={classes.formControl}  >

                <InputLabel   id="demo-simple-select-helper-label">Tipo Jornada</InputLabel >

                    <Select
                        label="demo-simple-select-helper-label"                            
                        id="tipoJornada"
                        value={this.state.tipoJornada}
                        onChange={this.handleChange}
                        error={this.state.errors.tipoJornada.length>0}
                        helperText={this.state.errors.tipoJornada[0]}
                        >
                        <MenuItem value="COMPLETA">COMPLETA</MenuItem>
                        <MenuItem value="MEDIA_JORNADA">MEDIA_JORNADA</MenuItem>
                        <MenuItem value="PARCIAL">PARCIAL</MenuItem>

                    </Select>
            </FormControl>
                    <TextField 
                        autofocus
                        id="nomina"
                        label="Nomina"
                        type="text"
                        variant="outlined"
                        name = "nomina"
                        margin="normal"
                        required
                        value={this.state.nomina}
                        onChange={this.handleChangeInput}
                        className={classes.formSpace}
                        error={this.state.errors.nomina.length>0}
                        helperText={this.state.errors.nomina[0]}
                />
                    <TextField 
                        id="entidadBancaria"
                        label="Entidad Bancaria"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "entidadBancaria"
                        required
                        value={this.state.entidadBancaria}
                        onChange={this.handleChangeInput}
                        error={this.state.errors.entidadBancaria.length>0}
                        helperText={this.state.errors.entidadBancaria[0]}
                />
                <div>
                    <span className={classes.fechaInicio}>
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <Grid container justify="space-around">
                            <KeyboardDatePicker
                            className={classes.fechaLeft}
                            disableToolbar
                            autoOk={true}
                            variant="inline"
                            format="dd/MM/yyyy"
                            margin="normal"
                            label="Fecha Inicio"
                            value={this.state.fechaInicio}
                            onChange={this.handleFechaInicioChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                            error={this.state.errors.fechaInicio.length>0}
                            helperText={this.state.errors.fechaInicio[0]}
                                />       
                        </Grid>
                    </MuiPickersUtilsProvider>
                    </span>                  
                    <span className={classes.fechaFin}>
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <Grid container justify="space-around">
                            <KeyboardDatePicker
                             className={classes.fechaRight}
                            disableToolbar
                            autoOk={true}
                            variant="inline"
                            format="dd/MM/yyyy"
                            margin="normal"
                            id="date"
                            label="Fecha Fin"
                            value={this.state.fechaFin}
                            onChange={this.handleFechaFinChange}
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                            error={this.state.errors.fechaFin.length>0}
                            helperText={this.state.errors.fechaFin[0]}
                            />
                        
                        </Grid>
                    </MuiPickersUtilsProvider>
                    </span>
                    </div>
               

                    <TextField
                        id="observaciones"
                        label="Observaciones"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "observaciones"
                        value={this.state.observaciones}
                        onChange={this.handleChangeInput}
                        className={classes.formLarge}
                        error={this.state.errors.observaciones.length>0}
                        helperText={this.state.errors.observaciones[0]}
                />

                </div>
            
            </form>

            </DialogContent>
            <div>
        <Button className={classes.addBoton} size="large"form="nuevoContrato" onClick={this.handleSubmit} 
                type ="submit" color="primary" variant="contained" startIcon={<AddCircleIcon />}>
            Añadir
        </Button>
        </div>
        </div>
        )   
     }
}
FormNuevoContrato.propTypes={
    classes: PropTypes.object.isRequired,
    addContrato: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    addContrato,
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(FormNuevoContrato))

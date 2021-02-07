import React,{Component} from 'react';

import { red } from '@material-ui/core/colors';
import CloseOutlinedIcon from '@material-ui/icons/CloseOutlined';
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import DialogContent from "@material-ui/core/DialogContent";
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import PropTypes from 'prop-types';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
  } from '@material-ui/pickers';
import FormControl from '@material-ui/core/FormControl';
import withStyles from '@material-ui/core/styles/withStyles';
import EditIcon from '@material-ui/icons/Edit';
import DialogTitle from '@material-ui/core/DialogTitle';

//REDUX stuff
import { editContrato } from '../../redux/actions/dataActions';
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
  
      }, wdialogue: {
        color: 'white'
      }
  });


 class EditDialogue extends Component{

    constructor(props){
        super(props);
        this.state = {
        nomina:this.props.edicion.nomina,
        entidadBancaria:this.props.edicion.entidadBancaria,
        fechaInicio: this.props.edicion.fechaInicio,
        fechaFin: this.props.edicion.fechaFin,
        tipoJornada:this.props.edicion.tipoJornada,
        observaciones:this.props.edicion.observaciones,
        personal: this.props.edicion.personal,
        errors:null,
        open:false
    }
    }

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
            personal: this.state.personal,

         };
        this.props.editContrato(contrato.personal.nif,contrato);
        this.setState({
            open:false
        })
     }
     handleChange = (event) => {
        this.setState({
            tipoJornada:event.target.value
        })
      };
      handleFechaInicioChange = (date) => {
           this.setState({
            fechaInicio:date
        })
      };
      handleFechaFinChange = (date) => {
        this.setState({
         fechaFin:date
     })
   };
   handleChangeInput = (event) => {
    this.setState({
        [event.target.name]:event.target.value
    })
  };

   handleClickOpen = () => {
       this.setState({
           open:true
       })

  };
 
   handleClose = () => {
    this.setState({
        open:false
    })
  };
  render(){
    const { classes,data } = this.props;

  return (
    <div>
      <Button variant="contained" color="secondary" className={classes.wdialogue} onClick={this.handleClickOpen}> 
        <EditIcon />
      </Button>
      <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
      <CloseOutlinedIcon style={{ color: red[600] }}  onClick={this.handleClose} />
      <DialogTitle id="form-dialog-title" align="center">Editar contrato</DialogTitle>
      <DialogContent>
            <form  id="editContrato" noValidate onSubmit={this.handleSubmit}>
                <div>
                <FormControl  className={classes.formControl} >

                <InputLabel  id="demo-simple-select-helper-label">Tipo Jornada</InputLabel>

                    <Select
                        label="demo-simple-select-helper-label"                            
                        id="tipoJornada"
                        value={this.state.tipoJornada}
                        onChange={this.handleChange}
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

                />

                </div>
            
            </form>

            </DialogContent>
            <div>
            <Button className={classes.addBoton} size="large"form="editContrato" type ="submit" color="primary" variant="contained" startIcon={<EditIcon />}>
            Editar
        </Button>
        </div>
        </Dialog>
    </div>
    
  );}
}
EditDialogue.propTypes={
    classes: PropTypes.object.isRequired,
    editContrato: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    editContrato
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(EditDialogue))

   
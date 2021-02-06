import React,{Component} from 'react';

import { red } from '@material-ui/core/colors';
import CloseOutlinedIcon from '@material-ui/icons/CloseOutlined';
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import DialogContent from "@material-ui/core/DialogContent";
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import PropTypes from 'prop-types';
import FormControl from '@material-ui/core/FormControl';
import withStyles from '@material-ui/core/styles/withStyles';
import DialogTitle from '@material-ui/core/DialogTitle';
import LocalShippingIcon from '@material-ui/icons/LocalShipping';

//REDUX stuff
import { loadVehiculosITVSeguroDisponibilidadByTransportista,enReparto } from '../../redux/actions/dataActions';
import { connect } from 'react-redux';

 const styles = theme => ({
    root: {
      '& .MuiTextField-root': {
        margin: theme.spacing(1),
      },
    },
    cancel:{
      colorPrimary:"red"
    }, container: {
        display: 'flex',
        flexWrap: 'wrap',
      }, formControl: {
        marginRight: theme.spacing(3),
      },
      formControl:{
        margin: theme.spacing(1),
        minWidth: 250,
        marginLeft: theme.spacing(17)
  
      }, wdialogue: {
        width:230,        
      },addBoton:{
        marginLeft:theme.spacing(28),
        marginBottom: theme.spacing(2),
        marginTop: theme.spacing(2)

      },selector:{
        marginRight:theme.spacing(15),
        width:300
      }
  });

  const initialState = {
    vehiculo:null,
    open:false,
    errors:null
}

 class ButtonEnReparto extends Component{

    constructor(props){
        super(props);
        this.state = initialState

      }

    componentDidMount(){
        this.props.loadVehiculosITVSeguroDisponibilidadByTransportista();
    }
    

    handleSubmit = (event) =>  {
        event.preventDefault();
        //Seleccionamos vehiculo a poner enReparto
        this.props.enReparto(this.props.idPedido,this.state.vehiculo);
        this.setState({
            open:false
        })
     }
     handleChange = (event) => {
        this.setState({
          vehiculo:event.target.value
        })
      };

     // this.props.personal
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
      <Button disabled={this.props.activacion==='PREPARADO'?false:true}variant="contained" color="primary" className={classes.wdialogue} onClick={this.handleClickOpen}  startIcon={<LocalShippingIcon />}>
        Poner en Reparto
      </Button>
      <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
      <CloseOutlinedIcon style={{ color: red[600] }}  onClick={this.handleClose} />
      <DialogTitle id="form-dialog-title" align="center">Seleccione un vehículo</DialogTitle>
      <DialogContent>
            <form  id="enReparto" noValidate onSubmit={this.handleSubmit}>
                <div>
                <FormControl  className={classes.formControl} >

                <InputLabel  id="demo-simple-select-helper-label"> Vehiculo</InputLabel>

                    <Select 
                        
                        label="demo-simple-select-helper-label"                            
                        id="tipoJornada"
                        value={this.state.vehiculo}
                        onChange={this.handleChange}
                        className={classes.selector}
                        >
                        {data.vehiculos===undefined?null:data.vehiculos
                            .filter(veh=> veh.disponibilidad==='DISPONIBLE' & veh.vehiculo.tipoVehiculo != 'CARRETILLA_ELEVADORA')
                            .map(vehF=>
                           <MenuItem dense value={vehF.vehiculo}>{vehF.vehiculo.tipoVehiculo + " (" + vehF.vehiculo.matricula  +")"}</MenuItem>
                        )}
                    </Select>
            </FormControl>

                </div>
            
            </form>

            </DialogContent>
            <div>
            <Button className={classes.addBoton} size="large"form="enReparto" type ="submit" color="primary" variant="contained" startIcon={<LocalShippingIcon />}>
            Repartir
        </Button>
        </div>
        </Dialog>
    </div>
    
  );}
}
ButtonEnReparto.propTypes={
    classes: PropTypes.object.isRequired,
    loadVehiculosITVSeguroDisponibilidadByTransportista: PropTypes.func.isRequired,
    enReparto: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    loadVehiculosITVSeguroDisponibilidadByTransportista,
    enReparto,
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(ButtonEnReparto))

   
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

//Components
import {loadPedidos, loadPedidosByEstado, updatePedido} from '../redux/actions/dataActions';
import { connect } from 'react-redux';

const styles = theme => ({


});

export class FormEditarPedido extends Component {
    
    constructor(props){
        super(props);
        this.state={};

        this.handleOnChange = this.handleOnChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.setState({
            ...this.props.pedido
        })
    }


    handleSubmit = (event) => {
        event.preventDefault();

        const pedido = {
            ...this.state
        }
        
        this.props.updatePedido(this.props.estado, this.props.orden, pedido)
        this.props.cerrar();
    }

    handleOnChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    };

    render() {
        const {classes}= this.props;

        return (
            
            <div>
                
                <div className= {classes.selectVehiculo}>
                    
                    <TextField id="fechaEnvio" label="Fecha de envio" type="date" variant="outlined" name="fechaEnvio" 
                            required value={this.state.fechaEnvio} onChange={this.handleOnChange} /> 
                    
                </div>
                

                <div style= {{padding:'0 60px 0 60px'}}>
                    <div className={classes.matriculaCapacidad}>

                        <TextField id="direccionEnvio" label="Direccion de envio" type="text" variant="outlined" name="direccionEnvio" 
                            required value={this.state.direccionEnvio} onChange={this.handleOnChange} /> 
                        
                        <TextField id="nombre" label="Nombre Cliente" type="text" variant="outlined" name="name" 
                            required value={this.state.facturaEmitida.cliente.name} onChange={this.handleOnChange} /> 

                    </div>

                    <div className={classes.mmaImagen}>
                        <TextField id="NIF" label="NIF" type="text" variant="outlined" name="nif" 
                            required value={this.state.facturaEmitida.cliente.nif} onChange={this.handleOnChange} /> 

                        <TextField id="email" label="Correo electronico" type="text" variant="outlined" name="email" 
                            required value={this.state.facturaEmitida.cliente.email} onChange={this.handleOnChange} /> 
                        
                        <TextField id="tlf" label="Telefono" type="text" variant="outlined" name="tlf" 
                            required value={this.state.facturaEmitida.cliente.tlf} onChange={this.handleOnChange} /> 
                        
                    </div>
                </div>
                
                <div className={classes.addBoton}>
                    <Button disabled={this.state.loading} onClick={this.handleSubmit} size="large" type ="submit" color="primary" 
                        variant="contained" startIcon={<AddCircleIcon />}>
                        Actualizar
                    </Button>
                </div>

            </div>
        )
    }

}

FormEditarPedido.propTypes={
    classes: PropTypes.object.isRequired,
    loadPedidos: PropTypes.object.isRequired,
    loadPedidosByEstado: PropTypes.object.isRequired,
    updatePedido: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
     loadPedidos,
     loadPedidosByEstado,
     updatePedido
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(FormEditarPedido))
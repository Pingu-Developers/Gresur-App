import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import TextField from '@material-ui/core/TextField';
import DateFnsUtils from '@date-io/date-fns';
import { MuiPickersUtilsProvider, KeyboardDatePicker} from '@material-ui/pickers';

import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

import { loadPedidos, loadPedidosByEstado, updatePedido } from '../redux/actions/dataActions';

const useStyles = makeStyles((theme) => ({
    root: {
    },

    main: {
        display: 'grid',
        gridTemplateRows: '1fr 1fr 1fr 1fr',
        gridTemplateColumns: '1fr 1fr',
        width: '100%', 
        alignItems: 'center',
        justifyItems: 'center',
        gridGap: 35
    },

    formInput: {
        margin: 0,
        height: '100%',
        width: '100%'
    }

}));

export default function FormUpdatePedido(props) {
    const classes = useStyles();
    const pedido = Object.assign({},props.pedido);
    const estado = props.estado;
    const orden = props.orden;

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [fechaEnvio, setFechaEnvio] = React.useState(pedido.fechaEnvio);
    const [direccionEnvio, setDireccionEnvio] = React.useState(pedido.direccionEnvio);
    const [nombre, setNombre] = React.useState(pedido.facturaEmitida.cliente.name);
    const [nif, setNIF] = React.useState(pedido.facturaEmitida.cliente.nif);
    const [email, setEmail] = React.useState(pedido.facturaEmitida.cliente.email);
    const [tlf, setTLF] = React.useState(pedido.facturaEmitida.cliente.tlf);

    const handleChangeFechaEnvio = (event) => {
        setFechaEnvio(event.target.value);
    };

    const handleChangeDirEnvio = (event) => {
        setDireccionEnvio(event.target.value);
    };

    const handleChangeNombre = (event) => {
        setNombre(event.target.value);
    };

    const handleChangeNIF = (event) => {
        setNIF(event.target.value);
    };

    const handleChangeEmail = (event) => {
        setEmail(event.target.value);
    };

    const handleChangeTLF = (event) => {
        setTLF(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        pedido["fechaEnvio"] = fechaEnvio;
        pedido["direccionEnvio"] = direccionEnvio;
        pedido["facturaEmitida"]["cliente"]["name"] = nombre;
        pedido["facturaEmitida"]["cliente"]["nif"] = nif;
        pedido["facturaEmitida"]["cliente"]["email"] = email;
        pedido["facturaEmitida"]["cliente"]["tlf"] = tlf;

        dispatch(updatePedido(estado,orden,pedido));
        props.cerrar();
    }
  
    return (
        <div className={classes.main}>
            
            <MuiPickersUtilsProvider
                className = {classes.formInput}
                style={{gridRow: 1, gridColumn: 1}} 
                utils={DateFnsUtils}>
                    <KeyboardDatePicker
                    className={classes.formInput}
                    disableToolbar
                    autoOk={true}
                    variant="inline"
                    format="dd/MM/yyyy"
                    margin="normal"
                    label="Fecha De Envio"
                    required
                    inputVariant='outlined'
                    value={fechaEnvio}
                    onChange={(date)=> {setFechaEnvio(date)}}
                        
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                    />      
            </MuiPickersUtilsProvider>

            <TextField className = {classes.formInput} style={{gridRow: 1, gridColumn: 2}} id="direccionEnvio" label="Direccion de envio" type="text" variant="outlined" name="direccionEnvio" 
                required value={direccionEnvio} onChange={handleChangeDirEnvio} /> 

            <TextField className = {classes.formInput} style={{gridRow: 2, gridColumn: 1}} id="nombre" label="Nombre Cliente" type="text" variant="outlined" name="name" 
                required value={nombre} onChange={handleChangeNombre} /> 

            <TextField className = {classes.formInput} style={{gridRow: 2, gridColumn: 2}} id="NIF" label="NIF" type="text" variant="outlined" name="nif" 
                required value={nif} onChange={handleChangeNIF} /> 

            <TextField className = {classes.formInput} style={{gridRow: 3, gridColumn: 1}} id="email" label="Correo electronico" type="text" variant="outlined" name="email" 
                required value={email} onChange={handleChangeEmail} /> 
            
            <TextField className = {classes.formInput} style={{gridRow: 3, gridColumn: 2}} id="tlf" label="Telefono" type="text" variant="outlined" name="tlf" 
                required value={tlf} onChange={handleChangeTLF} />   
            
            <Button className = {classes.formInput} style={{gridRow: 4, gridColumn: '1 / span 2'}} onClick={handleSubmit} size="large" type ="submit" color="primary" 
                variant="contained" startIcon={<AddCircleIcon />}>
                Actualizar
            </Button>

        </div>
    );
}
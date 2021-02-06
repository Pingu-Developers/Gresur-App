import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import TextField from '@material-ui/core/TextField';
import DateFnsUtils from '@date-io/date-fns';
import { MuiPickersUtilsProvider, KeyboardDatePicker} from '@material-ui/pickers';

import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

import { updatePedido } from '../../redux/actions/dataActions';

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

    const {estado,orden,pageNo,pageSize}=props

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [fechaEnvio, setFechaEnvio] = React.useState(pedido.fechaEnvio);
    const [direccionEnvio, setDireccionEnvio] = React.useState(pedido.direccionEnvio);
    const [nombre, setNombre] = React.useState(pedido.facturaEmitida.cliente.name);
    const [nif, setNIF] = React.useState(pedido.facturaEmitida.cliente.nif);
    const [email, setEmail] = React.useState(pedido.facturaEmitida.cliente.email);
    const [tlf, setTLF] = React.useState(pedido.facturaEmitida.cliente.tlf);
    const [enviar, setEnviar] = React.useState(false);
    const [error, setError] = React.useState({
        fechaEnvio:[], direccionEnvio:[], nombre: [], nif:[], email: [], tlf:[]
    });

    const handleChangeFechaEnvio = (date) => {
        setError({
            ...error, 
            fechaEnvio:[]
        })
        setFechaEnvio(date);
    };

    const handleChangeDirEnvio = (event) => {
        setError({
            ...error, 
            direccionEnvio:[]
        })
        setDireccionEnvio(event.target.value);
    };

    const handleChangeNombre = (event) => {
        setError({
            ...error, 
            nombre:[]
        })
        setNombre(event.target.value);
    };

    const handleChangeNIF = (event) => {
        setError({
            ...error, 
            nif:[]
        })
        setNIF(event.target.value);
    };

    const handleChangeEmail = (event) => {
        setError({
            ...error, 
            email:[]
        })
        setEmail(event.target.value);
    };

    const handleChangeTLF = (event) => {
        setError({
            ...error, 
            tlf:[]
        })
        setTLF(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        setEnviar(true);
    };

    const validateNif = (nif) => {
        const re = /^\d{8}[A-Z]$/;
        return re.test(String(nif));
    };

    const validateTlf = (tlf) => {   
        const re = /^\d{9}$/;
        return re.test(String(tlf).toLowerCase());
    };

    const validateEmail = (email) => {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    };

    const dateInPast = function(fechaEnvio) {
        const fechaRealizacion = new Date(pedido.fechaRealizacion);
        if (fechaRealizacion < fechaEnvio) {
          return true;
        }
      
        return false;
    };



    React.useEffect(()=> {
        if(enviar){
            var valid = true;

            var errores = {fechaEnvio:[], direccionEnvio:[], nombre: [], nif:[], email: [], tlf:[]};

            if(!dateInPast(fechaEnvio)){
                valid= false; 
                errores = {
                    ...errores,
                    fechaEnvio:['Fecha no valida']
                }
            }

            if(direccionEnvio.length<3){
                valid=false;
                errores = {
                    ...errores,
                    direccionEnvio:['Direccion con insuficientes caracteres']
                }
            }

            if(direccionEnvio.length>100){
                valid=false;
                errores = {
                    ...errores,
                    direccionEnvio:['Direccion con demasiados caracteres']
                }
            }

            if(nombre.length<3){
                valid=false;
                errores = {
                    ...errores,
                    nombre:['Nombre con insuficientes caracteres']
                }
            }

            if(nombre.length>50){
                valid=false;
                errores = {
                    ...errores,
                    nombre:['Nombre con demasidos caracteres']
                }
            }

            if(!validateNif(nif)){
                valid=false;
                errores = {
                    ...errores,
                    nif: ['Formato de NIF no valido']
                }
            }
            
            if(!validateEmail(email)){
                valid=false;
                errores = {
                    ...errores,
                    email: ['Formato de email no valido']
                }
            }

            if(!validateTlf(tlf)){
                valid=false;
                errores = {
                    ...errores,
                    tlf: ['Formato de telefono no valido']
                }
            }

            setError(errores);

            if(valid) {

                const fechaEnvioDef = new Date(Date.UTC(fechaEnvio.getFullYear(), fechaEnvio.getMonth(), fechaEnvio.getDate(), 
                fechaEnvio.getHours(), fechaEnvio.getMinutes(), fechaEnvio.getSeconds(), fechaEnvio.getMilliseconds()))

                pedido["fechaEnvio"] = fechaEnvioDef;
                pedido["direccionEnvio"] = direccionEnvio;
                pedido["facturaEmitida"]["cliente"]["name"] = nombre;
                pedido["facturaEmitida"]["cliente"]["nif"] = nif;
                pedido["facturaEmitida"]["cliente"]["email"] = email;
                pedido["facturaEmitida"]["cliente"]["tlf"] = tlf;

                dispatch(updatePedido(estado,orden,pedido,pageNo,pageSize));
                props.cerrar();
            }

            setEnviar(false);
        }
    },[enviar])
  
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
                    error={error.fechaEnvio.length>0} 
                    helperText={error.fechaEnvio[0]}
                    inputVariant='outlined'
                    value={fechaEnvio}
                    onChange={(date)=> {handleChangeFechaEnvio(date)}}
                        
                    KeyboardButtonProps={{
                        'aria-label': 'change date',
                    }}
                    />      
            </MuiPickersUtilsProvider>

            <TextField className = {classes.formInput} style={{gridRow: 1, gridColumn: 2}} id="direccionEnvio" 
                label="Direccion de envio" type="text" variant="outlined" name="direccionEnvio" 
                error={error.direccionEnvio.length>0} helperText={error.direccionEnvio[0]}
                required value={direccionEnvio} onChange={handleChangeDirEnvio} /> 

            <TextField className = {classes.formInput} style={{gridRow: 2, gridColumn: 1}} id="nombre" 
                label="Nombre Cliente" type="text" variant="outlined" name="name" 
                error={error.nombre.length>0} helperText={error.nombre[0]}
                required value={nombre} onChange={handleChangeNombre} /> 

            <TextField className = {classes.formInput} style={{gridRow: 2, gridColumn: 2}} id="NIF" 
                label="NIF" type="text" variant="outlined" name="nif" 
                error={error.nif.length>0} helperText={error.nif[0]}
                required value={nif} onChange={handleChangeNIF} /> 

            <TextField className = {classes.formInput} style={{gridRow: 3, gridColumn: 1}} id="email" 
                label="Correo electronico" type="text" variant="outlined" name="email" 
                error={error.email.length>0} helperText={error.email[0]}
                required value={email} onChange={handleChangeEmail} /> 
            
            <TextField className = {classes.formInput} style={{gridRow: 3, gridColumn: 2}} id="tlf" 
                label="Telefono" type="text" variant="outlined" name="tlf" 
                error={error.tlf.length>0} helperText={error.tlf[0]}
                required value={tlf} onChange={handleChangeTLF} />   
            
            <Button className = {classes.formInput} style={{gridRow: 4, gridColumn: '1 / span 2'}} 
                onClick={handleSubmit} size="large" type ="submit" color="primary" 
                variant="contained" startIcon={<AddCircleIcon />}>
                Actualizar
            </Button>

        </div>
    );
}
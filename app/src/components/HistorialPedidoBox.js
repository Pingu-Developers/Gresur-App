import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import CalendarTodayIcon from '@material-ui/icons/CalendarToday';
import TimerIcon from '@material-ui/icons/Timer';
import MonetizationOnIcon from '@material-ui/icons/MonetizationOn';
import EventIcon from '@material-ui/icons/Event';
import LocalShippingIcon from '@material-ui/icons/LocalShipping';
import PermContactCalendarIcon from '@material-ui/icons/PermContactCalendar';
import RecentActorsIcon from '@material-ui/icons/RecentActors';
import EmailIcon from '@material-ui/icons/Email';
import PhoneIcon from '@material-ui/icons/Phone';
import DoneAllIcon from '@material-ui/icons/DoneAll';
import AddIcon from '@material-ui/icons/Add';
import { FaBox } from "react-icons/fa";
import Button from '@material-ui/core/Button';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import Checkbox from '@material-ui/core/Checkbox';
import CheckBoxSharpIcon from '@material-ui/icons/CheckBoxSharp';
import CheckBoxOutlineBlankSharpIcon from '@material-ui/icons/CheckBoxOutlineBlankSharp';

import { setEstaPagadoFacturaE } from '../redux/actions/dataActions';


const useStyles = makeStyles((theme) => ({
  root: {
   
  },

  main: {
    display: 'inline-block',
    padding: 20,
    width: '40vw',
  },

  productosPedido: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '0.1fr 1fr 0.5fr',
    width: '100%',
    alignContent: 'flex-start',
  },

  cantNombre: {
    gridColumn: '2',
    gridRow: '1'
  },

  precio: {
    gridColumn: '3',
    gridRow: '1'
  }, 

  campoProductos: {
    overflowY: 'auto',
    height: 120,
  },

  borderMain: {
    borderColor: '#bdbdbd',
    borderStyle: 'solid',
    borderRadius: 24,
    border: 2,
    backgroundColor: '#f7f7f7',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.15), 0 6px 20px 0 rgba(0, 0, 0, 0.14)'
  }, 

  legend: {
    textDecoration: 'underline'
  }, 

  iconoObjeto: {
    display: 'flex',
    alignItems: 'center',
  },

  icono: {
    marginRight: 5,
  },

  iconoProductos: {
    gridRow: 1,
    gridColumn: 1,
    marginTop: 20
  },

  infoCliente: {
    borderColor: '#bdbdbd',
    borderRadius: 24,
    borderStyle: 'solid',
    border: 1,
  }, 

  datosContacto: {
    borderColor: '#bdbdbd',
    borderRadius: 24,
    borderStyle: 'solid',
    border: 1,
  },

  botonPagado: {
    marginLeft: 20,
    borderRadius: 20,
  },

}));

export default function HistorialPedidoBox(props) {
  const classes = useStyles();
  const pedido = props.pedido;

  const counter = useSelector(state => state);
  const dispatch = useDispatch();

  
  const handleSubmitPago = (id,event) => {
    event.preventDefault();
    dispatch(setEstaPagadoFacturaE(id))
  }

  return (
    <div className={classes.main}>
        <fieldset className={classes.borderMain}>
          <legend className={classes.legend}><b>PEDIDO-{pedido.id}</b></legend>

          <div className={classes.iconoObjeto}>
            <CalendarTodayIcon className={classes.icono}/>
            <p><b>Fecha de realizacion:</b> {pedido.fechaRealizacion}</p>
          </div>

          <div className={classes.iconoObjeto}>
            <TimerIcon className={classes.icono}/>
            <p><b>Estado del pedido:</b> {pedido.estado}</p>
          </div>
          
          <div className={classes.iconoObjeto}>
            <MonetizationOnIcon className={classes.icono}/>
            <p><b>Pedido pagado:</b> {pedido.facturaEmitida.estaPagada? 'SI' : 'NO'}</p>
            <Button className={classes.botonPagado} variant='outlined' color="secondary" size="small" onClick={(event) => handleSubmitPago(pedido.id,event)}>
              {pedido.facturaEmitida.estaPagada? 'PAGADO' : 'NO PAGADO'}
            </Button>          
          </div>

          <div className={classes.iconoObjeto}>
            <EventIcon className={classes.icono}/>
            <p><b>Fecha de envio:</b> {pedido.fechaEnvio}</p>
          </div>

          <div className={classes.iconoObjeto}>
            <LocalShippingIcon className={classes.icono}/>
            <p><b>Direccion de envio:</b> {pedido.direccionEnvio}</p>
          </div>
          
          <br/>

          <fieldset className={classes.infoCliente}>
              <legend className={classes.legend}><b>INFORMACION DEL CLIENTE</b></legend>

              <div className={classes.iconoObjeto}>
                <PermContactCalendarIcon className={classes.icono}/>
                <p><b>Nombre y apellidos:</b> {pedido.facturaEmitida.cliente.name} {pedido.facturaEmitida.cliente.apellidos}</p>
              </div>

              <div className={classes.iconoObjeto}>
                <RecentActorsIcon className={classes.icono}/>
                <p><b>NIF:</b> {pedido.facturaEmitida.cliente.nif}</p>
              </div>

              <fieldset className={classes.datosContacto}>
                  <legend className={classes.legend}><b>DATOS DE CONTACTO</b></legend>

                  <div className={classes.iconoObjeto}>
                    <EmailIcon className={classes.icono}/>
                    <p><b>Correo electronico:</b> {pedido.facturaEmitida.cliente.email}</p>
                  </div>

                  <div className={classes.iconoObjeto}>
                    <PhoneIcon className={classes.icono}/>
                    <p><b>Telefono:</b> {pedido.facturaEmitida.cliente.tlf}</p>
                  </div>
              </fieldset>

          </fieldset>

          <br/>

          <fieldset className={classes.campoProductos}>
              <legend className={classes.legend}><b>PRODUCTOS DEL PEDIDO</b></legend>
              {pedido.facturaEmitida.lineasFacturas.map((row) => (
                <div className={classes.productosPedido}>
                  <FaBox className={classes.iconoProductos}/>
                  <p className={classes.cantNombre}><b>{row.cantidad}</b> x {row.producto.nombre}</p>
                  <p className={classes.precio}>{row.precio}€</p>
                </div>
              ))}
          </fieldset>

          <br/>

          <div className={classes.iconoObjeto}>
            <DoneAllIcon className={classes.icono}/>
            <p><b>TOTAL DEL PEDIDO</b> ................................................................ {pedido.facturaEmitida.importe}€</p>
          </div>

        </fieldset>
    </div>
  );
}
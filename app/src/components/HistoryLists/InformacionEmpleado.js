import React from 'react';

//MATERIAL UI Stuff
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import DeleteDialogue from '../Dialogs/DeleteDialogue';
import EditDialogue from '../Dialogs/EditDialogue';

const useStyles = makeStyles((theme) => ({
  root: {
    position: 'relative',
    display: 'grid',
    gridTemplateRows: '2fr 10fr',
    backgroundColor: '#dadada',
    width: '100%',
    borderRadius: 20,
    marginBottom: 30,
    color: '#7a7a7a',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.15), 0 6px 20px 0 rgba(0, 0, 0, 0.14)'
  },
  nombreElmpleado: {
    display: 'flex',
    margin: 0,
    alignItems: 'center',
    fontWeight: 'bold',
    fontSize: 20,
    paddingLeft: 20,
    width: 'calc(100% - 20px)',
    margin: '10px 0px 10px 0px',
  },
  infoBody: {
    display: 'grid',
    padding: 20,
    gridGap: '10px 20px',
    alignItems: 'center',
    gridTemplateRows: '1fr 1fr 1fr 1fr',
    gridTemplateColumns: '1.2fr 2fr 2fr 3fr',
    backgroundColor: '#fafafa',
    borderRadius: 20,
  },
  infoContrato: {
    height: 'calc(100% - 20px)',
    backgroundColor: 'white',
    borderRadius: 20,
    border: '1px solid #bdbdbd',
    display: 'grid',
    padding: '10px 20px 10px 20px',
  },
  buttonDiv: {
    position: 'absolute',
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gridGap: 10,
    right: 0,
    bottom: -15
  }
}));

export default function InformacionEmpleado(props) {
  const classes = useStyles();
  return (
      <div className = {classes.root}>
          <Typography className = {classes.nombreElmpleado}>{!props.datos.personal ? null:props.datos.personal.name}</Typography>
          
          <div className = {classes.infoBody}>
            <img style = {{gridRow: '1 / span 4', gridColumn: '1', borderRadius: 20}} src={!props.datos.personal ? null:props.datos.personal.image} height = '100%' width = '100%' />      
            <Typography style = {{gridRow: 1, gridColumn: 2}}><b>Nombre:</b> {!props.datos.personal ? null:props.datos.personal.name} </Typography>
            <Typography style = {{gridRow: 2, gridColumn: 2}}><b>DNI:</b> {!props.datos.personal ? null:props.datos.personal.nif}</Typography>
            <Typography style = {{gridRow: 3, gridColumn: 2}}><b>Dirección:</b> {!props.datos.personal ? null:props.datos.personal.direccion}</Typography>
            {!props.datos.personal.almacen ? null :
              <Typography style = {{gridRow: 4, gridColumn: 2}}><b>Almacén:</b> {!props.datos.personal ? null:props.datos.personal.almacen.direccion}</Typography>
            }
            <Typography style = {{gridRow: 1, gridColumn: 3}}><b>Email:</b> {!props.datos.personal ? null:props.datos.personal.email}</Typography>
            <Typography style = {{gridRow: 2, gridColumn: 3}}><b>Teléfono:</b> {!props.datos.personal ? null:props.datos.personal.tlf}</Typography>
            <Typography style = {{gridRow: 3, gridColumn: 3}}><b>NSS:</b> {!props.datos.personal ? null:props.datos.personal.nss}</Typography>

            <div style = {{gridRow: '1 / span 3', gridColumn: 4}} className = {classes.infoContrato}>
                <Typography><b>Nomina:</b> {!props.datos.personal ? null:props.datos.nomina}€</Typography>
                <Typography><b>Fecha Inicio:</b> {!props.datos.personal ? null:props.datos.fechaInicio}</Typography>
                <Typography><b>Fecha Fin:</b> {!props.datos.personal ? null:props.datos.fechaFin}</Typography >
                <Typography><b>Jornada:</b>  {!props.datos.personal ? null:props.datos.tipoJornada}</Typography>
            </div>
          </div>
          <div className = {classes.buttonDiv}>
            <EditDialogue handleReload={props.handleReload} edicion={!props.datos ? '':props.datos}/>
            <DeleteDialogue handleReload={props.handleReload} eliminacion={!props.datos ? '':props.datos.personal}/>
          </div>
      </div>
    );
}

import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import { makeStyles } from '@material-ui/core/styles';
import ConfirmDialog from './CorfirmDialoge';

import FormModificarPedido from './FormModificarPedido';
//MUI stuff
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import IconButton from '@material-ui/core/IconButton';
import PictureAsPdfIcon from '@material-ui/icons/PictureAsPdf';
import Button from '@material-ui/core/Button';
import Divider from '@material-ui/core/Divider';

const Accordion = withStyles({
  root: {
    margin: '0 30px',
    border: '1px solid #a9bfcc',
    boxShadow: 'none',
    '&:not(:last-child)': {
      borderBottom: 0,
    },
    '&:before': {
      display: 'none',
    },
    '&$expanded': {
      margin: '0 30px',
    },
  },
  expanded: {},
})(MuiAccordion);

const AccordionSummary = withStyles({
  root: {
    backgroundColor: '#d4e6f1',
    borderBottom: '1px solid #a9bfcc',
    marginBottom: -1,
    minHeight: 56,
    '&$expanded': {
      minHeight: 56,
    },
  },
  content: {
    '&$expanded': {
      margin: '12px 0',
    },
  },
  expanded: {},
})(MuiAccordionSummary);

const AccordionDetails = withStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}))(MuiAccordionDetails);

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    marginTop:13,
    flexBasis: '88%',
  },
  secondaryHeading: {
    fontSize: theme.typography.pxToRem(15),
    width:100,
    marginTop:13,
    margin:"0 2% 0 2%",
    fontWeight:"bold",
  },
  thirdHeading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight:"bold",
    
  },
  detailsDiv: {
    width:"100%"
  },
  detailsTitle:{
    fontSize: theme.typography.pxToRem(15),
    fontWeight:"bold",
    display: "inline-block",
    marginTop:"1%"

  },
  detailsSubTitle:{
    fontSize: theme.typography.pxToRem(15),
    fontWeight:"bold",
    display: "inline-block",
    marginLeft:"5%"

  },

  detailsList:{
    fontSize: theme.typography.pxToRem(15),
    display: "inline-block",
  },
  detailsInfo:{
    fontSize: theme.typography.pxToRem(15),
    display: "inline-block",
    marginRight:"5%"
  },
  detailsPrecio:{
    fontSize: theme.typography.pxToRem(15),
    display: "inline-block",
    textAlign:"right"
  },
  divButtons:{
    position:'absolute',
    bottom:"1%",
    right:".5%",
  },
  Buttons:{
    margin: 10
  }

}));

export default function ControlledAccordions(props) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);
  const [open, setOpen] = React.useState(false);
  const [openDialog, setOpenDialog] = React.useState(false);


  

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (value) => {
    setOpen(false);
  };

  const handleClickOpenDialog = (value) => {
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  let disabled = false;

  switch (props.datos.estado) {
    case "CANCELADO":
        disabled=true;
      break;

    case "PREPARADO":
        disabled=true;
      break;

    case "EN_REPARTO":
        disabled=true;
      break;

    case "ENTREGADO":
        disabled=true;
      break;

    case "EN_TIENDA":
        disabled=true;
      break;

    default:
        disabled = false;
      break;
  }

  return (
    <div className={classes.root}>
        <Accordion expanded={props.isExpanded} square={true} onChange={props.handleChange(props.isExpanded?null:props.datos.id)}>
            <AccordionSummary 
            className={classes.summary}
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1bh-content"
            id="panel1bh-header"
            >
            <Typography className={classes.heading}>{props.datos.id}</Typography>
            <Divider orientation="vertical" flexItem />
            <Typography className={classes.secondaryHeading}>{props.datos.estado}</Typography>
            <Divider orientation="vertical" flexItem />
            <IconButton className ={classes.thirdHeading} color="primary" aria-label="add to shopping cart">
              <PictureAsPdfIcon />
            </IconButton>
            </AccordionSummary>
            <AccordionDetails>
              <div className={classes.detailsDiv}>
                  <Typography className={classes.detailsTitle}>
                    Fecha:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.facturaEmitida.fechaEmision?props.datos.facturaEmitida.fechaEmision:<p>No Emitida</p>}
                    
                  </Typography>
                <br/>
                  <Typography className={classes.detailsTitle}>
                      Direccion:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.direccionEnvio}
                  </Typography>
                <br/>
                  <Typography className={classes.detailsTitle}>
                      Nombre:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.facturaEmitida.cliente.name}
                  </Typography>
                  <Typography className={classes.detailsTitle}>
                      NIF:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.facturaEmitida.cliente.nif}
                  </Typography>
                  <br/>
                  <br/>
                  <Typography className={classes.detailsTitle}>
                      Datos de contacto:     
                  </Typography>
                  <br/>
                  <Typography className={classes.detailsSubTitle}>
                      email:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.facturaEmitida.cliente.email}
                  </Typography>
                  <br/>
                  <Typography className={classes.detailsSubTitle}>
                      tlf:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.facturaEmitida.cliente.tlf}
                  </Typography>
                  <br/>
                  <br/>
                  <Typography className={classes.detailsTitle}>
                      Fecha de envío:     
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {props.datos.fechaEnvio}
                  </Typography>
                  <br/>
                  <Typography className={classes.detailsTitle}>
                      Productos:     
                  </Typography>
                  <table>
                  {props.datos.facturaEmitida.lineasFacturas.map((linea) => 
                    <tr>
                      <td>
                      <Typography className={classes.detailsList}>
                        {linea.cantidad}x
                      </Typography>
                      </td>
                      <td  width="450px">
                      <Typography className={classes.detailsInfo}>
                        {linea.producto.nombre}
                      </Typography>
                      </td>
                      <td>
                      <Typography className={classes.detailsInfo}>
                        {(linea.precio).toFixed(2)}
                      </Typography>
                      </td>
                    </tr>
                  )}
                  </table>
                  <Typography className={classes.detailsList}>
                      ==============================================================
                  </Typography>
                  <br/>
                  <Typography className={classes.detailsList}>
                    Importe total...........................................................................................
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {((props.datos.facturaEmitida.importe)/121*100).toFixed(2)}
                  </Typography>
                  <br/>
                  <Typography className={classes.detailsList}>
                    IVA(21%)................................................................................................
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                    {((props.datos.facturaEmitida.importe)/121*21).toFixed(2)}
                  </Typography>
                  <br/>
                  <Typography className={classes.detailsTitle}>
                    TOTAL
                  </Typography>
                  <Typography className={classes.detailsInfo}>
                  ...................................................................................................{(props.datos.facturaEmitida.importe).toFixed(2)}
                  </Typography>
              </div>
              {props.isExpanded?<div className = {classes.divButtons}>
              <Button className = {classes.Buttons} disabled={disabled} variant="contained" color="primary" onClick={handleClickOpenDialog}>
                Modificar Pedido
              </Button>
              <Button className = {classes.Buttons} variant="contained" disabled={disabled} color="primary" onClick={handleClickOpen}>
                Cancelar Pedido
              </Button>
              <ConfirmDialog open={open} elementID = {props.datos.id} onConfirm={props.onConfirmCancelar} onClose={handleClose} />
              </div>:null}
            </AccordionDetails>
        </Accordion>
        <FormModificarPedido open={openDialog} onClose={handleCloseDialog} pedido={props.datos} />
    </div>
  );
}

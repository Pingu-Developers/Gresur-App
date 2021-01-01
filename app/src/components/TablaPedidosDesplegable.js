import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import { makeStyles } from '@material-ui/core/styles';
import ConfirmDialog from './CorfirmDialoge';

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
    border: '1px solid rgba(0, 0, 0, .125)',
    boxShadow: 'none',
    '&:not(:last-child)': {
      borderBottom: 0,
    },
    '&:before': {
      display: 'none',
    },
    '&$expanded': {
      margin: 'auto',
    },
  },
  expanded: {},
})(MuiAccordion);

const AccordionSummary = withStyles({
  root: {
    height:56,
    backgroundColor: 'rgba(197, 236, 255, .3)',
    borderBottom: '1px solid rgba(0, 0, 0, .125)',
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
    backgroundColor: 'rgba(240, 240, 240, .3)',
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


  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (value) => {
    setOpen(false);
  };

  return (
    <div className={classes.root}>
        <Accordion expanded={expanded === 'panel1'} square={true} onChange={handleChange('panel1')}>
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
                        {(linea.precio*linea.cantidad).toFixed(2)}
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
              <div className = {classes.divButtons}>
              <Button className = {classes.Buttons} variant="contained" color="primary">
                Modificar Pedido
              </Button>
              <Button className = {classes.Buttons} variant="contained" color="primary" onClick={handleClickOpen}>
                Cancelar Pedido
              </Button>
              <ConfirmDialog open={open} elementID = {props.datos.id} onConfirm={props.deletePedidos} onClose={handleClose} />
              </div>
            </AccordionDetails>
        </Accordion>
    </div>
  );
}
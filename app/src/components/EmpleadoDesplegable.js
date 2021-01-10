import React from 'react';
import ConfirmDialog from './CorfirmDialoge';

//MATERIAL UI Stuff
import { withStyles } from '@material-ui/core/styles';
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Divider from '@material-ui/core/Divider';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import DeleteDialogue from './DeleteDialogue';
import EditDialogue from './EditDialogue';

const useStyles = makeStyles((theme) => ({

  atributosLeft: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    flexShrink: 0,
  },
  atributosRight: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    fontWeight:"bold",  
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight:"bold",
    },
  alineacion: {
    display: 'inline-block',
    marginBlockEnd:'10px',
    },
  alineacionAtributos: {
    display: 'inline-block',
    fontWeight:"bold",
    marginRight:'2px',
    marginTop:theme.spacing(1.5),
    marginLeft:'20px'

    },
  imagen:{
    marginRight:theme.spacing(2)
  },
  divider:{
    width: '2px',
    ...theme.typography.body2,
    '& [role="separator"]': {
      margin: theme.spacing(0, 2),
    },
    contrato: {
      flexGrow: 1,
    },
    paper: {
      maxWidth: 800,
      margin: `${theme.spacing(1)} auto`,
      padding: theme.spacing(2),
    },funacion:{
      margin: `${theme.spacing(1)} auto`,
    }
  }
  
  
}));

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
      margin: 'right',
    },
  },
  expanded: {},
})(MuiAccordion);

const AccordionSummary = withStyles({
  root: {
    backgroundColor: '#f7d1a6',
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
    padding: theme.spacing(2),
  },
}))(MuiAccordionDetails);

export default function CustomizedAccordions(props) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);
  const [open, setOpen] = React.useState(false);
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = (value) => {
    setOpen(false);
  };
  const handleChange = (panel) => (event, newExpanded) => {
    setExpanded(newExpanded ? panel : false);
  };

  return (
      
      <Accordion square expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
        <AccordionSummary aria-controls="panel1d-content" id="panel1d-header">
          <Typography className={classes.heading}>{props.datos.personal === undefined?null:props.datos.personal.name}</Typography>
        </AccordionSummary>
        <AccordionDetails>
                <div className={classes.imagen}>
                <img src={props.datos.personal === undefined?null:props.datos.personal.image} width="150" height= "150"/>
                </div>
                <div className={classes.atributosLeft}>
                    <Typography className={classes.alineacionAtributos}>
                        Nombre:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.personal.name}
                    </Typography >
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        DNI:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.personal.nif}
                    </Typography>
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        Dirección:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.personal.direccion}
                    </Typography>
                    <br/>
                    {props.datos.personal.almacen === undefined?null:
                      <React.Fragment>
                      <Typography className={classes.alineacionAtributos}>
                        Almacén:
                      </Typography>
                      <Typography  className={classes.alineacion}>
                      {props.datos.personal ===undefined?null:props.datos.personal.almacen.direccion}
                      </Typography>
                     
                      </React.Fragment>
                    }
                </div>
                <div className={classes.atributosRight}>
                    <Typography className={classes.alineacionAtributos}>
                        Email: 
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.personal.email}
                    </Typography>
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        Teléfono:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.personal.tlf}
                    </Typography>
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        NSS:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.personal.nss}
                    </Typography>
                    <br/>                   
                    <br/>
                    <DeleteDialogue className={classes.funacion} eliminacion={props.datos===undefined?'':props.datos.personal}/>
                    <br/>
                    <EditDialogue className={classes.funacion} edicion={props.datos===undefined?'':props.datos}/>

                </div>
                <Divider orientation="vertical" flexItem className={classes.divider}/>
                <Paper className={classes.paper}>
                   <Grid container spacing={2} className={classes.alineacionAtributos}>
                   <Grid item xs>
                     <div className={classes.atributosRight}>
                     <Typography className={classes.alineacionAtributos}>
                        Nomina:
                    </Typography>
                    <Typography className={classes.alineacion} >
                        {props.datos.personal === undefined?null:props.datos.nomina}€
                    </Typography >

                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        Fecha Inicio:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.fechaInicio}
                    </Typography>

                    <br/>

                    <Typography className={classes.alineacionAtributos}>
                        Fecha Fin:
                    </Typography >
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.fechaFin}
                    </Typography>

                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        Jornada: 
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.personal === undefined?null:props.datos.tipoJornada}
                    </Typography>
                     </div>  
                     </Grid>
                  </Grid>
                </Paper>
            
        </AccordionDetails>
      </Accordion>
  );
}

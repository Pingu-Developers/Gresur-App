import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';

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
  root: {
    width: '100%',
    width: '100vw'
    },
  alineacion: {
    display: 'inline-block',
    marginBlockEnd:'10px',
    },
  alineacionAtributos: {
    display: 'inline-block',
    fontWeight:"bold",
    marginRight:'2px'
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


  const handleChange = (panel) => (event, newExpanded) => {
    setExpanded(newExpanded ? panel : false);
  };

  return (
<div className={classes.root}>
      
      <Accordion square expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
        <AccordionSummary aria-controls="panel1d-content" id="panel1d-header">
          <Typography className={classes.heading}>{props.datos.name}</Typography>
        </AccordionSummary>
        <AccordionDetails>
                <img src={props.datos.image} width="150" height= "150"/>
                <div className={classes.atributosLeft}>
                    <Typography className={classes.alineacionAtributos}>
                        Nombre:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.name}
                    </Typography >
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        DNI:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.nif}
                    </Typography>
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        Dirección:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.direccion}
                    </Typography>
                </div>
                <div className={classes.atributosRight}>
                    <Typography className={classes.alineacionAtributos}>
                        Email: 
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.email}
                    </Typography>
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        Teléfono:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.tlf}
                    </Typography>
                    <br/>
                    <Typography className={classes.alineacionAtributos}>
                        NSS:
                    </Typography>
                    <Typography className={classes.alineacion}>
                        {props.datos.nss}
                    </Typography>
                </div>
        </AccordionDetails>
      </Accordion>
    </div>
  );
}

import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Accordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MostradorProductos from './MostradorProductos';
import { findDOMNode } from 'react-dom';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    flexShrink: 0,
  },

  acordeon: {
    backgroundColor: '#d4e6f1',
  },

  lineasWrapper: {
    width: '100%'
  },

  elementos:{
    maxHeight: '90vh',
    overflowY: 'auto'
  }

}));

export default function ControlledAccordions(props) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(props.num === 0 ? true:false);

  const handleChange = () => (event, isExpanded) => { 
    setExpanded(isExpanded ? true : false);
    console.log(props.children)
  };

  const categoria = props.categoria;

  return (
    <div className={classes.root}>
        <Accordion expanded={expanded} onChange={handleChange()}>
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1bh-content"
            id="panel1bh-header"
            className= {classes.acordeon}
            >
            <Typography className={classes.heading}><b>{categoria}</b></Typography>
            </AccordionSummary>                
                <AccordionDetails className={classes.elementos}>
                <div className={classes.lineasWrapper}>
                  {props.productos.map((producto) => 
                      producto.estanteria.categoria===categoria? <MostradorProductos producto={producto}/> : null
                  )}
                </div>
              </AccordionDetails>              
            

        </Accordion>
    </div>
  );
}
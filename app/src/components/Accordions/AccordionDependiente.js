import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import EmpleadoDesplegable from './EmpleadoDesplegable';
import Accordion from '@material-ui/core/Accordion';


const useStyles = makeStyles((theme) => ({
  heading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    flexShrink: 0,
    fontWeight:"bold",

  },
  details:{
    display:'inline-block',
    width:'92%',
},
  acordeon: {
    backgroundColor:"#d4e6f1",
},
root: {
  width: '94%',
  marginLeft: '3%',
}
}));

export default function ControlledAccordions(props) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };
  return (
    <div className={classes.root}>
        <Accordion expanded={expanded === 'panel2'} onChange={handleChange('panel2')}>
            <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel2bh-content"
                id="panel2bh-header"
                className={classes.acordeon}>
                <Typography className={classes.heading}>Dependientes</Typography>
            </AccordionSummary>
            <AccordionDetails className={classes.details}>
                 {props.datos===undefined?null:props.datos.map((desplegable)=>
                    <EmpleadoDesplegable datos = {desplegable}/>
                )} 
            </AccordionDetails>
        </Accordion>
    </div>
  );
}
 

 
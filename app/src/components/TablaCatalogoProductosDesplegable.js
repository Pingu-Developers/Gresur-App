import React from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import MuiAccordion from '@material-ui/core/Accordion';
import MuiAccordionDetails from '@material-ui/core/AccordionDetails';
import MuiAccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import MostradorProductos from './MostradorProductos';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    flexShrink: 0,
  },
}));

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

export default function ControlledAccordions(props) {
  const classes = useStyles();
  const datos = props.data.productos;
  const [expanded, setExpanded] = React.useState(0);

  const handleChange = (panel) => (event, isExpanded) => { 
    setExpanded(isExpanded ? panel : false);
  };

  return (
    <div className={classes.root}>
      {
        Object.entries(datos).map((row) => 
          <Accordion 
              square
              className = {classes.acordeon}
              expanded={expanded === row[0]} 
              onChange={handleChange(row[0])}>
              <AccordionSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1d-content"
                id="panel1d-header"
                className= {classes.acordeonSum}
                >
                <Typography className={classes.heading}><b>{row[0]}</b></Typography>
              </AccordionSummary>

              <AccordionDetails className = {classes.elementos}>
                <div>
                    {row[1].map((producto) => <MostradorProductos producto={producto}/>)}
                </div>
              </AccordionDetails>             
          </Accordion>
        )
      }
        
    </div>
  );
}
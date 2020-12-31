import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Accordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    flexShrink: 0,
  },
  secondaryHeading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    fontWeight:"bold",
    
  },
  thirdHeading: {
    fontSize: theme.typography.pxToRem(15),
    flexBasis: '33.33%',
    fontWeight:"bold",
    
  },
}));

export default function ControlledAccordions(props) {
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);

  const handleChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  const categoria = props.categoria;

  return (
    <div className={classes.root}>
        <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1bh-content"
            id="panel1bh-header"
            >
            <Typography className={classes.heading}>{categoria}</Typography>
            </AccordionSummary>                
                <AccordionDetails>
                <div>
                  {props.productos.map((producto) => 
                    <Typography>
                      {producto.estanteria.categoria===categoria? producto.nombre : null}

                    </Typography>
                  )}
                </div>
              </AccordionDetails>              
            

        </Accordion>
    </div>
  );
}



/*
<Typography className={classes.thirdHeading}>
                      {producto.estanteria.categoria}
                  </Typography>
                  */
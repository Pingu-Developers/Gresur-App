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

  return (
    <div className={classes.root}>
        <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1bh-content"
            id="panel1bh-header"
            >
            <Typography className={classes.heading}>{props.datos.id}</Typography>
            <Typography className={classes.secondaryHeading}>{props.datos.estado}</Typography>
            <Typography className={classes.thirdHeading} align= 'right'>PDF</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <div>
                <Typography className={classes.thirdHeading}>
                  Fecha:     
                </Typography>

                <Typography>
                  {props.datos.fechaEnvio}
                </Typography>
                  <br/>
                <Typography className={classes.thirdHeading}>
                    Direccion:     
                </Typography>

                <Typography>
                  {props.datos.direccionEnvio}
                </Typography>
              </div>
            </AccordionDetails>
        </Accordion>
    </div>
  );
}

/*
props.datos? null :  props.datos.map((row) => {
        
        <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1bh-content"
            id="panel1bh-header"
            >
            <Typography className={classes.heading}>{row.id}</Typography>
            <Typography className={classes.secondaryHeading}>I am an accordion</Typography>
            </AccordionSummary>
            <AccordionDetails>
            <Typography>
                Nulla facilisi. Phasellus sollicitudin nulla et quam mattis feugiat. Aliquam eget
                maximus est, id dignissim quam.
            </Typography>
            </AccordionDetails>
        </Accordion>
})
*/ 

/*{
      props.datos[0].pedidos.map((row) => {
        {console.log(row)}
        <p>hola</p>
      })
      }
    */
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AccordionTransportista from './AccordionTransportista'
import AccordionDependiente from './AccordionDependiente'
import AccordionEncargado from './AccordionEncargado'
import AccordionAdministrador from './AccordionAdministrador'


const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
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
      <AccordionTransportista datos = {props.datos.transportista===null ? props.datos.transportista : null}/>
      <AccordionDependiente datos = {props.datos.dependiente===null ? props.datos.dependiente : null}/>
      <AccordionEncargado datos = {props.datos.encargadodealmacen===null ? props.datos.encargadodealmacen : null}/>
      <AccordionAdministrador datos = {props.datos.administrador===null ? props.datos.administrador : null}/>
    </div>
  );
}
 

import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AccordionTransportista from './AccordionTransportista'
import AccordionDependiente from './AccordionDependiente'
import AccordionEncargado from './AccordionEncargado'
import AccordionAdministrador from './AccordionAdministrador'

export default function ControlledAccordions(props) {
  const [expanded, setExpanded] = React.useState(false);

  return (
    <div>
      <AccordionTransportista datos = {props.datos.transportista}/>
      <AccordionDependiente  datos = {props.datos.dependiente}/>
      <AccordionEncargado datos = {props.datos.encargadodealmacen}/>
      <AccordionAdministrador  datos = {props.datos.administrador}/>
    </div>
  );
}
 

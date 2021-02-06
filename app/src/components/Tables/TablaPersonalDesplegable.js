import React from 'react';
import AccordionTransportista from '../Accordions/AccordionTransportista'
import AccordionDependiente from '../Accordions/AccordionDependiente'
import AccordionEncargado from '../Accordions/AccordionEncargado'
import AccordionAdministrador from '../Accordions/AccordionAdministrador'

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
 

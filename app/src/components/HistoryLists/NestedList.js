import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import Button from '@material-ui/core/Button';
import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';


const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    backgroundColor: '#d4e6f1',
    padding: 0,
  },
  nested: {
    paddingLeft: 5,
    backgroundColor: 'white',
  },
  listItem: {
      height: 40,
      backgroundColor: '#d4e6f1',
  },
  divider: {
      width: '95%'
  },
  nombre: {
      display: 'flex',
      alignItems: 'center',
      paddingLeft: 20,
      width: '100%'
  },
  button: {
      height: 50,
      width: 50,
      borderRadius: '100%',
      color: 'white'
  },
  botonContainer: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
  }
}));

export default function NestedList(props) {
  const classes = useStyles();
  const [open, setOpen] = React.useState('REVESTIMIENTOS');

  const handleClick = (event, key) => {
    event.preventDefault();
    setOpen(open === key ? null : key);
  };

  const parseUnidades = (unidad) => {
      switch(unidad){
            case 'M2':
                return <span> m<sup>2</sup></span>
            case 'UNIDADES':
                return ' Unidad';
            case 'SACOS':
                return ' Saco';
            case 'KG':
                return ' Kg';
            case 'LATAS':
                return ' Lata';
            default:
                return unidad;
      }
  }

  return (
    <List
      component="nav"
      aria-labelledby="nested-list-subheader"
      className={classes.root}
    >   
        {props.content ? Object.entries(props.content).map( (entry) => (
            <div>
                <ListItem 
                    divider 
                    button 
                    onClick={(e) => {handleClick(e, entry[0])}} 
                    className = {classes.listItem}
                    >
                    <ListItemText primary={<b>{entry[0]}</b>}/>
                    {open !== entry[0] ? <ExpandLess/> : <ExpandMore />}
                </ListItem>

                {entry[1].map( (value) =>
                    <Collapse in={open === entry[0]} timeout="auto" unmountOnExit>
                        <List component="div" disablePadding>
                        <ListItem 
                            divider 
                            className={classes.nested}
                            >
                            <ListItemText 
                                primary={
                                        <span 
                                            id = {value.id.toString() + ',' + value.stock.toString() + ',' + value.precioVenta.toString()} 
                                            style={{display: 'grid', gridTemplateColumns : '0.5fr 1.2fr 1fr 0.5fr', justifyItems: 'center'}}
                                            >
                                            <img style={{gridColumn: '1', gridRow: '1', height: 100, width: 100}} src = {value.urlimagen}></img>
                                            <p style={{gridColumn: '2', gridRow: '1'}} className={classes.nombre}>{value.nombre}</p>
                                            <span style={{gridColumn: '3', gridRow: '1'}} className={classes.precio}>
                                                <p>Precio Unit: <b>{value.precioVenta} € /{parseUnidades(value.unidad)}</b></p>
                                                <p>En stock: <b>{value.stock}</b></p>
                                            </span>
                                            <span style={{gridColumn: '4', gridRow: '1'}} className={classes.botonContainer}>
                                                <Button
                                                    id = {value.id}
                                                    variant="contained"
                                                    color="primary"
                                                    onClick={props.handleTransfer}
                                                    classes={{root : classes.button, disabled: classes.disabled}}
                                                    disabled={value.stock <= 0}                                 
                                                    >
                                                    <ArrowForwardIosIcon />
                                                </Button>
                                            </span>
                                        </span>
                                        }/>
                        </ListItem>
                        </List>
                    </Collapse>
                )}
            </div>
        )) : null}
    </List>
  );
}
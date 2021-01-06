import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';


const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
    }, 

    main: {
        display: 'grid', 
        gridTemplateRows: '1fr 1fr',
        gridTemplateColumns: '0.3fr 1fr 1fr 1fr',
        width: '100%'
    }, 

    imagen:{
        width: 150,
        height: 150,
        alignItems: 'center',
        gridColumn: '1',
        gridRow: '1'
    },

    infoGeneral:{
        fontWeight: 'bold',
        float:'right',
    }, 

    


    

}));

export default function MostradorProductos(props) {
    const classes = useStyles();
    const datos = props.datos;

    return (
        <div className={classes.main}>
            <img className={classes.imagen} src={datos.vehiculo.imagen} alt='IMAGEN' />
            <p className={classes.infoGeneral}>INFORMACION GENERAL</p>
        </div>
    )
}
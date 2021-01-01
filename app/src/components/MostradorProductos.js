import React from 'react';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
      width: '100%',
    }, 

    lineaProducto: {
        display:'inline-block',
        padding: 5,
        borderBottom: '1px solid grey',
        width: '100%',
    },

    foto: {
        width: 120,
        height: 120,
        float: 'left',
        marginTop: 9
    },

    nombreDescripcion: {
        width: 801,
        height: '100%',
        float: 'left',
        marginTop: -5,
        marginLeft: 15,
    }, 

    nombre: {
        fontWeight: 700,
    },

    descripcion: {
        textAlign: 'justify',
        height: '100%',
    }, 

    stockDimensionesUnidad: {
        float: 'left',
        width: '30%',
        paddingLeft: 20,
        borderLeft: '1px solid #808080',
    },

    precio: {
        float: 'right',
        marginRight: 70
    }, 
    

}));

export default function MostradorProductos(props) {
    const classes = useStyles();
    const producto = props.producto;

    return (
        <div container alignItems="center" className={classes.lineaProducto}>
            <img className={classes.foto} src={producto.urlimagen} alt='IMAGEN' />
                
            <div className={classes.nombreDescripcion}>
                <p className={classes.nombre}>{producto.nombre}:</p> 
                <p className={classes.descripcion}>{producto.descripcion}</p>
            </div>


            <div className={classes.stockDimensionesUnidad}>
                <p className={classes.stock}><b>Stock:</b> {producto.stock}</p>
                <p className={classes.dimensiones}><b>Dimensiones(HxWxD):</b> {producto.alto}x{producto.ancho}x{producto.profundo}</p>
                <p className={classes.unidad}><b>Unidad:</b> {producto.unidad}</p>
            </div>


            <div className= {classes.precio}>
                <p className={classes.pvpTexto}><h2>PVP</h2></p>
                <p className={classes.pvp}><h1><b>{producto.precioVenta}€ </b></h1></p>
            </div>
        </div>
    )
} 
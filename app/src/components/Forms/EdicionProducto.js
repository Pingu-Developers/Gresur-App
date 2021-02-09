import React from 'react';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            width: '25ch',
        },
    },
}));

export default function FormPropsTextFields(props) {
    const classes = useStyles();
    const producto = props.prod;

    return (
        <form className={classes.root} noValidate autoComplete="off">
            <div>

                <TextField
                    required
                    id="standard-required"
                    label="Nombre producto"
                    defaultValue={producto.nombre}
                />
                <TextField
                    required
                    id="standard-required"
                    label="Descripcion"
                    defaultValue={producto.descripcion} />
                <TextField
                    id="standard-number"
                    label="Stock"
                    type="number"
                    defaultValue={producto.stock}
                />
                <TextField
                    id="standard-number"
                    label="Stock de Seguridad"
                    type="number"
                    defaultValue={producto.stockSeguridad}
                />
                <TextField
                    id="standard-required"
                    label="Ancho (m)"
                    type="number"
                    defaultValue={producto.ancho}
                />
                <TextField
                    id="standard-required"
                    label="Alto (m)"
                    type="number"
                    defaultValue={producto.alto}
                />
                <TextField
                    id="standard-required"
                    label="Profundo (m)"
                    type="number"
                    defaultValue={producto.profundo}
                />
                <TextField
                    id="standard-required"
                    label="URL imagen"
                    defaultValue={producto.urlimagen}
                />
                <TextField
                    id="standard-required"
                    label="Precio venta público (€)"
                    type="number"
                    defaultValue={producto.precioVenta}
                />
                <TextField
                    id="standard-required"
                    label="Precio compra (€)"
                    type="number"
                    defaultValue={producto.precioCompra}
                />
            </div>
        </form>
    );
}
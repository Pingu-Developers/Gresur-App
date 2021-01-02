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

    return (
        <form className={classes.root} noValidate autoComplete="off">
            <div>

                <TextField
                    required
                    id="standard-required"
                    label="Nombre producto"
                    defaultValue={props.prod.nombre}
                />
                <TextField
                    required
                    id="standard-required"
                    label="Descripcion"
                    defaultValue={props.prod.descripcion} />
                <TextField
                    id="standard-number"
                    label="Stock"
                    type="number"
                    defaultValue={props.prod.stock}
                />
                <TextField
                    id="standard-number"
                    label="Stock de Seguridad"
                    type="number"
                    defaultValue={props.prod.stockSeguridad}
                />
                <TextField
                    id="standard-required"
                    label="Ancho (m)"
                    type="number"
                    defaultValue={props.prod.ancho}
                />
                <TextField
                    id="standard-required"
                    label="Alto (m)"
                    type="number"
                    defaultValue={props.prod.alto}
                />
                <TextField
                    id="standard-required"
                    label="Profundo (m)"
                    type="number"
                    defaultValue={props.prod.profundo}
                />
                <TextField
                    id="standard-required"
                    label="URL imagen"
                    defaultValue={props.prod.urlimagen}
                />
                <TextField
                    id="standard-required"
                    label="Precio venta público (€)"
                    type="number"
                    defaultValue={props.prod.precioVenta}
                />
                <TextField
                    id="standard-required"
                    label="Precio compra (€)"
                    type="number"
                    defaultValue={props.prod.precioCompra}
                />
            </div>
        </form>
    );
}
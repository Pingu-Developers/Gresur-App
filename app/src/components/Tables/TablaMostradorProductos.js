import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

/*function createData(name, calories, fat, carbs, protein) {
    return { name, calories, fat, carbs, protein };
}*/

/*const rows = [
    createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
    createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
    createData('Eclair', 262, 16.0, 24, 6.0),
    createData('Cupcake', 305, 3.7, 67, 4.3),
    createData('Gingerbread', 356, 16.0, 49, 3.9),
];*/

export default function TablaMostradorProductos(props) {
    const classes = useStyles();
    const producto = props.producto;

    return (
        <TableContainer component={Paper}>
            <Table className={classes.table} size="small" aria-label="a dense table">
                <TableHead>
                    <TableRow>
                        <TableCell><b>Producto</b></TableCell>
                        <TableCell align="right"><b>Dimensiones (H x W x D)</b></TableCell>
                        <TableCell align="right"><b>Precio de compra</b></TableCell>
                        <TableCell align="right"><b>Precio de venta</b></TableCell>
                        <TableCell align="right"><b>Beneficio por venta</b></TableCell>
                        <TableCell align="right"><b>Demanda</b></TableCell>
                        <TableCell align="right"><b>En stock</b></TableCell>
                        <TableCell align="right"><b>Unidad</b></TableCell>
                        <TableCell align="right"><b>X</b></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>

                    <TableRow>
                        <TableCell>{producto.nombre}</TableCell>
                        <TableCell align="right">{producto.alto}x{producto.ancho}x{producto.profundo}</TableCell>
                        <TableCell align="right">Precio de compra</TableCell>
                        <TableCell align="right">{producto.precioVenta}€</TableCell>
                        <TableCell align="right"></TableCell>
                        <TableCell align="right">//TODO</TableCell>
                        <TableCell align="right">{producto.stock}</TableCell>
                        <TableCell align="right">{producto.unidad}</TableCell>
                        <TableCell align="right">X</TableCell>

                    </TableRow>

                </TableBody>
            </Table>
        </TableContainer>
    );
}

import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Accordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import TablaMostradorProductos from '../components/TablaMostradorProductos';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        flexBasis: '33.33%',
        flexShrink: 0,
    },

    acordeon: {
        backgroundColor: '#d4e6f1',
    }

}));

export default function ControlledAccordions(props) {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };

    const categoria = props.categoria;
    return (
        <div className={classes.root}>
            <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon />}
                    aria-controls="panel1bh-content"
                    id="panel1bh-header"
                    className={classes.acordeon}
                >
                    <Typography className={classes.heading}><b>{categoria}</b></Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <TableContainer component={Paper}>
                        <Table className={classes.table} size="small" aria-label="a dense table">
                            <TableBody>
                                <TableRow>
                                    <TableCell align="left"><b>Producto</b></TableCell>
                                    <TableCell align="left"><b>Dimensiones (H x W x D)</b></TableCell>
                                    <TableCell align="left"><b>Precio de compra</b></TableCell>
                                    <TableCell align="left"><b>Precio de venta</b></TableCell>
                                    <TableCell align="left"><b>Beneficio por venta</b></TableCell>
                                    <TableCell align="left"><b>Demanda</b></TableCell>
                                    <TableCell align="left"><b>En stock</b></TableCell>
                                    <TableCell align="left"><b>Unidad</b></TableCell>
                                    <TableCell align="left"><b>X</b></TableCell>
                                </TableRow>



                                <div>
                                    {props.productos.map((producto) =>
                                        producto.estanteria.categoria === categoria ? (
                                            <TableRow>
                                                <TableCell>{producto.nombre}</TableCell>
                                                <TableCell>{producto.alto}x{producto.ancho}x{producto.profundo}</TableCell>
                                                <TableCell>{producto.precioCompra}€</TableCell>
                                                <TableCell>{producto.precioVenta}€</TableCell>
                                                <TableCell></TableCell>
                                                <TableCell>{(parseFloat(producto.precioVenta) - parseFloat(producto.precioCompra)).toFixed(2)}€</TableCell>
                                                <TableCell>demanda</TableCell>
                                                <TableCell>{producto.stock}</TableCell>
                                                <TableCell>{producto.unidad}</TableCell>
                                                <TableCell>X</TableCell>
                                            </TableRow>
                                        ) : null
                                    )}
                                </div>

                            </TableBody>
                        </Table>
                    </TableContainer>
                </AccordionDetails>


            </Accordion>
        </div>
    );
}
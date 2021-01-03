import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Accordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import WarningIcon from '@material-ui/icons/Warning';
import ErrorIcon from '@material-ui/icons/Error';
import EditIcon from '@material-ui/icons/Edit';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import EdicionProducto from '../components/EdicionProducto';
import AmpliarInfoProducto from '../components/AmpliarInfoProducto.js';
import Tooltip from '@material-ui/core/Tooltip';
import MostradorProductos from './MostradorProductos';
import TextField from '@material-ui/core/TextField';
import setProducto from '../redux/actions/dataActions';

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
function alertaStockProducto(producto) {
    var stock = parseInt(producto.stock, 10);
    var stockSeguridad = parseInt(producto.stockSeguridad, 10);
    if (stock < stockSeguridad) {
        return <WarningIcon color="primary" />;
    }
}

export default function ControlledAccordions(props) {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };
    

    const categorias = props.data.categorias
    const productos = props.data.productos

    //Funciones Vista Editar Producto
    const [open, setOpen] = React.useState(false);
    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
   

    return (
        <div className={classes.root}>
            {
                categorias.map((row) =>
                    <Accordion
                        square
                        className={classes.acordeon}
                        expanded={expanded === categorias.indexOf(row)}
                        onChange={handleChange(categorias.indexOf(row))}>
                        <AccordionSummary
                            expandIcon={<ExpandMoreIcon />}
                            aria-controls="panel1bh-content"
                            id="panel1bh-header"
                            className={classes.acordeon}
                        >
                            <Typography className={classes.heading}><b>{row}</b></Typography>
                        </AccordionSummary>
                        <AccordionDetails>
                            <TableContainer component={Paper}>
                                <Table className={classes.table} size="small" aria-label="a dense table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell></TableCell>
                                            <TableCell><b>Producto</b></TableCell>
                                            <TableCell></TableCell>
                                            <TableCell><b>Dimensiones (H x W x D)</b></TableCell>
                                            <TableCell><b>Precio de compra</b></TableCell>
                                            <TableCell><b>Precio de venta</b></TableCell>
                                            <TableCell><b>Beneficio por venta</b></TableCell>
                                            <TableCell><b>Demanda</b></TableCell>
                                            <TableCell><b>En stock</b></TableCell>
                                            <TableCell><b>Unidad</b></TableCell>
                                            <TableCell></TableCell>
                                        </TableRow>
                                    </TableHead>

                                    <TableBody>
                                        {productos.map((producto) =>
                                            producto.estanteria.categoria === row ? (
                                                <TableRow>
                                                    <TableCell><Button onClick={handleClickOpen}><EditIcon /></Button></TableCell>
                                                    <Dialog
                                                        open={open}
                                                        onClose={handleClose}
                                                        aria-labelledby="alert-dialog-title"
                                                        aria-describedby="alert-dialog-description"
                                                    >
                                                        <DialogTitle id="alert-dialog-title">{"Editar producto"}</DialogTitle>
                                                        <DialogContent>

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
                                                        </DialogContent>
                                                        <DialogActions>
                                                            
                                                            <Button type="submit" onclick={handleClose} color="primary">
                                                                Guardar Cambios
                                                </Button>
                                                            <Button onClick={handleClose} color="primary" autoFocus>
                                                                Descartar
                                                </Button>
                                                        </DialogActions>
                                                    </Dialog>

                                                    <Tooltip title={producto.descripcion}
                                                        placement="right">
                                                        <TableCell>{producto.nombre}</TableCell>
                                                    </Tooltip>

                                                    <TableCell>{alertaStockProducto(producto)}</TableCell>
                                                    <TableCell>{producto.alto} x {producto.ancho} x {producto.profundo}</TableCell>
                                                    <TableCell>{producto.precioCompra}€</TableCell>
                                                    <TableCell>{producto.precioVenta}€</TableCell>
                                                    <TableCell>{(parseFloat(producto.precioVenta) - parseFloat(producto.precioCompra)).toFixed(2)}€</TableCell>
                                                    <TableCell>demanda</TableCell>
                                                    <TableCell>{producto.stock}</TableCell>
                                                    <TableCell>{producto.unidad}</TableCell>
                                                    <TableCell></TableCell>
                                                </TableRow>
                                            ) : null
                                        )}

                                    </TableBody>

                                </Table>
                            </TableContainer>
                        </AccordionDetails>


                    </Accordion>
                )}

        </div>
    );
}
import React from 'react';
import { withStyles, makeStyles } from '@material-ui/core/styles';
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
import Button from '@material-ui/core/Button';
import Tooltip from '@material-ui/core/Tooltip';
import DialogoEditarProductos from '../components/DialogoEditarProducto';
import ErrorIcon from '@material-ui/icons/Error';

//Acordeon
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

//margen define el margen de capacidad para que salte la alerta [0, 1]
function getBotonNotificarAlertaStock(row, producto, margen){
    var sumVolumenes = getVolumenEstanteria(row);
    var capacidadEst = getCapacidadEstanteria(producto);
    if(sumVolumenes < (capacidadEst*margen)){
        return  <Tooltip title="Avisar al administrador para reponer stock" arrow><Button disabled><ErrorIcon/></Button></Tooltip>
    } else{
        return <Tooltip title="Avisar al administrador para reponer stock" arrow><Button><ErrorIcon/></Button></Tooltip>
    }

}
//obtiene la capacidad de la estanteria
function getCapacidadEstanteria(producto){
    return producto.estanteria.capacidad
}

//suma el volumen de todos los productos de una estanteria
function getVolumenEstanteria(row){
    var acum = 0;
    row.map((producto) => acum = acum + (producto.alto * producto.ancho * producto.profundo * producto.stock));
    return acum;
}

//cuando el stock es menor que el de seguridad, aparecerá un icono de warning
function alertaStockProducto(producto) {
    var stock = parseInt(producto.stock, 10);
    var stockSeguridad = parseInt(producto.stockSeguridad, 10);
    if (stock < stockSeguridad) {
        return <WarningIcon color="primary" />;
    }
}

//Tooltip
const HtmlTooltip = withStyles((theme) => ({
    tooltip: {
        backgroundColor: '#f5f5f9',
        color: 'rgba(0, 0, 0, 0.87)',
        maxWidth: 220,
        fontSize: theme.typography.pxToRem(12),
        border: '1px solid #dadde9',
    },
}))(Tooltip);

const useStylesBootstrap = makeStyles((theme) => ({
    arrow: {
        color: theme.palette.common.black,
    },
    tooltip: {
        backgroundColor: theme.palette.common.black,
    },
}));
function BootstrapTooltip(props) {
    const classes = useStylesBootstrap();

    return <Tooltip arrow classes={classes} {...props} />;
}

export default function ControlledAccordions(props) {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);

    const handleChange = (panel) => (event, isExpanded) => {
        setExpanded(isExpanded ? panel : false);
    };


    const datos = props.data.productos

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
                Object.entries(datos).map((row) =>
                    <Accordion
                        square
                        className={classes.acordeon}
                        expanded={expanded === row[0]}
                        onChange={handleChange(row[0])}>
                        <AccordionSummary
                            expandIcon={<ExpandMoreIcon />}
                            aria-controls="panel1bh-content"
                            id="panel1bh-header"
                            className={classes.acordeon}
                        >
                            <Typography className={classes.heading}><b>{row[0]}</b></Typography>
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
                                        {row[1].map((producto) =>
                                                <TableRow>

                                                    <DialogoEditarProductos producto={producto} />
                                                    
                                                    <HtmlTooltip
                                                        title={
                                                            <React.Fragment>
                                                                <Typography color="inherit">{producto.nombre}</Typography>
                                                                <table>
                                                                    <tr>
                                                                        <th>
                                                                            <img width="120px" src={producto.urlimagen}></img>
                                                                        </th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>
                                                                        {producto.descripcion}
                                                                        </th>
                                                                    </tr>
                                                                </table>
                                                            </React.Fragment>
                                                        }>
                                                        <TableCell> {producto.nombre} </TableCell>
                                                    </HtmlTooltip>

                                                    <TableCell>{alertaStockProducto(producto)}</TableCell>
                                                    <TableCell>{producto.alto} x {producto.ancho} x {producto.profundo}</TableCell>
                                                    <TableCell>{producto.precioCompra}€</TableCell>
                                                    <TableCell>{producto.precioVenta}€</TableCell>
                                                    <TableCell>{(parseFloat(producto.precioVenta) - parseFloat(producto.precioCompra)).toFixed(2)}€</TableCell>
                                                    <TableCell>demanda</TableCell>
                                                    <TableCell>{producto.stock}</TableCell>
                                                    <TableCell>{producto.unidad}</TableCell>
                                                    <TableCell>{ getBotonNotificarAlertaStock(row[1], producto, 0.85)}</TableCell>
                                                </TableRow>
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
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import WarningIcon from '@material-ui/icons/Warning';
import CircularProgress from '@material-ui/core/CircularProgress';
import Backdrop from '@material-ui/core/Backdrop';
import Pagination from '@material-ui/lab/Pagination';


//Components
import HistorialPedidoBox from './HistorialPedidoBox';
import { loadPedidosPaginados, loadPedidosByEstadoPaginado } from '../redux/actions/dataActions';

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
    },

    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
      },

    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },

    selectEmpty: {
        marginTop: theme.spacing(2),
    },

    tituloFiltro: {
        display: 'flex',
        alignItems: 'baseline',
        justifyContent: 'space-between',
        width: '100%'
    },

    warning: {
        width: 60,
        height: 60,
        marginRight: 20
    },

    warningError: {
        display: 'flex',
        alignItems: 'center'
    },

    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
    },
}));


export default function HistorialPedidosAdmin(props) {
    const classes = useStyles();

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [estado, setEstado] = React.useState('TODO');
    const [orden, setOrden] = React.useState(' ');
    const [pageNo, setPageNo] = React.useState(0);
    const [pageSize, setPageSize] = React.useState(2);

    const handlePage = (event) =>{
        estado === "TODO" ? dispatch(loadPedidosPaginados(orden,event,pageSize)) : dispatch(loadPedidosByEstadoPaginado(estado, orden,event,pageSize))
        setPageNo(event)
    }

    const handleSize = (event) =>{
        let int = Number.isNaN(parseInt(event.target.value))||parseInt(event.target.value)<=0? 1: parseInt(event.target.value)
        estado === "TODO" ? dispatch(loadPedidosPaginados(orden,0,int)) : dispatch(loadPedidosByEstadoPaginado(estado, orden,0,int))
        setPageSize(int)
        setPageNo(0);
    }
    const handleChangeEstado = (event) => {
        
        event.target.value === "TODO" ? dispatch(loadPedidosPaginados(orden,0,pageSize)) : dispatch(loadPedidosByEstadoPaginado(event.target.value, orden,0,pageSize))
        setEstado(event.target.value);
        setPageNo(0);
    };

    const handleChangeOrden = (event) => {
        estado === "TODO" ? dispatch(loadPedidosPaginados(event.target.value,0,pageSize)) : dispatch(loadPedidosByEstadoPaginado(estado, event.target.value,0,pageSize))
        setOrden(event.target.value);
        setPageNo(0);
    };


    const pedidos = counter.data.pedidos;

    return (
        <div>
            <div className={classes.tituloFiltro}>
                
                <Typography variant='h3' className={classes.titulo}>HISTORIAL DE PEDIDOS</Typography>

            </div>
            <div className={classes.tituloFiltro}>
                <div>
                <FormControl className={classes.formControl}>
                        {pedidos.content.length===0 || pedidos.totalPages<=1?null:
                            <Pagination 
                                count={pedidos.totalPages} 
                                className={classes.selectEmpty}
                                page={pageNo+1} 
                                onChange={(event,newValue) => handlePage(newValue-1)} 
                                color="secondary" 
                                />}
                    </FormControl>
                    <FormControl className={classes.formControl}  style={{minWidth:90}}>
                        {pedidos.content.length===0?null:<div>
                        <InputLabel shrink id="demo-simple-select-placeholder-label-label">
                            Nº pedidos:
                            </InputLabel>
                            <TextField
                                id="outlined-multiline-static"
                                style={{width:70}}
                                className={classes.selectEmpty}
                                value={pageSize}
                                onChange={handleSize}
                                type="number"
                                />
                                </div>}
                     </FormControl>
                </div>
                <div className={classes.selects}>
                    
                    <FormControl className={classes.formControl}>
                        <InputLabel shrink id="demo-simple-select-placeholder-label-label">
                        Mostrando
                        </InputLabel>
                        <Select
                            labelId="demo-simple-select-placeholder-label-label"
                            id="demo-simple-select-placeholder-label"
                            value={estado}
                            onChange={handleChangeEstado}
                            className={classes.selectEmpty}
                        >
                        <MenuItem value="TODO">Todo</MenuItem>
                        <MenuItem value="EN_ESPERA">En Espera</MenuItem>
                        <MenuItem value="EN_TIENDA">En Tienda</MenuItem>
                        <MenuItem value="PREPARADO">Preparado</MenuItem>
                        <MenuItem value="EN_REPARTO">En Reparto</MenuItem>
                        <MenuItem value="ENTREGADO">Entregado</MenuItem>
                        <MenuItem value="CANCELADO">Cancelado</MenuItem>
                        </Select>
                    </FormControl>

                    <FormControl className={classes.formControl}>
                        <InputLabel shrink id="demo-simple-select-placeholder-label-label">
                        Ordenar: 
                        </InputLabel>
                        <Select
                            labelId="demo-simple-select-placeholder-label-label"
                            id="demo-simple-select-placeholder-label"
                            value={orden}
                            onChange={handleChangeOrden}
                            className={classes.selectEmpty}
                        >   
                            <MenuItem value=" ">Por defecto</MenuItem>
                            <MenuItem value="DESC">Mas nuevo</MenuItem>
                            <MenuItem value="ASC">Mas antiguo</MenuItem>
                        </Select>
                    </FormControl>
                </div>
            </div>

            <Backdrop className={classes.backdrop} open={counter.UI.loading}>
                <CircularProgress color="secondary" />
            </Backdrop>

            <div className={classes.main}>
                {pedidos === undefined ? 
                    <div className={classes.warningError}>
                        <WarningIcon className={classes.warning} color='primary'/>
                        <Typography variant='h5'>Se ha producido un error al cargar los datos. Por favor intentenlo de nuevo</Typography>
                    </div> 
                    
                    : 

                    pedidos.content.length === 0 ? 
                    
                        counter.UI.loading ? 
                    
                            null :

                            <div className={classes.warningError}>
                                <WarningIcon className={classes.warning} color='primary'/>
                                <Typography variant='h5'>En este momento no existe ningun pedido con el estado: {estado} </Typography>
                            </div>

                        :

                        pedidos.content.map((row) => (
                            <HistorialPedidoBox pedido={row} estado={estado} orden={orden} pageNo={pageNo} pageSize={pageSize}/>
                ))}
            </div>
                <div>
                    <FormControl className={classes.formControl}>
                        {pedidos.content.length===0 || pedidos.totalPages<=1?null:
                            <Pagination 
                                count={pedidos.totalPages} 
                                className={classes.selectEmpty}
                                page={pageNo+1} 
                                onChange={(event,newValue) => handlePage(newValue-1)} 
                                color="secondary" 
                                />}
                    </FormControl>
                    <FormControl className={classes.formControl}  style={{minWidth:90}}>
                        {pedidos.content.length===0?null:<div>
                        <InputLabel shrink id="demo-simple-select-placeholder-label-label">
                            Nº pedidos:
                        </InputLabel>
                            <TextField
                                id="outlined-multiline-static"
                                style={{width:70}}
                                className={classes.selectEmpty}
                                value={pageSize}
                                onChange={handleSize}
                                type="number"
                                />
                           </div> }
                    </FormControl>
                </div>
        </div>
    )
}
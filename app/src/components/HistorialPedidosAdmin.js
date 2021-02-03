import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

//Components
import HistorialPedidoBox from './HistorialPedidoBox';
import { loadPedidos, loadPedidosByEstado } from '../redux/actions/dataActions';

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
    }

}));


export default function HistorialPedidosAdmin(props) {
    const classes = useStyles();
    const pedidos = props.datos;

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [estado, setEstado] = React.useState('TODO');

    const handleChange = (event) => {
        event.target.value === "TODO" ? dispatch(loadPedidos()) : dispatch(loadPedidosByEstado(event.target.value))
        setEstado(event.target.value);
    };

    return (
        <div>
            <div className={classes.tituloFiltro}>
                
                <Typography variant='h3' className={classes.titulo}>HISTORIAL DE PEDIDOS</Typography>

                <FormControl className={classes.formControl}>
                    <InputLabel shrink id="demo-simple-select-placeholder-label-label">
                    Mostrando
                    </InputLabel>
                    <Select
                        labelId="demo-simple-select-placeholder-label-label"
                        id="demo-simple-select-placeholder-label"
                        value={estado}
                        onChange={handleChange}
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
            </div>        

            <div className={classes.main}>
                {counter.data.pedidos === undefined ? null :
                counter.data.pedidos.map((row) => (
                    <HistorialPedidoBox pedido={row}/>
                ))}
            </div>

        </div>
    )
}
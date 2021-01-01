import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

//MUI stuff
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';

//Redux stuff
import { connect } from 'react-redux';
import { loadPedidos,loadPedidosByEstado,cancelarPedido } from '../redux/actions/dataActions';

//Components
import Topbar from '../components/Topbar';
import TablaPedidosDesplegable from '../components/TablaPedidosDesplegable';


const style = {
    root:{
        flexGrow: 1, 
    },
    cuerpo:{
        margin: "2%",
    },
    form:{
        display: "inline-block",
    },
    tituloCatalogo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600
    },
    Select:{
        marginRight:30,
        minWidth: 120,
        display: "inline-block",
    }
}

class dependienteHistorialPedido extends Component {

    constructor(){
        super();
        this.state = {
            selected: "TODO",
            ordered: "MAS_NUEVO"            
        }
    }

    componentDidMount(){
        this.props.loadPedidos();
    }

    handleChange = (event) =>  {
        this.setState({
            [event.target.name]:event.target.value
        });
    }

    handleChangeSelected = (event) => {

        event.target.value ==="TODO"?this.props.loadPedidos():this.props.loadPedidosByEstado(event.target.value);

        this.setState({
            [event.target.name]:event.target.value
        })
    }

    delete(element){
        console.log(element)
    }

    render() {
        const {classes, data} = this.props;
        return (
            <div className = {classes.root}>
                <Topbar/>
                <div className = {classes.cuerpo}>
                    <Typography variant='h3' className={classes.tituloCatalogo}>HISTORIAL DE PEDIDOS</Typography>
                    <form>
                    <Typography 
                        className={classes.form}
                        variant='body1'>
                        Mostrando:
                    </Typography>
                    <Select
                        className={classes.Select}
                        labelId="demo-simple-select-outlined-label"
                        id="demo-simple-select-outlined"
                        name = "selected" 
                        value={this.state.selected?this.state.selected:"TODO"}
                        onChange={this.handleChangeSelected}
                        >
                        <MenuItem value="TODO">Todo</MenuItem>
                        <MenuItem value="EN_ESPERA">En Espera</MenuItem>
                        <MenuItem value="EN_TIENDA">En Tienda</MenuItem>
                        <MenuItem value="PREPARADO">Preparado</MenuItem>
                        <MenuItem value="EN_REPARTO">En Reparto</MenuItem>
                        <MenuItem value="ENTREGADO">Entregado</MenuItem>
                        <MenuItem value="CANCELADO">Cancelado</MenuItem>
                    </Select>

                    <Typography 
                        className={classes.form}
                        variant='body1'>
                        Ordenar:
                    </Typography>
                    <Select
                        className={classes.Select}
                        labelId="demo-simple-select-outlined-label"
                        id="demo-simple-select-outlined"
                        name = "ordered" 
                        value={this.state.ordered?this.state.ordered:"MAS_NUEVO"}
                        onChange={this.handleChange}
                        >
                        <MenuItem value="MAS_NUEVO">Mas nuevo</MenuItem>
                        <MenuItem value="MAS_ANTIGUO">Mas antiguo</MenuItem>
                    </Select>
                    </form>
                    <div className={classes.main}>
                        
                        {data.pedidos === undefined?null:data.pedidos.map((row) =>
                            <TablaPedidosDesplegable onConfirmCancelar = {this.props.cancelarPedido} key = {row.id} deletePedidos={this.delete} datos={row}/>
                        ) }
                    </div>
                </div>
            </div>
        )
    }
}

dependienteHistorialPedido.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadPedidos: PropTypes.func.isRequired,
    cancelarPedido: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadPedidos,
    loadPedidosByEstado,
    cancelarPedido
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteHistorialPedido))

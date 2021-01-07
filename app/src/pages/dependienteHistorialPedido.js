import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

//MUI stuff
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';
import FormControl from '@material-ui/core/FormControl';
import InputBase from '@material-ui/core/InputBase';
import CircularProgress from '@material-ui/core/CircularProgress';
import Backdrop from '@material-ui/core/Backdrop';


//Redux stuff
import { connect } from 'react-redux';
import { loadPedidos,loadPedidosByEstado,cancelarPedido } from '../redux/actions/dataActions';

//Components
import TablaPedidosDesplegable from '../components/TablaPedidosDesplegable';
import Snackbar from '../components/SnackBar'


const style = theme => ({
    root:{
        flexGrow: 1, 
    },
    form:{
        margin:"0.7%",
        marginLeft:35,
        fontSize:15,
        display: "inline-block",
    },
    tituloCatalogo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600
    },
    Select:{
        
        marginRight:30,
        minWidth: 100,
        fontSize:15,
        display: "inline-block",

        '&$outlined':{
            padding:0
        },

        '&$input':{
            padding:0
        }

    },
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
  },
})

const BootstrapInput = withStyles((theme) => ({
    input: {
      borderRadius: 4,
      position: 'relative',
      backgroundColor: theme.palette.background.paper,
      border: '1px solid #FFBC69',
      fontSize: 16,
      padding: '10px 26px 10px 12px',
      transition: theme.transitions.create(['border-color', 'box-shadow']),
    },
  }))(InputBase);

class dependienteHistorialPedido extends Component {

    constructor(){
        super();
        this.state = {
            selected: "TODO",
            ordered: "MAS_NUEVO",
            expanded: 1,         
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

    handleChange = (panel) => (event, isExpanded) => {

        this.setState({
            expanded: panel
        })
      };

    render() {
        const {classes, data ,UI:{errors,loading}} = this.props;
        return (
            <div className = {classes.root}>

                <Snackbar type = "error" open = {errors?true:false} message = {errors}></Snackbar>
                {errors ? document.getElementById("botonSnack").click():null}
                <Backdrop className={classes.backdrop} open={loading}>
                    <CircularProgress color="secondary" />
                </Backdrop>
                <div>
                    <Typography variant='h3' className={classes.tituloCatalogo}>HISTORIAL DE PEDIDOS</Typography>
                    <form>
                    <Typography 
                        className={classes.form}
                        variant='body1'>
                        Mostrando:
                    </Typography>
                    <FormControl variant="outlined" className={classes.Select}>
                        <Select
                            //className={classes.Select}
                            labelId="demo-simple-select-outlined-label"
                            id="demo-simple-select-outlined"
                            name = "selected" 
                            value={this.state.selected?this.state.selected:"TODO"}
                            onChange={this.handleChangeSelected}
                            input = {<BootstrapInput/>}
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
                    

                    <Typography 
                        className={classes.form}
                        variant='body1'>
                        Ordenar:
                    </Typography>
                    <FormControl variant="outlined" className={classes.Select}>
                        <Select
                            //className={classes.Select}
                            labelId="demo-simple-select-outlined-label"
                            id="demo-simple-select-outlined"
                            name = "ordered" 
                            value={this.state.ordered?this.state.ordered:"MAS_NUEVO"}
                            onChange={this.handleChange}
                            input = {<BootstrapInput/>}
                            >
                            <MenuItem value="MAS_NUEVO">Mas nuevo</MenuItem>
                            <MenuItem value="MAS_ANTIGUO">Mas antiguo</MenuItem>
                        </Select>
                    </FormControl>
                    
                    </form>
                    <div className={classes.main}>
                        
                        {data.pedidos === undefined?null:data.pedidos.map((row) =>
                            <TablaPedidosDesplegable handleChange={this.handleChange} isExpanded={this.state.expanded === row.id} onConfirmCancelar = {this.props.cancelarPedido} key = {row.id} deletePedidos={this.delete} datos={row}/>
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
    UI:PropTypes.object.isRequired,
    loadPedidos: PropTypes.func.isRequired,
    cancelarPedido: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data,
    UI: state.UI
})

const mapActionsToProps = {
    loadPedidos,
    loadPedidosByEstado,
    cancelarPedido
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteHistorialPedido))

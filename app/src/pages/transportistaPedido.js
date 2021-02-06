import React, { Component } from 'react';
import PropTypes from 'prop-types';
import TablaPedidosHoy from '../components/Tables/TablaPedidosHoy'
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';
import FormControl from '@material-ui/core/FormControl';
import InputBase from '@material-ui/core/InputBase';
import withStyles from '@material-ui/core/styles/withStyles';
//Redux stuff
import { connect } from 'react-redux';
import { loadPedidosHoy,loadPedidosByEstadoTransportista ,clear} from '../redux/actions/dataActions';
import SnackCallController from '../components/Other/SnackCallController';

const style = {
    Select:{
        
        marginTop:6,
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
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
    },
    form:{
        margin:"0.7%",
        marginLeft:35,
        fontSize:15,
        display: "inline-block",

    }
}


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


export class tranportistaPedidos extends Component {
    constructor(props){
        super(props);
        this.state = {
            data:[]
        }
    }

    componentDidMount(){
        this.props.loadPedidosHoy();
    }

    componentWillUnmount(){
        this.props.clear();
    }
    handleChangeSelected = (event) => {

        event.target.value ==="TODO"?this.props.loadPedidosHoy():this.props.loadPedidosByEstadoTransportista(event.target.value);

        this.setState({
            [event.target.name]:event.target.value
        })
    }

    render() {
        const {classes, data , UI:{errors,enviado}} = this.props;
        return (
            <div>
            <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
            <Typography variant='h3' className={classes.titulo}>MIS PEDIDOS</Typography>
            <Typography 
            className={classes.form}
            variant='body1'>
            Mostrando:
            </Typography>
        <FormControl variant="outlined" className={classes.Select} >
            <Select
                labelId="demo-simple-select-outlined-label"
                id="demo-simple-select-outlined"
                name = "selected" 
                value={this.state.selected?this.state.selected:"TODO"}
                onChange={this.handleChangeSelected}
                input = {<BootstrapInput/>}
                >
                <MenuItem value="TODO">Todo</MenuItem>
                <MenuItem value="PREPARADO">Preparado</MenuItem>
                <MenuItem value="EN_REPARTO">En Reparto</MenuItem>
            </Select>
        </FormControl>
            <div>
                {data.pedidos===undefined?null:
                <TablaPedidosHoy datos = {data.pedidos}/>
                }
            </div>
            </div>
        )
    }
}

tranportistaPedidos.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadPedidosHoy: PropTypes.func.isRequired,
    loadPedidosByEstadoTransportista: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data:state.data,
    UI:state.UI
})

const mapActionsToProps = {
    loadPedidosHoy,
    loadPedidosByEstadoTransportista,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(tranportistaPedidos))

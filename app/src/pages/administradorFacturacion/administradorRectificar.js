import React, { Component } from 'react';
import ReactDOM from "react-dom";
import { makeStyles } from "@material-ui/core/styles";
import Input from "@material-ui/core/Input";
import IconButton from "@material-ui/core/IconButton";
import DoneIcon from "@material-ui/icons/DoneAllTwoTone";
import RevertIcon from "@material-ui/icons/NotInterestedOutlined";
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import FormControl from '@material-ui/core/FormControl';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { clearFacturaEmitida, loadFacturaEmitida,clear } from '../../redux/actions/dataActions';
import Grid from '@material-ui/core/Grid';
import { TableContainer } from '@material-ui/core';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import SearchIcon from '@material-ui/icons/Search';
import SaveIcon from '@material-ui/icons/Save';
import DialogoAddProductos from '../../components/DialogoAddProductos';
import TablaEditarFactura from '../../components/TablaEditarFactura';

import SnackCallController from '../../components/SnackCallController';

const style = {

}

class rectificar extends Component {
    static propTypes = {
        prop: PropTypes
    }

    constructor(props) {
        super(props);
        this.state = {
            numFactura:'',
            lineasFacturas:[]
        }
        this.handleSubmit=this.handleSubmit.bind(this);
        this.handleOnChange=this.handleOnChange.bind(this);
    }

    componentDidMount() {
        clearFacturaEmitida();

    }
    
    handleOnChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    };

    handleSubmit = (event) => {
        event.preventDefault();
        clearFacturaEmitida();
        this.props.loadFacturaEmitida(this.state.numFactura);
        console.log('hola');
        console.log(this.props);
    }
 
    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const { classes, data,UI:{errors,enviado} } = this.props;        
        

        return (
            <div>
                <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
                <div id="busqueda" >
                <TextField id="numFactura" name="numFactura" onChange={this.handleOnChange} label="Inserte nº de factura" />
                <Button onClick={(event) => this.handleSubmit(event)}><SearchIcon /></Button>
                </div>
                {data.facturas.length === 0 ? null :

                <TablaEditarFactura factura={data.facturas}/>


                }



            </div>
        )
    }
}

rectificar.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadFacturaEmitida: PropTypes.func.isRequired,

}

const mapStateToProps = (state) => ({
    data: state.data,
    UI: state.UI
})

const mapActionsToProps = {
    loadFacturaEmitida,
    clearFacturaEmitida,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(rectificar))
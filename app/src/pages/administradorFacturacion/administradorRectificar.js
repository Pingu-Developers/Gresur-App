import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { clearFacturaEmitida, loadFacturaEmitida,clear } from '../../redux/actions/dataActions';
import SearchIcon from '@material-ui/icons/Search';
import TablaEditarFactura from '../../components/Tables/TablaEditarFactura';

import SnackCallController from '../../components/Other/SnackCallController';

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
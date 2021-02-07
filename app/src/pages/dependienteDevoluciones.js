import React, { Component } from 'react';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import { getClientes,clearClientes } from '../redux/actions/clienteActions'
import { clear } from '../redux/actions/dataActions'

import FormDatosDevolucion from '../components/Forms/FormDatosDevolucion'

import SnackCallController from '../components/Other/SnackCallController';

//MUI stuff
import Typography from '@material-ui/core/Typography';

const styles = theme =>({

    root:{
        backgroundColor:"#F6F6F6",
        height:"100 vr"
    },
    tituloCatalogo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        color: '#7a7a7a',
        borderBottom: '1px solid #bdbdbd',
        paddingBottom: '10px'
    },
    fechaLeft:{
        margin:0,
        width:225
    }

});

class dependienteDevoluciones extends Component {


    handleFechaInicioChange = (date) => {
        this.setState({
         fechaInicio:date
     })
    };

    componentDidMount() {
        this.props.getClientes()
    }

    componentWillUnmount(){
        this.props.clearClientes();
        this.props.clear();
    }
    
    render() {

        const { classes ,UI:{errors,enviado} } = this.props;

        return (
            <div>
                <SnackCallController  enviado = {enviado} message = {"Devolucion realizada correctamente"} errors={errors} />

                <Typography variant='h3' className={classes.tituloCatalogo}>HACER UNA DEVOLUCION</Typography>
                <div style = {{padding: '0px 30px 0px 30px'}}>
                    <FormDatosDevolucion/>
                </div>
            </div>
        )
    }
}

dependienteDevoluciones.propTypes = {

}

const mapStateToProps = (state) => ({
    UI:state.UI
})

const mapActionsToProps = {
    getClientes,
    clearClientes,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(dependienteDevoluciones))

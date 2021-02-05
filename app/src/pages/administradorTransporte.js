import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

//Redux stuff
import { loadVehiculosSeguroITVReparacion , loadPedidos, clear} from '../redux/actions/dataActions';

//Components

import VerticalTabs from '../components/VerticalTabsAdmin';
import TablaMostradorVehiculosSeguroITVReparacion from '../components/TablaMostradorVehiculosSeguroITVReparacion';
import PopUpNuevoVehiculo from '../components/PopUpNuevoVehiculo';
import SnackCallController from '../components/SnackCallController';


const style = {  
    verticalTabs: {
    }
}

class administradorTransporte extends Component {
    constructor(props){
        super(props);
        this.state ={
            data: []
        }
    }

    componentDidMount(){
        this.props.loadVehiculosSeguroITVReparacion();
        this.props.loadPedidos();
    }

    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const {classes, data,UI:{errors,enviado}} = this.props;

        return (

            <div>  
                <VerticalTabs className={classes.verticalTabs} datos = {data}/>
            <div>
                <SnackCallController  enviado = {enviado} message = {"Factura realizada correctamente"} errors={errors} />
                <div className={classes.tituloyForm}>
                    <Typography variant='h3' className={classes.titulo}>VEHICULOS DE LA EMPRESA</Typography>
                    <PopUpNuevoVehiculo className={classes.boton}/>
                </div>

                <div className={classes.main}>
                    {data === undefined? null:<TablaMostradorVehiculosSeguroITVReparacion datos = {data}/>}
                </div>
            </div>
        )
    }
}

administradorTransporte.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadVehiculosSeguroITVReparacion: PropTypes.func.isRequired,
    loadPedidos: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data,
    UI: state.UI
})

const mapActionsToProps = {
    loadVehiculosSeguroITVReparacion,
    loadPedidos,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorTransporte))
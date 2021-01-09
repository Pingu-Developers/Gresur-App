import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import Topbar from '../components/Topbar';
import Typography from '@material-ui/core/Typography';

//Redux stuff
import { loadVehiculosSeguroITVReparacion , clear } from '../redux/actions/dataActions';

//Components
import TablaMostradorVehiculosSeguroITVReparacion from '../components/TablaMostradorVehiculosSeguroITVReparacion';
import PopUpNuevoVehiculo from '../components/PopUpNuevoVehiculo';



const style = {

    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
      },

      tituloyForm: {
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center'
      },
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
    }

    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const {classes, data} = this.props;

        return (
            <div>
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
    loadVehiculosSeguroITVReparacion: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadVehiculosSeguroITVReparacion,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorTransporte))
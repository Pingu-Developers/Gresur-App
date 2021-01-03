import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Typography from '@material-ui/core/Typography';

//Redux stuff
import { connect } from 'react-redux';
import { loadVehiculosITVSeguroDisponibilidadByTransportistaNIF } from '../redux/actions/dataActions';

//Componentes
import TablaMostradorVehiculos from '../components/TablaMostradorVehiculos';

const style = {

    tituloVehiculos: {
    }
}

class transportistaVehiculos extends Component {
    
    constructor(props){
        super(props);
        this.state ={
            data: []
        }
    }

    componentDidMount(){
        this.props.loadVehiculosITVSeguroDisponibilidadByTransportistaNIF();
    }

    render() {

        const {classes,data} = this.props;

        return (
            <div>
                <Typography variant='h3' className={classes.tituloVehiculos}>VEHICULOS</Typography>

                <TablaMostradorVehiculos data = {data}/>

            </div>
        )
    }
}

transportistaVehiculos.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadVehiculos: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadVehiculosITVSeguroDisponibilidadByTransportistaNIF
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(transportistaVehiculos))

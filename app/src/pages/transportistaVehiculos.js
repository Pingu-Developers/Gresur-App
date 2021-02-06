import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Typography from '@material-ui/core/Typography';

//Redux stuff
import { connect } from 'react-redux';
import { loadVehiculosITVSeguroDisponibilidadByTransportista, loadOcupacionVehiculosEnReparto,clear } from '../redux/actions/dataActions';

//Componentes
import TablaMostradorVehiculos from '../components/Tables/TablaMostradorVehiculos';
import BarraOcupacionVehiculo from '../components/ProgressBars/BarraOcupacionVehiculo';


const style = {

    tituloVehiculos: {
    },

    ocupacion: {
        marginTop: 70
    }
}

class transportistaVehiculos extends Component {
    
    constructor(props){
        super(props);
        this.state ={
            data: [],
        }
    }

    componentDidMount(){
        this.props.loadVehiculosITVSeguroDisponibilidadByTransportista();
        this.props.loadOcupacionVehiculosEnReparto();
    }

    componentWillUnmount(){
        this.props.clear();
    }
    render() {

        const {classes,data} = this.props;

        return (
            <div>
                <Typography variant='h3' className={classes.tituloVehiculos}>VEHICULOS</Typography>

                <TablaMostradorVehiculos data = {data.vehiculos}/>
                
                <div className={classes.ocupacion}>

                    {data.ocupaciones===undefined || data.ocupaciones.length===0? 
                        <Typography variant='h4'>NO HAY VEHICULOS CON PEDIDOS ASOCIADOS PARA LA FECHA {new Date().toLocaleDateString()}</Typography>
                        : <Typography variant='h4'>PORCENTAJE DE OCUPACION DE VEHICULOS CON PEDIDOS ASOCIADOS ({new Date().toLocaleDateString()})
                        
                            {data.ocupaciones.map((row) => <BarraOcupacionVehiculo datos={row}/> )}

                          </Typography> 
                    }

                </div>

              

            </div>
        )
    }
}

transportistaVehiculos.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadVehiculosITVSeguroDisponibilidadByTransportista: PropTypes.func.isRequired,
    loadOcupacionVehiculosEnReparto: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data,
})

const mapActionsToProps = {
    loadVehiculosITVSeguroDisponibilidadByTransportista,
    loadOcupacionVehiculosEnReparto,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(transportistaVehiculos))
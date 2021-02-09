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
import { Divider } from '@material-ui/core';


const style = {

    tituloVehiculos: {
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        color: '#7a7a7a',
        margin: '30px 0px 20px 0px',
        padding: '0px 0px 15px 20px',
        width: '100%',
        borderBottom: '1px solid #bdbdbd'
    },
    barText: {
        fontSize: 20,
        fontWeight: 600,
        color: '#7a7a7a',
        padding: '0px 0px 0px 20px',
        margin: 0,
        width: '100%',
    },
    ocupacion: {
        marginTop: 50,
        display: 'flex',
        justifyContent: 'center'
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
                
                <Divider style = {{marginTop: 40, width: '100%'}}/>        

                <div className={classes.ocupacion}>

                    {data.ocupaciones===undefined || data.ocupaciones.length===0? 
                        null
                        : 
                        <div style = {{marginTop: -20}}>
                            <Typography className = {classes.barText} variant='h6'>PORCENTAJE DE OCUPACION DE VEHICULOS CON PEDIDOS ASOCIADOS ({new Date().toLocaleDateString()})
                        
                            {data.ocupaciones.map((row) => <BarraOcupacionVehiculo datos={row}/> )}

                          </Typography> 
                        </div>
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
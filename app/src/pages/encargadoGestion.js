import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Typography from '@material-ui/core/Typography';
import VerticalResizablePBar from '../components/VerticalResizablePBar';

import { loadAlmacenGestionEncargado, clearAlmacenGestionEncargado } from '../redux/actions/dataActions'


const style = {
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
    },
    wrapper: {
        padding: '30px 30px 30px 60px',
    },
    axis: {
        borderLeft: '1px solid gray',
        borderBottom: '1px solid gray',
        height: '60vh',
        minHeight: 400,
        paddingLeft: 50,
        display: 'flex',
        alignItems: 'flex-end',
        width: '80%',
    },
}

class encargadoGestion extends Component {
    static propTypes = {
        prop: PropTypes
    }

    componentDidMount(){
        this.props.loadAlmacenGestionEncargado()
    }

    componentWillUpdate(nextProps, nextState){
        if(nextProps.data.gestionAlmacenEncargado !== this.props.data.gestionAlmacenEncargado){
            return true;
        }
    }

    componentWillUnmount(){
        this.props.clearAlmacenGestionEncargado()
    }

    porcentajeDeAlmacenOcupado(){
        let sum = 0.
        if(this.props.data.gestionAlmacenEncargado){
            this.props.data.gestionAlmacenEncargado.map( (entry) => sum = sum+entry.porcentajeAlmacen);
        }
        console.log(sum)
        return sum;
    }

    render() {
        const { classes, data:{gestionAlmacenEncargado} } = this.props;
        console.log(gestionAlmacenEncargado)
        return (
            <div>
                <Typography variant = 'h3' className = {classes.titulo}>GESTION DEL ALMACEN</Typography>

                <div className = {classes.wrapper}>
                    <div className = {classes.axis}>
                        {gestionAlmacenEncargado ? 
                            gestionAlmacenEncargado.map((entry) => 
                                <VerticalResizablePBar 
                                    categoria = {entry.categoria} 
                                    ocupacion = {entry.ocupacionEstanteria}
                                    porcentajeAlmacen = {entry.porcentajeAlmacen}
                                    totalOcupado = {this.porcentajeDeAlmacenOcupado()}/>
                            ) : null
                        }
                    </div>
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    data:state.data
})

const mapActionsToProps = {
    loadAlmacenGestionEncargado,
    clearAlmacenGestionEncargado,
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(encargadoGestion))
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Typography from '@material-ui/core/Typography';
import VerticalResizablePBar from '../components/VerticalResizablePBar';

import { loadAlmacenGestionEncargado, clearAlmacenGestionEncargado } from '../redux/actions/dataActions'
import { Divider } from '@material-ui/core';


const style = {
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
    },
    histogram: {
        padding: '30px 30px 30px 60px',
        overflowY: 'hidden',
        maxHeight: 600,
        minHeight: 400,
        width: '80%'
    },
    axis: {
        position: 'relative',
        borderLeft: '1px solid gray',
        borderBottom: '1px solid gray',
        height: '60vh',
        minHeight: 400,
        display: 'flex',
        alignItems: 'flex-end',
        justifyContent: 'space-around',
    },
    gridWrapper: {
        position: 'absolute',
        height: '100%',
        width: '100%',
        display: 'grid',
        alignContent: 'space-between'
    },
    gridRow: {
        userSelect: 'none'
    }
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
        return sum;
    }

    render() {
        const { classes, data:{gestionAlmacenEncargado} } = this.props;
        return (
            <div>
                <Typography variant = 'h3' className = {classes.titulo}>GESTION DEL ALMACEN</Typography>

                <div className = {classes.wrapper}>
                    <div className = {classes.histogram}>
                        <div className = {classes.axis}>
                            <div className = {classes.gridWrapper}>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -50}}>100%</span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}></span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>80%</span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}></span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>60%</span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}></span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>40%</span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}></span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>20%</span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -40}}></span><Divider style = {{marginTop: -13}}/></span>
                                <span className = {classes.gridRow}><span style = {{marginLeft: -30}}>0%</span><Divider style = {{marginTop: -13}}/></span>
                            </div>
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
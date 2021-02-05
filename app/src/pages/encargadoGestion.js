import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Typography from '@material-ui/core/Typography';
import VerticalResizablePBar from '../components/VerticalResizablePBar';
import AlmacenOcupacionPBar from '../components/AlmacenOcupacionPBar';
import Button from '@material-ui/core/Button';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import ZoomOutMapIcon from '@material-ui/icons/ZoomOutMap';

import { loadAlmacenGestionEncargado, clearAlmacenGestionEncargado } from '../redux/actions/dataActions'
import EncargadoGestionAxisNums from '../components/EncargadoGestionAxisNums';


const style = {
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
    },
    wrapper: {
        display: 'grid',
        gridTemplateColumns: '12fr 3fr',
        gridTemplateRows: '1fr 1fr 1fr 1fr 1fr',
        alignItems: 'center',
        justifyItems: 'center'
    },
    histogram: {
        padding: '30px 30px 30px 60px',
        overflowY: 'hidden',
        maxHeight: '60vh',
        minHeight: '60vh',
        width: '95%',
        gridRow: '1 / span 5'
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
    zoomControlGrp: {
        gridRow: 5,
        display: 'grid',
        justifyItems: 'center',
        gridRowGap: 5
    },
    zoomControl: {
        display: 'inline-flex',
        height: 36,
    },
    zoomBtn: {
        color: 'white'
    },
}

class encargadoGestion extends Component {
    constructor(props){
        super(props)

        this.state = {
            disableZoomIn : false,
            disableZoomOut : true,
            disableZoomOutMap: true,
            zoom: undefined}
    }
    componentDidMount(){
        var axis = document.getElementById('axis');
        this.setState({zoom: axis.clientHeight})
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

    porcentajeDeAlmacenAsignado(){
        let sum = 0.
        if(this.props.data.gestionAlmacenEncargado){
            this.props.data.gestionAlmacenEncargado.map( (entry) => sum = sum+entry.porcentajeAlmacen);
        }
        return sum;
    }
    porcentajeDeAlmacenOcupado(){
        let sum = 0.
        if(this.props.data.gestionAlmacenEncargado){
            this.props.data.gestionAlmacenEncargado.map( (entry) => sum = sum + entry.porcentajeAlmacen * (entry.ocupacionEstanteria/100));
        }
        return sum;
    }

    zoomIn(event){
        event.preventDefault();
        var axis = document.getElementById('axis');
        var hist = axis.parentElement;

        if(this.state.disableZoomOut && this.state.disableZoomOutMap){
            this.setState({disableZoomOut: false, disableZoomOutMap: false})
        } 
        if(axis.clientHeight < 5000){
            axis.style.height = axis.clientHeight * 1.2 + 'px';
            hist.scrollTop = hist.scrollHeight;
            this.setState({zoom: axis.clientHeight})
        } else{
            this.setState({disableZoomIn: true})
        }   
    }

    zoomOut(event){
        event.preventDefault();
        var axis = document.getElementById('axis');
        var hist = axis.parentElement;

        if(this.state.disableZoomIn){
            this.setState({disableZoomIn : false})
        }
        if(axis.clientHeight > window.innerHeight * 0.60){
            axis.style.height = axis.clientHeight / 1.2 + 'px';
            hist.scrollTop = hist.scrollHeight;
            this.setState({zoom: axis.clientHeight})
        } else{
            this.setState({disableZoomOut: true, disableZoomOutMap: true})
        }
    }

    zoomOutMap(event){
        event.preventDefault();
        var axis = document.getElementById('axis');
        axis.style.height = '60vh';
        this.setState({disableZoomIn: false, disableZoomOut: true, disableZoomOutMap: true, zoom: axis.clientHeight})
    }

    render() {
        const { classes, data:{gestionAlmacenEncargado} } = this.props;
        return (
            <div style = {{userSelect : 'none'}}>
                <Typography variant = 'h3' className = {classes.titulo}>GESTION DEL ALMACEN</Typography>

                <div className = {classes.wrapper}>
                    {/* HISTOGRAMA */}
                    <div className = {classes.histogram}>
                        <div className = {classes.axis} id = 'axis'>
                            <EncargadoGestionAxisNums zoom = {this.state.zoom}/>
                            {gestionAlmacenEncargado ? 
                                gestionAlmacenEncargado.map((entry) => 
                                        <VerticalResizablePBar 
                                            categoria = {entry.categoria} 
                                            ocupacion = {entry.ocupacionEstanteria}
                                            porcentajeAlmacen = {entry.porcentajeAlmacen}
                                            totalOcupado = {this.porcentajeDeAlmacenAsignado()}
                                            axisH = {document.getElementById('axis').clientHeight}/>
                                ) : null
                            }
                        </div>               
                    </div>

                    {/* GRUPO DE BOTONES DE ZOOM */}
                    <div className = {classes.zoomControlGrp}>
                        <Typography variant = "h7" style = {{fontWeight : 'bold'}}>Control de zoom</Typography>
                        <div className = {classes.zoomControl}>
                            <Button 
                                disabled = {this.state.disableZoomIn}
                                variant="contained" 
                                color="primary" 
                                onClick={(e) => this.zoomIn(e)} 
                                className={classes.zoomBtn}
                                style={{borderRadius:'10px 0px 0px 10px'}}
                            >
                                <ZoomInIcon/>
                            </Button>

                            <Button 
                                disabled = {this.state.disableZoomOutMap}
                                variant="contained" 
                                color="primary" 
                                onClick={(e)=>this.zoomOutMap(e)} 
                                className={classes.zoomBtn}
                                style={{borderRadius: 0, borderLeft: '1px solid #fafafa', borderRight: '1px solid #fafafa'}}
                            >
                                <ZoomOutMapIcon/>
                            </Button>

                            <Button 
                                disabled = {this.state.disableZoomOut}
                                variant="contained" 
                                color="primary" 
                                onClick={(e) => this.zoomOut(e)} 
                                className={classes.zoomBtn}
                                style={{borderRadius:'0px 10px 10px 0px'}}
                            >
                                <ZoomOutIcon/>
                            </Button>
                        </div>
                    </div>

                    {/* PROGRESS BARS DEL ALMACEN */}
                    <div className = {classes.progressBarAsignado} style = {{gridRow: 1, width: '80%'}}>
                        <div style = {{display: 'grid', gridRowGap: 5}}>
                            <Typography variant = "h7" style = {{fontWeight : 'bold', color: '#7a7a7a'}}>% Del almacen aprovechado</Typography>
                            <AlmacenOcupacionPBar value = {this.porcentajeDeAlmacenAsignado()}/>
                        </div>
                    </div>
                    <div className = {classes.progressBarOcupado} style = {{gridRow: 2, width: '80%'}}>
                        <div style = {{display: 'grid', gridRowGap: 5}}>
                            <Typography variant = "h7" style = {{fontWeight : 'bold', color: '#7a7a7a'}}>% Del almacen ocupado</Typography>
                            <AlmacenOcupacionPBar value = {this.porcentajeDeAlmacenOcupado()}/>
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
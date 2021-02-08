import React, { Component } from 'react';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Typography from '@material-ui/core/Typography';
import VerticalResizablePBar from '../components/ProgressBars/VerticalResizablePBar';
import AlmacenOcupacionPBar from '../components/ProgressBars/AlmacenOcupacionPBar';
import Button from '@material-ui/core/Button';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import ZoomOutMapIcon from '@material-ui/icons/ZoomOutMap';

import { loadAlmacenGestionEncargado, clearAlmacenGestionEncargado } from '../redux/actions/dataActions'
import EncargadoGestionAxisNums from '../components/Charts/EncargadoGestionAxisNums';
import SnackCallController from '../components/Other/SnackCallController';

const style = {
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        borderBottom: '1px solid #bdbdbd',
        margin: '20px 0px 15px 0px',
        color: '#7a7a7a',
        padding: '0px 0px 15px 20px'
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
        overflowY: 'auto',
        scrollbarWidth: 'none',
        maxHeight: '60vh',
        minHeight: '60vh',
        width: '95%',
        gridRow: '1 / span 5',
        '&::-webkit-scrollbar': {
            width: 0,
        }
    },
    axis: {
        position: 'relative',
        borderLeft: '1px solid gray',
        borderBottom: '1px solid gray',
        height: '60vh',
        minHeight: 494,
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

        this.pos = {top: 0, left: 0, x: 0, y: 0}
        this.mouseDownHandler = this.mouseDownHandler.bind(this);
        this.mouseUpHandler = this.mouseUpHandler.bind(this);
        this.mouseMoveHandler = this.mouseMoveHandler.bind(this);
    }
    componentDidMount(){
        //setea el zoom inicial
        var axis = document.getElementById('axis');
        this.setState({zoom: axis.clientHeight})

        //event Handler del histograma
        var hist = axis.parentElement;
        hist.addEventListener('mousedown', this.mouseDownHandler);

        //carga los datos de la bd
        this.props.loadAlmacenGestionEncargado(!this.props.almacenAdm ? -1 : this.props.almacenAdm)
    }

    componentWillUnmount(){
        this.props.clearAlmacenGestionEncargado()
    }

    componentWillUpdate(nextProps, nextState){
        if(nextProps.data.gestionAlmacenEncargado !== this.props.data.gestionAlmacenEncargado){
            return true;
        }
    }

    // CALCULO DE PORCENTAJES
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

    // FUNCIONES DE ZOOM
    zoomIn(event){
        event.preventDefault();
        var axis = document.getElementById('axis');
        var hist = axis.parentElement;
        var bottom = hist.scrollHeight - hist.scrollTop === hist.clientHeight;

        if(this.state.disableZoomOut && this.state.disableZoomOutMap){
            this.setState({disableZoomOut: false, disableZoomOutMap: false})
        } 
        if(axis.clientHeight < 5000){
            axis.style.height = axis.clientHeight * 1.2 + 'px';
            hist.style.cursor = 'grab';
            this.setState({zoom: axis.clientHeight})
        } 
        if(axis.clientHeight >= 5000){
            this.setState({disableZoomIn: true})
        }
        if(bottom){
            hist.scrollTop = hist.scrollHeight;
        }
    }

    zoomOut(event){
        event.preventDefault();
        var axis = document.getElementById('axis');
        var hist = axis.parentElement;
        var bottom = hist.scrollHeight - hist.scrollTop === hist.clientHeight;

        if(this.state.disableZoomIn){
            this.setState({disableZoomIn : false})
        }
        if(axis.clientHeight > window.innerHeight * 0.60 + 1){
            axis.style.height = axis.clientHeight / 1.2 + 'px';
            this.setState({zoom: axis.clientHeight})
        } 
        if(axis.clientHeight < window.innerHeight * 0.60 + 1){
            hist.style.cursor = 'default';
            this.setState({disableZoomOut: true, disableZoomOutMap: true})
        }
        if(bottom){
            hist.scrollTop = hist.scrollHeight;
        }
    }

    zoomOutMap(event){
        event.preventDefault();
        var axis = document.getElementById('axis');
        axis.style.height = '60vh';
        axis.parentElement.style.cursor = 'default';
        this.setState({disableZoomIn: false, disableZoomOut: true, disableZoomOutMap: true, zoom: axis.clientHeight})
    }

    // FUNCIONES DE SCROLL ARRASTRANDO
    mouseDownHandler(e){
        var axis = document.getElementById('axis');
        if(axis.clientHeight > window.innerHeight * 0.60 + 1){
            var hist = document.getElementById('axis').parentElement;

            hist.style.cursor = 'grabbing';
            this.pos = {
                left: hist.scrollLeft,
                top: hist.scrollTop,
                // Current mouse pos
                x: e.clientX,
                y: e.clientY,
            };
            document.addEventListener('mousemove', this.mouseMoveHandler);
            document.addEventListener('mouseup', this.mouseUpHandler);
        }
    };

    mouseMoveHandler(e){
        var hist = document.getElementById('axis').parentElement;

        // How far mouse been moved
        const dx = e.clientX - this.pos.x;
        const dy = e.clientY - this.pos.y;

        //Scroll the element
        hist.scrollTop = this.pos.top - dy;
        hist.scrollLeft = this.pos.left - dx;
    };

    mouseUpHandler(){
        var hist = document.getElementById('axis').parentElement;

        hist.style.cursor = 'grab';

        document.removeEventListener('mousemove', this.mouseMoveHandler);
        document.removeEventListener('mouseup', this.mouseUpHandler);
    }

    // RENDER
    render() {
        const { classes, data:{gestionAlmacenEncargado},UI:{errors,enviado} } = this.props;
        return (
            <div style = {{userSelect : 'none'}}>
                <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
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
                                            axisH = {document.getElementById('axis').clientHeight}
                                            dragHandler = {this.mouseDownHandler}
                                            almAdm = {!this.props.almacenAdm ? -1 : this.props.almacenAdm}/>
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
    data:state.data,
    UI: state.UI
})

const mapActionsToProps = {
    loadAlmacenGestionEncargado,
    clearAlmacenGestionEncargado,
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(encargadoGestion))
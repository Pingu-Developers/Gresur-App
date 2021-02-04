import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import DragHandleIcon from '@material-ui/icons/DragHandle';
import Tooltip from '@material-ui/core/Tooltip';

import { loadAlmacenGestionEncargado, updateEstanteriaCapacidad } from '../redux/actions/dataActions'


const style = {
    progressBarDiv: {
        position: 'relative',
        border: '1px solid #bdbdbd',
        backgroundColor: '#d4e6f1',
        width: '10%',
        display: 'flex',
        alignItems: 'flex-end',
        borderRadius: '10px 10px 0px 0px'
    },
    rszBtn: {
        position: 'absolute',
        top: 0,
        width: '100%',
        height: 20,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgb(0, 0, 0, 0.10)',
        borderRadius: '10px 10px 0px 0px',
        cursor: 'ns-resize',
        color: 'gray',
        zIndex: 10000,
    },
    ocupado: {
        width: '100%',
        textAlign: 'center',
        userSelect: 'none',
        borderRadius: '10px 10px 0px 0px',
    },
    categoriaTxt: {
        width: '100%',
        display: 'flex',
        justifyContent: 'center',
        position: 'absolute',
        bottom: -30,
        userSelect: 'none'
    }
}

class encargadoGestion extends Component {

    constructor(props){
        super(props)
        this.state = {
            porcentajeCapacidad : undefined,
            minHeight: undefined,
            porcentajeOcupacionEst: undefined,
        }
    }
    componentDidMount(){
        var axis = document.getElementById('axis');

        this.setState({
            porcentajeCapacidad : this.props.porcentajeAlmacen, 
            minHeight : this.props.ocupacion / 100 * (this.props.porcentajeAlmacen / 100 * axis.clientHeight),
            porcentajeOcupacionEst : this.props.ocupacion})
        this.updateBars(true);
    }

    componentDidUpdate(){
        this.updateBars(false);
    }

    updateBars(mount){
        //props a variables para usarla dentro de las funciones js
        var
            categoria = this.props.categoria,
            porcentajeAlm = this.props.porcentajeAlmacen,
            totalOcupado = this.props.totalOcupado,
            ocupacion = this.state.porcentajeOcupacionEst,
            postData = () => this.props.updateEstanteriaCapacidad(categoria, this.state.porcentajeCapacidad);


        //estado de las barras
        var progressBar = document.getElementById(categoria + 'progressBarDiv');
        progressBar.style['height'] = this.state.porcentajeCapacidad + '%';
        progressBar.style['max-height'] = (100 - totalOcupado + porcentajeAlm) + '%';
        progressBar.style['min-height'] = this.state.minHeight + 'px';

        var ocuppied = document.getElementById(categoria + 'ocuppied');
        ocuppied.style.height = progressBar.style.minHeight;
     
        if(ocupacion < 25)
            ocuppied.style.backgroundColor = 'green';
        else if(ocupacion <50)
            ocuppied.style.backgroundColor = 'yellow';
        else if (ocupacion < 75)
            ocuppied.style.backgroundColor = 'orange';
        else if (ocupacion < 90)
            ocuppied.style.backgroundColor = '#FF5009';
        else
            ocuppied.style.backgroundColor = 'red';

        // variables para las funciones
        var 
            doc = document,
            main = document.querySelector('#' + categoria + 'progressBarDiv'),
            rsz = document.getElementById(categoria + 'rszBtn'),
            ht, y, dy;

        // funcion actualiza el porcentaje
        var updateData = () => {
            var tmp = main.clientHeight/main.parentElement.clientHeight * 100;
            if(this.state.porcentajeCapacidad !== tmp){
                var ocupadoTmp = parseFloat(progressBar.style.minHeight) / progressBar.clientHeight * 100;
                this.setState({
                    porcentajeCapacidad : tmp,
                    porcentajeOcupacionEst : ocupadoTmp > 100 ? 100 : ocupadoTmp});
            }
        }
      
        // funciones de resize
        var startResize = function(evt) {
            y = evt.screenY;
            ht = main.clientHeight;
        };

        var resize = function(evt){
            dy = evt.screenY - y;
            y = evt.screenY;
            ht -= dy;
            main.style.height = ht + "px";
            updateData();         
        };

        if(mount){
            rsz.addEventListener("mousedown", function(evt){
                startResize(evt);
                doc.body.addEventListener("mousemove", resize);
                doc.body.addEventListener("mouseup", (ev) => {
                    doc.body.removeEventListener("mousemove", resize);
                    postData()
                }, {once : true});
            });
        }    
    }


    render() {
        const { classes } = this.props;
        return (
            <div className = {classes.progressBarDiv} id = {this.props.categoria + 'progressBarDiv'}>
                <Tooltip 
                    title = {this.state.porcentajeCapacidad ? 
                        <p style = {{fontSize : 15, margin: 5, fontWeight: 'bold'}}>
                            {this.state.porcentajeCapacidad.toFixed(1)} %
                        </p> : 'unknown'} 
                    placement = "top" 
                    arrow
                    interactive
                >
                    <div className = {classes.rszBtn} id = {this.props.categoria + 'rszBtn'}><DragHandleIcon/></div>
                </Tooltip>
                <div className = {classes.ocupado} id = {this.props.categoria + 'ocuppied'}>
                    <p style = {{position: 'absolute', bottom: 0, margin: 0, width: '100%'}}>
                        {this.state.porcentajeOcupacionEst ? this.state.porcentajeOcupacionEst.toFixed(2) + '%' : '?? %'}
                    </p>
                </div>
                <div className = {classes.categoriaTxt}>{this.props.categoria}</div>
            </div>
        )
    }
}

encargadoGestion.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    loadAlmacenGestionEncargado,
    updateEstanteriaCapacidad,
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(encargadoGestion))
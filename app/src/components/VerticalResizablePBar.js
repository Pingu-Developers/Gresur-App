import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import { loadAlmacenGestionEncargado, updateEstanteriaCapacidad } from '../redux/actions/dataActions'



const style = {
    progressBarDiv: {
        position: 'relative',
        border: '1px solid #bdbdbd',
        backgroundColor: '#eaeaea',
        width: 100,
        display: 'flex',
        alignItems: 'flex-end'
    },
    rszBtn: {
        position: 'absolute',
        top: 0,
        width: '100%',
        height: 20,
        backgroundColor: 'red',
    }
}

class encargadoGestion extends Component {

    componentDidMount(){
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
            updateData = (cat, cap) => this.props.updateEstanteriaCapacidad(cat, cap);

        //estado de las barras
        var progressBar = document.getElementById(categoria + 'progressBarDiv');
        progressBar.style['height'] = porcentajeAlm + '%';
        progressBar.style['max-height'] = (100 - totalOcupado + porcentajeAlm) + '%';

        // variables para las funciones
        var 
            doc = document,
            main = document.querySelector('#' + categoria + 'progressBarDiv'),
            rsz = document.getElementById(categoria + 'rszBtn'),
            ht, y, dy;

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
        };

        var updateTotalOcupado = function(evt){
            let porcentajeCapacidad = main.clientHeight/main.parentElement.clientHeight * 100;
            updateData(categoria, porcentajeCapacidad);
        }

        if(mount){
            rsz.addEventListener("mousedown", function(evt){
                startResize(evt);
                doc.body.addEventListener("mousemove", resize);
                doc.body.addEventListener("mouseup", (ev) => {
                    doc.body.removeEventListener("mousemove", resize);
                    updateTotalOcupado();
                }, {once : true});
            });
        }    
    }


    render() {
        const { classes } = this.props;
        return (
            <div className = {classes.progressBarDiv} id = {this.props.categoria + 'progressBarDiv'}>
                <div className = {classes.rszBtn} id = {this.props.categoria + 'rszBtn'}></div>
                <div className = {classes.ocupado} id = {this.props.categoria + 'ocuppied'}>{this.props.ocupacion}%</div>
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
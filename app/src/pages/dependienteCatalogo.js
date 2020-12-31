import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

//Redux stuff
import { connect } from 'react-redux';
import { loadProductos } from '../redux/actions/dataActions' 

//Componentes
import Topbar from '../components/Topbar';
import TablaCatalogoProductosDesplegable from '../components/TablaCatalogoProductosDesplegable';


const style = {

}

class dependienteCatalogo extends Component {
    
    constructor(props){
        super(props);
        this.state ={
            data: []
        }
    }
    
    componentDidMount(){
        this.props.loadProductos();
    }

    render() {
        const {classes, data} = this.props;

        return (
            <div>
                <Topbar/>

                <div className={classes.main}>
                  { 
                    data.length ===0? null: data.categorias.map((row) => 
                        <TablaCatalogoProductosDesplegable categoria={row} productos = {data.productos}/>
                    )
                  }
                </div>
            </div>
        )
    }
}

dependienteCatalogo.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadProductos: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadProductos
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteCatalogo))

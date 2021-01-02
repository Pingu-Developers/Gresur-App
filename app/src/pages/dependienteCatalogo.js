import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Typography from '@material-ui/core/Typography';

//Redux stuff
import { connect } from 'react-redux';
import { loadProductos } from '../redux/actions/dataActions' 

//Componentes
import Topbar from '../components/Topbar';
import TablaCatalogoProductosDesplegable from '../components/TablaCatalogoProductosDesplegable';


const style = {

    tituloCatalogo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600
      }
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
                <Typography variant='h3' className={classes.tituloCatalogo}>CATÁLOGO DE PRODUCTOS</Typography>
                
                <div className={classes.main}>
                    {data.length === 0? null:<TablaCatalogoProductosDesplegable data = {data}/>}
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

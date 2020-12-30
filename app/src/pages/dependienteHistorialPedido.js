import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

//Redux stuff
import { connect } from 'react-redux';
import { loadPedidos } from '../redux/actions/dataActions'

//Components
import Topbar from '../components/Topbar';
import TablaPedidosDesplegable from '../components/TablaPedidosDesplegable';

const style = {

}

class dependienteHistorialPedido extends Component {

    constructor(props){
        super(props);
        this.state = {
            data: []            
        }
    }

    componentDidMount(){
        this.props.loadPedidos();
    }

    render() {
        const {classes, data} = this.props;
        return (
            <div>
                <Topbar/>
                <div className={classes.main}>
                    {data.length === 0?null:data.pedidos.map((row) => 
                        <TablaPedidosDesplegable datos = {row} />
                    ) }
                </div>
            </div>
        )
    }
}
/*
<TablaPedidosDesplegable datos = {[data]} />
{
      props.datos[0].pedidos.map((row) => {
        {console.log(row)}
        <p>hola</p>
      })
      }
    */
dependienteHistorialPedido.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadPedidos: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadPedidos
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteHistorialPedido))

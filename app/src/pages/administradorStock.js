import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
//Redux stuff
import { connect } from 'react-redux';
import { loadAlmacenGestion } from '../redux/actions/dataActions';
//Components
import AccordionGestionStock from '../components/AccordionGestionStock';

const style = {

}

class administradorStock extends Component {
    constructor(props){
        super(props);
        this.state ={
            data: []
        }
    }

    componentDidMount(){
        this.props.loadAlmacenGestion();
    }

    render() {
        const almacenes = this.props.data.gestionAlmacen;
        return (
            
            
            <div>
                {almacenes === undefined? null:<AccordionGestionStock almacenes = {almacenes}/>}
            </div>
        )
    }
}

administradorStock.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadAlmacenGestion: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadAlmacenGestion
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorStock))
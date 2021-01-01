import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

//Redux stuff
import { connect } from 'react-redux';
import { loadVehiculos } from '../redux/actions/dataActions'

//Componentes
import Topbar from '../components/Topbar';


const style = {

}

class transportistaVehiculos extends Component {
    
    constructor(props){
        super(props);
        this.state ={
            data: []
        }
    }

    componentDidMount(){
        this.props.loadVehiculos();
    }

    render() {
        return (
            <div>
                <Topbar/>
            </div>
        )
    }
}

transportistaVehiculos.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadVehiculos: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadVehiculos
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(transportistaVehiculos))

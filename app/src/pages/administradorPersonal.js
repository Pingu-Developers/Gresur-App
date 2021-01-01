import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

//Redux stuff
import { connect } from 'react-redux';
import { loadPersonal } from '../redux/actions/dataActions'

//Components
import Topbar from '../components/Topbar';
import TablaPersonalDesplegable from '../components/TablaPersonalDesplegable';
import BotonNuevoEmpleado from '../components/BotonNuevoEmpleado'



const style = {


}

export class administradorPersonal extends Component {
    constructor(props){
        super(props);
        this.state = {
            data:[]
        }
    }
    componentDidMount(){
        this.props.loadPersonal();
    }

    render() {
        const {classes, data} = this.props;
        return (
           <div>
                <Topbar/>
                <div>
                    <BotonNuevoEmpleado align="right"></BotonNuevoEmpleado>
                </div>                  
                <div>
                    <TablaPersonalDesplegable datos = {data.personal}  />  
                </div>
            </div>
        )
    }
}      

administradorPersonal.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadPersonal: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
    
})

const mapActionsToProps = {
    loadPersonal
}

export default connect(mapStateToProps, mapActionsToProps)(administradorPersonal)

import React, { Component } from 'react';
import PropTypes from 'prop-types';

//Redux stuff
import { connect } from 'react-redux';
import { loadPersonalContrato} from '../redux/actions/dataActions';


//Components
import TablaPersonalDesplegable from '../components/TablaPersonalDesplegable';
import PopUpNuevoEmpleado from '../components/PopUpNuevoEmpleado'

export class administradorPersonal extends Component {
    constructor(props){
        super(props);
        this.state = {
            data:[]
        }
    }

    componentDidMount(){
        this.props.loadPersonalContrato();
    }
    render() {
        const {classes, data} = this.props;
        return (
           <div>
                <div>
                    <PopUpNuevoEmpleado/>
                </div>
                          
                <div>
                    {data.personal===undefined?null:
                    <TablaPersonalDesplegable datos = {data.contrato} />  
                    }
                </div>
            </div>
        )
    }
}      

administradorPersonal.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadPersonalContrato: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    data: state.data,
})

const mapActionsToProps = {
    loadPersonalContrato
}

export default connect(mapStateToProps, mapActionsToProps)(administradorPersonal)
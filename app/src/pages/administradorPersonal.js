import React, { Component } from 'react';
import PropTypes from 'prop-types';

//Redux stuff
import { connect } from 'react-redux';
import { loadPersonalContrato, clear} from '../redux/actions/dataActions';


//Components
import TablaPersonalDesplegable from '../components/TablaPersonalDesplegable';
import PopUpNuevoEmpleado from '../components/PopUpNuevoEmpleado';
import SnackCallController from '../components/SnackCallController';


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

    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const {classes, data,UI:{errors,enviado}} = this.props;
        return (
           <div>
                <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
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
    UI: state.UI
})

const mapActionsToProps = {
    loadPersonalContrato,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(administradorPersonal)
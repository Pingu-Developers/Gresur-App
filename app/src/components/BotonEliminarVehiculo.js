import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import { Button } from '@material-ui/core';
import DeleteIcon from '@material-ui/icons/Delete';
import withStyles from '@material-ui/core/styles/withStyles';


import { deleteVehiculo } from '../redux/actions/dataActions';

const styles = theme => ({
})


const initialState = {
}

export class BotonEliminarVehiculo extends Component {

    constructor(props){
        super(props);
        this.state = initialState;

    }

    handleSubmit(event,matricula) {
        event.preventDefault();
        this.props.deleteVehiculo(matricula);
        //PONER A NO DISPONIBLE EN VEZ DE BORRAR YA QUE PETA SI TIENE PEDIDOS ASOCIADOS Y NO SE PUEDE BORRAR
    }


    render() {
    const {classes}= this.props;
    const matricula = this.props.datos.matricula;

        return (
            <div>
                <Button className={classes.boton} startIcon={<DeleteIcon />} variant='contained' color='secondary' onClick={(event) => this.handleSubmit(event,matricula)}>Borrar vehiculo</Button>
            </div>
        )
    }
}

BotonEliminarVehiculo.propTypes={
    classes: PropTypes.object.isRequired,
    deleteVehiculo: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
})

const mapActionsToProps = {
    deleteVehiculo
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(styles)(BotonEliminarVehiculo))

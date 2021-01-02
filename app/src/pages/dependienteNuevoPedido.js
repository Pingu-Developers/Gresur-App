import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import Stepper from '../components/Stepper';

export class dependienteNuevoPedido extends Component {
    static propTypes = {
        prop: PropTypes
    }

    handleSubmit(){
        console.log('aoskljsjlñxc<llxcñ');
    }

    render() {
        return (
            <div>
                <Stepper 
                    opcionales = {[2]}
                    stepTitles = {['Datos del cliente', 'Selección de productos', 'Datos de envío', 'Resumen del pago']}
                    onSubmit = {this.handleSubmit}
                >
                    <div>
                        <fieldset>
                            <legend>Datos de cliente</legend>
                            <h1>Aqui va el form con cosas del cliente</h1>
                        </fieldset>
                    </div>
                    <div>
                        <fieldset>
                            <legend>Seleccion de productos</legend>
                            <h1>Aqui va otra cosa</h1>
                        </fieldset>
                    </div>
                    <div>
                        <fieldset>
                            <legend>Datos de envío</legend>
                            <h1>Aqui va el form con cosas del envio</h1>
                        </fieldset>
                    </div>
                    <div>
                        <fieldset>
                            <legend>Datos de envío</legend>
                            <h1>Aqui va la factura</h1>
                        </fieldset>
                    </div>
                </Stepper>
            </div>
        )
    }
}
dependienteNuevoPedido.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    
}

export default connect(mapStateToProps, mapActionsToProps)(dependienteNuevoPedido)


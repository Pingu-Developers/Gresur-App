import React, { Component } from 'react'
import { connect } from 'react-redux'
import { TableContainer } from '@material-ui/core';
import PropTypes from 'prop-types';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import SaveIcon from '@material-ui/icons/Save';
import DialogoAddProductos from '../Dialogs/DialogoAddProductos';
import {rectificaFactura} from '../../redux/actions/dataActions';

function getImporte(cantidad, precioUnitario) {
    const v1 = parseFloat(cantidad);
    const v2 = parseFloat(precioUnitario);
    const importe = (v1 * v2).toFixed(2);
    return importe;
}

Array.prototype.remove = function() {
    var what, a = arguments, L = a.length, ax;
    while (L && this.length) {
        what = a[--L];
        while ((ax = this.indexOf(what)) !== -1) {
            this.splice(ax, 1);
        }
    }
    return this;
};



export class TablaEditarFactura extends Component {
    constructor(props) {
        super(props);
        const numFactura = Object.assign({}, props.factura.numFactura)
        const factura = Object.assign({}, props.factura)
        const lineasFactura = Object.assign([], props.factura.lineasFacturas)
        this.state = {
            numFactura: numFactura,
            factura: factura,
            lineasFactura: lineasFactura,
        }
        this.handleOnChange = this.handleOnChange.bind(this);
        this.handleAnadir = this.handleAnadir.bind(this);
        this.handleEnviar = this.handleEnviar.bind(this);
    }

    componentDidUpdate(prevProps, prevState){
        if(prevProps.factura != this.props.factura){
            const numFactura = Object.assign({}, this.props.factura.numFactura)
            const factura = Object.assign({}, this.props.factura)
            const lineasFactura = Object.assign([], this.props.factura.lineasFacturas)
            this.setState({
                numFactura: numFactura,
                factura: factura,
                lineasFactura: lineasFactura,
            })
            
        }
    }

    handleEnviar = (event) => {
        const factura = this.state.factura;
        factura.lineasFacturas = this.state.lineasFactura;
        this.props.rectificaFactura(this.state.factura);
    }

    handleAnadir = (linea) => {
        const temp = this.state.lineasFactura.filter(obj => obj.producto.id==linea.producto.id);
        if(temp.length==0){
        this.setState(state => ({
            lineasFactura:[...state.lineasFactura, linea]
        }))
        }
    }

    handleOnChange = (event) => {
        console.log('Este es el id');
        console.log(event.target.id);
        //Extraemos la linea de factura
        const id = parseInt(event.target.id);
        const lineasFactura = this.state.lineasFactura;
        const newLineaFactura = lineasFactura[event.target.id];
        console.log('Linea antes');
        console.log(newLineaFactura);
        console.log('Cantidad antes');
        console.log(newLineaFactura.cantidad);
        console.log('Array antes');
        console.log(lineasFactura);


        //Actualizamos la linea
        var precioUnitario = 0;
        if(this.props.factura.lineasFacturas[id]){
            precioUnitario = (parseFloat(this.props.factura.lineasFacturas[id].precio)/parseFloat(this.props.factura.lineasFacturas[id].cantidad)).toFixed(2);
        } else{
            precioUnitario = newLineaFactura.producto.precioVenta;
        }
        newLineaFactura.cantidad = parseInt(event.target.value);
        newLineaFactura.precio = parseFloat(newLineaFactura.cantidad*precioUnitario);   
        


        console.log('Linea despues');
        console.log(newLineaFactura);
        console.log('Cantidad despues');
        console.log(newLineaFactura.cantidad);


        //La reemplazamos en el state   
        lineasFactura.splice(id, 1, newLineaFactura);
        console.log('Array despues')     
        console.log(lineasFactura);
        console.log('Cantidad despues');
        console.log(lineasFactura[id].cantidad);
        console.log('Estado antes');
        console.log(this.state);

        this.setState({
            lineasFactura: lineasFactura
        })

        console.log('Estado despues');
        console.log(this.state);
    };

    render() {
        console.log("LINEAS FACTURA")
        console.log(this.state.lineasFactura)
        //IMPORTANTE: El precio en lineaFactura se refiere al precioUnitario*cantidad
        return (            
            <div>
                <TableContainer>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><b>ID</b></TableCell>
                                <TableCell><b>Imagen</b></TableCell>
                                <TableCell><b>Cantidad</b></TableCell>
                                <TableCell><b>Producto</b></TableCell>
                                <TableCell><b>Precio unitario</b></TableCell>
                                <TableCell><b>Importe</b></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.lineasFactura.map((linea) => linea.cantidad == 0?null:
                                <TableRow>
                                    <TableCell>{linea.producto.id}</TableCell>
                                    <TableCell><img width="auto" height="50px" src={linea.producto.urlimagen}></img></TableCell>
                                    <TableCell>
                                        <TextField onChange={this.handleOnChange} value={parseInt(linea.cantidad)} required margin="normal" id={this.state.lineasFactura.indexOf(linea)} name="cantidad" label="cantidad" type="number" min="0">
                                        </TextField>
                                    </TableCell>
                                    <TableCell>{linea.producto.nombre}</TableCell>
                                    <TableCell>
                                        {this.props.factura.lineasFacturas[this.state.lineasFactura.indexOf(linea)] && this.props.factura.lineasFacturas[this.state.lineasFactura.indexOf(linea)] != -1?
                                        (parseFloat(this.props.factura.lineasFacturas[this.state.lineasFactura.indexOf(linea)].precio)/parseFloat(this.props.factura.lineasFacturas[this.state.lineasFactura.indexOf(linea)].cantidad)).toFixed(2)
                                        :linea.producto.precioVenta
                                    }</TableCell>
                                    <TableCell>{parseFloat(linea.precio).toFixed(2)} €</TableCell>
                                </TableRow>
                            )}
                            <TableRow>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell><b>TOTAL: </b></TableCell>
                                <TableCell><b>{this.state.lineasFactura.map(x => x.precio).reduce((a, b) => a + b).toFixed(2)} €</b></TableCell>
                            </TableRow>

                        </TableBody>

                    </Table>
                    <table class="default">
                        <tr>
                            <td><Button variant="contained" onClick={this.handleEnviar} color="primary" size="small" startIcon={<SaveIcon />}>Guardar</Button></td>
                            <td><DialogoAddProductos handleAdd={this.handleAnadir}/></td>
                            <td></td>
                        </tr>
                    </table>
                </TableContainer>

            </div>
        )
    }
}
TablaEditarFactura.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    rectificaFactura: PropTypes.func.isRequired,

}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    rectificaFactura


}

export default connect(mapStateToProps, mapActionsToProps)(TablaEditarFactura)

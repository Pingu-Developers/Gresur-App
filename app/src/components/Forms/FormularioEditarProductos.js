import React, { Component } from 'react'
import { connect } from 'react-redux'
import TextField from '@material-ui/core/TextField'
import { setProducto } from '../../redux/actions/dataActions'
import withStyles from '@material-ui/core/styles/withStyles';
import Button from '@material-ui/core/Button'
const styles = theme => ({
    formSpace: {
        marginRight: theme.spacing(2),
        marginLeft: theme.spacing(5)
    }
});


export class FormularioEditarProductos extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.producto.id,
            nombre: this.props.producto.nombre,
            descripcion: this.props.producto.descripcion,
            unidad: this.props.producto.unidad,
            stock: this.props.producto.stock,
            stockSeguridad: this.props.producto.stockSeguridad,
            precioVenta: this.props.producto.precioVenta,
            precioCompra: this.props.producto.precioCompra,
            alto: this.props.producto.alto,
            ancho: this.props.producto.ancho,
            profundo: this.props.producto.profundo,
            pesoUnitario: this.props.producto.pesoUnitario,
            urlimagen: this.props.producto.urlimagen,
            cerrar: true
        }
    }

    handleOnChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const producto = {
            id: this.state.id,
            nombre: this.state.nombre,
            descripcion: this.state.descripcion,
            unidad: this.state.unidad,
            stock: this.state.stock,
            stockSeguridad: this.state.stockSeguridad,
            precioVenta: this.state.precioVenta,
            precioCompra: this.state.precioCompra,
            alto: this.state.alto,
            ancho: this.state.ancho,
            profundo: this.state.profundo,
            pesoUnitario: this.state.pesoUnitario,
            urlimagen: this.state.urlimagen
        };
        this.props.setProducto(producto);
        this.props.cerrar();
    };

    render() {
        return (
            <div>
                {console.log(this.props.cerrar)}
                <form id="actualizarProducto" onSubmit={this.handleSubmit}>
                    <table>
                        <tr>
                            <td><TextField onChange={this.handleOnChange} value={this.state.nombre} required margin="normal" id="standard-required" name="nombre" label="Nombre" type="text" ></TextField></td>
                            <td colspan="2"><TextField onChange={this.handleOnChange} value={this.state.descripcion} required margin="normal" id="standard-full-width" name="descripcion" label="Descripcion" fullWidth></TextField></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><TextField onChange={this.handleOnChange} value={this.state.ancho} required margin="normal" id="standard-number" name="ancho" label="Ancho (m)" type="number" min="0"></TextField></td>
                            <td><TextField onChange={this.handleOnChange} value={this.state.alto} required margin="normal" id="standard-number" name="alto" label="Alto (m)" type="number" min="0" ></TextField></td>
                            <td><TextField onChange={this.handleOnChange} value={this.state.profundo} required margin="normal" id="standard-number" name="profundo" label="Profundo (m)" type="number" min="0"></TextField></td>
                        </tr>
                        <tr>
                            <td colSpan="3"><TextField onChange={this.handleOnChange} value={this.state.urlimagen} fullWidth margin="normal" id="standard-required" name="urlimagen" label="URL Imagen" ></TextField></td>
                        </tr>
                        <tr>
                            <td><TextField onChange={this.handleOnChange} value={this.state.stock} required margin="normal" id="standard-number" name="stock" label="Stock" type="number" min="0" ></TextField></td>
                            <td><TextField onChange={this.handleOnChange} value={this.state.stockSeguridad} required margin="normal" id="standard-number" name="stockSeguridad" label="Stock de seguridad" type="number" min="0"></TextField></td>
                            <td><TextField onChange={this.handleOnChange} value={this.state.pesoUnitario} required margin="normal" id="standard-number" name="pesoUnitario" label="Peso unitario (kg)" type="number" min="0"></TextField></td>
                        </tr>
                        <tr>
                            <td><TextField onChange={this.handleOnChange} value={this.state.precioCompra} required margin="normal" id="standard-number" name="precioCompra" label="Precio de Compra (€)" type="number" min="0"></TextField></td>
                            <td><TextField onChange={this.handleOnChange} value={this.state.precioVenta} required margin="normal" id="standard-number" name="precioVenta" label="Precio de Venta (€)" type="number" min="0"></TextField></td>
                        </tr>

                    </table>
                    <div>
                        <Button type="submit" variant="contained" color="primary">Guardar cambios</Button>
                    </div>
                </form>
            </div>


        )
    }
}



const mapStateToProps = (state) => ({

})

const mapDispatchToProps = {
    setProducto
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(styles)(FormularioEditarProductos))

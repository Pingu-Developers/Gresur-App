import React, { Component } from 'react';
import { connect } from 'react-redux';

import withStyles from '@material-ui/core/styles/withStyles';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Typography from '@material-ui/core/Typography';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';


import Stepper from '../components/Stepper';


const style = {
    tituloNuevoPedido: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        width: '100%'
    },
    container: {
        padding: '0 30px',
    },
    fieldset: {
        borderRadius: 10,
        backgroundColor: '#f7f7f7',
        color: '#3d3d3d'
    },
    input: {
        width: '40%',
        marginLeft: 30,
    },
    spanInputs : {
        display: 'flex',
        justifyContent: 'space-between',
        margin: '15px 0 15px 0',
        padding: '0 40px 0 10px',
    },
    NIF: {
        width: '20%',
    },
    provincia: {
        width: '25%'
    },
    municipio: {
        width: '20%',
    },
    cp: {
        width: '10%',
    },
    emailTlfRadioContainer:{
        display: 'inline-flex',
        width: '100%',
        justifyContent: 'space-between'
    },
    emailTlfContainer:{
        width: '25%'
    },
    radioGroup: {
        borderRadius: 5,
        marginRight: 40,
        paddingBottom: 10,
        height: 'min-content',
        marginTop: 20,
    },
    RadioFormControl: {
        width: '100%'
    },
    email: {
        width: '100%'
    },
    telefono: {
        width: '100%'
    },
    
    
}
export class dependienteNuevoPedido extends Component {
    
    constructor(){
        super();
        this.state = {
            errors : null,
            valueRadio: null,
        }
    }
    componentDidMount(){
        this.setState({valueRadio : 'Pago directo'})
    }

    handleChangeRadio = (event) => {
        this.setState({valueRadio : event.target.value});
    };

    handleSubmit(){
        console.log('aoskljsjlñxc<llxcñ');
    }

    render() {
        const {classes, valueRadio, errors} = this.props;
        return (
            <div>
                {console.log(valueRadio)}
                <Typography variant='h3' className={classes.tituloNuevoPedido}>GENERAR UN NUEVO PEDIDO</Typography><br/>
                <Stepper 
                    opcionales = {[2]}
                    stepTitles = {['Datos del cliente', 'Selección de productos', 'Datos de envío', 'Resumen del pago']}
                    onSubmit = {this.handleSubmit}
                >
                    <div className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Introduzca los datos de cliente</legend>
                            <div>
                                <span className = {classes.spanInputs}>
                                    <TextField
                                    error = {errors ? true:false}
                                    required
                                    label="Nombre, Apellidos"
                                    helperText={errors}
                                    className = {[classes.input, classes.nombreApellidos].join(" ")}/>
                                    
                                    <TextField
                                    error = {errors ? true:false}
                                    required
                                    label="NIF"
                                    helperText={errors}
                                    className = {[classes.input, classes.NIF].join(" ")}/>
                                </span>

                                <span className = {classes.spanInputs}>
                                    <TextField
                                    error = {errors ? true:false}
                                    required
                                    label="Dirección"
                                    helperText={errors}
                                    className = {[classes.input, classes.direccion].join(" ")}/>
                                    
                                    <Autocomplete
                                    options={provincias}
                                    getOptionLabel={(option) => option.title}
                                    renderInput={(params) => <TextField {...params} label="Provincia" required/>}
                                    className = {[classes.input, classes.provincia].join(" ")}/>

                                    <TextField
                                    error = {errors ? true:false}
                                    required
                                    label="Municipio"
                                    helperText={errors}
                                    className = {[classes.input, classes.municipio].join(" ")}/>
           
                                    <TextField
                                    error = {errors ? true:false}
                                    required
                                    label="CP"
                                    helperText={errors}
                                    className = {[classes.input, classes.cp].join(" ")}/>
                                </span>
                                
                                <div className={classes.emailTlfRadioContainer}>
                                    <span className={classes.emailTlfContainer}>
                                        <span className = {classes.spanInputs}>
                                            <TextField
                                            error = {errors ? true:false}
                                            required
                                            label="e-mail"
                                            helperText={errors}
                                            className = {[classes.input, classes.email].join(" ")}/>
                                        </span>

                                        <span className = {classes.spanInputs}>
                                            <TextField
                                            error = {errors ? true:false}
                                            required
                                            label="telefono"
                                            helperText={errors}
                                            className = {[classes.input, classes.telefono].join(" ")}/>
                                        </span>
                                    </span>

                                    <fieldset className = {classes.radioGroup}>
                                        <legend>Pago</legend>
                                        <RadioGroup row
                                        aria-label="position" 
                                        name="position" 
                                        onChange={this.handleChangeRadio} 
                                        value={valueRadio}
                                        >
                                            <FormControlLabel
                                            className = {classes.RadioFormControl}
                                            value={"Pago directo"}
                                            control={<Radio color="primary"/>}
                                            label="Pago directo"
                                            labelPlacement="End"
                                            />
                                            <FormControlLabel
                                            className = {classes.RadioFormControl}
                                            value={"Pendiente de pago"}
                                            control={<Radio color="primary" />}
                                            label="Pendiente de pago"
                                            labelPlacement="End"
                                            />
                                        </RadioGroup>
                                    </fieldset>
                                </div>
                            </div><br/>
                        </fieldset>
                    </div>

                    <div className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Seleccione los productos</legend>
                            <h1>Aqui va otra cosa</h1>
                            <br/>
                        </fieldset>
                    </div>

                    <div className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Introduzca los datos de envío</legend>
                            <h1>Aqui va el form con cosas del envio</h1>
                            <br/>
                        </fieldset>
                    </div>

                    <div className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Resumen del pedido</legend>
                            <h1>Aqui va la factura</h1>
                            <br/>
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
    errors : state.errors,
    valueRadio: state.valueRadio
})

const mapActionsToProps = {
    
}
const  provincias = [{title : 'Alava'},{title : 'Albacete'},{title : 'Alicante'},{title : 'Almería'},{title : 'Asturias'},{title : 'Avila'},
                     {title : 'Badajoz'},{title : 'Barcelona'},{title : 'Burgos'},{title : 'Cáceres'},{title : 'Cádiz'},
                     {title : 'Cantabria'},{title : 'Castellón'},{title : 'Ciudad Real'},{title : 'Córdoba'},{title : 'La Coruña'},
                     {title : 'Cuenca'},{title : 'Gerona'},{title : 'Granada'},{title : 'Guadalajara'}, {title : 'Guipúzcoa'},
                     {title : 'Huelva'},{title : 'Huesca'},{title : 'Islas Baleares'},{title : 'Jaén'},{title : 'León'},{title : 'Lérida'},
                     {title : 'Lugo'},{title : 'Madrid'},{title : 'Málaga'},{title : 'Murcia'},{title : 'Navarra'}, {title : 'Orense'},
                     {title : 'Palencia'},{title : 'Las Palmas'},{title : 'Pontevedra'},{title : 'La Rioja'},{title : 'Salamanca'},{title : 'Segovia'},
                     {title : 'Sevilla'},{title : 'Soria'},{title : 'Tarragona'}, {title : 'Santa Cruz de Tenerife'},{title : 'Teruel'},
                     {title : 'Toledo'},{title : 'Valencia'},{title : 'Valladolid'},{title : 'Vizcaya'},{title : 'Zamora'},{title : 'Zaragoza'}]

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteNuevoPedido))
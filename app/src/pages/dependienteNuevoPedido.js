import React, { Component } from 'react';
import { connect } from 'react-redux';

import withStyles from '@material-ui/core/styles/withStyles';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Typography from '@material-ui/core/Typography';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';

import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import DoneIcon from '@material-ui/icons/Done';
import Button from '@material-ui/core/Button';

import Stepper from '../components/Stepper';
import { loadClienteIsDefaulter, clearClienteIsDefaulter } from '../redux/actions/dataActions';
import Snackbar from '../components/SnackBar'


const style = {
    tituloNuevoPedido: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        width: '100%'
    },
    buttonDiv: {
        display: 'flex',
        justifyContent: 'space-between',
        width: '100%',
    },
    button: {
        borderRadius: '100%',
        height: 55,
        width: 50,
        marginRight: 0,
        marginTop: -30,
        color: 'white',
        '&$disabled' : {
            backgroundColor: '#f2f2f2',
            color: 'white',
            border: '1px solid #dbdbdb'
        }
    },
    disabled: {},

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
    
    constructor(props){
        super(props);
        this.state = {
            errors : {'nombreApellidos' : [], 'NIF': [], 'direccion': [], 'provincia': [] , 'municipio': [], 'CP': [], 'email':[], 'telefono':[]},
            valueRadio: '',
            nombreApellidos: '',
            NIF: '',
            direccion: '',
            provincia: '',
            municipio: '',
            CP: '',
            email: '',
            telefono: '',
        }
    }
    componentDidMount(){
        this.setState({valueRadio : 'Pago directo'})
    }
  
    componentDidUpdate(){
        
        if(this.props.data.isDefaulter){
            let hayMasErrores = this.hayErrores();
            let errores = {...this.state.errors} ;
            errores['NIF'].push('defoulter')
            this.setState({errors : errores})
            document.getElementById("botonSnack")? document.getElementById("botonSnack")?.click() : 
                                                   console.log('No se encuentra boton para abrir la snackbar');
            this.props.clearClienteIsDefaulter()
            if(!hayMasErrores)
                document.getElementById('backButton').click();
        }
    }

    handleChangeRadio = (event) => {
        this.setState({valueRadio : event.target.value});
    };

    hayErrores(){
        let res = false;
        Object.keys(this.state.errors).map((key) => {res = res || this.state.errors[key].length !== 0})
        return res;
    }

    back(event){
        event.preventDefault()
        document.getElementById('backButton').click();
    }

    validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    validatePage(event, step){
        event.preventDefault()
        this.props.clearClienteIsDefaulter()
        let errores = {...this.state.errors} ;
        switch(step){
            case 0:{
                this.props.loadClienteIsDefaulter(this.state.NIF)
                if(this.state.nombreApellidos === '')
                    errores['nombreApellidos'].push('No puede ser vacio')
                if(this.state.nombreApellidos.length < 3 || this.state.nombreApellidos.length > 50)
                    errores['nombreApellidos'].push('Debe estar entre 3 y 50 caracteres')
                if(this.state.NIF === '')
                    errores['NIF'].push('No puede ser vacio')
                if(!this.state.NIF.match(/^(\d{8})([A-Z])$/))
                    errores['NIF'].push('Formato invalido')
                if(this.state.direccion === '')
                    errores['direccion'].push('No puede ser vacio')
                if(this.state.direccion.length < 3 || this.state.direccion.length > 100)
                    errores['direccion'].push('Debe estar entre 3 y 50 caracteres')
                if(this.state.provincia === '' || this.state.provincia === 0)
                    errores['provincia'].push('No puede ser vacio')
                if(this.state.municipio === '')
                    errores['municipio'].push('No puede ser vacio')
                if(this.state.CP === '')
                    errores['CP'].push('No puede ser vacio')
                if(!this.state.CP.match(/^\d{5}$/))
                    errores['CP'].push('formato invalido')
                if(this.state.email === '')
                    errores['email'].push('No puede ser vacio')
                if(!this.validateEmail(this.state.email))
                    errores['email'].push('formato invalido')
                if(this.state.telefono === '')
                    errores['telefono'].push('No puede ser vacio')
                if(!this.state.telefono.match(/^\d{9}$/))
                    errores['telefono'].push('formato invalido')
                break
                }
            default:{
                console.error('QUE HACES NO INVENTES QUE ESTA YA TODO INVENTAO')
            }
        }
        this.setState({errors : errores})
        if(!this.hayErrores()){
            document.getElementById('nextButton').click()
        }
    }

    onChangeInput(event, name, value){
        event.preventDefault();
        if(this.state.errors[name][0] || this.state.errors[name][0] === ''){
            let errores = {...this.state.errors};
            errores[name] = []
            this.setState({errors: errores})
        }
        this.setState({[name] : value});
    }

    render() {
        const { classes, data } = this.props;
        let changing = '';
        let inputValue = null;
        return (
            <div>
                <Snackbar type = "error" open = {data.isDefaulter} message= 'Este cliente tiene impagos!'/>
                <Button id = 'onChangeInput' onClick = {(event) => {this.onChangeInput(event, changing, inputValue)}} style={{display : 'none'}}></Button>
                <Typography variant='h3' className={classes.tituloNuevoPedido}>GENERAR UN NUEVO PEDIDO</Typography><br/>
                <Stepper 
                    opcionales = {[2]}
                    stepTitles = {['Datos del cliente', 'Selección de productos', 'Datos de envío', 'Resumen del pago']}
                >
                    <form className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Introduzca los datos de cliente</legend>
                            <div>
                                <span className = {classes.spanInputs}>
                                    <TextField
                                    value={this.state.nombreApellidos}
                                    error = {this.state.errors['nombreApellidos'].length !== 0 ? true:false}
                                    required
                                    label="Nombre, Apellidos"
                                    helperText={this.state.errors['nombreApellidos'][0]}
                                    className = {[classes.input, classes.nombreApellidos].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'nombreApellidos'; 
                                                document.getElementById('onChangeInput').click()}}/>
                                    
                                    <TextField
                                    value={this.state.NIF}
                                    error = {this.state.errors['NIF'].length !== 0 ? true:false}
                                    required
                                    label="NIF"
                                    helperText={this.state.errors['NIF'][0]}
                                    className = {[classes.input, classes.NIF].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'NIF'; 
                                                document.getElementById('onChangeInput').click()}}/>
                                </span>

                                <span className = {classes.spanInputs}>
                                    <TextField
                                    value={this.state.direccion}
                                    error = {this.state.errors['direccion'].length !== 0 ? true:false}
                                    required
                                    label="Dirección"
                                    helperText={this.state.errors['direccion'][0]}
                                    className = {[classes.input, classes.direccion].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'direccion'; 
                                    document.getElementById('onChangeInput').click()}}/>     

                                    <Autocomplete
                                    options={provincias}
                                    getOptionLabel={(option) => option}
                                    value = {this.state.provincia}
                                    inputValue = {this.state.provincia}
                                    renderInput={(params) => 
                                                            <TextField 
                                                                {...params}
                                                                label="Provincia"
                                                                required
                                                                error = {this.state.errors['provincia'].length !== 0 ? true:false}
                                                                helperText = {this.state.errors['provincia'][0]}/>}                                
                                    className = {[classes.input, classes.provincia].join(" ")}
                                    onChange = {(event, value) => {inputValue = value; changing = 'provincia'; 
                                                           document.getElementById('onChangeInput').click()}}
                                    onInputChange = {(event, value) => {inputValue = value; changing = 'provincia'; 
                                                           document.getElementById('onChangeInput').click()}}/>
                                                           

                                    <TextField
                                    value = {this.state.municipio}
                                    error = {this.state.errors['municipio'].length !== 0 ? true:false}
                                    required
                                    label="Municipio"
                                    helperText={this.state.errors['municipio'][0]}
                                    className = {[classes.input, classes.municipio].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'municipio'; 
                                                document.getElementById('onChangeInput').click()}}/>  

                                    <TextField
                                    value = {this.state.CP}
                                    error = {this.state.errors['CP'].length !== 0 ? true:false}
                                    required
                                    label="CP"
                                    helperText={this.state.errors['CP'][0]}
                                    className = {[classes.input, classes.cp].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'CP'; 
                                                document.getElementById('onChangeInput').click()}}/>
                                </span>
                                
                                <div className={classes.emailTlfRadioContainer}>
                                    <span className={classes.emailTlfContainer}>
                                        <span className = {classes.spanInputs}>
                                            <TextField
                                            value = {this.state.email}
                                            error = {this.state.errors['email'].length !== 0 ? true:false}
                                            required
                                            label="e-mail"
                                            helperText={this.state.errors['email'][0]}
                                            className = {[classes.input, classes.email].join(" ")}
                                            onChange = {(event) => {inputValue = event.target.value; changing = 'email'; 
                                                        document.getElementById('onChangeInput').click()}}/>
                                        </span>

                                        <span className = {classes.spanInputs}>
                                            <TextField
                                            value = {this.state.telefono}
                                            error = {this.state.errors['telefono'].length !== 0 ? true:false}
                                            required
                                            label="telefono"
                                            helperText={this.state.errors['telefono'][0]}
                                            className = {[classes.input, classes.telefono].join(" ")}
                                            onChange = {(event) => {inputValue = event.target.value; changing = 'telefono'; 
                                                        document.getElementById('onChangeInput').click()}}/>
                                        </span>
                                    </span>

                                    <fieldset className = {classes.radioGroup}>
                                        <legend>Pago</legend>
                                        <RadioGroup row
                                        aria-label="position" 
                                        name="position" 
                                        onChange={this.handleChangeRadio} 
                                        value={this.state.valueRadio}
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

                        <div className = {classes.buttonDiv}>
                            <Button
                                variant = "contained"
                                color = "primary"
                                disabled
                                onClick={(e) => {this.back(e)}} 
                                classes={{root : classes.button, disabled: classes.disabled}}
                                style={{marginLeft: -24}}
                                >
                                <ArrowBackIosIcon/>
                            </Button>

                            <Button
                                variant="contained"
                                color="primary"
                                onClick={(e) => {this.validatePage(e,0)}}
                                classes={{root : classes.button, disabled: classes.disabled}}
                                disabled={this.hayErrores() ? true : false}
                                style={{marginRight: -24}}
                                >
                                <ArrowForwardIosIcon />
                            </Button>
                        </div>
                    </form>

                    <form className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Seleccione los productos</legend>
                            <h1>Aqui va otra cosa</h1>
                            <br/>
                        </fieldset>

                        <div className = {classes.buttonDiv}>
                            <Button
                                variant = "contained"
                                color = "primary"
                                onClick={(e) => {this.back(e)}} 
                                classes={{root : classes.button, disabled: classes.disabled}}
                                style={{marginLeft: -24}}
                                >
                                <ArrowBackIosIcon/>
                            </Button>

                            <Button
                                variant="contained"
                                color="primary"
                                onClick={(e) => {this.validatePage(e,1)}}
                                classes={{root : classes.button, disabled: classes.disabled}}
                                disabled={this.hayErrores() ? true : false}
                                style={{marginRight: -24}}
                                >
                                <ArrowForwardIosIcon />
                            </Button>
                        </div>
                    </form>

                    <form className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Introduzca los datos de envío</legend>
                            <h1>Aqui va el form con cosas del envio</h1>
                            <br/>
                        </fieldset>

                        <div className = {classes.buttonDiv}>
                            <Button
                                variant = "contained"
                                color = "primary"
                                onClick={(e) => {this.back(e)}} 
                                classes={{root : classes.button, disabled: classes.disabled}}
                                style={{marginLeft: -24}}
                                >
                                <ArrowBackIosIcon/>
                            </Button>

                            <Button
                                variant="contained"
                                color="primary"
                                onClick={(e) => {this.validatePage(e,2)}}
                                classes={{root : classes.button, disabled: classes.disabled}}
                                disabled={this.hayErrores() ? true : false}
                                style={{marginRight: -24}}
                                >
                                <ArrowForwardIosIcon />
                            </Button>
                        </div>
                    </form>

                    <form className = {classes.container}>
                        <fieldset className = {classes.fieldset}>
                            <legend>Resumen del pedido</legend>
                            <h1>Aqui va la factura</h1>
                            <br/>
                        </fieldset>

                        <div className = {classes.buttonDiv}>
                            <Button
                                variant = "contained"
                                color = "primary"
                                onClick={(e) => {this.back(e)}} 
                                classes={{root : classes.button, disabled: classes.disabled}}
                                style={{marginLeft: -24}}
                                >
                                <ArrowBackIosIcon/>
                            </Button>

                            <Button
                                variant="contained"
                                color="primary"
                                onClick={(e) => {this.validatePage(e,3)}}
                                classes={{root : classes.button, disabled: classes.disabled}}
                                disabled={this.hayErrores() ? true : false}
                                style={{marginRight: -24}}
                                >
                                <DoneIcon />
                            </Button>
                        </div>
                    </form>

                </Stepper>
            </div>
        )
    }
}
dependienteNuevoPedido.propTypes = {

}

const mapStateToProps = (state) => ({
    data:state.data
})

const mapActionsToProps = {
    loadClienteIsDefaulter,
    clearClienteIsDefaulter
}

const provincias = ['Alava','Albacete','Alicante','Almería','Asturias','Avila','Badajoz','Barcelona','Burgos','Cáceres',
             'Cádiz','Cantabria','Castellón','Ciudad Real','Córdoba','La Coruña','Cuenca','Gerona','Granada','Guadalajara',
             'Guipúzcoa','Huelva','Huesca','Islas Baleares','Jaén','León','Lérida','Lugo','Madrid','Málaga','Murcia','Navarra',
             'Orense','Palencia','Las Palmas','Pontevedra','La Rioja','Salamanca','Segovia','Sevilla','Soria','Tarragona',
             'Santa Cruz de Tenerife','Teruel','Toledo','Valencia','Valladolid','Vizcaya','Zamora','Zaragoza']

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteNuevoPedido))
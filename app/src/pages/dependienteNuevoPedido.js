import React, { Component } from 'react';
import { connect } from 'react-redux';

import withStyles from '@material-ui/core/styles/withStyles';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Typography from '@material-ui/core/Typography';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import SyncAltIcon from '@material-ui/icons/SyncAlt';
import SearchIcon from '@material-ui/icons/Search';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import ClearIcon from '@material-ui/icons/Clear';
import DateFnsUtils from '@date-io/date-fns';
import { MuiPickersUtilsProvider, KeyboardDatePicker } from '@material-ui/pickers';

import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import DoneIcon from '@material-ui/icons/Done';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';

import Stepper from '../components/Stepper';
import Snackbar from '../components/SnackBar'
import NestedList from '../components/NestedList';

import { loadClienteIsDefaulter, clearClienteIsDefaulter, clear } from '../redux/actions/dataActions';
import { loadCliente, clearClienteByNIF } from '../redux/actions/dataActions';
import { loadProductos, clearProductos, loadProductosByNombre } from '../redux/actions/dataActions';
import { IconButton } from '@material-ui/core';
import SentimentVeryDissatisfiedIcon from '@material-ui/icons/SentimentVeryDissatisfied';

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
    seleccionProductosContainer : {
        marginTop: 15,
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
    },
    catalogoBusquedaContainer: {
        width: '45%',
    },
    searchProducto : {
        backgroundColor: 'white',
    },
    catalogoDeSeleccion: {
        backgroundColor: '#eaeaea',
        width: '100%',
        border: '1px solid #BDBDBD',
        borderRadius: 10,
        maxHeight: 400,
        overflowY: 'auto'
    },
    cestaDeCompra: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        width: '100%',
        minHeight: 380,
        maxHeight: 380,
        overflowY: 'auto',
        border: '1px solid #BDBDBD',
        borderRadius: 5,  
        backgroundColor: '#eaeaea'
    },
    opcionesEnvio : {
        marginLeft: 30,
        width: '30%',
        padding: '0 20px 0 20px',
        marginTop: 20,
    }
    
    
}
export class dependienteNuevoPedido extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            errors : {'nombreApellidos' : [], 'NIF': [], 'direccion': [], 'provincia': [] , 'municipio': [], 
                      'CP': [], 'email':[], 'telefono':[], 'direccionEnvio': [], 'provinciaEnvio': [] , 'municipioEnvio': [], 
                      'CPEnvio': [],'fechaEnvio':[], 'otros': []},
            warnings: {'stock' : []},
            valueRadio: '',
            nombreApellidos: '',
            NIF: '',
            direccion: '',
            provincia: '',
            municipio: '',
            CP: '',
            email: '',
            telefono: '',
            compraProductos: {},
            importeFactura: 0.0,
            recogeEnTienda: false,
            busquedaProducto: '',
            direccionEnvio: '',
            provinciaEnvio: '',
            municipioEnvio: '',
            CPEnvio: '',
            fechaEnvio: null,
            n : true,
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
        if(this.props.data.cliente !== null && this.props.data.cliente !== ''){
            let errores = {...this.state.errors}
            errores['nombreApellidos'] = []
            errores['NIF'] = []
            errores['direccion'] = []
            errores['provincia'] = []
            errores['municipio'] = []
            errores['CP'] = []
            errores['email'] = []
            errores['telefono'] = []
            errores['otros'] = []
            this.setState({nombreApellidos : this.props.data.cliente.name, 
                           direccion : this.props.data.cliente.direccion.split(',')[0].trim(),
                           municipio: this.props.data.cliente.direccion.split(',')[1].trim(),
                           provincia: this.props.data.cliente.direccion.split(',')[2].trim(),
                           CP: this.props.data.cliente.direccion.split(',')[3].trim(),
                           email: this.props.data.cliente.email,
                           telefono: this.props.data.cliente.tlf,
                           errors: errores})
            this.props.clearClienteByNIF()
        }
        if(document.getElementById('step2')){
            this.updateCompra();
        } if(document.getElementById('step2') 
                && Object.entries(this.props.data.productos).length === 0 
                && this.state.busquedaProducto === ''){ 
            this.props.loadProductos();
        }
        if(document.getElementById('skipButton') && this.state.recogeEnTienda){
            document.getElementById('skipButton').click()
        }
    }

    componentWillUnmount(){
        this.props.clear();
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

        let warns = {...this.state.warnings}

        if(document.getElementById('step4') && this.state.recogeEnTienda){
            document.getElementById('backButton').click();
        } else if(!document.getElementById('step4')){
            warns['stock'] = []
        }
        let errores = {...this.state.errors}
        Object.keys(errores).map((error) => {errores[error] = []})

        this.setState({errors : errores, warnings : warns})

        document.getElementById('updater').click()
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
        let warns = {...this.state.warnings} ;
        switch(step){
            case 0:{
                this.props.loadClienteIsDefaulter(this.state.NIF)
                if(this.state.nombreApellidos.trim() === '')
                    errores['nombreApellidos'].push('No puede ser vacio')
                if(this.state.nombreApellidos.trim().length < 3 || this.state.nombreApellidos.trim().length > 50)
                    errores['nombreApellidos'].push('Debe estar entre 3 y 50 caracteres')
                if(this.state.NIF.trim() === '')
                    errores['NIF'].push('No puede ser vacio')
                if(!this.state.NIF.trim().match(/^(\d{8})([A-Z])$/))
                    errores['NIF'].push('Formato invalido')
                if(this.state.direccion.trim() === '')
                    errores['direccion'].push('No puede ser vacio')
                if(this.state.direccion.trim().length < 3 || this.state.direccion.trim().length > 100)
                    errores['direccion'].push('Debe estar entre 3 y 50 caracteres')
                if(this.state.provincia.trim() === '' || this.state.provincia === 0)
                    errores['provincia'].push('No puede ser vacio')
                if(!provincias.includes(this.state.provincia.trim()))
                    errores['provincia'].push('Provincia inválida')
                if(this.state.municipio.trim() === '')
                    errores['municipio'].push('No puede ser vacio')
                if(this.state.CP.trim() === '')
                    errores['CP'].push('No puede ser vacio')
                if(!this.state.CP.trim().match(/^\d{5}$/))
                    errores['CP'].push('formato invalido')
                if(this.state.email.trim() === '')
                    errores['email'].push('No puede ser vacio')
                if(!this.validateEmail(this.state.email.trim()))
                    errores['email'].push('formato invalido')
                if(this.state.telefono.trim() === '')
                    errores['telefono'].push('No puede ser vacio')
                if(!this.state.telefono.trim().match(/^\d{9}$/))
                    errores['telefono'].push('formato invalido')
                break
                }
            case 1: {
                let steppers = document.getElementsByName('cantidadProducto')
                if(this.state.recogeEnTienda){
                    let tomorrow = new Date();
                    tomorrow.setDate(tomorrow.getDate() + 1)
                    this.setState({direccionEnvio:'Avenida Gresur edificio AG', fechaEnvio: tomorrow})
                }
                if(steppers.length === 0){
                    errores['otros'].push('No hay productos comprados')
                    document.getElementById('pedidoSinProductos') ? 
                                                    document.getElementById('pedidoSinProductos').click() : 
                                                    console.warn('No se encuentra la snackbar')
                } else {
                    for(let stepper of steppers){
                        if(stepper.valueAsNumber < stepper.min){
                            stepper.style.border = '1px solid red';
                            if(!errores['otros'].includes('stockProductos')){
                                errores['otros'].push('stockProductos')
                                document.getElementById('stockProductos') ?
                                                        document.getElementById('stockProductos').click() : 
                                                        console.warn('No se encuentra la snackbar')
                            }
                        } else if(stepper.valueAsNumber > parseInt(stepper.id.split(',')[2])){
                            if(!warns['stock'].includes('sinStockProductos')){
                                warns['stock'].push('sinStockProductos')
                                document.getElementById('sinStockProductos') ?
                                                            document.getElementById('sinStockProductos').click() : 
                                                            console.warn('No se encuentra la snackbar')
                            }
                        }
                    }
                }
                break
            }
            case 2: {
                var today = new Date();
                today.setHours(23,59,59,999)
                if(this.state.direccionEnvio.trim() === '')
                    errores['direccionEnvio'].push('No puede ser vacio')
                if(this.state.direccionEnvio.trim().length < 3 || this.state.direccionEnvio.trim().length > 100)
                    errores['direccionEnvio'].push('Debe estar entre 3 y 50 caracteres')
                if(this.state.provinciaEnvio.trim() === '' || this.state.provinciaEnvio === 0)
                    errores['provinciaEnvio'].push('No puede ser vacio')
                if(!provincias.includes(this.state.provinciaEnvio.trim()))
                    errores['provinciaEnvio'].push('Provincia inválida')
                if(this.state.municipioEnvio.trim() === '')
                    errores['municipioEnvio'].push('No puede ser vacio')
                if(this.state.CPEnvio.trim() === '')
                    errores['CPEnvio'].push('No puede ser vacio')
                if(!this.state.CPEnvio.trim().match(/^\d{5}$/))
                    errores['CPEnvio'].push('formato invalido')
                if(!this.state.fechaEnvio || this.state.fechaEnvio === null)
                    errores['fechaEnvio'].push('No puede ser vacio')
                if(!(today < this.state.fechaEnvio)){
                    errores['fechaEnvio'].push('Debe ser una fecha futura')
                }
                break
            }
            case 3: {
                break
            }
            default:{
                console.error('QUE HACES NO INVENTES QUE ESTA YA TODO INVENTAO')
            }
        }
        this.setState({errors : errores, warnings : warns})
        if(!this.hayErrores()){
            document.getElementById('nextButton').click()
        }
    }

    onChangeInput(event, name, value){
        event.preventDefault();
        
        if(this.state.errors[name] && (this.state.errors[name][0] || this.state.errors[name][0] === '')){
            let errores = {...this.state.errors};
            errores[name] = []
            this.setState({errors: errores})
        }if(name === 'NIF' && value.match(/^(\d{8})([A-Z])$/)){
            this.props.loadCliente(value);
        }    
        this.setState({[name] : value});
    }

    handleTransfer(event){
        event.preventDefault();

        //fuerza un update
        document.getElementById('updater').click()

        var elemento = event.currentTarget.parentNode.parentNode;
        var productoId = elemento.id.split(',')[0];
        var productoStock = elemento.id.split(',')[1];
        var precioVenta = elemento.id.split(',')[2];
        elemento = elemento.parentNode.cloneNode(true)
        var cesta = document.getElementById('cesta');

        if(cesta.children.length === 0 || cesta.children[0].style.display !== 'none'){
            cesta.children[0].style.display = 'none';
            cesta.style.display = "block";
        }
        //creamos el num stepper
        var numStepper = document.getElementById('numStepper').cloneNode(true)
        numStepper.id = productoId.toString() + ',' + precioVenta.toString() + ',' + productoStock;
        numStepper.onchange = (e) => {document.getElementById('updater').click()}
        numStepper.name = 'cantidadProducto'
        numStepper.style.display = 'inline-block'

        //desacticamos el boton de anadir si ya se ha añadido
        var btnAñadir = event.currentTarget;
        btnAñadir.disabled = true;
        btnAñadir.classList.add('Mui-disabled')

        //cambiamos el boton a un boton de eliminar
        var boton = elemento.children[0].children[3].children[0]
        var clearIcon = document.getElementById('clearIcon').cloneNode(true)
        boton.onclick = () => {elemento.remove(); 
                              btnAñadir.disabled = false; 
                              btnAñadir.classList.remove('Mui-disabled')
                              document.getElementById('updater').click()}
        boton.innerHTML = '';
        boton.id = 'eliminar'
        clearIcon.style.display = 'block'
        boton.appendChild(clearIcon);

        //añadimos el num stepper al elemento de la cesta
        elemento.style.padding = "10px 0 10px 0";
        elemento.style['background-color'] = 'white';
        elemento.style['border-bottom'] = '1px solid #bdbdbd';
        elemento.children[0].children[2].innerHTML = '<p style = "width: 100%"><b>Cantidad:</b></p>'
        elemento.children[0].children[2].style.display = "flex"
        elemento.children[0].children[2].style.padding = "0 20px 0 20px"
        elemento.children[0].children[2].style["align-items"] = "center"
        elemento.children[0].children[2].appendChild(numStepper)
        cesta.appendChild(elemento);
    }
    updateCompra(){
        var cesta = document.getElementById('cesta')

        if(Object.keys(this.state.compraProductos).length !== 0 && this.state.compraProductos.constructor === Object
           && (cesta.children.length === 0 || cesta.children.length === 1 && cesta.children[0].id === 'iconoCarrito' && cesta.children[0].style.display !== 'none')){
            Object.keys(this.state.compraProductos).map(
                (productoId) => {
                    if(document.getElementById(productoId) && !document.getElementById(productoId).disabled){
                        document.getElementById(productoId).click()
                        
                        var numSteppers = document.getElementsByName('cantidadProducto')

                        for(let stepper of numSteppers){
                            if(stepper.id.split(',')[0] === productoId){
                                stepper.value = this.state.compraProductos[productoId]
                            }
                        }
                }})
            
        }else{

            if(cesta.children.length === 1 && cesta.children[0].id === 'iconoCarrito' && cesta.children[0].style.display === 'none'){
                cesta.style.display = "flex";
                cesta.children[0].style.display = 'block';
            }
            if(cesta.children.length === 0 || cesta.children[0].style.display !== 'none'){
                this.setState(function (prevState, props) { 
                    if(prevState.importeFactura !== 0.0){
                        let errores = {...this.state.errors}
                        let compraProductosUpdater = {}
                        if(!errores['otros'].includes('No hay productos comprados'))
                            errores['otros'].push('No hay productos comprados')
                        return {importeFactura : 0.0, errors : errores, compraProductos : compraProductosUpdater}
                    }
                })
            } else{
                var updater = 0.0;
                var compraProductosUpdater = {}
                var numSteppers = document.getElementsByName('cantidadProducto')
                for(let numStepper of numSteppers){
                    //actualiza los steppers
                    if(numStepper.valueAsNumber < numStepper.min){
                        numStepper.value = numStepper.min
                    } else if(numStepper.valueAsNumber > parseInt(numStepper.id.split(',')[2])){
                        numStepper.style.border = '1px solid orange'
                    } else {
                        numStepper.style.border = '1px solid rgb(189, 189, 189)'
                    }
                    //actualiza el precio total
                    var productoId = numStepper.id.split(',')[0]
                    var precioVenta = parseFloat(numStepper.id.split(',')[1])
                    var cantidad = parseInt(numStepper.value)
                    updater += cantidad * precioVenta;

                    //actualiza los productos
                    compraProductosUpdater[productoId] = cantidad;
                }
                updater = updater.toFixed(2)
                this.setState(function (prevState, props) { 
                    if(prevState.importeFactura !== updater){
                        let errores = {...this.state.errors}
                        errores['otros'] = []
                        return {importeFactura : updater, errors : errores, compraProductos : compraProductosUpdater}
                    }
                })
            }
        }
    }
    handleChangeBuscar(event){
        event.preventDefault()
        this.setState({busquedaProducto: event.target.value});
    }
    buscarProducto(event){
        event.preventDefault()
        this.state.busquedaProducto==='' ? this.props.loadProductos(): this.props.loadProductosByNombre(this.state.busquedaProducto);
    }



    handleSubmit(){
        
        // ENVIAR NOTIFICACION DE LOS PRODUCTOS PEDIDOS QUE NECESITAN STOCK

        //IMPLEMENTAR LA COLA DE PEDIDOS

        //VALIDAR EN CONTROLADOR QUE LA FECHA SEA FUTURA

    }

    render() {
        const { classes, data } = this.props;
        let changing = '';
        let inputValue = null;
        return (
            <div>
                {/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                  * COSAS CON DISPLAY NONE PARA LOS SCRIPTS
                  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */}
                <TextField
                    style = {{ height:12 , display:"none"}}
                    id='numStepper'
                    type="number"
                    defaultValue = {1}
                    value = {1}
                    inputProps={{ min: 1, style:{padding:5, border: '1px solid #bdbdbd', backgroundColor:'white', borderRadius:10} }}
                    variant="outlined"
                />
                <ClearIcon id = 'clearIcon' style = {{display: 'none'}}/>
                <Snackbar type = "error" open = {data.isDefaulter} message= 'Este cliente tiene impagos!'/>
                <Snackbar 
                    type = "error" 
                    id = "pedidoSinProductos"
                    open = {this.state.errors['otros'].includes('No hay productos comprados')} 
                    message= 'No has seleccionado ningun producto!'
                />
                <Snackbar 
                    type = "error" 
                    id = "stockProductos"
                    open = {this.state.errors['otros'].includes('stockProductos')} 
                    message= 'La cantidad de alguno de los productos seleccionados es menor que 1'
                />
                <Snackbar 
                    type = "warning" 
                    id = "sinStockProductos"
                    open = {this.state.warnings['stock'].includes('sinStockProductos')} 
                    message= 'La cantidad de alguno de los productos seleccionados supera el stock disponible'
                />
                <Button id = 'onChangeInput' onClick = {(event) => {this.onChangeInput(event, changing, inputValue)}} style={{display : 'none'}}></Button>
                <Button id = 'updater' onClick = {(e) => {e.preventDefault(); this.setState({n:!this.state.n})}} style = {{display: 'none'}}/>
                
                {/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                  * COMIENZO DE LA VISTA 
                  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */}
                <Typography variant='h3' className={classes.tituloNuevoPedido}>GENERAR UN NUEVO PEDIDO</Typography><br/>
                <Stepper 
                    opcionales = {[2]}
                    stepTitles = {['1. Datos del cliente', '2. Selección de productos', '3. Datos de envío', '4. Resumen del pedido']}
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
                                    renderInput={(params) => 
                                                            <TextField 
                                                                {...params}
                                                                label="Provincia"
                                                                required
                                                                error = {this.state.errors['provincia'].length !== 0 ? true:false}
                                                                helperText = {this.state.errors['provincia'][0]}/>}                                
                                    className = {[classes.input, classes.provincia].join(" ")}
                                    onChange = {(event, value) => {inputValue = value; changing = 'provincia'; 
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

                    <form className = {classes.container} id = 'step2'>
                        <fieldset className = {classes.fieldset}>
                            <legend>Seleccione los productos</legend>
                            <div className = {classes.seleccionProductosContainer}>
                                <div className = {classes.catalogoBusquedaContainer}>
                                    <span style = {{display:'inline-flex', width: '100%', marginBottom: 10}}>
                                        <TextField 
                                            className={classes.searchProducto} 
                                            label="Buscar productos"
                                            variant="outlined"
                                            fullWidth
                                            size = "small"
                                            value={this.state.busquedaProducto}
                                            onChange={(event) => this.handleChangeBuscar(event)}
                                            onKeyPress={(event) => event.key==='Enter'? this.buscarProducto(event): null}
                                            InputProps = {{
                                                style : {padding : 0},
                                                endAdornment : (
                                                    <InputAdornment position = 'end'>
                                                        <IconButton
                                                            onClick = {(event) => {event.preventDefault();
                                                                                  this.setState({busquedaProducto : ''});}}
                                                            onMouseDown = {(event) => event.preventDefault()}>
                                                            <ClearIcon style = {{color : '#bdbdbd'}}/>
                                                        </IconButton>
                                                    </InputAdornment> )}
                                            }
                                            />
                                        <Button
                                            variant = "contained"
                                            onClick = {(event) => this.buscarProducto(event)}   
                                            color = "secondary"
                                            style = {{color: 'white'}}
                                        >
                                            <SearchIcon/>
                                        </Button>
                                    </span>

                                    <div className = {classes.catalogoDeSeleccion}>
                                        { Object.entries(data.productos).length === 0 && data.productos.constructor === Object ? 
                                        <div>
                                            <SentimentVeryDissatisfiedIcon size = 'large' style = {{color : '#bdbdbd', fontSize: 100, width: '100%', marginTop: 30}}/>
                                            <p style = {{textAlign: 'center', width: '100%', color: '#bdbdbd', fontSize: 30, fontWeight: 'bold'}}>No hay productos</p>
                                        </div> :
                                        <NestedList content = {data.productos} handleTransfer = {this.handleTransfer}/>
                                        }
                                    </div>
                                </div>

                                <SyncAltIcon size = {'large'} style = {{fontSize : 60}}/>

                                <div style = {{width : "45%"}}>
                                    <div className = {classes.cestaDeCompra} id = 'cesta'>
                                        <ShoppingCartIcon id = 'iconoCarrito' style = {{fontSize: 100, color: '#bdbdbd'}}/>
                                    </div>
                                
                                    <span style = {{display: 'inline-flex', justifyContent: 'space-between', width: '100%'}}>
                                        <FormControlLabel 
                                            control = {
                                                <Checkbox
                                                color="secondary"
                                                inputProps={{ 'aria-label': 'secondary checkbox' }}
                                                onClick = {(e) => {e.preventDefault(); this.setState({recogeEnTienda : !this.state.recogeEnTienda})}}
                                                checked = {this.state.recogeEnTienda}/>}
                                                label = "Recoge en tienda" />
                                        
                                        <h3 style= {{marginRight : 30}}>TOTAL: {this.state.importeFactura} €</h3>
                                    </span>
                                </div>
                                        
                            </div><br/>
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

                    <form className = {classes.container} id = 'step3'>
                        <fieldset className = {classes.fieldset}>
                            <legend>Introduzca los datos de envío</legend>
                            <span className = {classes.spanInputs}>
                                    <TextField
                                    value={this.state.direccionEnvio}
                                    error = {this.state.errors['direccionEnvio'].length !== 0 ? true:false}
                                    required
                                    label="Dirección de envío"
                                    helperText={this.state.errors['direccionEnvio'][0]}
                                    className = {[classes.input, classes.direccion].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'direccionEnvio'; 
                                                            document.getElementById('onChangeInput').click()}}/>     

                                    <Autocomplete
                                    options={provincias}
                                    getOptionLabel={(option) => option}
                                    value = {this.state.provinciaEnvio}
                                    renderInput={(params) => 
                                                            <TextField 
                                                                {...params}
                                                                label="Provincia"
                                                                required
                                                                error = {this.state.errors['provinciaEnvio'].length !== 0 ? true:false}
                                                                helperText = {this.state.errors['provinciaEnvio'][0]}/>}                                
                                    className = {[classes.input, classes.provincia].join(" ")}
                                    onChange = {(event, value) => {inputValue = value; changing = 'provinciaEnvio'; 
                                                                  document.getElementById('onChangeInput').click()}}/>
                                                           
                                    <TextField
                                    value = {this.state.municipioEnvio}
                                    error = {this.state.errors['municipioEnvio'].length !== 0 ? true:false}
                                    required
                                    label="Municipio"
                                    helperText={this.state.errors['municipioEnvio'][0]}
                                    className = {[classes.input, classes.municipio].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'municipioEnvio'; 
                                                            document.getElementById('onChangeInput').click()}}/>  

                                    <TextField
                                    value = {this.state.CPEnvio}
                                    error = {this.state.errors['CPEnvio'].length !== 0 ? true:false}
                                    required
                                    label="CP"
                                    helperText={this.state.errors['CPEnvio'][0]}
                                    className = {[classes.input, classes.cp].join(" ")}
                                    onChange = {(event) => {inputValue = event.target.value; changing = 'CPEnvio'; 
                                                            document.getElementById('onChangeInput').click()}}/>
                                </span>
                                
                                <span className = {classes.spanInputs}>
                                    <fieldset className = {classes.opcionesEnvio}>
                                        <legend>Opciones de envío</legend>
                                        <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                            <KeyboardDatePicker
                                            error = {this.state.errors['fechaEnvio'].length !== 0}
                                            helperText = {this.state.errors['fechaEnvio'][0]}
                                            className={classes.fechaEnvio}
                                            disableToolbar
                                            autoOk={true}
                                            variant="inline"
                                            format="dd/MM/yyyy"
                                            margin="normal"
                                            label="Fecha de envío"
                                            value = {this.state.fechaEnvio}
                                            onChange={(date)=>{inputValue = date; changing = 'fechaEnvio'
                                                                    document.getElementById('onChangeInput').click()}}
                                            KeyboardButtonProps={{
                                                'aria-label': 'change date',
                                            }}/>       
                                        </MuiPickersUtilsProvider>
                                        <p style = {{color : '#bdbdbd', textAlign: 'justify'}}>
                                            La fecha de envío es la fecha en la que el pedido pasa a disposición para ponerse
                                            en reparto, en condiciones normales el reparto se efectúa el mismo día que la fecha
                                            de envío indica, pero pueden producirse retrasos.
                                        </p>
                                    </fieldset>
                                    { this.state.warnings['stock'].includes('sinStockProductos') ?
                                    <p style = {
                                        {border : '2px solid orange', 
                                        borderRadius: 10,
                                        padding: 10,
                                        color: 'orange',
                                        textAlign: 'justify',
                                        height: 'min-content',
                                        width: '55%',
                                        marginTop: 26}}>
                                        Algunos de los productos selecionados no se encuentran actualmente en stock, 
                                        por lo que el envío se realizará tan pronto como se tenga stock de todos los productos solicitados
                                        a partir de la fecha de envío indicada. Recibirá una notificación cuando el pedido esté preparado.
                                    </p> : null
                                    }
                                </span>
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

                    <form className = {classes.container} id = 'step4'>
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
    clearClienteIsDefaulter,

    clear,
    loadCliente,
    clearClienteByNIF,

    loadProductos,
    clearProductos,
    loadProductosByNombre,
}

const provincias = ['Alava','Albacete','Alicante','Almería','Asturias','Avila','Badajoz','Barcelona','Burgos','Caceres',
             'Cadiz','Cantabria','Castellon','Ciudad Real','Cordoba','La Coruña','Cuenca','Gerona','Granada','Guadalajara',
             'Guipuzkoa','Huelva','Huesca','Islas Baleares','Jaen','Leon','Lerida','Lugo','Madrid','Málaga','Murcia','Navarra',
             'Orense','Palencia','Las Palmas','Pontevedra','La Rioja','Salamanca','Segovia','Sevilla','Soria','Tarragona',
             'Santa Cruz de Tenerife','Teruel','Toledo','Valencia','Valladolid','Vizcaya','Zamora','Zaragoza']

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteNuevoPedido))
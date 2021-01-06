import React, { Component } from 'react';
import { connect } from 'react-redux';

import withStyles from '@material-ui/core/styles/withStyles';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Typography from '@material-ui/core/Typography';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import SyncAltIcon from '@material-ui/icons/SyncAlt';
import SearchIcon from '@material-ui/icons/Search';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import ClearIcon from '@material-ui/icons/Clear';

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
import { loadProductos, clearProductos } from '../redux/actions/dataActions';


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
    }
    
    
}
export class dependienteNuevoPedido extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            errors : {'nombreApellidos' : [], 'NIF': [], 'direccion': [], 'provincia': [] , 'municipio': [], 'CP': [], 'email':[], 'telefono':[], 'otros': []},
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
            this.setState({nombreApellidos : this.props.data.cliente.name, 
                           direccion : this.props.data.cliente.direccion.split(',')[0].trim(),
                           municipio: this.props.data.cliente.direccion.split(',')[1].trim(),
                           provincia: this.props.data.cliente.direccion.split(',')[2].trim(),
                           CP: this.props.data.cliente.direccion.split(',')[3].trim(),
                           email: this.props.data.cliente.email,
                           telefono: this.props.data.cliente.tlf})
            this.props.clearClienteByNIF()
        }
        if(document.getElementById('step2')){
            this.updateCompra();
        } if(document.getElementById('step2') && this.props.data.productos.length === 0){
            this.props.loadProductos();
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
        numStepper.id = productoId.toString() + ',' + precioVenta.toString();
        numStepper.max = productoStock;
        numStepper.onchange = (e) => {document.getElementById('updater').click()}
        numStepper.name = 'cantidadProducto'
        numStepper.style.display = 'inline-block'

        //desacticamos el boton de añadir si ya se ha añadido
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
        clearIcon.style.display = 'block'
        boton.appendChild(clearIcon);

        //añadimos el num stepper al elemento de la cesta
        elemento.style.padding = "10px 0 10px 0";
        elemento.children[0].children[2].innerHTML = '<p style = "width: 100%"><b>Cantidad:</b></p>'
        elemento.children[0].children[2].style.display = "flex"
        elemento.children[0].children[2].style.padding = "0 20px 0 20px"
        elemento.children[0].children[2].style["align-items"] = "center"
        elemento.children[0].children[2].appendChild(numStepper)
        cesta.appendChild(elemento);
    }
    updateCompra(){
        var cesta = document.getElementById('cesta')
        if(cesta.children.length === 1 && cesta.children[0].id === 'iconoCarrito' && cesta.children[0].style.display === 'none'){
            cesta.style.display = "flex";
            cesta.children[0].style.display = 'block';
        }
        if(cesta.children.length === 0 || cesta.children[0].style.display !== 'none'){
            this.setState(function (prevState, props) { 
                if(prevState.importeFactura !== 0.0){
                    let errores = {...this.state.errors}
                    errores['otros'] = 'No hay productos comprados'
                    return {importeFactura : 0.0, errors : errores}
                }
            })
        } else{
            var updater = 0.0;
            var compraProductosUpdater = {...this.state.compraProductos}
            var numSteppers = document.getElementsByName('cantidadProducto')
            for(let numStepper of numSteppers){
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
                <Button id = 'onChangeInput' onClick = {(event) => {this.onChangeInput(event, changing, inputValue)}} style={{display : 'none'}}></Button>
                <Button id = 'updater' onClick = {(e) => {e.preventDefault(); this.setState({n:!this.state.n})}} style = {{display: 'none'}}/>
                
                {/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                  * COMIENZO DE LA VISTA 
                  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */}
                <Typography variant='h3' className={classes.tituloNuevoPedido}>GENERAR UN NUEVO PEDIDO</Typography><br/>
                <Stepper 
                    opcionales = {[2]}
                    stepTitles = {['Datos del cliente', 'Selección de productos', 'Datos de envío', 'Resumen del pedido']}
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
                                            value={null}
                                            onChange={null}
                                            onKeyPress={(event) => event.key==='Enter'? null: null}
                                            />
                                        <Button
                                            variant = "contained"
                                            onClick = {null}   
                                            color = "secondary"
                                            style = {{color: 'white'}}
                                            >
                                            <SearchIcon/>
                                        </Button>
                                    </span>

                                    <div className = {classes.catalogoDeSeleccion}>
                                        <NestedList content = {data.productos} handleTransfer = {this.handleTransfer}/>
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
    clearClienteIsDefaulter,

    clear,
    loadCliente,
    clearClienteByNIF,

    loadProductos,
    clearProductos,

}

const provincias = ['Alava','Albacete','Alicante','Almería','Asturias','Avila','Badajoz','Barcelona','Burgos','Caceres',
             'Cadiz','Cantabria','Castellon','Ciudad Real','Cordoba','La Coruña','Cuenca','Gerona','Granada','Guadalajara',
             'Guipuzkoa','Huelva','Huesca','Islas Baleares','Jaen','Leon','Lerida','Lugo','Madrid','Málaga','Murcia','Navarra',
             'Orense','Palencia','Las Palmas','Pontevedra','La Rioja','Salamanca','Segovia','Sevilla','Soria','Tarragona',
             'Santa Cruz de Tenerife','Teruel','Toledo','Valencia','Valladolid','Vizcaya','Zamora','Zaragoza']

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteNuevoPedido))
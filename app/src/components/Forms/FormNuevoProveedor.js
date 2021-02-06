import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

//MUI stuff
import Button from '@material-ui/core/Button';
import DialogContent from '@material-ui/core/DialogContent';
import TextField from '@material-ui/core/TextField';
import withStyles from '@material-ui/core/styles/withStyles';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';

import { newProveedores } from '../../redux/actions/proveedorActions';

const styles = theme => ({
    root: {
      '& .MuiTextField-root': {
        margin: theme.spacing(1),
      },
    },
    title:{
        display: "flex",
        justifyContent: "center"
    },
    selectEmpty: {
      marginTop: theme.spacing(2),
    },
    formControl: {
      margin: theme.spacing(2),
      minWidth: 220,
    }, 
    upload: {
      '& > *': {
        margin: theme.spacing(1),
      },
    },
    input: {
      display: 'none',
    },
    cancel:{
      colorPrimary:"red"
    },
    formControl:{
      margin: theme.spacing(1),
      minWidth: 300,
      marginLeft: theme.spacing(15)

    },
    formSpace:{
      marginRight: theme.spacing(2),
      marginLeft: theme.spacing(5)
    },
    formLarge:{
      marginLeft: theme.spacing(5),
      minWidth: 465
    },
    uploadBoton:{
      marginTop: theme.spacing(1),
      marginLeft: theme.spacing(4),
    },
    addBoton:{
        marginTop:10,
        display: "flex",
        justifyContent: "center"
    },
    separador:{
      marginTop: theme.spacing(4),
      marginLeft: theme.spacing(7),
      marginBottom : theme.spacing(2)
      
    }
  });

const initialState = {
    name:'',
    email:'',
    tlf:'',
    direccion:'',
    iban:'',
    nif:'',
    errors:{ name:[],email:[],tlf:[],direccion:[],iban:[],nif:[] },
    enviar:false,
    proveedor:{}
}

export class FormNuevoProveedor extends Component {

    constructor(){
        super();
        this.state = initialState;

        this.handleOnChange = this.handleOnChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const proveedorNew = {
            name:this.state.name,
            email:this.state.email,
            tlf:this.state.tlf,
            direccion:this.state.direccion,
            iban:this.state.iban,
            nif:this.state.nif
        }
        this.setState({
            proveedor:proveedorNew,
            enviar:true
        })
        //this.props.handleClose();
    }

    handleOnChange = (event) => {
        this.setState(state =>({
            [event.target.name]: event.target.value,
            errors :{
                ...state.errors,
                [event.target.name]:[]
            }
        }))
    };

    validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    validateNif(nif) {   
        const re = /^\d{8}[A-Z]$/;
        return re.test(String(nif));
    }

    validateTlf(tlf) {   
        const re = /^\d{9}$/;
        return re.test(String(tlf).toLowerCase());
    }

    validateIban(iban) {   
        const re = /^([a-zA-Z]{2})\s*\t*(\d{2})\s*\t*(\d{4})\s*\t*(\d{4})\s*\t*(\d{2})\s*\t*(\d{10})$/;
        return re.test(String(iban).toLowerCase());
    }

    componentDidUpdate(prevProps,prevState){

        if(this.props.open != prevProps.open && !this.props.open){
            this.setState({
                name:'',
                email:'',
                tlf:'',
                direccion:'',
                iban:'',
                nif:'',
                errors:{ name:[],email:[],tlf:[],direccion:[],iban:[],nif:[] },
                enviar:false,
                proveedor:{}
            })
        }

        if(this.state.enviar != prevState.enviar && this.state.enviar ){
            
            let errores = false;
            if((this.state.name.length<3 || this.state.name.length>50) && this.state.errors.name.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        name:[...state.errors.name,'Tamaño invalido']
                    }
                }))
                errores = true
            }else if(this.state.name.length>=3 && this.state.name.length<=50){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        name:[]
                    }
                }))
            }else{
                errores = true
            }
            
            if(!this.validateEmail(this.state.email) && this.state.errors.email.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        email:[...state.errors.email,'Formato Email no valido']
                    }
                }))
                errores = true
            }else if(this.validateEmail(this.state.email)){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        email:[]
                    }
                }))
            }else{
                errores = true
            }

            if(!this.validateTlf(this.state.tlf) && this.state.errors.tlf.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        tlf:[...state.errors.tlf,'Formato Tlf no valido']
                    }
                }))
                errores = true
            }else if(this.validateTlf(this.state.tlf)){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        tlf:[]
                    }
                }))
            }else{
                errores = true
            }

            if((this.state.direccion.length<3 || this.state.direccion.length>100) && this.state.errors.direccion.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        direccion:[...state.errors.direccion,'Esta vacio']
                    }
                }))
                errores = true
            }else if(this.state.direccion.length>1){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        direccion:[]
                    }
                }))
            }else{
                errores = true
            }

            if(!this.validateIban(this.state.iban) && this.state.errors.iban.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        iban:[...state.errors.iban,'Formato Iban no valido']
                    }
                }))
                errores = true
            }else if(this.validateIban(this.state.iban)){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        iban:[]
                    }
                }))
            }else{
                errores = true
            }

            if(!this.validateNif(this.state.nif) && this.state.errors.nif.length === 0){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        nif:[...state.errors.nif,'Formato Nif no valido']
                    }
                }))
                errores = true
            }else if(this.validateNif(this.state.nif)){
                this.setState(state=>({
                    errors :{
                        ...state.errors,
                        nif:[]
                    }
                }))
            }else{
                errores = true
            }

            if(!errores){
                this.props.newProveedores(this.state.proveedor)
                this.props.handleClose()
            }
            this.setState({
                enviar:false
            })
            
        }

    }

    render() {
        const { classes, handleClose , open } = this.props;

        return (
            <Dialog onClose={handleClose} aria-labelledby="simple-dialog-title" open={open}>
                <DialogTitle className={classes.title}>
                    Nuevo Proveedor
                </DialogTitle>
                <DialogContent>
                    <div style={{marginLeft:15}}>
                    <TextField 
                        autofocus
                        id="name"
                        label="Nombre"
                        type="text"
                        variant="outlined"
                        name = "name"
                        margin="normal"
                        marginRight ="normal"
                        required
                        error={this.state.errors.name.length>0}
                        helperText={this.state.errors.name[0]}
                        value={this.state.name}
                        onChange={this.handleOnChange}
                        className={classes.formSpace}
                    
                    />
                    <TextField 
                        id="tlf-number"
                        id="tlf"
                        label="Teléfono"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "tlf"
                        required
                        error={this.state.errors.tlf.length>0}
                        helperText={this.state.errors.tlf[0]}
                        value={this.state.tlf}
                        onChange={this.handleOnChange}

                    />
                    <TextField 
                        id="email"
                        label="Email"
                        type="email"
                        variant="outlined"
                        margin="normal"
                        required
                        error={this.state.errors.email.length>0}
                        helperText={this.state.errors.email[0]}
                        name = "email"
                        value={this.state.email}
                        onChange={this.handleOnChange}
                        className={classes.formSpace}

                    />

                    <TextField 
                        id="direccion"
                        label="Dirección"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "direccion"
                        required
                        error={this.state.errors.direccion.length>0}
                        helperText={this.state.errors.direccion[0]}
                        value={this.state.direccion}
                        onChange={this.handleOnChange}
                    />
                    <TextField
                        id="standard-number"
                        required
                        id="nif"
                        label="NIF"
                        variant="outlined"
                        type="text"
                        margin="normal"
                        name = "nif"
                        error={this.state.errors.nif.length>0}
                        helperText={this.state.errors.nif[0]}
                        value={this.state.nif}
                        onChange={this.handleOnChange}
                        className={classes.formLarge}

                    />
                    <TextField
                        id="standard-number"
                        id="iban"
                        label="IBAN"
                        type="text"
                        variant="outlined"
                        margin="normal"
                        name = "iban"
                        required
                        error={this.state.errors.iban.length>0}
                        helperText={this.state.errors.iban[0]}
                        value={this.state.iban}
                        onChange={this.handleOnChange}
                        className={classes.formLarge}

                    />
                    </div>
                    <div className={classes.addBoton}>
                        <Button onClick={this.handleSubmit} size="large" type ="submit" color="primary" 
                            variant="contained" startIcon={<AddCircleIcon />}>
                            Añadir
                        </Button>
                    </div>
                </DialogContent>
            </Dialog>
        )
    }
}
FormNuevoProveedor.propTypes = {
    classes : PropTypes.object.isRequired,
    newProveedores : PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    newProveedores
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(FormNuevoProveedor))

import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import  FormNuevoProveedor  from "../../../components/FormNuevoProveedor";

import Grid from '@material-ui/core/Grid';
import Paper from "@material-ui/core/Paper";
import withStyles from '@material-ui/core/styles/withStyles';
import CircularProgress from "@material-ui/core/CircularProgress";
import IconButton from "@material-ui/core/IconButton";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';

import { getProveedores,clearProveedores,newProveedores,newFacturaOtro } from "../../../redux/actions/proveedorActions";


const style = (theme) => ({
    root:{
        width:"100%",
        height:"50vh",
        marginTop:30
    }
})

const initialState= {
    open:false,
    proveedorSel:null,
    openProveedor: false,
    enviar:false,
    import:0,
    desc:"",
    errores:{proveedorSel:[],import:[]}
}

export class otro extends Component {
    constructor(){
        super();
        this.state = initialState;

        this.handleEnviar = this.handleEnviar.bind(this)
        this.handleCloseProveedor = this.handleCloseProveedor.bind(this)
        this.handleChangeProveedor = this.handleChangeProveedor.bind(this)
    }

    handleEnviar(){
        this.setState({
          enviar:true
        })
    }

    handleCloseProveedor(){
        this.setState({
          openProveedor: false, 
        });
    };

    handleChangeProveedor(event, newValue) {
    this.setState(state=>({
        proveedorSel: newValue,
        errores:{
            ...state.errores,
            proveedorSel:[]
        }
    }));
    }

    componentDidUpdate(prevProps,prevState){

        if (prevState.open !== this.state.open) {
            if (this.state.open) {
              this.props.getProveedores();
            } else {
              this.props.clearProveedores();
            }
        }

        
        if(prevState.enviar !== this.state.enviar && this.state.enviar){
            let valid = true;

            if(!this.state.proveedorSel){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        proveedorSel:["Seleccione Proveedor"]
                    }
                }))
                valid = false
            }else{
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        proveedorSel:[]
                    }
                }))
            }

            if(parseFloat(this.state.import) < 0.0){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        import:["Importe debe ser positivo"]
                    }
                }))
                valid = false
            }else{
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        import:[]
                    }
                }))
            }

            if(valid){
                const fact = {
                    importe:this.state.import,
                    estaPagada:true,
                    descripcion:this.state.desc,
                    concepto:"OTROS",
                    proveedor:this.state.proveedorSel
                }
                this.props.newFacturaOtro(fact)

                this.setState({
                    ...initialState
                })
            }

            this.setState({
                enviar:false
            })
        }
    }

    render() {
        const { classes ,proveedor: { proveedores }} = this.props;
        const loading = this.state.open && proveedores.length === 0;
        return (
            <div className={classes.root}>
                <div style={{display:"flex", justifyContent:"flex-end",alignItems:"center", width:"100%"}}>
                    <Autocomplete
                    id="proveedor"
                    size="small"
                    style={{
                        width: 200,
                        margin: 10,
                        marginLeft: 20,
                        display: "inline-block",
                    }}
                    open={this.state.open}
                    value={this.state.proveedorSel}
                    onChange={this.handleChangeProveedor}
                    onOpen={() => {
                        this.setState({
                        open: true,
                        });
                    }}
                    onClose={() => {
                        this.setState({
                        open: false,
                        });
                    }}
                    getOptionSelected={(option, value) => option.name === value.name}
                    getOptionLabel={(option) => option.name}
                    options={proveedores}
                    loading={loading}
                    renderInput={(params) => (
                        <TextField
                        {...params}
                        error={this.state.errores.proveedorSel.length>0}
                        helperText={this.state.errores.proveedorSel[0]}
                        label="Proveedor"
                        InputProps={{
                            ...params.InputProps,
                            endAdornment: (
                            <React.Fragment>
                                {loading ? (
                                <CircularProgress color="inherit" size={20} />
                                ) : null}
                                {params.InputProps.endAdornment}
                            </React.Fragment>
                            ),
                        }}
                        />
                    )}
                    />
                    <IconButton style={{marginLeft:-20,marginRight:10}} aria-label="delete" onClick={() => this.setState({
                    openProveedor:true
                    })} className={classes.addButton}>
                    <AddCircleIcon color="secondary" />
                    </IconButton>
                    <FormNuevoProveedor handleClose={this.handleCloseProveedor} open={this.state.openProveedor}/>
                    <Button
                    variant="contained"
                    color="primary"
                    style={{height:"60%"}}
                    onClick={this.handleEnviar}
                    >
                    Crear factura
                    </Button>
                </div>
                <Paper variant="outlined" style={{width:"100%",height:"100%",backgroundColor:"#fafafa" }}>
                    <div  style={{width:"100%",height:"20%", margin:40}}>
                        <TextField
                                id="outlined-multiline-static"
                                label="Importe"
                                style={{margin:"auto", width:"15%",backgroundColor:"white"}}
                                value={this.state.import}
                                error={this.state.errores.import.length>0}
                                helperText={this.state.errores.import[0]}
                                onChange={(event) => {this.setState(state=>({import:event.target.value,errores:{...state.errores,import:[]}}))}}
                                type="number"
                                variant="outlined"
                                />
                    </div>
                    <div style={{width:"100%",height:"20%" , margin:40}}>
                        <TextField
                            id="outlined-multiline-static"
                            label="Descripcion"
                            style={{margin:"auto", width:"70%",backgroundColor:"white"}}
                            value={this.state.desc}
                            onChange={(event) => {this.setState({desc:event.target.value})}}
                            multiline
                            fullWidth
                            rows={9}
                            variant="outlined"
                            />
                    </div>
                    <img style={{height:1,width:1}} src="https://firebasestorage.googleapis.com/v0/b/upload-images-gresur.appspot.com/o/pictures%2F0-0Animados.jpg?alt=media&token=51d5d976-9021-41d9-b0cb-7ac3403b7493" alt='IMAGEN'/>
                </Paper>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    proveedor: state.proveedor,
})

const mapDispatchToProps = {
    newProveedores,
    getProveedores,
    clearProveedores,
    newFacturaOtro
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(style)(otro))

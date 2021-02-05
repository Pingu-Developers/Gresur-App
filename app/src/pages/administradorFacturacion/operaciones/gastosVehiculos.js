import React, { Component } from "react";
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";

import  FormNuevoProveedor  from "../../../components/FormNuevoProveedor";
import SnackCallController from '../../../components/SnackCallController';

import Grid from '@material-ui/core/Grid';
import Paper from "@material-ui/core/Paper";

import { connect } from "react-redux";
import { getVehiculos,clearVehiculo,getVehiculosInfo,clearVehiculoInfo } from '../../../redux/actions/vehiculoActions';
import { getProveedores,clearProveedores,newProveedores,newFacturaVehiculo } from "../../../redux/actions/proveedorActions";
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Typography from "@material-ui/core/Typography";
import WarningIcon from '@material-ui/icons/Warning';
import Divider from '@material-ui/core/Divider';
import MenuItem from '@material-ui/core/MenuItem';
import DateFnsUtils from '@date-io/date-fns';
import CircularProgress from "@material-ui/core/CircularProgress";
import IconButton from "@material-ui/core/IconButton";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import Button from "@material-ui/core/Button";

import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
  } from '@material-ui/pickers';

const style = (theme) => ({
    grid1:{
        height:"66vh",
    },
    paper:{
        backgroundColor:"#fafafa",
        height:"100%",
        width:"100%"
    },
    verticalGrid1:{
        height:"100%",
        width:"46%"
    },
    verticalGrid2:{
        height:"100%",
        width:"30%"
    },
    verticalGrid3:{
        height:"100%",
        width:"24%",
    },
    grid2:{
        padding:5,
        height:"20vh",
        display: "flex",
        alignItems:"center"
    },
    grid3:{
        marginLeft:"5%",
        padding:5,
        height:"20vh",

    },
    grid4:{
        marginLeft:"5%",
        padding:5,
        height:"23vh",
        display: "flex",
        alignItems:"top"
    },
    comboVehiculo:{
        margin:"auto",
        width:"45%",
        minWidth:180,
        position: "relative",
        left: "-5%",
    },
    title:{
        fontWeight: 'bold',
        textDecoration: 'underline'
    },
    imagen:{
        width: 150,
        height: 150,
        //marginLeft:"2%",
        display: "inline-block",
        position: "relative",
        top:-25
    },
    info:{
        margin:20,
        display: "inline-block",
        position: "relative",
        top:-40
    },
    info2:{
        margin:10,
        display: "inline-block",
        position: "relative",
        top:-15
    },
    fieldset: {
        backgroundColor:"#fefefe",
        borderColor:'#C4C4C4',
        paddingBottom:0,
        borderRadius: 10,
        marginLeft:10,
        marginRight:20

      },
    warning: {
        width: 60,
        height: 60,
    },
  });

const initialState = {
    valueVehiculo:null,
    concepto:"seguro",
    valueTipo:"",
    desc:"",
    fechaInicio:new Date(),
    fechaFin:null,
    open:false,
    proveedorSel:null,
    openProveedor: false,
    enviar:false,
    import:0,
    errores:{valueVehiculo:[],valueTipo:[],fechaInicio:[],fechaFin:[],proveedorSel:[],import:[]}
}
export class gatosEnvio extends Component {
    constructor(props) {
        super(props);
        this.state = initialState
        this.handleCloseProveedor = this.handleCloseProveedor.bind(this)
        this.handleChangeVehiculo = this.handleChangeVehiculo.bind(this)
        this.handleChangeProveedor = this.handleChangeProveedor.bind(this)
        this.handleEnviar = this.handleEnviar.bind(this)
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

    handleChangeVehiculo(value) {
        this.setState(state =>({
            valueVehiculo:value,
            errores:{
                ...state.errores,
                valueVehiculo:[]
            }
          }))
        if(value){
            this.props.getVehiculosInfo(value);
        }else{
            this.props.clearVehiculoInfo()
        }
    }

    handleChangeProveedor(event, newValue) {
        this.setState(state=>({
          proveedorSel: newValue,
          errores:{
              ...state.errores,
              proveedorSel:[]
          }
        }));
      }

    componentDidMount(){
        this.props.getVehiculos();
    }

    dateInPast = function(fecha) {
        const firstDate = new Date()
        if (firstDate.setHours(0, 0, 0, 0) < fecha.setHours(0, 0, 0, 0)) {
          return true;
        }
      
        return false;
    };

    componentDidUpdate(prevProps, prevState) {
       
        if (prevState.open !== this.state.open) {
          if (this.state.open) {
            this.props.getProveedores();
          } else {
            this.props.clearProveedores();
          }
        }

        if(prevState.enviar !== this.state.enviar && this.state.enviar){
            let valid = true;

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

            if(!this.state.valueVehiculo){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        valueVehiculo:["Seleccione Vehiculo"]
                    }
                }))
                valid = false
            }else{
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        valueVehiculo:[]
                    }
                }))
            }

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

            if(this.state.valueTipo === ""){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        valueTipo:["Seleccione algun valor"]
                    }
                }))
                valid = false
            }else{
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        valueTipo:[]
                    }
                }))
            }

            if(this.state.valueTipo === ""){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        valueTipo:["Seleccione algun valor"]
                    }
                }))
                valid = false
            }else{
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        valueTipo:[]
                    }
                }))
            }

            if(!this.state.fechaInicio){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        fechaInicio:["Seleccione alguna fecha"]
                    }
                }))
                valid = false
            }else if(this.dateInPast(this.state.fechaInicio)){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        fechaInicio:["La Fecha no puede ser futura"]
                    }
                }))
                valid = false
            }else{
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        fechaInicio:[]
                    }
                }))
            }

            if(!this.state.fechaFin){
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        fechaFin:["Seleccione alguna fecha"]
                    }
                }))
                valid = false
            }else {
                this.setState(state=>({
                    errores:{
                        ...state.errores,
                        fechaFin:[]
                    }
                }))
            }

            if(valid){
                
                const factura = {
                    recibidas:{
                        importe:this.state.import,
                        estaPagada:true,
                        descripcion:this.state.desc,
                        concepto:"GASTOS_VEHICULOS",
                        proveedor:this.state.proveedorSel
                    }      
                }
                const data = {
                    e1:factura.recibidas,
                    e2:null
                }
                switch (this.state.concepto) {
                    case "seguro":
                        const seguro = {
                            compania:this.state.proveedorSel.name,
                            tipoSeguro:this.state.valueTipo,
                            fechaContrato:this.state.fechaInicio,
                            fechaExpiracion:this.state.fechaFin,
                            recibidas:factura.recibidas,
                            vehiculo:this.state.valueVehiculo
                        };
                        data.e2 = seguro;
                        this.props.newFacturaVehiculo("seguro",data)
                        break;

                    case "itv":
                        const ITV = {
                            resultado:this.state.valueTipo,
                            fecha:this.state.fechaInicio,
                            expiracion:this.state.fechaFin,
                            recibidas:factura,
                            vehiculo:this.state.valueVehiculo
                        }
                        data.e2 = ITV;
                        this.props.newFacturaVehiculo("itv",data)
                        break;
                    case "reparacion":
                        const reparacion = {
                            descripcion:this.state.desc,
                            fechaEntradaTaller:this.state.fechaInicio,
                            fechaSalidaTaller:this.state.fechaFin,
                            recibidas:factura,
                            vehiculo:this.state.valueVehiculo
                        }
                        data.e2 = reparacion;
                        this.props.newFacturaVehiculo("reparacion",data)
                        break;
                    default:
                        break;
                }
                this.setState({
                    ...initialState
                })
            }
            this.setState({
                enviar:false
            })
        }
    }
    componentWillUnmount(){
        this.props.clearVehiculo();
    }

   

render() {

    const {classes,UI:{errors,enviado}, vehiculo:{vehiculos , seguroitv},proveedor: { proveedores }} = this.props;
    const loading = this.state.open && proveedores.length === 0;
    return (
        <Grid container>
            <SnackCallController  enviado = {enviado} message = {"Factura realizada correctamente"} errors={errors} />
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
            <Grid item xs={12} className={classes.grid1}>
                <Paper variant="outlined" className={classes.paper}>
                    <Grid container className={classes.container} >
                        <Grid container item direction="column" className={classes.verticalGrid1}>
                            <Grid item direction="column" className={classes.grid2}>
                                <Autocomplete
                                    id="grouped-demo"
                                    value={this.state.valueVehiculo}
                                    onChange={(event, newValue) => {
                                      this.handleChangeVehiculo(newValue)
                                    }}
                                    options={vehiculos?vehiculos.sort((a,b)=> -b.tipoVehiculo[0].localeCompare(a.tipoVehiculo[0])):[]}
                                    groupBy={(option) => option.tipoVehiculo}
                                    getOptionLabel={(option) => option.matricula}
                                    className={classes.comboVehiculo}
                                    renderInput={(params) => <TextField {...params} 
                                         error={this.state.errores.valueVehiculo.length>0} 
                                         helperText={this.state.errores.valueVehiculo[0]} 
                                         label="Seleccione Vehiculo" 
                                         style = {{backgroundColor:"white"}}
                                         variant="outlined" />}
                                />
                            </Grid>
                            {this.state.valueVehiculo&&seguroitv?<fieldset className={classes.fieldset}>
                                <Grid item className={classes.grid3}>
                                    <Typography variant={"h5"} className={classes.title}>Vehiculo</Typography>
                                    <img className={classes.imagen} src={this.state.valueVehiculo.imagen} alt='IMAGEN' />
                                    <div className={classes.info}>
                                        <p><b>Matricula:</b> {this.state.valueVehiculo.matricula}</p>
                                        <p><b>Capacidad:</b> {this.state.valueVehiculo.capacidad} m<sup>3</sup> </p>
                                        <p><b>MMA:</b> {this.state.valueVehiculo.mma}</p>
                                    </div>
                                    
                                </Grid>
                                <Grid item className={classes.grid4}>
                                    <div className={classes.info2}>
                                        <Typography variant={"h6"} className={classes.title}>Seguro</Typography>
                                        {seguroitv.seguro===undefined?
                                            <div>
                                                <p>Este vehiculo no dispone de ningun seguro</p>
                                                <WarningIcon className={classes.warning} color='primary'/>
                                            </div>
                                            :
                                            <div>
                                                <p><b>Compañia:</b> {seguroitv.seguro.compania}</p>
                                                <p><b>Tipo de Seguro:</b> {seguroitv.seguro.tipoSeguro}</p>
                                                <p><b>Fecha de contratacion:</b> {seguroitv.seguro.fechaContrato}</p>
                                                <p><b>Fecha de expiracion:</b> {seguroitv.seguro.fechaExpiracion}</p>    
                                            </div>
                                        }
                                    </div>
                                    <Divider orientation="vertical" flexItem />
                                    <div className={classes.info2}>
                                        <Typography variant={"h6"} className={classes.title}>ITV</Typography>
                                        {seguroitv.itv===undefined?
                                            <div>
                                                <p>Este vehiculo no dispone de ninguna ITV</p>
                                                <WarningIcon className={classes.warning} color='primary'/>
                                            </div>
                                            :
                                            <div>
                                                <p><b>Fecha de ultima revision:</b> {seguroitv.itv.fecha}</p>
                                                <p><b>Resultado ultima ITV:</b> {seguroitv.itv.resultado}</p>
                                                <p><b>Fecha de expiracion:</b> {seguroitv.itv.expiracion}</p>
                                            </div>
                                        }
                                    </div> 
                                </Grid>
                            </fieldset>:null}
                            
                         </Grid>
                         <Grid container item direction="column" className={classes.verticalGrid2}>
                            <Grid item direction="column" style={{height:"20vh", display:"flex",alignItems:"center"}}>
                                <TextField label="Concepto" value={this.state.concepto} onChange={(event)=>this.setState({concepto:event.target.value,valueTipo:""})} 
                                    style={{margin:"auto",marginLeft:20,width:"45%",minWidth:180,backgroundColor:"white"}} variant="outlined" select >
                                    <MenuItem key="seguro" value="seguro">
                                        Seguro
                                    </MenuItem>
                                    <MenuItem key="itv" value="itv">
                                        ITV
                                    </MenuItem>
                                    <MenuItem key="reparacion" value="reparacion">
                                        Reparación
                                    </MenuItem>
                                </TextField>
                            </Grid>
                            {this.state.concepto!=="reparacion"?
                            <Grid item direction="column" style={{height:"13vh", display:"flex",alignItems:"center"}}>
                                {this.state.concepto==="itv"?
                                    <TextField label="Resultado"  value={this.state.valueTipo} onChange={(event)=>this.setState(state=>({valueTipo:event.target.value, errores:{...state.errores,valueTipo:[]} }))} 
                                        style={{margin:"auto",marginTop:0,marginLeft:20,width:"45%",minWidth:180,backgroundColor:"white"}} variant="outlined" select 
                                        error={this.state.errores.valueTipo.length>0} helperText={this.state.errores.valueTipo[0]}
                                        >
                                        <MenuItem key="favorable" value="FAVORABLE">
                                            Favorable
                                        </MenuItem>
                                        <MenuItem key="desfavorable" value="DESFAVORABLE">
                                            Desfavorable
                                        </MenuItem>
                                        <MenuItem key="negativa" value="NEGATIVA">
                                            Negativa
                                        </MenuItem>
                                    </TextField>:null}
                                {this.state.concepto==="seguro"?
                                    <TextField label="Tipo Seguro" value={this.state.valueTipo} onChange={(event)=>this.setState(state=>({valueTipo:event.target.value, errores:{...state.errores,valueTipo:[]} }))} 
                                        style={{margin:"auto",marginTop:0,marginLeft:20,width:"45%",minWidth:180,backgroundColor:"white"}} variant="outlined" select 
                                        error={this.state.errores.valueTipo.length>0} helperText={this.state.errores.valueTipo[0]}
                                        >
                                        <MenuItem key="todorriesgo" value="TODORRIESGO">
                                            Todorriesgo
                                        </MenuItem>
                                        <MenuItem key="terceros" value="TERCEROS">
                                            Terceros
                                        </MenuItem>
                                        <MenuItem key="tercerosAmp" value="TERCEROS_AMPLIADO">
                                            Terceros ampliados 
                                        </MenuItem>
                                    </TextField>:null}
                            </Grid>:null}
                            <Grid item direction="column" style={{height:"29vh", display:"flex",alignItems:"center"}}>
                                <TextField
                                    id="outlined-multiline-static"
                                    label="Descripcion"
                                    style={{margin:"auto",marginTop:0,marginLeft:20,backgroundColor:"white"}}
                                    value={this.state.desc}
                                    onChange={(event) => {this.setState({desc:event.target.value})}}
                                    multiline
                                    fullWidth
                                    rows={7}
                                    variant="outlined"
                                    />
                            </Grid>
                         </Grid>
                         <Grid container item direction="column" className={classes.verticalGrid3}>
                            <Grid item direction="column" style={{height:"20vh",marginLeft:40, display:"flex",alignItems:"center"}}>
                                <MuiPickersUtilsProvider 
                                utils={DateFnsUtils}>
                                    <KeyboardDatePicker
                                    style={{margin:"auto",marginLeft:"15%",width:"50%",minWidth:200}}
                                    className={classes.date}
                                    disableToolbar
                                    autoOk={true}
                                    variant="inline"
                                    format="dd/MM/yyyy"
                                    margin="normal"
                                    error={this.state.errores.fechaInicio.length>0} 
                                    helperText={this.state.errores.fechaInicio[0]}
                                    label={this.state.concepto!=="reparacion"?(this.state.concepto!=="seguro"?"Fecha":"Fecha Contratacion"):"Fecha Entrada Taller"}
                                    value={this.state.fechaInicio}
                                    onChange={(date)=>{
                                        this.setState(state=>({
                                            fechaInicio:date,
                                            errores:{
                                                ...state.errores,
                                                fechaInicio:[]
                                            }
                                        }))
                                    }}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                        />       
                                </MuiPickersUtilsProvider>
                            </Grid>
                            <Grid item direction="column" style={{height:"13vh",marginLeft:40, display:"flex",alignItems:"center"}}>
                                <MuiPickersUtilsProvider 
                                utils={DateFnsUtils}>
                                    <KeyboardDatePicker
                                    style={{margin:"auto",marginTop:0,marginLeft:"15%",width:"55%",minWidth:200}}
                                    className={classes.date}
                                    disableToolbar
                                    autoOk={true}
                                    variant="inline"
                                    format="dd/MM/yyyy"
                                    margin="normal"
                                    error={this.state.errores.fechaFin.length>0} 
                                    helperText={this.state.errores.fechaFin[0]}
                                    label={this.state.concepto!=="reparacion"?(this.state.concepto!=="seguro"?"Expiracion":"Fecha Expiracion"):"Fecha Salida Taller"}
                                    value={this.state.fechaFin}
                                    onChange={(date)=>{
                                        this.setState(state=>({
                                            fechaFin:date,
                                            errores:{
                                                ...state.errores,
                                                fechaFin:[]
                                            }
                                        }))
                                    }}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                        />       
                                </MuiPickersUtilsProvider>
                            </Grid>
                            <Grid item direction="column" style={{height:"20vh",marginLeft:40, display:"flex",alignItems:"center"}}>
                            <TextField
                                    id="outlined-multiline-static"
                                    label="Importe"
                                    error={this.state.errores.import.length>0} 
                                    helperText={this.state.errores.import[0]}
                                    style={{margin:"auto",marginTop:0,marginLeft:"15%",width:"55%",minWidth:200,backgroundColor:"white"}}
                                    value={this.state.import}
                                    onChange={(event) => {
                                        this.setState(state=>({
                                            import:event.target.value,
                                            errores:{
                                                ...state.errores,
                                                import:[]
                                            }
                                        }))}}
                                    type="number"
                                    variant="outlined"
                                    />
                            </Grid>
                         </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    )
}}

gatosEnvio.propTypes = {
    classes: PropTypes.object.isRequired,
    vehiculo: PropTypes.object.isRequired,
    getVehiculos: PropTypes.func.isRequired,
    clearVehiculo: PropTypes.func.isRequired,
    getVehiculosInfo: PropTypes.func.isRequired,
    clearVehiculoInfo: PropTypes.func.isRequired,
    newProveedores: PropTypes.func.isRequired,
    newFacturaVehiculo: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    vehiculo: state.vehiculo,
    proveedor: state.proveedor,
    UI: state.UI
})

const mapActionsToProps = {
    getVehiculos,
    clearVehiculo,
    getVehiculosInfo,
    clearVehiculoInfo,
    getProveedores,
    clearProveedores,
    newProveedores,
    newFacturaVehiculo,
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(gatosEnvio))


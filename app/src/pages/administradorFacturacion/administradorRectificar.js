import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import withStyles from '@material-ui/core/styles/withStyles';

import { clearFacturaEmitida, loadFacturaEmitida,clear,rectificaFactura } from '../../redux/actions/dataActions';

import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import IconButton from '@material-ui/core/IconButton';
import SearchIcon from '@material-ui/icons/Search';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Typography from "@material-ui/core/Typography";

import ViewModuleIcon from '@material-ui/icons/ViewModule';
import ViewHeadlineIcon from '@material-ui/icons/ViewHeadline';

import  MotradorLineas  from '../../components/HistoryLists/MotradorLineas';
import Snackbar from '../../components/Other/SnackBar'
import SnackCallController from '../../components/Other/SnackCallController';

import ReplayIcon from '@material-ui/icons/Replay';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

const style = theme => ({

    formControl:{
        display: "grid",
        gridTemplateRows: "1fr",
        gridTemplateColumns:"1fr 15fr 1fr",
        alignItems: "center",
        width:"15%",
    },
    selector: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        '& > *': {
          margin: theme.spacing(1),
        },
      },
      rootMostrador1:{
        width: "100%",
        display: "inline-grid",
        gridTemplateColumns:"1fr 1fr 1fr",
        justifyContent: "space-between",
        "@media(max-width:1500px)":{
            gridTemplateColumns:"1fr 1fr",
        },
        "@media(max-width:1070px)":{
            gridTemplateColumns:"1fr ",
        }
    },
    rootMostrador2:{
        width: "100%",
        display: "inline-grid",
        gridTemplateColumns:"1fr",
    },
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        color: '#7a7a7a',
        margin: '0px 0px 20px 0px',
        padding: '0px 0px 15px 20px',
        width: '100%',
        borderBottom: '1px solid #bdbdbd'
    },
})


export class administradorRectificar2 extends Component {
    constructor(props){
        super(props)
        this.state={
            type:"E-",
            valor:null,
            tipoMost:true,
            buscar:false,
            lineas:[],
            total:0,
            reset:false,
            send:false,
            t:false,
        }
        this.handleChange = this.handleChange.bind(this)
        this.handleBuscar = this.handleBuscar.bind(this)
        this.handleModLinea = this.handleModLinea.bind(this)
        this.handleAnadirLinea = this.handleAnadirLinea.bind(this)
        this.handleDelLinea = this.handleDelLinea.bind(this)
    }

    handleChange(event){
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    handleModLinea(linea){

        const index = this.state.lineas.indexOf(this.state.lineas.filter(obj => obj.producto.id === linea.producto.id)[0])
        var updatedLineas = [...this.state.lineas];
        updatedLineas[index] = linea
        this.setState({
            lineas:updatedLineas
        })
    }

    handleDelLinea(linea){

        const index = this.state.lineas.indexOf(this.state.lineas.filter(obj => obj.producto.id === linea.producto.id)[0])
        if(index > -1){
            var updatedLineas = [...this.state.lineas]
            updatedLineas.splice(index,1)
            this.setState(state=>({
                lineas:state.lineas.filter((_,i) => i !== index),
            }))
        }
        
    }

    handleAnadirLinea(linea){
        
        linea["factura"] = this.props.data.facturas
        console.log(linea)
        const index = this.state.lineas.indexOf(this.state.lineas.filter(obj => obj.producto.id === linea.producto.id)[0])
        if(index === -1){

            this.setState(state=>({
                lineas:[...state.lineas,linea]
            }))
        }
        
    }

    handleBuscar(){
        this.setState({
            buscar:true
        })
    }
    arrayEquals(a, b) {
        return Array.isArray(a) &&
            Array.isArray(b) &&
            a.length == b.length &&
            a.every((val, index) => val == b[index]);
    }

    componentDidUpdate(prevProps,prevState){

        if(prevState.buscar != this.state.buscar && this.state.buscar){
            this.props.clear()
            this.props.loadFacturaEmitida(this.state.type+this.state.valor)
            this.setState({
                buscar:false
            })
        }

        if(!this.arrayEquals(prevState.lineas,this.state.lineas) && this.state.lineas){
            var temp = 0;
            this.state.lineas.map(obj => temp += obj.precio)
            this.setState({
                total:temp
            })
        }

        if(prevProps.data.facturas != this.props.data.facturas && this.props.data.facturas.id){
            var temp = [...this.props.data.facturas.lineasFacturas];
            temp.every((val,index)=>temp.splice(index,1,{...val,id:null}))
            this.setState({
                lineas:temp
            })
        }

        if(prevState.reset !== this.state.reset && this.state.reset && this.props.data.facturas.numFactura){
            this.props.loadFacturaEmitida(this.props.data.facturas.numFactura)
            this.setState({
                reset:false
            })
        }

        if(prevState.send !== this.state.send && this.state.send && this.props.data.facturas.numFactura){
            console.log(this.state.lineas)
            var valid = true;
            if(this.state.lineas.length>0 && this.state.lineas.every((val, index) => val>0 )){
                valid = false;
            }

            if(this.state.lineas.every((val,index)=> {return (val.producto === this.props.data.facturas.lineasFacturas[index].producto) 
                &&(val.cantidad === this.props.data.facturas.lineasFacturas[index].cantidad)}) && (this.state.lineas.length == 0 && this.props.data.facturas.lineasFacturas.length == 0))
            {
                valid=false;
                document.getElementById("botonSnack")? document.getElementById("botonSnack").click() : null();
            }
            if(valid){
                const facturaRect = {
                    ...this.props.data.facturas,
                    id:null,
                    numFactura:null,
                    lineasFacturas:[...this.state.lineas],
                    importe:parseFloat(this.state.total.toFixed(2)),
                    descripcion:"",
                    original:this.props.data.facturas
                }
                console.log(facturaRect)
                this.props.rectificaFactura(facturaRect);
                this.props.clear()
            }
            this.setState({
                send:false
            })
        }
    }

    componentWillUnmount(){
        this.props.clear()
    }
    render() {
        const {type,valor,tipoMost,lineas} = this.state
        const {classes,data:{facturas},UI:{errors,enviado}} = this.props

        return (
            <div style={{height:"100%"}}>
                <Typography className = {classes.titulo}>RECTIFICAR FACTURAS</Typography>

                <Snackbar id="botonSnack" type = "warning" message= 'No has modificado la factura'/>
                <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
                <div style={{height:"10%",margin:20,width:"100%", display:'inline-flex', justifyContent:"space-between"}}>
                    <div style={{display:"grid", gridTemplateColumns:"1fr 1fr",width: "30%",alignItems:"center", gridColumnGap:20}}>
                        <div className={classes.formControl} >
                            <TextField
                                labelId="factura"
                                select
                                value={type}
                                name="type"
                                style={type==="RCTE-"?{width:84,marginLeft:15,"&:Select":{paddingRight:0}}:{width:54,marginLeft:15,paddingRight:0}}
                                inputProps={{
                                    style:{paddingRight:0}
                                }}
                                onChange={this.handleChange}
                            >
                                <MenuItem value="E-">  E-</MenuItem>
                                <MenuItem value="RCTE-">  RCTE-</MenuItem>
                            </TextField>
                            <TextField
                                name="valor"
                                type="number"
                                value={valor}
                                style={{minWidth:40}}
                                autoComplete={"off"}
                                onChange={(event)=>{
                                    const int = Number.isNaN(parseInt(event.target.value))||parseInt(event.target.value)<=0? 1: parseInt(event.target.value)
                                    this.setState({
                                        valor:int
                                    })
                                }}
                                />
                            <IconButton onClick={this.handleBuscar} color="secondary" aria-label="delete">
                                <SearchIcon />
                            </IconButton>
                        </div>
                        <div>
                            <ButtonGroup size="large" color="primary">
                                <Button  color="primary" onClick={()=>this.setState({reset:true})} endIcon={<ReplayIcon color="primary"/>}>
                                    Deshacer cambios
                                </Button>
                                <Button  color="primary" onClick={()=>this.setState({send:true})} endIcon={<CheckCircleIcon color="primary"/>}>
                                    Aceptar cambios
                                </Button>
                            </ButtonGroup>
                        </div>
                    </div>
                    <div className={classes.selector}>
                        <ButtonGroup style={{marginRight:42}} disableElevation  color="secondary" aria-label="outlined secondary button group">
                            <Button style={tipoMost?{backgroundColor:"#00bcd4",color:"white"}:null} onClick={()=>this.setState({tipoMost:true})}><ViewModuleIcon/></Button>
                            <Button style={tipoMost?null:{backgroundColor:"#00bcd4",color:"white"}} onClick={()=>this.setState({tipoMost:false})}><ViewHeadlineIcon/></Button>
                        </ButtonGroup>
                    </div>               
                </div>
                <Divider />
                <div style={{height:"90%",padding:20}}>
                    {facturas.id?<div><MotradorLineas tipo={tipoMost} 
                        classes={classes} 
                        datos={this.state.lineas} 
                        handleChange={this.handleModLinea} 
                        handleAnadirLinea={this.handleAnadirLinea}
                        handleDelLinea={this.handleDelLinea}
                        />
                        <Divider />
                        <div>
                            <Typography
                                align="right"
                                style={{fontWeight:600,marginRight:20}}
                                variant="h4"
                                color="textSecondary"
                                >
                                TOTAL: {this.state.total.toFixed(2)}€
                            </Typography>
                        </div>
                        </div>    
                        :<div style={{height:370, display: "flex",justifyContent: "center",alignItems:"flex-end"}}>
                            <Typography
                                align="center"
                                style={{fontWeight:600,marginRight:20,width:450,display:"flex",justifyContent:"center"}}
                                variant="h2"
                                color="textSecondary"
                                >
                                SELECCIONE UNA FACTURA
                            </Typography>
                        </div>}
                </div>         
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    data:state.data,
    UI:state.UI
})

const mapDispatchToProps = {
    loadFacturaEmitida,
    clearFacturaEmitida,
    clear,
    rectificaFactura
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(style)(administradorRectificar2))

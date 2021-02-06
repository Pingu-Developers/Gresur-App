import React from 'react'
import { makeStyles } from '@material-ui/core/styles';

import Snackbar from '../Other/SnackBar'

import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Divider from '@material-ui/core/Divider';
import Autocomplete from '@material-ui/lab/Autocomplete';
import TextField from '@material-ui/core/TextField';
import DateFnsUtils from '@date-io/date-fns';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { loadClienteIsDefaulter, clearClienteIsDefaulter } from "../../redux/actions/dataActions"
import { getFacturasCliente, getFacturasClienteAndFecha,sendDevolucion } from "../../redux/actions/clienteActions"
import ArrowForwardIcon from '@material-ui/icons/ArrowForward';
import ClearIcon from '@material-ui/icons/Clear';

import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
  } from '@material-ui/pickers';



const useStyles = makeStyles({

    cuerpo:{
        marginTop:10,
    },
    grid:{
        width:"100%",
        margin:20,
        marginRight:0
    },
    grid2:{
        width:"100%",
        margin:20,
        marginLeft:75
    },
    date:{
        marginTop:-10
    },
    arrowDiv:{
        position: 'relative',
        alignItems:'center',
        display:'flex',
        justifyContent:'center',
    },
    tables:{
        minHeight:260,
        maxHeight:260,
        overflowY:"auto",
        overflowX:"hidden"        
    },
    tablesform:{
        minHeight:200,
        maxHeight:200,
        overflowY:"auto",
        overflowX:"hidden"
    }, 
    fieldset: {
        borderRadius: 10,
        backgroundColor: '#f7f7f7',
        color: '#3d3d3d'
    },
    bottomSpace: {
       height:20,
    },
    button:{
        position:'relative',
        top:25,
        '&$disabled' : {
            backgroundColor: '#f2f2f2',
            border: '1px solid #dbdbdb'
        }
    },
    disabled:{},
    rowTitle:{
        position:'relative',
        top:32,
        marginLeft:"4.5%",
        fontWeight: 600
    },
    rowTitle2:{
        float:'right',
        position:'relative',
        top:10,
        marginRight:"36.4%",
        fontWeight: 600
    },
    gridproductos :{
        margin:8,
        fontSize:14
    },
    gridboton :{
        margin:8,
        marginLeft:0,
        fontSize:14
    },
    total: {
        margin:10,
        fontSize: 25,
        fontWeight: 600,
        float:'right'
    }


});

export default function FormDatosDevolucion() {

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [errores, setErrores] = React.useState([]);
    const [next, setNext] = React.useState(false);
    const [formData, setFormData] = React.useState(null);

    const [value, setValue] = React.useState(null);
    const [valueNum, setValueNum] = React.useState(null);
    const [inputValue, setInputValue] = React.useState('');
    const [fechaInicio, setFechaInicio] = React.useState(null);
    const [open, setOpen] = React.useState(false);
    const [options1, setOptions1] = React.useState([]);
    const [options2, setOptions2] = React.useState([]);
    const [motivo, setMotivo] = React.useState('');
    const [lineasSelected,setLineasSelected] = React.useState([]);
    const [valueSelected,setValueSelected] = React.useState([]);
    const [n,setN] =React.useState(0);
    const classes = useStyles();

    let test = React.useRef(null)
    const handleFechaInicioChange = (date) => {
        setFechaInicio(date);
        
    };

    const handleClickSelected = (row) => {
        let temp = lineasSelected
        let temp2 = valueSelected
        if(temp.indexOf(row) === -1){
            temp.push(row)
            temp2.push({e1: row.producto.id , e2:1})
        }
        setLineasSelected(temp);
        setValueSelected(temp2);
        setN(n+1)
    }

    const handleChangeValueSelected = (producto,value) => {
        let temp = valueSelected
        const index = temp.indexOf(temp.filter(obj => {
            return obj.e1 === producto
          })[0]);
        if(index !== -1){
            temp.splice(index,1,{e1:producto , e2: parseInt(value)})
        }
        setValueSelected(temp);
        setN(n+1)
    }

    const handleClickDeselected = (row) => {
        let temp = lineasSelected
        let temp2 = valueSelected

        const index = temp.indexOf(row);
        const index2 = temp2.indexOf(temp2.filter(obj => {
            return obj.e1 === row.id
          })[0]);
        if(index !== -1){
            temp.splice(index,1)
            temp2.splice(index2,1)
        }

        let tempErrores = errores;
        const error = errores.indexOf(errores.filter(obj => {
            return obj.id === row.producto.id
        })[0]);
        if(error !== -1 ){
            tempErrores.splice(error,1);
        }
        setErrores(tempErrores);

        setLineasSelected(temp);
        setValueSelected(temp2);
        setN(n+1)
    }

    const handelLoadFacturas= (value,fecha = null) => {
        if(fecha ===null){
            if(value !== null){
                dispatch(getFacturasCliente(value.id))
            }
            setFechaInicio(null);    
        }else{
            if(value !== null){
                const temp = {
                    e1:value.id,
                    e2:fecha
                }
                dispatch(getFacturasClienteAndFecha(temp))
            }
        }   

        setValueNum(null);
        setMotivo('');
        setLineasSelected([])
        setValueSelected([])
        
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData2 = {
            e1: valueNum,
            e2: motivo,
            e3: valueSelected
        }
        
        valueSelected.map((row) => {
            const linea = lineasSelected.filter(obj => {
                                    return obj.producto.id === row.e1
                                })[0]
            if(row.e2 > linea.cantidad){
                let tempErrores = errores;
                const error = errores.indexOf(errores.filter(obj => {
                    return obj.id === row.e1
                })[0]);
                if(error !== -1 ){
                    tempErrores.splice(error,1);
                }
                tempErrores.push({tipo:"MaxExceeded",id:row.e1,error:`Max exceeded`});
                setErrores(tempErrores);
            }
            else {
                let tempErrores = errores;
                const error = errores.indexOf(errores.filter(obj => {
                    return obj.id === row.e1
                })[0]);
                if(error !== -1 ){
                    tempErrores.splice(error,1);
                }
                setErrores(tempErrores);
            }

        })
        setFormData(formData2);
        setNext(true&&errores.length === 0&&valueSelected.length > 0) 
        setN(n+1)  
    }

    React.useEffect(()=> {
        if(value){
            dispatch(loadClienteIsDefaulter(value.nif));
        }else{
            dispatch(clearClienteIsDefaulter())
        }
    },[value])


    React.useEffect(()=> {

        
       
    },[valueSelected])

    React.useEffect(()=> {
        if(counter.data.isDefaulter){
            document.getElementById("botonSnack")? document.getElementById("botonSnack").click() : console.log('No se encuentra boton para abrir la snackbar');
        }
        if(counter.cliente.errores){
            document.getElementById("botonSnack2")? document.getElementById("botonSnack2").click() : console.log('No se encuentra boton para abrir la snackbar');
        }
        if(next && errores.length ===0){
            console.log(formData)
            
            dispatch(sendDevolucion(formData))
            setValue(null)
            setValueNum(null);
            setInputValue('');
            setMotivo('');
            setFechaInicio(null);
            setLineasSelected([]);
            setValueSelected([]);
            setNext(false);
            setFormData(null);
        }else{
            setNext(false);
        }
    })


    let total = 0;
    valueSelected.map((row)=>{
       const linea = lineasSelected.filter(obj => {
            return obj.producto.id === row.e1
        })[0]

        if(linea){

            total = total + ((linea.precio/linea.cantidad)*row.e2)
        }
    })
    
    return (
        <fieldset className={classes.fieldset}> 
            <legend> <Typography variant='subtitle1' className={classes.subtituloCatalogo}>Datos de la devolucion</Typography> </legend>
            {counter.data.isDefaulter?<Snackbar id="botonSnack" type = "error" open = {counter.data.isDefaulter} message= 'Este cliente tiene impagos!'/>:null}          
            {counter.cliente.errores?<Snackbar id="botonSnack2" type = "error" open = {counter.cliente.errores} message= {counter.cliente.errores}/>:null}
            <Grid  container spacing={3}>
                <Grid className={classes.grid }item xs={3}>
                    <Autocomplete
                        size="small"
                        value={value}
                        onOpen={()=> {setOptions1(counter.cliente.clientes)}}
                        onChange={(event, newValue) => {
                            handelLoadFacturas(newValue);
                            setValue(newValue);
                        }}
                        inputValue={inputValue}
                        onInputChange={(event, newInputValue) => {
                           setInputValue(newInputValue);
                        }}
                        id="cliente"
                        options={options1}
                        getOptionLabel = {(option) => option.name}
                        style={{ width: 300 }}

                        renderInput={(params) => <TextField {...params} label="Cliente" variant="outlined" />}
                    />
                </Grid>
                <Grid className={classes.grid } item xs={3}>
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                            <KeyboardDatePicker
                            disabled={value === null || value==='' || counter.data.isDefaulter?true:false}
                            className={classes.fechaLeft}
                            className={classes.date }
                            disableToolbar
                            autoOk={true}
                            variant="inline"
                            format="dd/MM/yyyy"
                            margin="normal"
                            label="Fecha de la compra"
                            value={fechaInicio}
                            onChange={(date)=>{
                                handleFechaInicioChange(date)
                                handelLoadFacturas(value,date)
                            }}
                            KeyboardButtonProps={{
                                'aria-label': 'change date',
                            }}
                                />       
                    </MuiPickersUtilsProvider>
                </Grid>
                <Grid className={classes.grid } item xs={3}>
                    <Autocomplete
                        disabled={value === null || value==='' || counter.data.isDefaulter?true:false}
                        size="small"
                        id="numfactura"
                        freeSolo
                        options={options2 !== null && options2.length > 0?options2:[null]}
                        open={open}
                        value={valueNum}
                        onChange={(event, newValue) => {
                            setValueNum(newValue);
                            setLineasSelected([]);
                        }}
                        onOpen={() => {
                            setOptions2(counter.cliente.facturas)
                            
                            setOpen(true)
                        }}
                        onClose={() => {
                            setOpen(false)
                        }}
                        getOptionDisabled={(option) => option  === null || option.numFactura === null}
                        getOptionLabel={(option) => option&&option.numFactura?option.numFactura:"SIN FACTURAS"}
                        
                        renderInput={(params) => <TextField {...params} label="Numero factura" variant="outlined" />}
                        />
                </Grid>
                <Grid item xs={1}/>
                <Grid className={classes.grid }  item xs={6}>
                    <TextField
                        fullWidth
                        disabled={value === null || value==='' || counter.data.isDefaulter?true:false}
                        onChange = {(event) => {setMotivo(event.target.value)}}
                        inputRef={test}
                        value = {motivo}
                        rows={5}
                        id="outlined-textarea"
                        label="Motivos de devolucion"
                        multiline
                        variant="outlined"
                    />
                </Grid>
            </Grid>
            <Divider />
            <div className={classes.rowTitle}>Seleccione los productos a devolver</div>
            <div className={classes.rowTitle2}>Productos a devolver</div>  
            <Grid className={classes.grid2 } container spacing={2}>
                <Grid  item xs={5}>
                    <Paper className={classes.tables } variant="outlined" >
                        <form onSubmit = {(event)=> {event.preventDefault(); }}>
                            {valueNum && valueNum.lineasFacturas?valueNum.lineasFacturas.map((row)=>    
                                row.cantidad !== 0? (
                                    <div>
                                        <Grid   container spacing={3}>
                                            <Grid item xs={5}>
                                                <Typography className={classes.gridproductos } variant='body1'>{row.producto.nombre} (x{row.cantidad})</Typography>
                                            </Grid>
                                            <Grid item xs={5}>
                                                <Typography className={classes.gridproductos } variant='body1'>Precio Compra: {(row.precio/row.cantidad).toFixed(2)}€ / {row.producto.unidad}</Typography>
                                            </Grid>
                                            <Grid item xs={2}>
                                            <Button className={classes.gridboton } variant="contained" type="submit" disabled={lineasSelected.indexOf(row)!==-1} size="small" color="primary" onClick = {() => {handleClickSelected(row)}}>Devolver</Button>
                                            </Grid>
                                        </Grid>
                                        <Divider  />
                                    </div>  
                                ):null     
                            ):()=>null}
                        </form> 
                    </Paper>
                </Grid>
                <Grid className={classes.arrowDiv } item xs={1}>
                <ArrowForwardIcon className={classes.arrow } style={{ fontSize: 80 }} />
                </Grid>
                <Grid  item xs={5}>
                    <Paper className={classes.tables } variant="outlined">
                        <form className={classes.tablesform } onSubmit = {(event)=> {event.preventDefault(); }}>
                            {lineasSelected.map((row)=>                            
                                <div>
                                    <Grid  container spacing={1}>
                                        <Grid item xs={1}>
                                            <Button className={classes.gridproductos } className={classes.iconButton }  type="submit" disabled={lineasSelected.indexOf(row)===-1} size="small" color="primary" onClick = {() => {handleClickDeselected(row)}}>
                                                <ClearIcon />
                                            </Button>
                                        </Grid>
                                        <Grid item xs={5}>
                                            <Typography className={classes.gridproductos } variant='body1'>{row.producto.nombre}</Typography>
                                        </Grid>
                                        <Grid item xs={4}>
                                            <Typography className={classes.gridproductos } variant='body1'>Cantidad: <TextField
                                                    style = {{ height:12 , width:60, display:"inline-block", position:"relative", top: -4}}
                                                    id={row.id}
                                                    type="number"
                                                    onKeyPress={(e)=>{e.target.keyCode === 13 && e.preventDefault();}}
                                                    defaultValue = {1}
                                                    onChange={(event)=>{
                                                        handleChangeValueSelected(row.producto.id,event.target.value)
                                                    }}
                                                    inputProps={{ min: 1, max: row.cantidad , style:{padding:5} }}
                                                    variant="outlined"
                                                    error={errores.filter(obj => {
                                                        return obj.id === row.producto.id
                                                    })[0]?true:false}
                                                    />                                        
                                                   
                                            </Typography>
                                              
                                        </Grid>                                      
                                        <Grid item xs={1}>
                                            <Grid container>
                                                <Grid item xs={2}><Divider orientation='vertical' /></Grid>
                                                    <Grid item xs={10}>
                                                        <Typography className={classes.gridproductos } variant='body1'> 
                                                            {valueSelected.filter(obj => {
                                                                        return obj.e1 === row.producto.id
                                                                    })[0]?((row.precio/row.cantidad)*valueSelected.filter(obj => {
                                                                        return obj.e1 === row.producto.id
                                                                    })[0].e2).toFixed(2):null}€
                                                        </Typography>
                                                    </Grid>          
                                                </Grid> 
                                            </Grid>
                                    </Grid>
                                    <Divider  />       
                                </div>                                      
                            )}
                        </form>
                        <Typography className={classes.total} variant='body1'> TOTAL: {total.toFixed(2)}€</Typography>
                    </Paper>
                </Grid>
                <Grid item xs={5} className={classes.bottomSpace } />
                <Grid item xs={5} className={classes.bottomSpace } />

                <Grid item xs={2} className={classes.bottomSpace } >
                    <form noValidate onSubmit={handleSubmit}>
                        <Button classes={{root:classes.button , disabled:classes.disabled}}disabled={valueSelected.length > 0?false:true} type="submit" variant="contained" size="large" color="primary">
                            <Typography variant='body1' >CONFIRMAR</Typography>
                        </Button>
                    </form>
                </Grid >
                
            </Grid>
        </fieldset>
    )
}

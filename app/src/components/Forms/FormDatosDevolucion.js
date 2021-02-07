import React from 'react'
import { makeStyles } from '@material-ui/core/styles';

import Snackbar from '../Other/SnackBar'

import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import Divider from '@material-ui/core/Divider';
import Autocomplete from '@material-ui/lab/Autocomplete';
import TextField from '@material-ui/core/TextField';
import DateFnsUtils from '@date-io/date-fns';
import Button from '@material-ui/core/Button';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { loadClienteIsDefaulter, clearClienteIsDefaulter } from "../../redux/actions/dataActions"
import { getFacturasCliente, getFacturasClienteAndFecha,sendDevolucion } from "../../redux/actions/clienteActions"
import ArrowForwardRoundedIcon from '@material-ui/icons/ArrowForwardRounded';
import ChevronRightRoundedIcon from '@material-ui/icons/ChevronRightRounded';
import DoneRoundedIcon from '@material-ui/icons/DoneRounded';
import ClearIcon from '@material-ui/icons/Clear';

import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
  } from '@material-ui/pickers';



const useStyles = makeStyles({

    fieldset: {
        position: 'relative',
        borderRadius: 10,
        backgroundColor: '#f7f7f7',
        color: '#3d3d3d',
        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.15), 0 6px 20px 0 rgba(0, 0, 0, 0.14)',
        border: '2px solid #bdbdbd',
        padding: 20,
    },
    formDiv: {
        display: 'grid',
        gridTemplateRows: '1fr 1fr 1fr',
        gridTemplateColumns: '1fr 1fr',
        padding: '20px',
        gridGap: '10px 50px'
    },
    inputDiv:{
        width:"100%",
        height:"100%",
    },
    inputField: {
        height: '100%',
        width: '100%',
        margin: 0,
    },
    textInput: {
        backgroundColor: 'white',
        width: '100%',
        '&:disabled':{
            backgroundColor: '#f7f7f7'
        }
    },
    transferListDiv: {
        display: 'grid',
        gridTemplateColumns: '5fr 1fr 5fr',
        gridTemplateRows: '0.5fr 10fr 1fr',
        gridRowGap: 5,
        justifyItems: 'center',
        alignItems: 'center',
        padding: '20px 20px 0px 20px',
    },
    transferList: {
        width: 'calc(100% - 24px)',
        backgroundColor: 'white',
        padding: 10,
        border: '2px solid #bdbdbd',
        borderRadius: 20,
        maxHeight: 296,
        minHeight: 296,
        overflowY: 'auto'
    },
    lineaDevolucion1: {
        display: 'grid',
        gridTemplateColumns: '1fr 4fr 4fr 1fr',
        gridTemplateRows: '1fr',
        gridColumnGap: 10,
        justifyItems: 'center',
        alignItems: 'center',
        borderBottom: '1px solid #bdbdbd',
        padding: '10px 10px',
    },
    button:{
        color: 'white',
        height: 60,
        width: 30,
        position:'absolute',
        borderRadius: '50%',
        right: -25,
        bottom: -25,
        '&$disabled' : {
            backgroundColor: '#f2f2f2',
            border: '1px solid #dbdbdb'
        }
    },
    disabled:{
    },
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
            <div className = {classes.formDiv}>
                <div className={classes.inputDiv} style = {{gridRow: 1, gridColumn: 1}}>
                    <Autocomplete
                        className = {classes.inputField}
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
                        renderInput={(params) => <TextField {...params} label="Cliente" variant="outlined" className={classes.textInput}/>}
                    />
                </div>
                <div className={classes.inputDiv} style = {{gridRow: 2, gridColumn: 1}}>
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                            <KeyboardDatePicker
                                className = {classes.inputField}
                                disabled={value === null || value==='' || counter.data.isDefaulter?true:false}
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
                                inputVariant = 'outlined'
                                inputProps = {{className : classes.textInput }}
                            />       
                    </MuiPickersUtilsProvider>
                </div>
                <div className={classes.inputDiv} style = {{gridRow: 3, gridColumn: 1}}>
                    <Autocomplete
                        className = {classes.inputField}
                        disabled={value === null || value==='' || counter.data.isDefaulter}
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
                        renderInput={(params) => 
                            <TextField {...params}
                                label="Numero factura"
                                variant="outlined"
                                className={classes.textInput}
                                style = {{backgroundColor : (value === null || value==='' || counter.data.isDefaulter) ? '#f7f7f7' : 'white'}}
                            />}
                        />
                </div>
                <div className={classes.inputDiv} style = {{gridRow: '1 / span 3', gridColumn: 2}}>
                    <TextField
                        className = {classes.inputField}
                        className={classes.textInput}
                        style = {{backgroundColor : (value === null || value==='' || counter.data.isDefaulter) ? '#f7f7f7' : 'white'}}
                        disabled={value === null || value==='' || counter.data.isDefaulter}
                        onChange = {(event) => {setMotivo(event.target.value)}}
                        inputRef={test}
                        value = {motivo}
                        rows={8}
                        id="outlined-textarea"
                        label="Motivos de devolucion"
                        multiline
                        variant="outlined"
                    />
                </div>
            </div>

            <div style = {{display: (valueNum === null || valueNum === '' || value === null || value==='' || counter.data.isDefaulter) ? 'none':'block'}}>
                
                <Divider />

                <div  className = {classes.transferListDiv}>

                    <div style = {{gridRow: 1, gridColumn: 1}}>
                        <Typography><b>Productos en la factura:</b></Typography>
                    </div>

                    <div style = {{gridRow: 1, gridColumn: 3}}>
                        <Typography><b>Productos a devolver:</b></Typography>
                    </div>

                    <form 
                    onSubmit = {(event)=> {event.preventDefault(); }} 
                    className = {classes.transferList} 
                    style = {{gridRow: 2, gridColumn: 1}}
                    >
                        {valueNum && valueNum.lineasFacturas?valueNum.lineasFacturas.map((row)=>    
                            row.cantidad !== 0? (
                                <div className = {classes.lineaDevolucion1}>
                                    <img src = {row.producto.urlimagen} style = {{height:'100%', width: '100%'}}></img>
                                    <Typography style = {{width: '80%', textAlign: 'left'}}>{row.producto.nombre} (x{row.cantidad})</Typography>
                                    <Typography style = {{width: '80%', textAlign: 'left'}}>Precio Compra: {(row.precio/row.cantidad).toFixed(2)}€ / {row.producto.unidad}</Typography>
                                    <Button style = {{width: '100%', color: 'white'}} color = "secondary" variant="contained" type="submit" disabled={lineasSelected.indexOf(row)!==-1} size="small" onClick = {() => {handleClickSelected(row)}}><ChevronRightRoundedIcon/></Button>
                                </div>  
                            ):null     
                        ):()=>null}
                    </form> 

                    <div style = {{gridRow: 2, gridColumn: 2}}>
                        <ArrowForwardRoundedIcon className={classes.arrow } style={{ fontSize: 80 }} />
                    </div>

                    <form 
                    onSubmit = {(event)=> {event.preventDefault()}}
                    className={classes.transferList }
                    style = {{gridRow: 2, gridColumn: 3}}
                    >
                        {lineasSelected.map((row)=>                            
                            <div className = {classes.lineaDevolucion1} style = {{gridTemplateColumns: '0.1fr 1fr 5fr 3fr 1.5fr'}}>
                                
                                <Button 
                                    type="submit" 
                                    disabled={lineasSelected.indexOf(row)===-1} 
                                    color="secondary" 
                                    onClick = {() => {handleClickDeselected(row)}}
                                    style = {{borderRight: '1px solid #bdbdbd', borderRadius: 0}}
                                >
                                    <ClearIcon />
                                </Button>

                                <img src = {row.producto.urlimagen} style = {{height:'100%', width: '100%'}}></img>
                                <Typography style = {{width: '80%', textAlign: 'left'}}>{row.producto.nombre}</Typography>
                                
                                <span style = {{display: 'inline-flex'}}>
                                    <Typography>Cantidad:</Typography>
                                    <TextField
                                        style = {{ height:10 , width:60, top: -2, left: 10}}
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
                                </span>
                                    
                                <Typography style = {{width: '100%', textAlign: 'right', borderLeft: '1px solid #bdbdbd'}}> 
                                    {valueSelected.filter(obj => {
                                                return obj.e1 === row.producto.id
                                            })[0]?((row.precio/row.cantidad)*valueSelected.filter(obj => {
                                                return obj.e1 === row.producto.id
                                            })[0].e2).toFixed(2):null}€
                                </Typography>
                            </div>                                      
                        )}
                    </form>

                    <div style = {{gridRow: 3, gridColumn: 3, width: '100%', textAlign: 'right'}}>
                        <Typography variant = 'h5' style = {{paddingRight:10}}><b> TOTAL: </b>{total.toFixed(2)} €</Typography>
                    </div>
                </div>           
            </div>

            <Button 
                onClick = {handleSubmit}
                classes={{root:classes.button , disabled:classes.disabled}}disabled={valueSelected.length > 0?false:true} type="submit" variant="contained" size="large" color="primary">
                <DoneRoundedIcon/>
            </Button>
        </fieldset>
    )
}

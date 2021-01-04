import React from 'react'
import { makeStyles } from '@material-ui/core/styles';

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
import { getFacturasCliente, getFacturasClienteAndFecha,sendDevolucion } from "../redux/actions/clienteActions"
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
    arrowDiv:{
        position: 'relative',
        //top: '50%',
        alignItems:'center',
        display:'flex',
        justifyContent:'center',
    },
    arrow:{

    },
    tables:{
        minHeight:260
    },


});

export default function FormDatosDevolucion() {

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [value, setValue] = React.useState(null);
    const [valueNum, setValueNum] = React.useState(null);
    const [inputValue, setInputValue] = React.useState('');
    const [fechaInicio, setFechaInicio] = React.useState(null);
    const [open, setOpen] = React.useState(false);
    const [options1, setOptions1] = React.useState([]);
    const [options2, setOptions2] = React.useState([]);
    const [motivo, setMotivo] = React.useState('');
    const [lineasSelected,setLineasSelected] = React.useState([])
    const [valueSelected,setValueSelected] = React.useState([])
    const [n,setN] =React.useState(0)
    const classes = useStyles();

    let test = React.useRef(null)
    const handleFechaInicioChange = (date) => {
        setFechaInicio(date);
        
    };

    const handleClickSelected = (row) => {
        let temp = lineasSelected
        let temp2 = valueSelected
        console.log(row)
        if(temp.indexOf(row) == -1){
            temp.push(row)
            temp2.push({e1: row.producto.id , e2:1})
        }
        setLineasSelected(temp);
        setValueSelected(temp2);
    }

    const handleChangeValueSelected = (producto,value) => {
        let temp = valueSelected
        const index = temp.indexOf(temp.filter(obj => {
            return obj.e1 === producto
          })[0]);
        if(index != -1){
            temp.splice(index,1,{e1:producto , e2: parseInt(value)})
        }
        setValueSelected(temp);
    }

    const handleClickDeselected = (row) => {
        let temp = lineasSelected
        let temp2 = valueSelected

        const index = temp.indexOf(row);
        const index2 = temp2.indexOf(temp2.filter(obj => {
            return obj.e1 === row.id
          })[0]);
        if(index != -1){
            temp.splice(index,1)
            temp2.splice(index2,1)
        }
        setLineasSelected(temp);
        setValueSelected(temp2);
    }

    const handelLoadFacturas= (value,fecha = null) => {
        if(fecha ===null){
            if(value !== null){
                dispatch(getFacturasCliente(value.id))
            }
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
        setInputValue('');
        setMotivo('');
        setFechaInicio(null);
        setLineasSelected([])
        setValueSelected([])
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = {
            e1: valueNum,
            e2: motivo,
            e3: valueSelected
        }
        //dispatch(sendDevolucion(formData))
        //console.log(formData)
        setValue(null)
        setValueNum(null);
        setInputValue('');
        setMotivo('');
        setFechaInicio(null);
        setLineasSelected([]);
        setValueSelected([]);
    }


    return (
        <Paper className={classes.root}>
            <Typography variant='subtitle1' className={classes.subtituloCatalogo}>Datos de la devolucion</Typography>
            <Divider />
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
                            disabled={value === null || value===''?true:false}
                            className={classes.fechaLeft}
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
                        disabled={value === null || value===''?true:false}
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
            <div>TABLA 1</div> 
            <Grid className={classes.grid2 } container spacing={2}>
                <Grid  item xs={5}>
                    <Paper className={classes.tables } variant="outlined" >
                        <form onSubmit = {(event)=> {event.preventDefault(); setN(n+1)}}>
                            <br/>
                            <Divider  />
                            {valueNum && valueNum.lineasFacturas?valueNum.lineasFacturas.map((row)=>    
                                row.cantidad != 0? (
                                    <div>
                                        {row.producto.nombre} (x{row.cantidad}) 
                                        Precio Compra: {row.precio/row.cantidad}€ / {row.producto.unidad} 
                                        <Button variant="contained" type="submit" disabled={lineasSelected.indexOf(row)!=-1} size="small" color="primary" onClick = {() => {handleClickSelected(row)}}>Devolver</Button>
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
                        <form onSubmit = {(event)=> {event.preventDefault(); setN(n+1)}}>
                            <br/>
                            <Divider  />
                            {lineasSelected.map((row)=>    
                                <div>
                                    <Button className={classes.iconButton }  type="submit" disabled={lineasSelected.indexOf(row)==-1} size="small" color="primary" onClick = {() => {handleClickDeselected(row)}}>
                                        <ClearIcon />
                                    </Button>
                                    {row.producto.nombre} 
                                       Cantidad: <TextField
                                                style = {{ height:12 , width:60, display:"inline-block"}}
                                                id={row.id}
                                                type="number"
                                                defaultValue = {1}
                                                value = {valueSelected.filter(obj => {
                                                    return obj.e1 === row.id
                                                  }).e2}
                                                onChange={(event)=>{
                                                    handleChangeValueSelected(row.producto.id,event.target.value)
                                                }}
                                                inputProps={{ min: 1, max: row.cantidad , style:{padding:5} }}
                                                variant="outlined"
                                                />                                        
                                                {row.precio/row.cantidad}€
                                                <Divider  />
                                </div>                                      
                            )}
                        </form> 
                    </Paper>
                </Grid>
                <Grid item xs={5} />
                <Grid item xs={5} />

                <Grid item xs={2}>
                <form noValidate onSubmit={handleSubmit}>
                    <Button style = {{position:'relative',top:25}} type="submit" variant="contained" size="large" color="primary">
                        <Typography variant='body1' >CONFIRMAR</Typography>
                    </Button>
                </form>
                </Grid >
                
            </Grid>
        </Paper> 
    )
}

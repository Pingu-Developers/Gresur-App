import React from 'react';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import { makeStyles } from '@material-ui/core/styles';
import { blue } from '@material-ui/core/colors';
import Button from '@material-ui/core/Button';
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import Autocomplete from "@material-ui/lab/Autocomplete";
import CircularProgress from "@material-ui/core/CircularProgress";

import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { cargaEstanterias, clearEstanterias,nuevoProducto } from "../redux/actions/nuevoProductoActions";
//import {  } from "../redux/actions/clienteActions";

const useStyles = makeStyles({
    avatar: {
      backgroundColor: blue[100],
      color: blue[600],
    },
    Buttons: {
        margin: "1% 12% 6% 12%",
        display: 'inline-block'
    }
  });
  
export default function EstanteriaConfirm(props) {

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const classes = useStyles();
    const { onClose,onCloseConfirm , open, product } = props;
    const [categoria, setCategoria] = React.useState(null);
    const [openAuto,setOpenAuto]  = React.useState(false);
    
    const valid = product.nombre && product.nombre.length > 0 && product.unidad.length > 0 && product.stockSeguridad >= 0 && product.precioVenta >= 0 && product.precioCompra >= 0
        && product.alto > 0 && product.ancho > 0 && product.profundo > 0 && product.pesoUnitario >= 0 && categoria;

    const handleClose = () => {
      onClose();
    };

    const handleConfirm = () =>{
        const nuevoprod = {
            ...product,
            estanteria:categoria,
        }
        const nuevoProd2 = {
            alto: 2,
            ancho: 1,
            descripcion: "eqe",
            estanteria: {id: 1, categoria: "AZULEJOS", capacidad: 650},
            nombre: "Papa",
            pesoUnitario: 1,
            precioCompra: 2,
            precioVenta: 1,
            profundo: 1,
            stock: 0,
            stockSeguridad: 1,
            unidad: "M2",
            urlimagen: "https://firebasestorage.googleapis.com/v0/b/upload-images-gresur.appspot.com/o/pictures%2Fcamion.jpg?alt=media&token=ad8c3191-844f-43c8-835f-eb5608398ded"
        }
        if(valid){
            console.log(nuevoprod)
            console.log(nuevoProd2)
            dispatch(nuevoProducto(nuevoProd2))
            onCloseConfirm();
        }   
    };

    const handleCategoria = (data) =>{
        setCategoria(data);
    };
    
    const loading = openAuto && counter.nuevoProducto.estanterias.size === 0;
    return (
      <Dialog onClose={handleClose} aria-labelledby="simple-dialog-title" open={open}>
        <DialogTitle style={{ position: "relative",alignItems: "center",display: "flex",justifyContent: "center"}} id="simple-dialog-title">
            Selecione una estanteria para el nuevo producto
        </DialogTitle>
        <Grid container>
            <Grid  style={{display: "flex",justifyContent: "center"}} item xs={12}>
                <Typography
                    style={{fontWeight:'bold',width:"70%",textAlign:'center'}}
                    variant="body1"
                >
                    {product.nombre}
                </Typography>
            </Grid>
            <Grid  style={{height:370,display: "flex",justifyContent: "center"}} item xs={12}>
                <img
                    style={{width:"43%",padding:"5%",height:"84%"}}
                    src={product.urlimagen}
                    alt="IMAGEN"
                />
            </Grid>
            <Grid  style={{display: "flex",justifyContent: "center"}} item xs={12}>
                <Autocomplete
                    id="Estanteria"
                    size="small"
                    style={{
                        width: 200,
                        margin: 10,
                        marginLeft: 20,
                        display: "inline-block",
                    }}
                    open={openAuto}
                    value={categoria}
                    onChange={(event, newValue) => {
                        handleCategoria(newValue);
                    }}
                    onOpen={() => {
                        console.log(product)
                        dispatch(cargaEstanterias())
                        setOpenAuto(true)
                    }}
                    onClose={() => {
                        dispatch(clearEstanterias())
                        setOpenAuto(false)
                    }}
                    getOptionSelected={(option, value) => option.categoria === value.categoria}
                    getOptionLabel={(option) => option.categoria}
                    options={counter.nuevoProducto.estanterias}
                    loading={loading}
                    renderInput={(params) => (
                        <TextField
                        {...params}
                        label="Estanteria"
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
            </Grid>
            <Grid item xs={12} style={{ position: "relative",alignItems: "center",display: "flex",justifyContent: "center"}}>
                <Button disabled={!valid} className = {classes.Buttons} variant="contained" color="primary" onClick={handleConfirm}>
                    Si
                </Button>
                <Button className = {classes.Buttons} variant="contained" color="primary" onClick={handleClose}>
                    No
                </Button>
            </Grid>
        </Grid>
      </Dialog>
    );
  }
  
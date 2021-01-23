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
//import {  } from "../redux/actions/dataActions";
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
  
export default function ConfirmDialog(props) {
    const classes = useStyles();
    const { onClose, open, onConfirm, product } = props;
    const [categoria, setCategoria] = React.useState(null);
    const [openAuto,setOpenAuto]  = React.useState(false);
  
    const handleClose = () => {
      onClose();
    };

    const handleConfirm = () =>{
        //onConfirm(elementID);
        onClose();
    };

    const handleCategoria = (event) =>{
        setCategoria(event.target.value);
    };

    const loading = openAuto;
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
                    id="proveedor"
                    size="small"
                    style={{
                        width: 200,
                        margin: 10,
                        marginLeft: 20,
                        display: "inline-block",
                    }}
                    open={openAuto}
                    value={categoria}
                    onChange={handleCategoria}
                    onOpen={() => {
                        setOpenAuto(true)
                    }}
                    onClose={() => {
                        setOpenAuto(false)
                    }}
                    getOptionSelected={(option, value) => option.name === value.name}
                    getOptionLabel={(option) => option.name}
                    //options={}
                    loading={loading}
                    renderInput={(params) => (
                        <TextField
                        {...params}
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
            </Grid>
            <Grid item xs={12} style={{ position: "relative",alignItems: "center",display: "flex",justifyContent: "center"}}>
                <Button className = {classes.Buttons} variant="contained" color="primary" onClick={handleConfirm}>
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
  
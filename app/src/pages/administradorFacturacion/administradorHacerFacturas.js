import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

import CompraMaterial from './operaciones/compraMaterial'

//Redux stuff
import { connect } from 'react-redux';
import { getProveedores, clearProveedores, newProveedores } from '../../redux/actions/proveedorActions';
import { getProductosPaginados, clearProductosPaginados } from '../../redux/actions/productoActions';
//MUI stuff
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';



const style = theme =>({
    root: {
        flexGrow: 1,
      },
    paper: {
        padding: theme.spacing(1),
        color: theme.palette.text.secondary,
        minHeight:260,
        maxHeight:260,
        overflowY:"auto",
        overflowX:"hidden",
    },
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        backgroundColor:"rgba(0,0,0,0.3)"
    }, 
    formControl: {
        margin: theme.spacing(2),
        minWidth: 220,
      },
})

class facturas extends Component {

    constructor(props) {
        super(props);
        this.state = {
            valueOperacion:1,
        };
        this.handleChangeOperacion = this.handleChangeOperacion.bind(this)
    }

    handleChangeOperacion(event){
        this.setState({
            valueOperacion:event.target.value
        })
    }


    render() {

        const { classes,UI:{loading}} = this.props;

        return (
            <div className={classes.root}>
                <Backdrop className={classes.backdrop} 
                    open={loading}
                    >
                        <CircularProgress color="secondary" />
                </Backdrop>
                <Grid container spacing={1}>

                    <Grid xs={12} container spacing={2}>
                        <Grid item xs={2}/>
                        <Grid item xs={4}>
                            <FormControl className={classes.formControl}>
                                <InputLabel id="lableOperacion">Operación</InputLabel>
                                <Select
                                    labelId="lableOperacion"
                                    id="selectOperacion"
                                    name="valueOperacion"
                                    value={this.state.valueOperacion}
                                    onChange={this.handleChangeOperacion}
                                >
                                    <MenuItem value={1}>Compra de material</MenuItem>
                                    <MenuItem value={2}>Gastos de vehiculos</MenuItem>
                                    <MenuItem value={3}>Otro</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                    </Grid>
                    
                   
                    <Grid xs={12} container spacing={0}>
                    {this.state.valueOperacion===1?
                        <CompraMaterial/>:null}
                        
                    {this.state.valueOperacion===2?
                        <Grid xs={12} container spacing={3}>
                            <Grid item xs={4}>
                                <Paper className={classes.paper}>2</Paper>
                            </Grid>
                            <Grid item xs={4}>
                                <Paper className={classes.paper}>item</Paper>
                            </Grid>
                            <Grid item xs={4}>
                                <Paper className={classes.paper}>item</Paper>
                            </Grid>
                        </Grid>:null}
                    {this.state.valueOperacion===3?
                        <Grid xs={12} container spacing={3}>
                            <Grid item xs={4}>
                                <Paper className={classes.paper}>3</Paper>
                            </Grid>
                            <Grid item xs={4}>
                                <Paper className={classes.paper}>item</Paper>
                            </Grid>
                            <Grid item xs={4}>
                                <Paper className={classes.paper}>item</Paper>
                            </Grid>
                        </Grid>:null}
                    </Grid>
                </Grid>
            </div>
        )
    }
}

facturas.propTypes = {

}

const mapStateToProps = (state) => ({

    UI:state.UI
})

const mapActionsToProps = {

}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(facturas))

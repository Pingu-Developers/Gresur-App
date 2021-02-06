import React, { Component } from 'react';
import withStyles from '@material-ui/core/styles/withStyles';

import CompraMaterial from './operaciones/compraMaterial'
import GastosVehiculos from './operaciones/gastosVehiculos'
import Otro  from './operaciones/otro';

//Redux stuff
import { connect } from 'react-redux';

//MUI stuff
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
    container: {
        padding:25,
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

                    <Grid xs={12} style={{borderBottom:"1px solid #E0E0E0"}} container spacing={2}>
                        <Grid item xs={2}>
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
                    
                   
                    <Grid xs={12} className={classes.container} container spacing={0}>
                    {this.state.valueOperacion===1?
                        <CompraMaterial />:null}                        
                    {this.state.valueOperacion===2?
                        <GastosVehiculos />:null}
                    {this.state.valueOperacion===3?
                        <Otro />:null}
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

import React, { Component } from 'react';
import withStyles from '@material-ui/core/styles/withStyles';

import CompraMaterial from './operaciones/compraMaterial'
import GastosVehiculos from './operaciones/gastosVehiculos'
import Otro  from './operaciones/otro';

//Redux stuff
import { connect } from 'react-redux';
import { clearLoading } from '../../redux/actions/dataActions';

//MUI stuff
import Grid from '@material-ui/core/Grid';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import Typography from "@material-ui/core/Typography";


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
      },
    container: {
        padding:25,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center'
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

class facturas extends Component {

    constructor(props) {
        super(props);
        this.state = {
            valueOperacion:0,
        };
        this.handleChangeOperacion = this.handleChangeOperacion.bind(this)
    }

    handleChangeOperacion(event){
        this.setState({
            valueOperacion:event.target.value
        })
    }

    componentWillUnmount(){
        this.props.clearLoading();
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

                <Typography className = {classes.titulo}>REGISTRAR UNA FACTURA</Typography>
                <Grid container spacing={1}>

                    <Grid xs={12} style={{borderBottom:"1px solid #E0E0E0"}} container spacing={2}>
                        <Grid item xs={2}>
                            <div style = {{display: 'inline-flex', alignItems: 'center'}}>
                                <Typography>Operacion: </Typography>
                                <FormControl className={classes.formControl}>
                                    <Select
                                        labelId="lableOperacion"
                                        id="selectOperacion"
                                        name="valueOperacion"
                                        value={this.state.valueOperacion}
                                        onChange={this.handleChangeOperacion}
                                        variant = "outlined"
                                        style = {{height: 35}}
                                    >
                                        <MenuItem value={0} disabled> -- Seleccionar --</MenuItem>
                                        <MenuItem value={1}>Compra de material</MenuItem>
                                        <MenuItem value={2}>Gastos de vehiculos</MenuItem>
                                        <MenuItem value={3}>Otro</MenuItem>
                                    </Select>
                                </FormControl>
                            </div>
                        </Grid>
                    </Grid>
                    
                   
                    <Grid xs={12} className={classes.container} container spacing={0}>
                    {this.state.valueOperacion===0?
                        <Typography
                        align="center"
                        style={{fontWeight:600,marginRight:20,width:450,display:"flex",justifyContent:"center"}}
                        variant="h2"
                        color="textSecondary"
                        >
                            SELECCIONE UNA OPERACION
                        </Typography>:null}  
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
    clearLoading,
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(facturas))

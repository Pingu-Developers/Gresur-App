import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import withStyles from '@material-ui/core/styles/withStyles';

import Typography from "@material-ui/core/Typography";
import TextField from '@material-ui/core/TextField';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';


const style = theme => ({
    root: {
        height:80,
        display:"grid",
        gridTemplateColumns:"0fr 0.1fr 3fr 0.5fr 1.4fr 0.1fr 0fr",
        gridColumnGap:"20px",
        alignItems:"center"
    },

})

export class RowLinea extends Component {
    static propTypes = {
        prop: PropTypes
    }
    handleChangeLinea(valor,unitario){

        const int = Number.isNaN(parseInt(valor))||parseInt(valor)<=0? 1: parseInt(valor)
        const linea = {...this.props.linea, cantidad:int, precio:int*unitario}

        this.props.handleChange(linea)
    }

    render() {
        const{classes,linea,data:{facturas:{lineasFacturas}}} = this.props
        const index = lineasFacturas.indexOf(lineasFacturas.filter(obj => obj.id === linea.id)[0])
        console.log(index)
        const unitario =  index !== -1? parseFloat(lineasFacturas[index].precio)/ parseFloat(lineasFacturas[index].cantidad):linea.producto.precioVenta;
        return (
            <div className={classes.root}>
                <div>

                </div>
                <img
                    style={{ height:75,width:75,borderRadius:10}}
                    src={linea.producto.urlimagen}
                    alt="IMAGEN"
                />
                <div style={{width:"100%"}}>
                    <Typography
                        style={{fontWeight:600}}
                        variant="h5"
                        color="textSecondary"
                        >
                        {linea.producto.nombre}
                    </Typography>
                </div>
                <Typography variant="subtitle1" color="textSecondary">
                    {unitario.toFixed(2)}€/{linea.producto.unidad.toLowerCase()}
                </Typography>
                <div style={{ display: 'grid', gridColumnGap: "10px",gridTemplateColumns:"1fr 1fr" ,alignItems:"center", justifyItems:"end"}}>
                    <TextField 
                        label="Cantidad" 
                        id="standard-size-small" 
                        type="number"
                        style={{maxWidth:115}}
                        inputProps={{
                            style:{backgroundColor:"white"}
                        }}
                        value={linea.cantidad}
                        onChange={(event)=>this.handleChangeLinea(event.target.value,unitario)}
                        variant="outlined" 
                        size="small" />
                    <Typography style={{display: "flex",alignItems: "flex-end",justifyContent: "flex-end"}} variant="subtitle1" color="textSecondary">
                        <b>Subtotal:</b>{(linea.cantidad*unitario).toFixed(2)}€
                    </Typography>
                </div>
                <div>
                    <IconButton style={{backgroundColor:"#FF8D8D",color:"white"}} onClick={()=>this.props.handleDelLinea(linea)}aria-label="delete" className={classes.margin}>
                        <DeleteIcon fontSize="small" />
                    </IconButton>
                </div>
                <div></div>   
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    data:state.data
})

const mapDispatchToProps = {
    
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(style)(RowLinea))
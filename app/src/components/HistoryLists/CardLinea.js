import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import withStyles from '@material-ui/core/styles/withStyles';

import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';

const style = theme => ({
    root: {
        height:"100%",
        margin:0,
        padding:10,

      },

      card: {
        display: 'grid',
        margin:10,
        padding:10,
        height:"100%",
        maxHeight:180,
        gridColumnGap: "20px",
        gridTemplateColumns:"1fr 2fr",
        backgroundColor:"#f8f8f8"
      },

      cardContent: {
        padding:0,
        display: 'grid',
        height:"100%",
        gridTemplateRows:"0.1fr 0.5fr 1fr",
        backgroundColor:"#f8f8f8"
      },
})

export class CardLinea extends Component {
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
        const unitario =  index !== -1? parseFloat(lineasFacturas[index].precio)/ parseFloat(lineasFacturas[index].cantidad):linea.producto.precioVenta;
        return (
            <div className={classes.root}>
                <Card elevation={3} className={classes.card} >
                    <CardMedia
                        style={{borderRadius:10}}
                        image={linea.producto.urlimagen}
                        title="Live from space album cover"
                    ></CardMedia>             
                    <CardContent className={classes.cardContent}>
                        
                        <Typography component="h6" variant="h6">
                            {linea.producto.nombre}
                        </Typography>
                        <Typography variant="subtitle1" color="textSecondary">
                            {unitario.toFixed(2)}€/{linea.producto.unidad.toLowerCase()}
                        </Typography>
                        <div style={{ display: 'grid', gridColumnGap: "10px",gridTemplateColumns:"0.6fr 1fr" ,alignItems:"end"}}>
                            <TextField 
                                label="Cantidad" 
                                id="standard-size-small" 
                                type="number"
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
                    </CardContent>
                </Card>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    data:state.data
})

const mapDispatchToProps = {
    
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(style)(CardLinea))
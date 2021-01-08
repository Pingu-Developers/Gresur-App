import React, { Component } from 'react';

//MATERIAL UI STUFF
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import GresurImg from '../images/Gresur.png';
import Grid from '@material-ui/core/Grid';
import Divider from '@material-ui/core/Divider';
import Button from "@material-ui/core/Button";
import PictureAsPdfIcon from '@material-ui/icons/PictureAsPdf';

//PDF STUFF
import Pdf from "react-to-pdf";

//REDUX STUFF
import { loadFactura} from '../redux/actions/dataActions';
import { connect } from 'react-redux';

const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: '#d4e6f1',
    color: theme.palette.common.black,
    fontWeight:'bold'
  },
  body: {
    fontSize: 14,
  }, table: {
    minWidth: 700,
  },
}))(TableCell);

const StyledTableRow = withStyles((theme) => ({
  root: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}))(TableRow);
const ref = React.createRef();


class TablaFactura extends Component {

    constructor(){
        super();
        this.state = {
        }
    }

    componentDidMount(){
      this.props.loadFactura(this.props.datos);
  }

    render(){
      const classes = this.props;
      return (
        <React.Fragment>
       
          <div style={{paddingRight:490,paddingLeft:660}}>
      {this.props.data.facturas.facturaEmitida===undefined?null:

    <React.Fragment >
      
    <div  ref={ref}>
      <Grid container spacing={3}>
          <Grid item xs>
            <Paper square={true} elevation={0}>
              <img style={{ marginLeft:10}} src = {GresurImg} width='100'></img>
              <Typography  style={{fontWeight:'bold',marginLeft:10}}>Azulejos Gresur</Typography>
            
          <Grid  style={{display:"inline-block",float:'right'}} >
              <Grid >
                <Typography style={{fontWeight:'bold'}} display='inline'> Nº Factura: </Typography> <Typography display='inline'>{this.props.data.facturas.facturaEmitida.numFactura} </Typography>
              </Grid>
              <Grid >
                <Typography style={{fontWeight:'bold'}}display='inline'> Fecha envío: </Typography> <Typography display='inline'>{this.props.data.facturas.fechaEnvio} </Typography>
              </Grid>
          </Grid>
            </Paper>
          </Grid>
      </Grid>   

      <Grid container spacing={3} style={{ marginLeft:10}}>
          <Grid item xs>
            <Paper  square={true} elevation={0}>
            <Typography style={{fontWeight:'bold'}} display="inline" > Tienda Exposicion: </Typography><Typography display="inline">Avda. Blas Infante</Typography>
            <Grid>
            <Typography style={{fontWeight:'bold'}} display="inline"> Almacén: </Typography><Typography display="inline">Las Columnas, s/n</Typography>
            </Grid>
            <Grid>
            <Typography style={{fontWeight:'bold'}} display="inline"> Telf. Móvil: </Typography><Typography display="inline">627 53 29 29</Typography>
            </Grid>
            <Typography display="inline-block"> 11687 EL GASTOR (Cádiz)</Typography>
            <Typography style={{fontWeight:'bold'}} display="inline"> E-mail: </Typography><Typography display="inline">atyca@hotmail.com  atyca61@gmail.com</Typography>
          
          <Grid  style={{display:"inline-block",marginTop:10, marginLeft:10}}>
           <Divider />
              <Grid>
                <Typography  style={{fontWeight:'bold'}} display="inline"> D/Dª: </Typography><Typography display="inline">{this.props.data.facturas.facturaEmitida.cliente.name}</Typography>
              </Grid>
              <Grid>
                <Typography  style={{fontWeight:'bold'}} display="inline"> Telf: </Typography><Typography display="inline" >{this.props.data.facturas.facturaEmitida.cliente.tlf} </Typography>
              </Grid>
              <Grid>
                <Typography  style={{fontWeight:'bold'}} display="inline"> Domicilio: </Typography><Typography display="inline" >{this.props.data.facturas.facturaEmitida.cliente.direccion}</Typography>
              </Grid>
            </Grid>
            </Paper>

          </Grid>
      </Grid>   


      <TableContainer component={Paper} style={{marginTop:20, marginLeft:4}}>
        <Table  aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell  align="center">Cantidad</StyledTableCell>
              <StyledTableCell align="center">Producto</StyledTableCell>
              <StyledTableCell align="center">Precio Unitario</StyledTableCell>
              <StyledTableCell align="center">Precio</StyledTableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {
              this.props.data.facturas.facturaEmitida.lineasFacturas.map((linea)=>
                <StyledTableRow>
                <StyledTableCell align="center">{linea.cantidad}</StyledTableCell>
                <StyledTableCell align="center">{linea.producto.nombre}</StyledTableCell>
                <StyledTableCell align="center">{linea.producto.precioVenta}€</StyledTableCell>
                <StyledTableCell align="center">{linea.precio}€</StyledTableCell>
                </StyledTableRow>
              )}
          </TableBody>

        </Table>
        <Grid  style={{display:"inline-block",float:'right',marginTop:10,marginLeft:10,marginRight:20}}  >

<Typography  style={{fontWeight:'bold'}} display="inline"> IMPORTE: </Typography><Typography display="inline" >{this.props.data.facturas.facturaEmitida.importe}€</Typography>
</Grid>
      </TableContainer >
 
      </div>

    </React.Fragment>  }
        <Pdf targetRef={ref} filename="FacturaAzulejosGresur.pdf" scale={0.9}>
    
        {({ toPdf }) => <Button style={{margin:10,float:'left',position:'relative',left:'40%'}}variant="contained" color="primary" onClick={toPdf}  
        startIcon={<PictureAsPdfIcon />}>Generar PDF  </Button>} 
        </Pdf>

    </div> </React.Fragment>
  );
}

}

TablaFactura.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadFactura: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    data: state.data,
})

const mapActionsToProps = {
  loadFactura,
}

export default connect(mapStateToProps, mapActionsToProps)((TablaFactura))

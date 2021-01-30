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
import GresurImg from '../images/Gresur_transparente.png';
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
       
          <div style={{padding : '20px 50px 20px 50px', marginTop: 50}}>
      {this.props.data.facturas.facturaEmitida===undefined?null:

    <React.Fragment >
      
    <div  ref={ref}>

      {/******************* ENCABEZADO *********************/}
      <div style = {{display : 'inline-flex', justifyContent:'space-between', alignItems: 'center', width:'100%', borderBottom: '1px solid #bdbdbd'}}>
        <img style={{ marginLeft:10}} src = {GresurImg} width='100'></img>
        <Typography style={{fontWeight:'bold', fontSize: 30}}>Azulejos Gresur</Typography>
      
        <div  style={{display:"inline-block",float:'right'}} >
            <span>
              <Typography style={{fontWeight:'bold'}} display='inline'> 
                Nº Factura: 
              </Typography> 
              <Typography display='inline'>
                {this.props.data.facturas.facturaEmitida.numFactura} 
              </Typography>
            </span>
            <br/>
            <span>
              <Typography style={{fontWeight:'bold'}}display='inline'> 
                Fecha emisión: 
              </Typography> 
              <Typography display='inline'>
                {this.props.data.facturas.fechaEmisión} 
              </Typography>
            </span>
        </div>
      </div>
      
      {/***************** CUERPO ******************/}
      <div>
          <div style = {{padding: '30px 0 10px 0'}}>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>Tienda Exposicion:</b> Avda. Blas Infante</Typography>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>Almacén:</b> Las Columnas, s/n</Typography>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>Telf. Móvil:</b> 627 53 29 29</Typography>
              
              <Typography style = {{padding: '5px 0 5px 0'}}> 11687 EL GASTOR (Cádiz)</Typography>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>E-mail:</b> atyca@hotmail.com    atyca61@gmail.com</Typography>
          </div>

          <Divider />

          <div style = {{paddingTop: 10}}>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>D / Dª : </b> {this.props.data.facturas.facturaEmitida.cliente.name} </Typography>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>Telf : </b> {this.props.data.facturas.facturaEmitida.cliente.tlf} </Typography>
              <Typography style = {{padding: '5px 0 5px 0'}}> <b>Domicilio : </b> {this.props.data.facturas.facturaEmitida.cliente.direccion} </Typography>
          </div>

          <Divider />

          <div style = {{paddingTop: 20}}>
            <Typography style = {{padding: '5px 0 5px 0'}}> <b>Fecha : </b> {new Date().toUTCString()} </Typography>
          </div>

      </div>   


      <TableContainer component={Paper} style={{marginTop:10, marginLeft:4}}>
        <Table  aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell align="center" style = {{padding: '8px 16px 8px 16px'}}>Cantidad</StyledTableCell>
              <StyledTableCell align="center" style = {{padding: '8px 16px 8px 16px'}}>Producto</StyledTableCell>
              <StyledTableCell align="center" style = {{padding: '8px 16px 8px 16px'}}>Precio Unitario <p style = {{fontSize: 10, margin: 0}}>(Sin IVA)</p></StyledTableCell>
              <StyledTableCell align="center" style = {{padding: '8px 16px 8px 16px'}}>Precio <p style = {{fontSize: 10, margin: 0}}>(Sin IVA)</p></StyledTableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {
              this.props.data.facturas.facturaEmitida.lineasFacturas.map((linea)=>
                <StyledTableRow>
                  <StyledTableCell align="center">{linea.cantidad}</StyledTableCell>
                  <StyledTableCell align="center">{linea.producto.nombre}</StyledTableCell>
                  <StyledTableCell align="center">{(linea.producto.precioVenta*(100/121)).toFixed(2)}€</StyledTableCell>
                  <StyledTableCell align="center">{(linea.precio*(100/121)).toFixed(2)}€</StyledTableCell>
                </StyledTableRow>
              )}
          </TableBody>
              <TableRow>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell>
                  <Typography style = {{float: 'inline-end'}}><b>IMPORTE:   </b></Typography>
                  <Typography style = {{float: 'inline-end'}}><b>IVA:   </b></Typography>
                </StyledTableCell>
                <StyledTableCell style = {{width: 'min-content'}}>
                    <Typography style = {{float: 'inline-end'}}>  {(this.props.data.facturas.facturaEmitida.importe*(100/121)).toFixed(2)}€</Typography>
                    <Typography style = {{float: 'inline-end'}}>  {(this.props.data.facturas.facturaEmitida.importe/121*21).toFixed(2)}€</Typography>
                </StyledTableCell>
            </TableRow>
            <TableRow>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell></StyledTableCell>
                <StyledTableCell>
                  <Typography style = {{float: 'inline-end'}}><b>TOTAL:   </b></Typography>
                </StyledTableCell>
                <StyledTableCell style = {{width: 'min-content'}}>
                    <Typography style = {{float: 'inline-end'}}>  {this.props.data.facturas.facturaEmitida.importe}€</Typography>
                </StyledTableCell>
            </TableRow>
        </Table>
          
      </TableContainer >
 
      </div>

    </React.Fragment>  }
        <Pdf targetRef={ref} filename="FacturaAzulejosGresur.pdf" scale={0.9}>
    
        {({ toPdf }) => <Button style={{margin:10,float:'left',position:'relative',left:'40%'}}variant="contained" color="primary" onClick={toPdf}  
        startIcon={<PictureAsPdfIcon />}>Descargar PDF  </Button>} 
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

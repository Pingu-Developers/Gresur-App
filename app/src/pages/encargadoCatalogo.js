import React, {Component} from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

import SnackCallController from '../components/Other/SnackCallController'

import { connect } from 'react-redux';
import { getProductosPaginados, clearProductosPaginados,getCategorias,clearCategorias,putNotificacion } from '../redux/actions/productoActions'

import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import CircularProgress from '@material-ui/core/CircularProgress';
import Backdrop from '@material-ui/core/Backdrop';
import Pagination from '@material-ui/lab/Pagination';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import WarningIcon from '@material-ui/icons/Warning';
import Tooltip from '@material-ui/core/Tooltip';
import DialogoEditarProductos from '../components/Dialogs/DialogoEditarProducto';
import PriorityHighIcon from '@material-ui/icons/PriorityHigh';
import IconButton from '@material-ui/core/IconButton';

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
            >
            {value === index && (
                <Box p={3}>
                <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}
  
TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

const style = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        display: 'flex',
        height: 384,
      },
    tabs: {
        borderRight: `1px solid ${theme.palette.divider}`,
        minWidth:180
    },
    tabBtn: {
        '&:hover':{
            backgroundColor: 'rgba(0, 188, 212, 0.2)',
        },
    },
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        backgroundColor:"rgba(255,255,255,0.2)"
    },
    tituloyForm: {
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        borderBottom: '1px solid #bdbdbd',
    },
    formBoton: {
        display: 'flex',
        alignItems: 'center',
        marginRight: 30,
    },
    formulario: {
        marginRight: 2
    },
    tituloCatalogo: {
        margin: '20px 0px 15px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        color: '#7a7a7a',

      },
      
    table:{
        width: '100%',
    },
    tablehead:{
        height:20,
        backgroundColor:"#d4e6f1"
    }
})



//Tooltip
const HtmlTooltip = withStyles((theme) => ({
    tooltip: {
        backgroundColor: '#f5f5f9',
        color: 'rgba(0, 0, 0, 0.87)',
        maxWidth: 220,
        fontSize: theme.typography.pxToRem(12),
        border: '1px solid #dadde9',
    },
}))(Tooltip);

const StyledTableRow = withStyles((theme) => ({
    root: {
      '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
      },
    },
  }))(TableRow);

class EncargadoCatalogo extends Component {

  constructor(props) {
    super(props);
    this.state = {
        activePage:1,
        value:null,
        mount:false,
        bajoStock:false,
        enviarNoti:false,
        productos:[],
        reload:false,
    };
    this.handlePageChange = this.handlePageChange.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleReload = this.handleReload.bind(this);
  }

  handleChange(newValue) {
        this.setState({
            activePage:1,
            value:newValue,
        })
    }

    handleReload() {
        this.setState({
            reload:true,
        })
    }

    componentDidUpdate(prevProps,prevState){
        console.log(this.state)

        if(!prevProps.productos.categorias[this.state.value] && this.props.productos.categorias[this.state.value]){
            this.props.getProductosPaginados(this.state.activePage,this.props.productos.categorias[this.state.value],null,5,'')
        }

        if((this.state.value !== prevState.value || this.state.activePage !== prevState.activePage) && this.props.productos.categorias[this.state.value]){
                this.setState({
                    bajoStock:false
                })
                this.props.clearProductosPaginados();

                this.props.getProductosPaginados(this.state.activePage,this.props.productos.categorias[this.state.value],null,5,'')
                        
        }
        if(prevState.reload !== this.state.reload && this.state.reload){

            this.setState({
                reload:false
            })
        }


        if(prevProps.productos.articlesDetails != this.props.productos.articlesDetails && this.props.productos.articlesDetails){
            const bajoStock = this.props.productos.articlesDetails.filter(
                producto => producto.stock < producto.stockSeguridad
            )

            if(bajoStock.length > 0){
                this.setState({
                    bajoStock:true
                })
            }
        }

        if(this.state.enviarNoti !== prevState.enviarNoti && this.state.enviarNoti ){
            this.props.putNotificacion(this.state.productos.slice(-1).pop())
            this.setState({
                enviarNoti:false,
            })
        }
        
    }

    componentDidMount () {
        this.props.getCategorias()
        this.setState({
            value:0
        })
    }

    handlePageChange(pageNumber) {
        this.setState({activePage: pageNumber})

    }
      

    componentWillUnmount(){
        this.props.clearCategorias()
        this.props.clearProductosPaginados();
    }

  render(){

    const { classes,UI:{loading,errors,enviado},productos:{articlesDetails,totalPages,categorias} } = this.props;
    return (
    <div>
        <SnackCallController  enviado = {enviado} message = {"Producto editado correctamente"} errors={errors} />
        <Backdrop className={classes.backdrop} 
            open={loading}
            >
                <CircularProgress color="secondary" />
        </Backdrop>
        <div className={classes.tituloyForm}>
            <Typography variant='h3' className={classes.tituloCatalogo}>CATÁLOGO DE PRODUCTOS</Typography>
                    
        </div>
        
        <div className={classes.root}>
            <Tabs
                orientation="vertical"
                indicatorColor="secondary"
                textColor="secondary"
                value={this.state.value}
                onChange={(event, newValue)=>{this.handleChange(newValue)}}
                aria-label="Vertical tabs example"
                className={classes.tabs}
            >
                {categorias?categorias.map(categoria=>
                    <Tab className = {classes.tabBtn} label={categoria === "BANOS"?"BAÑOS":categoria} {...a11yProps(categorias.indexOf(categoria))} />
                ):null}
            </Tabs>
            <TabPanel className={classes.table} value={this.state.value} index={this.state.value}>
                    <TableContainer component={Paper}>
                        <Table className={classes.table} size="medium" aria-label="a dense table">
                            <TableHead className={classes.tablehead}>
                                <TableRow>
                                    <TableCell></TableCell>
                                    <TableCell><b>Producto</b></TableCell>
                                    <TableCell></TableCell>
                                    <TableCell><b>Dimensiones (H x W x D)</b></TableCell>
                                    <TableCell><b>Precio de compra</b></TableCell>
                                    <TableCell><b>Precio de venta</b></TableCell>
                                    <TableCell><b>Beneficio por venta</b></TableCell>
                                    <TableCell><b>En stock</b></TableCell>
                                    <TableCell><b>Stock Seg.</b></TableCell>
                                    <TableCell><b>Unidad</b></TableCell>
                                    <TableCell align="center">
                                        <WarningIcon style={this.state.bajoStock?{color:"#F5B041"}:{color:"#C9C9C9"}} />
                                    </TableCell>
                                </TableRow>
                            </TableHead>

                            <TableBody>
                                {articlesDetails?articlesDetails.map((producto) =>
                                        <StyledTableRow key={producto.nombre}>
                                            <TableCell><DialogoEditarProductos producto={producto} page={this.state.activePage} categoria={this.props.productos.categorias[this.state.value]} handleReload={this.handleReload} /></TableCell>                                                 
                                            <HtmlTooltip
                                                title={
                                                    <React.Fragment>
                                                        <table>
                                                            <tr>
                                                                <th>
                                                                    <img width="120px" src={producto.urlimagen}></img>
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                {producto.descripcion}
                                                                </th>
                                                            </tr>
                                                        </table>
                                                    </React.Fragment>
                                                }>
                                                <TableCell style={{width:"20%"}}> {producto.nombre} </TableCell>
                                            </HtmlTooltip>
                                            <TableCell></TableCell>
                                            <TableCell>{producto.alto} x {producto.ancho} x {producto.profundo}</TableCell>
                                            <TableCell>{producto.precioCompra}€</TableCell>
                                            <TableCell>{producto.precioVenta}€</TableCell>
                                            <TableCell>{(parseFloat(producto.precioVenta) - parseFloat(producto.precioCompra)).toFixed(2)}€</TableCell>
                                            <TableCell>{producto.stock}</TableCell>
                                            <TableCell>{producto.stockSeguridad}</TableCell>
                                            <TableCell>{producto.unidad}</TableCell>
                                            <TableCell align="center">
                                                <IconButton disabled={producto.stock>=producto.stockSeguridad || this.state.productos.indexOf(producto) !== -1} aria-label="alert" onClick={()=>this.setState(state=>({enviarNoti:true,productos:[...state.productos,producto]}))}>
                                                    <PriorityHighIcon style={producto.stock<producto.stockSeguridad?{color:"#FF3D3D"}:null} />
                                                </IconButton>
                                            </TableCell>
                                        </StyledTableRow>
                                ):null}
                            </TableBody>
                        </Table>
                    </TableContainer>               
                <div className="d-flex justify-content-center" style={{margin:10}}>
                    {articlesDetails.length===0?null:<Pagination count={totalPages} hidePrevButton={this.state.activePage ===1} hideNextButton={this.state.activePage ===totalPages}  page={this.state.activePage} onChange={(event,newValue) => this.handlePageChange(newValue)} color="secondary" />}
                </div>
            </TabPanel>
        </div>
    </div>
      
    );
  }
}

const mapStateToProps = (state) => ({
    productos: state.productos,
    UI: state.UI
})

const mapActionsToProps = {
    getProductosPaginados,
    clearProductosPaginados,
    getCategorias,
    clearCategorias,
    putNotificacion
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(EncargadoCatalogo));
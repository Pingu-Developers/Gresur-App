import React, {Component} from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import withStyles from '@material-ui/core/styles/withStyles';

import MostradorProductos from '../components/MostradorProductos';

import { connect } from 'react-redux';
import { getProductosPaginados, clearProductosPaginados } from '../redux/actions/productoActions'

import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import SearchIcon from '@material-ui/icons/Search';
import CircularProgress from '@material-ui/core/CircularProgress';
import Backdrop from '@material-ui/core/Backdrop';
import Pagination from '@material-ui/lab/Pagination';


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
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        backgroundColor:"rgba(255,255,255,0.2)"
    },
    tituloyForm: {
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
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
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left'
      }, 
})

class Catalogo extends Component {

  constructor(props) {
    super(props);
    this.state = {
      activePage:1,
      value:0,
      busqueda:''
    };
    this.handlePageChange = this.handlePageChange.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleChangeBusqueda = this.handleChangeBusqueda.bind(this);
  }

  handleChange(newValue) {
        this.setState({
            activePage:1,
            busqueda:'',
            value:newValue
        })
    }

    componentDidUpdate(prevProps,prevState){
        if(this.state.value !== prevState.value || this.state.activePage !== prevState.activePage){
            this.props.clearProductosPaginados();
            if(this.state.value===0){
                if(this.state.busqueda){
                    this.props.getProductosPaginados(this.state.activePage, null,this.state.busqueda)
                }else{
                    this.props.getProductosPaginados(this.state.activePage)
                } 
            }else{
                switch (this.state.value) {
                    case 1:
                        this.props.getProductosPaginados(this.state.activePage,"AZULEJOS")
                        break;
                    case 2:
                        this.props.getProductosPaginados(this.state.activePage,"BANOS")
                        break;
                    case 3:
                        this.props.getProductosPaginados(this.state.activePage,"CALEFACCION")
                        break;
                    case 4:
                        this.props.getProductosPaginados(this.state.activePage,"LADRILLOS")
                        break;
                    case 5:
                        this.props.getProductosPaginados(this.state.activePage,"PINTURAS")
                        break;
                    case 6:
                        this.props.getProductosPaginados(this.state.activePage,"REVESTIMIENTOS")
                        break;
                    case 7:
                        this.props.getProductosPaginados(this.state.activePage,"SILICES")
                        break;                
                    default:
                        break;
                }
            }
        }
        if(this.state.busqueda !== prevState.busqueda){
            this.props.getProductosPaginados(this.state.activePage, null,this.state.busqueda)
        }
    }


    componentDidMount () {
        this.props.getProductosPaginados(this.state.activePage)
    }

    handlePageChange(pageNumber) {
        this.setState({activePage: pageNumber})

    }
    

    handleChangeBusqueda(event) {
        this.props.clearProductosPaginados();
        this.setState({
            activePage:1,
            value:0,
            busqueda:event.target.value
        })
                 
    }

    componentWillUnmount(){
        this.props.clearProductosPaginados();
    }

  render(){

    const { classes,UI:{loading},productos:{articlesDetails,totalPages} } = this.props;
    return (
    <div>
        <Backdrop className={classes.backdrop} 
            open={loading}
            >
                <CircularProgress color="secondary" />
        </Backdrop>
        <div className={classes.tituloyForm}>
            <Typography variant='h3' className={classes.tituloCatalogo}>CATÁLOGO DE PRODUCTOS</Typography>
                    
                <div className={classes.formBoton}>
                <TextField className={classes.formulario} label="Buscar productos" variant="standard" value={this.state.busqueda} onChange={(event)=>this.handleChangeBusqueda(event)}
                                InputProps={{
                                    startAdornment: (
                                      <InputAdornment position="start">
                                        <SearchIcon color='primary'/>
                                      </InputAdornment>
                                    ),
                                  }}/>
                </div>
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
                <Tab label="Todos" {...a11yProps(0)} />
                <Tab label="AZULEJOS" {...a11yProps(1)} />
                <Tab label="BAÑOS" {...a11yProps(2)} />
                <Tab label="CALEFACCION" {...a11yProps(3)} />
                <Tab label="LADRILLOS" {...a11yProps(4)} />
                <Tab label="PINTURAS" {...a11yProps(5)} />
                <Tab label="REVESTIMIENTOS" {...a11yProps(6)} />
                <Tab label="SILICES" {...a11yProps(7)} />
            </Tabs>
            <TabPanel value={this.state.value} index={this.state.value}>
                {articlesDetails?articlesDetails.map(producto => 
                    <MostradorProductos producto={producto}/>
                ):()=>null}

            <div className="d-flex justify-content-center">
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
    clearProductosPaginados
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(Catalogo));
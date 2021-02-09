import React, { Component } from 'react';
import PropTypes from 'prop-types';

//Redux stuff
import { connect } from 'react-redux';
import { loadPersonalContrato, clear} from '../redux/actions/dataActions';

//Mui
import withStyles from '@material-ui/core/styles/withStyles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

//Components
import InformacionEmpleado from '../components/HistoryLists/InformacionEmpleado';
import PopUpNuevoEmpleado from '../components/Dialogs/PopUpNuevoEmpleado';
import SnackCallController from '../components/Other/SnackCallController';

// VERTICAL TAB PANEL
function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
            style = {{overflowY: 'auto', height: '80vh'}}
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

//PAGE
const style = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        display: 'grid',
        gridTemplateColumns: '3fr 20fr',
        gridTemplateRows: '30fr',
        height: 384,
    },
    tituloForm: {
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        borderBottom: '1px solid #bdbdbd',
        margin: '30px 0px 20px 0px',
        padding: '0px 0px 15px 0px'
    },
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        color: '#7a7a7a',
        margin: '0px 0px 0px 20px'
      },
    tabs: {
        borderRight: `1px solid ${theme.palette.divider}`,
    },
    tabBtn: {
        '&:hover':{
            backgroundColor: 'rgba(0, 188, 212, 0.2)',
        },
    }
})
export class administradorPersonal extends Component {
    constructor(props){
        super(props);
        this.state = {
            value:0,
            data:[]
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(newValue) {
        this.setState({
            value:newValue
        })
    }

    componentDidMount(){
        this.props.loadPersonalContrato("TODOS");
    }

    componentDidUpdate(prevprops, prevstate) {
        if(prevstate.value !== this.state.value){
            console.log("Pls")
            this.props.clear()
            switch(this.state.value){
                case 0:
                    this.props.loadPersonalContrato("TODOS"); break;
                case 1:
                    this.props.loadPersonalContrato("TRANSPORTISTA"); break;
                case 2:
                    this.props.loadPersonalContrato("DEPENDIENTE"); break;
                case 3:
                    this.props.loadPersonalContrato("ENCARGADO"); break;
                case 4:
                    this.props.loadPersonalContrato("ADMINISTRADOR"); break;
                default:
                    this.props.loadPersonalContrato("TODOS");
                    this.setState({value: 0}); break;
                    
            }
        }
    }

    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const {classes, data,UI:{errors,enviado}} = this.props;
        return (
           <div>
                <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
                <div className = {classes.tituloForm}>
                    <Typography variant = 'h3' className = {classes.titulo}>GESTION DE PERSONAL</Typography>
                    <PopUpNuevoEmpleado/>
                </div>

                <div className = {classes.root}>
                    <Tabs
                    orientation="vertical"
                    indicatorColor="secondary"
                    textColor="secondary"
                    value={this.state.value}
                    onChange={(event, newValue)=>{this.handleChange(newValue)}}
                    aria-label="Vertical tabs example"
                    className={classes.tabs}
                    >
                        <Tab label="Todos" {...a11yProps(0)} className = {classes.tabBtn}/>
                        <Tab label="Transportistas" {...a11yProps(1)} className = {classes.tabBtn}/>
                        <Tab label="Dependientes" {...a11yProps(2)} className = {classes.tabBtn}/>
                        <Tab label="Encargados de almacen" {...a11yProps(3)} className = {classes.tabBtn}/>
                        <Tab label="Administradores" {...a11yProps(4)} className = {classes.tabBtn}/>
                    </Tabs>

                    <TabPanel value={this.state.value} index={this.state.value}>
                        {data.contrato.length === 0 ? <p>No se han podido cargar los datos</p>:
                            data.contrato.map( (cont) => 
                                <InformacionEmpleado key={data.contrato.indexOf(cont)} datos = {cont} handleReload={this.handleChange} />  
                            )
                        }
                    </TabPanel>                    
                </div>
            </div>
        )
    }
}      

administradorPersonal.propTypes = {
    data: PropTypes.object.isRequired,
    loadPersonalContrato: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    data: state.data,
    UI: state.UI
})

const mapActionsToProps = {
    loadPersonalContrato,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorPersonal));
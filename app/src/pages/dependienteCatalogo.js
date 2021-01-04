import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import Button from '@material-ui/core/Button';
import Icon from '@material-ui/core/Icon';
import SendIcon from '@material-ui/icons/Send';

//Redux stuff
import { connect } from 'react-redux';
import { loadProductos } from '../redux/actions/dataActions';
import { loadProductosByNombre , clear } from '../redux/actions/dataActions';

//Componentes
import TablaCatalogoProductosDesplegable from '../components/TablaCatalogoProductosDesplegable';


const style = {

    tituloCatalogo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left'
      },

    tituloyForm: {
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between'
    },

    formulario: {
        marginRight: 2
    },

    formBoton: {
        display: 'flex',
        alignItems: 'center',
        marginRight: 30,
    }, 
    
    boton: {
        marginTop: 10,
    },

    botonAll: {
        marginTop: 10,
        marginLeft: 10
    }

  
}

class dependienteCatalogo extends Component {
    
    constructor(props){
        super(props);
        this.state ={
            data: [],
            value: ''
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleAll = this.handleAll.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
      }
    
    handleSubmit(event) {
        this.state.value===''?this.props.loadProductos(): this.props.loadProductosByNombre(this.state.value);
    }

    handleAll(event) {
        this.props.loadProductos();
        this.setState({
            value:''
        })
    }

    componentDidMount(){
        this.props.loadProductos();
    }

    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const {classes, data} = this.props;
        
        return (
            <div>
                <div className={classes.tituloyForm}>
                    <Typography variant='h3' className={classes.tituloCatalogo}>CATÁLOGO DE PRODUCTOS</Typography>
                    
                    <div className={classes.formBoton}>
                        <TextField className={classes.formulario} label="Buscar productos" variant="standard" value={this.state.value} onChange={this.handleChange}
                                onKeyPress={(event) => event.key==='Enter'? this.handleSubmit(event): null} />
                        <Button className={classes.boton} onClick={this.handleSubmit} variant='contained' color='primary'>Buscar</Button>
                        <Button className={classes.botonAll} onClick={this.handleAll} variant='contained' color='secondary'>MOSTRAR TODOS</Button>
                    </div>

                </div>
                    <div className={classes.main}>
                        {data === undefined? null:<TablaCatalogoProductosDesplegable data = {data}/>}
                    </div>
            </div>
        )
    }
}

dependienteCatalogo.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadProductos: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadProductos,
    loadProductosByNombre,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(dependienteCatalogo))

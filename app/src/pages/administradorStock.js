import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Divider from '@material-ui/core/Divider';

//Redux stuff
import { connect } from 'react-redux';
import { loadAlmacenGestion, clear} from '../redux/actions/dataActions';
//Components
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';import { Typography } from '@material-ui/core';
import AdminEditAlmacenFSDialog from '../components/Dialogs/AdminEditAlmacenFSDialog'


const style = {
    root: {
        width: '100%',
        display: 'inline-flex',
        justifyContent: 'space-around',
        overflowX: 'auto',
        overflowY: 'hidden',
        color: '#7a7a7a'
    },
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        color: '#7a7a7a',
        margin: '30px 0px 20px 0px',
        padding: '0px 0px 15px 20px',
        width: '100%',
        borderBottom: '1px solid #bdbdbd'
    },
    cardRoot: {
        position: 'relative',
        height: 'min-content',
        display: 'grid',
        gridTemplateRows: '0.5fr 8fr',
        backgroundColor: '#dadada',
        maxWidth: 565,
        width: '33vw',
        borderRadius: 20,
        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.15), 0 6px 20px 0 rgba(0, 0, 0, 0.14)',
        border: '2px solid #bdbdbd'
    },
    nombreAlmacen: {
        display: 'flex',
        alignItems: 'center',
        fontWeight: 'bold',
        fontSize: 20,
        paddingLeft: 20,
        width: 'calc(100% - 20px)',
        margin: '10px 0px 10px 0px',
    },
    cardBody: {
        backgroundColor: '#fafafa',
        display: 'grid',
        gridTemplateRows: '220px 0.5fr 3fr 0.9fr',
        padding: 20,
        borderRadius: 20,
        justifyItems: 'center',
    },
    ocupacionTable: {
        border: '1px solid #bdbdbd',
        height: '100%',
        width: '100%',
        maxHeight: 216,
        overflowY: 'hidden',
        borderRadius: 20,
        backgroundColor: 'white'
    },
    porcOcupado: {
        display: 'flex',
        margin: 0,
        paddingLeft: 20,
        width: '100%',
        textAlign: 'left',
        alignItems: 'center',
        fontSize: 25,
    },
    info:{
        display: 'flex',
        margin: 0,
        alignItems: 'center',
        fontWeight: 'bold',
        fontSize: 16,
        paddingLeft: 20,
        width: 'calc(100% - 20px)',
        margin: '10px 0px 10px 0px',
      },
      fila: {
        '&:nth-of-type(2n-1)': {
          backgroundColor: "#E3F2FC",
        },
        '&:nth-of-type(even)': {
            backgroundColor: "#FFFFFF",
          },
      },
}

class administradorStock extends Component {
    constructor(props){
        super(props);
        this.state ={
            data: []
        }
        this.updateView = this.updateView.bind(this);
    }

    componentDidMount(){
        this.props.loadAlmacenGestion();
    }

    updateView(){
        this.props.loadAlmacenGestion();
    }

    componentWillUnmount(){
        this.props.clear();
    }
    render() {
        const { classes } = this.props;
        const almacenes = this.props.data.gestionAlmacen;
        const imgAlmacen1 = 'https://firebasestorage.googleapis.com/v0/b/upload-images-gresur.appspot.com/o/pictures%2FAlmacen1.png?alt=media&token=835578f3-174a-4210-8d59-19392bf543c5'
        const imgAlmacen2 = 'https://firebasestorage.googleapis.com/v0/b/upload-images-gresur.appspot.com/o/pictures%2FAlmacen2.png?alt=media&token=ae4059a1-4f5c-4b97-95be-86102674288b'
        return (
            <div>
                <Typography variant = 'h3' className = {classes.titulo}>GESTION DE ALMACENES</Typography>
                {almacenes.length === 0 ? <div>No se han podido cargar los almacenes</div> : 
                    <div className = {classes.root}>
                        {almacenes.map((row) =>
                            <div className = {classes.cardRoot}>
                                <Typography className = {classes.nombreAlmacen}><b>Almacen {row.almacen.id}</b></Typography>
                                <div className = {classes.cardBody}>
                                    <img src = {row.almacen.id === 1 ? imgAlmacen1 : imgAlmacen2} style = {{borderRadius: 20}} height = '100%' width = '100%'></img>
                                    <Typography style = {{padding: '20px 0px 20px 0px'}}><b>Direccion:</b> {row.almacen.direccion}</Typography>
                                    <div className = {classes.ocupacionTable}>
                                    <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",paddingRight:10,gridColumnGap:15,backgroundColor:"#d4e6f1", height:57,borderBottom:"1px solid #B0D7F0"}}>
                                        <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Categoria</Typography>
                                        <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Ocupacion</Typography>
                                    </div>
                                    <div style={row.categorias.length<4?{height:165,overflowY:"hidden"}:{height:165,overflowY:"scroll"}}>
                                    {row.categorias.map((cat) =>
                                        <div className={classes.fila}>
                                            <div style={{display:"grid",gridTemplateColumns:"1fr 1fr ",height:55,paddingRight:10}}>
                                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",margin:0}} color="textSecondary" >{cat.categoria}</Typography>
                                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",margin:0}} color="textSecondary" >{cat.ocupacionEstanteria.toFixed(2)} %</Typography>
                                            </div>
                                            <Divider />
                                        </div>)}
                                    </div>
                                    </div>
                                    <Typography className = {classes.porcOcupado}><b style = {{marginRight: 10}}>Ocupado:</b> {row.ocupacionAlmacen.toFixed(2)} %</Typography>                  
                                </div>
                                <AdminEditAlmacenFSDialog almacenId = {row.almacen.id} updtFunc = {this.updateView}/>
                            </div> 
                        )}
                    </div>
                }
            </div>
        )
    }
}

administradorStock.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadAlmacenGestion: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    data: state.data
})

const mapActionsToProps = {
    loadAlmacenGestion,
    clear
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorStock))
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';

//Components
import TablaMostradorVehiculosSeguroITVReparacion from '../Tables/TablaMostradorVehiculosSeguroITVReparacion2';
import PopUpNuevoVehiculo from '../Dialogs/PopUpNuevoVehiculo';
import { loadVehiculosSeguroITVReparacionPaged , clear} from '../../redux/actions/dataActions';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import Pagination from '@material-ui/lab/Pagination';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';

const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
    },
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        color: '#7a7a7a',
        borderBottom: '1px solid #bdbdbd',
        width: '100%',
        marginRight:0
      },

      tituloyForm: {
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center'
      },
      tituloFiltro: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingRight: 20,
    },
    paginacionGrp: {
        display: 'inline-flex',
        alignItems: 'center'
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
        height: 30,
    },
}));


export default function VehiculosInfoAdmin(props) {
    const classes = useStyles();
    

    const counter = useSelector(state => state);
    const dispatch = useDispatch();
    const[pageNo,setPageNo]=React.useState(0)
    const[size,setSize]=React.useState(2)

    React.useEffect(()=>{
        dispatch(loadVehiculosSeguroITVReparacionPaged(pageNo,size))
    },[pageNo,size])

    const vehiculos = counter.data.vehiculosITVSeguroReparacion;
    return (
        <div>
            <div className={classes.tituloFiltro}>
                
                <Typography variant='h3' className={classes.titulo}>HISTORIAL DE PEDIDOS</Typography>

            </div>       
            <div className={classes.tituloFiltro}>
                <div className = {classes.paginacionGrp}>
                {vehiculos.articlesDetails && vehiculos.articlesDetails.length===0?null:
                    <FormControl className={classes.formControl}>
                        <Pagination count={vehiculos.totalPages} page={pageNo+1} onChange={(event,newValue) => setPageNo(newValue-1)} color="secondary" />
                    </FormControl>
                    }
                    <FormControl className={classes.formControl}  style={{minWidth:90,display:"flex",position:"relative",top:-7}}>
                        <div>
                            {vehiculos.articlesDetails && vehiculos.articlesDetails.length===0?null:
                            <div style = {{display: 'grid', gridTemplateColumns : '1.5fr 1fr'}}>
                                <div style={{height:"100%",display:"flex",alignItems:"center"}}>
                                <InputLabel id="demo-simple-select-placeholder-label-label">
                                    Nº Vehiculos:
                                </InputLabel>
                                </div>
                                <div style={{height:"100%",display:"flex",alignItems:"center"}}>
                                <TextField
                                    id="outlined-multiline-static"
                                    style={{width:70, gridColumn: 2}}
                                    className={classes.selectEmpty}
                                    value={size}
                                    onChange={(event)=>setSize(event.target.value)}
                                    type="number"
                                    variant = 'outlined'
                                    inputProps = {
                                        {
                                            style : {padding : '0px 0px 0px 10px', height: 30}
                                        }
                                    }
                                />
                                </div>
                            </div> }
                        </div>
                    </FormControl>
                </div>
                <PopUpNuevoVehiculo className={classes.boton}/>
            </div>
            <div className={classes.main}>
                {vehiculos === undefined? null:<TablaMostradorVehiculosSeguroITVReparacion datos = {vehiculos.articleDetails}/>}
            </div>
            {size===1?null:<div className={classes.tituloFiltro}>
                <div className = {classes.paginacionGrp}>
                {vehiculos.articlesDetails && vehiculos.articlesDetails.length===0?null:
                    <FormControl className={classes.formControl}>
                        <Pagination count={vehiculos.totalPages} page={pageNo+1} onChange={(event,newValue) => setPageNo(newValue-1)} color="secondary" />
                    </FormControl>
                    }
                    <FormControl className={classes.formControl}  style={{minWidth:90,display:"flex",position:"relative",top:-7}}>
                        <div>
                            {vehiculos.articlesDetails && vehiculos.articlesDetails.length===0?null:
                            <div style = {{display: 'grid', gridTemplateColumns : '1.5fr 1fr'}}>
                                <div style={{height:"100%",display:"flex",alignItems:"center"}}>
                                <InputLabel id="demo-simple-select-placeholder-label-label">
                                    Nº Vehiculos:
                                </InputLabel>
                                </div>
                                <div style={{height:"100%",display:"flex",alignItems:"center"}}>
                                <TextField
                                    id="outlined-multiline-static"
                                    style={{width:70, gridColumn: 2}}
                                    className={classes.selectEmpty}
                                    value={size}
                                    onChange={(event)=>setSize(event.target.value)}
                                    type="number"
                                    variant = 'outlined'
                                    inputProps = {
                                        {
                                            style : {padding : '0px 0px 0px 10px', height: 30}
                                        }
                                    }
                                />
                                </div>
                            </div> }
                        </div>
                    </FormControl>
                </div>
            </div>}
        </div>
    )
}
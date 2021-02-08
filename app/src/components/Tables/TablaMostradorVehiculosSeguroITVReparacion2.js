import React from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import MostradorVehiculosSeguroITVReparacion from '../HistoryLists/MostradorVehiculosSeguroITVReparacion';
import WarningIcon from '@material-ui/icons/Warning';
import Divider from '@material-ui/core/Divider';
import PopUpHistorialSegurosITV from '../Dialogs/PopUpHistorialSegurosITV';
import BuildIcon from '@material-ui/icons/Build';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import MoneyOffIcon from '@material-ui/icons/MoneyOff';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  borderMain: {
    margin:20,
    height:534,
    borderColor: '#bdbdbd',
    borderStyle: 'solid',
    borderRadius: 24,
    border: 2,
    backgroundColor: '#f7f7f7',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.15), 0 6px 20px 0 rgba(0, 0, 0, 0.14)',
    backgroundColor:"#dadada",
    display:"grid",
    gridTemplateRows:"0.3fr 2fr"
  },
  titulo:{
    display: 'flex',
    margin: 0,
    alignItems: 'center',
    fontWeight: 'bold',
    fontSize: 20,
    paddingLeft: 20,
    width: 'calc(100% - 20px)',
    margin: '10px 0px 10px 0px',
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
  cuerpo:{
    maxHeight:520,
    borderRadius: 24,
    border: 2,
    height:"100%",
    backgroundColor:"#fafafa",
    display:"grid",
    gridTemplateColumns:"1fr 1fr 1fr",
    gridTemplateRows:"1fr 1fr"
  },
  categoria:{
    height:200,
    margin:20,
    boxSizing:'content-box',
    borderRadius: 24,
    border: 2,
    borderColor: '#bdbdbd',
    borderStyle: 'solid',
    backgroundColor:"#FFFFFF",
  },
    warning: {
        width: 80,
        height: 80,
    },
    fila: {
        '&:nth-of-type(2n-1)': {
          backgroundColor: "#F7F0ED",
        },
        '&:nth-of-type(even)': {
            backgroundColor: "#FFFFFF",
          },
      },
}));

function Row(props) {
    const classes = useStyles();
    const datos = props.data;

    return (
        <div className={classes.fila}>
            <div style={{display:"grid",gridTemplateColumns:"1fr 1fr 3.5fr 1fr 0.5fr",height:55,paddingRight:10}}>
                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",margin:0}} color="textSecondary" >{datos.fechaEntradaTaller}</Typography>
                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",margin:0}} color="textSecondary" >{datos.fechaSalidaTaller?
                    <p>{datos.fechaSalidaTaller}</p>:
                    <div style={{display:"flex", justifyContent:"left",alignItems:"center"}}>
                        <p>En reparacion</p>
                        <WarningIcon color="primary" style={{marginLeft:10,height:15,width:15}}/>
                    </div>}</Typography>
                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",margin:0}} color="textSecondary" >
                    {datos.descripcion && datos.descripcion.length !== 0?datos.descripcion:"Sin descripcion"}
                </Typography>
                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",margin:0}} color="textSecondary" >{datos.recibidas.importe}€</Typography>
                <Typography variant='h6'align="center" className={classes.info} style={{fontWeight:"normal",margin:0, display:"flex",justifyContent:"center"}} color="textSecondary" >{datos.recibidas.estaPagada?
                    <AttachMoneyIcon style={{color:"#49C638"}}/>:
                    <MoneyOffIcon style={{color:"#FF3D3D"}}/>
                }</Typography>
            </div>
            <Divider />
        </div>
    )
}


export default function ControlledAccordions(props) {
  const classes = useStyles();
  const datos = props.datos;
  const [expanded, setExpanded] = React.useState(0);

  const handleChange = (panel) => (event, isExpanded) => { 
    setExpanded(isExpanded ? panel : false);
  };

  return (
    <div className={classes.root}>
      {
        (datos === undefined)? null : 
        datos.map((row) => 
          <div className={classes.borderMain}>
              <Typography variant='h5' className={classes.titulo} color="textSecondary" >{row.vehiculo.matricula}</Typography>
              <div className={classes.cuerpo}>
                    <div className={classes.categoria} style={{display:"grid",gridTemplateColumns:"0.6fr 0fr 1fr"}}>
                        <div style={{maxWidth:200,maxHeight:200}}>
                            <img style={{width:"100%",height:"100%"}} src={row.vehiculo.imagen} alt="image"/>
                        </div>
                        <Divider orientation="vertical" style={{height:"90%", margin:"auto"}}/>
                        <div style={{display:"grid",gridTemplateRows:"1fr 1fr 1fr",width:"100%" ,padding:"30px 0px"}}>
                            <div style={{display:"grid",gridTemplateColumns:"0.3fr 1fr",width:"100%" , gridColumnGap:20}}>
                                <Typography variant='h6' className={classes.info} color="textSecondary" >CLASE:</Typography>
                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",paddingLeft:0}} color="textSecondary" >
                                    {row.vehiculo.tipoVehiculo!=="CARRETILLA_ELEVADORA"?row.vehiculo.tipoVehiculo.charAt(0)+row.vehiculo.tipoVehiculo.slice(1).toLowerCase():"Carretilla elevadora"}
                                </Typography>
                            </div>
                            <div style={{display:"grid",gridTemplateColumns:"0.6fr 1fr",width:"100%" , gridColumnGap:20}}>
                                <Typography variant='h6' className={classes.info} color="textSecondary" >CAPACIDAD:</Typography>
                                <Typography 
                                    variant='h6' 
                                    className={classes.info} 
                                    style={{fontWeight:"normal",paddingLeft:0}} 
                                    color="textSecondary" >{row.vehiculo.capacidad}M<sup style={{ display:"flex",position:"relative",top:"-4px"}}>3</sup></Typography>
                            </div>
                            <div style={{display:"grid",gridTemplateColumns:"0.3fr 1fr",width:"100%" , gridColumnGap:20}}>
                                <Typography variant='h6' className={classes.info} color="textSecondary" >MMA:</Typography>
                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",paddingLeft:0}} color="textSecondary" >{row.vehiculo.mma}kg</Typography>
                            </div>         
                        </div>
                    </div>
                    {row.seguros[0]?
                    <div className={classes.categoria} style={{display:"grid",gridTemplateRows:"1fr 5fr ", height:200 ,padding:"0px 15px"}}>
                        <Typography variant='h6' align="center" className={classes.info} style={{display:"flex" , justifyContent:"center"}} color="textSecondary" >SEGURO</Typography>
                        <div style={{height:125}}>
                        <Divider />
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Compañia:</Typography>
                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",paddingLeft:0, margin:0}} color="textSecondary" >{row.seguros[0].compania}</Typography>
                            </div>
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Tipo:</Typography>
                                <Typography 
                                    variant='h6' 
                                    className={classes.info} 
                                    style={{fontWeight:"normal",paddingLeft:0 , margin:0}} 
                                    color="textSecondary" >{row.seguros[0].tipoSeguro}M</Typography>
                            </div>
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Contratacion:</Typography>
                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",paddingLeft:0, margin:0}} color="textSecondary" >{row.seguros[0].fechaContrato}</Typography>
                            </div>
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Expiracion:</Typography>
                                <Typography 
                                    variant='h6' 
                                    className={classes.info} 
                                    style={{fontWeight:"normal",paddingLeft:0 , margin:0}} 
                                    color="textSecondary" >{row.seguros[0].fechaExpiracion}</Typography>
                            </div>
                            <Divider />
                            <div style={{display:"flex",justifyContent:"center",margin:7}}>
                                <PopUpHistorialSegurosITV  data={row.seguros} condicion={true}/>
                            </div>       
                        </div>
                    </div>:
                    <div className={classes.categoria} style={{display:"grid",gridTemplateRows:"1fr 1fr",justifyItems:"center",alignItems:"center"}}>
                        <WarningIcon className={classes.warning} color='primary'/>
                        <Typography align="center" variant="body2" color="textSecondary" style={{margin: "10px 15px"}}>El vehiculo con matricula {row.vehiculo.matricula} no dispone de reparaciones registradas. Puede comenzar 
                        a registrarle reparaciones en el apartado de facturacion seleccionando "Gastos de vehiculos"</Typography>
                    </div>}
                    {row.itvs[0]?
                    <div className={classes.categoria} style={{display:"grid",gridTemplateRows:"1fr 5fr ", height:200 ,padding:"0px 15px"}}>
                        <Typography variant='h6' align="center" className={classes.info} style={{display:"flex" , justifyContent:"center"}} color="textSecondary" >ITV</Typography>
                        <div style={{height:125 }}>
                        <Divider />
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Ultima revision:</Typography>
                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",paddingLeft:0, margin:0}} color="textSecondary" >{row.itvs[0].fecha}</Typography>
                            </div>
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Resultado:</Typography>
                                <Typography variant='h6' className={classes.info} style={{fontWeight:"normal",paddingLeft:0, margin:0}} color="textSecondary" >
                                    {row.itvs[0].resultado.charAt(0)+row.itvs[0].resultado.slice(1).toLowerCase()}</Typography>
                            </div>
                            <div style={{display:"flex", justifyContent:"space-around"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0,marginBottom:25}} color="textSecondary" >Expiracion:</Typography>
                                <Typography 
                                    variant='h6' 
                                    className={classes.info} 
                                    style={{fontWeight:"normal",paddingLeft:0 , margin:0 ,marginBottom:25}} 
                                    color="textSecondary" >{row.itvs[0].expiracion}</Typography>
                            </div>
                            <Divider />
                            <div style={{display:"flex",justifyContent:"center",margin:7}}>
                                <PopUpHistorialSegurosITV  data={row.itvs} condicion={false}/>
                            </div>       
                        </div>
                    </div>:
                    <div className={classes.categoria} style={{display:"grid",gridTemplateRows:"1fr 1fr",justifyItems:"center",alignItems:"center"}}>
                        <WarningIcon className={classes.warning} color='primary'/>
                        <Typography align="center" variant="body2" color="textSecondary" style={{margin: "10px 15px"}}>El vehiculo con matricula {row.vehiculo.matricula}
                         no dispone de ninguna ITV</Typography>
                    </div>}
                    
                    <div className={classes.categoria} style={{marginTop:0, height:220,gridRow:2,gridColumn:"1 / span 3",overflow: "hidden",
                        background: "repeating-linear-gradient(-45deg,#F2F8FC,#F2F8FC 10px, #E3F2FC 10px,#E3F2FC 20px)"
                    }}>
                       {row.reparaciones[0]? 
                       <div>
                            <div style={{display:"grid",gridTemplateColumns:"1fr 1fr 3.5fr 1fr 0.5fr",paddingRight:10,gridColumnGap:15,backgroundColor:"#d4e6f1", height:55,borderBottom:"1px solid #B0D7F0"}}>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Fecha Entrada</Typography>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Fecha Salida</Typography>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Descripcion</Typography>
                                <Typography variant='h6' className={classes.info} style={{margin:0}} color="textSecondary" >Importe</Typography>
                                <Typography variant='h6' className={classes.info} style={{margin:0,display:"flex",justifyContent:"center"}} color="textSecondary" >Pagada</Typography>
                            </div>
                            <div style={row.reparaciones.length<4?{height:165,overflowY:"hidden"}:{height:165,overflowY:"scroll"}}>
                                {row.reparaciones.map((linea)=><Row key={row.reparaciones.indexOf(linea)} data={linea}/>)}
                            </div>
                        </div>:
                        <div style={{display:"flex",justifyContent:"center",alignItems:"center",height:"100%"}}>
                            <Typography variant='h3' style={{margin:0,fontWeight:600}} color="textSecondary" >VEHICULO SIN REPARACIONES <BuildIcon style={{height:50,width:50}}/></Typography>
                        </div>}
                    </div>
              </div>
          </div>
        )
      }
        
    </div>
  );
}
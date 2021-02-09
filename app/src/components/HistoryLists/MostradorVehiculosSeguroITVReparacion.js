import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import MostradorAllReparaciones from './MostradorAllReparaciones';
import WarningIcon from '@material-ui/icons/Warning';
import PopUpHistorialSegurosITV from '../Dialogs/PopUpHistorialSegurosITV';


import BotonEliminarVehiculo from '../Buttons/BotonEliminarVehiculo';


const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
    }, 

    main: {
        display: 'grid', 
        gridTemplateRows: '1fr 1fr',
        gridTemplateColumns: '0.3fr 1fr 1fr 1fr',
        width: '100%',
        alignContent: 'flex-start',
    },

    imagenBorrar: {
        gridColumn: '1',
        gridRow: '1',
    },

    imagen:{
        width: 150,
        height: 150,
    },

    infoGeneral:{
        display: 'flex',
        justifyContent: 'center',
        textAlign: 'center',
        gridColumn: '2',
        gridRow: '1'
    }, 

    titulo:{
        fontWeight: 'bold',
        textDecoration: 'underline'
    },

    seguro:{
        display: 'flex',
        justifyContent: 'center',
        textAlign: 'center',
        gridColumn: '3',
        gridRow: '1'
    },

    itv:{
        display: 'flex',
        justifyContent: 'center',
        textAlign: 'center',
        gridColumn: '4',
        gridRow: '1'
    }, 

    reparacion:{
        gridColumnStart: '2',
        gridColumnEnd: '5',
        gridRow: '2',
    }, 

    sinReparacion: {
        gridColumnStart: '2',
        gridColumnEnd: '5',
        gridRow: '2',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        textDecoration: 'underline'
    }, 

    warning: {
        width: 60,
        height: 60,
    }
    
}));

export default function MostradorVehiculosSeguroITVReparacion(props) {
    const classes = useStyles();
    const vehiculo = props.datos.vehiculo;
    const seguros = props.datos.seguros;
    const itvs = props.datos.itvs;
    const reparaciones = props.datos.reparaciones;

    return (
        <div className={classes.main}>

            <div className={classes.imagenBorrar}>
                <img className={classes.imagen} src={vehiculo.imagen} alt='IMAGEN' />
                <BotonEliminarVehiculo datos = {vehiculo} />
                {//BOTON DE ELIMINAR NO FUNCIONA PARA TODOS LOS VEHICULOS VER EXPLICACION DENTRO
                                                                                                }   
            </div>

            <div className={classes.infoGeneral}>
                <div>
                    <h2 className={classes.titulo}>INFORMACION GENERAL</h2>
                    <p><b>Matricula:</b> {vehiculo.matricula}</p>
                    <p><b>Capacidad:</b> {vehiculo.capacidad} m<sup>3</sup> </p>
                    <p><b>MMA:</b> {vehiculo.mma}</p>
                </div>
            </div>

            <div className={classes.seguro}>
                {seguros[0]===undefined?
                    <div>
                        <h2 className={classes.titulo}>SEGURO</h2>
                        <p>Este vehiculo no dispone de ningun seguro</p>
                        <WarningIcon className={classes.warning} color='primary'/>
                    </div>
                    :
                    <div>
                        <h2 className={classes.titulo}>SEGURO</h2>
                        <p><b>Compañia:</b> {seguros[0].compania}</p>
                        <p><b>Tipo de Seguro:</b> {seguros[0].tipoSeguro}</p>
                        <p><b>Fecha de contratacion:</b> {seguros[0].fechaContrato}</p>
                        <p><b>Fecha de expiracion:</b> {seguros[0].fechaExpiracion}</p>
                        
                        {seguros===undefined? null : <PopUpHistorialSegurosITV data={seguros} condicion={true}/>}
                    
                    </div>
                }
            </div>

            <div className={classes.itv}>
                {itvs[0]===undefined?
                    <div>
                        <h2 className={classes.titulo}>ITV</h2>
                        <p>Este vehiculo no dispone de ninguna ITV</p>
                        <WarningIcon className={classes.warning} color='primary'/>
                    </div>
                    :
                    <div>
                        <h2 className={classes.titulo}>ITV</h2>
                        <p><b>Fecha de ultima revision:</b> {itvs[0].fecha}</p>
                        <p><b>Resultado ultima ITV:</b> {itvs[0].resultado}</p>
                        <p><b>Fecha de expiracion:</b> {itvs[0].expiracion}</p>

                        {itvs===undefined? null : <PopUpHistorialSegurosITV data={itvs} condicion={false}/>}

                    </div>
                }
            </div>
                
            
            {reparaciones.length===0?

            <div className={classes.sinReparacion}>
                <WarningIcon className={classes.warning} color='primary'/>
                <p>El vehiculo con matricula {vehiculo.matricula} no dispone de reparaciones registradas. Puede comenzar 
                a registrarle reparaciones en el apartado de facturacion seleccionando "Gastos de vehiculos"</p>
            </div>

            :

            <div className={classes.reparacion}>       
                <div>
                    <p><b>Listado de reparaciones</b></p>
                    <MostradorAllReparaciones data={reparaciones}/>
                </div>
            </div>

            }

        </div>
    )
}
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

//Components
import TablaMostradorVehiculosSeguroITVReparacion from '../Tables/TablaMostradorVehiculosSeguroITVReparacion';
import PopUpNuevoVehiculo from '../Dialogs/PopUpNuevoVehiculo';
 
const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
    },
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
      },

      tituloyForm: {
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center'
      },
}));


export default function VehiculosInfoAdmin(props) {
    const classes = useStyles();
    const vehiculos = props.datos;

    return (
        <div>
            <div className={classes.tituloyForm}>
                <Typography variant='h3' className={classes.titulo}>VEHICULOS DE LA EMPRESA</Typography>
                <PopUpNuevoVehiculo className={classes.boton}/>
            </div>        

            <div className={classes.main}>
                {vehiculos === undefined? null:<TablaMostradorVehiculosSeguroITVReparacion datos = {vehiculos}/>}
            </div>

        </div>
    )
}
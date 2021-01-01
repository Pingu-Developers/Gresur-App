import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Notificacion from './Notificacion';
import IconButton from '@material-ui/core/IconButton';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Divider from '@material-ui/core/Divider';


const useStyles = makeStyles({

});

export default function ListadoNotificacion(props) {
    const classes = useStyles();

    const { notificaciones } = props;

    return (
        <div>
            {notificaciones?notificaciones.map((notificacion) =>
            <div>
                <Notificacion confirmNoti={props.confirmNoti} notificacion = {notificacion}/>
                <Divider />
            </div>
            ):null}

            <IconButton color="primary"  aria-label="New notification">
                <AddCircleIcon fontSize="large" />
            </IconButton>
        </div>
    )
}

import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Notificacion from './Notificacion';
import IconButton from '@material-ui/core/IconButton';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Divider from '@material-ui/core/Divider';
import NuevaNotificacion from './NuevaNotificacion';


//REDUX Stuff
import { useDispatch } from "react-redux";
import { loadPersonal } from '../redux/actions/dataActions'


const useStyles = makeStyles({

});

function ListadoNotificacion(props) {
    const classes = useStyles();

    const dispatch = useDispatch();
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        dispatch(loadPersonal())
        setOpen(true);
    };
  
    const handleClose = () => {
      setOpen(false);
    };

    const { notificaciones } = props;

    return (
        <div>
            {notificaciones?notificaciones.map((notificacion) =>
            <div>
                <Notificacion confirmNoti={props.confirmNoti} notificacion = {notificacion}/>
                <Divider />
            </div>
            ):null}

            <IconButton color="primary" onClick={open?null:handleClickOpen}  aria-label="New notification">
                <AddCircleIcon fontSize="large" />
                <NuevaNotificacion open={open} handleClose={open?handleClose:null} />
            </IconButton>
        </div>
    )
}




export default ListadoNotificacion

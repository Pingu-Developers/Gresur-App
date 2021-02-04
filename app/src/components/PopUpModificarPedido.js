import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useTheme } from '@material-ui/core/styles';
import MostradorAllSeguros from './MostradorAllSeguros';
import CloseIcon from '@material-ui/icons/Close';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import MostradorAllITVs from './MostradorAllITVs';
import FormNuevoVehiculo from './FormNuevoVehiculo';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import { FormEditarPedido } from './FormEditarPedido';

const useStyles = makeStyles((theme) => ({

    diaboton: {
        display:'flex',
        justifyContent: 'space-between'
    },

    botonPagado: {
        marginLeft: 35,
        borderRadius: 20,
        fontWeight: 'bold'
      },

}));

export default function PopUpModificarPedido(props) {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const classes = useStyles();

  const pedido = props.pedido;
  const estado = props.estado;
  const orden = props.orden;


  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };


  return (
    <div>
      <Button className={classes.botonPagado} variant='outlined' color="secondary" size="small" disabled={pedido.estado !== "EN_ESPERA"} onClick={handleClickOpen}><b>MODIFICAR</b></Button>
      <Dialog
        fullWidth={true}
        fullScreen={false}
        maxWidth='sm'
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <div className={classes.diaboton}>
            <DialogTitle id="responsive-dialog-title">Modificar pedido</DialogTitle>
            <Button className={classes.boton} onClick={handleClose} color="primary"><CloseIcon/></Button>
        </div>
        <DialogContent>
          <DialogContentText>
           
            <FormEditarPedido pedido={pedido} estado={estado} orden={orden} cerrar={handleClose}/>
           
          </DialogContentText>
        </DialogContent>
        <DialogActions>
        </DialogActions>
      </Dialog>
    </div>
  );
}
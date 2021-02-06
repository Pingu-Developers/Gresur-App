import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useTheme } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { makeStyles } from '@material-ui/core/styles';

import FormUpdatePedido from '../Forms/FormUpdatePedido';

const useStyles = makeStyles((theme) => ({

    diaboton: {
        display:'flex',
        justifyContent: 'space-between',
        position: 'relative'
    },

    botonPagado: {
        marginLeft: 35,
        borderRadius: 20,
        fontWeight: 'bold'
      },

    boton: {
      position: 'absolute',
      right: 0,
      top: 7
    }

}));

export default function PopUpModificarPedido(props) {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const classes = useStyles();

  const {pedido,estado,orden,pageNo,pageSize}=props
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
            <DialogTitle style={{width: '100%', padding: '16px 0px 16px 0px', textAlign: 'center'}} id="responsive-dialog-title"><b>MODIFICACION DEL PEDIDO</b></DialogTitle>
            <Button className={classes.boton} onClick={handleClose} color="primary"><CloseIcon/></Button>
        </div>
        <DialogContent>
          <DialogContentText>
           
            <FormUpdatePedido pedido={pedido} estado={estado} orden={orden} pageNo={pageNo} pageSize={pageSize} cerrar={handleClose}/>
           
          </DialogContentText>
        </DialogContent>
        <DialogActions>
        </DialogActions>
      </Dialog>
    </div>
  );
}
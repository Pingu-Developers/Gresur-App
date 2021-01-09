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

const useStyles = makeStyles((theme) => ({

    diaboton: {
        display:'flex',
        justifyContent: 'space-between'
    },

    boton: {
        marginRight:30
    }

}));

export default function ResponsiveDialog(props) {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const classes = useStyles();



  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };


  return (
    <div>
      <Button className={classes.boton} startIcon={<AddCircleIcon />} variant='contained' color='primary' onClick={handleClickOpen}>Añadir vehiculo</Button>
      <Dialog
        fullWidth={true}
        fullScreen={false}
        maxWidth='sm'
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <div className={classes.diaboton}>
            <DialogTitle id="responsive-dialog-title">Nuevo vehiculo</DialogTitle>
            <Button className={classes.boton} onClick={handleClose} color="primary"><CloseIcon/></Button>
        </div>
        <DialogContent>
          <DialogContentText>
           
            <FormNuevoVehiculo cerrar={handleClose}/>
           
          </DialogContentText>
        </DialogContent>
        <DialogActions>
        </DialogActions>
      </Dialog>
    </div>
  );
}

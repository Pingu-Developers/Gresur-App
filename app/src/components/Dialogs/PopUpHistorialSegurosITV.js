import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useTheme } from '@material-ui/core/styles';
import MostradorAllSeguros from '../HistoryLists/MostradorAllSeguros';
import CloseIcon from '@material-ui/icons/Close';
import { makeStyles } from '@material-ui/core/styles';
import MostradorAllITVs from '../HistoryLists/MostradorAllITVs';


const useStyles = makeStyles((theme) => ({

    diaboton: {
        display:'flex',
        justifyContent: 'space-between'
    },     

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

  const data = props.data;
  const condicion = props.condicion;
  const resp = condicion===false? 'Ver historial de ITVs' : 'Ver historial de seguros';

  return (
    <div>
      <Button variant='contained' color='primary' onClick={handleClickOpen}>{resp}</Button>
      <Dialog
        fullWidth={true}
        fullScreen={false}
        maxWidth='lg'
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <div className={classes.diaboton}>
            <DialogTitle id="responsive-dialog-title">{resp}</DialogTitle>
            <Button className={classes.boton} onClick={handleClose} color="primary"><CloseIcon/></Button>
        </div>
        <DialogContent>
          <DialogContentText>
           
           {condicion===false? <MostradorAllITVs data = {data} cerrar={handleClose}/> : <MostradorAllSeguros data = {data} cerrar={handleClose}/> }
           
          </DialogContentText>
        </DialogContent>
        <DialogActions>
        </DialogActions>
      </Dialog>
    </div>
  );
}
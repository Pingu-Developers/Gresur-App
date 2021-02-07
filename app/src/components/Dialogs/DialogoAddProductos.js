import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogContent from '@material-ui/core/DialogContent';
import MuiDialogActions from '@material-ui/core/DialogActions';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import AddMaterial from '../Forms/AddMaterial';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import {
  getProductosPaginados,
  clearProductosPaginados,
} from "../../redux/actions/productoActions";

const styles = (theme) => ({
  root: {
    margin: 0,
    padding: theme.spacing(2),
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
});

const DialogTitle = withStyles(styles)((props) => {
  const { children, classes, onClose, ...other } = props;
  return (
    <MuiDialogTitle disableTypography className={classes.root} {...other}>
      <Typography variant="h6">{children}</Typography>
      {onClose ? (
        <IconButton aria-label="close" className={classes.closeButton} onClick={onClose}>
          <CloseIcon />
        </IconButton>
      ) : null}
    </MuiDialogTitle>
  );
});

const DialogContent = withStyles((theme) => ({
  root: {
    padding: theme.spacing(2),
  },
}))(MuiDialogContent);

const DialogActions = withStyles((theme) => ({
  root: {
    margin: 0,
    padding: theme.spacing(1),
  },
}))(MuiDialogActions);

export default function DialogoAddProductos(props) {
  const [open, setOpen] = React.useState(false);

  const {handleAdd} = props;
  const dispatch = useDispatch();
  const counter = useSelector(state => state);

  const handleClickOpen = () => {
    dispatch(getProductosPaginados(
      0,
      null,
      null,
      10
    ))
    setOpen(true);
  };
  const handleClose = () => {
    dispatch(clearProductosPaginados());
    setOpen(false);
  };

  const handleAnadir = (prod) =>{

    const linea = {
      id:null,
      cantidad:1,
      precio:prod.precioVenta,
      producto:prod
    }

    handleAdd(linea);
    handleClose();
  }

  return (
    <div>
      <Fab color="secondary" onClick={handleClickOpen} aria-label="add">
          <AddIcon />
      </Fab>
      
      <Dialog onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
        <DialogTitle id="customized-dialog-title" onClose={handleClose}>
          Añadir producto
        </DialogTitle>
        <DialogContent dividers>
          <AddMaterial handleAdd={handleAnadir}/>
        </DialogContent>
        
        <DialogActions>
        </DialogActions>
      </Dialog>
    </div>
  );
}
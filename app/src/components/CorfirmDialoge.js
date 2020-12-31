import React from 'react';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import { makeStyles } from '@material-ui/core/styles';
import { blue } from '@material-ui/core/colors';
import Button from '@material-ui/core/Button';


const useStyles = makeStyles({
    avatar: {
      backgroundColor: blue[100],
      color: blue[600],
    },
    Buttons: {
        margin: "1% 12% 6% 12%",
        display: 'inline-block'
    }
  });
  
export default function ConfirmDialog(props) {
    const classes = useStyles();
    const { onClose, open, onConfirm, elementID } = props;
  
    const handleClose = () => {
      onClose();
    };

    const handleConfirm = () =>{
        onConfirm(elementID);
        onClose();
    };

  
    return (
      <Dialog onClose={handleClose} aria-labelledby="simple-dialog-title" open={open}>
        <DialogTitle id="simple-dialog-title">Confirmar cancelacion</DialogTitle>
        <div>
            <Button className = {classes.Buttons} variant="contained" color="primary" onClick={handleConfirm}>
                Si
            </Button>
            <Button className = {classes.Buttons} variant="contained" color="primary" onClick={handleClose}>
                No
            </Button>
        </div>
        
      </Dialog>
    );
  }
  
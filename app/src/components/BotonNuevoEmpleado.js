import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';


export default function FormDialog() {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
      
    <div >
      <Button   variant="containedPrimary" color="primary" onClick={handleClickOpen}>
        Añadir Empleado
      </Button>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle align="center" id="outlined-basic">Añadir nuevo Empleado</DialogTitle>
        <DialogContent>
            <TextField
                autoFocus
                margin="dense"
                id="name"
                label="Nombre"
                type="name"
                fullWidth
          />
            <TextField
                autoFocus
                margin="dense"
                id="name"
                label="Email"
                type="email"
                fullWidth
          />  
            <TextField
                id="standard-number"
                autoFocus
                margin="dense"
                id="name"
                label="Teléfono"
                type="number"
                fullWidth
          />
            <TextField
                autoFocus
                margin="dense"
                id="name"
                label="Dirección"
                type="direccion"
                fullWidth
          /> 
            <TextField
                id="standard-number"
                autoFocus
                margin="dense"
                id="name"
                label="NSS"
                type="number"
                fullWidth
          /> 
            <TextField
                id="standard-number"
                autoFocus
                margin="dense"
                id="name"
                label="NIF"
                type="number"
                fullWidth
          /> 
        
        

        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleClose} color="primary">
            Registrar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

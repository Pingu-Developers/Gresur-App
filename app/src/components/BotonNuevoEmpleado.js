import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import PropTypes from 'prop-types';




const useStyles = makeStyles((theme) => ({
  root: {
    '& .MuiTextField-root': {
      margin: theme.spacing(1),
    },
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 220,
  }
}));

export default function FormNuevoEmpleado() {
  
  const [open, setOpen] = React.useState(false);
  const [name,setName] = React.useState()
  const [email, setEmail] = React.useState();
  const [tlf, setTlf] = React.useState();
  const [direccion, setDireccion] = React.useState();
  const [nss, setNss] = React.useState();
  const [nif, setNif] = React.useState();
  const [errors, setErrors] = React.useState();
  const [rol, setRol] = React.useState('');




  const classes = useStyles();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = (event) =>  {
    event.preventDefault();
     const empleado = {
         name: this.state.name,
         email: this.state.email,
         tlf: this.state.tlf,
         direccion: this.state.direccion,
         nss: this.state.nss,
         nif: this.state.nif
     };
     this.props.addPersonal(empleado);
 }


  const handleChange = (event) => {
    setRol(event.target.value);
  };
  return (
    
    <div>
      <Button variant="contained" color="primary" onClick={handleClickOpen}>
       Añadir nuevo empleado
      </Button>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title" align="center">Nuevo empleado</DialogTitle>
        <DialogContent>
        <form  noValidate onSubmit={handleSubmit}>
            <div>
              <FormControl className={classes.formControl}>

                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={rol}
                  onChange={handleChange}
                  autoWidth
                >
                  <MenuItem value="Dependiente">Dependiente</MenuItem>
                  <MenuItem value="Encargado de almacén">Encargado de almacén</MenuItem>
                  <MenuItem value="Transportista">Transportista</MenuItem>
                  <MenuItem value="Administrador">Administrador</MenuItem>

                </Select>
              </FormControl>
            </div>
        <div>
            <TextField className={classes.input}
                autofocus
                id="name"
                label="Nombre"
                type="text"
                variant="outlined"
                name = "name"
                margin="normal"
                required
                value={name}
               
          />
            <TextField 
                id="tlf-number"
                id="tlf"
                label="Teléfono"
                type="text"
                variant="outlined"
                margin="normal"
                name = "tlf"
                required
                value={tlf}

          />
            <TextField className={classes.input}
                id="email"
                label="Email"
                type="text"
                variant="outlined"
                margin="normal"
                required
                name = "email"
                value={email}
          />

            <TextField 
                id="direccion"
                label="Dirección"
                type="text"
                variant="outlined"
                margin="normal"
                name = "direccion"
                required
                value={direccion}
          />
         <TextField
                id="standard-number"
                required
                id="nif"
                label="NIF"
                variant="outlined"
                type="text"
                margin="normal"
                name = "nif"
                fullWidth
                value={nif}
          />
            <TextField
                id="standard-number"
                id="nss"
                label="NSS"
                type="text"
                variant="outlined"
                margin="normal"
                name = "nss"
                fullWidth
                required
                value={nss}
          />
          </div>
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary" variant="contained">
            Cancelar
          </Button>
          <Button type="submit" onClick={handleClose} variant="contained" color="primary" className={classes.button}>
          Añadir</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}


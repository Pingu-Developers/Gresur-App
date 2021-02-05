import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { makeStyles } from '@material-ui/core/styles';

import { useDispatch } from "react-redux";
import { clear } from '../redux/actions/userActions';

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    '& > * + *': {
      marginTop: theme.spacing(2),
    },
  },
  button:{
    background: 'transparent',
    border:'none',
    position: 'absolute',
    top: 0,
    zIndex: 0,
  }
}));

export default function CustomizedSnackbars(props) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const dispatch = useDispatch();

  React.useEffect(() => {

    return () => { dispatch(clear()); }
  }, [] );

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    dispatch(clear())
    setOpen(false)
  };

  const handleClick = () => {
    setOpen(true);
  };

  
  return (
    <div className={classes.root}>
      <button id = {props.id?props.id:"botonSnack"} className = {classes.button} onClick={handleClick}></button>
      <Snackbar open={open&&!(!props.open&&props.truco)} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity={props.type}>
          {props.message}
        </Alert>
      </Snackbar>
    </div>
  );
}
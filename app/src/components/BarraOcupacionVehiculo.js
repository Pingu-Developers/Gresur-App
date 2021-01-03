import React from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';
import Typography from '@material-ui/core/Typography';

const BorderLinearProgress = withStyles((theme) => ({
  root: {
    marginTop: 30,
    height: 50,
    borderRadius: 5,
    width: 1050,
  },
  colorPrimary: {
    backgroundColor: theme.palette.grey[theme.palette.type === 'light' ? 200 : 700],
  },
  bar: {
    borderRadius: 5,
    backgroundColor: '#1a90ff',
  },
}))(LinearProgress);

const useStyles = makeStyles({
  root: {
    flexGrow: 1,
    display: 'flex'
  },

  vehiculo: {
    marginTop: 40,
    marginLeft: 40
  }

});

export default function CustomizedProgressBars(props) {
  const classes = useStyles();

  const valor = (props.datos.dimProductos/props.datos.dimTotales)*100;

  return (
    <div className={classes.root}>
      <BorderLinearProgress variant="determinate" value={valor} />

      <div>
        <Typography variant='h5' className={classes.vehiculo}>{props.datos.vehiculo.tipoVehiculo} ({props.datos.vehiculo.matricula})</Typography> 
      </div>

    </div>
  );
}
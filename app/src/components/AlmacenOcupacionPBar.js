import React from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';

const BorderLinearProgress = withStyles((theme) => ({
  root: {
    height: 40,
    borderRadius: 5,
  },
  colorPrimary: {
    backgroundColor: theme.palette.grey[theme.palette.type === 'light' ? 200 : 700],
  },
  bar: {
    borderRadius: 5,
    backgroundColor: '#00bcd4',
  },
}))(LinearProgress);


const useStyles = makeStyles({
  root: {
    flexGrow: 1,
    position: 'relative'
  },
  showVal: {
    position: 'absolute',
    display: 'flex',
    alignItems: 'center',
    zIndex: '1000',
    color: '#e6f4f7',
    fontWeight: 'bold',
    fontSize: 23,
    height: '100%',
    margin: 0,
    marginLeft: 20,
  }
});

export default function CustomizedProgressBars(props) {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <p className = {classes.showVal}>{props.value.toFixed(2)} %</p>
      <BorderLinearProgress variant="determinate" value={props.value} />
    </div>
  );
}
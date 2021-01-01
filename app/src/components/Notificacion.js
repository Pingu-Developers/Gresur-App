import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { red } from '@material-ui/core/colors';
import DoneAllIcon from '@material-ui/icons/DoneAll';

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 345,
  },
  media: {
    height: 0,
    paddingTop: '56.25%', // 16:9
  },
  expand: {
    transform: 'rotate(0deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  },
  expandOpen: {
    transform: 'rotate(180deg)',
  },
  avatar: {
    backgroundColor: red[500],
  },
  actions: {
    float:'right'
  },
  actionsText: {
    fontSize:13,
    marginRight:170
  },
  button:{
      '&:hover':{
          color:"#28C8FF"
      }
  }
}));
 
export default function Notificacion(props) {
    const classes = useStyles();
    const {notificacion}  = props;


    const handleClick = (event) => {
        props.confirmNoti(notificacion.id)
    }

    return (
        <Card elevation={0} className={classes.root}>
        <CardHeader
            avatar={
            <Avatar src={notificacion.emisor?notificacion.emisor.image:null} className={classes.avatar}>
                {notificacion.emisor?notificacion.emisor.name.charAt(0):"S"}
            </Avatar>
            }
            title={notificacion.emisor?notificacion.emisor.name:"SISTEMA"}
            subheader={notificacion.fechaHora}
        />
        <CardContent>
            <Typography variant="body2" color="textPrimary" component="p">
                {notificacion.cuerpo}
            </Typography>
        </CardContent>
        <CardActions className={classes.actions} disableSpacing>

            <Typography  className={classes.actionsText} variant="caption text" color="textSecondary">
                Prioridad: {notificacion.tipoNotificacion.toLowerCase()}
            </Typography>

            <IconButton id={notificacion.id} onClick={handleClick} className={classes.button} aria-label="Check leido">
                <DoneAllIcon />
            </IconButton>
        </CardActions>
        </Card>
    );
}

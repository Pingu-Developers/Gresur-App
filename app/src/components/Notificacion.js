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
  actionsTextRojo: {
    fontSize:13,
    marginRight:170,
    color:"#FF6351"
  },

  actionsTextGris: {
    fontSize:13,
    marginRight:170,
    color:"#A2A2A2"
  },

  actionsTextNaranja: {
    fontSize:13,
    marginRight:170,
    color:"#E7AE68"
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

    let color = null;
    
    switch (notificacion.tipoNotificacion) {
      case "URGENTE":
          color = classes.actionsTextRojo
        break;
      case "NORMAL":
          color = classes.actionsTextGris
        break;

      case "SISTEMA":
          color = classes.actionsTextNaranja
        break;

      default:
          color = classes.actionsTextGris
        break;
    }

    let fecha = notificacion.fechaHora;
    return (
        <Card elevation={0} className={classes.root}>
        <CardHeader
            avatar={
            <Avatar src={notificacion.emisor?notificacion.emisor.image:null} className={classes.avatar}>
                {notificacion.emisor?notificacion.emisor.name.charAt(0):"S"}
            </Avatar>
            }
            title={notificacion.emisor?notificacion.emisor.name:"SISTEMA"}
            subheader={fecha.slice(0,4)+"/"+ fecha.slice(5,7)+"/"+fecha.slice(8,10)+"  "+fecha.slice(11,16)}
        />
        <CardContent>
            <Typography variant="body2" color="textPrimary" component="p">
                {notificacion.cuerpo}
            </Typography>
        </CardContent>
        <CardActions className={classes.actions} disableSpacing>

            <Typography  className={color} variant="caption text">
                Prioridad: {notificacion.tipoNotificacion.toLowerCase()}
            </Typography>
            {!props.leida?(
              <IconButton id={notificacion.id} onClick={handleClick} className={classes.button} aria-label="Check leido">
                  <DoneAllIcon />
              </IconButton>
            ):null}
            
        </CardActions>
        </Card>
    );
}

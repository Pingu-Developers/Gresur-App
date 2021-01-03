import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Notificacion from './Notificacion';
import IconButton from '@material-ui/core/IconButton';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Divider from '@material-ui/core/Divider';
import NuevaNotificacion from './NuevaNotificacion';
import VisibilityIcon from '@material-ui/icons/Visibility';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import Typography from '@material-ui/core/Typography';
import EmojiPeopleIcon from '@material-ui/icons/EmojiPeople';
import Button from '@material-ui/core/Button';
import Autocomplete from '@material-ui/lab/Autocomplete';
import TextField from '@material-ui/core/TextField';




//REDUX Stuff
import { useDispatch,useSelector } from "react-redux";
import { loadPersonal } from '../redux/actions/dataActions'
import { getNotificacionesLeidas } from '../redux/actions/userActions'


const useStyles = makeStyles({

});


function ListadoNotificacion(props) {
    const classes = useStyles();

    const counter = useSelector(state => state);
    const dispatch = useDispatch();

    const [open, setOpen] = React.useState(false);
    const [openAuto, setOpenAuto] = React.useState(false);
    const [options, setOptions] = React.useState([]);
    const [seleccionado, setSeleccionado] = React.useState(false);
    const [filter, setFilter] = React.useState('');

    const handleClickOpen = () => {
        dispatch(loadPersonal())
        setOpen(true);
    };
  
    const handleClose = () => {
      setOpen(false);
    };

    const handleChangeState = () => {
        console.log(!seleccionado)
        if(!seleccionado){
            dispatch(getNotificacionesLeidas())
        }
        setSeleccionado(!seleccionado);
      };
    
    function onlyUnique(value, index, self) {
        return self.indexOf(value) === index;
    }

    const { notificaciones } = props;
    
    const handleOpenAuto = () => {
        var emisores = []
    
        counter.user.notificacionesLeidas.map((noti)=>{
            noti.emisor?emisores.push(noti.emisor.name):emisores.push("Sistema")
            return null
        })

        setOptions(emisores.filter(onlyUnique))
        console.log(options)
      };

    const handleChangeFilter = (value) => {
        setFilter(value);
    }

    const dataPreFilter = counter.user.notificacionesLeidas;

    let temp = []

    if(filter && filter !== ''){
        if(filter === 'Sistema'){
            dataPreFilter? dataPreFilter.map((noti) => noti.tipoNotificacion === "SISTEMA"?temp.push(noti):null):console.log(dataPreFilter)
        }
        else{
            dataPreFilter? dataPreFilter.map((noti) => noti.emisor && noti.emisor.name === filter?temp.push(noti):null):console.log(dataPreFilter)
        }
    }else{
        temp = dataPreFilter;
    }

    const data = seleccionado? temp:notificaciones;

    return (
        <div>
            <div>
                {seleccionado?(
                    <div>

                    <Button
                        style={{float:'left',margin:10}}
                        color="primary"
                        className={classes.button}
                        onClick={handleChangeState}
                        startIcon={<VisibilityOffIcon />}
                    >
                        No leidas
                    </Button>
                    <Autocomplete
                    id="combo-box-demo"
                    options={options}
                    size="small"
                    open={openAuto}
                    onOpen={() => {
                        setOpenAuto(true);
                        handleOpenAuto();
                      }}
                    onClose={() => {
                        setOpenAuto(false);
                    }}
                    onChange={(event,value) =>{
                        handleChangeFilter(value);  
                    }}
                    getOptionLabel={(option) => option}
                    style={{ width: 200 , marginTop:10, display:'inline-block' }}
                    renderInput={(params) => <TextField {...params}  variant="outlined" />}
                  />
                    </div>
                    

                ):(<Button
                        style={{float:'right',margin:10}}
                        color="primary"
                        className={classes.button}
                        onClick={handleChangeState}
                        endIcon={<VisibilityIcon />}
                    >
                        Leidas
                    </Button>)
                }
            </div>
            <br/>
            {!seleccionado?<br/>:null}
            {!seleccionado?<br/>:null}
            <Divider />
            {data &&data.length !==0?data.map((notificacion) =>
            <div style={{width:305, margin:20}}>
                <Notificacion confirmNoti={props.confirmNoti} leida = {seleccionado} notificacion = {notificacion}/>
                <Divider />
            </div>
            ):<div style={{width:305, margin:20}}>
                <Typography variant="subtitle1" gutterBottom>
                    ¡Nada que ver, sigue trabajando asi! <EmojiPeopleIcon fontSize="small" />
                </Typography>
            </div>}

            <IconButton color="primary" onClick={open?null:handleClickOpen}  aria-label="New notification">
                <AddCircleIcon fontSize="large" />
                <NuevaNotificacion open={open} handleClose={open?handleClose:null} />
            </IconButton>
        </div>
    )

    
}




export default ListadoNotificacion

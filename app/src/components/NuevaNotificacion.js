import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { makeStyles } from '@material-ui/core/styles';

//REDUX Stuff
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { postNotificacion } from '../redux/actions/userActions'

//MUI Stuff
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Autocomplete from '@material-ui/lab/Autocomplete';
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import Checkbox from '@material-ui/core/Checkbox';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';



const useStyles = makeStyles({

    cuerpo:{
        marginTop:10,
    }
    
  });


function NuevaNotificacion(props) {

    const dispatch = useDispatch();
    const classes = useStyles();
    const counter = useSelector(state => state);

    const [value, setValue] = React.useState('NORMAL');
    const [valueBody, setValueBody] = React.useState('');
    const [valuePara, setValuePara] = React.useState([]);
    const [data,setValueData] = React.useState([]);

    const handleChangeRadio = (event) => {
        setValue(event.target.value);
    };

    const handleChangeBody = (event) =>  {
        setValueBody(event.target.value);
    }

    const handleChangePara = (event , value) =>  {
        let temp = []

        value.map((pair)=>temp.push(pair.Persona.id))

        setValuePara(temp);
    }
    
    const handleClick = () => {
        props.handleClose()

        
        
    };

    const handleOpen = () => {
        let datos = [];
        console.log("No estoy llorando")
    Object.entries(counter.data.personal).map((pair) => pair[1].map((persona) => {
        
            switch (pair[0]) {
                case 'administrador':
                     Tipo = "Administrador"
                break;
    
                case 'encargadodealmacen':
                    Tipo = "Encargados de almacen"
                break;
    
                case 'dependiente':
                    Tipo = "Dependiente"
                break;
    
                case 'transportista':
                    Tipo = "Transportista"
                break;
    
                default:
                    break;
            }
            if(valuePara.indexOf(persona.id) == -1 && persona.id !== counter.user.personal.id){
                datos.push({ 'Tipo': Tipo , 'Persona': persona})
            }
            
            
        return null
    }))
        setValueData(datos)
    }

    const handleSubmit = (event) =>  {
        event.preventDefault();
        console.log(valuePara)
        const nuevaNoti = {
            e1:valuePara,
            e2:valueBody,
            e3:value
        }
        console.log(nuevaNoti)
        dispatch(postNotificacion(nuevaNoti))  
        
        setValue('NORMAL')
        setValuePara([])
        setValueBody('')
        
     }

    let Tipo = null;
    

    
   
    const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
    const checkedIcon = <CheckBoxIcon fontSize="small" />;

  return (
    <div>
      <Dialog open={props.open} aria-labelledby="form-dialog-title">
        <DialogTitle  id="form-dialog-title">Send notification</DialogTitle>
        <form onSubmit={handleSubmit}>
        <DialogContent style={{ width: 550 }}>
                       
            <Autocomplete
                size="small"
                multiple
                id="multiple-limit-tags"
                options={data}
                limitTags={2}
                groupBy={(option) => option.Tipo}
                getOptionLabel={(option) => option.Persona.name }
                defaultValue={[]}
                disableCloseOnSelect
                onOpen = {handleOpen}
                renderOption={(option, { selected }) => (
                    <React.Fragment>
                      <Checkbox
                        icon={icon}
                        checkedIcon={checkedIcon}
                        style={{ marginRight: 8 }}
                        checked={selected}
                      />
                      {option.Persona.name}
                    </React.Fragment>
                  )}
                renderInput={(params) => (
                <TextField
                    {...params}
                    fullWidth
                    variant="outlined"
                    label="Para"
                   
                />
                )}
                onChange = {handleChangePara}
            />
            <TextField
                value={valueBody}
                onChange = {handleChangeBody}
                className = {classes.cuerpo}
                fullWidth
                id="outlined-multiline-static"
                placeholder="Cuerpo"
                multiline
                rows={10}
                variant="outlined"
            />
             <FormControl component="fieldset">
                <RadioGroup row aria-label="position" name="position" onChange={handleChangeRadio} value={value}>
                    <FormControlLabel
                    value="NORMAL"
                    control={<Radio color="primary" />}
                    label="Normal"
                    labelPlacement="End"
                    />
                    <FormControlLabel
                    value="URGENTE"
                    control={<Radio color="primary" />}
                    label="Urgente"
                    labelPlacement="End"
                    />
                </RadioGroup>
            </FormControl>
            </DialogContent>

            <DialogActions>
            <Button onClick={handleClick} color="primary">
                Cancelar
            </Button>
            <Button onClick={handleClick} type="submit"  color="primary">
                Enviar
            </Button>
            </DialogActions>
        </form>
       
      </Dialog>
    </div>
  );
}



export default NuevaNotificacion

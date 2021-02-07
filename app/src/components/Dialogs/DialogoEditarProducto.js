import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useTheme } from '@material-ui/core/styles';
import { makeStyles } from '@material-ui/core/styles';
import EditIcon from '@material-ui/icons/Edit';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import firebase from "../../firebaseConfig/firebase";
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';
import { setProducto } from '../../redux/actions/dataActions';
import { getProductosPaginados} from '../../redux/actions/productoActions'
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";

const useStyles = makeStyles((theme) => ({

  title:{
    width:600,
    boxSizing:"border-box",
    display: "flex",
    justifyContent: "center"
  },
  content:{
    boxSizing:"border-box",
    padding: "10px 40px 10px 40px",
    width:"100%",
    height:"100%",
    display: "grid",
    gridTemplateColumns:"1fr",
    gridTemplateRows:"1fr 2fr 1fr 1fr 1fr",
    gridRowGap:"20px",
    justifyContent: "center"
  },
  input: {
    display: 'none',
  },    

}));

export default function ResponsiveDialog(props) {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();
  const [enviar,setEnviar ] = React.useState(false);
  const classes = useStyles();

  const counter = useSelector(state => state);
  const dispatch = useDispatch();
  const producto = props.producto;

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleEnviar = () => {
    var valid = true;

      var errores = {nombre:[], ancho:[], alto: [], profundo:[], stock: [], stockS:[], precioV:[], precioC: [], pesoU:[], unidad:[]};

      if(nombre.length<1){
        valid = false;
        errores = {
          ...errores,
          nombre:['Nombre en blanco']
        }
      }

      if(ancho<0){
        valid = false;
        errores = {
          ...errores,
          ancho:['Ancho negativo']
        }
      }

      if(alto<0){
        valid = false;
        errores = {
          ...errores,
          alto:['Alto negativo']
        }
      }

      if(profundo<0){
        valid = false;
        errores = {
          ...errores,
          profundo:['Profundo negativo']
        }
      }

      if(stock<0){
        valid = false;
        errores = {
          ...errores,
          stock:['Stock negativo']
        }
      }

      if(stockS<0){
        valid = false;
        errores = {
          ...errores,
          stockS:['Stock Seg. negativo']
        }
      }

      if(precioC<0){
        valid = false;
        errores = {
          ...errores,
          precioC:['Precio comp. negativo']
        }
      }

      if(precioV<0){
        valid = false;
        errores = {
          ...errores,
          precioV:['Precio vent. negativo']
        }
      }

      if(pesoU<0){
        valid = false;
        errores = {
          ...errores,
          pesoU:['Peso und. negativo']
        }
      }

      switch (unidad) {
        case "SACOS":
          
          break;
      
        case "KG":
        
          break;
        case "M2":
        
          break;
        case "UNIDADES":
        
          break;
        case "LATAS":
      
          break;

        default:
          valid= false;
          errores = {
            ...errores,
            unidad:['Unidad invalida']
          }
          break;
      }

      setError(errores);
      console.log(errores)
      if(valid) {
        const productoNew = {
          id: producto.id,
          nombre: nombre,
          descripcion: descripcion,
          unidad: unidad,
          stock: stock,
          stockSeguridad:stockS,
          precioVenta: precioV,
          precioCompra: precioC,
          alto: alto,
          ancho: ancho,
          profundo: profundo,
          pesoUnitario: pesoU,
          urlimagen: urlImagen
        }
        console.log(productoNew)
        dispatch(setProducto(productoNew,props.page,props.categoria));
        handleSubmit(productoNew);
      }
  };

  const handleSubmit = (producto)=>{
    handleClose()
    //props.handleReload()
  }

  const handleClose = () => {
    setOpen(false);
  };

  
  const [nombre,setNombre ] = React.useState(producto.nombre);
  const [descripcion,setDescripcion ] = React.useState(producto.descripcion);
  const [ancho,setAncho ] = React.useState(producto.ancho);
  const [alto,setAlto ] = React.useState(producto.alto);
  const [profundo,setProfundo ] = React.useState(producto.profundo);
  const [stock,setStock ] = React.useState(producto.stock);
  const [stockS,setStockS ] = React.useState(producto.stockSeguridad);
  const [precioV,setPrecioV ] = React.useState(producto.precioVenta);
  const [precioC,setPrecioC ] = React.useState(producto.precioCompra);
  const [pesoU,setPesoU ] = React.useState(producto.pesoUnitario);
  const [unidad,setUnidad] = React.useState(producto.unidad);
  const [urlImagen,setUrlImagen] = React.useState(producto.urlimagen);
  const [error, setError] = React.useState({
    nombre:[], ancho:[], alto: [], profundo:[], stock: [], stockS:[], precioV:[], precioC: [], pesoU:[], unidad:[]
  });

  const handleNumFloat = (value,min) =>{
    return Number.isNaN(parseFloat(value))||parseFloat(value)<min? min: parseFloat(value)
  }

  const handleNumInt = (value,min) =>{
    return Number.isNaN(parseInt(value))||parseInt(value)<min? min: parseInt(value)
  }

  const handleChangeImg = (event) => {
    event.preventDefault();
    console.log('Comienzo de upload')
    //Aqui hacemos el upload a Firebase
    const file = event.target.files[0]
    const storageRef = firebase.storage().ref(`pictures/${file.name}`)
    const task = storageRef.put(file)
    //Ya esta subida en Firebase

    //Cogemos el url de Firebase para guardar el URL 
    task.on('state_changed',(snapshot) => {
     snapshot.ref.getDownloadURL().then(function(downloadURL) {
        setUrlImagen(downloadURL)
     }.bind(this));
      console.log(snapshot)    
    },(err) => {
      console.log(err)
    })
  }



  return (
    <div>
      <Button onClick={handleClickOpen}><EditIcon /></Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle className={classes.title}>Editar producto</DialogTitle>
        <DialogContent className={classes.content} >
          <TextField
            id="standard-required"
            label="Nombre producto"
            value={nombre}
            error={error.nombre.length>0} 
            helperText={error.nombre[0]}
            onChange={(event)=> setNombre(event.target.value)}
            />
          <TextField
            multiline
            rows={6}
            variant="outlined"
            id="standard-required"
            label="Descripcion"
            value={descripcion}
            onChange={(event)=> {setDescripcion(event.target.value)}}
            />
          <div style={{display:"grid" , gridTemplateColumns:"1fr 1fr 1fr", gridColumnGap:"40px" }}>
            <TextField
              id="standard-required"
              type="number"
              label="Ancho"
              value={ancho}
              error={error.ancho.length>0} 
              helperText={error.ancho[0]}
              onChange={(event)=>setAncho(handleNumFloat(event.target.value,0.0))}
              />
            <TextField
              type="number"
              id="standard-required"
              label="Alto"
              value={alto}
              error={error.alto.length>0} 
              helperText={error.alto[0]}
              onChange={(event)=>setAlto(handleNumFloat(event.target.value,0.0))}
              />
            <TextField
              type="number"
              id="standard-required"
              label="Profundo"
              value={profundo}
              error={error.profundo.length>0} 
              helperText={error.profundo[0]}
              onChange={(event)=>setProfundo(handleNumFloat(event.target.value,0.0))}
              />
          </div>
          <div style={{display:"grid" , gridTemplateColumns:"1fr 1fr", gridColumnGap:"30px" }}>
            <div style={{display:"grid" , gridTemplateColumns:"1fr 1fr", gridColumnGap:"15px" }}>
              <TextField
                type="number"
                id="standard-required"
                label="Stock"
                value={stock}
                error={error.stock.length>0} 
                helperText={error.stock[0]}
                onChange={(event)=>setStock(handleNumInt(event.target.value,0))}
                />
              <TextField
                type="number"
                id="standard-required"
                label="Stock seg."
                value={stockS}
                error={error.stockS.length>0} 
                helperText={error.stockS[0]}
                onChange={(event)=>setStockS(handleNumInt(event.target.value,0))}
                />
            </div>
            <div style={{display:"grid" , gridTemplateColumns:"1fr 1fr", gridColumnGap:"15px" }}>
              <TextField
                type="number"
                id="standard-required"
                label="Precio venta"
                value={precioV}
                error={error.precioV.length>0} 
                helperText={error.precioV[0]}
                onChange={(event)=>setPrecioV(handleNumFloat(event.target.value,0.0))}
                />
              <TextField
                type="number"
                id="standard-required"
                label="Precio compra"
                value={precioC}
                error={error.precioC.length>0} 
                helperText={error.precioC[0]}
                onChange={(event)=>setPrecioC(handleNumFloat(event.target.value,0.0))}
                />
            </div>           
          </div>
          <div style={{display:"grid" , gridTemplateColumns:"1fr 2fr 2fr", gridColumnGap:"40px" ,alignItems:"center"}}>
            <TextField
              type="number"
              id="standard-required"
              label="Peso und."
              value={pesoU}
              error={error.pesoU.length>0} 
              helperText={error.pesoU[0]}
              onChange={(event)=>setPesoU(handleNumFloat(event.target.value,0.0))}
              />
            <TextField
              variant="outlined"
              select
              id="standard-required"
              label="Unidad"
              value={unidad}
              error={error.unidad.length>0} 
              helperText={error.unidad[0]}
              onChange={(event)=> {setUnidad(event.target.value)}}
              >
              <MenuItem value={"SACOS"}>
                SACOS
              </MenuItem>
              <MenuItem value={"KG"}>
                KG
              </MenuItem>
              <MenuItem value={"M2"}>
                M2
              </MenuItem>
              <MenuItem value={"UNIDADES"}>
                UNIDADES
              </MenuItem>
              <MenuItem value={"LATAS"}>
                LATAS
              </MenuItem>
            </TextField>
            <div style={{display:"grid" , gridTemplateColumns:"1fr 2fr", gridColumnGap:"20px",alignItems: "center"}}>
              <img src={urlImagen} style={{width:"80px",height:"80px"}} alt="image"/>
              <div className={classes.upload}>
                  <input
                      type="file"
                      onChange={handleChangeImg}
                      className={classes.input}
                      id="contained-button-file"
                  />
                  <label htmlFor="contained-button-file">
                  <Button className={classes.uploadBoton} variant="contained" color="secondary" component="span"  startIcon={<CloudUploadIcon />}  >
                      Subir
                  </Button>
                  </label>
              </div>
            </div>
          </div> 
        </DialogContent>
        <DialogActions style={{margin:20,display:"flex",justifyContent:"space-around"}}>
          <Button onClick={handleEnviar} startIcon={<SaveIcon />} variant="contained" color="primary">Confirmar</Button>
          <Button onClick={handleClose} variant="contained" color="primary" endIcon={<DeleteIcon/>}>Descartar</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

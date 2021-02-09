import React, { Component } from "react";
import withStyles from "@material-ui/core/styles/withStyles";

import EstanteriaConfirm from '../../../components/Dialogs/EstanteriaConfirm'

import { connect } from "react-redux";
import {
  getProveedores,
  clearProveedores,
  newProveedores,
  newFacturaRepo
} from "../../../redux/actions/proveedorActions";
import {
  getProductosPaginados,
  clearProductosPaginados,
} from "../../../redux/actions/productoActions";
import SnackCallController from '../../../components/Other/SnackCallController'

//MUI stuff
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import InputAdornment from "@material-ui/core/InputAdornment";
import SearchIcon from "@material-ui/icons/Search";
import ArrowForwardRoundedIcon from '@material-ui/icons/ArrowForwardRounded';
import Pagination from "@material-ui/lab/Pagination";
import Autocomplete from "@material-ui/lab/Autocomplete";
import CircularProgress from "@material-ui/core/CircularProgress";
import IconButton from "@material-ui/core/IconButton";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import Button from "@material-ui/core/Button";
import Divider from "@material-ui/core/Divider";
import DeleteIcon from "@material-ui/icons/Delete";
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

//FIREBASE stuff
import firebase from "../../../firebaseConfig/firebase";
import  FormNuevoProveedor  from "../../../components/Forms/FormNuevoProveedor";

const style = (theme) => ({
  root: {
    flexGrow: 1,
  },
  lineaProducto: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '1fr 3fr 3fr 1fr',
    alignItems: 'center',
    padding: 5,
    gridColumnGap: 10,
    '&:nth-of-type(2n-1)': {
      backgroundColor: "#f7f7f7",
    },
    '&:nth-of-type(even)': {
        backgroundColor: "#FFFFFF",
      },
  },
  leftListWrapper: {
    minHeight: 260,
    maxHeight: 260,
    overflowY: "auto",
    overflowX: "hidden",
    borderRadius: 20,
    border: '1px solid #bdbdbd'
  },
  elegidos: {
    padding: theme.spacing(1),
    color: theme.palette.text.secondary,
    minHeight: 260,
    maxHeight: 260,
    overflowY: "auto",
    overflowX: "hidden",
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
  search: {
    margin: 0,
  },
  categoria: {
    margin: 0,
    minWidth: 100,
  },
  total: {
    fontSize: 25,
    fontWeight: 600,
    color: '#7a7a7a'
  },
  addButton: {
    position: "relative",
    marginLeft: -10,
    top: 20,
  },
  foto: {
    marginLeft: 20,
    height: 50,
    width: 50,
  },
  fieldset: {
    backgroundColor:"#fafafa",
    borderColor:'#C4C4C4',
    padding:60,
    paddingBottom:0,
    boxSizing:"border-box",
    borderRadius: 10,
    height: "100%",
    width:"100%"
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 220,
  }, 
  upload: {
    backgroundColor: "rgba(220,220,220,0.4)",
    height: "100%",
    width: "100%",
    position: "relative",
    alignItems: "center",
    display: "flex",
    justifyContent: "center",
  },
  input: {
    display: 'none',
  },
});


export class compraMaterial extends Component {
  constructor(props) {
    super(props);
    this.state = {
      valueSearch: "",
      valueCategoria: "TODOS",
      valuePaginaActual: 1,
      open: false,
      proveedorSel: null,
      selected: [],
      valueSelected: [],
      enviar:false,
      nombreNuevoProd: "",
      descNuevoProd: "",
      stockSegNuevoProd:"0",
      precioCompraNuevoProd: "0",
      precioVentaNuevoProd: "0",
      dimensionesNuevoProd: { H: "0.0", W: "0.0", D: "0.0" },
      pesoNuevoProd: "0",
      unidadNuevoProd: "",
      urlImageNuevoProd: "",
      opendialoge: false,
      openProveedor: false,
      newProduct:{},
      errors:false,
      enviado:false,
      openNuevoProducto: false,
    };
    this.handleChangeCategoria = this.handleChangeCategoria.bind(this);
    this.handleChangeSearch = this.handleChangeSearch.bind(this);
    this.handleChangePage = this.handleChangePage.bind(this);
    this.handleChangeProveedor = this.handleChangeProveedor.bind(this);
    this.handleChangeNombre = this.handleChangeNombre.bind(this);
    this.handleChangeDesc = this.handleChangeDesc.bind(this);
    this.handleChangeStock = this.handleChangeStock.bind(this);
    this.handleChangePrecioCompra = this.handleChangePrecioCompra.bind(this);
    this.handleChangePrecioVenta = this.handleChangePrecioVenta.bind(this);
    this.handleChangeD = this.handleChangeD.bind(this);
    this.handleChangeW = this.handleChangeW.bind(this);
    this.handleChangeH = this.handleChangeH.bind(this);
    this.handleChangeH = this.handleChangeH.bind(this);
    this.handleChangeUnidad = this.handleChangeUnidad.bind(this);
    this.handleChangePeso = this.handleChangePeso.bind(this);
    this.handleClickOpen = this.handleClickOpen.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleCloseClear = this.handleCloseClear.bind(this);
    this.handleCloseProveedor = this.handleCloseProveedor.bind(this);
    this.handleEnviar = this.handleEnviar.bind(this);
    this.handleOpenDialogNP = this.handleOpenDialogNP.bind(this);
    this.handleCloseDialogNP = this.handleCloseDialogNP.bind(this);
  }

  handleEnviar(){
    this.setState({
      enviar:true
    })
  }
  handleClose(){
    this.setState({
      opendialoge: false,
      newProduct : {},
    });
  };

  handleCloseProveedor(){
    this.setState({
      openProveedor: false, 
    });
  };

  handleCloseClear(){
    this.setState({
      opendialoge: false,
      nombreNuevoProd: "",
      descNuevoProd: "",
      stockSegNuevoProd:"0",
      precioCompraNuevoProd: "0",
      precioVentaNuevoProd: "0",
      dimensionesNuevoProd: { H: "0.0", W: "0.0", D: "0.0" },
      pesoNuevoProd: "0",
      unidadNuevoProd: "",
      urlImageNuevoProd: "",
      newProduct : {},
    });
    
  };

  handleClickOpen(){
    const newItem = {
      alto:parseFloat(this.state.dimensionesNuevoProd.H),
      ancho: parseFloat(this.state.dimensionesNuevoProd.W),
      descripcion: this.state.descNuevoProd, 
      nombre: this.state.nombreNuevoProd,
      pesoUnitario:parseFloat(this.state.pesoNuevoProd),
      precioCompra:parseFloat(this.state.precioCompraNuevoProd),
      precioVenta:parseFloat(this.state.precioVentaNuevoProd),
      profundo:parseFloat(this.state.dimensionesNuevoProd.D),
      stock: 0,
      stockSeguridad:parseInt(this.state.stockSegNuevoProd),
      unidad: this.state.unidadNuevoProd,
      urlimagen: this.state.urlImageNuevoProd,
    }
    
    this.setState({
      opendialoge: true,
      newProduct : newItem
    });

    
  };

  handleChangeUnidad(event) {
    this.setState({
      unidadNuevoProd: event.target.value,
    });
  }

  handleChangePeso(event) {
    this.setState({
      pesoNuevoProd: event.target.value,
    });
  }

  handleChangeCategoria(event) {
    this.setState({
      valuePaginaActual: 1,
      valueSearch: "",
      valueCategoria: event.target.value,
    });
  }

  handleChangeCategoria(event) {
    this.setState({
      valuePaginaActual: 1,
      valueSearch: "",
      valueCategoria: event.target.value,
    });
  }

  handleChangeSearch(event) {
    this.props.clearProductosPaginados();
    this.setState({
      valuePaginaActual: 1,
      valueCategoria: "TODOS",
      valueSearch: event.target.value,
    });
  }

  handleChangePage(event, newValue) {
    console.log(newValue);
    this.setState({
      valuePaginaActual: newValue,
    });
  }

  handleChangeProveedor(event, newValue) {
    this.setState({
      proveedorSel: newValue,
      errors:false
    });
  }

  handleClickAñadir(producto) {
    let temp = this.state.selected;
    let temp2 = this.state.valueSelected;
    if (temp.indexOf(producto) === -1) {
      temp.push(producto);
      temp2.push({ e1: producto.id, e2: 1 });
    }
    this.setState({
      selected: temp,
      valueSelected: temp2,
    });
  }

  handleClickDeselected(producto) {
    let temp = this.state.selected;
    let temp2 = this.state.valueSelected;

    const index = temp.indexOf(producto);
    const index2 = temp2.indexOf(
      temp2.filter((obj) => {
        return obj.e1 === producto.id;
      })[0]
    );
    if (index !== -1) {
      temp.splice(index, 1);
      temp2.splice(index2, 1);
    }
    this.setState({
      selected: temp,
      valueSelected: temp2,
    });
  }

  handleChangeValueSelected = (producto, value) => {
    let temp = this.state.valueSelected;
    const index = temp.indexOf(
      temp.filter((obj) => {
        return obj.e1 === producto.id;
      })[0]
    );
    if (index !== -1) {
      temp.splice(index, 1, { e1: producto.id, e2: parseInt(value) });
    }
    this.setState({
      valueSelected: temp,
    });
  };

  handleChangeNombre(event) {
    this.setState({
      nombreNuevoProd: event.target.value,
    });
  }

  handleChangeDesc(event) {
    this.setState({
      descNuevoProd: event.target.value,
    });
  }

  handleChangeStock(event) {
    this.setState({
      stockSegNuevoProd: event.target.value,
    });
  }

  handleChangePrecioCompra(event) {
    this.setState({
      precioCompraNuevoProd: event.target.value,
    });
  }

  handleChangePrecioVenta(event) {
    this.setState({
      precioVentaNuevoProd: event.target.value,
    });
  }

  handleChangeH(event) {
    let temp = this.state.dimensionesNuevoProd;
    temp.H = event.target.value;
    this.setState({
      dimensionesNuevoProd: temp,
    });
  }

  handleChangeW(event) {
    let temp = this.state.dimensionesNuevoProd;
    temp.W = event.target.value;
    this.setState({
      dimensionesNuevoProd: temp,
    });
  }

  handleChangeD(event) {
    let temp = this.state.dimensionesNuevoProd;
    temp.D = event.target.value;
    this.setState({
      dimensionesNuevoProd: temp,
    });
  }
  handleCloseDialogNP(event) {
    this.setState({openNuevoProducto: false})
  }

  handleOpenDialogNP(event) {
    console.log('aa')
    this.setState({openNuevoProducto: true})
  }

  handleChangeImg = (event) => {
    event.preventDefault();
    console.log('Comienzo de upload')
    const file = event.target.files[0]
    const storageRef = firebase.storage().ref(`pictures/${file.name}`)
    const task = storageRef.put(file)

    task.on('state_changed',(snapshot) => {
     snapshot.ref.getDownloadURL().then(function(downloadURL) {
        this.setState({
            urlImageNuevoProd:downloadURL
        })
     }.bind(this));
      console.log(snapshot)    
    },(err) => {
      console.log(err)
    })
  }

  componentWillUnmount(){
    this.props.clearProductosPaginados()
  }
  componentDidMount() {
    this.props.getProductosPaginados(
      this.state.valuePaginaActual,
      null,
      null,
      10,
      "Ord"
    );
  }

  componentDidUpdate(prevProps, prevState) {
    if(this.props.nuevoProducto.nuevoProd !== prevProps.nuevoProducto.nuevoProd){
      this.handleClickAñadir(this.props.nuevoProducto.nuevoProd);
    }

    if (prevState.open !== this.state.open) {
      if (this.state.open) {
        this.props.getProveedores();
      } else {
        this.props.clearProveedores();
      }
    }
    if (
      this.state.valueCategoria !== prevState.valueCategoria ||
      this.state.valuePaginaActual !== prevState.valuePaginaActual
    ) {
      this.props.clearProductosPaginados();
      if (this.state.valueCategoria === "TODOS") {
        if (this.state.valueSearch) {
          this.props.getProductosPaginados(
            this.state.valuePaginaActual,
            null,
            this.state.valueSearch,
            10,
            "Ord"
          );
        } else {
          this.props.getProductosPaginados(
            this.state.valuePaginaActual,
            null,
            null,
            10,
            "Ord"
          );
        }
      } else {
        this.props.getProductosPaginados(
          this.state.valuePaginaActual,
          this.state.valueCategoria,
          null,
          10,
          "Ord"
        );
      }
    }

    if (this.state.valueSearch !== prevState.valueSearch) {
      this.props.getProductosPaginados(
        this.state.activePage,
        null,
        this.state.valueSearch,
        10,
        "Ord"
      );
    }

    if(this.state.enviar != prevState.enviar && this.state.enviar){
      if(this.state.proveedorSel && this.state.selected.length > 0){
        const factNueva = {
          proveedor: this.state.proveedorSel,
          concepto:'REPOSICION_STOCK',
          descripcion: 'Reposicion de stock',
          estaPagada: true,
          importe: 0
        }
        const DataEnv = {
          e1:factNueva,
          e2:this.state.valueSelected,
        }
        this.setState({
          proveedorSel: null,
          selected: [],
          valueSelected: [],
        })
        this.props.newFacturaRepo(DataEnv)
      }else{
        this.setState({errors:true})
      }

      this.setState({
        enviar:false
      })  
    }

  }

  render() {
    const {
      classes,
      proveedor: { proveedores },
      UI:{errors,enviado},
      productos: { articlesDetails, totalPages },
    } = this.props;

    let total = 0;
    this.state.valueSelected.map((row) => {
      const linea = this.state.selected.filter((obj) => {
        return obj.id === row.e1;
      })[0];

      if (linea) {
        total = total + linea.precioCompra * row.e2;
      }
    });

    const loading = this.state.open && proveedores.length === 0;
    return (
      <Grid xs={12} container spacing={0}>
        <SnackCallController  enviado = {enviado} message = {"Compra realizada correctamente"} errors={errors} />

        <div style={{display:"grid" , justifyItems:"center", alignItems: 'center', gridTemplateColumns: '5fr 1fr 5fr', width: '100%'}}>
          <div style = {{width: '100%'}}>
            <Grid item xs={12}>
              <div style = {{display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end', marginBottom: 10}}>
                <TextField
                  className={classes.search}
                  variant = "outlined"
                  id="idBusqueda"
                  label="Buscar"
                  name="valueSearch"
                  value={this.state.valueSearch}
                  onChange={(event) => this.handleChangeSearch(event)}
                  InputProps={{
                    style : {height: 35},
                    startAdornment: (
                      <InputAdornment position="start">
                        <SearchIcon />
                      </InputAdornment>
                    ),
                  }}
                />

                <TextField
                  className={classes.categoria}
                  variant = "outlined"
                  id="selectCategoria"
                  name="valueCategoria"
                  select
                  label="Categoria"
                  value={this.state.valueCategoria}
                  onChange={this.handleChangeCategoria}
                  InputProps={{
                    style : {height: 35}}}
                >
                  <MenuItem value={"TODOS"}>Todos</MenuItem>
                  <MenuItem value={"AZULEJOS"}>AZULEJOS</MenuItem>
                  <MenuItem value={"BANOS"}>BAÑOS</MenuItem>
                  <MenuItem value={"CALEFACCION"}>CALEFACCION</MenuItem>
                  <MenuItem value={"LADRILLOS"}>LADRILLOS</MenuItem>
                  <MenuItem value={"PINTURAS"}>PINTURAS</MenuItem>
                  <MenuItem value={"REVESTIMIENTOS"}>REVESTIMIENTOS</MenuItem>
                  <MenuItem value={"SILICES"}>SILICES</MenuItem>
                </TextField>
              </div>
              <div className={classes.leftListWrapper}>
                {articlesDetails
                  ? articlesDetails.map((producto) => (
                      <div style={{ minHeight: 80 }} className = {classes.lineaProducto}>
                            <img
                              className={classes.foto}
                              src={producto.urlimagen}
                              alt="IMAGEN"/>
                            <Typography
                              className={classes.gridproductos}
                              variant="body1"
                            >
                              {producto.nombre}
                            </Typography>

                            <Typography
                              className={classes.gridproductos}
                              variant="body1"
                            >
                              Precio Compra: {producto.precioCompra}€ /
                              {producto.unidad === "UNIDADES"
                                ? "Ud."
                                : producto.unidad}
                            </Typography>

                            <Button
                              style = {{color: 'white'}}
                              variant="contained"
                              size="small"
                              color="secondary"
                              disabled={this.state.selected.filter(obj => {return obj.id === producto.id})[0]?true:false}
                              onClick={() => this.handleClickAñadir(producto)}
                            >
                              Añadir
                            </Button>
                      </div>
                    ))
                  : () => null}
              </div>

              {articlesDetails.length === 0 ? null : (
                <Pagination
                  style={{marginTop:10}}
                  count={totalPages}
                  hidePrevButton={this.state.valuePaginaActual === 1}
                  hideNextButton={this.state.valuePaginaActual === totalPages}
                  page={this.state.valuePaginaActual}
                  onChange={(event, newValue) =>
                    this.handleChangePage(event, newValue)
                  }
                  color="secondary"
                />
              )}
            </Grid>
          </div>

          <div style = {{width: 'min-content'}}>
            <ArrowForwardRoundedIcon
              style={{ fontSize: 50}}
            />
          </div>

          <div style = {{width: '100%'}}>
            <Grid item xs={12}>
              <div style = {{display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end', marginTop: -10, paddingBottom: 5}}>
                <Typography className={classes.total} variant="body1">
                  TOTAL: {total.toFixed(2)}€
                </Typography>
                <span style = {{display: 'inline-flex', alignItems: 'center'}}>
                  <Autocomplete
                    id="proveedor"
                    style={{
                      margin: 0,
                      minWidth: 200,
                    }}
                    open={this.state.open}
                    value={this.state.proveedorSel}
                    onChange={this.handleChangeProveedor}
                    onOpen={() => {
                      this.setState({
                        open: true,
                      });
                    }}
                    onClose={() => {
                      this.setState({
                        open: false,
                      });
                    }}
                    getOptionSelected={(option, value) => option.name === value.name}
                    getOptionLabel={(option) => option.name}
                    options={proveedores}
                    loading={loading}
                    renderInput={(params) => (
                      <TextField
                        variant = "outlined"
                        {...params}
                        label="Proveedor"
                        error={this.state.errors}
                        InputLabelProps={{
                          style: {
                            marginTop: -9,
                          }
                        }}
                        InputProps={{
                          ...params.InputProps,
                          style : {height:35, paddingTop: 0},
                          endAdornment: (
                            <React.Fragment>
                              {loading ? (
                                <CircularProgress color="inherit" size={20} />
                              ) : null}
                              {params.InputProps.endAdornment}
                            </React.Fragment>
                          ),
                        }}
                      />
                    )}
                  />
                  <IconButton aria-label="delete" onClick={() => this.setState({
                    openProveedor:true
                  })}>
                    <AddCircleIcon color="secondary" />
                  </IconButton>                
                  <FormNuevoProveedor handleClose={this.handleCloseProveedor} open={this.state.openProveedor}/>
                </span>

                <Button
                  variant="contained"
                  color="secondary"
                  onClick={this.handleEnviar}
                  style = {{color: 'white', marginBottom: 5}}
                >
                  Crear factura
                </Button>
              </div>




              <div className={classes.leftListWrapper}>
                {this.state.selected.map((producto) => (
                  <div style={{ minHeight: 80, gridTemplateColumns: '0.5fr 1fr 3fr 3fr 1fr'}} className = {classes.lineaProducto}>
                        <IconButton
                          aria-label="delete"
                          onClick={() => this.handleClickDeselected(producto)}
                        >
                          <DeleteIcon color="secondary" />
                        </IconButton>

                        <img
                          className={classes.foto}
                          src={producto.urlimagen}
                          alt="IMAGEN"
                        />

                        <Typography variant="body1">{producto.nombre}</Typography>

                        <Typography variant="body1">
                          Cantidad :
                          <TextField
                            style={{
                              height: 12,
                              width: 60,
                              display: "inline-block",
                              position: "relative",
                              top: -4,
                            }}
                            type="number"
                            defaultValue={1}
                            value={
                              this.state.valueSelected.filter((obj) => {
                                return obj.e1 === producto.id;
                              })[0]
                                ? this.state.valueSelected.filter((obj) => {
                                    return obj.e1 === producto.id;
                                  })[0].e2
                                : 1
                            }
                            onChange={(event) => {
                              this.handleChangeValueSelected(
                                producto,
                                event.target.value
                              );
                            }}
                            error={
                              this.state.valueSelected.filter((obj) => {
                                return obj.e1 === producto.id;
                              })[0]
                                ? this.state.valueSelected.filter((obj) => {
                                    return obj.e1 === producto.id;
                                  })[0].e2 < 1 ||
                                  !this.state.valueSelected.filter((obj) => {
                                    return obj.e1 === producto.id;
                                  })[0].e2
                                : false
                            }
                            inputProps={{ min: 1, style: { padding: 5 } }}
                            variant="outlined"
                          />
                        </Typography>

                        <Typography variant="body1">
                            {" "}
                            {producto.precioCompra *
                              (this.state.valueSelected.filter((obj) => {
                                return obj.e1 === producto.id;
                              })[0]
                                ? this.state.valueSelected.filter((obj) => {
                                    return obj.e1 === producto.id;
                                  })[0].e2
                                : 0)}
                            €
                        </Typography>
                  </div>
                ))}
              </div>
              <Button 
              color = "secondary" 
              variant = "contained"
              onClick={this.handleOpenDialogNP}
              style = {{marginTop: 10, color: 'white', float: 'right'}}>
                Añadir nuevo producto
              </Button>
            </Grid>
          </div>
          
        </div>
        
        
        {/* FORM NUEVO PRODUCTO */}
        <Divider style = {{width:'100%', margin: '20px 0px 20px 0px'}}/>

        <Dialog 
        fullWidth
        maxWidth = {'lg'}
        open = {this.state.openNuevoProducto} 
        onclose = {this.handleCloseDialogNP} 
        aria-labelledby="form-dialog-title">
          <DialogTitle>
            Añadir un nuevo producto
          </DialogTitle>
          <DialogContent 
            style = {{
              display: 'grid', 
              gridTemplateColumns: '1fr 1fr 1fr',
              gridTemplateRows: '1fr 1fr 1fr 1fr 1fr', 
              padding: 24, 
              gridGap: 24,
              alignItems: 'center'}}>
              <TextField
                label="Nombre"
                variant="outlined"
                fullWidth
                style={{
                  gridColumn: 1,
                  gridRow: 1,
                }}
                value={this.state.nombreNuevoProd}
                onChange={this.handleChangeNombre}
              />

              <TextField
                label="Descripcion"
                variant="outlined"
                multiline
                style={{
                  gridColumn: 1,
                  gridRow: '2 / span 2',
                }}
                rows={6}
                fullWidth
                value={this.state.descNuevoProd}
                onChange={this.handleChangeDesc}
              />

            <TextField
                id="selectCategoria"
                name="valueCategoria"
                variant="outlined"
                select
                style={{
                  gridColumn: 1,
                  gridRow: 4,
                  backgroundColor:'white',
                }}
                label="Unidad"
                value={this.state.unidadNuevoProd}
                onChange={this.handleChangeUnidad}
              >
                <MenuItem value={"SACOS"}>SACOS</MenuItem>
                <MenuItem value={"KG"}>KG</MenuItem>
                <MenuItem value={"M2"}>M2</MenuItem>
                <MenuItem value={"UNIDADES"}>UNIDADES</MenuItem>
                <MenuItem value={"LATAS"}>LATAS</MenuItem>
              </TextField>

              
              <div style={{gridColumn: 2,gridRow: 1}}>
                <Typography color="textSecondary" variant="body1">
                  Precio de compra:
                </Typography>
        
                <TextField
                  style={{
                    gridColumn: 2,
                    gridRow: 1,
                  }}
                  type="number"
                  defaultValue={1}
                  value={this.state.precioCompraNuevoProd}
                  onChange={this.handleChangePrecioCompra}
                  inputProps={{ min: 1, style: { padding: 5 } }}
                  variant="outlined"
                />
              </div>

              <div style={{gridColumn: 2,gridRow: 2}}>
                <Typography color="textSecondary" variant="body1">
                  Precio de venta:
                </Typography>
              
                <TextField
                  type="number"
                  defaultValue={1}
                  value={this.state.precioVentaNuevoProd}
                  onChange={this.handleChangePrecioVenta}
                  inputProps={{ min: 1, style: { padding: 5 } }}
                  variant="outlined"
                />
              </div>
              
              <div style={{gridColumn: 2,gridRow: 3}}>

                <Typography color="textSecondary" variant="body1">
                  Dimensiones(Al x An x L):
                </Typography>

                <TextField
                  style={{
                    marginRight: 10,
                    width: 60,
                  }}
                  type="number"
                  defaultValue={1}
                  value={this.state.dimensionesNuevoProd.H}
                  onChange={this.handleChangeH}
                  inputProps={{ min: 1, style: { padding: 5 } }}
                  variant="outlined"
                />
                  x
                <TextField
                  style={{  
                    marginRight: 10,
                    marginLeft: 10,
                    width: 60,
                  }}
                  type="number"
                  defaultValue={1}
                  value={this.state.dimensionesNuevoProd.W}
                  onChange={this.handleChangeW}
                  inputProps={{ min: 1, style: { padding: 5 } }}
                  variant="outlined"
                />
                  x  
            
                <TextField
                  style={{  
                    marginLeft: 10,
                    width: 60,
                  }}
                  type="number"
                  defaultValue={1}
                  value={this.state.dimensionesNuevoProd.D}
                  onChange={this.handleChangeD}
                  inputProps={{ min: 1, style: { padding: 5 } }}
                  variant="outlined"
                />
              </div>
              
              <div style={{gridColumn: 2,gridRow: 4, width: '100%', display: 'inline-flex', justifyContent: 'space-between'}}>
                <span style = {{display: 'grid', justifyItems: 'flex-start'}}>
                  <Typography color="textSecondary" variant="body1">
                    Peso unitario:
                  </Typography>
              
                  <TextField
                    style={{
                      width: 60,
                    }}
                    type="number"
                    defaultValue={1}
                    value={this.state.pesoNuevoProd}
                    onChange={this.handleChangePeso}
                    inputProps={{ min: 1, style: { padding: 5 } }}
                    variant="outlined"
                  />
                </span>
                
                <span style = {{display: 'grid', justifyItems: 'center', marginRight: 50}}>
                  <Typography color="textSecondary" variant="body1">
                    Stock de Seguridad:
                  </Typography>
            
                  <TextField
                    style={{
                      width: 60,
                    }}
                    type="number"
                    defaultValue={1}
                    value={this.state.stockSegNuevoProd}
                    onChange={this.handleChangeStock}
                    inputProps={{ min: 1, style: { padding: 5 } }}
                    variant="outlined"
                  />
                </span>
              </div>
              
              <div style={{gridColumn: 3,gridRow: '1 / span 4', height: '100%', width: '100%'}}>
              {this.state.urlImageNuevoProd === ''?
              <Paper elevation={3} className={classes.upload}>
                  <input
                      type="file"
                      onChange={this.handleChangeImg.bind(this)}
                      className={classes.input}
                      id="contained-button-file"
                  />
                  <label htmlFor="contained-button-file">
                  <Button style = {{color: 'white'}} className={classes.uploadBoton} variant="contained" color="primary" component="span"  startIcon={<CloudUploadIcon />} >
                      Subir
                  </Button>
                  </label>
              </Paper>:<div>
                          <img
                              style={{width:"70%",padding:"15%",height:"70%"}}
                              src={this.state.urlImageNuevoProd}
                              alt="IMAGEN"
                          />
                  </div>}
              </div>
            
            <div style = {{position: 'absolute', bottom: 10, display:'inline-flex', width: 'calc(100% - 48px)', justifyContent: 'space-between', alignItems: 'center'}}>
              <Button
                onClick = {this.handleCloseDialogNP}
                color = "secondary">
                  Cancelar
              </Button>

              <Button
                onClick = {this.handleClickOpen}
                variant="contained" 
                color="secondary"
                style = {{color: 'white'}}>
                  Añadir producto
              </Button>
            </div>          
          </DialogContent>
          <EstanteriaConfirm open={this.state.opendialoge} onCloseConfirm ={() => {this.handleCloseClear(); this.handleCloseDialogNP()}}  product={this.state.newProduct} onClose={this.handleClose}/>
        </Dialog>
      </Grid>
    );
  }
}

const mapStateToProps = (state) => ({
  proveedor: state.proveedor,
  productos: state.productos,
  nuevoProducto: state.nuevoProducto,
  UI: state.UI
});

const mapDispatchToProps = {
  getProveedores,
  clearProveedores,
  newProveedores,
  getProductosPaginados,
  clearProductosPaginados,
  newFacturaRepo
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(style)(compraMaterial));

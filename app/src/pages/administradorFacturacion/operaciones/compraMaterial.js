import React, { Component } from "react";
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";

import EstanteriaConfirm from '../../../components/EstanteriaConfirm'

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
//MUI stuff
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import InputAdornment from "@material-ui/core/InputAdornment";
import SearchIcon from "@material-ui/icons/Search";
import ArrowForwardIcon from "@material-ui/icons/ArrowForward";
import Pagination from "@material-ui/lab/Pagination";
import Autocomplete from "@material-ui/lab/Autocomplete";
import CircularProgress from "@material-ui/core/CircularProgress";
import IconButton from "@material-ui/core/IconButton";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import Button from "@material-ui/core/Button";
import Divider from "@material-ui/core/Divider";
import DeleteIcon from "@material-ui/icons/Delete";
import FormControl from '@material-ui/core/FormControl';
import CloudUploadIcon from '@material-ui/icons/CloudUpload';

//FIREBASE stuff
import firebase from "../../../firebaseConfig/firebase";
import  FormNuevoProveedor  from "../../../components/FormNuevoProveedor";

const style = (theme) => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(1),
    color: theme.palette.text.secondary,
    minHeight: 260,
    maxHeight: 260,
    overflowY: "auto",
    overflowX: "hidden",
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
    margin: 10,
  },
  categoria: {
    margin: 10,
    minWidth: 100,
  },
  arrowDiv: {
    position: "relative",
    alignItems: "center",
    display: "flex",
    justifyContent: "center",
  },
  arrow: {
    position: "relative",
    top: 30,
  },
  total: {
    position: "relative",
    top: 25,
    marginRight: 20,
    fontSize: 25,
    fontWeight: 600,
    display: "inline-block",
  },
  addButton: {
    position: "relative",
    marginLeft: -10,
    top: 20,
  },
  createButton: {
    position: "relative",
    float:'right',
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
    borderRadius: 10,
    height: "100%",
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 220,
  }, 
  upload: {
    padding: theme.spacing(1),
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

    if(this.state.enviar != prevState.enviar && this.state.enviar && this.state.proveedorSel){
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
        <Grid container spacing={3} style={{width:"39.7vw"}}>
          <Grid item xs={12}>
            <TextField
              className={classes.search}
              id="idBusqueda"
              label="Buscar"
              name="valueSearch"
              value={this.state.valueSearch}
              onChange={(event) => this.handleChangeSearch(event)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchIcon />
                  </InputAdornment>
                ),
              }}
            />

            <TextField
              className={classes.categoria}
              id="selectCategoria"
              name="valueCategoria"
              select
              label="Categoria"
              value={this.state.valueCategoria}
              onChange={this.handleChangeCategoria}
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
            <Paper className={classes.paper} elevation={0} variant="outlined">
              {articlesDetails
                ? articlesDetails.map((producto) => (
                    <div style={{ minHeight: 80 }}>
                      <Grid
                        style={{
                          height: "100%",
                          position: "relative",
                          alignItems: "center",
                          display: "flex",
                          justifyContent: "center",
                        }}
                        container
                        spacing={3}
                      >
                        <Grid item xs={2}>
                          <img
                            className={classes.foto}
                            src={producto.urlimagen}
                            alt="IMAGEN"
                          />
                        </Grid>
                        <Grid item xs={4}>
                          <Typography
                            className={classes.gridproductos}
                            variant="body1"
                          >
                            {producto.nombre}
                          </Typography>
                        </Grid>
                        <Grid item xs={4}>
                          <Typography
                            className={classes.gridproductos}
                            variant="body1"
                          >
                            Precio Compra: {producto.precioCompra}€ /
                            {producto.unidad === "UNIDADES"
                              ? "Ud."
                              : producto.unidad}
                          </Typography>
                        </Grid>
                        <Grid item xs={2}>
                          <Button
                            className={classes.gridboton}
                            variant="contained"
                            size="small"
                            color="primary"
                            disabled={this.state.selected.filter(obj => {return obj.id === producto.id})[0]?true:false}
                            onClick={() => this.handleClickAñadir(producto)}
                          >
                            Añadir
                          </Button>
                        </Grid>
                      </Grid>
                      <Divider />
                    </div>
                  ))
                : () => null}
            </Paper>
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
        </Grid>

        <Grid className={classes.arrowDiv} xs={1}>
          <ArrowForwardIcon
            className={classes.arrow}
            style={{ fontSize: 80 }}
          />
        </Grid>

        <Grid container spacing={3} style={{width:"39.7vw"}}>
          <Grid item xs={12}>
            <Typography className={classes.total} variant="body1">
              TOTAL: {total.toFixed(2)}€
            </Typography>
            <Autocomplete
              id="proveedor"
              size="small"
              style={{
                width: 200,
                margin: 10,
                marginLeft: 20,
                display: "inline-block",
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
                  {...params}
                  label="Proveedor"
                  InputProps={{
                    ...params.InputProps,
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
            })} className={classes.addButton}>
              <AddCircleIcon color="secondary" />
            </IconButton>
            <FormNuevoProveedor handleClose={this.handleCloseProveedor} open={this.state.openProveedor}/>
            <Button
              variant="contained"
              color="primary"
              className={classes.createButton}
              onClick={this.handleEnviar}
            >
              Crear factura
            </Button>
            <Paper
              className={classes.elegidos}
              elevation={0}
              variant="outlined"
            >
              {this.state.selected.map((producto) => (
                <div style={{ minHeight: 80 }}>
                  <Grid
                    style={{
                      height: "100%",
                      position: "relative",
                      alignItems: "center",
                      display: "flex",
                      justifyContent: "center",
                    }}
                    container
                    spacing={3}
                  >
                    <Grid item xs={1}>
                      <IconButton
                        aria-label="delete"
                        onClick={() => this.handleClickDeselected(producto)}
                      >
                        <DeleteIcon color="secondary" />
                      </IconButton>
                    </Grid>
                    <Grid item xs={2}>
                      <img
                        className={classes.foto}
                        src={producto.urlimagen}
                        alt="IMAGEN"
                      />
                    </Grid>
                    <Grid item xs={4}>
                      <Typography variant="body1">{producto.nombre}</Typography>
                    </Grid>
                    <Grid item xs={3}>
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
                    </Grid>
                    <Grid container xs={2}>
                      <Grid xs={2}>
                        <Divider orientation="vertical" />
                      </Grid>
                      <Grid>
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
                      </Grid>
                    </Grid>
                  </Grid>
                  <Divider />
                </div>
              ))}
            </Paper>
          </Grid>
        </Grid>
        <Grid style={{ height: "55%" }} xs={12}>
          <fieldset className={classes.fieldset}>
            <legend>
              <Typography
                variant="subtitle1"
                className={classes.subtituloCatalogo}
              >
                Añadir nuevo producto
              </Typography>
            </legend>
            <Grid container spacing={1}>
              <Grid style={{ height: 20 }} item xs={12} />
              <Grid container direction="column" spacing={5} xs={5}>
                <Grid item container>
                  <Grid item xs={10}>
                    <TextField
                      label="Nombre"
                      variant="outlined"
                      fullWidth
                      style={{
                        backgroundColor:'white',
                      }}
                      value={this.state.nombreNuevoProd}
                      onChange={this.handleChangeNombre}
                    />
                  </Grid>
                </Grid>
                <Grid item container>
                  <Grid item xs={12}>
                    <TextField
                      label="Descripcion"
                      variant="outlined"
                      multiline
                      style={{
                        backgroundColor:'white',
                      }}
                      rows={6}
                      fullWidth
                      value={this.state.descNuevoProd}
                      onChange={this.handleChangeDesc}
                    />
                  </Grid>
                </Grid>
                <Grid item container>
                  <Grid item container xs={12}>
                  <TextField
                      id="selectCategoria"
                      name="valueCategoria"
                      variant="outlined"
                      select
                      style={{
                        width:200,
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
                  </Grid>
                </Grid>
              </Grid>
              <Grid container direction="column" xs={1} />
              <Grid
                style={{ marginTop: 20 }}
                container
                direction="column"
                spacing={5}
                xs={4}
              >
                <Grid item container>
                  <Grid xs={5}>
                    <Typography color="textSecondary" variant="body1">
                      Precio de compra:
                    </Typography>
                  </Grid>
                  <Grid xs={6}>
                    <TextField
                      style={{
                        backgroundColor:'white',
                        height: 12,
                        width: 60,
                        position: "relative",
                        top: -4,
                      }}
                      type="number"
                      defaultValue={1}
                      value={this.state.precioCompraNuevoProd}
                      onChange={this.handleChangePrecioCompra}
                      inputProps={{ min: 1, style: { padding: 5 } }}
                      variant="outlined"
                    />
                  </Grid>
                </Grid>
                <Grid item container>
                  <Grid xs={5}>
                    <Typography color="textSecondary" variant="body1">
                      Precio de venta:
                    </Typography>
                  </Grid>
                  <Grid xs={6}>
                    <TextField
                      style={{
                        backgroundColor:'white',
                        height: 12,
                        width: 60,
                        position: "relative",
                        top: -4,
                      }}
                      type="number"
                      defaultValue={1}
                      value={this.state.precioVentaNuevoProd}
                      onChange={this.handleChangePrecioVenta}
                      inputProps={{ min: 1, style: { padding: 5 } }}
                      variant="outlined"
                    />
                  </Grid>
                </Grid>
                <Grid item container>
                  <Grid xs={5}>
                    <Typography color="textSecondary" variant="body1">
                      Dimensiones(AlxAnxL):
                    </Typography>
                  </Grid>
                  <Grid>
                    <TextField
                      style={{
                        backgroundColor:'white',
                        marginRight:5,
                        height: 12,
                        width: 60,
                        position: "relative",
                        top: -4,
                      }}
                      type="number"
                      defaultValue={1}
                      value={this.state.dimensionesNuevoProd.H}
                      onChange={this.handleChangeH}
                      inputProps={{ min: 1, style: { padding: 5 } }}
                      variant="outlined"
                    />
                    x
                  </Grid>
                  <Grid>
                    <TextField
                      style={{
                        backgroundColor:'white',
                        marginRight:5,
                        marginLeft:5,
                        height: 12,
                        width: 60,
                        position: "relative",
                        top: -4,
                      }}
                      type="number"
                      defaultValue={1}
                      value={this.state.dimensionesNuevoProd.W}
                      onChange={this.handleChangeW}
                      inputProps={{ min: 1, style: { padding: 5 } }}
                      variant="outlined"
                    />
                      x  
                  </Grid>
                  <Grid>
                    <TextField
                      style={{
                        backgroundColor:'white',
                        marginLeft:5,
                        height: 12,
                        width: 60,
                        position: "relative",
                        top: -4,
                      }}
                      type="number"
                      defaultValue={1}
                      value={this.state.dimensionesNuevoProd.D}
                      onChange={this.handleChangeD}
                      inputProps={{ min: 1, style: { padding: 5 } }}
                      variant="outlined"
                    />
                  </Grid>
                </Grid>
                <Grid item container>
                  <Grid xs={5}>
                    <Typography color="textSecondary" variant="body1">
                      Peso unitario:
                    </Typography>
                  </Grid>
                  <Grid xs={6}>
                    <TextField
                      style={{
                        backgroundColor:'white',
                        height: 12,
                        width: 60,
                        position: "relative",
                        top: -4,
                      }}
                      type="number"
                      defaultValue={1}
                      value={this.state.pesoNuevoProd}
                      onChange={this.handleChangePeso}
                      inputProps={{ min: 1, style: { padding: 5 } }}
                      variant="outlined"
                    />
                  </Grid>
                </Grid>
                <Grid item container>
                    <Grid xs={5}>
                      <Typography color="textSecondary" variant="body1">
                        Stock de Seguridad:
                      </Typography>
                    </Grid>
                    <Grid xs={6}>
                      <TextField
                        style={{
                          backgroundColor:'white',
                          height: 12,
                          width: 60,
                          position: "relative",
                          top: -4,
                        }}
                        type="number"
                        defaultValue={1}
                        value={this.state.stockSegNuevoProd}
                        onChange={this.handleChangeStock}
                        inputProps={{ min: 1, style: { padding: 5 } }}
                        variant="outlined"
                      />
                    </Grid>
                  </Grid>
              </Grid>
              <Grid
                style={{ marginTop: 20 }}
                container
                direction="column"
                spacing={5}
                xs={2}
              >
                <Grid xs={12} style={{
                          height: "100%",
                          position: "relative",
                          alignItems: "center",
                          display: "flex",
                          justifyContent: "center",
                          color:"rgba(0,0,0,0.2)"
                        }}>
                   {this.state.urlImageNuevoProd === ''?
                    <Paper elevation={3} className={classes.upload}>
                        <input
                            type="file"
                            onChange={this.handleChangeImg.bind(this)}
                            className={classes.input}
                            id="contained-button-file"
                        />
                        <label htmlFor="contained-button-file">
                        <Button className={classes.uploadBoton} variant="contained" color="primary" component="span"  startIcon={<CloudUploadIcon />} >
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
                </Grid>
              </Grid>
            </Grid>
          </fieldset>
        <Button
        onClick = {this.handleClickOpen}
        style={{
            float:'right',
            position: "relative",
            top:-40,
            margin:20
          }}
        variant="contained" color="primary" component="span">
                Añadir producto
        </Button>
        </Grid>
        <EstanteriaConfirm open={this.state.opendialoge} onCloseConfirm ={this.handleCloseClear}  product={this.state.newProduct} onClose={this.handleClose}/>
      </Grid>
    );
  }
}

const mapStateToProps = (state) => ({
  proveedor: state.proveedor,
  productos: state.productos,
  nuevoProducto: state.nuevoProducto
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

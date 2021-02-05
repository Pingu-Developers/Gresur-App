import React, { Component } from "react";
import withStyles from "@material-ui/core/styles/withStyles";


import { connect } from "react-redux";
import {
    getProveedores,
    clearProveedores,
    newProveedores,
} from "../redux/actions/proveedorActions";
import {
    getProductosPaginados,
    clearProductosPaginados,
} from "../redux/actions/productoActions";
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

//FIREBASE stuff
import firebase from "../firebaseConfig/firebase";
import { TablaEditarFactura } from "./TablaEditarFactura";

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
        marginLeft: 50,
        top: 20,
    },
    foto: {
        marginLeft: 20,
        height: 50,
        width: 50,
    },
    fieldset: {
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

export class AddMaterial extends Component {
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
            nombreNuevoProd: "",
            descNuevoProd: "",
            stockSegNuevoProd: 1,
            precioCompraNuevoProd: 1,
            precioVentaNuevoProd: 1,
            dimensionesNuevoProd: { H: 0.1, W: 0.1, D: 0.1 },
            pesoNuevoProd: 0,
            unidadNuevoProd: "",
            urlImageNuevoProd: "",
            opendialoge: false,
            newProduct: {},
        };
        this.handleClickAnadir = this.handleClickAnadir.bind(this);
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
    }

    handleClose() {
        this.setState({
            opendialoge: false,
            newProduct: {},
        });
    };

    handleClickOpen() {
        const newItem = {
            alto: this.state.dimensionesNuevoProd.H,
            ancho: this.state.dimensionesNuevoProd.W,
            descripcion: this.state.descNuevoProd,
            nombre: this.state.nombreNuevoProd,
            pesoUnitario: this.state.pesoNuevoProd,
            precioCompra: this.state.precioCompraNuevoProd,
            precioVenta: this.state.precioVentaNuevoProd,
            profundo: this.state.dimensionesNuevoProd.D,
            stock: 0,
            stockSeguridad: this.state.stockSegNuevoProd,
            unidad: this.state.unidadNuevoProd,
            urlimagen: this.state.urlImageNuevoProd,
        }

        this.setState({
            opendialoge: true,
            newProduct: newItem
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

    handleClickAnadir(producto) {
        const newLinea = {
            id: null,
            producto: producto,
            cantidad: 1,
            precio: producto.precioVenta
        };
        this.props.handleAdd(newLinea);
        /*let temp = this.state.selected;
        let temp2 = this.state.valueSelected;
        if (temp.indexOf(producto) === -1) {
            temp.push(producto);
            temp2.push({ e1: producto.id, e2: 1 });
        }
        this.setState({
            selected: temp,
            valueSelected: temp2,
        });*/
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

        task.on('state_changed', (snapshot) => {
            snapshot.ref.getDownloadURL().then(function (downloadURL) {
                this.setState({
                    urlImageNuevoProd: downloadURL
                })
            }.bind(this));
            console.log(snapshot)
        }, (err) => {
            console.log(err)
        })
    }

    componentDidMount() {
        this.props.getProductosPaginados(
            this.state.valuePaginaActual,
            null,
            null,
            10
        );
    }

    componentDidUpdate(prevProps, prevState) {
        console.log(this.state);
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
                        10

                    );
                } else {
                    this.props.getProductosPaginados(
                        this.state.valuePaginaActual,
                        null,
                        null,
                        10

                    );
                }
            } else {
                this.props.getProductosPaginados(
                    this.state.valuePaginaActual,
                    this.state.valueCategoria,
                    null,
                    10
                );
            }
        }

        if (this.state.valueSearch !== prevState.valueSearch) {
            this.props.getProductosPaginados(
                this.state.activePage,
                null,
                this.state.valueSearch,
                10
            );
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
            <Grid>
                <Grid />
                <Grid>
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
                                                PVP: {producto.precioVenta}€
                                            </Typography>
                                        </Grid>
                                        <Grid item xs={2}>
                                            <Button
                                                className={classes.gridboton}
                                                variant="contained"
                                                size="small"
                                                color="primary"
                                                onClick={() => this.handleClickAnadir(producto)}
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
                            style={{ marginTop: 10 }}
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
        );
    }
}

const mapStateToProps = (state) => ({
    proveedor: state.proveedor,
    productos: state.productos,
});

const mapDispatchToProps = {
    getProveedores,
    clearProveedores,
    newProveedores,
    getProductosPaginados,
    clearProductosPaginados,
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withStyles(style)(AddMaterial));

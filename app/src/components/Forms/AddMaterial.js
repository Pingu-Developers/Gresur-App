import React, { Component } from "react";
import withStyles from "@material-ui/core/styles/withStyles";


import { connect } from "react-redux";
import {
    getProductosPaginados,
    clearProductosPaginados,
} from "../../redux/actions/productoActions";
//MUI stuff
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import InputAdornment from "@material-ui/core/InputAdornment";
import SearchIcon from "@material-ui/icons/Search";
import Pagination from "@material-ui/lab/Pagination";
import Button from "@material-ui/core/Button";
import Divider from "@material-ui/core/Divider";

//FIREBASE stuff
import firebase from "../../firebaseConfig/firebase";

const style = (theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(1),
        color: theme.palette.text.secondary,
        height: 260,
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
});

export class AddMaterial extends Component {
    constructor(props) {
        super(props);
        this.state = {
            valuePaginaActual:1,
            valueSearch:"",
            valueCategoria:"TODOS"
        };
        
        this.handleChangeCategoria = this.handleChangeCategoria.bind(this);
        this.handleChangeSearch = this.handleChangeSearch.bind(this);
        this.handleChangePage = this.handleChangePage.bind(this);
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

    componentDidMount() {
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
            productos: { articlesDetails, totalPages },
        } = this.props;

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
                                <div>
                                    <div style={{ minHeight: 80, width:500, display:"grid",gridTemplateColumns:"0.5fr 1fr 1fr 0.5fr",gridColumnGap: "20px" ,alignItems:"center"}}>
                                        <img
                                            style={{width:"90%",height:"90%", maxHeight:90}}
                                            src={producto.urlimagen}
                                            alt="IMAGEN"
                                        />
                                        <Typography
                                            style={{overflowY:"hidden"}}
                                            variant="body1"
                                            >
                                            {producto.nombre}
                                        </Typography>
                                        <Typography
                                            variant="body1"
                                            >
                                            PVP: {producto.precioVenta}€
                                        </Typography>
                                        <Button
                                            variant="contained"
                                            size="small"
                                            color="primary"
                                            onClick={() => this.props.handleAdd(producto)}
                                            >
                                            Añadir
                                        </Button>
                                    </div>
                                    <Divider />
                                </div>
                                
                            ))
                            : () => null}
                    </Paper>
                    {articlesDetails.length === 0 ? null : (
                        <Pagination
                            style={{ marginTop: 10 }}
                            count={totalPages}
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
    productos: state.productos,
});

const mapDispatchToProps = {
    getProductosPaginados,
    clearProductosPaginados,
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withStyles(style)(AddMaterial));

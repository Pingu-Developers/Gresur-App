import React, { Component } from 'react'
import axios from 'axios'
import withStyles from '@material-ui/core/styles/withStyles';
import PropTypes from 'prop-types';

//Redux stuff
import { connect } from 'react-redux';
import { loadEntidadData } from '../redux/actions/dataActions';

//MUI Stuff
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Avatar from '@material-ui/core/Avatar';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

const style = (theme) =>({
    root: {
        flexGrow: 1, 
      },
      grid:{
          marginTop:'5%',
      },
    tabla:{
        margin:theme.spacing(2),
        marginTop:"50px"
    },
    paper: {
       margin:"26px 20px 0px 20px",
      padding: 0,
      textAlign: 'center',
      opacity:0.8
    },
    textField:{
      margin:'15px auto 15px auto'
  },
  button:{
      marginTop: 25
  },
  large: {
      marginLeft:14,
      width: 100,
      height: 100,
    },
});

class entidad extends Component {

    constructor(){
        super();
        this.state = {
            dni:'',
            nombre:'',
            email:'',
            telefono:'',
            direccion:'',
        };
    }

    componentDidMount() {
        this.props.loadEntidadData();
    };

    handleSubmit = (event) =>  {
        event.preventDefault();
        const userData = {
            nif: this.state.dni,
            nombre: this.state.nombre,
            email: this.state.email,
            tlf: this.state.telefono,
            direccion: this.state.direccion,
        };
        console.log(userData)
        axios.post('http://localhost:8080/entidad/addEntidad',userData)
            .then( this.props.loadEntidadData())
            .catch(err => console.log(err))
        window.location.reload();
     }

    handleChange = (event) =>  {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    render() {
        document.body.style.background = "#C9C9C9"
        const { classes, data  } = this.props;

        return (
            <Grid container spacing={4} className={classes.root}>
                <Grid item xs> 
                {console.log(data)}
                <TableContainer className={classes.tabla} component={Paper}>
                <Table  aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>Nif</TableCell>
                        <TableCell >Name</TableCell>
                        <TableCell >Email</TableCell>
                        <TableCell >Telephone</TableCell>
                        <TableCell >Dir</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {data.data.map((row) => (
                        <TableRow key={row.nif}>
                        <TableCell component="th" scope="row">
                            {row.nif}
                        </TableCell>
                        <TableCell >{row.nombre}</TableCell>
                        <TableCell >{row.email}</TableCell>
                        <TableCell >{row.tlf}</TableCell>
                        <TableCell >{row.direccion}</TableCell>
                        <TableCell >
                            <Button type="submit" variant="contained" color="primary" >
                                Delete
                            </Button>
                        </TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
                </TableContainer>
                </Grid>
                <Grid item xs className={classes.paper}>
                    <form noValidate onSubmit={this.handleSubmit}>
                        <TextField fullWidth id="dni" name="dni" label="dni" onChange={this.handleChange} value={this.state.dni} className={classes.textField} />
                        <TextField fullWidth id="nombre" name="nombre" label="nombre" onChange={this.handleChange} value={this.state.nombre} className={classes.textField} />
                        <TextField fullWidth id="email" name="email" label="email" onChange={this.handleChange} value={this.state.email} className={classes.textField} />
                        <TextField fullWidth id="telefono" name="telefono" label="telefono" onChange={this.handleChange} value={this.state.telefono} className={classes.textField} />
                        <TextField fullWidth id="direccion" name="direccion" label="direccion" onChange={this.handleChange} value={this.state.direccion} className={classes.textField} />
                        <Button type="submit" variant="contained" color="primary" >
                            Submit
                        </Button>
                    </form>
                </Grid>
            </Grid>
        )
    }
}

const mapStateToProps = (state) => ({
    data: state.data
})
const mapActionsToProps = { loadEntidadData };

entidad.propTypes = {
    data: PropTypes.object.isRequired,
    loadEntidadData: PropTypes.func.isRequired
}

export default connect( mapStateToProps,mapActionsToProps )(withStyles(style)(entidad))

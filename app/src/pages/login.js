import React,{Component} from 'react';
import withStyles from '@material-ui/core/styles/withStyles';
import GresurImage from '../images/Gresur_login.png';
import PropTypes from 'prop-types';

//MUI Stuff
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Avatar from '@material-ui/core/Avatar';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';

//Redux stuff
import { connect } from 'react-redux';
import { loginUser } from '../redux/actions/userActions';

const style = {
    root: {
        flexGrow: 1, 
      },
      grid:{
          marginTop:'5%',
      },
      paper: {
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
    progress:{
        position: 'absolute'
    }
  }

class login extends Component {

    constructor(){
        super();
        this.state = {
            username:'',
            password:'',
            errors: {}
        }
    }

    handleSubmit = (event) =>  {
        event.preventDefault();
         const userData = {
             username: this.state.username,
             password: this.state.password
         };
         this.props.loginUser(userData,this.props.history);
     }

    componentWillReceiveProps(nextProps){
        if(nextProps.UI.errors){
            this.setState({errors: nextProps.UI.errors});
        }
    }

    handleChange = (event) =>  {
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    render() {

        document.body.style.background = `url(${GresurImage}) no-repeat center center fixed`;
        document.body.style.backgroundSize = "cover";

        const {classes, UI:{loading}} = this.props;
        const {errors} = this.state;
        return (
            <div className={classes.root}>
                <Grid container spacing={0} className={classes.grid}>
                    <Grid item xs/>
                    <Grid item xs={6}>
                    <Paper className={classes.paper} elevation={2}>
                        <div className={classes.paperItems}>
                        <Grid container spacing={3} className={classes.grid}>
                            <Grid item xs>
                            <Avatar src="Gresur_transparente.png" variant='square' className={classes.large}/>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography variant='h3'>
                                   <u>Inicio de sesión</u> 
                                </Typography>
                                <form  noValidate onSubmit={this.handleSubmit}>
                                    <TextField fullWidth id="username" name="username" label="Username" onChange={this.handleChange} className={classes.textField} 
                                        helperText={errors.username} error={errors.username?true:false} value={this.state.username}/>
                                    <TextField fullWidth id="password" name="password" label="Password" type="password" onChange={this.handleChange} className={classes.textField} 
                                         helperText={errors.password} error={errors.password?true:false} value={this.state.password}/>

                                    <Button type="submit" variant="contained" color="primary" className={classes.button} disabled={loading}>
                                    Login
                                        {loading && (
                                            <CircularProgress size={20} className={classes.progress}/>
                                        )}
                                    </Button>
                                </form>
                            </Grid>
                            <Grid item xs/>
                        </Grid> 
                        </div>
                    </Paper> 
                    </Grid>
                    <Grid item xs/>
                </Grid>  
            </div>
        )
    }
}

login.propTypes={
    classes: PropTypes.object.isRequired,
    loginUser: PropTypes.func.isRequired,
    user:PropTypes.object.isRequired,
    UI:PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    user: state.user,
    UI: state.UI
});

const mapActionsToProps = {
    loginUser
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(style)(login))

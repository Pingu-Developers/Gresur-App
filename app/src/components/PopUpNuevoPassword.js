import React,{Component} from 'react';
import PropTypes from 'prop-types';

//MUI stuff
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogTitle from '@material-ui/core/DialogTitle';
import withStyles from '@material-ui/core/styles/withStyles';
import CloseOutlinedIcon from '@material-ui/icons/CloseOutlined';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import { red } from '@material-ui/core/colors';
import ProfilePassword from './ProfilePassword'
import LockOpenOutlinedIcon from '@material-ui/icons/LockOpenOutlined';

//REDUX stuff
import { connect } from 'react-redux';

  const styles = theme => ({
    root: {
      '& .MuiTextField-root': {
        margin: theme.spacing(1),
      },
    },
    selectEmpty: {
      marginTop: theme.spacing(2),
    },
    formControl: {
      margin: theme.spacing(2),
      minWidth: 220,
    }, 
    upload: {
      '& > *': {
        margin: theme.spacing(1),
      },
    },
    cancel:{
      colorPrimary:"red"
    },separador:{
      marginBottom: theme.spacing(2),
      marginLeft: theme.spacing(7)
    }

  });


  const initialState = {
            open:false,

  }

class PopUpNuevoPassword extends Component{

    constructor(){
        super();
        this.state = initialState;
    }

    handleClickOpen = () => {
        this.setState({
            open:true
        })     
      };
    
    handleClose = () => {
        this.setState({
            open:false
        })
      };

  
     render(){
      const { classes,data } = this.props;

       return(
           <div>
              <Button variant="contained" color="primary" onClick={this.handleClickOpen} startIcon={<LockOpenOutlinedIcon />} className={classes.separador}>
                Cambiar contraseña
              </Button>
            
              <Dialog fullWidth open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                <CloseOutlinedIcon style={{ color: red[600] }}  onClick={this.handleClose} />
                <DialogTitle id="form-dialog-title" align="center">Nueva contraseña</DialogTitle>
                <ProfilePassword FinalizarhandleClose={this.handleClose}/>
              <DialogActions>

              </DialogActions>
      </Dialog>
           </div>
       )
     }
}
PopUpNuevoPassword.propTypes={
    classes: PropTypes.object.isRequired,
}

const mapStateToProps = (state) => ({
  data: state.data,

});

const mapActionsToProps = {
  
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(PopUpNuevoPassword))

import React,{Component} from 'react';

//MATERIAL UI STUFF
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import PropTypes from 'prop-types';
import DeleteIcon from '@material-ui/icons/Delete';
import withStyles from '@material-ui/core/styles/withStyles';

//REDUX stuff
import { deleteContrato } from '../../redux/actions/dataActions';
import { connect } from 'react-redux';

const styles = theme => ({
  wdialogue: {
    color: 'white',
    backgroundColor: 'rgb(255, 141, 141)',
  }
});

 class DeleteDialogue extends Component{

    constructor(props){
        super(props);
        this.state = {
            open:false
        };
    }

   handleClickOpen = () => {
       this.setState({
           open:true
       })
  };
  handleDelete = (event) => {
        event.preventDefault();
        const nif = this.props.eliminacion.nif
        this.props.deleteContrato(nif)
        this.setState({
            open:false
        })
  }

   handleClose = () => {
    this.setState({
        open:false
    })
  };
  render(){
    const { data,classes } = this.props;
  return (
    <div>
      <Button className={classes.wdialogue} variant="contained" onClick={this.handleClickOpen}> 
        <DeleteIcon />
      </Button>
      <Dialog
        open={this.state.open}
        onClose={this.handleClose}
        aria-labelledby="responsive-dialog-title"
      >
        <DialogTitle align="center"id="responsive-dialog-title">
          Rescindir contato del empleado
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            ¿Está seguro de rescindir el contrato de {this.props.eliminacion.name + "?"}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" autoFocus onClick={this.handleClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={this.handleDelete} variant="contained" color="primary" autoFocus>
            Eliminar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );}
}
DeleteDialogue.propTypes={
    classes: PropTypes.object.isRequired,
    deleteContrato: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
  deleteContrato
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(DeleteDialogue))

   
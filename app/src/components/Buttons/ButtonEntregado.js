import React,{Component} from 'react';

import { red } from '@material-ui/core/colors';
import CloseOutlinedIcon from '@material-ui/icons/CloseOutlined';
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import DialogContent from "@material-ui/core/DialogContent";
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import DialogTitle from '@material-ui/core/DialogTitle';
import CheckCircleOutlineIcon from '@material-ui/icons/CheckCircleOutline';
//REDUX stuff
import {entregado } from '../../redux/actions/dataActions';
import { connect } from 'react-redux';

 const styles = theme => ({
    
    cancel:{
      colorPrimary:"red"
    }, container: {
        display: 'flex',
        flexWrap: 'wrap',
      }, wdialogue: {
        width:230,
        marginTop:theme.spacing(2)
      },
      Buttons: {
          margin: theme.spacing(1),
          display: 'inline-block'
      }, wdialogue: {
        width:230,
      }
  });

  const initialState = {
    open:false,
    errors:null
}

 class ButtonEntregado extends Component{

    constructor(props){
        super(props);
        this.state = initialState
      }


    handleSubmit = (event) =>  {
        event.preventDefault();
        //Marcamos pedido como entregado 
        this.props.entregado(this.props.idPedido);
        this.setState({
            open:false
        })
     }
     handleChange = (event) => {
        this.setState({
          vehiculo:event.target.value
        })
      };


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
  return (
    <React.Fragment>
      <Button disabled={this.props.activacion==='EN_REPARTO'?false:true}variant="contained" color="primary" className={classes.wdialogue} onClick={this.handleClickOpen}  startIcon={<CheckCircleOutlineIcon />}>
        Entregar
      </Button>
      <Dialog  open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
      <CloseOutlinedIcon style={{ color: red[600] }}  onClick={this.handleClose} />
      <DialogTitle id="simple-dialog-title">Confirmar entrega</DialogTitle>
      <DialogContent>
            <form  id="editContrato" noValidate onSubmit={this.handleSubmit}>
            <Button className = {classes.Buttons} type="submit" variant="contained" color="primary">
                Si
            </Button>
            <Button className = {classes.Buttons} variant="contained" color="primary" onClick={this.handleClose}>
                No
            </Button>
            
            </form>

            </DialogContent>
     
        </Dialog>
        </React.Fragment>    
  );}
}
ButtonEntregado.propTypes={
    classes: PropTypes.object.isRequired,
    entregado: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    entregado,
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(ButtonEntregado))

   
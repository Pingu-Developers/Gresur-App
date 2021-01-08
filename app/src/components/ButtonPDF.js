import React,{Component} from 'react';


//MATERIAL UI STUFF
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import PictureAsPdfIcon from '@material-ui/icons/PictureAsPdf';
import TablaFactura from './TablaFactura';
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import DialogActions from "@material-ui/core/DialogActions";
import Slide from '@material-ui/core/Slide';

//REDUX stuff
import { connect } from 'react-redux';

 const styles = theme => ({
    
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

const ref = React.createRef(<TablaFactura></TablaFactura>);

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});
 class ButtonPDF extends Component{

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


     handleClickOpen = () => {
        this.setState({
            open:true
        })
   };   handleClose = () => {
    this.setState({
        open:false
    })
  };

  render(){
    return (
    <React.Fragment>
         <Button variant="contained" color="primary" onClick={this.handleClickOpen}  startIcon={<PictureAsPdfIcon />}>
         </Button>
        <Dialog
        TransitionComponent={Transition}
        fullScreen
        open={this.state.open}
        onClose={this.handleClose}
        aria-labelledby="responsive-dialog-title"
      >        <TablaFactura datos={this.props.idPedido} ></TablaFactura>
        <DialogActions>
          <Button variant="contained" autoFocus onClick={this.handleClose}color="primary">
            Salir
          </Button>
        </DialogActions>
        </Dialog>
    </React.Fragment>    
  );}
}
ButtonPDF.propTypes={
    classes: PropTypes.object.isRequired,
}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(ButtonPDF))

   
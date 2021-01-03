import React,{Component} from 'react';
import PropTypes from 'prop-types';

//MATERIAL UI stuff
import { makeStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import withStyles from '@material-ui/core/styles/withStyles';
import FormNuevoEmpleado from './FormNuevoEmpleado';
import FormNuevoContrato from './FormNuevoContrato';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
//REDUX stuff
import { addPersonal } from '../redux/actions/dataActions';
import { connect } from 'react-redux';


const styles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  backButton: {
    marginRight: theme.spacing(1),
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },finalizar:{
    marginLeft: theme.spacing(30),
    marginBottom: theme.spacing(2)
  }
}));

function getSteps() {
  return ['Añada un nuevo empleado', 'Añada su contrato', '¡Hecho!'];
}
const steps = getSteps();
 class StepperPersonal extends Component {
    constructor(props){
        super(props);
        this.state = {
            activeStep:0,
            nifEmpleado:'',
            close:true
        };
    }
    getStepContent(stepIndex) {
        switch (stepIndex) {
          case 0:
            return <FormNuevoEmpleado onSubmitNif={this.handleNif} onNextStep={this.handleNextStep}/>;
          case 1:
            return <FormNuevoContrato dni ={this.state.nifEmpleado}  onNextStep={this.handleNextStep}/>;
          case 2:
            return  <Button fullWidth className='finalizar' variant="contained" color="primary"  onClick={this.handleClose}startIcon={<CheckCircleIcon />}>
            Finalizar
          </Button>;
          default:
            return 'Ha ocurrido un error';
        }
      }
      //Coge el boton de añadir como step + 1 
      handleNextStep = (step) => {
        this.setState({
            activeStep: step + 1
        })
        };
    
        handleClose = () => {
            this.props.FinalizarhandleClose(false)
          };
 
      
      //Coge el nif del hijo Form de empleado para poder pasarselo a su otro hijo Form de Contrato
    handleNif = (nif) => {
       this.setState({
            nifEmpleado:nif
       })
   } 
    handleReset = () => {
    this.setState({
        activeStep: 0
    })
  };


render(){
    const { classes } = this.props;

    return (
    <div className={classes.root}>
      <Stepper activeStep={this.state.activeStep} alternativeLabel>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
      <div>
        {this.state.activeStep === steps.length ? (
          <div>
            <Typography className={classes.instructions}>All steps completed</Typography>
            <Button onClick={this.handleReset}>Reset</Button>
          </div>
        ) : (
          <div>
            <Typography className={classes.instructions}>{this.getStepContent(this.state.activeStep)}</Typography>

          </div>
        )}
      </div>
    </div>
  );
}
}

StepperPersonal.propTypes={
    classes: PropTypes.object.isRequired,
    addPersonal: PropTypes.func.isRequired,

}

const mapStateToProps = (state) => ({
  data: state.data,
});

const mapActionsToProps = {
    addPersonal,
}

export default connect(mapStateToProps,mapActionsToProps)(withStyles(styles)(StepperPersonal))

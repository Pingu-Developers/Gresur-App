import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';

import PersonIcon from '@material-ui/icons/Person';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import LocalShippingIcon from '@material-ui/icons/LocalShipping';
import ReceiptIcon from '@material-ui/icons/Receipt';

import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';


const useColorlibStepIconStyles = makeStyles({
    root: {
        backgroundColor: '#ccc',
        zIndex: 1,
        color: '#fff',
        width: 50,
        height: 50,
        display: 'flex',
        borderRadius: '50%',
        justifyContent: 'center',
        alignItems: 'center',
    },
    active: {
        backgroundImage:
        'linear-gradient(90deg, rgba(0,9,91,1) 0%, rgba(12,132,181,1) 0%, rgba(0,191,203,1) 100%)',
        boxShadow: '0 4px 10px 0 rgba(0,0,0,.25)',
    },
    completed: {
        backgroundImage:
        'linear-gradient(90deg, rgba(0,9,91,1) 0%, rgba(12,132,181,1) 0%, rgba(0,191,203,1) 100%)',
    },
});

function ColorlibStepIcon(props) {
    const classes = useColorlibStepIconStyles();
    const { active, completed } = props;
        
    const icons = {
        1: <PersonIcon />,
        2: <ShoppingCartIcon />,
        3: <LocalShippingIcon />,
        4: <ReceiptIcon/>,
    };

    return (
        <div
        className={clsx(classes.root, {
            [classes.active]: active,
            [classes.completed]: completed,
        })}
        >
        {icons[String(props.icon)]}
        </div>
    );
}

ColorlibStepIcon.propTypes = {
    /**
     * Whether this step is active.
     */
    active: PropTypes.bool,
    /**
     * Mark the step as completed. Is passed to child components.
     */
    completed: PropTypes.bool,
    /**
     * The label displayed in the step icon.
     */
    icon: PropTypes.node,
};

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    marginTop: '120px'
  },
  botonInvisible: {
    top: 0,
    position: 'absolute',
    zIndex: 0,
    display: 'none',
  },
  fieldset: {    
    borderRadius: 10,
    backgroundColor: '#f7f7f7',
    color: '#3d3d3d',
    maxHeight: 614,
    overflowY: 'auto',
    margin: '0 30px 0 30px',
  },
  instructions: {
    color: '#bdbdbd',
    fontWeight: 'bold',
    textAlign: 'center',
    fontSize: 30,
    padding: '60px 20px 10px 20px'
  }
}));

function getSteps(stepTitles, num) {
  if(stepTitles){
      return stepTitles;
  } else{
      var arr = []
      for(var i = 0; i<num; i++){
          arr.push('')
      }
      return arr;
  }
}

function getStepContent(step, children) {
  if(children){
    return children[step];
  }
}

export default function HorizontalLinearStepper(props) {
  const classes = useStyles();
  const [activeStep, setActiveStep] = React.useState(0);
  const [skipped, setSkipped] = React.useState(new Set());
  const steps = getSteps(props.stepTitles, props.children ? props.children.length : 0);

  const isStepOptional = (step) => {
    return props ? props.opcionales.includes(step) : false;
  };

  const isStepSkipped = (step) => {
    return skipped.has(step);
  };

  const handleNext = () => {
    let newSkipped = skipped;
    if (isStepSkipped(activeStep)) {
      newSkipped = new Set(newSkipped.values());
      newSkipped.delete(activeStep);
    }

    setActiveStep((prevActiveStep) => prevActiveStep + 1);
    setSkipped(newSkipped);
    return
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
    return
  };

  const handleSkip = () => {
    if (!isStepOptional(activeStep)) {
      // You probably want to guard against something like this,
      // it should never occur unless someone's actively trying to break something.
      throw new Error("You can't skip a step that isn't optional.");
    }

    setActiveStep((prevActiveStep) => prevActiveStep + 1);
    setSkipped((prevSkipped) => {
      const newSkipped = new Set(prevSkipped.values());
      newSkipped.add(activeStep);
      return newSkipped;
    });
  };

  const handleReset = () => {
    setActiveStep(0);
  };

  return (
    <div className={classes.root}>
      <Stepper activeStep={activeStep}>
        {steps.map((label, index) => {
          const stepProps = {};
          const labelProps = {};
          if (isStepSkipped(index)) {
            stepProps.completed = false;
          }
          return (
            <Step key={label} {...stepProps}>
              <StepLabel StepIconComponent={ColorlibStepIcon} {...labelProps}>{label}</StepLabel>
            </Step>
          );
        })}
      </Stepper>
      
      <div>
        {activeStep === steps.length ? (
          <div>   
            <fieldset className = {classes.fieldset} id = 'lastStep'>
              <Typography className={classes.instructions}>
                  Completado! Puede descargar el PDF con la factura del pedido aquí. También podrá acceder a la información 
                  del pedido desde el historial de pedidos.
              </Typography>
              <div style = {{display : 'flex', justifyContent : 'center'}}>
                {props.botonDescarga}
              </div>
              <div style = {{display : 'flex', justifyContent : 'flex-end'}}>
                <Button 
                    id='aceptar'
                    onClick={handleReset} 
                    color = "primary"
                    variant = "contained"
                    style = {{color : 'white', fontWeight : 'bold'}}
                    >
                  Aceptar
                </Button>
              </div>
            </fieldset>
          </div>
        ) : (

          <div>
            {getStepContent(activeStep, props.children)}
   
              <Button id = 'backButton' onClick = {handleBack} className={classes.botonInvisible}> </Button>

              {isStepOptional(activeStep) && (
              <Button id='skipButton' onClick={handleSkip} className={classes.botonInvisible}> </Button>)}
              <Button id='nextButton' onClick={handleNext} className={classes.botonInvisible}>  </Button>

          </div>
        )}
      </div>
    </div>
  );
}
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
import NavigateNextIcon from '@material-ui/icons/NavigateNext';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import DoneIcon from '@material-ui/icons/Done';

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
  },
  button: {
    marginRight: theme.spacing(1),
    borderRadius: '100%',
    height: 55,
    width: 50,
    margin: 0,
    marginTop: -30,
  },
  buttonDiv: {
      display: 'flex',
      justifyContent: 'space-between',
      width: '100vw',
      marginLeft: -24
  },
  botonHacker: {
    position: 'absolute',
    top: 0,
    zIndex: 0,
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
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

  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
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

  const handleSubmit = (event) => {
    event.preventDefault();
    props.onSubmit()
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
      
      <form id = 'nuevoPedido' 
            className={classes.instructions}
            onSubmit = {handleSubmit}>
      <div>
        {activeStep === steps.length ? (
          <div>           
              {document.getElementById('submit') ? document.getElementById('submit').click() : null }
            <Typography className={classes.instructions}>
              Completado!
            </Typography>
            <Button 
                id='aceptar'
                onClick={handleReset} 
                className={classes.button}>
              Aceptar
            </Button>
          </div>
        ) : (
          <div> 


            {getStepContent(activeStep, props.children)}
            

            <div className = {classes.buttonDiv}>
              <Button
                variant = "contained"
                disabled={activeStep === 0} 
                onClick={handleBack} 
                className={classes.button}
                >
                <ArrowBackIosIcon/>
              </Button>

              {isStepOptional(activeStep) && (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleSkip}
                  className={classes.button}
                >
                  Skip
                </Button>
              )}

                {
                activeStep === steps.length - 1 ?  
                <div>
                    <Button className = {classes.botonHacker} type = 'submit' id = 'submit'></Button>
                </div> : null
                }

              <Button
                variant="contained"
                color="primary"
                onClick={handleNext}
                className={classes.button}
              >
                {activeStep === steps.length - 1 ? <DoneIcon/> : <NavigateNextIcon />}
              </Button>

              

            </div>
          </div>
        )}
      </div>
      </form>
    </div>
  );
}
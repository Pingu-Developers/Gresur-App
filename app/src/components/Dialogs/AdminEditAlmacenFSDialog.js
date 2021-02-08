import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import CreateIcon from '@material-ui/icons/Create';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Box from '@material-ui/core/Box'

import EncargadoCatalogo from '../../pages/encargadoCatalogo';
import EncargadoGestion from '../../pages/encargadoGestion';


const useStyles = makeStyles((theme) => ({
  appBar: {
    position: 'relative',
    color: 'white',
    height: 60,
  },
  topbarTitle: {
    margin: 0,
    padding: '10px 20px 0px 20px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontWeight: 'bold',
    fontSize: 18,
    backgroundColor: '#f09b3b',
  },
  title: {
    marginLeft: theme.spacing(2),
    flex: 1,
  },
  btnOpen: {
      color: 'white',
      height: 60,
      width: 30,
      position: 'absolute',
      right: 10,
      bottom: 10,
      borderRadius: '50%',
  },
  tab: {
      marginTop: 10,
      fontWeight: 'bold',
      fontSize: 18,
  }
}));

function TabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`simple-tabpanel-${index}`}
        aria-labelledby={`simple-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box p={3}>
            <Typography>{children}</Typography>
          </Box>
        )}
      </div>
    );
  }
  
  TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
  };
  
  function a11yProps(index) {
    return {
      id: `simple-tab-${index}`,
      'aria-controls': `simple-tabpanel-${index}`,
    };
  }

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export default function FullScreenDialog(props) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    props.updtFunc();
    setOpen(false);
  };

  return (
    <div>
        <Button variant = "contained" className = {classes.btnOpen} color="secondary" onClick={handleClickOpen}>
            <CreateIcon/>
        </Button>
        <Dialog fullScreen open={open} onClose={handleClose} TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
                
                <Toolbar style = {{display: 'inline-flex', justifyContent: 'space-between'}}>
                    <span style = {{display: 'inline-flex', margin: '-4px 0px 0px -24px', height: 60}}>
                        <Typography className = {classes.topbarTitle}>Gestion del Almacen {props.almacenId}</Typography>
                        <Tabs 
                        value={value} 
                        onChange={handleChange} 
                        aria-label="simple tabs example" 
                        style = {{height: '100%'}}>
                            <Tab label="Catalogo" {...a11yProps(0)} className = {classes.tab}/>
                            <Tab label="Estanterias" {...a11yProps(1)} className = {classes.tab}/>
                        </Tabs>
                    </span>
                    <IconButton edge="start" color="inherit" onClick={handleClose} aria-label="close">
                        <CloseIcon />
                    </IconButton>
                </Toolbar>
            </AppBar>

        <TabPanel value={value} index={0}>
            <EncargadoCatalogo almacenAdm = {props.almacenId}/>
        </TabPanel>

        <TabPanel value={value} index={1}>
            <EncargadoGestion almacenAdm = {props.almacenId}/>
        </TabPanel>


      </Dialog>
    </div>
  );
}
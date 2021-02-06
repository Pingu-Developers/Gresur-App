import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import VehiculosInfoAdmin from '../HistoryLists/VehiculosInfoAdmin';
import HistorialPedidosAdmin from '../HistoryLists/HistorialPedidosAdmin';


function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
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
    id: `vertical-tab-${index}`,
    'aria-controls': `vertical-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    display: 'flex',
    height: 'min-content',
  },
  tabs: {
    borderRight: `1px solid ${theme.palette.divider}`,
    minWidth: '10%'
  },

}));

export default function VerticalTabs(props) {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);
  const vehiculos = props.datos.vehiculosITVSeguroReparacion;
  const pedidos = props.datos.pedidos;

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <Tabs
        orientation="vertical"
        variant="scrollable"
        indicatorColor="secondary"
        textColor="secondary"
        value={value}
        onChange={handleChange}
        aria-label="Vertical tabs example"
        className={classes.tabs}
      >
        <Tab label="Ver vehiculos" {...a11yProps(0)} />
        <Tab label="Ver pedidos" {...a11yProps(1)} />

      </Tabs>
      <TabPanel style={{width:'100%'}} value={value} index={0}>
        <VehiculosInfoAdmin datos = {vehiculos}/>      
      </TabPanel>
      <TabPanel style={{width:'100%'}} value={value} index={1}>
        <HistorialPedidosAdmin datos = {pedidos}/>
      </TabPanel>
    </div>
  );
}




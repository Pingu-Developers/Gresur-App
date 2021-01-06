import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Facturas from './administradorFacturacion/administradorHacerFacturas'
import Balance from './administradorFacturacion/administradorBalance'
import Rectificar from './administradorFacturacion/administradorRectificar'

import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';


const style =theme=> ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
        display: 'flex',
        height: 260,
      },
    tabs: {
        borderRight: `1px solid ${theme.palette.divider}`,
    },
});

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

class administradorFacturacion extends Component {
    constructor(props){
        super(props);
        this.state = {
            value:0
        }
    }

    handleChange(newValue) {
        this.setState({
            value:newValue
        })
    }

    render() {
        const { classes } = this.props
        return (
            <div className={classes.root}>
                <Tabs
                    orientation="vertical"
                    indicatorColor="secondary"
                    textColor="secondary"
                    value={this.state.value}
                    onChange={(event, newValue)=>{this.handleChange(newValue)}}
                    aria-label="Vertical tabs example"
                    className={classes.tabs}
                >
                    <Tab label="Hacer factura" {...a11yProps(0)} />
                    <Tab label="Balance" {...a11yProps(1)} />
                    <Tab label="Rectificar factura" {...a11yProps(2)} />
                </Tabs>
                <TabPanel value={this.state.value} index={0}>
                    <Facturas/>
                </TabPanel>
                <TabPanel value={this.state.value} index={1}>
                    <Balance/>
                </TabPanel>
                <TabPanel value={this.state.value} index={2}>
                    <Rectificar/>
                </TabPanel>
            </div>
        )
    }
}

administradorFacturacion.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorFacturacion))
import React, { Component } from 'react'
import withStyles from '@material-ui/core/styles/withStyles';
import PropTypes from 'prop-types';
import GresurImg from '../images/Gresur_rectangles.png';
import { withRouter } from "react-router";

//MUI Stuff
import Avatar from '@material-ui/core/Avatar';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';

import NotificationCenter from './NotificationCenter'
import ProfileMenu from './ProfileMenu';

//redux stuff
import { connect } from 'react-redux';

const styles = {
    root: {
        flexGrow: 1,
      },
    large: {
        width: 60,
        height: 60,
      },
    menuButton: {
        marginRight: 20,
      },
    tabs: {
        flexGrow: 1,
      },
    button: {
        display: "inline-block",
        paddingTop: 7,
        color: "white",
    },
    nombreUser: {
        marginRight: 20,
        fontSize: 22,
        fontWeight: 600,
    },
}

// TABS COMPONENT
function TabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`wrapped-tabpanel-${index}`}
        aria-labelledby={`wrapped-tab-${index}`}
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
      id: `wrapped-tab-${index}`,
      'aria-controls': `wrapped-tabpanel-${index}`,
    };
  }


//TOPBAR COMPONENT
  class Topbar extends Component {

    constructor(props){
        super(props);
        this.anchorEl = null;
        this.state = {tabValue:props.selectedTab}
    }

    tabHandleChange = (event, newValue) => {
        this.setState({tabValue : newValue})
      };
    

    render() {
        const { classes , user} = this.props
        document.body.style.background = ``;
        return (
        <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Avatar src={GresurImg} className={classes.large}/>

                <div className={classes.tabs}>
                    <Tabs value={this.state.tabValue} onChange={this.tabHandleChange} aria-label="wrapped label tabs example">
                        <Tab
                            value="one"
                            label="New Arrivals in the Longest Text of Nonfiction"
                            wrapped
                            {...a11yProps('one')}
                        />
                        <Tab value="two" label="Item Two" {...a11yProps('two')} />
                    </Tabs>
                </div>

                <div className={classes.button}>
                    <Typography variant='h5' align='center' display='inline' className={classes.nombreUser}>
                        {user.personal?user.personal.name:''}
                    </Typography>
                    

                    <NotificationCenter />
                    <ProfileMenu/>
                </div>
            </Toolbar>     
        </AppBar>

        <TabPanel value={this.state.tabValue} index="one">
           Item one
        </TabPanel>
        <TabPanel value={this.state.tabValue} index="two">
            Item Two
        </TabPanel>
        
        </div>
        )
    }
}

Topbar.propTypes = {
    classes: PropTypes.object.isRequired,
    user:PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    user: state.user
})

export default withRouter(connect(mapStateToProps)(withStyles(styles)(Topbar)));

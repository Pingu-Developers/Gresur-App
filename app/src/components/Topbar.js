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
    tabsDiv: {
      flexGrow: 1,
      paddingLeft: 30
    },
    tabs: {
      minHeight: 64,
    },
    tab: {
      minHeight: 64,
      color: "white",
      fontSize: 17,
      fontWeight: 600
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
        this.state = {tabValue : null}
    }
    componentDidMount(){
      this.setState({tabValue : this.props.selectedTab})
    }
    componentDidUpdate(){
      if(!this.state.tabValue && this.props.selectedTab){
        this.setState({tabValue : this.props.selectedTab})
      }
    }

    tabHandleChange = (event, newValue) => {
        this.setState({tabValue : newValue})
      };
    

    render() {
        const { classes , user, dict} = this.props
        document.body.style.background = ``;
        return (
        <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Avatar src={GresurImg} style = {{cursor: 'pointer'}} className={classes.large} onClick = {(e) => window.location.reload()}/>

                <div className={classes.tabsDiv}>
                    <Tabs 
                      value={this.state.tabValue} 
                      onChange={this.tabHandleChange} 
                      aria-label="topnav tabs"
                      className={classes.tabs}
                      >
                        {
                        dict ? Object.keys(dict).map((key) =>         
                          <Tab
                            value= {key}
                            label= {key}
                            wrapped
                            className = {classes.tab}
                            {...a11yProps(key)}
                          />
                        ) : null
                        }
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
        
        {
          dict ? Object.entries(dict).map((pair) => 
            <TabPanel value={this.state.tabValue} index={pair[0]}>
              {pair[1]}
            </TabPanel>
          ):null
        }
              
        </div>
        )
    }
}

Topbar.propTypes = {
    classes: PropTypes.object.isRequired,
    user:PropTypes.object.isRequired,
    selectedTab:PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    user: state.user
})

export default withRouter(connect(mapStateToProps)(withStyles(styles)(Topbar)));

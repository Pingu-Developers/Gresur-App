import React, { Component } from 'react'
import withStyles from '@material-ui/core/styles/withStyles';
import PropTypes from 'prop-types';

//MUI Stuff
import Avatar from '@material-ui/core/Avatar';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import Notifications from '@material-ui/icons/Notifications';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';

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
      title: {
        flexGrow: 1,
      },
    
}

class Topbar extends Component {

    constructor(props){
        super(props);
        this.anchorEl = null;
        this.open = Boolean(this.anchorEl);
    }
    
   handleMenu = (event) => {
    this.setState({
        anchorEl: event.currentTarget
    });
  };

   handleClose = () => {
    this.setState({
        anchorEl: null
    });
  };

    render() {
        const { classes } = this.props

        return (
        <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
            <Avatar src="Gresur_transparente.png" className={classes.large}/>
            <div className={classes.title}>

            </div>
                <div>
                <Typography variant='h5' align='center' display='inline'>
                    Nombre de este seño
                </Typography>
                
                <IconButton
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    color="inherit"
                >
                    <Notifications />
                </IconButton>
                
                <IconButton
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    onClick={this.handleMenu}
                    color="inherit"
                >
                    <AccountCircle />
                </IconButton>
                <Menu
                    id="menu-appbar"
                    anchorEl={this.anchorEl}
                    anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                    }}
                    open={this.open}
                    onClose={this.handleClose}
                >
                    <MenuItem onClick={this.handleClose}>Logout</MenuItem>
                    <MenuItem onClick={this.handleClose}>My account</MenuItem>
                </Menu>
                </div>
            </Toolbar>
        </AppBar>
        </div>
        )
    }
}

Topbar.propTypes = {
    classes: PropTypes.object.isRequired
}

export default withStyles(styles)(Topbar)

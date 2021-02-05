import React from 'react';
import PropTypes from 'prop-types';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import AccountCircle from '@material-ui/icons/AccountCircle';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { logoutUser,getUserData } from '../redux/actions/userActions';
import { withRouter } from "react-router";
import Slide from '@material-ui/core/Slide';
import FormUserSettings from './FormUserSettings';
import { useEffect } from 'react';


const useStyles = makeStyles((theme) => ({
    root: {
        display: 'inline-block',
    },
    iconCircle: {
        marginTop: -5,
        marginRight: -7,
    }
}));

function ProfileMenu(props) {

    const classes = useStyles();
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [openDialog, setOpenDialog] = React.useState(false);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };
   
  const handleClickOpenDialog = (value) => {
    setOpenDialog(true);
  };
 
  const handleCloseDialog = () => {
    setOpenDialog(false);
  };
 
    const Transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="up" ref={ref} {...props} />;
      });
      
    const handleLogout = () => {
        setAnchorEl(null);
        props.logoutUser();
        props.history.push('/login');
    };

    return (
        <div className = {classes.root}>
            <IconButton
                aria-controls="simple-menu"
                aria-haspopup="true"
                color="inherit"
                onClick={handleClick}
                className={classes.iconCircle}
            >
                <AccountCircle />
            </IconButton>
        <Menu
            id="simple-menu"
            anchorEl={anchorEl}
            keepMounted
            open={Boolean(anchorEl)}
            onClose={handleClose}
        >
            <MenuItem onClick={() => { handleClose(); handleClickOpenDialog();}}>Perfil</MenuItem>
                {openDialog===true?<FormUserSettings open={openDialog} onClose={handleCloseDialog} usuario={props.userSettings}></FormUserSettings>:null
                }
                
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </Menu>
        </div>
    );
}
ProfileMenu.propTypes = {
    classes: PropTypes.object.isRequired,
    logoutUser: PropTypes.func.isRequired,
    user:PropTypes.object.isRequired,
    UI:PropTypes.object.isRequired,
}

const mapStateToProps = (state) => ({
    user: state.user,
    UI: state.UI,
    userSettings: state.user.personal,
});

const mapActionsToProps = {
    logoutUser,
}

export default withRouter(connect(mapStateToProps,mapActionsToProps)(ProfileMenu));
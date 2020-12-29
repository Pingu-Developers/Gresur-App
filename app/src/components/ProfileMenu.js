import React from 'react';
import PropTypes from 'prop-types';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import AccountCircle from '@material-ui/icons/AccountCircle';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { logoutUser } from '../redux/actions/userActions';
import { withRouter } from "react-router";

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'inline-block',
    },
}));

function ProfileMenu(props) {
    const classes = useStyles();
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

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
            <MenuItem onClick={handleClose}>Profile</MenuItem>
            <MenuItem onClick={handleClose}>My account</MenuItem>
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </Menu>
        </div>
    );
}
ProfileMenu.propTypes = {
    classes: PropTypes.object.isRequired,
    logoutUser: PropTypes.func.isRequired,
    user:PropTypes.object.isRequired,
    UI:PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    user: state.user,
    UI: state.UI
});

const mapActionsToProps = {
    logoutUser
}

export default withRouter(connect(mapStateToProps,mapActionsToProps)(ProfileMenu));
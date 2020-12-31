import React, { Component } from 'react'
import PropTypes from 'prop-types'
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux'
import { getNotificacionesNoLeidas } from '../redux/actions/userActions';

import IconButton from '@material-ui/core/IconButton';
import Notifications from '@material-ui/icons/Notifications';
import Badge from '@material-ui/core/Badge';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';


const style = {
    profileIcons:{
        marginTop: -5,
    }
}

export class NotificationCenter extends Component {

    constructor(props) {
        super(props);
        this.state = {
            anchorEl:null,
        };
    }

    componentDidMount() {
        this.props.getNotificacionesNoLeidas()
        this.interval = setInterval(() => this.props.getNotificacionesNoLeidas(), 60000);
    }
    
    componentWillUnmount() {
        clearInterval(this.interval);
    }

    handleClick = (event) => {
        console.log("IM HERE2")
        this.setState({
            anchorEl:event.currentTarget
        })
    };
    
    handleClose = () => {
        console.log("IM HERE")
        this.setState({
            anchorEl:null
        })
    };

    render() {

        const {classes,user} = this.props;
        
        const open = Boolean(this.state.anchorEl);
        const id = open ? 'simple-popover' : undefined;

        return (
                <IconButton
                    className = {classes.profileIcons}
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    aria-describedby={id}
                    color="inherit"
                    onClick={open?null:this.handleClick}
                >
                    <Badge badgeContent={user.nNotification} color="secondary">
                        <Notifications/>    
                    </Badge>
                    <Popover
                        id={id}
                        open={open}
                        anchorEl={this.state.anchorEl}
                        onClose={this.handleClose}
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'center',
                        }}
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'center',
                        }}
                        >
                        <Typography className={classes.typography}>The content of the Popover.</Typography>
                    </Popover>
                </IconButton>
        )
    }
}

NotificationCenter.propTypes = {
    classes: PropTypes.object.isRequired,
    user : PropTypes.object.isRequired,
    getNotificacionesNoLeidas: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    user : state.user
})

const mapActionsToProps = {
    getNotificacionesNoLeidas
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(NotificationCenter))

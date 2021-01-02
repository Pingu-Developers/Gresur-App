import React, { Component } from 'react'
import PropTypes from 'prop-types'
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux'
import { getNotificacionesNoLeidas, clearNotificacionesNoLeidas, setNotificacionLeida } from '../redux/actions/userActions';
import ListadoNotificacion from './ListadoNotificacion'

import IconButton from '@material-ui/core/IconButton';
import Notifications from '@material-ui/icons/Notifications';
import Badge from '@material-ui/core/Badge';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';


const style = {
    profileIcons:{
        marginTop: -5,
    },
    popover:{
        maxHeight: 900,
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
        this.interval = setInterval(() => this.props.getNotificacionesNoLeidas(), 15000);
    }
    
    componentWillUnmount() {
        clearInterval(this.interval);
    }

    handleClick = (event) => {
        this.setState({
            anchorEl:event.currentTarget
        })
    };
    
    handleClose = () => {
        this.setState({
            anchorEl:null
        })
    };

    render() {

        const { classes,user:{ notificaciones } } = this.props;
        
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
                    <Badge badgeContent={notificaciones?notificaciones.length:null} color="secondary">
                        <Notifications/>    
                    </Badge>
                    <Popover
                        id={id}
                        open={open}
                        className = {classes.popover}
                        anchorEl={this.state.anchorEl}
                        onClose={this.handleClose}
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'left',
                          }}
                          transformOrigin={{
                            vertical: 'top',
                            horizontal: 'center',
                          }}
                        >
                        <ListadoNotificacion confirmNoti={this.props.setNotificacionLeida} notificaciones = {notificaciones?notificaciones:null}/>
                    </Popover>
                </IconButton>
        )
    }
}

NotificationCenter.propTypes = {
    classes: PropTypes.object.isRequired,
    user : PropTypes.object.isRequired,
    getNotificacionesNoLeidas : PropTypes.func.isRequired,
    clearNotificacionesNoLeidas: PropTypes.func.isRequired,
    setNotificacionLeida: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    user : state.user
})

const mapActionsToProps = {
    getNotificacionesNoLeidas,
    clearNotificacionesNoLeidas,
    setNotificacionLeida
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(NotificationCenter))

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import Profile from '../components/Profile';

const style = {

}

class personalProfile extends Component {
    static propTypes = {
        prop: PropTypes
    }

    render() {
        return (
                <Profile></Profile>
        )
    }
}

personalProfile.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(personalProfile))

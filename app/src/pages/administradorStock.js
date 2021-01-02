import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Topbar from '../components/Topbar';

const style = {

}

class administradorStock extends Component {
    static propTypes = {
        prop: PropTypes
    }

    render() {
        return (
            <div>
                <h1>WIP</h1>
            </div>
        )
    }
}

administradorStock.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(administradorStock))
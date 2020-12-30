import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Topbar from '../components/Topbar';

const style = {

}

class dependienteHistorialPedido extends Component {
    static propTypes = {
        prop: PropTypes
    }

    render() {
        return (
            <div>
                <Topbar/>
            </div>
        )
    }
}

dependienteHistorialPedido.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapDispatchToProps = {
    
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(style)(dependienteHistorialPedido))

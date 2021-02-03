import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import Mapa from '../components/GoogleMap'


const style = {

}

class transportistaMapa extends Component {
    static propTypes = {
        prop: PropTypes
    }

    render() {
        return (
                <Mapa></Mapa>
        )
    }
}

transportistaMapa.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapActionsToProps = {
    
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(transportistaMapa))

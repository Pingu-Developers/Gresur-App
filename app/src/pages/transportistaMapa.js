import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';

import Topbar from '../components/Topbar';

const style = {

}

class transportistaMapa extends Component {
    static propTypes = {
        prop: PropTypes
    }

    render() {
        return (
                <iframe src="https://zen-shannon-219066.netlify.app/index.html"  frameborder="0" width="100%" height="800px"></iframe>
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

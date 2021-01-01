import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import Topbar from '../components/Topbar';

export class main extends Component {
    static propTypes = {
        prop: PropTypes
    }

    render() {
        return (
            <div>
                <Topbar selectedTab = "one"/>
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    
})

const mapDispatchToProps = {
    
}

export default connect(mapStateToProps, mapDispatchToProps)(main)

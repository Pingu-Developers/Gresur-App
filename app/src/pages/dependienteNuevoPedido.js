import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import Topbar from '../components/Topbar'

export class dependienteNuevoPedido extends Component {
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
dependienteNuevoPedido.propTypes = {

}

const mapStateToProps = (state) => ({
    
})

const mapDispatchToProps = {
    
}

export default connect(mapStateToProps, mapDispatchToProps)(dependienteNuevoPedido)


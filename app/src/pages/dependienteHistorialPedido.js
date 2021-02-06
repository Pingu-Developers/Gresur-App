import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { loadPedidosPaginados, clear} from '../redux/actions/dataActions';

import HistorialPedidosAdmin from '../components/HistoryLists/HistorialPedidosAdmin';
import SnackCallController from '../components/Other/SnackCallController';

export class dependienteHistorialPedido2 extends Component {
    static propTypes = {
        prop: PropTypes
    }

    componentDidMount(){
        this.props.loadPedidosPaginados("",0,2);
    }

    componentWillUnmount(){
        this.props.clear();
    }

    render() {
        const {classes, data:{pedidos},UI:{errors,enviado}} = this.props;

        return (
            <div style={{margin:"auto", width:"88%"}}>
                <SnackCallController  enviado = {enviado} message = {"Operacion realizada correctamente"} errors={errors} />
                {pedidos.content?<HistorialPedidosAdmin datos = {pedidos}/>:null}
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    data: state.data,
    UI: state.UI
})

const mapDispatchToProps = {
    loadPedidosPaginados, 
    clear
}

export default connect(mapStateToProps, mapDispatchToProps)(dependienteHistorialPedido2)

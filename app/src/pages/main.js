import React from 'react'
import PropTypes from 'prop-types'
import Topbar from '../components/Other/Topbar'

//Pages
import AdministradorPersonal from './administradorPersonal'
import AdministradorFacturacion from './administradorFacturacion'
import AdministradorStock from './administradorStock'
import AdministradorTransporte from './administradorTransporte'
import EncargadoCatalogo from './encargadoCatalogo'
import EncargadoGestion from './encargadoGestion'
import TranportistaPedidos from './transportistaPedido'
import TransportistaVehiculos from './transportistaVehiculos'
import TransportistaMapa from './transportistaMapa'
import DependienteNuevoPedido from './dependienteNuevoPedido'
import DependienteDevoluciones from './dependienteDevoluciones'
import DependienteCatalogo from './dependienteCatalogo'
import DependienteHistorialPedido from './dependienteHistorialPedido'

//Redux stuff
import { connect } from 'react-redux';

function main(props) {

    const {user:{roles}} = props

    let selectedTab = null;
    let dict = null;

    if(roles){
        switch(roles[0]){
            case 'ROLE_DEPENDIENTE':
                selectedTab = 'Catalogo';
                dict = {'Nuevo Pedido' : <DependienteNuevoPedido/>, 
                        'Todos los pedidos' : <DependienteHistorialPedido/>,
                        'Catalogo' : <DependienteCatalogo/>,
                        'Devoluciones' : <DependienteDevoluciones/>}
                break;
            case 'ROLE_TRANSPORTISTA':
                selectedTab = 'Pedidos';
                dict = {'Pedidos' : <TranportistaPedidos/>,
                        'Mis vehiculos' : <TransportistaVehiculos/>,
                        'Mapa' : <TransportistaMapa/>} 
                break;
            
            case "ROLE_ENCARGADO":
                selectedTab = 'Catalogo';
                dict = {'Catalogo' : <EncargadoCatalogo/>,
                        'Gestion de almacen' : <EncargadoGestion/>}
                break;

            case "ROLE_ADMIN":
                selectedTab = 'Personal';
                dict = {'Personal' : <AdministradorPersonal/>,
                        'Gestion de transporte' : <AdministradorTransporte/>,
                        'Gestion de stock' : <AdministradorStock/>,
                        'Facturacion' : <AdministradorFacturacion/>}
                break;

            default:
                console.log("que hace quien ere")
                break;
        }
    }
    

    return (
        <div>
            <Topbar selectedTab = {selectedTab} dict = {dict}/>
        </div>
    )
}

main.propTypes = {
    user:PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    user:state.user
})

export default connect(mapStateToProps)(main)

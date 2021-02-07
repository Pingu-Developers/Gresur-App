import { SET_ALMACENGESTIONENCARGADO, CLEAR_ALMACENGESTIONENCARGADO, SET_BALANCE,SET_ENVIADO, CLEAR_BALANCE, SET_PEDIDO, CLEAR_PEDIDO, CLEAR_ALMACENGESTION, SET_ALMACENGESTION, CLEAR_CLIENTE, SET_CLIENTE, CLEAR ,CLEAR_ISDEFAULTER, SET_PEDIDOS, SET_ERRORS, CLEAR_PEDIDOS, LOADING_UI, CLEAR_ERRORS, SET_PRODUCTOS, CLEAR_PRODUCTOS,SET_PERSONAL,CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_OCUPACION, CLEAR_OCUPACION, SET_ISDEFAULTER, SET_VEHICULOSITVSEGUROREPARACION, CLEAR_VEHICULOSITVSEGUROREPARACION,SET_CONTRATO,SET_ALMACEN,CLEAR_CONTRATO,SET_FACTURAS, SET_TIPOSVEHICULOS, CLEAR_TIPOSVEHICULOS,SET_USER  } from '../types';
import axios from 'axios';
import {getUserData} from './userActions';
export const loadPedidos = (orden="DEFAULT") => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/${orden}`)
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: res.data})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const loadPedidosPaginados = (orden="",pageNo,pageSize) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/page=${pageNo}&size=${pageSize}&order=${orden}`)
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: {content:res.data.content,totalElements:res.data.totalElements,totalPages:res.data.totalPages}})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const loadPedidoById = (id) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/id/${id}`)
        .then((res) => {
            dispatch({type: SET_PEDIDO, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const loadPedidosByEstado = (estado, orden="DEFAULT") => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/${estado}/${orden}`)
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: res.data});
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const loadPedidosByEstadoPaginado = (estado, orden="",pageNo,pageSize) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/${estado}/page=${pageNo}&size=${pageSize}&order=${orden}`)
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: {content:res.data.content,totalElements:res.data.totalElements,totalPages:res.data.totalPages}});
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const clearPedidos = () => (dispatch) => {
    dispatch({type: CLEAR_PEDIDOS})
}

export const cancelarPedido = (id, estado="TODO", orden="",pageNo,pageSize) => (dispatch) => {

    axios.post(`/pedido/${id}`)
        .then((res) => {
            estado === "TODO" ? dispatch(loadPedidosPaginados(orden,pageNo,pageSize)) : dispatch(loadPedidosByEstadoPaginado(estado, orden,pageNo,pageSize))
            dispatch({
                type: SET_ENVIADO,
            });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
               console.log(err)
            }
        })

}

export const loadProductos = () => (dispatch) => {
    dispatch({type: LOADING_UI})

    axios.get('/producto')
        .then((res) => {
            dispatch({type: SET_PRODUCTOS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {

            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const loadProductosByNombre = (nombre) => (dispatch) => {
    dispatch({type: LOADING_UI})

    axios.get(`/producto/${nombre}`)
        .then((res) => {
            dispatch({type: SET_PRODUCTOS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {

            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearProductos = () => (dispatch) => {
    dispatch({type: CLEAR_PRODUCTOS})
}

export const loadPersonalContrato = () => function (dispatch) {
    dispatch({type: LOADING_UI})
    
    axios.get('/contrato')
        .then((res) => {
            dispatch({type: SET_CONTRATO, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        })
}

export const loadPersonal = () => function (dispatch) {
    dispatch({type: LOADING_UI})
    
    axios.get('/adm/personal')
        .then((res) => {
            dispatch({type: SET_PERSONAL, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response
            })
        })
}

export const deleteContrato = (nif) => function (dispatch){
    dispatch({type: LOADING_UI})
    axios.delete(`/contrato/delete/${nif}`)
        .then((res) => {
            dispatch(loadPersonalContrato())
            dispatch({
                type: SET_ENVIADO,
            });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
               console.log(err)
            }
        })
}
export const loadAlmacen = () => function (dispatch){
    dispatch({type: LOADING_UI})
    
    axios.get('/almacen')
        .then((res) => {
            dispatch({type: SET_ALMACEN, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response
            })
        })


}

export const loadAlmacenDisponible = () => function (dispatch){
    dispatch({type: LOADING_UI})
    
    axios.get('/encargado/almacen')
        .then((res) => {
            dispatch({type: SET_ALMACEN, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response
            })
        })


}

export const clearPersonal = () => (dispatch) => {
    dispatch({type: CLEAR_PERSONAL})
    
}

export const loadPersonalProfile = () => function (dispatch) {
    dispatch({type: LOADING_UI})
    
    axios.get('/adm/personal/profile')
        .then((res) => {
            dispatch({type: SET_USER, payload: res})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response
            })
        })
}
 
export const putPersonalProfile = (personal) => function (dispatch) {
    axios.put('/adm/personal/profile',personal)    .then((res) => {
        dispatch(loadPersonalContrato());
        dispatch(getUserData());
    })
    .catch((err) => {
        if(err.response){
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        } else {
            dispatch({
                type: SET_ERRORS,
                payload: err
            })
        }
    })
}

export const putPersonalProfilePassword = (personal) => function (dispatch) {
    axios.put('/auth/password',personal)    .then((res) => {
        dispatch(loadPersonalProfile());
    })
    .catch((err) => {
        if(err.response){
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        } else {
            dispatch({
                type: SET_ERRORS,
                payload: err
            })
        }
    })
}


export const addPersonal = (rolEmpleado,personal) => (dispatch) =>{
        axios.post(`/adm/add/${rolEmpleado}`,personal)
        .then((res) => {
            dispatch(loadPersonal());
        })
        .catch((err) => {
            console.log(err)
        }) 
}

export const addContrato = (nif,contrato) => (dispatch) =>{
    axios.post(`/contrato/add/${nif}`,contrato)
    .then((res) => {
        dispatch(loadPersonalContrato());
        dispatch({
            type: SET_ENVIADO,
        });
    })
    .catch((err) => {
        console.log(err)
    }) 
}

export const editContrato = (nif,contrato) => (dispatch) =>{
    axios.put(`/contrato/update/${nif}`,contrato)
    .then((res) => {
        dispatch(loadPersonalContrato());
        dispatch({
            type: SET_ENVIADO,
        });
    })
    .catch((err) => {
        if(err.response){
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        } else {
           console.log(err)
        }
    })
}

export const clearContrato = () => (dispatch) => {
    dispatch({type: CLEAR_CONTRATO})
}

export const enReparto = (id,vehiculo) => (dispatch) =>{
    axios.put(`/pedido/reparto/${id}`,vehiculo)
    .then((res) => {
        dispatch(loadPedidosHoy());
        dispatch({
            type: SET_ENVIADO,
        });
    })
    .catch((err) => {
        console.log(err)
    }) 
}

export const entregado = (id) => (dispatch) =>{
    axios.put(`/pedido/entregado/${id}`)
    .then((res) => {
        dispatch(loadPedidosHoy());
        dispatch({
            type: SET_ENVIADO,
        });
    })
    .catch((err) => {
        console.log(err)
    }) 
}

export const loadPedidosHoy = () => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get('/pedido/hoy')
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: res.data})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}


export const transportistaEnReparto = () => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get('/auth/transportista')
        .then((res) => {
            dispatch({type: SET_PERSONAL, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const loadPedidosByEstadoTransportista = (estado) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/transportista/${estado}`)
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: res.data});
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const loadFactura = (id) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/factura/${id}`)
        .then((res) => {
            dispatch({type: SET_FACTURAS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}


export const loadVehiculosITVSeguroDisponibilidadByTransportista = () => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get('/vehiculo')
        .then((res) => {
            dispatch({type: SET_VEHICULOS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearVehiculos = () => (dispatch) => {
    dispatch({type: CLEAR_VEHICULOS})
}

export const setProducto = (producto) => (dispatch) => {

    axios.post('/producto/save', producto)
        .then((res) => {
            dispatch(loadProductos());
            dispatch({
                type: SET_ENVIADO,
            });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
               console.log(err)
            }
        })
}

export const loadOcupacionVehiculosEnReparto = () => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get('/pedido/ocupacion')
        .then((res) => {
            dispatch({type: SET_OCUPACION, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearOcupacion = () => (dispatch) => {
    dispatch({type: CLEAR_OCUPACION})
}


export const loadClienteIsDefaulter = (NIF) => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get(`/cliente/${NIF}/isDefaulter`)
        .then((res) => {
            dispatch({type: SET_ISDEFAULTER, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearClienteIsDefaulter = () => (dispatch) => {
    dispatch({type: CLEAR_ISDEFAULTER})
}


export const loadCliente = (NIF) => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get(`/cliente/${NIF}`)
        .then((res) => {
            dispatch({type: SET_CLIENTE, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearClienteByNIF = () => (dispatch) => {
    dispatch({type: CLEAR_CLIENTE})
}

export const clear = () => (dispatch) => {
    dispatch({type: CLEAR})
}

export const loadAlmacenGestion = () => (dispatch) => {
    dispatch({type: LOADING_UI})
    axios.get(`/almacen/gestion`)
        .then((res) => {
            dispatch({type: SET_ALMACENGESTION, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}
export const clearAlmacenGestion = () => (dispatch) => {
    dispatch({type: CLEAR_ALMACENGESTION})
}

export const loadAlmacenGestionEncargado = () => (dispatch) => {
    dispatch({type: LOADING_UI})
    axios.get(`/almacen/gestionEncargado`)
        .then((res) => {
            dispatch({type: SET_ALMACENGESTIONENCARGADO, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const updateEstanteriaCapacidad = (categoria, capacidad) => (dispatch) =>{
    axios.put(`/estanterias/update/${categoria}/${capacidad}`)
    .then((res) => {
        dispatch(loadAlmacenGestionEncargado());
        dispatch({
            type: SET_ENVIADO,
        });
    })
    .catch((err) => {
        if(err.response){
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        } else {
            dispatch({
                type: SET_ERRORS,
                payload: err
            })
        }
        dispatch(loadAlmacenGestionEncargado());
    })
}

export const clearAlmacenGestionEncargado = () => (dispatch) => {
    dispatch({type: CLEAR_ALMACENGESTIONENCARGADO})
}

export const loadVehiculosSeguroITVReparacion = () => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get('/vehiculo/all')
        .then((res) => {
            dispatch({type: SET_VEHICULOSITVSEGUROREPARACION, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearVehiculosSeguroITVReparacion = () => (dispatch) => {
    dispatch({type: CLEAR_VEHICULOSITVSEGUROREPARACION})
    
}

export const addVehiculo = (vehiculo) => (dispatch) =>{
    axios.post(`/vehiculo/add/`, vehiculo)
    .then((res) => {
        dispatch(loadVehiculosSeguroITVReparacion());
        dispatch({
            type: SET_ENVIADO,
        });
    })
    .catch((err) => {
        console.log(err)
    }) 
}

export const loadTiposVehiculos = () => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get('/vehiculo/allTiposVehiculos')
        .then((res) => {
            dispatch({type: SET_TIPOSVEHICULOS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const clearTiposVehiculos = () => (dispatch) => {
    dispatch({type: CLEAR_TIPOSVEHICULOS})
    
}

export const deleteVehiculo = (matricula) => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.delete(`/vehiculo/delete/${matricula}`)
        .then((res) => {
            dispatch(loadVehiculosSeguroITVReparacion())
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const loadBalance = (year) => (dispatch) => {
    
    dispatch({type: LOADING_UI})

    axios.get(`/balance/${year}`)
        .then((res) => {
            dispatch({type: SET_BALANCE, payload: res})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const setEstaPagadoFacturaE = (id,estado="TODO",orden="", pageNo,pageSize) => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.put(`/pedido/pagado/${id}`)
        .then((res) => {
            estado === "TODO" ? dispatch(loadPedidosPaginados(orden,pageNo,pageSize)) : dispatch(loadPedidosByEstadoPaginado(estado, orden,pageNo,pageSize))
            dispatch({
                type: SET_ENVIADO,
            });
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
        })
}

export const updatePedido = (estado="TODO",orden="", pedido, pageNo,pageSize) => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.put('/pedido/update/', pedido)
        .then((res) => {
            estado === "TODO" ? dispatch(loadPedidosPaginados(orden,pageNo,pageSize)) : dispatch(loadPedidosByEstadoPaginado(estado, orden,pageNo,pageSize))
            dispatch({
                type: SET_ENVIADO,
            });
        })

        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                })
            } else {
                dispatch({
                    type: SET_ERRORS,
                    payload: err
                })
            }
            estado === "TODO" ? dispatch(loadPedidosPaginados(orden,pageNo,pageSize)) : dispatch(loadPedidosByEstadoPaginado(estado, orden,pageNo,pageSize))
        })
}


export const loadFacturaEmitida = (numFactura) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/facturaEmitida/cargar/${numFactura}`)
        .then((res) => {
            dispatch({type: SET_FACTURAS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data
            })
        })
}

export const clearFacturaEmitida = () => (dispatch) => {
    dispatch({type: CLEAR})
}

export const rectificaFactura = (factura) => (dispatch) => {
    
    axios.post(`/facturaEmitida/rectificar`, factura)
    .catch((err) => {
        if(err.response){
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        } else {
            dispatch({
                type: SET_ERRORS,
                payload: err
            })
        }
    })

}
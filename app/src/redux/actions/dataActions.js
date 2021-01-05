import { CLEAR_ALMACENGESTION, SET_ALMACENGESTION, CLEAR_CLIENTE, SET_CLIENTE, CLEAR ,CLEAR_ISDEFAULTER, SET_PEDIDOS, SET_ERRORS, CLEAR_PEDIDOS, LOADING_UI, CLEAR_ERRORS, SET_PRODUCTOS, CLEAR_PRODUCTOS,SET_PERSONAL,CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_OCUPACION, CLEAR_OCUPACION, SET_ISDEFAULTER, SET_VEHICULOSITVSEGUROREPARACION, CLEAR_VEHICULOSITVSEGUROREPARACION } from '../types';
import axios from 'axios';

export const loadPedidos = () => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get('/pedido')
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const loadPedidosByEstado = (estado) => (dispatch) => {

    dispatch({type: LOADING_UI})

    axios.get(`/pedido/${estado}`)
        .then((res) => {
            dispatch({type: SET_PEDIDOS, payload: res});
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

export const cancelarPedido = (id) => (dispatch) => {

    axios.post(`/pedido/${id}`)
        .then((res) => {
            dispatch(loadPedidos());
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

export const loadPersonal = () => function (dispatch) {
    dispatch({type: LOADING_UI})
    
    axios.get('/contrato')
        .then((res) => {
            dispatch({type: SET_PERSONAL, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.get.message
            })
        })
}

export const deletePersonal = (nif) => function (dispatch){
    dispatch({type: LOADING_UI})
    axios.post(`/delete/${nif}`)
        .then((res) => {
            dispatch({type: SET_PERSONAL, payload: res})
            dispatch({type: CLEAR_ERRORS})
        })
        .catch((err) => {
            dispatch({
                type: SET_ERRORS,
                payload: err.response.data.get.message
            })
        })
}

export const clearPersonal = () => (dispatch) => {
    dispatch({type: CLEAR_PERSONAL})
}

export const addPersonal = (rolEmpleado,personal) => (dispatch) =>{
        axios.post(`/adm/add/${rolEmpleado.rol}`,personal)
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
        dispatch(loadPersonal());
    })
    .catch((err) => {
        console.log(err)
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
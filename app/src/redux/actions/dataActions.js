import { SET_PEDIDOS, SET_ERRORS, CLEAR_PEDIDOS, LOADING_UI, CLEAR_ERRORS, SET_PRODUCTOS, SET_CATEGORIAS, CLEAR_CATEGORIAS, CLEAR_PRODUCTOS,SET_PERSONAL,CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_TIPOSVEHICULOS, CLEAR_TIPOSVEHICULOS  } from '../types';
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
            console.log(err)
        })

}

export const loadProductos = () => (dispatch) => {
    dispatch({type: LOADING_UI})

    axios.get('/producto')
        .then((res) => {
            dispatch({type: SET_PRODUCTOS, payload: res.data.first})
            dispatch({type: SET_CATEGORIAS, payload: res.data.second})
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

export const clearCategorias = () => (dispatch) => {
    dispatch({type: CLEAR_CATEGORIAS})
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
                payload: err.response.data.get.message
            })
        })
}

export const clearPersonal = () => (dispatch) => {
    dispatch({type: CLEAR_PERSONAL})
}

export const loadVehiculos = () => function (dispatch) {
    dispatch({type: LOADING_UI})

    axios.get('/vehiculo')
        .then((res) => {
            dispatch({type: SET_VEHICULOS, payload: res.data.first})
            dispatch({type: SET_TIPOSVEHICULOS, payload: res.data.second})
            dispatch({type: CLEAR_ERRORS})

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
        })
}

export const clearVehiculos = () => (dispatch) => {
    dispatch({type: CLEAR_VEHICULOS})
}

export const clearTiposVehiculos = () => (dispatch) => {
    dispatch({type: CLEAR_TIPOSVEHICULOS})
}
import { SET_PEDIDOS, SET_ERRORS, CLEAR_PEDIDOS, LOADING_UI, CLEAR_ERRORS, SET_PRODUCTOS, SET_CATEGORIAS, CLEAR_CATEGORIAS, CLEAR_PRODUCTOS } from '../types';
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

export const clearPedidos = () => (dispatch) => {
    dispatch({type: CLEAR_PEDIDOS})
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
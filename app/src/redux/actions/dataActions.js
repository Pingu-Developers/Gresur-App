import { SET_PEDIDOS, SET_ERRORS, CLEAR_PEDIDOS, LOADING_UI, CLEAR_ERRORS } from '../types';
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
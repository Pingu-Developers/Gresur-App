import { SET_ERRORS, SET_USER, CLEAR_ERRORS, LOADING_UI, SET_UNAUTHENTICATED ,LOADING_USER, SET_NOTIFICACIONES_NO_LEIDAS , CLEAR_NOTIFICACIONES_NO_LEIDAS,SET_NOTIFICACIONES_LEIDAS } from '../types';
import axios from 'axios';

export const loginUser = (userData,history) => (dispatch) =>{
    dispatch({ type:LOADING_UI })

    axios.post('/auth/signin',userData)
        .then((res) => {
            setAuthorizationHeader(res.data.token);
            dispatch(getUserData());
            dispatch({ type: CLEAR_ERRORS });
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

export const logoutUser = () => (dispatch) =>{
    localStorage.removeItem('GresurIdToken');
    delete axios.defaults.headers.common['Authorization'];
    dispatch({ type:SET_UNAUTHENTICATED });
};


export const getUserData = () => (dispatch) => {
    dispatch({type: LOADING_USER});
    axios.get('/auth/user')
        .then(res => {
            dispatch({
                type : SET_USER,
                payload: res.data
            })
        })
        .catch(err => console.log(err))
};

const setAuthorizationHeader = (token) => {
    const GresurIdToken = `Bearer ${token}`;
    localStorage.setItem('GresurIdToken', GresurIdToken);
    axios.defaults.headers.common['Authorization'] = GresurIdToken;
};


export const getNotificacionesNoLeidas = () => (dispatch) => {

    axios.get('/notificacion')
        .then((res) => {
            dispatch(
                {
                    type: SET_NOTIFICACIONES_NO_LEIDAS,
                    payload: res.data
                }
            )
        })
        .catch(err => console.log(err))
}

export const getNotificacionesLeidas = () => (dispatch) => {

    axios.get('/notificacion/leidas')
        .then((res) => {
            dispatch(
                {
                    type: SET_NOTIFICACIONES_LEIDAS,
                    payload: res.data
                }
            )
        })
        .catch(err => console.log(err))
}

export const clearNotificacionesNoLeidas = () => (dispatch) => {

        dispatch({ type: CLEAR_NOTIFICACIONES_NO_LEIDAS });
}


export const setNotificacionLeida = (id) => (dispatch) => {

    axios.post(`/notificacion/setLeida/${id}`)
        .then((res) => {
            dispatch(getNotificacionesNoLeidas());
        })
        .catch((err) => {
            console.log(err);
            dispatch({
                type:SET_ERRORS,
                payload: err
            })
        })
}

export const postNotificacion = (nuevaNoti) => (dispatch) => {

    axios.post('/notificacion',nuevaNoti)
        .then((res) => {
            console.log("TODO OK BRO")
        })
        .catch((err) => {
            console.log(err.response);
            dispatch({
                type:SET_ERRORS,
                payload: err
            })
        })
}

export const postCliente = (nuevoCliente) => (dispatch) => {

    axios.post('/add/cliente', nuevoCliente)
        .then((res) => {
            console.log("TODO OK BRO")
        })
        .catch((err) => {
            console.log(err.response);
            dispatch({
                type:SET_ERRORS,
                payload: err
            })
        })
}

export const postPedido = (nuevoPedido) => (dispatch) => {

    axios.post('pedido/add', nuevoPedido)
        .then((res) => {
            console.log("TODO OK BRO")
        })
        .catch((err) => {
            console.log(err.response);
            dispatch({
                type:SET_ERRORS,
                payload: err
            })
        })
}
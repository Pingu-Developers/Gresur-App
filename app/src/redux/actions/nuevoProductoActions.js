import {LOADING_UI, SET_ERRORS, CLEAR_ERRORS } from '../types'
import axios from 'axios';

const CARGA_ESTANTERIAS = 'CARGA_ESTANTERIAS';
const CLEAR_ESTANTERIAS = 'CLEAR_ESTANTERIAS';
const NUEVO_PROD = 'NUEVO_PROD';


export const cargaEstanterias = () => (dispatch) =>{

    dispatch({ type:LOADING_UI })

    axios.get('estanterias')
        .then(res => {
            dispatch({
                type: CARGA_ESTANTERIAS,
                payload: res.data
            });
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
        });
    
}

export const clearEstanterias = () => (dispatch) =>{

    dispatch({ type:CLEAR_ESTANTERIAS })

}

export const nuevoProducto = () => (dispatch,product) =>{

    dispatch({ type:LOADING_UI })

    axios.defaults.headers.common['Content-Type'] = 'application/json';
    axios.post('producto/add',product)
        .then(res => {
            dispatch({
                type: NUEVO_PROD,
                payload: res.data
            });
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
        });
    
}
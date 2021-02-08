import { SET_PROVEEDORES , CLEAR_PROVEEDORES , LOADING_UI, SET_ERRORS, CLEAR_ERRORS,SET_ENVIADO } from '../types';
import axios from 'axios';


export const getProveedores = () => (dispatch) =>{

    dispatch({ type:LOADING_UI });

    axios.get('proveedor')
        .then((res)=>{
            dispatch({
                type:SET_PROVEEDORES,
                payload:res.data
            })
            dispatch({ type: CLEAR_ERRORS });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                });
            } else {
                console.log(err);
            }
        });
}

export const clearProveedores = () => (dispatch) =>{
    dispatch({ type:CLEAR_PROVEEDORES });
}

export const newProveedores = (data) => (dispatch) =>{
    axios.post('proveedor',data)
        .then(()=>{
            
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                });
            } else {
                console.log(err);
            }
        });
}

export const newFacturaRepo = (data) => (dispatch) =>{
    axios.post('facturaRecibida/repo',data)
        .then(()=>{
            dispatch({
                type: SET_ENVIADO,
            });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data.message
                });
            } else {
                console.log(err);
            }
        });
}


export const newFacturaVehiculo = (tipo,data) => (dispatch) =>{
    axios.post(`facturaRecibida/${tipo}`,data)
        .then(()=>{
            dispatch({
                type: SET_ENVIADO,
            });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data
                })
            } else {
                console.log(err)
            }
        });
}

export const newFacturaOtro = (data) => (dispatch) =>{
    axios.post(`facturaRecibida/otro`,data)
        .then(()=>{
            dispatch({
                type: SET_ENVIADO,
            });
        })
        .catch((err) => {
            if(err.response){
                dispatch({
                    type: SET_ERRORS,
                    payload: err.response.data
                })
            } else {
                console.log(err)
            }
        });
}
import { SET_PROVEEDORES , CLEAR_PROVEEDORES , LOADING_UI, SET_ERRORS, CLEAR_ERRORS } from '../types';
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
    axios.get('proveedor',data)
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
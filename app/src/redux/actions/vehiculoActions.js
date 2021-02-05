import axios from 'axios';
import { LOADING_UI, SET_ERRORS, CLEAR_ERRORS } from '../types'

const SET_VEHICULOS = "SET_VEHICULOS_2";
const CLEAR_VEHICULO = "CLEAR_VEHICULO";
const SET_SEGUROITV = "SET_SEGUROITV";
const CLEAR_SEGUROITV = "CLEAR_SEGUROITV";

export const getVehiculos = () => (dispatch) =>{

    dispatch({ type:LOADING_UI })

    axios.get('/vehiculo/allsimple')
        .then(res =>{
            dispatch({type: SET_VEHICULOS, payload: res.data})
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

export const clearVehiculo = () => (dispatch) =>{
    dispatch({ type:CLEAR_VEHICULO })
}

export const getVehiculosInfo = (value) => (dispatch) =>{

    dispatch({ type:LOADING_UI })

    axios.post('/vehiculo/info',value)
        .then(res =>{
            dispatch({type: SET_SEGUROITV, payload: res.data})
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

export const clearVehiculoInfo = () => (dispatch) =>{
    dispatch({ type:CLEAR_SEGUROITV })
}
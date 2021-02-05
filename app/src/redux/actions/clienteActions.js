import { SET_CLIENTES,CLEAR_CLIENTES,SET_FACTURAS,CLEAR_FACTURAS , SET_ERRORS,CLEAR_ERRORS ,SET_ENVIADO} from '../types';
import axios from 'axios';


export const getClientes = () => (dispatch) => {

    axios.get("/cliente")
        .then((res) => {
            dispatch({
                type:SET_CLIENTES,
                payload:res.data
            })
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

export const clearClientes = () => (dispatch) => {
    dispatch({
        type:CLEAR_CLIENTES
    })
}

export const getFacturasCliente = (id) => (dispatch) => {
    axios.get(`/facturaEmitida/${id}`)
        .then((res) => {
            dispatch({
                type:SET_FACTURAS,
                payload:res.data
            })
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

export const getFacturasClienteAndFecha = (datos) => (dispatch) => {
    axios.post('/facturaEmitida/clienteFecha',datos)
        .then((res) => {
            dispatch({
                type:SET_FACTURAS,
                payload:res.data
            })
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


export const sendDevolucion = (datos) => (dispatch) => {

    console.log(datos)
    axios.post('/facturaEmitida/devolucion',datos)
        .then(() => {
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


import { CLEAR_PRODUCTOS_PAGINADO , SET_PRODUCTOS_PAGINADO,SET_CATEGORIASALM,CLEAR_CATEGORIASALM, LOADING_UI, SET_ERRORS, CLEAR_ERRORS, CLEAR_PRODUCTOS } from '../types'
import axios from 'axios';

export const getProductosPaginados = (page,categoria = null,string =null,size = 5,ord='') => (dispatch) =>{

    dispatch({ type:LOADING_UI })

    if(categoria){
        axios.get(`producto/paged${ord}/${categoria}?page=${page-1}&size=${size}`)
            .then( response => {
                dispatch({
                    type:SET_PRODUCTOS_PAGINADO,
                    payload: response.data
                });
                dispatch({ type: CLEAR_ERRORS });
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

    }else if(string){
        axios.get(`producto/pagedName${ord}/${string}?page=${page-1}&size=${size}`)
            .then( response => {
                dispatch({
                    type:SET_PRODUCTOS_PAGINADO,
                    payload: response.data
                });
                dispatch({ type: CLEAR_ERRORS });
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
    }else{
        axios.get(`producto/paged${ord}?page=${page-1}&size=${size}`)
            .then( response => {
                dispatch({
                    type:SET_PRODUCTOS_PAGINADO,
                    payload: response.data
                });
                dispatch({ type: CLEAR_ERRORS });
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
};

export const getCategorias = (almacenAdm) => (dispatch) =>{

    axios.get(`almacen/categorias/${almacenAdm}`)
            .then( response => {
                dispatch({
                    type:SET_CATEGORIASALM,
                    payload: response.data
                });
                dispatch({ type: CLEAR_ERRORS });
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

export const putNotificacion = (almacenAdm, producto) => (dispatch) =>{

    axios.post(`producto/notiStock/${almacenAdm}`,producto)
            .then( () => {
                
                dispatch({ type: CLEAR_ERRORS });
            })
            .catch((err) => {
                if(err.response){
                    dispatch({
                        type: SET_ERRORS,
                        payload: err.response.data
                    })
                } else {
                    dispatch({
                        type: SET_ERRORS,
                        payload: err
                    })
                }
            });
}

export const clearCategorias = () => (dispatch) =>{
    dispatch({
        type: CLEAR_CATEGORIASALM
    })
}

export const clearProductosPaginados = () => (dispatch) =>{
    dispatch({
        type: CLEAR_PRODUCTOS_PAGINADO
    })
}
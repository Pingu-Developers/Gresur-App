import { CLEAR_PRODUCTOS_PAGINADO , SET_PRODUCTOS_PAGINADO, LOADING_UI, SET_ERRORS, CLEAR_ERRORS } from '../types'
import axios from 'axios';

export const getProductosPaginados = (page,categoria = null,string =null) => (dispatch) =>{

    dispatch({ type:LOADING_UI })

    if(categoria){
        axios.get(`producto/paged/${categoria}?page=${page-1}&size=5`)
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
                        payload: err.response.data.message
                    })
                } else {
                    dispatch({
                        type: SET_ERRORS,
                        payload: err
                    })
                }
            });

    }else if(string){
        axios.get(`producto/pagedName/${string}?page=${page-1}&size=5`)
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
                        payload: err.response.data.message
                    })
                } else {
                    dispatch({
                        type: SET_ERRORS,
                        payload: err
                    })
                }
            });
    }else{
        axios.get(`producto/paged?page=${page-1}&size=5`)
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
};

export const clearProductosPaginados = () => (dispatch) =>{
    dispatch({
        type: CLEAR_PRODUCTOS_PAGINADO
    })
}
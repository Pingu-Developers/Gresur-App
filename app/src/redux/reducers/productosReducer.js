import { CLEAR_PRODUCTOS_PAGINADO ,SET_CATEGORIASALM,CLEAR_CATEGORIASALM, SET_PRODUCTOS_PAGINADO } from '../types'

const initialState = {
    articlesDetails: [],
    totalPages: null,
    itemsCountPerPage:null,
    totalItemsCount:null,
    categorias: []
}

export default function (state = initialState,{ type,payload }){

    switch (type) {
        case SET_PRODUCTOS_PAGINADO:
            return {
                ...state,
                articlesDetails: payload.content,
                totalPages: payload.totalPages,
                itemsCountPerPage: payload.size,
                totalItemsCount: payload.totalElements
            };
    
        case CLEAR_PRODUCTOS_PAGINADO:
            return {
                ...initialState,
                categorias: state.categorias
            };
        case SET_CATEGORIASALM:
            return {
                ...state,
                categorias: payload
            };
    
        case CLEAR_CATEGORIASALM:
            return {
                ...state,
                categorias: []
            };
        
        default:
            return state;
    }
}
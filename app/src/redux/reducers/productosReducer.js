import { CLEAR_PRODUCTOS_PAGINADO , SET_PRODUCTOS_PAGINADO } from '../types'

const initialState = {
    articlesDetails: [],
    totalPages: null,
    itemsCountPerPage:null,
    totalItemsCount:null,
}

export default function (state = initialState,{ type,payload }){

    switch (type) {
        case SET_PRODUCTOS_PAGINADO:
            return {
                articlesDetails: payload.content,
                totalPages: payload.totalPages,
                itemsCountPerPage: payload.size,
                totalItemsCount: payload.totalElements
            };
    
        case CLEAR_PRODUCTOS_PAGINADO:
            return initialState;
            
        default:
            return state;
    }
}
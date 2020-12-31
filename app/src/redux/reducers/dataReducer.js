import { SET_PEDIDOS, CLEAR_PEDIDOS, CLEAR_PRODUCTOS, SET_PRODUCTOS, SET_CATEGORIAS, CLEAR_CATEGORIAS } from '../types';

const initialState = {
    pedidos: [],
    productos:[],
    categorias:[]
}

export default function(state = initialState,action){
    switch (action.type) {
        case SET_PEDIDOS:
            return {
                ...state,
                pedidos: action.payload.data
            };
        case CLEAR_PEDIDOS:
            return initialState;
        case SET_PRODUCTOS:
            return {
                ...state,
                productos: action.payload
            }
        case CLEAR_PRODUCTOS:
            return initialState;
        case SET_CATEGORIAS:
            return {
                ...state,
                categorias: action.payload
            }
        case CLEAR_CATEGORIAS:
                return initialState;
        default:
            return state;
    }
}
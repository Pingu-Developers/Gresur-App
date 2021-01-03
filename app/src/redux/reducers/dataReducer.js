import { SET_PEDIDOS, CLEAR_PEDIDOS, CLEAR_PRODUCTOS, SET_PRODUCTOS, SET_CATEGORIAS, CLEAR_CATEGORIAS,SET_PERSONAL,CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_OCUPACION, CLEAR_OCUPACION } from '../types';

const initialState = {
    pedidos: [],
    productos:[],
    categorias:[],
    vehiculos:[],
    personal:[],
    ocupaciones:[]
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
        case SET_PERSONAL:
            return {
                ...state,
                personal: action.payload.data
            };
        case CLEAR_PERSONAL:
            return initialState;  
        case SET_VEHICULOS:
            return {
                ...state,
                vehiculos: action.payload.data
            };
        case CLEAR_VEHICULOS:
            return initialState;

        case SET_OCUPACION:
            return {
                ...state,
                ocupaciones: action.payload.data
            };
        case CLEAR_OCUPACION:
            return initialState;
        
        default:
            return state;
    }
}
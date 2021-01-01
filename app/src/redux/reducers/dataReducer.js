import { SET_PEDIDOS, CLEAR_PEDIDOS, CLEAR_PRODUCTOS, SET_PRODUCTOS, SET_CATEGORIAS, CLEAR_CATEGORIAS,SET_PERSONAL,CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_TIPOSVEHICULOS, CLEAR_TIPOSVEHICULOS } from '../types';

const initialState = {
    pedidos: [],
    productos:[],
    categorias:[],
    vehiculos:[],
    tiposVehiculos:[]
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
                vehiculos: action.payload
            };
        case CLEAR_VEHICULOS:
            return initialState;

        case SET_TIPOSVEHICULOS:
            return {
                ...state,
                tiposVehiculos: action.payload
            };
        case CLEAR_TIPOSVEHICULOS:
            return initialState;
        
        default:
            return state;
    }
}
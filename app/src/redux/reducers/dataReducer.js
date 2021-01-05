import { CLEAR_ALMACENGESTION, SET_ALMACENGESTION, SET_CLIENTE, CLEAR_CLIENTE, CLEAR_ISDEFAULTER, SET_ISDEFAULTER, SET_PEDIDOS, CLEAR_PEDIDOS, CLEAR_PRODUCTOS, SET_PRODUCTOS, SET_PERSONAL, CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_OCUPACION, CLEAR_OCUPACION, CLEAR, SET_VEHICULOSITVSEGUROREPARACION, CLEAR_VEHICULOSITVSEGUROREPARACION } from '../types';

const initialState = {
    pedidos: [],
    productos: [],
    vehiculos: [],
    personal: [],
    ocupaciones: [],
    isDefaulter: false,
    cliente: null,
    vehiculosITVSeguroReparacion: []
}

export default function (state = initialState, action) {
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
                productos: action.payload.data
            }
        case CLEAR_PRODUCTOS:
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

        case SET_ISDEFAULTER:
            return {
                ...state,
                isDefaulter: action.payload.data
            }
        case CLEAR_ISDEFAULTER:
            return initialState;

        case SET_CLIENTE:
            return {
                ...state,
                cliente: action.payload.data
            }
        case CLEAR_CLIENTE:
            return initialState;

        case CLEAR:
            return initialState;
        case SET_ALMACENGESTION:
            return {
                ...state,
                gestionAlmacen: action.payload.data
            };
        case CLEAR_ALMACENGESTION:
            return initialState;
        case SET_VEHICULOSITVSEGUROREPARACION:
            return {
                ...state,
                vehiculosITVSeguroReparacion: action.payload.data
            };
        case CLEAR_VEHICULOSITVSEGUROREPARACION:
            return initialState;
        default:
            return state;
    }
}
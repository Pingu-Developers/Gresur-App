import { SET_ALMACENGESTIONENCARGADO, CLEAR_ALMACENGESTIONENCARGADO, SET_BALANCE, CLEAR_BALANCE, SET_PEDIDO, CLEAR_PEDIDO, CLEAR_ALMACENGESTION, SET_ALMACENGESTION, SET_CLIENTE, CLEAR_CLIENTE, CLEAR_ISDEFAULTER, SET_ISDEFAULTER, SET_PEDIDOS, CLEAR_PEDIDOS, CLEAR_PRODUCTOS, SET_PRODUCTOS, SET_PERSONAL, CLEAR_PERSONAL, SET_VEHICULOS, CLEAR_VEHICULOS, SET_OCUPACION, CLEAR_OCUPACION, CLEAR, SET_VEHICULOSITVSEGUROREPARACION, CLEAR_VEHICULOSITVSEGUROREPARACION,SET_ALMACEN,CLEAR_ALMACEN,SET_FACTURAS ,CLEAR_FACTURAS,SET_CONTRATO,CLEAR_CONTRATO, SET_TIPOSVEHICULOS, CLEAR_TIPOSVEHICULOS, } from '../types';

const initialState = {
    pedidos: [],
    pedido: null,
    productos: [],
    vehiculos: [],
    personal: [],
    ocupaciones: [],
    isDefaulter: false,
    cliente: null,
    gestionAlmacen: [],
    gestionAlmacenEncargado: [],
    vehiculosITVSeguroReparacion: [],
    almacen:[],
    facturas:[],
    contrato:[],
    tiposVehiculos:[],
    balance:[]
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
        case SET_PEDIDO:
            return {
                ...state,
                pedido: action.payload.data
            };
        case CLEAR_PEDIDO:
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
        case SET_ALMACENGESTIONENCARGADO:
            return {
                ...state,
                gestionAlmacenEncargado: action.payload.data
            }
        case CLEAR_ALMACENGESTIONENCARGADO:
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
        case SET_ALMACEN:
            return{
                ...state,
                almacen: action.payload.data
            }   
        case CLEAR_ALMACEN:
            return initialState;    
        case SET_FACTURAS:
            return{
                ...state,
                facturas: action.payload.data
            }
        case CLEAR_FACTURAS:
            return initialState; 
        case SET_CONTRATO:
            return{
                ...state,
                contrato: action.payload.data
            } 
        case CLEAR_CONTRATO:
            return initialState;
        case SET_TIPOSVEHICULOS:
            return{
                ...state,
                tiposVehiculos: action.payload.data
            } 
        case CLEAR_TIPOSVEHICULOS:
            return initialState;
        case SET_BALANCE:
            return{
                ...state,
                balance: action.payload.data
            }
        case CLEAR_BALANCE:
            return initialState;
    }
    
}
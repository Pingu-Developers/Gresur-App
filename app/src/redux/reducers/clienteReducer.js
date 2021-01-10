import { SET_CLIENTES,CLEAR_CLIENTES,SET_FACTURAS,CLEAR_FACTURAS, SET_ERRORS, CLEAR_ERRORS } from '../types';

const initialState = {
    clientes:[],
    facturas:[],
    errores:null,
}


export default function(state = initialState,action){

    switch (action.type) {

        case SET_CLIENTES:
            return {
                ...state,
                clientes:action.payload
            }

        case CLEAR_CLIENTES:
            return {
                ...state,
                clientes:[]
            }

        case SET_FACTURAS:
            return {
                ...state,
                facturas:action.payload
            }

        case CLEAR_FACTURAS:
            return {
                ...state,
                facturas:[]
            }
        case SET_ERRORS:
            return {
                ...state,
                errores:action.payload
            }
        
        case CLEAR_ERRORS:
            return {
                ...state,
                errores:null
            }
    
        default: 
            return state 

    }
}
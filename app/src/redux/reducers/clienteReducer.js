import { SET_CLIENTES,CLEAR_CLIENTES,SET_FACTURAS,CLEAR_FACTURAS } from '../types';

const initialState = {
    clientes:[],
    facturas:[],
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
    
        default: 
            return state 

    }
}
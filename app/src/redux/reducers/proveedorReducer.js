import { SET_PROVEEDORES , CLEAR_PROVEEDORES } from '../types'

const initialState = {
    proveedores: [],
}

export default function (state = initialState,{ type,payload }){

    switch (type) {
        case SET_PROVEEDORES:
            return {
                proveedores: payload
            };
    
        case CLEAR_PROVEEDORES:
            return initialState;
            
        default:
            return state;
    }
}
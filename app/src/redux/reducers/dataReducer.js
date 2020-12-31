import { SET_PEDIDOS, CLEAR_PEDIDOS } from '../types';

const initialState = {
    pedidos: []
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
        default:
            return state;
    }
}
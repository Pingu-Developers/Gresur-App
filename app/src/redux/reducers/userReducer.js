import { SET_USER, SET_AUTHENTICATED, SET_UNAUTHENTICATED, LOADING_USER, SET_NOTIFICACIONES_NO_LEIDAS , CLEAR_NOTIFICACIONES_NO_LEIDAS } from '../types';

const initialState = {
    loading:false,
    authenticated: false,
    notificaciones:[],
    credentials: {},
}

export default function(state = initialState,action){
    switch (action.type) {
        case SET_AUTHENTICATED:
            return {
                ...state,
                authenticated : true
            };
        case SET_UNAUTHENTICATED:
            return initialState;
        case SET_USER:
            return{
                authenticated: true,
                loading:false,
                ...action.payload
            };
        case LOADING_USER:
            return{
                ...state,
                loading: true
            };
        case SET_NOTIFICACIONES_NO_LEIDAS:
            return{
                ...state,
                notificaciones: action.payload
            }
        
        case CLEAR_NOTIFICACIONES_NO_LEIDAS:
            return{
                ...state,
                notificaciones:[]
            }

        default:
            return state;
    }
}
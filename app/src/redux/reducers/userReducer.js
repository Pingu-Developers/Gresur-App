import { SET_USER, SET_AUTHENTICATED, SET_UNAUTHENTICATED, LOADING_USER, GET_NUMBER_NOT } from '../types';

const initialState = {
    loading:false,
    authenticated: false,
    nNotification: null,
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
        case GET_NUMBER_NOT:
            return{
                ...state,
                nNotification: action.payload
            }
        default:
            return state;
    }
}
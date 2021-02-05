import { SET_ERRORS, CLEAR_ERRORS,CLEAR_ENVIADO, LOADING_UI,SET_ENVIADO} from '../types';

const initialState = {
    loading:false,
    errors:null,
    enviado:false
};

export default function(state = initialState,action){
    switch(action.type){
        case SET_ERRORS:
            return {
                ...state,
                loading:false,
                errors: action.payload,
                enviado:false
            };
        case CLEAR_ERRORS:
            return {
                ...state,
                loading:false,
                errors: null,
            };
        case LOADING_UI:
            return{
                ...state,
                loading:true
            }
        case SET_ENVIADO:
            return{
                ...state,
                enviado:true
            }
        case CLEAR_ENVIADO:
            return{
                ...state,
                enviado:false
            }
        default:
            return state;
    }
}
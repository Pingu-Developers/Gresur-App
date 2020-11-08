import { SET_ENTIDAD_DATA, SET_ERRORS } from '../types';

const initialState = {
    data:[],
    errors:[]
};

export default function(state = initialState,action){

    switch(action.type){
        case SET_ENTIDAD_DATA:
            return {
                ...state,
                data: action.payload
            };
        case SET_ERRORS:
            return{
                ...state,
                errors: action.payload
            };
        default:
            return state;

    }

}
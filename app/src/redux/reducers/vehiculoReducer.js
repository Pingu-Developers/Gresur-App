const SET_VEHICULOS = "SET_VEHICULOS_2";
const CLEAR_VEHICULO = "CLEAR_VEHICULO";
const SET_SEGUROITV = "SET_SEGUROITV";
const CLEAR_SEGUROITV = "CLEAR_SEGUROITV";


const initialState = {
    vehiculos:[],
    seguroitv:null,
};


export default function(state = initialState,{type, payload}){
    switch(type){
        case SET_VEHICULOS:
            return {
                ...state,
                vehiculos:payload
            };
        case CLEAR_VEHICULO:
            return {
                ...initialState
            };
        case SET_SEGUROITV:
            return{
                ...state,
                seguroitv:payload
            }
        case CLEAR_SEGUROITV:
            return{
                ...state,
                seguroitv:null
            }
        default:
            return state;
    }
}
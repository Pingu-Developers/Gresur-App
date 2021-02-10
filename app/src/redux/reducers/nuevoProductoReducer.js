const CARGA_ESTANTERIAS = 'CARGA_ESTANTERIAS';
const CLEAR_ESTANTERIAS = 'CLEAR_ESTANTERIAS';
const NUEVO_PROD = 'NUEVO_PROD';

const initialState = {
    estanterias: [],
    nuevoProd:null

}

export default function (state = initialState, {type , payload}) {
    switch (type) {
        case CARGA_ESTANTERIAS:
            
            return {
                ...state,
                estanterias:payload
            };
        
        case CLEAR_ESTANTERIAS:
        
            return {
                ...state,
                estanterias:[]
            };
        
        case NUEVO_PROD:
    
            return {
                ...state,
                nuevoProd:payload
            };

        default:
            return state;
    }
}
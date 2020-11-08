import { SET_ENTIDAD_DATA, SET_ERRORS } from '../types';
import axios from 'axios';


export const loadEntidadData = () => (dispatch) =>{
    axios.get("/entidad")
    .then(res => {
        dispatch({ type: SET_ENTIDAD_DATA, 
                   payload: res.data
        })
    })
    .catch(err => 
        dispatch({
            type: SET_ERRORS,
            payload: err.response.data
        }));
}
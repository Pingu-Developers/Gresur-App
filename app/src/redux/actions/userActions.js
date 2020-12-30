import { SET_ERRORS, SET_USER, CLEAR_ERRORS, LOADING_UI, SET_UNAUTHENTICATED ,LOADING_USER} from '../types';
import axios from 'axios';

export const loginUser = (userData,history) => (dispatch) =>{
    dispatch({ type:LOADING_UI })

    axios.post('/auth/signin',userData)
        .then((res) => {
            setAuthorizationHeader(res.data.token);
            dispatch(getUserData());
            dispatch({ type: CLEAR_ERRORS });
            //TODO PUSH TO USER PAGE

            switch (res.data.roles[0]) {
                case "ROLE_DEPENDIENTE":
                    history.push('/nuevoPedido');
                    break;
                 
                case "ROLE_TRANSPORTISTA":
                    history.push('/pedidos');
                    break;

                case "ROLE_ENCARGADO":
                    history.push('/catalogo');
                    break;

                case "ROLE_ADMIN":
                    history.push('/personal');
                    break;

                default:
                    history.push('/');
                    break;
            } 
        })
        .catch((err) => {
            dispatch({
                type:SET_ERRORS,
                payload: err.response.data.message
            })
        })
}

export const logoutUser = () => (dispatch) =>{
    localStorage.removeItem('GresurIdToken');
    delete axios.defaults.headers.common['Authorization'];
    dispatch({ type:SET_UNAUTHENTICATED });
};


export const getUserData = () => (dispatch) => {
    dispatch({type: LOADING_USER});
    axios.get('/auth/user')
        .then(res => {
            dispatch({
                type : SET_USER,
                payload: res.data
            })
        })
        .catch(err => console.log(err))
};

const setAuthorizationHeader = (token) => {
    const GresurIdToken = `Bearer ${token}`;
    localStorage.setItem('GresurIdToken', GresurIdToken);
    axios.defaults.headers.common['Authorization'] = GresurIdToken;
};
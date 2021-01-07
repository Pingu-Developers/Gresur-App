import { createStore,combineReducers,applyMiddleware,compose } from 'redux'
import thunk from 'redux-thunk'

import userReducer from './reducers/userReducer';
import uiReducer from './reducers/uiReducer';
import dataReducer from './reducers/dataReducer';
import clienteReducer from './reducers/clienteReducer';
import productosReducer from './reducers/productosReducer';


const initialState = {};

const middleware = [thunk];

const reducers = combineReducers({
    user:userReducer,
    UI:uiReducer,
    data: dataReducer,
    cliente:clienteReducer,
    productos: productosReducer
});

let store;
if (process.env.NODE_ENV === "development" && window.__REDUX_DEVTOOLS_EXTENSION__) {

    // Enable Redux Devtools
    store = createStore(reducers, initialState, compose(
        applyMiddleware(...middleware),
        window.__REDUX_DEVTOOLS_EXTENSION__())
    );
} else {
    store = createStore(reducers, initialState,applyMiddleware(...middleware));
}

export default store;
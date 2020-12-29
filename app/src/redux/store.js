import { createStore,combineReducers,applyMiddleware,compose } from 'redux'
import thunk from 'redux-thunk'

import userReducer from './reducers/userReducer';
import uiReducer from './reducers/uiReducer';



const initialState = {};

const middleware = [thunk];

const reducers = combineReducers({
    user:userReducer,
    UI:uiReducer
});

let store;
if (process.env.NODE_ENV === "development" && window.__REDUX_DEVTOOLS_EXTENSION__) {

    // Enable Redux Devtools
    store = createStore(reducers, initialState, compose(
        applyMiddleware(...middleware),
        window.__REDUX_DEVTOOLS_EXTENSION__())
    );
} else {
    store = createStore(reducers, initialState);
}

export default store;
import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import { composeWithDevTools } from "redux-devtools-extension";
import reducers from './reducers'
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import { LOGOUT_SUCCESS } from './actions/actionTypes'


const composeEnhancers = composeWithDevTools || compose;
const appReducer = combineReducers(reducers);

let middleware = [thunk];

if (process.env.NODE_ENV !== 'production') {
    middleware = [...middleware, logger];
} else {
    middleware = [...middleware];
}

const rootReducer = (state, action) => {
    if (action.type === LOGOUT_SUCCESS) {
        state = undefined;
    }

    return appReducer(state, action)
}

const store = createStore(rootReducer, composeEnhancers(applyMiddleware(...middleware)));

export default store;
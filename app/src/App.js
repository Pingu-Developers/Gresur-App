import React from 'react';
import {BrowserRouter as Router,Route,Switch,Redirect} from 'react-router-dom';
import './App.css';
import {ThemeProvider} from '@material-ui/core/styles'
import createMuiTheme from '@material-ui/core/styles/createMuiTheme'
import jwtDecode from 'jwt-decode'
import axios from 'axios';


//Redux
import {Provider} from 'react-redux';
import store from './redux/store';
import {SET_AUTHENTICATED} from './redux/types';
import { logoutUser, getUserData } from './redux/actions/userActions'

//Components
import AuthRoute from './util/AuthRoute';

//Pages
import main from './pages/main'
import login from './pages/login';

axios.defaults.baseURL = "http://localhost:8080/api";

const theme = createMuiTheme({
  palette: {
    primary: {
      light: '#f7bf67',
      main: '#F5B041',
      dark: '#ab7b2d',
      contrastText: '#000',
    },
    secondary: {
      light: '#008394',
      main: '#00bcd4',
      dark: '#33c9dc',
      contrastText: '#000',
    },
  },
})

const token = localStorage.GresurIdToken;

if(token){
  const decodedToken = jwtDecode(token);
  if(decodedToken.exp * 1000 < Date.now()){
    store.dispatch(logoutUser());
    window.location.href = '/login';
  } else {
    store.dispatch({ type:SET_AUTHENTICATED });
    axios.defaults.headers.common['Authorization'] = token;
    store.dispatch(getUserData());
  }
}else if(window.location.href !== 'http://localhost:3000/login'){
    window.location.href = '/login';
  }


function App() {

  return (
    <ThemeProvider theme = {theme}>
      <Provider store = {store}>
      <div>
        <Router>
              <Switch>
                <Route exact path = "/" component={main}/>
                <AuthRoute exact path="/login" component={login}/>
              </Switch>
        </Router>
      </div>
      </Provider>
    </ThemeProvider>
  );
}

export default App;

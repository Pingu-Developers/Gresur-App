import React, { Component } from 'react';
import {BrowserRouter as Router,Route,Switch} from 'react-router-dom';
import './App.css';
import {ThemeProvider} from '@material-ui/core/styles'
import createMuiTheme from '@material-ui/core/styles/createMuiTheme'
import axios from 'axios';

//Redux
import {Provider} from 'react-redux';
import store from './redux/store';

//Components
import Topbar from './components/Topbar';

//Pages
import Login from './pages/login';
import entidad from './pages/entidad';



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


function App() {
  return (
    <ThemeProvider theme = {theme}>
      <Provider store = {store}>
      <div>
        <Router>
        <Topbar/>
              <Switch>
                <Route exact path="/login" component={Login}/>
                <Route exact path="/entidad" component={entidad}/>
              </Switch>
        </Router>
      </div>
      </Provider>
    </ThemeProvider>
  );
}

export default App;

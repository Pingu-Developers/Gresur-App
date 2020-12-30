import React, { Component } from 'react';
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
import administradorPersonal from './pages/administradorPersonal'
import administradorFacturacion from './pages/administradorFacturacion'
import administradorStock from './pages/administradorStock'
import administradorTransporte from './pages/administradorTransporte'
import encargadoCatalogo from './pages/encargadoCatalogo'
import encargadoGestion from './pages/encargadoGestion'
import tranportistaPedidos from './pages/transportistaPedido'
import transportistaVehiculos from './pages/transportistaVehiculos'
import transportistaMapa from './pages/transportistaMapa'
import dependienteNuevoPedido from './pages/dependienteNuevoPedido'
import dependienteDevoluciones from './pages/dependienteDevoluciones'
import dependienteCatalogo from './pages/dependienteCatalogo'
import dependienteHistorialPedido from './pages/dependienteHistorialPedido'
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
                <Route exact path = "/personal" component={administradorPersonal}/>
                <Route exact path = "/facturas" component={administradorFacturacion}/> 
                <Route exact path = "/gestionStock" component={administradorStock}/> 
                <Route exact path = "/gestionVehiculo" component={administradorTransporte}/>  
                <Route exact path = "/catalogo" component={encargadoCatalogo}/> 
                <Route exact path = "/almacen" component={encargadoGestion}/> 
                <Route exact path = "/pedidos" component={tranportistaPedidos}/>
                <Route exact path = "/vehiculos" component={transportistaVehiculos}/> 
                <Route exact path = "/mapa" component={transportistaMapa}/>  
                <Route exact path = "/nuevoPedido" component={dependienteNuevoPedido}/>
                <Route exact path = "/devolucion" component={dependienteDevoluciones}/>
                <Route exact path = "/catalogoVenta" component={dependienteCatalogo}/>
                <Route exact path = "/historial" component={dependienteHistorialPedido}/>
                <AuthRoute exact path="/login" component={login}/>
              </Switch>
        </Router>
      </div>
      </Provider>
    </ThemeProvider>
  );
}

export default App;

import React, { Component } from 'react';
import { GoogleMap, LoadScript } from '@react-google-maps/api';
import Button from '@material-ui/core/Button';
import { Autocomplete } from '@react-google-maps/api';
import { DirectionsService  } from '@react-google-maps/api';
import { DirectionsRenderer } from '@react-google-maps/api';
import TextField from '@material-ui/core/TextField';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import CancelIcon from '@material-ui/icons/Cancel';
import Typography from '@material-ui/core/Typography';
import TripOriginIcon from '@material-ui/icons/TripOrigin';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import InputAdornment from '@material-ui/core/InputAdornment';
import RadioButtonCheckedIcon from '@material-ui/icons/RadioButtonChecked';
import ExploreIcon from '@material-ui/icons/Explore';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
}));

const initialState = {
  response: null,
      travelMode: 'DRIVING',
      origin: '',
      destination: '',
      waypoints: [],
      addParada:0,
      deletado:false,
      enviado:false,
} 
const containerStyle = {
  width: '1000px',
  height: '750px'
};

const center = {
  lat: 36.8551 ,
  lng: -5.32362
};

const libraries = ['places'];
var way = [];
var deleteWay = [];



class Mapa extends Component {

  constructor (props) {
    super(props)
    this.waypoints = []

    this.state = initialState;

    //Directions
    this.directionsCallback = this.directionsCallback.bind(this)
    this.getOrigin = this.getOrigin.bind(this)
    this.getDestination = this.getDestination.bind(this)
    this.getWaypoints = this.getWaypoints.bind(this)
    this.onClick = this.onClick.bind(this)
    this.onMapClick = this.onMapClick.bind(this)
    this.addParada = this.addParada.bind(this)
    this.deleteParada = this.deleteParada.bind(this)

    this.autocomplete = null
    this.onLoad = this.onLoad.bind(this)
    this.onPlaceChanged = this.onPlaceChanged.bind(this)
  }

  directionsCallback (response) {
    console.log(response)

    if (response !== null && this.state.enviado === true) {
      if (response.status === 'OK' ) {
        this.setState(
          
          () => ({
            response,
            enviado:false
          })
        )
      } else {
        console.log('response: ', response)
      }
    }
  }

  componentDidUpdate(prevProps,prevState){
    if( (prevState.deletado !== this.state.deletado) && this.state.deletado){
    way.splice(this.state.addParada,1)
    this.setState({
      deletado:false,
    })
  }

  }
 
  getOrigin (ref) {
    this.origin = ref
  }

  getDestination (ref) {
    this.destination = ref
  }
  getWaypoints (ref){
     this.waypoints= [...this.waypoints,ref]

  }

  onClick () {
      this.waypoints
      .filter( parada => (parada !== null && parada.value !== '') && parada.value !== undefined)
      .map((parada=>{
        way = [...way,{
          location: parada.value,
          stopover: true,
        }] 
      }))
    

    if (this.origin.value !== '' && this.destination.value !== '' && this.waypoints.value !== '') {
      this.setState(
        () => ({
          origin: this.origin.value,
          destination: this.destination.value,
          waypoints: way,
          enviado:true
        })
      )
    }
    if (this.origin.value !== '' && this.destination.value !== '' && this.waypoints.value === '') {
      this.setState(
        () => ({
          origin: this.origin.value,
          destination: this.destination.value,
          waypoints: null,
          enviado:true
        })
      )
    }
    if (this.origin.value === '' || this.destination.value === '' ) {
      this.setState(
        {
          response:null
        })
      
    }
    this.waypoints
    .filter( parada => (parada !== null && parada.value !== '') && parada.value !== undefined)
    .map((parada=>{
      deleteWay = [...deleteWay, parada.value] 
    }))


    this.waypoints = []
  }

  onMapClick (...args) {
  //  console.log('onClick args: ', args)
  }

  //
  onLoad (autocomplete) {
   // console.log('autocomplete: ', autocomplete)
    this.autocomplete = autocomplete
  }

  onPlaceChanged () {
    if (this.autocomplete !== null) {
   //   console.log(this.autocomplete.getPlace())
    } else {
      console.log('Autocomplete is not loaded yet!')
    }
  }
  addParada(){
    if(this.state.addParada<8){
      this.setState({
        addParada: this.state.addParada +1          
      })
    }
  }
  deleteParada(){
    if(this.state.addParada>0){
      this.setState({
        addParada: this.state.addParada -1,
        deletado:true
      })
    }

  }

  render() {
    console.log(this.state)
    return (
      <LoadScript
      googleMapsApiKey="AIzaSyDJnDnhlu5Xf8OAtfDqjbqcaMvH0Rxs_2Y"
      libraries = {libraries}
      >
        <Grid container spacing={5}>
        <Grid item xs={3} style={{display:'inline-block',justifyContent:'center', paddingTop:'3%', paddingLeft:'5%'}}>
          <Paper style={{border: 'solid', borderColor:'#f5b041', borderWidth:'3px'}}>
            <Typography style={{display:'flex', justifyContent:'center', fontWeight:'bold', margin:'3%',fontSize:'30px'}}>Calcular ruta óptima</Typography>
      <div className='map-settings' >
          <div className='row'>
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center', margin:'5%'}}>
                <label htmlFor='ORIGIN'></label>
                <br />
                  <Autocomplete
                    onLoad={this.onLoad}
                    onPlaceChanged={this.onPlaceChanged}
                  > 
                  
                    <TextField 
                         InputProps={{
                          startAdornment: (
                            <InputAdornment position="start">
                              <TripOriginIcon  style={{color:'#1a73e8'}}/> 
                            </InputAdornment>
                          )}}
                    label="Origen" id="destino" variant='outlined' placeholder="Introduzca su origen " size="small" inputRef={this.getOrigin} />
                  </Autocomplete>
              </div>
            </div>
            {/*************************************************************************************** WAYPOINTS *******************************************************************/}
            <div>
            {this.state.addParada>= 1?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 1" id="destino" variant='outlined' placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 2?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 2" variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 3?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 3" variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 4?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 4" variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 5?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 5" variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 6?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 6 " variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 7?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group'style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 7" variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            <div>
            {this.state.addParada>= 8?
            <div className='col-md-6 col-lg-4'>
              <div className='form-group'style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='WAYPOINTS'></label>
                <br />
                <Autocomplete style={{display:this.state.display}} onLoad={this.onLoad} onPlaceChanged={this.onPlaceChanged} > 
                  <TextField 
                   InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <RadioButtonCheckedIcon style={{color:'#1a73e8'}}/> 
                      </InputAdornment>
                    )}}
                  style={{display:this.state.display}} label="Parada 8" variant='outlined' id="destino" placeholder="Introduzca ubicacion" size="small" inputRef={this.getWaypoints} /> 
                </Autocomplete>
              </div>
            </div>:null}
            </div>
            {/*************************************************************************************** WAYPOINTS *********************************************************************/}
            <div className='col-md-6 col-lg-4'>
              <div className='form-group' style={{display:'flex', justifyContent:'center' , margin:'5%'}}>
                <label htmlFor='DESTINATION'></label>
                <br />
                <Autocomplete 
                    onLoad={this.onLoad}
                    onPlaceChanged={this.onPlaceChanged}
                  >
                 <TextField  
                 InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <LocationOnIcon style={{color:'#de1f12'}}/> 
                    </InputAdornment>
                  )}}
                 label="Destino" id="destino" placeholder="Introduzca su destino" variant='outlined' size="small" inputRef={this.getDestination} />
                  </Autocomplete>
              </div>
            </div>
          </div>   

            <div style={{display:'flex', justifyContent:'center', margin:'3%'}}>
          <Button size="mediun"  variant="contained" style={{margin:'5px'}} endIcon={<ExploreIcon />} onClick={this.onClick} color="primary" >
          Ruta
        </Button>
        </div>
        <div style={{display:'flex',justifyContent:'center'}}>
          <div style={{display:'inline-block',margin:'2%'}}>    
              <Button size="small" onClick={() => { this.deleteParada(); this.onClick();}}
                type ="submit" color="primary" variant="contained" endIcon={<CancelIcon />} disabled={this.state.addParada===0?true:false} >
                          Borrar Parada
              </Button>
            </div>
            <div style={{display:'inline-block',alignItems:'right',margin:'2%'}}>    
              <Button size="small" onClick={this.addParada}
                type ="submit" color="primary" variant="contained" endIcon={<AddCircleIcon />} disabled={this.state.addParada===8?true:false} >
                        Nueva Parada
              </Button>
            </div>
          </div>
        </div>
        </Paper>
        </Grid>
        <Grid item xs='480px' style={{ display:'inline',paddingTop:'3%',paddingLeft:'5%'}}>
          <Paper style={{border: 'solid', borderColor:'#f5b041', borderWidth:'3px'}} >
        <GoogleMap 
          mapContainerStyle = {{width:'50px'}}
          clickableIcons = {true}
          mapContainerStyle={containerStyle}
          center={center}
          zoom={15}
           // optional
           onClick={this.onMapClick}
           // optional
           onLoad={map => {
             console.log('DirectionsRenderer onLoad map: ', map)
           }}
           // optional
           onUnmount={map => {
             console.log('DirectionsRenderer onUnmount map: ', map)
           }}
        >  {
              (
                this.state.destination !== '' &&
                this.state.origin !== '') && (
                <DirectionsService
                  // required
                  options={{ 
                    destination: this.state.destination,
                    origin: this.state.origin,
                    waypoints: this.state.waypoints,
                    travelMode: this.state.travelMode,
                    //PONER A TRUE DESPUES DE LAS PRUEBAS
                    optimizeWaypoints: false

                  }}
                  // required
                    callback={this.directionsCallback}
                />
              )
            }

            {
              this.state.response !== null&&(
                <DirectionsRenderer
                  // required
                  options={{ 
                    directions: this.state.response
                  }}
                  // optional
                  onLoad={directionsRenderer => {
                    console.log('DirectionsRenderer onLoad directionsRenderer: ', directionsRenderer)
                  }}
                  // optional
                  onUnmount={directionsRenderer => {
                    console.log('DirectionsRenderer onUnmount directionsRenderer: ', directionsRenderer)
                  }}
                />
              )
            }
          <></>
          
        </GoogleMap>
        </Paper>
        </Grid>
        </Grid>
        </LoadScript>

      
    )
    
  }
}

export default Mapa;

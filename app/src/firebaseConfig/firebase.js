import firebase from 'firebase/app'
import 'firebase/storage'


  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyBMK2ASPr2S7nqiNjJjF-70WH8OQELFp7k",
    authDomain: "upload-images-gresur.firebaseapp.com",
    projectId: "upload-images-gresur",
    storageBucket: "upload-images-gresur.appspot.com",
    messagingSenderId: "344427744417",
    appId: "1:344427744417:web:cb73248d44f7d04cce74e0"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

  const storage = firebase.storage()


  export  {
    storage, firebase as default
  }
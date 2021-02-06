import React from 'react'
import Snackbar from './SnackBar';

export default function SnackCallController(props) {
    return (
        <div>
            <Snackbar id="successSnack" type = "success" open = {props.enviado} truco={true} message = {props.message}></Snackbar>
                {props.enviado? document.getElementById("successSnack").click():null}
                    
            <Snackbar id="errorSnack" type = "error" open = {props.errors} truco={true} message = {props.errors}></Snackbar>
                {props.errors? document.getElementById("errorSnack").click():null}

        </div>
    )
}

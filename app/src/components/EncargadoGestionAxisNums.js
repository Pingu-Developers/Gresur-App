import React from 'react'
import { makeStyles } from '@material-ui/core/styles';
import { Divider } from '@material-ui/core';

const useStyles = makeStyles({
    gridRow: {
        userSelect: 'none'
    },
    gridWrapper: {
        position: 'absolute',
        height: '100%',
        width: '100%',
        display: 'grid',
        alignContent: 'space-between'
    },
});

export default function EncargadoGestionAxisNums(props) {

    const classes = useStyles();
    return (
        <div className = {classes.gridWrapper}>
            <span className = {classes.gridRow}><span style = {{marginLeft: -50}}>100%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>97.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>95%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>92.5%</span><Divider style = {{marginTop: -13}}/></span>

            <span className = {classes.gridRow}><span style = {{marginLeft: -40, opacity: props.zoom > 1200 ? 1 : 0}}>90%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>87.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>85%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>82.5%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>80%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>77.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>75%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>72.5%</span><Divider style = {{marginTop: -13}}/></span>

            <span className = {classes.gridRow}><span style = {{marginLeft: -40, opacity: props.zoom > 1200 ? 1 : 0}}>70%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>67.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>65%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>62.5%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>60%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>57.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>55%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>52.5%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow}><span style = {{marginLeft: -40, opacity: props.zoom > 1200 ? 1 : 0}}>50%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>47.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>45%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>42.5%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>40%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>37.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>35%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>32.5%</span><Divider style = {{marginTop: -13}}/></span>

            <span className = {classes.gridRow}><span style = {{marginLeft: -40, opacity: props.zoom > 1200 ? 1 : 0}}>30%</span><Divider style = {{marginTop: -13}}/></span>

            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>27.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>25%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>22.5%</span><Divider style = {{marginTop: -13}}/></span>

            <span className = {classes.gridRow}><span style = {{marginLeft: -40}}>20%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>17.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>15%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>12.5%</span><Divider style = {{marginTop: -13}}/></span>

            <span className = {classes.gridRow}><span style = {{marginLeft: -40, opacity: props.zoom > 1200 ? 1 : 0}}>10%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>7.5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 2400 ? 1 : 0}}><span style = {{marginLeft: -40}}>5%</span><Divider style = {{marginTop: -13}}/></span>
            <span className = {classes.gridRow} style = {{opacity: props.zoom > 5000 ? 1 : 0}}><span style = {{marginLeft: -40}}>2.5%</span><Divider style = {{marginTop: -13}}/></span>
            
            <span className = {classes.gridRow}><span style = {{marginLeft: -30}}>0%</span><Divider style = {{marginTop: -13}}/></span>
        </div>
    )
}

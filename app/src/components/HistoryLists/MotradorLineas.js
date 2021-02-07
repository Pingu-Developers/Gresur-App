import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import withStyles from '@material-ui/core/styles/withStyles';
import CardLinea  from './CardLinea';
import Card from '@material-ui/core/Card';
import DialogoAddProductos from '../Dialogs/DialogoAddProductos';
import RowLinea  from './RowLinea';
import DeleteIcon from '@material-ui/icons/Delete';
import Fab from '@material-ui/core/Fab';

const style = theme => ({

    root:{
        width: "100%",
        display: "inline-grid",
        gridTemplateColumns:"1fr 1fr 1fr",
        justifyContent: "space-between",
    },

    card: {
        margin:10,
        padding:10,
        height:"100%",
      },
})

const StyledRow = withStyles((theme) => ({
    root: {
      '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
      },
    },
  }))(RowLinea);

export class MotradorLineas extends Component {
    constructor(props) {
        super(props);
    
    }


    render() {
        const{ classes ,datos,tipo }= this.props
        return (
            <div className={tipo?classes.rootMostrador1:classes.rootMostrador2}>
                {datos? 
                    datos.map(linea => 
                        (tipo?
                            <div style={{height:"260px"}}>
                                <CardLinea key={datos.indexOf(linea)} linea={linea} handleDelLinea={this.props.handleDelLinea} handleChange={this.props.handleChange}/>
                                <Fab  style={{display:"flex",position:"relative", top:"-111%",left: "91%" ,backgroundColor:"#FF8D8D",color:"white"}} color="inherit" onClick={()=>this.props.handleDelLinea(linea)} aria-label="add">
                                    <DeleteIcon fontSize="small"/>
                                </Fab>
                            </div>
                            :<StyledRow key={datos.indexOf(linea)} linea={linea} handleDelLinea={this.props.handleDelLinea} handleChange={this.props.handleChange}/>)
                        )
                    :null}
                {tipo?
                    <div style={{margin:10,padding:10}}>
                        <Card elevation={3} style={{padding:10,height:180,display: "flex",justifyContent: "center",alignItems: "center",backgroundColor:"#f8f8f8"}} >
                            <div><DialogoAddProductos handleAdd={this.props.handleAnadirLinea}/></div>
                        </Card>
                    </div>
                :
                    <div style={{display:"flex",justifyContent:"flex-end",position:"relative",top:"-50%",left:"2%"}}>
                        <div style={{display:"flex",position:"relative"}}>
                            <DialogoAddProductos handleAdd={this.props.handleAnadirLinea}/>
                        </div>
                    </div>
                }          
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    
})

const mapDispatchToProps = {
    
}

export default connect(mapStateToProps, mapDispatchToProps)(withStyles(style)(MotradorLineas))

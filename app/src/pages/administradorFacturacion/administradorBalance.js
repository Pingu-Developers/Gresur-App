import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import Chart from "react-google-charts";
import { loadBalance, clearLoading } from '../../redux/actions/dataActions';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import MenuItem from '@material-ui/core/MenuItem';
import { Divider } from '@material-ui/core';
import Typography from '@material-ui/core/Typography'


const style = {
    tituloForm: {
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        borderBottom: '1px solid #bdbdbd',
        margin: '0px 0px 20px 0px',
        padding: '0px 0px 15px 0px'
    },
    titulo: {
        margin: '30px 20px',
        fontSize: 40,
        fontWeight: 600,
        float: 'left',
        color: '#7a7a7a',
        margin: '0px 0px 0px 20px'
      },
}

class balance extends Component {
    constructor(props) {
        super(props);
        this.state = {
            year: new Date().getFullYear(),
            arr:[0,1,2,3,4],
            data: []
        }
    }

    handleChangeSelected = (event) => {
        var dfltyear = new Date().getFullYear();
        event.target.value === dfltyear?this.props.loadBalance(dfltyear):this.props.loadBalance(event.target.value);

        this.setState({
            [event.target.name]:event.target.value
        })
    }

    
    componentDidMount() {
        this.props.loadBalance(this.state.year);
    }

    componentWillUnmount(){
        this.props.clearLoading()
    }

    render() {
        
        const { classes, data } = this.props;
        const año = new Date().getFullYear()
        return (
            <div>
                <div className = {classes.tituloForm}>
                    <Typography className = {classes.titulo}>GRAFICO DE GASTOS / INGRESOS</Typography>
                    <div style = {{display: 'inline-flex', paddingBottom: 10, alignItems: 'center', marginRight: 40}}>
                        <Typography>Año: </Typography>
                        <FormControl style = {{marginLeft: 10}}>
                            <Select
                                variant = "outlined"
                                style = {{height: 35}}
                                labelId="demo-simple-select-outlined-label"
                                id="demo-simple-select-outlined"
                                name="year"
                                value={this.state.year ? this.state.year : new Date().getFullYear()}
                                onChange={this.handleChangeSelected}
                            >
                                {this.state.arr.map(i => <MenuItem value={año-this.state.arr.length+1+i}>{año-this.state.arr.length+1+i}</MenuItem>)}
                            </Select>
                        </FormControl>
                    </div>
                </div>
                {data.balance.length == 0 ?null:
                                    <Chart
                                    width={'100%'}
                                    height={'700px'}
                                    chartType="ComboChart"
                                    loader={<div>Loading Chart</div>}
                                    data={[
                                        ['Mes', 'Ingresos', 'Gastos', 'Beneficio'],
                                        ['ENERO', data.balance[0].ingresos, -data.balance[0].gastos, data.balance[0].ingresos - data.balance[0].gastos],
                                        ['FEBRERO', data.balance[1].ingresos, -data.balance[1].gastos, data.balance[1].ingresos - data.balance[1].gastos],
                                        ['MARZO', data.balance[2].ingresos, -data.balance[2].gastos, data.balance[2].ingresos - data.balance[2].gastos],
                                        ['ABRIL', data.balance[3].ingresos, -data.balance[3].gastos, data.balance[3].ingresos - data.balance[3].gastos],
                                        ['MAYO', data.balance[4].ingresos, -data.balance[4].gastos, data.balance[4].ingresos - data.balance[4].gastos],
                                        ['JUNIO', data.balance[5].ingresos, -data.balance[5].gastos, data.balance[5].ingresos - data.balance[5].gastos],
                                        ['JULIO', data.balance[6].ingresos, -data.balance[6].gastos, data.balance[6].ingresos - data.balance[6].gastos],
                                        ['AGOSTO', data.balance[7].ingresos, -data.balance[7].gastos, data.balance[7].ingresos - data.balance[7].gastos],
                                        ['SEPTIEMBRE', data.balance[8].ingresos, -data.balance[8].gastos, data.balance[8].ingresos - data.balance[8].gastos],
                                        ['OCTUBRE', data.balance[9].ingresos, -data.balance[9].gastos, data.balance[9].ingresos - data.balance[9].gastos],
                                        ['NOVIEMBRE', data.balance[10].ingresos, -data.balance[10].gastos, data.balance[10].ingresos - data.balance[10].gastos],
                                        ['DICIEMBRE', data.balance[11].ingresos, -data.balance[11].gastos, data.balance[11].ingresos - data.balance[11].gastos],
                                    ]}
                                    options={{
                                        // Material design options
                                        title: 'Balance financiero de Gresur',
                                        seriesType: 'bars',
                                        series: {2 : {type: 'line'}},

                                        chartArea: {
                                            height: '100%',
                                            width: '100%',
                                            top: 48,
                                            left: 100,
                                            right: '11%',
                                            bottom: 150
                                        },

                                        hAxis: {
                                            slantedText:true, 
                                        },
                                        vAxis: {
                                            title: "Ingresos / Gastos ( € )"
                                        },
                                    }}
                                    // For tests
                                    rootProps={{ 'data-testid': '1' }}
                                />
                
                
                }
                </div>

        )
    }
}

balance.propTypes = {
    classes: PropTypes.object.isRequired,
    data: PropTypes.object.isRequired,
    loadBalance: PropTypes.func.isRequired,
}

const mapStateToProps = (state) => ({
    data: state.data

})

const mapActionsToProps = {
    loadBalance,
    clearLoading
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(balance))
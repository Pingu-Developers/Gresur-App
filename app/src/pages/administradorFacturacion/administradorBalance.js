import React, { Component } from 'react';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { connect } from 'react-redux';
import Chart from "react-google-charts";
import { loadBalance } from '../../redux/actions/dataActions';
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';
import MenuItem from '@material-ui/core/MenuItem';


const style = {
}

class balance extends Component {
    constructor(props) {
        super(props);
        this.state = {
            year: 2020,
            data: []
        }
    }

    handleChangeSelected = (event) => {

        event.target.value ===2020?this.props.loadBalance(2020):this.props.loadBalance(event.target.value);

        this.setState({
            [event.target.name]:event.target.value
        })
    }

    
    componentDidMount() {
        this.props.loadBalance(2020);
    }

    render() {
        
        const { classes, data } = this.props;

        return (
            <div>
                <FormControl>
                    <Select
                        labelId="demo-simple-select-outlined-label"
                        id="demo-simple-select-outlined"
                        name="year"
                        value={this.state.year ? this.state.year : 2020}
                        onChange={this.handleChangeSelected}
                    >
                        <MenuItem value={2017}>2017</MenuItem>
                        <MenuItem value={2018}>2018</MenuItem>
                        <MenuItem value={2019}>2019</MenuItem>
                        <MenuItem value={2020}>2020</MenuItem>
                        <MenuItem value={2021}>2021</MenuItem>


                    </Select>
                </FormControl>
                {data.balance.length == 0 ?null:
                                    <Chart
                                    width={'100%'}
                                    height={'600px'}
                                    chartType="Bar"
                                    loader={<div>Loading Chart</div>}
                                    data={[
                                        ['Mes', 'Ingresos', 'Gastos'],
                                        [data.balance[0].mes, data.balance[0].ingresos, data.balance[0].gastos],
                                        [data.balance[1].mes, data.balance[1].ingresos, data.balance[1].gastos],
                                        [data.balance[2].mes, data.balance[2].ingresos, data.balance[2].gastos],
                                        [data.balance[3].mes, data.balance[3].ingresos, data.balance[3].gastos],
                                        [data.balance[4].mes, data.balance[4].ingresos, data.balance[4].gastos],
                                        [data.balance[5].mes, data.balance[5].ingresos, data.balance[5].gastos],
                                        [data.balance[6].mes, data.balance[6].ingresos, data.balance[6].gastos],
                                        [data.balance[7].mes, data.balance[7].ingresos, data.balance[7].gastos],
                                        [data.balance[8].mes, data.balance[8].ingresos, data.balance[8].gastos],
                                        [data.balance[9].mes, data.balance[9].ingresos, data.balance[9].gastos],
                                        [data.balance[10].mes, data.balance[10].ingresos, data.balance[10].gastos],
                                        [data.balance[11].mes, data.balance[11].ingresos, data.balance[11].gastos],
                                    ]}
                                    options={{
                                        // Material design options
                                        chart: {
                                            title: 'Balance financiero de Gresur',
                                            subtitle: 'Gráfico de Ingresos y gastos',
                                        },
                                    }}
                                    // For tests
                                    rootProps={{ 'data-testid': '2' }}
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
    loadBalance
}

export default connect(mapStateToProps, mapActionsToProps)(withStyles(style)(balance))
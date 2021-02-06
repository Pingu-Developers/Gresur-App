import React from 'react';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: '#d4e6f1',
    color: theme.palette.common.black,
    fontWeight: 'bold'
  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

const StyledTableRow = withStyles((theme) => ({
  root: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}))(TableRow);

const useStyles = makeStyles({
  table: {
    minWidth: 700,
  },

});

export default function CustomizedTables(props) {
  const classes = useStyles();
  const datos = props.data;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow className={classes.columnas}>
            <StyledTableCell align="center">Matricula</StyledTableCell>
            <StyledTableCell align="center">Tipo de Vehiculo</StyledTableCell>
            <StyledTableCell align="center">Disponibilidad</StyledTableCell>
            <StyledTableCell align="center">MMA</StyledTableCell>
            <StyledTableCell align="center">Capacidad(HxWxD)</StyledTableCell>
            <StyledTableCell align="center">ITV</StyledTableCell>
            <StyledTableCell align="center">Seguro</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {datos.map((row) => (
            <StyledTableRow key={row.vehiculo.matricula}>
              <StyledTableCell component="th" scope="row" align="center">
                {row.vehiculo.matricula}
              </StyledTableCell>
              <StyledTableCell align="center">{row.vehiculo.tipoVehiculo}</StyledTableCell>
              <StyledTableCell align="center">{row.disponibilidad}</StyledTableCell>
              <StyledTableCell align="center">{row.vehiculo.mma}</StyledTableCell>
              <StyledTableCell align="center">{row.vehiculo.capacidad}</StyledTableCell>
              <StyledTableCell align="center">{row.itv}</StyledTableCell>
              <StyledTableCell align="center">{row.seguro}</StyledTableCell>

            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
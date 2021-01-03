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
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
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
  const vehiculos = props.data.vehiculos;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Matricula</StyledTableCell>
            <StyledTableCell align="center">Capacidad(HxWxD)</StyledTableCell>
            <StyledTableCell align="center">Tipo de Vehiculo</StyledTableCell>
            <StyledTableCell align="center">Disponibilidad</StyledTableCell>
            <StyledTableCell align="center">Masa máxima autorizada (MMA)</StyledTableCell>
            <StyledTableCell align="center">ITV</StyledTableCell>
            <StyledTableCell align="center">Seguro</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {vehiculos.map((row) => (
            <StyledTableRow key={row.matricula}>
              <StyledTableCell component="th" scope="row">
                {row.matricula}
              </StyledTableCell>
              <StyledTableCell align="center">{row.capacidad}</StyledTableCell>
              <StyledTableCell align="center">{row.tipoVehiculo}</StyledTableCell>
              <StyledTableCell align="center">DISPONIBILIDAD</StyledTableCell>
              <StyledTableCell align="center">{row.mma}</StyledTableCell>
              <StyledTableCell align="center">ITV</StyledTableCell>
              <StyledTableCell align="center">SEGURO</StyledTableCell>

            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
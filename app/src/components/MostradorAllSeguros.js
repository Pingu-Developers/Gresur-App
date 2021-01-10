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
  const seguros = props.data;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow className={classes.columnas}>
            <StyledTableCell align="center">Compañia</StyledTableCell>
            <StyledTableCell align="center">Tipo de Seguro</StyledTableCell>
            <StyledTableCell align="center">Fecha de Contrato</StyledTableCell>
            <StyledTableCell align="center">Fecha de Expiracion</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {seguros.map((row) => (
            <StyledTableRow key={row.compania}>
              <StyledTableCell component="th" scope="row" align="center">
                {row.compania}
              </StyledTableCell>
              <StyledTableCell align="center">{row.tipoSeguro}</StyledTableCell>
              <StyledTableCell align="center">{row.fechaContrato}</StyledTableCell>
              <StyledTableCell align="center">{row.fechaExpiracion}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
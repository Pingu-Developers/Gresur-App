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
  const facturas = props.data;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow className={classes.columnas}>
            <StyledTableCell align="center">Numero de factura</StyledTableCell>
            <StyledTableCell align="center">Fecha de emision</StyledTableCell>
            <StyledTableCell align="center">Importe</StyledTableCell>
            <StyledTableCell align="center">Pagada(Si/No)</StyledTableCell>
            <StyledTableCell align="center">Descripcion</StyledTableCell>

          </TableRow>
        </TableHead>
        <TableBody>
            <StyledTableRow key={facturas.numFactura}>
              <StyledTableCell component="th" scope="row" align="center">
                {facturas.numFactura}
              </StyledTableCell>
              <StyledTableCell align="center">{facturas.fechaEmision}</StyledTableCell>
              <StyledTableCell align="center">{facturas.importe}</StyledTableCell>
              <StyledTableCell align="center">{facturas.estaPagada? 'SI': 'NO'}</StyledTableCell>
              <StyledTableCell align="center">{facturas.descripcion}</StyledTableCell>
            </StyledTableRow>
        </TableBody>
      </Table>
    </TableContainer>
  );
}
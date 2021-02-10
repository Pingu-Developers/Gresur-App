import React from 'react';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import PopUpFacturaReparacion from '../Dialogs/PopUpFacturaReparacion';

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
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow className={classes.columnas}>
            <StyledTableCell align="center">Fecha de entrada al taller</StyledTableCell>
            <StyledTableCell align="center">Fecha de salida del taller</StyledTableCell>
            <StyledTableCell align="center">Descripcion</StyledTableCell>
            <StyledTableCell align="center">Importe</StyledTableCell>
            <StyledTableCell align="center">Facturas</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {datos.map((row) => (
            <StyledTableRow key={row.fechaEntradaTaller}>
              <StyledTableCell component="th" scope="row" align="center">
                {row.fechaEntradaTaller}
              </StyledTableCell>
              <StyledTableCell align="center">{row.fechaSalidaTaller===null? 'Fecha de salida desconocida' : row.fechaSalidaTaller}</StyledTableCell>
              <StyledTableCell align="center">{row.descripcion===null? 'Reparacion sin descripcion' : row.descripcion}</StyledTableCell>
              <StyledTableCell align="center">{row.recibidas.importe}</StyledTableCell>
              <StyledTableCell align="center"> <PopUpFacturaReparacion data ={row.recibidas} /> </StyledTableCell>

            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
  );
}
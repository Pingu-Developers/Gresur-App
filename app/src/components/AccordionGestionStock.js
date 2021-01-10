import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Accordion from '@material-ui/core/Accordion';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  heading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight: theme.typography.fontWeightRegular,
  },
  acordeon: {
    backgroundColor: "#d4e6f1",

  }
}));

export default function SimpleAccordion(props) {
  const almacenes = props.almacenes;
  const classes = useStyles();

  return (
    <div className={classes.root}>
      {
        almacenes.map((gestAlmacen) =>
          <Accordion>
            <AccordionSummary
              className={classes.acordeon}
              expandIcon={<ExpandMoreIcon />}
              aria-controls="panel1a-content"
              id="panel1a-header"
            >
              <Typography className={classes.heading}>
                <b>Almacén {gestAlmacen.almacen.id}: {gestAlmacen.almacen.direccion}</b> Ocupación {gestAlmacen.ocupacionAlmacen.toFixed(2)} %</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography>
                <TableContainer>
                  <TableHead>
                    <TableRow>
                      <TableCell><b>Categoría</b></TableCell>
                      <TableCell><b>Ocupación por categoría</b></TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {gestAlmacen.categorias.map((cat) =>
                                      <TableRow>
                                      <TableCell>{cat.categoria}</TableCell>
                                      <TableCell>{cat.ocupacionEstanteria.toFixed(2)} %</TableCell>
                                    </TableRow>
                    )}

                  </TableBody>
                </TableContainer>
              </Typography>
            </AccordionDetails>
          </Accordion>
        )
      }
    </div>
  );
}

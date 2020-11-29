package org.springframework.gresur.util;

import java.time.LocalDate;

import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;

public class FechaInicioFinValidation {

	public static <T> void fechaInicioFinValidation(Class<T> c,LocalDate fechaInicio, LocalDate fechaFin) {
		
		if(c.equals(Reparacion.class) && fechaInicio.isAfter(fechaFin)) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de finalizacion debe ser posterior o igual a la fecha de inicio!");
		} else if((fechaInicio.isAfter(fechaFin) || fechaInicio.isEqual(fechaFin))) {
			throw new FechaFinNotAfterFechaInicioException("La fecha de finalizacion debe ser posterior a la fecha de inicio!");
		}
	}
}

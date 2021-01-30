package org.springframework.gresur.web;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.util.Tuple3;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RequestMapping("api/balance")
@RestController
public class BalanceController {
	
	private final FacturaEmitidaService facturaEmitidaService;
	
	private final FacturaRecibidaService facturaRecibidaService;

	@Autowired
	public BalanceController(FacturaEmitidaService facturaEmitidaService, FacturaRecibidaService facturaRecibidaService) {
		this.facturaEmitidaService = facturaEmitidaService;
		this.facturaRecibidaService = facturaRecibidaService;
	}
	
	@GetMapping("/{year}")
	public List<Tuple3<String, Double, Double>> getBalance(@PathVariable Integer year){
		List<Tuple3<String, Double, Double>> res = new ArrayList<Tuple3<String,Double,Double>>();
		//List<Double> res = new ArrayList<Double>();
		for(int i=1; i<=12; i++) {
			LocalDate primerDiaMes = LocalDate.of(year, i, 1).minusDays(1);
			LocalDate ultimoDiaMes = LocalDate.of(year, i, 1).plusMonths(1);
			
			String mes = Month.of(i).name() + " " + year.toString();
			Double ingresos = facturaEmitidaService.findByFechaEmisionBeforeAndFechaEmisionAfter(ultimoDiaMes, primerDiaMes).stream().mapToDouble(x -> x.getImporte()).sum();
			Double gastos = facturaRecibidaService.findByFechaEmisionBeforeAndFechaEmisionAfter(ultimoDiaMes, primerDiaMes).stream().mapToDouble(x -> x.getImporte()).sum();
			
			Tuple3<String, Double, Double> it = new Tuple3<String, Double, Double>();
				
			it.setE1(mes);
			it.setE2(ingresos);
			it.setE3(gastos);
			
			
			it.name1 = "mes";
			it.name2 = "ingresos";
			it.name3 = "gastos";
			
			
			res.add(it);
		}
		
		return res;
		
	}
	
	

	
	

}

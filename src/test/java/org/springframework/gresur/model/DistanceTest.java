package org.springframework.gresur.model;


import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.jupiter.api.Test;
import org.springframework.gresur.model.DistanceMatrix.DistanceMatrix;
import org.springframework.web.reactive.function.client.WebClient;

public class DistanceTest {
	
	

	@Test
	void DistanceGraphTest() {
		
		List<String> ciudades = new ArrayList<String>();
		ciudades.add("Sevilla");
		ciudades.add("Cadiz");
		ciudades.add("El Gastor");
		String uriParam = "";
		
		for (String string : ciudades) {
			uriParam += string;
			uriParam += "|";
		}
		uriParam += ciudades.get(0);
		DistanceMatrix test = WebClient.create("https://www.distancia.co")
				.get()
                .uri("/route.json?stops="+uriParam)
                .retrieve()
                .bodyToMono(DistanceMatrix.class)
                .block();
		
		System.out.println(test.getDistances());
		
		SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		graph.addVertex("Sevilla");
		graph.addVertex("Cadiz");
		graph.addVertex("El Gastor");
		

		DefaultWeightedEdge e1 = graph.addEdge("Sevilla", "Cadiz");
		graph.setEdgeWeight(e1, test.getDistances().get(1));
		DefaultWeightedEdge e2 = graph.addEdge("Cadiz", "El Gastor");
		graph.setEdgeWeight(e2, test.getDistances().get(2));
		DefaultWeightedEdge e3 = graph.addEdge("El Gastor", "Sevilla");
		graph.setEdgeWeight(e3, test.getDistances().get(3));
		
		//System.out.println(hamiltonianPath(graph));
	}
	
//	public List<String> hamiltonianPath(Graph<String, DefaultWeightedEdge> graph){
//		
//		TwoApproxMetricTSP<String, DefaultWeightedEdge> test2 = new TwoApproxMetricTSP<String, DefaultWeightedEdge>();
//		
//		
//		return test2.getTour(graph).getVertexList();
//	}

}

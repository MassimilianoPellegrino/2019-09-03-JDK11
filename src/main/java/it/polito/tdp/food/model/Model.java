package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	FoodDao dao = new FoodDao();
	List<String> bestSol;
	double bestW;
	
	public void creaGrafo(double c) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<String> vertici = dao.getVertici(c);
		Graphs.addAllVertices(grafo, vertici);
		List<Arco> archi = dao.getArchi();
		for(Arco a: archi)
			if(vertici.contains(a.getP1()) && vertici.contains(a.getP2()) && a.getW()>0)
				Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getW());
			
		//System.out.println(grafo.vertexSet().size());
		//System.out.println(grafo.edgeSet().size());

	}

	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public Set<DefaultWeightedEdge> getAdiacenti(String s){
		Set<DefaultWeightedEdge> result = new HashSet<>();
		
		for(String st: grafo.vertexSet())
			if(st.equals(s))
				result = new HashSet<>(grafo.incomingEdgesOf(st));
		
		return result;
	}
	
	public List<String> getCammino(String partenza, int n){
		
		bestSol = new LinkedList<>();
		bestW = 0;
		
		LinkedList<String> partSol = new LinkedList<>();
		double partW = 0;
		
		partSol.add(partenza);
		
		itera(partSol, partW, n);
		
		return bestSol;
		
	}

	private void itera(LinkedList<String> partSol, double partW, int n) {
		
		if(partSol.size() == n+1) {
			if(partW>bestW) {
				bestW = partW;
				bestSol = new LinkedList<>(partSol);
			}
			return;
		}
		
		for(String st: Graphs.neighborListOf(grafo, partSol.getLast())) {
			if(!partSol.contains(st)) {
				partW += grafo.getEdgeWeight(grafo.getEdge(partSol.getLast(), st));
				partSol.add(st);
				
				itera(partSol, partW, n);
				
				partSol.remove(st);
				partW -= grafo.getEdgeWeight(grafo.getEdge(partSol.getLast(), st));
			}
		}
				
		
	}

	public double getBestW() {
		return bestW;
	}
	
	
	
	
	
}

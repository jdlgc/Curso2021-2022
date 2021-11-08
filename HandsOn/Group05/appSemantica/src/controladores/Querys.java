package controladores;

import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class Querys {
	public static void main(String[] args) {
//		System.out.println("Prueba 1: ");
//		System.out.println(getTieneMunicipio());
//		System.out.println("Prueba 2: ");
//		System.out.println(getMunicipio_TieneEstacion("Puerto-de-Cotos"));
//		System.out.println("Prueba 3: ");
//		System.out.println(getEstacion_TienePuntoMuestreo_TieneMagnitud("1"));
//		System.out.println("Prueba 4: ");
//		System.out.println(getMunicipio_TieneUri("Puerto-de-Cotos"));
	}
	
	public ArrayList<String> getTieneMunicipio() {
		QueryExecution qe = QueryExecutionFactory.sparqlService(
				"http://localhost:3030/sparql", "SELECT DISTINCT ?municipio\r\n"
						+ "WHERE{\r\n"
						+ "    ?provincia <http://www.calidadAire.com#tieneMunicipio> ?municipio.\r\n"
						+ " }");
		ResultSet distrito = qe.execSelect();
		ArrayList<String> tieneMunicipio = toList(distrito,"municipio");
		
		qe.close();
		
		return tieneMunicipio;
	}
	
	public ArrayList<String> getMunicipio_TieneEstacion(String municipio) {
		municipio = nombreMunicipio(municipio);
		QueryExecution qe = QueryExecutionFactory.sparqlService(
				"http://localhost:3030/sparql", "SELECT DISTINCT ?estacion\r\n"
						+ "WHERE{\r\n"
						+ "    <http://www.calidadAire.com/refMunicipio/"+municipio+"> <http://www.calidadAire.com#tieneEstacion> ?estacion.\r\n"
						+ " }");
		ResultSet distrito = qe.execSelect();
		ArrayList<String> tieneEstacion = toList(distrito,"estacion");
		
		qe.close();
		
		return tieneEstacion;
	}
	
	public ArrayList<String> getEstacion_TienePuntoMuestreo_TieneMagnitud(String estacion) {
		Model model = ModelFactory.createDefaultModel();
		model.read("output-with-links.nt");
		
		Query query = QueryFactory.create("SELECT DISTINCT ?puntoMuestreo ?magnitud\r\n"
				+ "WHERE{\r\n"
				+ "    <http://www.calidadAire.com/refEstacion/"+estacion+"> <http://www.calidadAire.com#tienePuntoMuestreo> ?puntoMuestreo.\r\n"
				+ "    <http://www.calidadAire.com/refEstacion/"+estacion+"> <http://www.calidadAire.com#mide> ?magnitud.\r\n"
				+ " }");
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		
		/**
		QueryExecution qe = QueryExecutionFactory.sparqlService(
				"http://localhost:3030/sparql", "SELECT DISTINCT ?puntoMuestreo ?magnitud\r\n"
						+ "WHERE{\r\n"
						+ "    <http://www.calidadAire.com/"+estacion+"> <http://www.calidadAire.com#tienePuntoMuestreo> ?puntoMuestreo.\r\n"
						+ "    <http://www.calidadAire.com/"+estacion+"> <http://www.calidadAire.com#mide> ?magnitud.\r\n"
						+ " }");
						
		*/
		
		ResultSet distrito = qe.execSelect();
		ArrayList<String> tieneEstacion = toList(distrito,"puntoMuestreo", "magnitud");
		
		qe.close();
		 
		return tieneEstacion;
	}
	
	public ArrayList<String> getMunicipio_TieneUri(String municipio) {
		municipio = nombreMunicipio(municipio);
		
		Model model = ModelFactory.createDefaultModel();
		model.read("output-with-links.nt");
		
		Query query = QueryFactory.create("SELECT DISTINCT ?uri\r\n"
				+ "WHERE{\r\n"
				+ "    <http://www.calidadAire.com/refMunicipio/"+municipio+"> <http://www.calidadAire.com#tieneURIMuni> ?uri.\r\n"
				+ " }");
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		/**
		QueryExecution qe = QueryExecutionFactory.sparqlService(
				"http://localhost:3030/sparql", "SELECT DISTINCT ?uri\r\n"
						+ "WHERE{\r\n"
						+ "    <http://www.calidadAire.com/"+municipio+"> <http://www.calidadAire.com#tieneURIMuni> ?uri.\r\n"
						+ " }");
		*/
		ResultSet distrito = qe.execSelect();
		ArrayList<String> tieneEstacion = toList(distrito,"uri");
		
		
		qe.close();

		return tieneEstacion;
	}
	
	public static ArrayList<String> toList(ResultSet result, String column) {
		ArrayList<String> aux = new ArrayList<String>();
		
		do {
			aux.add(result.next().get(column).toString());
		}while(result.hasNext());
		
		return aux;
	}
	public static ArrayList<String> toList(ResultSet result, String column1, String column2) {
		ArrayList<String> aux = new ArrayList<String>();
		
		do {
			QuerySolution set = result.next();
			aux.add(set.get(column1).toString() + " " + set.get(column2).toString());
		}while(result.hasNext());
		
		return aux;
	}
	
	public static String nombreMunicipio(String municipio) {
		if(municipio.equals("Orusco de Taju�a")) {
			municipio="Orusco-de-Tajuña";
		}else if(municipio.equals("Puerto de Cotos")){
			municipio="Puerto-de-Cotos";
		}else if(municipio.equals("Rivas-Vaciamadrid")){
			municipio="Rivas-Vaciamadrid";
		}else if(municipio.equals("Aranjuez")){
			municipio="Aranjuez";
		}else if(municipio.equals("San Martin de Valdeiglesias")){
			municipio="San-Martín-de-Valdeiglesias";
		}else if(municipio.equals("Arganda del Rey")){
			municipio="Arganda-del-Rey";
		}else if(municipio.equals("Torrejon de Ardoz")){
			municipio="Torrejón-de-Ardoz";
		}else if(municipio.equals("El Atazar")){
			municipio="El-Atazar";
		}else if(municipio.equals("Valdemoro")){
			municipio="Valdemoro";
		}else if(municipio.equals("Villa del Prado")){
			municipio="Villa-del-Prado";
		}else if(municipio.equals("Villarejo de Salvanes")){
			municipio="Villarejo-de-Salvanés";
		}else if(municipio.equals("Colmenar Viejo")){
			municipio="Colmenar-Viejo";
		}else if(municipio.equals("Collado Villalba")){
			municipio="Collado-Villalba";
		}else if(municipio.equals("Coslada")){
			municipio="Coslada";
		}else if(municipio.equals("Alcala de Henares")){
			municipio="Alcalá-de-Henares";
		}else if(municipio.equals("Fuenlabrada")){
			municipio="Fuenlabrada";
		}else if(municipio.equals("Alcobendas")){
			municipio="Alcobendas";
		}else if(municipio.equals("Getafe")){
			municipio="Getafe";
		}else if(municipio.equals("Guadalix de la Sierra")){
			municipio="Guadalix-de-la-Sierra";
		}else if(municipio.equals("Alcorcon")){
			municipio="Alcorcón";
		}else if(municipio.equals("Leganes")){
			municipio="Leganés";
		}else if(municipio.equals("Majadahonda")){
			municipio="Majadahonda";
		}else if(municipio.equals("Algete")){
			municipio="Algete";
		}else if(municipio.equals("Mostoles")){
			municipio="Móstoles";
		}
		
		return municipio;
	}
	
}

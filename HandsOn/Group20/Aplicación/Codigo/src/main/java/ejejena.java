package main.java;

import java.util.ArrayList;

/*import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;*/

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;


//import jena.query;

public class ejejena {
	public ejejena() {
		
	}
	static String letra="";
	static String letra2="";

	//@SuppressWarnings("deprecation")
	@SuppressWarnings("deprecation")
	public static ArrayList<QuerySolution> metodo(){
		
		letra = interfaz.Ventana2.val;
		letra2 = interfaz.Ventana3.val;
		
		
		
		ArrayList<QuerySolution> lista = new ArrayList<QuerySolution>();
		
		FileManager.get().addLocatorClassLoader(ejejena.class.getClassLoader());
		
		Model model1 = ModelFactory.createDefaultModel();
		model1.read("output-with-links.ttl");
		
		//Model model2 = ModelFactory.createDefaultModel();
		//model2.read("output-with-links.ttl");
		
		/*QUERYS.SPARQL*/
		String queryString1 = //Numero de terrazas con más de 20 sillas.
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT (count(?Terraza) as ?o) \n" + 
				"WHERE { \n" +
				  "?Terraza local:sillas ?sillas \n" +
				  "FILTER(?sillas>=20). \n" +
				"}";
		
		String queryString2 = //5 locales que se encuentran en una avenida
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT (?Distrito as ?o) \n" + 
				"WHERE { \n" +
				  "?Distrito local:tipoVia ?via \n" +
				  "FILTER(?via=\"Avenida\"). \n" +
				"} LIMIT 5";
		
		String queryString3 = //Bares del barrio de salamanca
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT ?loc (?obj as ?o) ?ndir \n" +
						" WHERE { \n" +
				  "?loc local:rotulo ?obj . \n" +
				  "?loc local:perteneceADistrito ?dist . \n" +
				  "?dist local:distrito ?ndir \n" +
				  "filter(?ndir = \"Salamanca\"). \n" +
				"}";
		
		String queryString4 = //Los nombres de los bares y sus calle (4)
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT  ?loc ?obj ?ndir \n " +
						"WHERE { \n" +
					"?loc local:rotulo ?obj .\n" + 
				    "?loc local:perteneceADistrito ?dist . \n" +
				    "?dist local:nombreCalle ?ndir. \n" +
			    "} LIMIT 4 ";
		
		/*QUERYS-WITH-LINKS.SPARQL*/
		String queryString5 = //Links de barrios en wikidata
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT distinct ?bar ?wbar " +
						"WHERE { \n" +
				  "?dis local:barrio ?bar . \n" +
				  "?dis local:sameAsBarrio ?wbar \n" + 
				"} LIMIT 4";
		
		
		String queryString6 = //Las terrazas que tienen 10 sillas o menos .
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT ?bar ?wbar \n" + 
				"WHERE { \n" +
				  "?bar local:sillas ?sillas \n" + 
				  "FILTER(?sillas<=10). \n" + 
				  "?bar local:sameAsSillas ?wbar \n" + 
				"}";
		String queryString7 = //Los tipos de via y sus direcciones de wikidata
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
						"PREFIX local: <http://localizalocales.es/lcc/ontology/locales#> \n " +
						"PREFIX local2: <http://localizalocales.es/lcc/resource/> \n" +
		"SELECT distinct ?tvia ?wtvia \n" +
				" WHERE { \n" +
					"?dis local:tipoVia ?tvia . \n" +
					"?dis local:sameAsTipoVia ?wtvia \n" +
				"} \n";
		
		Query query = null;
		
		switch (letra) {
		case "A":
			query = QueryFactory.create(queryString1);
			break;
		case "B":
			query = QueryFactory.create(queryString2);
			break;
		case "C":
			query = QueryFactory.create(queryString3);
			break;
		case "D":
			query = QueryFactory.create(queryString4);
			break;
			case "": switch (letra2){
				case "E":
					query = QueryFactory.create(queryString5);
					break;
				case "F":
					query = QueryFactory.create(queryString6);
					break;
				case "G":
					query = QueryFactory.create(queryString7);
					break;
			}

		}

		
		QueryExecution qexec1 = QueryExecutionFactory.create(query, model1);
		
		try {
			org.apache.jena.query.ResultSet results = qexec1.execSelect();
			while(results.hasNext()) {
				QuerySolution soln = results.nextSolution();
			System.out.println(soln);
			lista.add(soln);
			}
		} finally {
			qexec1.close();
		}
		return lista;
			
	}
}

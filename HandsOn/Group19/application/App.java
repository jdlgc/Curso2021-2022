

import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;

import java.util.*;
import java.io.*;


public class App {

	public static void main(String[] args) {

		LogCtl.setCmdLogging();
		String teclado = "";
		Scanner sc = new Scanner(System.in);
		Model datosLocal = RDFDataMgr.loadModel("output-with-links.nt");
		Model wikidata = RDFDataMgr.loadModel("http://query.wikidata.org/sparql");
		
		System.out.println("Bienvenido a calidappDelAire");

		while(true) {
		
		System.out.println("--------------------------------------------------------");
		System.out.println("Seleccione de las siguientes opciones:");
		System.out.println("--------------------------------------------------------");
		System.out.println("1: Todas las mediciones de Madrid en una fecha concreta");
		System.out.println("--------------------------------------------------------");
		System.out.println("2: Todas las estaciones que midan un gas concreto");
		System.out.println("--------------------------------------------------------");
		System.out.println("3: Todas las fechas en las que los gases alcanzaron un valor espec�fico");
		System.out.println("--------------------------------------------------------");
		System.out.println("4: La imagen de la provincia");
		System.out.println("--------------------------------------------------------");
		System.out.println("salir: Cerrar aplicaci�n");


		teclado = sc.nextLine();
		String valor1;
		String valor2;
		
		switch(teclado) {
		case "1":
			System.out.println("Introduzca la fecha con formato 'a�o mes'");
			System.out.println("o 'menu' para volver al men� principal");
			System.out.println("Introduzca a�o (AAAA):");
			System.out.println("--------------------------------------------------------");
			valor1 = sc.nextLine();
			if(valor1.equals("menu")) {
				break;
		}
			System.out.println("Introduzca mes (MM)");
			System.out.println("--------------------------------------------------------");
			valor2 = sc.nextLine();
			if(valor2.equals("menu")) {
				break;
		}
			
			else {
			medMunFecha(valor1,valor2,datosLocal);
			}
			break;
		case "2":
			System.out.println("Introduzca uno de los siguientes gases:");
			System.out.println("--------------------------------------------------------");
			System.out.println("1. Mon�xido de Carbono (mg/m3)");
			System.out.println("2. Mon�xido de Nitrogeno (ug/m3)");
			System.out.println("3. Dioxido de Nitrogeno (ug/m3)");
			System.out.println("4. Oxido de Nitrogeno (ug/m3)");
			System.out.println("5. Dioxido de Azufre (ug/m3)");
			System.out.println("6. Particulas menores a 2.5um (ug/m3)");
			System.out.println("7. Particulas menores a 10um (ug/m3)");
			System.out.println("8. Ozono (ug/m3)");
			System.out.println("9. Tolueno (ug/m3)");
			System.out.println("10. Benceno (ug/m3)");
			System.out.println("11. Etilbenceno (ug/m3)");
			System.out.println("12. Hexano (mg/m3)");
			System.out.println("13. Metano (mg/m3)");
			System.out.println("14. Hidrocarburos no metanicos (mg/m3)");
			System.out.println("--------------------------------------------------------");
			System.out.println("o 'menu' para volver al men� principal");
			teclado = sc.nextLine();
			
			if(teclado.equals("menu")) {
				break;
			}
			else {
				//idEstMun(teclado,datosLocal);
				switch(teclado) {
				case "1": idEstMun("Monoxido de Carbono (mg/m3)",datosLocal);
				break;
				case "2": idEstMun("Monoxido de Nitrogeno (ug/m3)",datosLocal);
				break;
				case "3": idEstMun("Dioxido de Nitrogeno (ug/m3)",datosLocal);
				break;
				case "4": idEstMun("Oxido de Nitrogeno (ug/m3)",datosLocal);
				break;
				case "5": idEstMun("Dioxido de Azufre (ug/m3)",datosLocal);
				break;
				case "6": idEstMun("Particulas menores a 2.5um (ug/m3)",datosLocal);
				break;
				case "7": idEstMun("Particulas menores a 10um (ug/m3)",datosLocal);
				break;
				case "8": idEstMun("Ozono (ug/m3)",datosLocal);
				break;
				case "9": idEstMun("Tolueno (ug/m3)",datosLocal);
				break;
				case "10": idEstMun("Benceno (ug/m3)",datosLocal);
				break;
				case "11": idEstMun("Etilbenceno (ug/m3)",datosLocal);
				break;
				case "12": idEstMun("Hexano (mg/m3)",datosLocal);
				break;
				case "13": idEstMun("Metano (mg/m3",datosLocal);
				break;
				case "14": idEstMun("Hidrocarburos no metanicos (mg/m3)",datosLocal);
				break;
				default: break;
				}
			}
			break;
		case "3":
			System.out.println("Introduzca un valor concreto de gas formato (X.X)");
			System.out.println("o 'menu' para volver al men� principal");
			teclado = sc.nextLine();
			if(teclado.equals("menu")) {
				break;
			}
			else {
				medValFecha(teclado,datosLocal);
			}
			break;
			
		case "4":
			
			imagen(wikidata);
			break;

		case "5":
			System.out.print("skere ");
			break;

		case "salir":
			return;
			
		}}
			
		}

	public static void medMunFecha(String valor1,String valor2, Model datosLocal){
		
		String query1 =   "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" 
				        + "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"  
				        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				        + "SELECT ?magnitud ?valor \n"
				        + "WHERE\n"
				        + "{ ?magnitud <http://www.spainaq.linkeddata.com/aq/ontology/Magnitud#hasDate> \"" +  valor1 + "-" + valor2 + "-01T00:00:00Z\".\n"
				        + " ?magnitud <http://www.spainaq.linkeddata.com/aq/ontology/Magnitud#hasValor> ?valor.}"
						+ "LIMIT 25";

		
		Query consulta = QueryFactory.create(query1);
		QueryExecution ejecucion = QueryExecutionFactory.create(consulta,datosLocal);
		ResultSet resultados = ejecucion.execSelect();
		
		while(resultados.hasNext()) {
            QuerySolution solucion = resultados.nextSolution();
            RDFNode resultado = solucion.get("?magnitud");
            RDFNode resultado1 = solucion.get("?valor");
            System.out.print(resultado.toString() + " ");
            System.out.println(resultado1.toString());

		}		
	}
	
	
	public static void idEstMun(String gas,Model datosLocal){
		String query2 =  "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" 
		               + "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"  
		               + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" 
		               + "SELECT DISTINCT ?estacion\n" 
				       + "WHERE\n"
		               + "{ ?estacion <http://www.spainaq.linkeddata.com/aq/ontology/Estacion#hasMagnitud> \""+ gas + "\".}";
		
		Query consulta = QueryFactory.create(query2);
		QueryExecution ejecucion = QueryExecutionFactory.create(consulta,datosLocal);
		ResultSet resultados = ejecucion.execSelect();
		
		while(resultados.hasNext()) {
            QuerySolution solucion = resultados.nextSolution();
            RDFNode resultado = solucion.get("?estacion");
            System.out.println(resultado.toString());      
		}
	}

	
	
	public static void medValFecha(String valor, Model datosLocal){
		

		
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" 
	                 + "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"  
	                 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
	                 + "SELECT DISTINCT ?magnitud ?date\n"
				     + "WHERE\n"
	                 + "{ ?magnitud <http://www.spainaq.linkeddata.com/aq/ontology/Magnitud#hasValor> \"" + valor + "\".\n"
				     + "?magnitud <http://www.spainaq.linkeddata.com/aq/ontology/Magnitud#hasDate> ?date.}";
		
		Query consulta = QueryFactory.create(query);
		QueryExecution ejecucion = QueryExecutionFactory.create(consulta,datosLocal);
		ResultSet resultados = ejecucion.execSelect();
		
		while(resultados.hasNext()) {
            QuerySolution solucion = resultados.nextSolution();
            RDFNode resultado = solucion.get("?date");
            String result1 = resultado.toString();
            String result2 = result1.substring(0, 10);
            RDFNode resultado1 = solucion.get("?magnitud");
            String result = resultado1.toString();
            System.out.print(result + "; ");
            System.out.println(result2 + "; ");
		}
		
	}

	public static void imagen(Model wikidata){
		String wikidataURI = "http://query.wikidata.org/sparql";
		
		
		String query4 =   "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" 
				        + "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"  
				        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX wiki: <http://www.wikidata.org/prop/direct/>\n"
						+ "PREFIX en: <http://www.wikidata.org/entity/>\n"
				        + "SELECT ?image  \n"
				        + "WHERE\n"
				        + "{ en:Q5756 wiki:P154 ?image\n"
						+ "}\n";

		//System.out.print(query4);
		// Query consulta = QueryFactory.create(query4);
		QueryExecution ejecucion = QueryExecutionFactory.sparqlService(wikidataURI,query4);
		ResultSet resultados = ejecucion.execSelect();
		System.out.print("llego ");
		
		while(resultados.hasNext()) {
            QuerySolution solucion = resultados.nextSolution();
            RDFNode resultado = solucion.get("?image");
            System.out.print(resultado.toString() + " ");
		}		
	}

	}


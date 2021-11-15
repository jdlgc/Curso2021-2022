package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;

public class ReciclaMadrid {

	// Attributes
	static Model model;

	// Contstructor
	@SuppressWarnings("deprecation")
	public ReciclaMadrid() {
		// Read the data and save the graph
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open("src/resources/rdf-with-links.ttl");
		model.read(in, null, "TURTLE");
		this.model = model;
	}

	public static void main(String[] args) throws FileNotFoundException {
		String opcion = "";
		String distrito;
		// Read the data and save the graph

		// InputStream in = new FileInputStream(new
		// File("src/resources/rdf-with-links.ttl"));

		// Create an empty inmemory model and populate it from the graph
		Model model = ModelFactory.createDefaultModel();
		model.read("./src/resources/rdf-with-links.ttl");
		HashMap<String, RDFNode> distritos = getDistritos(model);
		Scanner teclado = new Scanner(System.in);
		boolean igual = false;
		System.out.println("############################################################################");
		System.out.println("##\t\t\t      _____\\    _______ \t\t\t  ##\r\n"
				+ "##\t\t\t     /      \\  |      /\\ \t\t\t  ##\r\n"
				+ "##\t\t\t    /_______/  |_____/  \\ \t\t\t  ##\r\n"
				+ "##\t\t\t   |   \\   /        /   / \t\t\t  ##\r\n"
				+ "##\t\t\t    \\   \\ RECICLA \\/   / \t\t\t  ##\r\n"
				+ "##\t\t\t     \\  /   MADRID \\__/_ \t\t\t  ##\r\n" + "##\t\t\t      \\/ ____    /\\ \t\t\t\t  ##\r\n"
				+ "##\t\t\t        /  \\    /  \\ \t\t\t\t  ##\r\n" + "##\t\t\t       /\\   \\  /   / \t\t\t\t  ##\r\n"
				+ "##\t\t\t         \\   \\/   / \t\t\t\t  ##\r\n" + "##\t\t\t          \\___\\__/ \t\t\t\t  ##");
		System.out.println("############################################################################\n");
		System.out.println("Por favor, elija el distrito donde se encuentra.\nEstos son los distritos disponibles:\n");
		do {
			// Intentamos que no se repitieran los distritos con un distinct, y cambiandolos en el rdf los nombres, pero no lo conseguimos. Hicimos esto como workaround
			int[] val = new int[] { 8, 11, 16, 18, 19, 27 };
			for (Integer i = 1; i <= distritos.size(); i++)
				if (!Arrays.stream(val).anyMatch(i::equals))
					System.out.println("Distrito: " + distritos.get(i.toString()));
			distrito = teclado.nextLine();
			for (Integer i = 1; i <= distritos.size(); i++)
				if (distrito.equals(distritos.get(i.toString()).toString()))
					igual = true;
			if (!igual)
				System.out.println("\nNo se encontró el distrito; elija un distrito de entre los disponibles:\n");
		} while (!igual);
		do {
			System.out.println("\nElija la opcion deseada:");
			System.out.println("1.Busqueda de contendores en el distrito");
			System.out.println("2.Busqueda de puntos limpios fijos en el distrito");
			System.out.println("3.Busqueda de puntos limpios moviles en el distrito");
			System.out.println("4.Cambiar el distrito");
			System.out.println("5.Salir de la aplicación");
			System.out.println("Teclee el numero de la opcion\n");
			opcion = teclado.nextLine();
			if (opcion.equals("5"))
				opcion = "salir";

			switch (opcion) {
			case "1":
				System.out.println("¿Quiere paginar sus resultados? \n (S/N)");
				String pag;
				pag = teclado.nextLine();
				boolean paginado = pag.equals("S") || pag.equals("s");
				if (paginado) {
					int offset;
					System.out.println("Página seleccionada: \n");
					offset = teclado.nextInt();
					System.out.println("Contenedores por página: \n");
					int count;
					count = teclado.nextInt();
					HashMap<String, ArrayList<String>> contenedores = getAllContainersDireccion(distrito, model,
							paginado, offset, count);
					for (HashMap.Entry<String, ArrayList<String>> entry : contenedores.entrySet()) {
						System.out.println("\nDirección: " + entry.getKey() + " / Residuos: "
								+ entry.getValue().toString() + "\n");
					}
				} else {
					HashMap<String, ArrayList<String>> contenedores = getAllContainersDireccion(distrito, model,
							paginado, 0, 0);
					for (HashMap.Entry<String, ArrayList<String>> entry : contenedores.entrySet()) {
						System.out.println("\nDirección: " + entry.getKey() + " / Residuos: "
								+ entry.getValue().toString() + "\n");
					}
				}

				break;
			case "2":
				HashMap<String, RDFNode> plimpiosFijos = getAllPLFijosDireccion(distrito, model);
				if (plimpiosFijos.size() == 0)
					System.out.println(
							"\nLo sentimos, no hay puntos limpios fijos en el distrito \"" + distrito + "\"\n");
				else {
					for (HashMap.Entry<String, RDFNode> entry : plimpiosFijos.entrySet()) {
						System.out.println("\nDirección: " + entry.getKey());
						System.out.print("Horario: ");
						String e = "De lunes a sábados de 8 a 20 horas. Domingos y festivos de 9 a 14 horas. Los días 24 y 31 de diciembre de 9 a 14 horas. Cerrado días 25 de diciembre y 1 y 6 de enero.";
						e = e.replace(".", ",");
						String[] e2 = e.split(",");
						;
						for (String elem : e2) {
							System.out.println(elem + ".");
						}
						System.out.println();
					}
				}
				break;
			case "3":
				HashMap<String, RDFNode> plimpiosMoviles = getAllPLMovilesDireccion(distrito, model);
				if (plimpiosMoviles.size() == 0)
					System.out.println("\nLo sentimos, no hay puntos limpios fijos en el distrito " + distrito + "\n");
				else {
					for (HashMap.Entry<String, RDFNode> entry : plimpiosMoviles.entrySet()) {
						System.out.println("\nDirección: " + entry.getKey());
						System.out.print("Horario: ");
						String e = "De lunes a sábados de 8 a 20 horas. Domingos y festivos de 9 a 14 horas. Los días 24 y 31 de diciembre de 9 a 14 horas. Cerrado días 25 de diciembre y 1 y 6 de enero.";
						e = e.replace(".", ",");
						String[] e2 = e.split(",");
						;
						for (String elem : e2) {
							System.out.println(elem + ".");
						}
						System.out.println();
					}
				}
				break;
			case "4":
				igual = false;
				System.out.println(
						"Por favor, elija el distrito donde se encuentra. Estos son los distritos disponibles");
				do {
					int[] val = new int[] { 8, 11, 16, 18, 19, 27 };
					for (Integer i = 1; i <= distritos.size(); i++)
						if (!Arrays.stream(val).anyMatch(i::equals))
							System.out.println("Distrito: " + distritos.get(i.toString()));
					distrito = teclado.nextLine();
					for (Integer i = 1; i <= distritos.size(); i++)
						if (distrito.equals(distritos.get(i.toString()).toString()))
							igual = true;
					if (!igual)
						System.out.println("Elija un distrito de entre los disponibles.");
				} while (!igual);
				break;
			default:
				break;
			}
		} while (!opcion.equals("salir"));
		teclado.close();
	}

	// FUnciuones auziliares

	public static String[] getNames(HashMap<String, String> map) {
		String[] names = new String[map.size()];
		Iterator<String> results = map.keySet().iterator();
		String aux;
		int i = 0;
		while (results.hasNext()) {
			names[i] = results.next();
			i++;
		}
		return names;
	}

	private static HashMap<String, RDFNode> consultDistritos(String queryString, Model model, String busca) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		HashMap<String, RDFNode> mapResult = new HashMap<String, RDFNode>();
		Integer contador = 1;
		QuerySolution result;
		try {
			ResultSet results = qexec.execSelect();
			if (results.hasNext())
				while (results.hasNext()) {
					result = results.next();
					String temp = result.get(busca).toString();
					mapResult.put(contador.toString(), result.get(busca));
					contador++;
				}
		} finally {
			qexec.close();
		}
		return mapResult;
	}

	private static HashMap<String, ArrayList<String>> consultContenedores(String queryString, Model model, String dir,
			String residuo) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		HashMap<String, ArrayList<String>> mapResult = new HashMap<String, ArrayList<String>>();
		Integer contador = 1;
		QuerySolution result;
		ArrayList<String> lista;
		try {
			ResultSet results = qexec.execSelect();
			String actual = null;
			if (results.hasNext())
				while (results.hasNext()) {
					result = results.next();
					if (actual != result.get(dir).toString() || actual == null)
						actual = result.get(dir).toString();
					if (mapResult.containsKey(actual)) {
						mapResult.get(actual).add(result.get(residuo).toString());
					}

					else {
						mapResult.put(actual, lista = new ArrayList<String>());
						mapResult.get(actual).add(result.get(residuo).toString());
					}
					contador++;
				}
		} finally {
			qexec.close();
		}
		return mapResult;
	}

	private static HashMap<String, RDFNode> consultPLFijo(String queryString, Model model, String dir, String horario) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		HashMap<String, RDFNode> mapResult = new HashMap<String, RDFNode>();
		Integer contador = 1;
		QuerySolution result;
		try {
			ResultSet results = qexec.execSelect();
			if (results.hasNext())
				while (results.hasNext()) {
					result = results.next();
					String temp = result.get(dir).toString();
					mapResult.put(result.get(dir).toString(), result.get(horario));
					contador++;
				}
		} finally {
			qexec.close();
		}
		return mapResult;
	}

	private static HashMap<String, RDFNode> getAllPLMovilesDireccion(String distrito, Model model) {
		String queryString = "SELECT DISTINCT  ?residuo ?calle   WHERE {"
				+ "?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#PuntoLimpioMovil>."
				+ "?s <http://www.reciclajemadrid.com/resources/distrito> \"" + distrito + "\" ."
				+ "?s <http://www.reciclajemadrid.com/resources/direccion> ?calle ."
				+ "?sup <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#PuntoLimpioMovil>."
				+ "?sup <http://www.reciclajemadrid.com/resources/direccion> ?calle."
				+ "?sup <http://www.reciclajemadrid.com/resources/horario> ?horario ." + "}";

		HashMap<String, RDFNode> plmovil = consultPLFijo(queryString, model, "calle", "horario");
		return plmovil;
	}

	private static HashMap<String, RDFNode> getAllPLFijosDireccion(String distrito, Model model) {
		String queryString = "SELECT DISTINCT  ?calle ?horario   WHERE {"
				+ "?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#PuntoLimpioFijo>."
				+ "?s <http://www.reciclajemadrid.com/resources/distrito> \"" + distrito + "\" ."
				+ "?s <http://www.reciclajemadrid.com/resources/direccion> ?calle ."
				+ "?sup <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#PuntoLimpioFijo>."
				+ "?sup <http://www.reciclajemadrid.com/resources/direccion> ?calle."
				+ "?sup <http://www.reciclajemadrid.com/resources/horario> ?horario ." + "}";

		HashMap<String, RDFNode> plfijos = consultPLFijo(queryString, model, "calle", "horario");
		return plfijos;
	}

	private static HashMap<String, ArrayList<String>> getAllContainersDireccion(String distrito, Model model,
			boolean paginado, int offset, int count) {
		String queryString = "";
		if (paginado) {
			queryString = "SELECT DISTINCT  ?residuo ?calle   WHERE {"
					+ "?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#Contenedor>."
					+ "?s <http://www.reciclajemadrid.com/resources/distrito> \"" + distrito + "\" ."
					+ "?s <http://www.reciclajemadrid.com/resources/direccion> ?calle ."
					+ "?sup <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#Contenedor>."
					+ "?sup <http://www.reciclajemadrid.com/resources/direccion> ?calle."
					+ "?sup <http://www.reciclajemadrid.com/resources/residuo> ?residuo ." + "} ORDER BY ?calle"
					+ " LIMIT " + count + " OFFSET " + offset;
		} else {
			queryString = "SELECT DISTINCT  ?residuo ?calle   WHERE {"
					+ "?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#Contenedor>."
					+ "?s <http://www.reciclajemadrid.com/resources/distrito> \"" + distrito + "\" ."
					+ "?s <http://www.reciclajemadrid.com/resources/direccion> ?calle ."
					+ "?sup <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#Contenedor>."
					+ "?sup <http://www.reciclajemadrid.com/resources/direccion> ?calle."
					+ "?sup <http://www.reciclajemadrid.com/resources/residuo> ?residuo ." + "} ORDER BY ?calle";
		}

		HashMap<String, ArrayList<String>> contenedores = consultContenedores(queryString, model, "calle", "residuo");
		return contenedores;
	}
	
	// En esta funcion intentamos fitlrar por calle los contenedores que elegimos, pero no conseguimos que funcionara; quedo pendiente de hacer en caso de que se
	// quisiera ampliar la aplicacion

//	private static HashMap<String, ArrayList<String>> getNearlyContainers(String distrito, Model model, String calle) {
//		String queryString = "SELECT DISTINCT  ?residuo ?calle   WHERE {"
//				+ "?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#Contenedor>."
//				+ "?s <http://www.reciclajemadrid.com/resources/distrito> \"" + distrito + "\" ."
//				+ "?s <http://www.reciclajemadrid.com/resources/direccion> ?calle ."
//				+ "?sup <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.reciclajemadrid.com#Contenedor>."
//				+ "?sup <http://www.reciclajemadrid.com/resources/direccion> ?calle."
//				+ "?sup <http://www.reciclajemadrid.com/resources/nombreVia> \"" + calle + "\" ."
//				+ "?sup <http://www.reciclajemadrid.com/resources/residuo> ?residuo ." + "}";
//
//		HashMap<String, ArrayList<String>> contenedores = consultContenedores(queryString, model, "calle", "residuo");
//		return contenedores;
//	}

	private static HashMap<String, RDFNode> getDistritos(Model model) {
		String queryString = "SELECT DISTINCT ?distrito  WHERE {"
				+ "?obj <http://www.reciclajemadrid.com/resources/distrito> ?distrito" + "} ORDER BY ?distrito";

		HashMap<String, RDFNode> distritos = consultDistritos(queryString, model, "distrito");

		return distritos;

	}
}

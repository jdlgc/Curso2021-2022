package main;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

public class App {

	static String sparqlCalles(String distrito, String barrio) {
		String resultado = "\n";
		Model model;

		model = ModelFactory.createDefaultModel();
		model.read("output.nt");

		String querystr = "SELECT ?direccion WHERE { "
				+ "?s <http://parquimetrosdemadrid.com/ontology/parquimetros#hasLocatedDistrict> <http://parquimetrosdemadrid.com/District#"
				+ distrito + ">. "
				+ "?s <http://parquimetrosdemadrid.com/ontology/parquimetros#hasLocatedNeighborhood> <http://parquimetrosdemadrid.com/Neighborhood#"
				+ barrio + ">. "
				+ "?s <http://parquimetrosdemadrid.com/ontology/parquimetros#hasLocatedVia> ?direccion. }";

		Query query = QueryFactory.create(querystr);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				String limpiar = soln.toString().substring(52, soln.toString().length() - 3);
				String a = limpiar.replace("-", " ");
				resultado = resultado + "·  " + a + "\n";

			}
		} finally {
			qexec.close();
		}
		if (resultado.equals("\n"))
			resultado = "\n" + "No hay parquimetros en la base de datos" + " para los campos introducidos";

		///////////////////////////////////////////////////////////////////////////////////////////////
		String frontera = "\n";
		SPARQLRepository sparqlRepository = new SPARQLRepository("https://query.wikidata.org/sparql");
		RepositoryConnection sparqlConnection = sparqlRepository.getConnection();
		String codigo = "";
		switch (distrito) {
		case "01-CENTRO":
			codigo = "Q1763376";
			break;
		case "02-ARGANZUELA":
			codigo = "Q2000929";
			break;
		case "03-RETIRO":
			codigo = "Q2002296";
			break;
		case "04-SALAMANCA":
			codigo = "Q1773521";
			break;
		case "05-CHAMARTIN":
			codigo = "Q1766348";
			break;
		case "06-TETUAN":
			codigo = "Q1773540";
			break;
		case "07-CHAMBERI":
			codigo = "Q1763370";
			break;
		case "08-FUENCARRAL-EL-PARDO":
			codigo = "Q656196";
			break;
		case "09-MONCLOA-ARAVACA":
			codigo = "Q2017682";
			break;
		default:

		}

		String query1 = "SELECT ?distrito ?area ?nombre WHERE {\n" + "  ?distrito wdt:P31 wd:Q3032114;\n"
				+ "    wdt:P47 wd:" + codigo + ";\n" + "    wdt:P2046 ?area; \n" + "    wdt:P373 ?nombre. }\n" + "";

		TupleQuery tupleQuery = sparqlConnection.prepareTupleQuery(QueryLanguage.SPARQL, query1);
		for (BindingSet bs : QueryResults.asList(tupleQuery.evaluate())) {
			System.out.println(bs);
			String limpiar = bs.toString().substring(49);
			String a = limpiar.replace("^^<http://www.w3.org/2001/XMLSchema#decimal>", "");
			String b = a.replace(";", "");
			String c = b.replace("nombre", " del distrito ");
			String d = c.replace("]", "");
			String e = d.replace("District", "");
			String f = e.replace("\"", "");
			String g = f.replace(" =", " ");
			String h = g.replace(" , Madrid", "");
			String i = h.replace(" del", "km² del");
			frontera = frontera + i + "\n";

		}
		if (frontera.equals("\n"))
			frontera = "\n" + "No hay distritos frontera en la base de datos" + " para los campos introducidos";

		return "Las areas de los distritos frontera son:" + "\n" + frontera + "\n" + "\n"
				+ "Los parquiemtros estan en las calles:" + "\n" + resultado;
	}

}

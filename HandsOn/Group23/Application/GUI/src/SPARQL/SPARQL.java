package SPARQL;

import org.apache.jena.*;
import org.apache.jena.assembler.Mode;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelGetter;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.impl.MemoryModelGetter;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.algebra.Str;
import org.eclipse.rdf4j.query.resultio.text.tsv.SPARQLResultsTSVWriter;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SPARQL {
    public ArrayList <String> Districts(){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        ArrayList <String> aux=new ArrayList<String>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?o  ?z WHERE{" +
                        "?Subject bici:hasDistrictName ?o." +
                        "?Subject bici:hasDistrictCode ?z"+
                        "} ORDER BY ASC(xsd:integer(?z))";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("o");
//                RDFNode object2 =solution.get("z");
                aux.add(object.toString());
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }
    public ArrayList <String> BarriosInDistricts(String district){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        ArrayList <String> aux=new ArrayList<String>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?z WHERE{" +
                        "?Subject bici:hasDistrictName ?o." +
                        "?Subject bici:hasNeighborhoodName ?z."+
                        "FILTER (?o='"+district+"')"+
                        "} ORDER BY ASC(?z)";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("z");
                aux.add(object.toString());
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }
    public ArrayList Neighborhoods(){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        ArrayList aux=new ArrayList();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?o  ?z WHERE{" +
                        "?Subject bici:hasNeighborhoodName ?o." +
                        "?Subject bici:hasNeighborhoodCode ?z"+
                        "} ORDER BY ASC(xsd:integer(?z))";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("o");
                RDFNode object2 =solution.get("z");
                aux.add(object2 +" "+object);
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }
    public ArrayList <String> CallesBarriosBikeStand(String barrio){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        ArrayList<String> aux=new ArrayList();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?o WHERE{" +
                        "?Subject rdf:type bici:BikeStand."+
                        "?Subject bici:hasNeighborhoodName ?x."+
                        "?Subject bici:hasAddress ?o." +
                        "FILTER (?x='"+barrio+"')"+
                        "} ORDER BY ASC(?o)";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("o");
                aux.add(object.toString());
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }
    public ArrayList<String> CallesBarriosBicimadStation(String barrio){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        ArrayList<String> aux=new ArrayList<String>();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?o ?y WHERE{" +
                        "?Subject rdf:type bici:Bicimad_Stations."+
                        "?Subject bici:hasNeighborhoodName ?x."+
                        "?Subject bici:hasAddress ?o." +
                        "FILTER (?x='"+barrio+"')"+
                        "} ORDER BY ASC(?o)";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("o");
                aux.add(object.toString());
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }
    public ArrayList Description(String barrio){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        ArrayList aux=new ArrayList();
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?o ?y WHERE{" +
                        "?Subject rdf:type bici:BikeStand."+
                        "?Subject bici:hasNeighborhoodName ?x."+
                        "?Subject bici:hasDescription ?o." +
                        "FILTER (?x='"+barrio+"')"+
                        "} ORDER BY ASC(?o)";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("o");
                System.out.println(object);
                aux.add(object);
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }
    public String SameAs(String barrio){
        FileManager.get().addLocatorClassLoader(PROBARSPARQL.class.getClassLoader());
        Model model= FileManager.get().loadModel("D:\\2021-2022\\Semantic Web\\GUI\\NTT\\BiciWhereMadridMappingNT-with-links.nt");
        String aux="";
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX bici: <http://www.biciwhere-madrid.es/bwm/ontology#>" +
                        "PREFIX bici2: <http://www.biciwhere-madrid.es/bwm/resource/>"+
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                        "SELECT DISTINCT ?o WHERE{" +
                        "?Subject rdf:type bici:BikeStand."+
                        "?Subject bici:hasNeighborhoodName ?x."+
                        "?Subject owl:sameAs ?o." +
                        "FILTER (?x='"+barrio+"')"+
                        "} ORDER BY ASC(?o)";
        Query query= QueryFactory.create(queryString);
        QueryExecution qexec= QueryExecutionFactory.create(query,model);
        try{
            ResultSet resultado=qexec.execSelect();
            while(resultado.hasNext()){
                QuerySolution solution=resultado.nextSolution();
                RDFNode object =solution.get("o");
                aux=object.toString();
                aux=aux.replace("s://","://www.");
            }
        }catch (Exception e){
            System.err.println(e);
        }
        finally {
            qexec.close();
        }
        return aux;
    }

    public String Foto(String Uri){
        SPARQLRepository sparqlRepository=new SPARQLRepository("https://query.wikidata.org/sparql");
        RepositoryConnection sparqlConnection = sparqlRepository.getConnection();
        String aux="";
        String query = "SELECT ?pic WHERE {"
                + "<"+Uri+"> wdt:P18 ?pic"
                + "}" ;

        TupleQuery tupleQuery = sparqlConnection.prepareTupleQuery(QueryLanguage.SPARQL, query);
        // tupleQuery.evaluate(new SPARQLResultsTSVWriter(System.out));
        for (BindingSet bs : QueryResults.asList(tupleQuery.evaluate())) {
            String ole=bs.toString();
            String[] partes=ole.split("=");
            String resultado=partes[1].replace("]"," ");
            aux=resultado;
            aux = aux.replace("Special:FilePath/", "File:");
            aux = aux.replace("http", "https");
        }
        return aux;
    }
}


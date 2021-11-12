import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.util.Arrays.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;







public class App {
    static boolean km;
    static int element;
    static File csv;
    static String mCsv;

    public static String sacarQnode(int nCsv, String district) throws FileNotFoundException{
        String link = "";
        switch(nCsv){
            case 1: mCsv = "bici.csv"; break;
            case 2: mCsv = "apar.csv"; break;
            case 3: mCsv = "puntoC.csv"; break;
            default: mCsv = "papelera.csv";
        }
        csv= new File(mCsv);
		Scanner sc=new Scanner(csv);
		sc.useDelimiter(",");
		while(sc.hasNext()) {
            if(district.equals(sc.next())){
                link = sc.next();
                break;
            }
        }
        //sacar qnode
        String qnode = "";
        Scanner sc1=new Scanner(link);
        sc1.useDelimiter("/");
		while(sc1.hasNext()) {
            qnode = sc1.next();
        }
        return qnode;
    }


    public static String test(boolean ope, int elementRe, String district) throws CsvValidationException, IOException{

        km = ope;
        element = elementRe;
        String qnode = sacarQnode(elementRe, district);
        String result = null;
        double dresult = 0;
        String queryString;
        String showvalue=null;
        String unidad = "";
        // se trata de una búsqueda de superficie
        switch(elementRe){
            case 1: unidad = "bicicletas"; break;
            case 2: unidad = "aparcamiento de bicicletas"; break;
            default: unidad = "puntos de carga para los coches eléctricos";
        }
        if(km){
            queryString = "PREFIX wdt: <http://www.wikidata.org/prop/direct/>PREFIX wd: <http://www.wikidata.org/entity/>SELECT DISTINCT ?area WHERE {wd:"+qnode+" wdt:P2046 ?area}"; 
        }
        // se trata de una búsqueda de población
        else{
            queryString = "PREFIX wdt: <http://www.wikidata.org/prop/direct/>PREFIX wd: <http://www.wikidata.org/entity/>SELECT DISTINCT ?popu WHERE {wd:"+qnode+" wdt:P1082 ?popu}";
        }
        
        Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", queryString);
        try {
            ResultSet results = qexec.execSelect();
            if(km){
                String regEx="[^0-9.]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(ResultSetFormatter.asText(results,query));
                result = m.replaceAll("").trim();
            }else{
                Scanner scp=new Scanner(ResultSetFormatter.asText(results));
                scp.useDelimiter("\"");
                result = scp.next();
                result = scp.next();
            }
            dresult = Double.valueOf(result);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            qexec.close();
        }

        CSVReader csvReader = new CSVReader(new FileReader(mCsv));
        String[] fila = null;
        double num = 0;
        switch(elementRe){
            case 1:  
            while((fila = csvReader.readNext()) != null) {
                if(fila[4].equals(district)){
                    num += Double.valueOf(fila[10]);
                }
            }
            break;
            case 2: 
            while((fila = csvReader.readNext()) != null) {
                if(fila[2].equals(district)){
                    num += 1;
                }
            }
            break;
            case 3: 
            while((fila = csvReader.readNext()) != null) {
                if(fila[2].equals(district)){
                    num += 1;
                }
            }
            break;
            default: 
        }
        csvReader.close();

        double valor;
        int v1;
        if(km){
            //System.out.println("km:" + dresult +" unidad: "+ num);
            valor = num/dresult;
            v1 = (int)Math.round(valor);
            showvalue = district+": Para cada kilometro cuadrado del barrio se dispone "+v1+" unidades de "+unidad;
        }
        else{
            //System.out.println("population:" + dresult +" unidad: "+ num);
            valor = dresult/num;
            v1 = (int)Math.round(valor);
            showvalue = district+": Para cada unidad de "+unidad+" se comparte entre "+v1+" personas";
        }

        return showvalue;
    }

    public static void main(String[] args) throws Exception {

    
        System.out.println(test(true, 3, "Carabanchel"));       
    }











}

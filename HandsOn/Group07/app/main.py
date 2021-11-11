import os

from flask import Flask, render_template, request, json, redirect, url_for, session, jsonify
from flask.wrappers import Response
from flaskext.mysql import MySQL
from werkzeug.security import generate_password_hash, check_password_hash
from rdflib import Graph, Namespace, Literal
from rdflib.namespace import RDF, RDFS
from rdflib.plugins.sparql import prepareQuery
from pathlib import Path
import time

class MyResponse(Response):
    default_mimetype = 'application/json'

# CONFIGURACION DE LA APP
app = Flask(__name__)
app.secret_key = 'clave secreta'
app.config['SEND_FILE_MAX_AGE_DEFAULT'] = 0
app.config["CACHE_TYPE"] = "null"

# RDFLIB

g = Graph()
g.namespace_manager.bind('fountain', Namespace("http://www.smartCityParks.es/group07/resource/Fountain/"), override=False)
path = g.parse("./rdf/output.nt", format="turtle")


# MAIN Y METODOS PARA MOSTRAR PAGINAS

@app.route("/")
def main():
    return render_template('index.html')
@app.route("/results", methods=['POST', 'GET'])
def results():
    return render_template("results.html")

@app.route("/busqueda", methods=['POST', 'GET'])
def busqueda():
    try:
        hora=time.strftime("%d%m%Y_%H%M%S")
        filename=str(hora)+".json"
        fileroute="../static/"+hora+".json"
        filepath=Path(fileroute)
        filetoOpne=filepath/filename
        _request=request.args.get('districName')
        #_districName = str(request.form['districName'])
        _districName=str(_request)
        _jsonList= []
        wikiDataLink=None

        # SPARQL query
        fountain = Namespace("http://www.smartCityParks.es/group07/resource/Fountain/")
        relacion = Namespace("http://ww.smartCityParks.es/group07/ontology/Font#")
        dato = Namespace("http://www.smartCityParks.es/group07/resource/District/")
        g.parse(path, format="turtle")

        q1 = prepareQuery('''
         SELECT ?p
        WHERE {
         ?p relacion:isInDistrict dato:''' + _districName + '''
        }
        ''', initNs={
            'relacion': relacion,
            'dato': dato
        })
        fountainList = g.query(q1)

        print(fountainList)

        if(len(_jsonList)==0):
            return render_template("error.html",error="El distrito introducido no es correcto o no existe")
        return render_template("results.html",distrito=_districName,enlace=wikiDataLink)
    except Exception as e:
        return json.dumps({'error': e})


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    app.run(port=8000)
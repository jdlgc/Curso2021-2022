import decimal
from rdflib import Graph, Namespace, Literal
from rdflib.namespace import RDF, RDFS, XSD
from rdflib.plugins.sparql import prepareQuery
from rdflib.plugins.sparql.parserutils import value
import requests


while(True):

  print("====================================================")
  print("Escriba el número del barrio del que desea consultar los datos")

  archivo = "./output-with-links.nt"
  url = 'https://query.wikidata.org/sparql'

  g = Graph()
  g.namespace_manager.bind('acc', Namespace("http://safemadrid.linkeddata.es/ontology/accidentalidad#"), override=False)
  g.namespace_manager.bind('rdf', Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#"), override=False)
  g.namespace_manager.bind('owl', Namespace("http://www.w3.org/2002/07/owl#"), override=False)
  g.parse(source=archivo, format="nt")

  acc = Namespace("http://safemadrid.linkeddata.es/ontology/accidentalidad#")
  owl = Namespace("http://www.w3.org/2002/07/owl#")
  wikidata = Namespace("https://www.wikidata.org/")

  q1 = prepareQuery('''
    SELECT ?Name WHERE { 
      ?Barrio rdf:type acc:barrio.
      ?Barrio acc:hasNAMEBARRIO ?Name.
    }
    ''',
    initNs = { "acc": acc }
  )
  i=0
  for r in g.query(q1):
    i += 1
    print(i, ":", r.Name)
  print("====================================================")
  

  barrio=""
  barrioID = int(input("Barrio:"))
  i=0
  for r in g.query(q1):
    i += 1
    if (i==barrioID):
      barrio = r.Name
  print("++ Datos de ",barrio,"++")
  q2 = prepareQuery('''
    SELECT ?Name WHERE { 
      ?Barrio acc:hasNAMEBARRIO "'''+ barrio +'''".
      ?Barrio acc:hasDISTRITO ?Distrito.
      ?Distrito acc:hasNAME ?Name.
    }
    ''',
    initNs = { "acc": acc }
  )

  distrito = ""
  for r in g.query(q2):
    print("DISTRITO:",r.Name)
    distrito = r.Name



  q3 = prepareQuery('''
    SELECT ?Accidente WHERE { 
      ?Distrito acc:hasNAME "'''+ distrito +'''".
      ?Accidente acc:ocurreEN ?Distrito.
    }
    ''',
    initNs = { "acc": acc }
  )

  nAccidents = 0
  for r in g.query(q3):
    nAccidents += 1

  print("Nº de Accidentes del Distrito:",nAccidents)



  if nAccidents==0:
    print("Media de lesividad: 0.0/14")
  else:
    q4 = prepareQuery('''
    SELECT ?Accidente ?Numero WHERE { 
      ?Distrito acc:hasNAME "'''+ distrito +'''".
      ?Accidente acc:ocurreEN ?Distrito.
      ?Accidente acc:hasLESIVIDAD ?Numero.
    }
    ''',
    initNs = { "acc": acc }
    )
    lesividadTotal = 0
    for r in g.query(q4):
      lesividadTotal += int(r.Numero)

    print("Media de lesividad: " + str(lesividadTotal/nAccidents) + "/14")


  q5 = prepareQuery('''

    SELECT ?Uri WHERE {
    ?Distrito acc:hasNAME "'''+ distrito +'''".
    ?Distrito  owl:sameAs ?Uri. 
    }

  ''',
  initNs = { "acc": acc, "owl": owl }
  )

  distritoUri = ""
  for r in g.query(q5):
    distritoUri = r.Uri
    distritoUri = distritoUri.replace("https://wikidata.org/entity/", "")


    

  # ------------------------ WIKIDATA ------------------------

  query = '''
  SELECT DISTINCT ?number WHERE {
    SERVICE wikibase:label { bd:serviceParam wikibase:language "[AUTO_LANGUAGE]". }
    {
      SELECT DISTINCT ?number WHERE {
        wd:'''+ distritoUri +''' p:P1082 ?statement1.
        ?statement1 (psv:P1082/wikibase:quantityAmount) ?number.
      }
    }
  }
  '''

  r = requests.get(url, params = {'format': 'json', 'query': query})
  data = r.json()
  for d in data['results']['bindings']:
    print("\nComenzamos a consultar datos de Wikidata a partir de nuestras consultas anteriores:")
    print("POBLACION DEL DISTRITO:",d['number']['value'], "Personas")
    print("ACCIDENTES PER CAPITA:",nAccidents/int(d['number']['value']), "Accidentes por Persona")
    break



  query = '''
  SELECT DISTINCT ?number WHERE {
    SERVICE wikibase:label { bd:serviceParam wikibase:language "[AUTO_LANGUAGE]". }
    {
      SELECT DISTINCT ?number WHERE {
        wd:'''+ distritoUri +''' p:P2046 ?statement1.
        ?statement1 (psv:P2046/wikibase:quantityAmount) ?number.
      }
    }
  }
  '''

  r = requests.get(url, params = {'format': 'json', 'query': query})
  data = r.json()
  for d in data['results']['bindings']:
    print("AREA DEL DISTRITO: ",d['number']['value'], "KM2")
    print("ACCIDENTES POR KM2: ",nAccidents/float(d['number']['value']), "Accidentes")
    break

  print("====================================================")

  cons = input("Pulse 0 para salir y cualquier otra tecla para realizar otra consulta:")
  if(cons =="0"):
    break

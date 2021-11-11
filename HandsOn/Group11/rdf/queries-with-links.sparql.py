from rdflib import Graph, Namespace, Literal
from rdflib.namespace import RDF, RDFS, XSD
from rdflib.plugins.sparql import prepareQuery

archivo = "./output-with-links.nt"

g = Graph()
g.namespace_manager.bind('acc', Namespace("http://safemadrid.linkeddata.es/ontology/accidentalidad#"), override=False)
g.namespace_manager.bind('rdf', Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#"), override=False)
g.namespace_manager.bind('owl', Namespace("http://www.w3.org/2002/07/owl#"), override=False)
g.parse(source=archivo, format="nt")

acc = Namespace("http://safemadrid.linkeddata.es/ontology/accidentalidad#")
owl = Namespace("http://www.w3.org/2002/07/owl#")

print("\nConsultamos las uris de todos los distritos de todos los accidentes con mujeres\n")
q1 = prepareQuery('''
  SELECT ?Subject ?Name ?Uri WHERE { 
    ?Subject rdf:type acc:accidente.
    ?Subject acc:hasSEXO "Mujer".
    ?Subject acc:ocurreEN ?Object.
    ?Object owl:sameAs ?Uri.
    ?Object acc:hasNAME ?Name.
  }
  ''',
  initNs = { "acc": acc, "owl": owl }
)

for r in g.query(q1):
  print(r.Subject, r.Name, r.Uri)


print("\nConsultamos todos los accidentes de un distrito a partir del barrio Orcasitas\n")
q2 = prepareQuery('''
  SELECT ?Accidente WHERE {
    ?Barrio acc:hasNAMEBARRIO "Orcasitas".
    ?Barrio acc:hasDISTRITO ?Distrito.
    ?Accidente acc:ocurreEN ?Distrito.
  }
  ''',
  initNs = { "acc": acc }
)

for r in g.query(q2):
  print(r.Accidente)


print("\nConsultamos las uris de todos los barrios del distrito Tetuán\n")
q3 = prepareQuery('''
  SELECT ?Barrio WHERE {
    ?Distrito acc:hasNAME "Tetuán".
    ?Distrito acc:hasBARRIO ?Barrio.
  }
  ''',
  initNs = { "acc": acc }
)

for r in g.query(q3):
  print(r.Barrio)
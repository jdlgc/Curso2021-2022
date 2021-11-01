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

print("\nConsultamos las Uris de todos los distritos de todos los accidentes con mujeres\n")
q1 = prepareQuery('''
  SELECT ?Subject ?Name ?Uri WHERE { 
    ?Subject rdf:type acc:accidente.
    ?Subject acc:hasSEXO "Mujer".
    ?Subject acc:hasDISTRITO ?Object.
    ?Object owl:sameAs ?Uri.
    ?Object acc:hasNAME ?Name.
  }
  ''',
  initNs = { "acc": acc, "owl": owl }
)

for r in g.query(q1):
  print(r.Subject, r.Name, r.Uri)


print("\nConsultamos las Uris de todos los barrios de todos los accidentes de tipo alcance\n")
q2 = prepareQuery('''
  SELECT ?Name ?Uri WHERE { 
    ?Subject rdf:type acc:accidente.
    ?Subject acc:hasTIPO_ACCIDENTE "Alcance".
    ?Subject acc:ocurreEN ?Object.
    ?Object owl:sameAs ?Uri.
    ?Object acc:hasBARRIO ?Name.
  }
  ''',
  initNs = { "acc": acc, "owl": owl }
)

for r in g.query(q2):
  print(r.Name, r.Uri)
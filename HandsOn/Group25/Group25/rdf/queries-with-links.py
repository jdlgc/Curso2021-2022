from rdflib import Graph, Namespace
from rdflib.plugins.sparql import prepareQuery

path = 'output-with-links.nt'
g = Graph()
g.namespace_manager.bind('dogpark', Namespace("http://www.madridDogs.es/info/resource/district#"), override=False)

dogpark = Namespace("http://www.madridDogs.es/info/resource/district#")


g.parse(path, format="nt")

q1 = prepareQuery('''
    SELECT ?p ?o
WHERE {
    district:11 ?p ?o
}
''', initNs={
    'district':dogpark
})

for s in g.query(q1):
    print(s.p,' -> ' ,s.o)
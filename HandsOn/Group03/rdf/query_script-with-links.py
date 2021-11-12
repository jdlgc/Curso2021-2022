from rdflib import Graph, Namespace, Literal, RDF, RDFS, XSD
from rdflib.plugins import sparql
from rdflib.plugins.sparql import prepareQuery

file = "./recycle_triples-with-links.nt"
out1 = []
out2 = []

g = Graph()
g.namespace_manager.bind(
    'rc', Namespace("http://smartcity.smartbins.es/lcc/ontology/recycle#"), override=False)
g.namespace_manager.bind(
    'rdf', Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#"), override=False)
g.namespace_manager.bind(
    'owl', Namespace("http://www.w3.org/2002/07/owl#"), override=False)
g.parse(source=file, format="nt")

rc = Namespace("http://smartcity.smartbins.es/lcc/ontology/recycle#")
rdf = Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#")
owl = Namespace("http://www.w3.org/2002/07/owl#")

q1 = prepareQuery(
    '''
    SELECT DISTINCT ?Bin ?link WHERE{
        ?Bin rdf:type rc:Bin.
        ?Bin owl:sameAs ?link.
    }
    ''',
    initNs={
        "rdf": rdf,
        "rc": rc,
        "owl": owl
    }
)

q2 = prepareQuery(
    '''
    SELECT DISTINCT ?Product ?link WHERE{
        ?Product rdf:type rc:Product.
        ?Product owl:sameAs ?link.
    } ORDER BY asc(?Product)
    ''',
    initNs={
        "rdf": rdf,
        "rc": rc,
        "owl": owl
    }
)

for r in g.query(q1):
    out1.append(f'\n#{r.Bin.toPython()}\t\t{r.link.toPython()}')

for r in g.query(q2):
    out2.append(f'\n#{r.Product.toPython()}\t\t{r.link.toPython()}')

with open('querys-with-links.sparql', 'w') as f:
    f.write('''PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rc: <http://smartcity.smartbins.es/lcc/ontology/recycle#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
    ''')

    f.write('\n#QUERY 1: BINS\n\n')
    f.write('''SELECT DISTINCT ?Bin ?link WHERE{
        ?Bin rdf:type rc:Bin.
        ?Bin owl:sameAs ?link.
    }
''')

    for line in out1:
        f.write(line)

    f.write('\n\n#QUERY 2: PRODUCTS\n\n')

    f.write('''SELECT DISTINCT ?Product ?link WHERE{
        ?Product rdf:type rc:Product.
        ?Product owl:sameAs ?link.
    } ORDER BY asc(?Product)
''')

    for line in out2:
        f.write(line)

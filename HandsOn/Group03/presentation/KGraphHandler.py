from rdflib import Graph, Namespace
from rdflib.plugins.sparql import prepareQuery


class KGraphHandler:
    g = Graph()
    rc = Namespace("http://smartcity.smartbins.es/lcc/ontology/recycle#")
    rdf = Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    owl = Namespace("http://www.w3.org/2002/07/owl#")

    def __init__(self, file):
        self.g.namespace_manager.bind('rc', Namespace(
            "http://smartcity.smartbins.es/lcc/ontology/recycle#"), override=False)
        self.g.namespace_manager.bind(
            'rdf', Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#"), override=False)
        self.g.namespace_manager.bind(
            'owl', Namespace("http://www.w3.org/2002/07/owl#"), override=False)
        self.g.parse(source=file, format="nt")

    def requestTypes(self):
        q = prepareQuery(
            """
        SELECT DISTINCT ?Type WHERE{
                ?Product rc:hasName ?Type.
            }
            """,
            initNs={"rc": self.rc}
        )
        res = []
        for r in self.g.query(q):
            res.append(r.Type.toPython())
        return res
# ?Product rc:recycledIn ?Bin.
# ?Bin rc:hasPlace ?Place.

    def requestBins(self, todo, lat, lon):
        dist = 0.009  # 1 km
        q = prepareQuery(
            f"""
        SELECT DISTINCT ?Bin ?Place ?Lvl WHERE {{
            ?Product rc:hasName \"{todo}\".
            ?Product rc:recycledIn ?Bin.
            ?Bin rc:hasLocationName ?Place.
            ?Bin rc:hasAlarm \"OK\".
            ?Bin rc:levelOfFullnes ?Lvl.
            ?Bin rc:hasLat ?Lat.
            ?Bin rc:hasLon ?Lon.
            FILTER( ?Lat > {lat - dist} 
                    && ?Lat < {lat + dist} 
                    && ?Lon > {lon - dist} 
                    && ?Lon < {lon + dist})
        }} LIMIT 10
            """,
            initNs={"rc": self.rc}
        )
        res = []
        for r in self.g.query(q):
            res.append(
                (r.Bin.toPython(), r.Place.toPython(), int((r.Lvl.toPython()/8)*100)))
        return res

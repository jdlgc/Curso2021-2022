8
    General
    Analysis
        - The resource naming strategy is not defined for individuals.
        - URIs should not include years or other information that may change over time.
    Ontology
        - The ontology does not follow the resource naming strategy defined in the analysis.
        - It is a good practice to name classes in singular and not plural.
        - The domains and ranges of properties are wrong. Do not define them with restrictions, just use the class IRIs.
    RDF generated
        - The URIs have to follow the resource naming strategy.
        - The RDF file does not use the class and property URIs defined in the ontology.
        - It could happen that two individuals from different classes have the same URI because the naming strategy does not ensure uniqueness.
        - Stations data are not generated.
        - It is not clear which entities will be used for linking.

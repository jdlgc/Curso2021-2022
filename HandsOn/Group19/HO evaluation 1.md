19
    General
    Analysis
        - The analysis.html file does not contain the analysis of the selected datasets.
        - The resource naming strategy for ontology terms is not defined.
        - The resource naming strategy for individuals is not correct. Do not thing in REST terms but consider resources as independent.
    Ontology
        - The resource naming strategy followed is wrong.
        - Do not use accents in term names (i.e., IRIs).
        - The domain and range of properties is not defined.
        - Some of the defined classes are properties, not classes.
    RDF generated
        - It happens that two individuals from different classes have the same URI because the naming strategy does not ensure uniqueness.
        - The definition of properties is wrong.
        - Datatypes are missing.

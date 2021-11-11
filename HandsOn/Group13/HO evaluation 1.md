13
    General
    Analysis
        - The analysis.html file does not contain the license of the dataset to be generated.
    Ontology
        - The ontology file must be in the Turtle (.ttl) format. There are syntax problems in the ontology; use a tool to create it.
        - There are some properties without domain.
        - It is not clear which of the classes will be used for linking.
    RDF generated
        - Verify that the class and property names used in the RDF data are the same as those used in the ontology.
        - The generation is wrong. Data from different resources are merged into only one.
        - Datatypes are missing.
        - Solve the character encoding issues.

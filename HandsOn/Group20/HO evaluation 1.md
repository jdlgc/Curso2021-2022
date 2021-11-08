20
    General
        - The files are not in the required directories.
    Analysis
        - The analysis.html file does not contain the license of the dataset to be generated.
        - The analysis.html file does not contain the resource naming strategy.
    Ontology
        - There are mistakes in the term names and descriptions.
        - In OWL, having multiple domains (or ranges) means that the domain (or range) is the intersection of all the classes.  The current definitions of properties with multiple domains are wrong.
        - It is not clear which class will be used for linking.
    RDF generated
        - It could happen that two individuals from different classes have the same URI because the naming strategy does not ensure uniqueness.
        - Individuals have no type.
        - There are empty values.
        - Values are not encoded properly.
        - Datatypes are missing.
        - Solve the character encoding issues.

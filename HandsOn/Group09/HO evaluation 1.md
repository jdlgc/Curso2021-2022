9
    General
    Analysis
        - The resource naming strategy should use different paths for ontology resources and individuals.
        - Property URIs should not be related to class URIs. The reason for this is that properties are independent of the class and could be used in other classes.
    Ontology
        - Some of the properties defined with numbers as ranges are not numbers.
        - In OWL, there are object properties (where value of the property is a resource) and datatype properties (where the value of the property is a string literal, usually typed). 
        - Check that all class names start with capital letter.
    RDF generated
        - URIs are encoded as strings.
        - It is not clear which resources will be used for linking.

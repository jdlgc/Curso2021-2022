from rdflib import Graph, Namespace
from rdflib.plugins.sparql import prepareQuery
from helper import *

path = 'rdf/output-with-links.nt'
g = Graph()
g.namespace_manager.bind('district', Namespace("http://www.madridDogs.es/info/resource/district#"), override=False)
districtNs = Namespace("http://www.madridDogs.es/info/resource/district#")
ontologyNs = Namespace("http://www.madridDogs.es/info/ontology#")
neighborhoodNs = Namespace("http://www.madridDogs.es/info/resource/neighborhood#")
dogParkns = Namespace("http://www.madridDogs.es/info/resource/dogpark#")
rubbishNs = Namespace("http://www.madridDogs.es/info/resource/rubbish#")

g.parse(path, format="nt")

menu = '''
        1. Información sobre un distrito
        2. Información sobre un barrio
        3. Información sobre un parque de perros
        4. Mirar los barrios que tiene un distrito
        5. Mirar los parques de perros que tiene un barrio
        6. Mirar la dirección de las papeleras dispensadoras de un barrio
        7. Información sobre una papelera dispensadora
        Salir
    '''

while True:


    print(menu)
    accion = input("Selecciona una opción: ")


    if accion == "1":
        distrito = input("Escribe un distrito: ")
        idDistrito = getIdDistrict(distrito)
        if idDistrito == None:
            print('\nEl distrito {} no existe'.format(distrito))
        else:
            q1 = prepareQuery('''
                SELECT ?p ?o
            WHERE {
                district:'''+ idDistrito + ''' ?p ?o
            }
            ''',
            initNs={
            'district':districtNs,
            })

            print("La información sobre el distrito " + distrito + " es: ")
            print('-------------------------------')
            count = 0
            for s in g.query(q1):
                if count == 1:
                    print("Distrito ID: ", s.o)
                elif count == 2:
                    print("Nombre Distrito: ", s.o)  
                elif count == 3:
                    print("Población perruna: ", s.o)       
                count += 1    
        print('-------------------------------')


    elif accion == "2":
        barrio = input("Escribe un barrio: ")
        idBarrio = getIdNeighborhood(barrio)
        if idBarrio == None:
            print('\nEl barrio {} no existe'.format(barrio))
        else:
            q1 = prepareQuery('''
                SELECT ?p ?o
            WHERE {
                nb:'''+ idBarrio + ''' ?p ?o
            }
            ''',
            initNs={
            'nb':neighborhoodNs,
            })
            print("La información sobre el barrio " + barrio + " es: ")
            print('-------------------------------')
            count = 0
            for s in g.query(q1):
                if count == 1:
                    print("Barrio ID: ", s.o)
                elif count == 2:
                    print("Nombre Barrio: ", s.o.capitalize())  
                elif count == 4:
                    digito_izqda = s.o[-2]
                    if isCharacteraDigit(digito_izqda):
                        num_district = s.o[-2]+s.o[-1]
                    else:
                        num_district = s.o[-1] 
                    name_district = getDistrict(num_district)
                    print("Localizado en distrito: ", name_district)       
                count += 1    
            print('-------------------------------')    
    
        
    elif accion == "3":
        print("Información sobre un parque de perros")
        dogParkId = input("Escribe el identificador de un parque de perros: ")
        q1 = prepareQuery('''
                SELECT ?p ?o
            WHERE {
                dp:'''+ dogParkId + ''' ?p ?o
            }
            ''',
            initNs={
            'dp':dogParkns,
            })
        print("La información sobre el parque de perros " + dogParkId + " es: ")
        print('-------------------------------')  
        
        count = 0
        num_rubbish = 0
        for s in g.query(q1):
            if count == 1:
                print("Parque de Perros ID: ", s.o)
            elif count == 2:
                print("Día de instalación: ", s.o)
            elif count == 3:
                print("Calle: ", s.o.capitalize())
            elif count == 5:
                print("Número de juguetes: ", s.o)     
            elif count == 7:
                print("Estado: ", s.o.capitalize())     
            elif count == 8:
                digito_izqda = s.o[-3]
                digito_centro = s.o[-2]
                if isCharacteraDigit(digito_izqda) and isCharacteraDigit(digito_centro):
                    num_neigh = s.o[-3]+s.o[-2]+s.o[-1]
                elif isCharacteraDigit(digito_centro):
                    num_neigh = s.o[-2]+s.o[-1]
                else:
                    num_neigh = s.o[-1] 
                name_neigh = getNeighborhood(num_neigh)
                print("Localizado en el barrio: ", name_neigh.capitalize())
            elif count > 8:
                num_rubbish += 1    
            count += 1 
        print("Número de papeleras cercanas: ", num_rubbish)                
        print('-------------------------------')         
    
    elif accion == "4":
        distrito = input("Escribe un distrito: ")
        idDistrito = getIdDistrict(distrito)
        if idDistrito == None:
            print('\nEl distrito {} no existe'.format(distrito))
        else:
            q1 = prepareQuery('''
                SELECT ?n
            WHERE {
                ?s on:isPartOf district:'''+ idDistrito + ''' . 
                ?s on:hasName ?n
            }
            ''',
            initNs={
            'district':districtNs,
            'on':ontologyNs,
            })
            print('Los barrios de {} son'.format(distrito))
            for s in g.query(q1):
                print(s.n)
            
    elif accion == '5':
        barrio = input("Escribe un barrio: ")
        idBarrio = getIdNeighborhood(barrio)
        if idBarrio == None:
            print('El barrio {} no existe'.format(barrio))
        else:
            q1 = prepareQuery('''
            SELECT ?s ?n ?a
            WHERE {
                ?s on:isLocatedIn nb:''' + idBarrio + ''' . 
  				?s on:streetName ?n .
  				?s on:isActive ?a
                }
            ''', 
            initNs={
            'nb':neighborhoodNs,
            'on':ontologyNs,
            })
            print('\nLas direcciones de los parques de perros de {} son: \n'.format(barrio))
            for s in g.query(q1):
                print("Dirección: {},  activo: {}, Identificador {}".format(s.n, s.a, s.s[-7:]))
    
    elif accion == '6':
        barrio = input("Escribe un barrio: ")
        idBarrio = getIdNeighborhood(barrio)
        if idBarrio == None:
            print('El barrio {} no existe'.format(barrio))
        else:
            q1 = prepareQuery('''
            SELECT DISTINCT ?n ?id
            WHERE {
  				?d on:isLocatedIn nb:''' + idBarrio + ''' .
                ?d on:hasRubbish ?id .
  				?id on:streetName ?n .
            } 
            ''', 
            initNs={
            'nb':neighborhoodNs,
            'on':ontologyNs,
            })
            print('\nLas direcciones de las papeleras dispensadoras de {} son: \n'.format(barrio))
            for s in g.query(q1):
                print("Dirección: {}, Identificador {}".format(s.n, s.id[-6:]))
    
    elif accion == '7':
        rubbishId = input("Escribe el identificador de una papelera dispensadora: ")
        q1 = prepareQuery('''
            SELECT ?p ?o
            WHERE {
  				rubbish:''' + rubbishId + ''' ?p ?o
            }
        ''', 
        initNs={
            'rubbish': rubbishNs
        })
        counter = 0
        for s in g.query(q1):
            if counter == 2:
                print("Activo: ", s.o)
            if counter == 3:
                print("Fecha de instalación: ", s.o)
            if counter == 4:
                print("Calle: ", s.o)
            if counter == 5:
                print("model: ", s.o)
            counter += 1
            

    elif accion == "Salir" or accion == "salir":
        break
    
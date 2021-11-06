# TransportesMad App


This application will be used to search for information about the taxi stands, Cercanias stations and BiciMad stations of Madrid, such as the district where they are located, address, number of seats, etc. 
Beginning with a CSV file, we have created an RDF from where the data is extracted. Once this RDF was built, we linked it to Wikidata, so we could obtain additional information about the different districts.
You can filter the information through:

BiciMad station:
- Neighborhood
- Urban estate
- Type of reservation
- Number of seats
- Coordinates
- Address
- Release date
- District

Taxi stand:
- Coordinates
- Release date
- District
- Neighborhood
- Urban estate
- Type of reservation
- Way of parking (park at an angle or park on the sidewalk)
- Number of seats
- Address

Cercanias station:
- Settlement
- Province
- File card
- Description
- Coordinates
- Address
- Zip code


### Tools:

- To storage the rdf, a Blazegraph endpoint has been used.
- The Blazegraph libraries have been used to program and perform the queries.
- JavaScript IDE has been used for the programming and testing of the application.
- To create the graphical interface we have also used JavaScript.


### How to use the application:

- At first, when you run the application, you will have 2 boxes and 1 button.
- The first box is used to select which type of transport you want to see the information about.
- The second box is used to type the disctrict or the population you want to see the information about.
- Once both boxes have been filled, the button will need to be pressed, in order to search for the information.
- After the button has been pressed, a list which contains all the available (bycicle/taxi/bus) stops (inside the distrcit or the population) will be shown aswell as an image from the district or the population.
- A new search can be done after getting the results, following the same steps.

# TransportesMad App


This application will be used to search for information about the taxi stands, Cercanias stations and BiciMad stations of Madrid, such as the district where they are located, address, number of seats, etc. 
Beginning with a CSV file, we have created an RDF from where the data is extracted. Once this RDF was built, we linked it to Wikidata, so we could obtain additional information about the different districts.
You can filter the information through:

- Type of transport (BiciMad, Taxi or Cercanias)
- District or population (Exaples: Centro, Retiro, Salamanca or Madrid)


### Tools:

- To storage the rdf, a Blazegraph endpoint has been used.
- The Blazegraph libraries have been used to program and perform the queries.
- JavaScript IDE and ReactJS has been used for the programming and testing of the application.
- To create the graphical interface we have also used JavaScript.

### How to execute the application:

1. Download and extract the semantic_interface.zip file
2. Run blazegraph and load the rdf file to blazegraph as shown in the Installing and runnig blazegraph section
3. In the root of the files extracted from the semantic_interface.zip file run the following commands: 
 ```
npm install
npm start
```
4. A tab on Google Chrome will open were you can start doing the queries you want

### Installing and running Blazegraph:

1. Download the blazegraph.jar file
2. Run it by : 
 ```
 java -server -Xmx4g -jar blazegraph.jar
```
3. Go to http://localhost:9999/blazegraph/
4. Load RDF dataset through the "Update" tab

### Installing nodejs

The application has been built using ReactJS so in order to launch the app, nodejs and npm has to be installed.
```
# Installation for linux users
sudo apt install nodejs
sudo apt install npm
# Installation for Mac users
brew install node
``` 
 Windows users must download nodejs (this will install nodejs and npm) from [here.](https://nodejs.org/es/download/)
 
 Once nodejs and npm packages have been installed, in the root of the app run the following commands to launch it:
 
 ```
npm install
npm start
```

Note: Remember that since the app uses Blazegraph, it has to be already executing in port 9999.


### How to use the application:

- At first, when you run the application, you will have 2 boxes and 1 button.
- The first box is used to select which type of transport you want to see the information about.
- The second box is used to type the disctrict or the population you want to see the information about.
- Once both boxes have been filled, the button will need to be pressed, in order to search for the information.
- After the button has been pressed, a list which contains all the available (bycicle/taxi/train) stops (inside the distrcit or the population) will be shown aswell as an image from the district or the population.
- A new search can be done after getting the results, following the same steps.
## Application of the group 09

This app is built on node js using electron. 
To use first you need node.js installed.<br>

Run this in the folder where package.json is located: 
```
npm install
```

This will get every module needed to run the app. Once it finishes run the following to start the app:

```
npm start
```

Once the app is running you can test it.
It allows for a search on the bar to the right, it searches for exact coincidences for values of a public parking.
So if you want to search for every parking in a district, simply type the name of the district and it will 
mark the coincidences in the map. You can also search by id or name.

### Other stuff

The app uses Open Street Map layers by the use of the Open Layers library. Also, for the queries to our rdf file
we use rdflib for node, which didn't support complex sparql syntax like the keyword UNION, so we had to do more queries 
than what we wanted.
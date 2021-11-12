const rdf = require("rdflib");
const fs = require("fs");



let prefixes=["PREFIX base: <https://publicparkingmad.com/>",
    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>",
    "PREFIX owl: <http://www.w3.org/2002/07/owl#>"]


let prefixesToString=(prefixes)=>{
    let str="";
    for(let i in prefixes)
        str+=prefixes+"\n";
    return str
}


let file=fs.readFileSync("..\\rdf\\out-with-links.n3").toString()
let uri = 'https://publicparkingmad.com/'
let body = file;
let mimeType = 'text/turtle'
let store = rdf.graph()

try {
    rdf.parse(body, store, uri, mimeType)
} catch (err) {
    console.log(err)
}

 function query(query, max){


        let posquery=rdf.SPARQLToQuery(`
        ${prefixesToString(prefixes)}
        LIMIT ${max}



${query}


}
    `,false,store);

        let cont=0;
        let lista=  store.querySync(posquery)

        if(lista===undefined||lista.length===0){
            return ("Ha habido un error con la query")}
        return (lista)

}


module.exports={
    query:query
}
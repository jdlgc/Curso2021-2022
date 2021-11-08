const {contextBridge, ipcRenderer} = require("electron")
const ol = require("./ol.js");
const querycaller = require("../querycaller");
const {st} = require("rdflib");
let desc
window.addEventListener('DOMContentLoaded', async () => {

    let querycaller = require("../querycaller")

    let cosa = querycaller.query(`
    select ?PK ?lat ?lon
    WHERE{
    
    ?elemento base:hasPK ?PK.
    ?elemento base:hasLocation ?loc.
    ?loc base:hasGeographicCoordinates ?geo.
    ?geo base:hasLatitude ?lat.
    ?geo base:hasLongitude ?lon.
    
    }
    
    
    `, 20)


    let coords = {}
    for (let i in cosa) {
        coords[cosa[i]["?PK"].value] = {
            lat: Number(cosa[i]["?lat"].value),
            lon: Number(cosa[i]["?lon"].value)
        }
    }


    let ol = require("./ol.js")
    let map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([-3.701154201312126, 40.43277769820206]),
            zoom: 13
        })
    });

    const iconstyle = new ol.style.Style({
        image: new ol.style.Circle({
            fill: new ol.style.Fill({
                color: '#c61489',
            }),
            radius: 8
        })


    })
    const iconstyle2 = new ol.style.Style({
        image: new ol.style.Circle({
            fill: new ol.style.Fill({
                color: '#00ffe3',
            }),
            radius: 8
        })


    })
    let features = []
    for (let i in coords) {
        let feature = new ol.Feature({
            key: i,
            geometry: new ol.geom.Point(ol.proj.fromLonLat([coords[i].lon, coords[i].lat])),
        })
        if (i === "uwu")
            feature.setStyle(iconstyle2)
        else feature.setStyle(iconstyle);
        coords[i]["feature"] = feature;

        features.push(feature)

    }


    let layer = new ol.layer.Vector({
        source: new ol.source.Vector({
            features: features
        })
    });
    map.addLayer(layer);


    let container = document.getElementById('popup');
    let content = document.getElementById('popup-content');
    let closer = document.getElementById('popup-closer');
    let desc = document.getElementById("desc");
    let loc = document.getElementById("loc");
    let overlay = new ol.Overlay({
        element: container,
        autoPan: true,
        autoPanAnimation: {
            duration: 250
        }
    });
    map.addOverlay(overlay);

    closer.onclick = function () {
        overlay.setPosition(undefined);
        closer.blur();
        return false;
    };

    let coso;
    map.on('singleclick', async function (event) {
        if (coso) {
            coso = false;
            document.getElementById("recienpopup").style.clipPath = "circle(0% at 0 0)";
            desc.style.transform = "translateX(-150%)";
            loc.style.transform = "translateX(-150%)";
        }
        if (map.hasFeatureAtPixel(event.pixel) === true) {
            let coordinate = event.coordinate;
            let elem;
            map.forEachFeatureAtPixel(event.pixel, async (cosa) => {
                elem = querycaller.query(`
                SELECT ?nombre WHERE{
               
                ?elemento base:hasPK "${cosa.A.key}"^^<http://www.w3.org/2001/XMLSchema#int> .
                ?elemento base:hasName ?nombre .
                
                
          
                }    
                `)


                content.innerHTML = `<div class="popup" id="recienpopup"><b>${elem[0]["?nombre"].value}.</b><br />ID: ${cosa.A.key}. 
                <br/><button class="botonver" id="btnabrir">Info</button><button class="botonver" id="btnloc">Location</button></div>`;
                setTimeout(() => {
                    document.getElementById("recienpopup").style.clipPath = "circle(200% at 0 0)";
                    document.getElementById("btnloc").onclick = (e) => {
                        getLocation(e, cosa.A.key)
                    };
                    document.getElementById("btnabrir").onclick = (e) => {
                        getEverything(e, cosa.A.key)
                    }
                }, 20)
                coso = true;

            })


            overlay.setPosition(coordinate);
        } else {

            setTimeout(() => {
                overlay.setPosition(undefined);
                closer.blur();
            }, 400)

        }
    });


    async function getLocation(e, id) {
        desc.style.transform = "translateX(-200%)";
        loc.style.transform = "none";
        loc.innerHTML = `<h1>Location</h1>`
        let s = querycaller.query(`SELECT ?location WHERE
       {
        ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
               ?elemento base:hasLocation ?location.
              
       } 
       
`)
        let locat = s[0]["?location"].value;

        console.log(locat)
        let stringaponer = ""

        s = querycaller.query(`SELECT ?coordx ?coordy WHERE
        {
        ${locat} base:hasCoordinates ?coords.
        ?coords base:hasCoorX ?coordx.
        ?coords base:hasCoorY ?coordy.
        }
    `)

    if(s[0]["?coordx"]!==undefined) {
        stringaponer += `<h3>Coordinates</h3><div class="fondoclaro">`

        stringaponer += `<p><b>Cordinate X: </b>${s[0]["?coordx"].value} <br>`
        stringaponer += `<b>Cordinate Y: </b>${s[0]["?coordy"].value}</p>`

        stringaponer += "</div>";
        loc.innerHTML += stringaponer


        stringaponer = ""
        stringaponer += `<h3>Geographical Coordinates</h3><div class="fondoclaro">`
    }
        s = querycaller.query(`SELECT ?lat ?lon WHERE
      {
        ${locat} base:hasGeographicCoordinates ?gcoords.
        ?gcoords base:hasLatitude ?lat.
        ?gcoords base:hasLongitude ?lon.
        }
    `)


        stringaponer += `<p><b>Latitude: </b>${s[0]["?lat"].value} <br>`
        stringaponer += `<b>Longitude: </b>${s[0]["?lon"].value}</p>`

        stringaponer += "</div>";
        loc.innerHTML += stringaponer


        s = querycaller.query(`SELECT  ?postalAddress WHERE
    {
    ${locat} base:hasPostalAddress ?postaladdress.
    }
    `)

        let postadd = s[0]["?postaladdress"].value;
        loc.innerHTML += `<h3>Postal Address</h3>`
        s = querycaller.query(`SELECT ?stname ?stnum ?sttype ?postalcode WHERE
        {
        ${postadd} base:hasStreetName ?stname.
      
        ${postadd} base:hasStreetType ?sttype.
        ${postadd} base:hasPostalCode ?postalcode.
        }
    `)
        let s2 = querycaller.query(`SELECT ?stnum  WHERE
        {
       
        ${postadd} base:hasStreetNumber ?stnum.
        
        }
    `)


        loc.innerHTML += `
    <h5>${s[0]["?sttype"].value} ${s[0]["?stname"].value}${s2[0]["?stnum"]?", "+s2[0]["?stnum"].value:""} <br>
    Madrid. ${s[0]["?postalcode"].value} 
    </h5> 
    `


        s = querycaller.query(`SELECT ?district ?wikidata WHERE
        {
        ${postadd} base:hasDistrict ?dis.
        ?dis rdfs:label ?district.
        ?dis <http://www.w3.org/2002/07/owl#sameAs> ?wikidata.
        
        
  
        }
    `)
        loc.innerHTML += `<h3>District</h3>`
        loc.innerHTML += ` <h5>
      <a href='${s[0]["?wikidata"].value}' target="_blank">${s[0]["?district"].value}</a>  <i>linked to wikidata</i>
    </h5> 
    `

        s = querycaller.query(`SELECT ?ne ?wikidata WHERE
        {
        ${postadd} base:hasNeighborhood ?n.
        ?n rdfs:label ?nei.
        ?n <http://www.w3.org/2002/07/owl#sameAs> ?wikidata.
        
        
  
        }
    `)
        loc.innerHTML += `<h3>Neighborhood</h3>`
        loc.innerHTML += ` <h5>
      <a href='${s[0]["?wikidata"].value}' target="_blank">${s[0]["?nei"].value}</a>  <i>linked to wikidata</i>
    </h5> 
    `

    }


    async function getEverything(e, id) {
        let s = querycaller.query(`SELECT ?nombre ?descripcion ?district WHERE
       {
        ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
                ?elemento base:hasName ?nombre.
              
                ?elemento base:hasDescription ?descripcion.
                
       
       } 
      
      
      
      
`)

        desc.style.transform = "none";
        loc.style.transform = "translateX(-200%)";

        desc.innerHTML = `
                        <h1>${s[0]["?nombre"].value}</h1>
                        <h3>Description</h3>
                        <p>${s[0]["?descripcion"].value}</p>
                        
                        `

        s = querycaller.query(`SELECT ?shedule where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasSchedule ?schedule.
            
            
            `)


        if (s[0]["?schedule"] !== undefined) {
            desc.innerHTML += `
               <h3>Schedule</h3>
               <p>${s[0]["?schedule"].value}</p>
               `
        }

        let transport = {}
        s = querycaller.query(`SELECT ?metro where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasMetroNear ?metro.
            
            
            `)

        if (s[0]["?metro"] !== undefined)
            transport["Metro"] = s[0]["?metro"].value

        s = querycaller.query(`SELECT ?bus where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasBusNear ?bus.
            
            
            `)

        if (s[0]["?bus"] !== undefined)
            transport["Bus"] = s[0]["?bus"].value

        s = querycaller.query(`SELECT ?cercanias where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasCercaniasNear ?cercanias.
            
            
            `)

        if (s[0]["?cercanias"] !== undefined)
            transport["Cercanias"] = s[0]["?cercanias"].value

        s = querycaller.query(`SELECT ?Tranvia where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasTranviaNear ?Tranvia.
            
            
            `)

        if (s[0]["?Tranvia"] !== undefined)
            transport["Tranvia"] = s[0]["?Tranvia"].value


        if (Object.keys(transport).length) {
            let stringaponer = ""
            stringaponer += `<h3>Public transport near:</h3><div class="fondoclaro">`
            for (let i in transport) {
                stringaponer += `
               <h5>${i}</h5>
               <p>${transport[i]}</p>
               `
            }

            stringaponer += "</div>"
            desc.innerHTML += stringaponer
        }


        s = querycaller.query(`SELECT ?tel where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasTelephone ?tel.
            
            `)
        if (s[0]["?tel"] !== undefined) {

            desc.innerHTML += `
               <h3>Telephone</h3>
               <p>${s[0]["?tel"].value}</p>
               `

        }
        s = querycaller.query(`SELECT ?acces where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasAccesibility ?acces.
            
            
            `)
        if (s[0]["?acces"] !== undefined) {

            desc.innerHTML += `
               <h3>Access for people with reduced movility?</h3>
               <p>${s[0]["?acces"].value === "true" ? "Yes" : "No"}</p>
               `

        }

        s = querycaller.query(`SELECT ?email where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasEmail ?email.
            
            
            `)

        if (s[0]["?email"] !== undefined) {

            desc.innerHTML += `
               <h3>Email</h3>
               <p>${s[0]["?email"].value}</p>
               `

        }
        s = querycaller.query(`SELECT ?url where {
             ?elemento base:hasPK "${id}"^^<http://www.w3.org/2001/XMLSchema#int> .
             ?elemento base:hasUrl ?url.
            
            
            `)
        if (s[0]["?url"] !== undefined) {
            desc.innerHTML += `
               <h3>Url</h3>
               <p><a href=" ${s[0]["?url"].value}"  target="_blank">Click Here</a></p>
               `
        }


    }

    document.getElementById("btnenter").onclick = () => {
        let valorinput = document.getElementById("in").value

        for (let i in coords)
            coords[i].feature.setStyle(iconstyle)

        if (!isNaN(valorinput)) {

            coords[valorinput].feature.setStyle(iconstyle2)

            return
        }



        let s = querycaller.query(`
        select ?pk where{
        
        ?cosa ?propiedad "${valorinput}".
           ?cosa base:hasPK ?pk.
        
        `)

        for(let i in s){
            if (s[i]["?pk"] !== undefined)
                coords[s[i]["?pk"].value].feature.setStyle(iconstyle2)
        }
         s = querycaller.query(`
        select ?pk where{
        
        ?cosa ?propiedad "${valorinput}".
        ?cosa2 ?propiedad2 ?cosa.
           ?cosa2 base:hasPK ?pk.
        
        `)

        for(let i in s){
            if (s[i]["?pk"] !== undefined)
                coords[s[i]["?pk"].value].feature.setStyle(iconstyle2)
        }
        s = querycaller.query(`
        select ?pk where{
        
        ?cosa ?propiedad "${valorinput}".
        ?cosa2 ?propiedad2 ?cosa.
          ?cosa3 ?propiedad3 ?cosa2.
           ?cosa3 base:hasPK ?pk.
        
        `)

        for(let i in s){
            if (s[i]["?pk"] !== undefined)
                coords[s[i]["?pk"].value].feature.setStyle(iconstyle2)
        }


        s = querycaller.query(`
        select ?pk where{
        
        ?cosa ?propiedad "${valorinput}".
        ?cosa2 ?propiedad2 ?cosa.
          ?cosa3 ?propiedad3 ?cosa2.
           ?cosa4 ?propiedad4 ?cosa3.
           ?cosa4 base:hasPK ?pk.
        
        `)

        for(let i in s){
            if (s[i]["?pk"] !== undefined)
                coords[s[i]["?pk"].value].feature.setStyle(iconstyle2)
        }




        //console.log(s)


    }


})


function capitalizarPrimeraLetra(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

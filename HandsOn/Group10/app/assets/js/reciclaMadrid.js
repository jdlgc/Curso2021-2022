function showInfo() {
  this.resetView();
  var url = `http://localhost:9000/sparql?query=`
  var listDiv = document.getElementById("idListDiv");

  // Elementos seleccionados en los Select
  var oSelectDistrict = document.getElementById("selectDistrict");
  var sDistrictId = oSelectDistrict.options[oSelectDistrict.selectedIndex].value;
  var oSelectRecyclePoint = document.getElementById("selectRecyclePoint");
  var sRecyclePoint = oSelectRecyclePoint.options[oSelectRecyclePoint.selectedIndex].value;

  //Validación de campos
  if (sDistrictId === "None" || sRecyclePoint === "None") {
    alert("Seleccione un distrito y un punto de reciclaje.");
    return;
  }
  if (sRecyclePoint === 'Todos') {
    url = url + `PREFIX%20%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX%20owl%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX%20%20vocab%3A%20%3Chttp%3A%2F%2Fwww.reciclamadrid.es%2Fontology%23%3E%0ASELECT%20%3Fid%20%3FdataType%20%3FroadType%20%3FroadName%20%3Fnum%20%3Fwiki%0A%20%20%20%20WHERE%7B%0A%20%20%20%20%20%20%20%20%3Felemento%20vocab%3Aname%20%3Fid%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Adata_type%20%3FdataType%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3AhasAddress%20%3Faddress%3B%0A%09%09%09vocab%3AhasDistrict%20%3Fdistrict.%0A%20%20%20%20%20%20%20%20%3Faddress%20vocab%3Aroad_type%20%3FroadType%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Aroad_name%20%3FroadName%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Anum%20%3Fnum.%0A%20%20%09%09%3Fdistrict%20vocab%3Adistrict_code%20%3Fd%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20owl%3AsameAs%20%3Fwiki%3B%0A%09%09%09FILTER%20(%3Fd%3D%22${sDistrictId}%22).%0A%7D`
  } else {
    url = url + `PREFIX%20%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX%20owl%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX%20%20vocab%3A%20%3Chttp%3A%2F%2Fwww.reciclamadrid.es%2Fontology%23%3E%0ASELECT%20%3Fid%20%3FdataType%20%3FroadType%20%3FroadName%20%3Fnum%20%3Fwiki%0A%20%20%20%20WHERE%7B%0A%20%20%20%20%20%20%20%20%3Felemento%20vocab%3Aname%20%3Fid%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Adata_type%20%3FdataType%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3AhasAddress%20%3Faddress%3B%0A%09%09%09vocab%3AhasDistrict%20%3Fdistrict%3B%0A%20%20%20%09%09%09FILTER%20(%3FdataType%3D%22${sRecyclePoint}%22).%0A%20%20%20%20%20%20%20%20%3Faddress%20vocab%3Aroad_type%20%3FroadType%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Aroad_name%20%3FroadName%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Anum%20%3Fnum.%0A%20%20%09%09%3Fdistrict%20vocab%3Adistrict_code%20%3Fd%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20owl%3AsameAs%20%3Fwiki%3B%0A%09%09%09FILTER%20(%3Fd%3D%22${sDistrictId}%22).%0A%7D`
  }

  const http = new XMLHttpRequest()

  http.open("GET", url)
  http.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      aRes = JSON.parse(this.responseText)["results"]["bindings"];
      if(aRes.length === 0) {
        alert("No hay información para esta búsqueda.");
        return;
      }
      var index = 0;
      for (var oRow in aRes) {
        var divInfo = document.createElement("div");
        divInfo.innerHTML = `
            <div id='idDivInfo${index}' class='row'>
                <div class="col-lg-5 py-2">
                    <p id='idRecycleType${index}'>${aRes[oRow]["dataType"]["value"]}</p>
                </div>
                <div class="col-lg-5 py-2">
                    <p id='idAddress${index}'>${aRes[oRow]["roadType"]["value"]} ${aRes[oRow]["roadName"]["value"]}, ${aRes[oRow]["num"]["value"]}</p>
                </div>
                <div class="col-lg-1">
                    <button id='idNavButton${index}' class="btn btn-secondary" onclick="navDetails(${index}, '${aRes[oRow]["id"]["value"]}')">⮞</button>
                </div>
            </div>
        `

        listDiv.appendChild(divInfo);
        listDiv.appendChild(document.createElement("hr"));
        index++;
      }
      listDiv.setAttribute("class", "col-lg-8 py-3 bg-light");
      showImg(aRes[oRow]["wiki"]["value"]);
    }
  }
  http.send()
}

function showImg(wikiUrl) {
  var wikiCode = wikiUrl.split("/")[wikiUrl.split("/").length - 1];
  var url = `https://query.wikidata.org/sparql?query=SELECT%20%3FimgUrl%20%3Fbarrios%0AWHERE%0A%7B%0A%20%20wd%3A${wikiCode}%20wdt%3AP242%20%3FimgUrl%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20wdt%3AP150%20%3FwikiBarrios.%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%0A%20%20%20%20bd%3AserviceParam%20wikibase%3Alanguage%20%22es%22.%0A%20%20%20%20%3FwikiBarrios%20rdfs%3Alabel%20%3Fbarrios.%0A%20%20%20%20%7D%0A%7D`

  const http = new XMLHttpRequest()

  http.open("GET", url)
  http.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      var aBarrios = [];
      var splitRes = this.responseText.split("'es'>");
      for (var index in splitRes) {
        if (index === 0) {
          continue;
        }
        aBarrios[index - 1] = splitRes[index].split("</literal>")[0];
      }
      var imgUrl = this.responseText.split("<uri>")[1].split('</uri>')[0];
      var imgDiv = document.getElementById("idImgDiv");
      imgDiv.innerHTML = `
        <div id='idImgDiv'>
          <img id='idDistrictImg' src=${imgUrl} width='265' height='265' class='py-4 px-4'/>
          <hr/>
          <p class='py-1 px-2'>Incluye los barrios:</p>
        </div>
      `
      var barriosList = document.createElement("ul");
      for (var i in aBarrios) {
        if (i < 0) {
          continue;
        }
        var dot = document.createElement("li");
        dot.setAttribute("class", "px-3");
        dot.textContent = aBarrios[i];
        barriosList.appendChild(dot);
      }
      imgDiv.setAttribute("class", "col-lg-3 bg-light");
      imgDiv.appendChild(barriosList);
    }
  }
  http.send()

}

function navDetails(index, id) {
  // Borro info si ya se hizo una búsqueda anterior
  var detailsSection = document.getElementById("idDetailsSection");
  if (document.getElementById("idContainerDetails")) {
    while (detailsSection.hasChildNodes()) {
      detailsSection.lastChild.remove();
    }
  }

  location.href = '#idDetailsSection'
  var sRecycleType = document.getElementById("idRecycleType" + index).textContent;
  var sAddress = document.getElementById("idAddress" + index).textContent;

  var url = `http://localhost:9000/sparql?query=PREFIX%20%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX%20%20vocab%3A%20%3Chttp%3A%2F%2Fwww.reciclamadrid.es%2Fontology%23%3E%0ASELECT%20%3Fd%20%3Fcenter%20%3Fschedule%20%3Fwaste%20%3FutmX%20%3FutmY%0A%20%20%20%20WHERE%7B%0A%20%20%20%20%20%20%20%20%3Felemento%20vocab%3Aname%20%3Fid%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Acenter%20%3Fcenter%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Aschedule%20%3Fschedule%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20vocab%3Awaste%20%3Fwaste%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20vocab%3AhasDistrict%20%3Fdistrict%3B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20vocab%3AhasCoordinates%20%3Fcoord%0A%20%20%09%09%09%09FILTER%20(%3Fid%3D%22${id}%22).%0A%20%20%09%09%3Fdistrict%20vocab%3Adistrict%20%3Fd.%0A%20%20%09%09%3Fcoord%20vocab%3AutmX%20%3FutmX%3B%0A%20%20%20%20%20%20%20%20%20%20%20vocab%3AutmY%20%3FutmY%3B%0A%7D`

  const http = new XMLHttpRequest()

  http.open("GET", url)
  http.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      aRes = JSON.parse(this.responseText)["results"]["bindings"];
      var container = document.createElement("div");
      container.innerHTML = `
    <div id="idContainerDetails" class="container">
      <div class="row wow zoomIn">
        <div class="col-lg-12 py-3 bg-light">
          <div class="row align-items-center">
            <button class="btn btn-secondary" onclick="navBackDetails(${index})">⮜</button>
            <h2 class="title-section py-3">${sRecycleType}</h2>
          </div>
          <div class="row align-items-center">
            <div class="col-lg-6">
              <div class="row">
                <div class="col-lg-4 py-1 text-right">
                  <p>Distrito:</p>
                </div>
                <div id="idDetalleDistrito" class="col-lg-4 py-1">
                  <p>${aRes[0]["d"]["value"]}</p>
                </div>
              </div>
              <div class="row">
                <div class="col-lg-4 py-1 text-right">
                  <p>Dirección:</p>
                </div>
                <div id="idDetalleDireccion" class="col-lg-4 py-1">
                  <p>${sAddress}</p>
                </div>
              </div>
              <div class="row">
                <div class="col-lg-4 py-1 text-right">
                  <p>Centro:</p>
                </div>
                <div id="idDetalleCentro" class="col-lg-4 py-1">
                  <p>${aRes[0]["center"]["value"]}</p>
                </div>
              </div>
              <div class="row">
                <div id="idDetalleHorario" class="col-lg-4 py-1 text-right">
                  <p>Horario:</p>
                </div>
                <div class="col-lg-4 py-1">
                  <p>${aRes[0]["schedule"]["value"]}</p>
                </div>
              </div>
            </div>
            <div id="map" class="row col-lg-5">
            </div>
          </div>
        </div>
      </div>
    </div>
    `
      document.getElementById("idDetailsSection").appendChild(container);

      var map = L.map('map').
        setView([aRes[0]["utmY"]["value"], aRes[0]["utmX"]["value"]],
          14);

      L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://cloudmade.com">CloudMade</a>',
        maxZoom: 18
      }).addTo(map);

      L.control.scale().addTo(map);
      L.marker([aRes[0]["utmY"]["value"], aRes[0]["utmX"]["value"]]).addTo(map);
    }
  }
  http.send()
}

function navBackDetails(index) {
  location.href = '#';
  var detailsSection = document.getElementById("idDetailsSection");
  if (document.getElementById("idContainerDetails")) {
    while (detailsSection.hasChildNodes()) {
      detailsSection.lastChild.remove();
    }
  }
}

function resetView(){
    var listDiv = document.getElementById("idListDiv");
    if (document.getElementById("idDivInfo0")) {
      listDiv.removeAttribute("class");
      while (listDiv.hasChildNodes()) {
        listDiv.lastChild.remove();
      }
    }
    var imgDiv = document.getElementById("idImgDiv");
    if (document.getElementById("idDistrictImg")) {
      imgDiv.removeAttribute("class");
      while (imgDiv.hasChildNodes()) {
        imgDiv.lastChild.remove();
      }
    }
    
}
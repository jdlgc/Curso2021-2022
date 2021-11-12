# importing libraries
from PyQt5.QtWidgets import * 
from PyQt5 import QtCore, QtGui
from PyQt5.QtGui import * 
from PyQt5.QtCore import *
from rdflib import Graph, Namespace, Literal
from rdflib.namespace import OWL, RDF, RDFS
from rdflib.plugins.sparql import prepareQuery
import rdflib.plugins.sparql as sparql
import sys, os

aplication_path = os.path.dirname(sys.executable)

# Creation of pyqt5 app
App = QApplication(sys.argv)
win = QMainWindow()
central = QWidget()
win.setCentralWidget(central)

# Widgets used
list_widget = QListWidget()
list_widget2 = QListWidget() 
button_accept = QPushButton()
button_return = QPushButton()
label = None

# RDFLib (SmartCity.Madrid)
g = Graph()
g.bind("owl", OWL)
g.bind("rdf", RDF)
g.bind("rdfs", RDFS)

dbo = Namespace("http://www.smartcity.madrid.es/mobility/")
g.bind("dbo", dbo)

data = "output-with-links.nt"
g.parse(data, format="nt")

class Window(QMainWindow):
    
    def __init__(self):
        super().__init__()
  
        # setting title
        self.setWindowTitle("Movilidad Madrid")
  
        # setting geometry
        self.setGeometry(100, 100, 500, 400)
  
        # calling the Main Window method (Menu)
        self.UiComponents()
  
        # showing all the widgets (Menu)
        self.show()
    
    # Second Screen (Once you press the button)
    def buttonReturn():        
        window.show()
        win.close()
        

    # Second Screen (Once you press the button)
    def buttonClicked(_list_widget):
        
        # creating the widgets
        button_return = QPushButton(win)
  
        # setting geometry to the widgets
        button_return.setGeometry(30, 595, 135, 30)

        # button widget actions
        button_return.setText("Atrás")

        rows = _list_widget.selectedItems()
        row = rows[0].text()
       
        # variables for SPARQL queries 
        selected = ""
        busList = []
        busListW = []
        lrList =[]
        lrListW =[]
        underList = []
        underListW = []
        
        # District that has been selected in the last Screen
        items = list_widget.selectedItems()
        for x in range(len(list_widget)):
            if (list_widget.item(x).isSelected()):
                selected = list_widget.item(x).text()      

        # Queries To SmartCity.Madrid
        # Bus
        queryB = 'SELECT DISTINCT ?y ?w WHERE { ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.smartcity.madrid.es/mobility/ontology/Bus>. ?y <http://www.smartcity.madrid.es/mobility/ontology/District_Name> "' + selected + '" . ?y <http://www.w3.org/2002/07/owl#sameAs>  ?w . }'
        queryBpq = sparql.prepareQuery(queryB)

        for bus in g.query(queryBpq):
            busList.append(bus.y)
            busListW.append(bus.w) 

        # Light rail
        queryLR = 'SELECT DISTINCT ?y ?w WHERE { ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.smartcity.madrid.es/mobility/ontology/LightMetro>. ?y <http://www.smartcity.madrid.es/mobility/ontology/District_Name> "' + selected + '" . ?y <http://www.w3.org/2002/07/owl#sameAs>  ?w . }'
        queryLRpq = sparql.prepareQuery(queryLR)

        for lightRail in g.query(queryLRpq):
            lrList.append(lightRail.y)
            lrListW.append(lightRail.w) 

        # Metro 
        queryM = 'SELECT DISTINCT ?y ?w WHERE { ?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.smartcity.madrid.es/mobility/ontology/Metro>. ?y <http://www.smartcity.madrid.es/mobility/ontology/District_Name> "' + selected + '" . ?y <http://www.w3.org/2002/07/owl#sameAs>  ?w . }'
        queryMpq = sparql.prepareQuery(queryM)

        for metro in g.query(queryMpq):
            underList.append(metro.y)
            underListW.append(metro.w) 
            
        # SCROLLS for the results
        widgetM = QWidget(win)
        widgetB = QWidget(win)
        widgetLR = QWidget(win)
        voxM = QVBoxLayout(win)
        voxB = QVBoxLayout(win)
        voxLR = QVBoxLayout(win)
        scrollAreaM = QScrollArea(win)
        scrollAreaB = QScrollArea(win)
        scrollAreaLR = QScrollArea(win)
        
        # Metro Scroll
        txtResultQueryM = QLabel(win)
        voxM.addWidget(txtResultQueryM)
        for i in range(len(underList)):
            txtResultQueryM = QLabel(win)
            if i%2 == 0:
                txtResultQueryM.setText("<a href=\""+ underListW[i] + "\">"+ underList[i] +"</a>")
                txtResultQueryM.setTextFormat(Qt.RichText)
                txtResultQueryM.setTextInteractionFlags(Qt.TextBrowserInteraction)
                txtResultQueryM.setOpenExternalLinks(True)

                voxM.addWidget(txtResultQueryM)

        # Bus Scroll
        txtResultQueryB = QLabel(win)
        voxB.addWidget(txtResultQueryB)
        for i in range(len(busList)):
            txtResultQueryB = QLabel(win)
            txtResultQueryB.setText("<a href=\""+ busListW[i] + "\">"+ busList[i] +"</a>")
            txtResultQueryB.setTextFormat(Qt.RichText)
            txtResultQueryB.setTextInteractionFlags(Qt.TextBrowserInteraction)
            txtResultQueryB.setOpenExternalLinks(True)
            
            voxB.addWidget(txtResultQueryB)
            
        # LightRail Scroll
        txtResultQueryLR = QLabel(win)
        voxLR.addWidget(txtResultQueryLR)
        for i in range(len(lrList)):
            txtResultQueryLR = QLabel(win)
            if i%2 != 0:
                txtResultQueryLR.setText("<a href=\""+ lrListW[i] + "\">"+ lrList[i] +"</a>")
                txtResultQueryLR.setTextFormat(Qt.RichText)
                txtResultQueryLR.setTextInteractionFlags(Qt.TextBrowserInteraction)
                txtResultQueryLR.setOpenExternalLinks(True)

                voxLR.addWidget(txtResultQueryLR)

        widgetM.setLayout(voxM)
        widgetB.setLayout(voxB)
        widgetLR.setLayout(voxLR)

        # ScrollArea Metro
        scrollAreaM.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOn)
        scrollAreaM.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        scrollAreaM.setWidgetResizable(True)
        scrollAreaM.setGeometry(200, 80, 640, 150)
        scrollAreaM.setWidget(widgetM)

        # ScrollArea Bus
        scrollAreaB.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOn)
        scrollAreaB.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        scrollAreaB.setWidgetResizable(True)
        scrollAreaB.setGeometry(200, 80+190, 640, 150)
        scrollAreaB.setWidget(widgetB)
        
        # ScrollArea LightRail
        scrollAreaLR.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOn)
        scrollAreaLR.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
        scrollAreaLR.setWidgetResizable(True)
        scrollAreaLR.setGeometry(200, 80+190+190, 640, 150)
        scrollAreaLR.setWidget(widgetLR)
        
        title = QLabel("Movilidad en el distrito " + selected, win)
        title.setGeometry(200, -13, 280, 80)
        img = QLabel(win)
        path = os.path.join(os.path.dirname(sys.modules[__name__].__file__), 'distritos_madrid.png')
        mapDistritos = QPixmap(path)
        img.setPixmap(mapDistritos)
        img.setGeometry(1, 100, 200, 200)
        titleM = QLabel("Metros:", win)
        titleM.setGeometry(220, 25, 280, 80)
        titleB = QLabel("Buses:", win)
        titleB.setGeometry(220, 25+190, 280, 80)
        titleLR = QLabel("Metros ligeros:", win)
        titleLR.setGeometry(220, 25+190+190, 280, 80)
        button_return.clicked.connect(lambda: Window.buttonReturn())
        win.show()
        win.setGeometry(100, 100, 860, 640)
        win.setWindowTitle('Movilidad Madrid')
        window.hide()
      
    # method for components
    def UiComponents(self):
        global list_widget
        global button_accept

        # creating the widgets
        list_widget = QListWidget(self) 
        button_accept = QPushButton(self)
  
        # setting geometry to the widgets
        list_widget.setGeometry(50, 70, 190, 300)
        button_accept.setGeometry(260, 345, 135, 30)

        # button widget actions
        button_accept.setText("Elegir Distrito")

        # list widget items (Districts in order)
        centro = QListWidgetItem("Centro")
        arganzuela = QListWidgetItem("Arganzuela")
        retiro = QListWidgetItem("Retiro")
        salamanca = QListWidgetItem("Salamanca")
        chamartin = QListWidgetItem("Chamartin")
        tetuan = QListWidgetItem("Tetuan")
        chamberi = QListWidgetItem("Chamberi")
        fuencarral_elpardo = QListWidgetItem("Fuencarral-El Pardo")
        moncloa_aravaca = QListWidgetItem("Moncloa-Aravaca")
        latina = QListWidgetItem("Latina")
        carabanchel = QListWidgetItem("Carabanchel")
        usera = QListWidgetItem("Usera")
        puentedevallecas = QListWidgetItem("Puente de Vallecas")
        moratalaz = QListWidgetItem("Moratalaz")
        ciudadlineal = QListWidgetItem("Ciudad Lineal")
        hortaleza = QListWidgetItem("Hortaleza")
        villaverde = QListWidgetItem("Villaverde")
        villadevallecas = QListWidgetItem("Villa de Vallecas")
        vicalvaro = QListWidgetItem("Vicalvaro")
        sanblas_canillejas = QListWidgetItem("San blas-Canillejas")
        barajas = QListWidgetItem("Barajas")
  
        # adding items to the list widget
        list_widget.addItem(centro)
        list_widget.addItem(arganzuela)
        list_widget.addItem(retiro)
        list_widget.addItem(salamanca)
        list_widget.addItem(chamartin)
        list_widget.addItem(tetuan)
        list_widget.addItem(chamberi)
        list_widget.addItem(fuencarral_elpardo)
        list_widget.addItem(moncloa_aravaca)
        list_widget.addItem(latina)
        list_widget.addItem(carabanchel)
        list_widget.addItem(usera)
        list_widget.addItem(puentedevallecas)
        list_widget.addItem(moratalaz)
        list_widget.addItem(ciudadlineal)
        list_widget.addItem(hortaleza)
        list_widget.addItem(villaverde)
        list_widget.addItem(villadevallecas)
        list_widget.addItem(vicalvaro)
        list_widget.addItem(sanblas_canillejas)
        list_widget.addItem(barajas)
  
        # setting selection mode property
        list_widget.setSelectionMode(QAbstractItemView.SingleSelection)
  
        # creating a label
        label = QLabel("Distritos :", self)
        
        # setting geometry to the label
        label.setGeometry(50, 15, 280, 80)
  
        # making label multi line
        label.setWordWrap(True)
        button_accept.clicked.connect(lambda: Window.buttonClicked(list_widget))
        label.setWordWrap(True)
        ttl1 = QLabel("Movilidad en Madrid", self)
        ttl1.setGeometry(200, -13, 280, 80)
        img = QLabel(self)
        path = os.path.join(os.path.dirname(sys.modules[__name__].__file__), 'distritos_madrid.png')
        mapDistritos = QPixmap(path)
        img.setPixmap(mapDistritos)
        img.setGeometry(300, 100, 200, 200)

# create the instance of our Window
window = Window()

# start the app
sys.exit(App.exec())
import csv
import unicodedata

# READ AREAS_CANINAS_2021-with-links.csv and get the IDDistrict corresponding to a District
def getIdDistrict(district):
    with open('csv/AREAS_CANINAS_2021-with-links.csv', 'r') as f:
        for row in list(csv.reader(f))[1:]:
            if row[2] == removeAccentMark(district.capitalize()):
                return row[1]

def getDistrict(idDistrict):
    with open('csv/AREAS_CANINAS_2021-with-links.csv', 'r') as f:
        for row in list(csv.reader(f))[1:]:
            if row[1] == idDistrict:
                return row[2]

def getIdNeighborhood(neighborhoodName):
    with open('csv/AREAS_CANINAS_2021-with-links.csv', 'r') as f:
        for row in list(csv.reader(f))[1:]:
            if row[5] == removeAccentMark(neighborhoodName.upper()):
                return row[4]

def getNeighborhood(idNeighborhoodName):
    with open('csv/AREAS_CANINAS_2021-with-links.csv', 'r') as f:
        for row in list(csv.reader(f))[1:]:
            if row[4] == idNeighborhoodName:
                return row[5]

def removeAccentMark(text):
    text = ''.join(c for c in unicodedata.normalize('NFD', text) if unicodedata.category(c) != 'Mn')
    return text

def upper(text: str):
    return text.upper()


def isCharacteraDigit(text: str):
    return text.isdigit()

#print('123456789'[-7:])
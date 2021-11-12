from re import template
from starlette.requests import Request
from starlette.responses import HTMLResponse
from KGraphHandler import KGraphHandler
from fastapi import FastAPI
from fastapi.templating import Jinja2Templates

file = "../rdf/recycle_triples-with-links.nt"
handler = KGraphHandler(file=file)
resList = handler.requestTypes()


app = FastAPI()
templates = Jinja2Templates(directory="./")


@app.get("/", response_class=HTMLResponse)
async def home(request: Request):
    return templates.TemplateResponse("index.html", {"request": request, "types": resList})


@app.post("/", response_class=HTMLResponse)
async def home(request: Request):
    form = await request.form()
    # 40.416817, -3.703410 Puerta del sol
    res = handler.requestBins(todo=form.get(
        "types"), lat=40.416817, lon=-3.703410)
    places = []
    for r in res:
        mystr = f'{r[0]}\t-\t{r[1]}'
        newstr = mystr.encode('latin1').decode('utf8')
        percentstr = f'{r[2]}%'
        places.append((newstr, percentstr))
    return templates.TemplateResponse("index.html", {"request": request, "types": resList, "places": places})

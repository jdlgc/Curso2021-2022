const {app,ipcMain, BrowserWindow, globalShortcut, screen, shell} = require('electron')
const fs=require("fs")
const path = require('path')
const $=require("jquery")
let width=1280;
let height=1000;

let mainWindow;
function createWindow () {
    // Create the browser window.
    const mainWindow = new BrowserWindow({
        width: width,
        height: height,
        frame:true,
        icon:path.join(__dirname,"works","logoconContorno.png"),
        webPreferences: {
            nodeIntegration:true,
            enableRemoteModule:true,
            nodeIntegrationInSubFrames:true,
            nodeIntegrationInWorker: true,
            preload: path.join(__dirname, 'static/script.js')
        }
    })
    mainWindow.setResizable(false);
    // and load the index.html of the app.
    mainWindow.loadFile('static/index.html')

    // Open the DevTools.
    // mainWindow.webContents.openDevTools()
    return mainWindow;
}
app.whenReady().then(() => {
    mainWindow = createWindow()
    globalShortcut.register("f2",()=>{
        BrowserWindow.getAllWindows()[0].webContents.openDevTools();
    })

    mainWindow.webContents.setWindowOpenHandler(({ url }) => {
        shell.openExternal(url);
        return { action: 'deny' };
    });
});

// Open the DevTools.
// mainWindow.webContents.openDevTools()

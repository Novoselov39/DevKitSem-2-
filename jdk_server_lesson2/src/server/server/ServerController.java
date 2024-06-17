package server.server;

import server.client.ClientController;
import server.client.ClientGUI;

public class ServerController {
    private ServerView serverView;

    
    ServerWindow serverWindow =new ServerWindow();
    FileStorage fileStorage = new FileStorage();
    
    public Boolean connectUser(ClientController clientController){
        return serverWindow.connect(clientController);
    }
    
    public String getHistory(){
        return fileStorage.read();
    }
    
    public void disconnectUser(ClientController clientController){
        serverWindow.disconnect(clientController);
    }
    
    public void message(String text){
        serverWindow.appendLog(text);
    }

    public ServerWindow getServerWindow() {
        return serverWindow;
    }

    public void setServerWindow(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
    }

    public FileStorage getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public ServerView getServerView() {
        return serverView;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
    }
}

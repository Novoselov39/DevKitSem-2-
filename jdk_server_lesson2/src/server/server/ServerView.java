package server.server;

import server.client.ClientController;
import server.client.ClientGUI;

public interface ServerView {
    void showMessage(String message);
    void disconnect(ClientController clientController);
    Boolean connect(ClientController clientController);
}

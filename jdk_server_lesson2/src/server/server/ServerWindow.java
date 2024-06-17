package server.server;

import server.client.ClientController;
import server.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//класс требуется разделить на GUI, controller и repository (смотри схему проекта)
public class ServerWindow extends JFrame implements ServerView {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final String LOG_PATH = "src/server/log.txt";

    private ServerController serverController;
    List<ClientController> clientControllerList;


    JButton btnStart, btnStop;
    JTextArea log;
    boolean work;

    FileStorage fileStorage = new FileStorage();
    ClientController clientController = new ClientController();

    public ServerWindow(){
        clientControllerList = new ArrayList<>();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

//    public boolean connectUser(ClientGUI clientGUI){
//        if (!work){
//            return false;
//        }
//        clientControllerList.add(clientGUI);
//        return true;
//    }

    public String getLog() {
        return fileStorage.read();
    }

//    public void disconnectUser(ClientGUI clientGUI){
//        clientControllerList.remove(clientGUI);
//        if (clientGUI != null){
//            clientGUI.disconnectedFromServer();
//        }
//    }

    public void message(String text){
        if (!work){
            return;
        }
        appendLog(text);
        answerAll(text);
        fileStorage.save(text);
    }

    private void answerAll(String text){
        for (ClientController clientController: clientControllerList){
            clientController.answerFromServer(text);
        }
    }

//    private void saveInLog(String text){
//        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
//            writer.write(text);
//            writer.write("\n");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private String readLog(){
//        StringBuilder stringBuilder = new StringBuilder();
//        try (FileReader reader = new FileReader(LOG_PATH)){
//            int c;
//            while ((c = reader.read()) != -1){
//                stringBuilder.append((char) c);
//            }
//            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
//            return stringBuilder.toString();
//        } catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void appendLog(String text){
        log.append(text + "\n");
    }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void disconnect(ClientController clientController) {
        clientControllerList.remove(clientController);
        if (clientController != null){
            clientController.disconnectedFromServer();
        }
    }

    @Override
    public Boolean connect(ClientController clientController) {
        if (!work){
            return false;
        }
        clientControllerList.add(clientController);
        return true;

    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (work){
                    appendLog("Сервер уже был запущен");
                } else {
                    work = true;
                    appendLog("Сервер запущен!");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!work){
                    appendLog("Сервер уже был остановлен");
                } else {
                    work = false;
                    while (!clientControllerList.isEmpty()){
                        disconnect(clientControllerList.get(clientControllerList.size()-1));
                    }
                    appendLog("Сервер остановлен!");
                }
            }
        });


        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    public List<ClientController> getClientControllerList() {
        return clientControllerList;
    }

    public void setClientControllerList(List<ClientController> clientControllerList) {
        this.clientControllerList = clientControllerList;
    }

    public ServerController getServerController() {
        return serverController;
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }
}

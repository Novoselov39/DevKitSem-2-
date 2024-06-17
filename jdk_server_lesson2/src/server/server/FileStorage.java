package server.server;

import java.io.FileReader;
import java.io.FileWriter;

public class FileStorage implements Repository {
    public static final String LOG_PATH = "src/server/log.txt";
    @Override
    public void save(String massage) {

        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(massage);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String read() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH)){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}

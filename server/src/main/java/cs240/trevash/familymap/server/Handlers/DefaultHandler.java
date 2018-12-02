package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        Headers h = t.getResponseHeaders();

        String path = t.getRequestURI().getPath();
        String[] pathParams = path.split("/");
        String appendedPath = "";
        if (pathParams.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < pathParams.length; i++) {
                sb.append(pathParams[i]);
                sb.append("/");
            }
            appendedPath = sb.toString();
        }

        String line;
        String totalPath = "server/web/";
        if (appendedPath.length() > 0) {
            totalPath = totalPath + appendedPath;
        }
        else totalPath = totalPath + "index.html";
        File newFile = new File(totalPath);

        /*
        try {
            System.out.println("Server file collected");
            System.out.println("Server name: " + newFile.getName());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                resp += line;
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        */

        //h.add("Content-Type", "application/html");
        t.sendResponseHeaders(200, 0);
        Path filePath = FileSystems.getDefault().getPath(newFile.getPath());
        Files.copy(filePath, t.getResponseBody());
        OutputStream os = t.getResponseBody();
        //os.write(resp.getBytes());
        os.close();
    }
}
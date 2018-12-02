package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cs240.trevash.familymap.server.Services.Fill;
import cs240.trevash.familymap.shared.Objects.ClearFillResult;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class FillHandler implements HttpHandler {
    private Encoder encode = new Encoder();
    private Fill fill = new Fill();

    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                String response;
                int generations = 4;
                String path = exchange.getRequestURI().getPath();
                String[] pathParams = path.split("/");
                if (pathParams.length > 2) {
                    if (pathParams.length > 3) {
                        generations = Integer.parseInt(pathParams[3]);
                    }
                    String userName = pathParams[2];

                    ClearFillResult result = fill.fill(userName, generations);


                    String data = encode.createJSON(result);

                    exchange.sendResponseHeaders(201, 0);

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(data, respBody);
                    respBody.close();
                }
                else throw new IOException("Must have a pathParameter showing the userName request is for");
            }
            else throw new IOException("Must be a post method on fill requests");
        }
        catch (IOException e) {
            e.printStackTrace();
            String data = encode.createJSON(new ClearFillResult("Failed with error" + e.getMessage()));

            exchange.sendResponseHeaders(500, 0);

            OutputStream respBody = exchange.getResponseBody();
            writeString(data, respBody);
            respBody.close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}

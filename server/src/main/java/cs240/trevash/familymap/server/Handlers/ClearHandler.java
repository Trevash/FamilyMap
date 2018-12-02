package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cs240.trevash.familymap.server.Services.Clear;
import cs240.trevash.familymap.shared.Objects.ClearFillResult;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class ClearHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Encoder encode = new Encoder();
                Clear clear = new Clear();

                ClearFillResult clearDB = clear.clear();

                String data = encode.createJSON(clearDB);

                exchange.sendResponseHeaders(201, 0);

                OutputStream respBody = exchange.getResponseBody();
                writeString(data, respBody);
                respBody.close();
            }
            else throw new IOException();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cs240.trevash.familymap.server.Services.Load;
import cs240.trevash.familymap.shared.Objects.LoadRequest;
import cs240.trevash.familymap.shared.Objects.MessageObject;
import cs240.trevash.familymap.shared.Services.Decoder;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class LoadHandler implements HttpHandler {
    private Encoder encoder = new Encoder();
    private Decoder decoder = new Decoder();
    private Load load = new Load();

    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                Object reqFormatted = decoder.decodeJSON(reqData, LoadRequest.class);
                LoadRequest reqInfo = (LoadRequest) reqFormatted;

                MessageObject loadResult = load.Load(reqInfo);

                String data = encoder.createJSON(loadResult);

                exchange.sendResponseHeaders(201, 0);

                OutputStream respBody = exchange.getResponseBody();
                writeString(data, respBody);
                respBody.close();
            }
            else throw new IOException("");
        }
        catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, 0);
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

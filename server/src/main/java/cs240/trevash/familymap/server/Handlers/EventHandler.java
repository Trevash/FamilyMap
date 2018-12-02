package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cs240.trevash.familymap.server.Services.EventRequest;
import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Objects.EventsResult;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class EventHandler implements HttpHandler {
    private Encoder encode = new Encoder();
    private EventRequest eventRequest = new EventRequest();

    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String response;
                    String path = exchange.getRequestURI().getPath();
                    String[] pathParams = path.split("/");

                    if (pathParams.length > 2) {
                        String eventID = pathParams[2];
                        Event event = eventRequest.getEvent(eventID, authToken);

                        if (event == null) {
                            event = new Event("No person in database with that id");
                            exchange.sendResponseHeaders(404, 0);
                        }
                        else if (event.getMessage() != null) {
                            exchange.sendResponseHeaders(403, 0);
                        }
                        else exchange.sendResponseHeaders(200, 0);

                        response = encode.createJSON(event);
                    }
                    else {
                        EventsResult events = eventRequest.getAllByUser(authToken);
                        response = encode.createJSON(events);
                        exchange.sendResponseHeaders(200, 0);
                    }


                    OutputStream respBody = exchange.getResponseBody();
                    writeString(response, respBody);
                    respBody.close();
                }
                else throw new IOException("No authorization header. Requires authToken to make this request");
            }
            else throw new IOException("Not a get request, must be a get request with any /person requests");
        }
        catch (IOException e) {
            e.printStackTrace();
            RegisterLoginResult regResult = new RegisterLoginResult(e.getMessage());

            String data = encode.createJSON(regResult);

            exchange.sendResponseHeaders(500, 0);

            OutputStream respBody = exchange.getResponseBody();
            writeString(data, respBody);
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


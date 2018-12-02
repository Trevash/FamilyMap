package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cs240.trevash.familymap.server.Services.PersonRequest;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.PersonsResult;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class PersonHandler implements HttpHandler {
    private Encoder encode = new Encoder();
    private PersonRequest personRequest = new PersonRequest();

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
                        System.out.println("Individual person requested for");
                        String personID = pathParams[2];
                        Person person = personRequest.getPerson(personID, authToken);

                        if (person == null) {
                            person = new Person("No person in database with that id");
                            exchange.sendResponseHeaders(404, 0);
                        }
                        else if (person.getError() != null) {
                            exchange.sendResponseHeaders(403, 0);
                        }
                        else exchange.sendResponseHeaders(200, 0);

                        response = encode.createJSON(person);
                    }
                    else {

                        PersonsResult persons = personRequest.getAllByUser(authToken);

                        response = encode.createJSON(persons);
                        exchange.sendResponseHeaders(200, 0);
                    }

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(response, respBody);
                    respBody.close();
                }
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


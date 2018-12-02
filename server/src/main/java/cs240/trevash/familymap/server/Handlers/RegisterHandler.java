package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import cs240.trevash.familymap.server.Services.Register;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.shared.Services.Decoder;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class RegisterHandler implements HttpHandler {
    private Encoder encode = new Encoder();
    private Decoder decode = new Decoder();
    private Register myReg = new Register();

    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                Object reqFormatted = decode.decodeJSON(reqData, RegisterRequest.class);
                RegisterRequest reqInfo = (RegisterRequest)reqFormatted;

                RegisterLoginResult regResult = myReg.RegisterUser(reqInfo);

                String data = encode.createJSON(regResult);

                exchange.sendResponseHeaders(201, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(data, respBody);
                respBody.close();


                System.out.println(HttpURLConnection.HTTP_CREATED);
                //}
            }
            else throw new IOException("Request must be a post method");
        } catch (IOException e) {
            e.printStackTrace();
            RegisterLoginResult regResult = new RegisterLoginResult("IOException with error: " + e.getMessage());
            String data = encode.createJSON(regResult);
            exchange.sendResponseHeaders(201, 0);
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
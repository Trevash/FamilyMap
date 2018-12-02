package cs240.trevash.familymap.server.Handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cs240.trevash.familymap.server.Services.Login;
import cs240.trevash.familymap.shared.Objects.LoginRequest;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Services.Decoder;
import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by trevash on 2/28/18.
 */

public class LoginHandler implements HttpHandler {
    public void handle(HttpExchange exchange) {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Encoder encode = new Encoder();
                Decoder decode = new Decoder();
                Login login = new Login();
                Headers reqHeaders = exchange.getRequestHeaders();

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                Object reqFormatted = decode.decodeJSON(reqData, LoginRequest.class);
                LoginRequest reqInfo = (LoginRequest)reqFormatted;

                RegisterLoginResult regResult = login.LoginUser(reqInfo);

                String data = encode.createJSON(regResult);

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


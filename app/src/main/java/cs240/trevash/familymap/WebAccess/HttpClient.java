package cs240.trevash.familymap.WebAccess;

        import android.util.Log;

        import java.io.ByteArrayOutputStream;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

        import cs240.trevash.familymap.shared.Objects.EventsResult;
        import cs240.trevash.familymap.shared.Objects.LoginRequest;
        import cs240.trevash.familymap.shared.Objects.PersonsResult;
        import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
        import cs240.trevash.familymap.shared.Objects.RegisterRequest;
        import cs240.trevash.familymap.shared.Services.Decoder;
        import cs240.trevash.familymap.shared.Services.Encoder;

/**
 * Created by rodham on 3/5/2016.
 */
public class HttpClient {

    Decoder mDecoder = new Decoder();
    Encoder mEncoder = new Encoder();

    public String getUrl(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                return responseBodyData;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }

        return null;
    }

    public RegisterLoginResult login(URL url, LoginRequest loginRequest) {
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.connect();

            String decodedRequest = mEncoder.createJSON(loginRequest);

            byte[] outputInBytes = decodedRequest.getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write( outputInBytes );
            os.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                RegisterLoginResult registerLoginResult = (RegisterLoginResult) mDecoder.decodeJSON(responseBodyData, RegisterLoginResult.class);
                return registerLoginResult;
            }
            else return new RegisterLoginResult("Wasn't a successful response, response code was: " + connection.getResponseCode());
        }
        catch(Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
            return new RegisterLoginResult("Failed HTTP Request with error: " + e.getMessage());
        }
    }

    public RegisterLoginResult register(URL url, RegisterRequest registerRequest) {
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.connect();

            String decodedRequest = mEncoder.createJSON(registerRequest);

            byte[] outputInBytes = decodedRequest.getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write( outputInBytes );
            os.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                RegisterLoginResult registerLoginResult = (RegisterLoginResult) mDecoder.decodeJSON(responseBodyData, RegisterLoginResult.class);
                return registerLoginResult;
            }
            else return new RegisterLoginResult("Wasn't a successful response, response code was: " + connection.getResponseCode());
        }
        catch(Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
            return new RegisterLoginResult("Failed HTTP Request with error: " + e.getMessage());
        }
    }

    public PersonsResult getPersons(URL url, String authToken) {
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", authToken);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                PersonsResult result = (PersonsResult) mDecoder.decodeJSON(responseBodyData, PersonsResult.class);
                return result;
            }
            else return new PersonsResult("Wasn't a successful response, response code was: " + connection.getResponseCode());
        }
        catch(Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
            return new PersonsResult("Failed HTTP Request with error: " + e.getMessage());
        }
    }

    public EventsResult getEvents(URL url, String authToken) {
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", authToken);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                EventsResult result = (EventsResult) mDecoder.decodeJSON(responseBodyData, EventsResult.class);
                return result;
            }
            else return new EventsResult("Wasn't a successful response, response code was: " + connection.getResponseCode());
        }
        catch(Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
            return new EventsResult("Failed HTTP Request with error: " + e.getMessage());
        }
    }
}

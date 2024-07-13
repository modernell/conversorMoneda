package controller;

/**
 *
 * @author 
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connection {
    public static String FetchHTTP(String uriResource) {
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriResource))
                    .build();
            HttpResponse<String> response;
        try{
            response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Respuesta de la API: " + response.body());
            return response.body();

        } catch (NullPointerException e) {
            System.err.println("Se produjo una NullPointerException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Se produjo una IOException: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Se produjo una InterruptedException: " + e.getMessage());
        }

        return "http_response";
    }
}

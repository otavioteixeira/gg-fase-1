package br.com.zup.ProjetoDesafio4;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class ProjetoDesafio4Application {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private final HttpClient httpClientBearer = HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            "user",
                            "password".toCharArray());
                }
            })
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {
        ProjetoDesafio4Application obj = new ProjetoDesafio4Application();
        obj.sendPOST();
        obj.sendDELETE();
        obj.sendBEARER();
    }

    private void sendPOST() throws IOException, InterruptedException {
        System.out.println("Request POST");
        String json = new StringBuilder()
                .append("{")
                .append("\"id\":\"123\",")
                .append("\"field\":\"testing\"")
                .append("}").toString();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("https://httpbin.org/post"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        System.out.println(response.body());

    }

    private void sendDELETE() throws IOException, InterruptedException {
        System.out.println("Request DELETE");
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("https://httpbin.org/delete"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        System.out.println(response.body());
    }

    private void sendBEARER() throws IOException, InterruptedException {
        System.out.println("Request BEARER");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://httpbin.org/bearer"))
                .header("Authorization", "Bearer my secret-token")
                .build();

        HttpResponse<String> response = httpClientBearer.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        System.out.println(response.body());
    }
}

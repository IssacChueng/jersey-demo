package com.vivian.jerseyasync.client;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class JerseyClient {
    public Object getIndexAsync() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080");
        Invocation.Builder builder = webTarget.path("/index").request();

        List<Future<Response>> responseList = new ArrayList<>(10);
        AsyncInvoker async = builder.async();

        for (int i = 0; i < 10; i++) {
            Future<Response> responseFuture = async.get();
            responseList.add(responseFuture);
        }

        for (Future<Response> responseFuture:
        responseList){
            if (responseFuture.isDone()) {
                try {
                    Response response = responseFuture.get();
                    Response.StatusType statusInfo = response.getStatusInfo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.printf("1");
        return null;
    }

    public static void main(String[] args) {
        JerseyClient jerseyClient = new JerseyClient();
        jerseyClient.getIndexAsync();
    }
}

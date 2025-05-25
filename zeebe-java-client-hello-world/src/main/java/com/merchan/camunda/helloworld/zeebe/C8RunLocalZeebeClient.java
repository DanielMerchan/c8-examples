package com.merchan.camunda.helloworld.zeebe;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static java.net.http.HttpClient.newHttpClient;

/**
 * Zeebe Client for using while coding for C8Run instances
 * @author dmerchang
 */
public final class C8RunLocalZeebeClient implements ZeebeClientFactory {

    /**
     * Default constructor
     */
    public C8RunLocalZeebeClient() {
    }

    /**
     * Create a Zeebe Client for C8Run
     * @return ZeebeClient
     */
    @Override
    public ZeebeClient create() {
        final Optional<String> OPERATE_SESSION_ID = C8SessionCookieFetcher.fetchSessionCookie();
        ZeebeClientBuilder zeebeClientBuilder = ZeebeClient.newClientBuilder();
        zeebeClientBuilder.usePlaintext();
        if (OPERATE_SESSION_ID.isPresent()) {
            return zeebeClientBuilder
                    .withChainHandlers(
                            (request, producer, scope, chain, callback) -> {
                                request.setHeader("Cookie", "OPERATE-SESSION=" + OPERATE_SESSION_ID);
                                chain.proceed(request, producer, scope, callback);
                            }).build();
        }
        return zeebeClientBuilder.build();
    }

    /**
     * Aux method to retrieve the cookie from the authenticated session
     */
    private static class C8SessionCookieFetcher {
        public static Optional<String> fetchSessionCookie() {
            try (HttpClient httpClient = newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8080/api/login?username=demo&password=demo"))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                List<String> setCookies = response.headers().allValues("set-cookie");
                return setCookies.stream()
                        .filter(cookie -> cookie.startsWith("OPERATE-SESSION="))
                        .map(cookie -> cookie.split(";", 2)[0])
                        .findFirst();
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    }
}

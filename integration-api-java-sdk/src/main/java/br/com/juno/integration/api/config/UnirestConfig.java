package br.com.juno.integration.api.config;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import br.com.juno.integration.api.base.exception.ErrorDetail;
import br.com.juno.integration.api.base.exception.JunoApiException;
import br.com.juno.integration.api.services.JunoApiManager;
import br.com.juno.integration.api.utils.JacksonUtils;
import kong.unirest.Config;
import kong.unirest.GenericType;
import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestSummary;
import kong.unirest.HttpResponse;
import kong.unirest.Interceptor;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.UnirestParsingException;

public final class UnirestConfig {

    public static void configure() {
        Unirest.config().setDefaultHeader("X-API-Version", "2");
        Unirest.config().setObjectMapper(new ObjectMapper() {

            @Override
            public String writeValue(Object value) {
                try {
                    return JacksonUtils.getObjectMapper().writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new JunoApiException(e);
                }
            }

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return JacksonUtils.getObjectMapper().readValue(value, valueType);
                } catch (JsonProcessingException e) {
                    throw new JunoApiException(e);
                }
            }

            @Override
            public <T> T readValue(String value, GenericType<T> genericType) {
                try {
                    return JacksonUtils.getObjectMapper().readValue(value, new TypeReference<T>() {

                        @Override
                        public Type getType() {
                            return genericType.getType();
                        }
                    });
                } catch (JsonProcessingException e) {
                    throw new JunoApiException(e);
                }
            }
        });

        Unirest.config().interceptor(new Interceptor() {

            @Override
            public void onRequest(HttpRequest<?> request, Config config) {
                // TODO: Find a better way to exclude authorization call from the chain
                if (!request.getUrl().contains("/oauth/token")) {
                    request.headers(JunoApiManager.getAuthorizationService().getAuthorizationHeader());
                }
            }

            @Override
            public void onResponse(HttpResponse<?> response, HttpRequestSummary request, Config config) {

                if (!response.isSuccess()) {
                    UnirestParsingException parsingException = response.getParsingError().orElse(null);
                    if (parsingException != null) {
                        ErrorDetail errorDetail;
                        try {
                            errorDetail = JacksonUtils.getObjectMapper().readValue(parsingException.getOriginalBody(), ErrorDetail.class);
                        } catch (JsonProcessingException e) {
                            throw new JunoApiException("Failed to read error response from Juno. Please contact the support team.", e);
                        }
                        throw new JunoApiException(errorDetail);
                    } 
                    else {
                    	throw new JunoApiException(response.mapError(ErrorDetail.class)); 
                    }
                }

            }

            @Override
            public HttpResponse<?> onFail(Exception e, HttpRequestSummary request, Config config) {
                if (e instanceof JunoApiException) {
                    throw (JunoApiException)e;
                }
                throw new JunoApiException(e);
            }

        });
    }
}

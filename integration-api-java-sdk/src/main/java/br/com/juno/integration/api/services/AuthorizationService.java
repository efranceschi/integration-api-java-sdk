package br.com.juno.integration.api.services;

import static br.com.juno.integration.api.services.JunoApiManager.CONTENT_TYPE_HEADER;
import static br.com.juno.integration.api.utils.ResponseUtils.validateSuccess;

import java.util.HashMap;
import java.util.Map;

import br.com.juno.integration.api.model.AuthorizationToken;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public final class AuthorizationService extends BaseService {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private AuthorizationToken authorizationToken;

    public synchronized String getToken() {
        if (authorizationToken == null || authorizationToken.isExpired(JunoApiConfig.TOKEN_TIMEOUT)) {
            refresh();
        }

        return authorizationToken.getAccessToken();
    }

    public Map<String, String> getAuthorizationHeader() {
        Map<String, String> authorizationHeader = new HashMap<>();
        authorizationHeader.put(AUTHORIZATION_HEADER, BEARER + getToken());
        return authorizationHeader;
    }

    private void refresh() {
        HttpResponse<AuthorizationToken> response = //
                Unirest.post(JunoApiManager.config().getAuthorizationEndpoint() + "/oauth/token") //
                        .basicAuth(JunoApiManager.config().getClientId(), JunoApiManager.config().getClientSecret()) //
                        .header(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded") //
                        .field("grant_type", "client_credentials") //
                        .asObject(AuthorizationToken.class);

        validateSuccess(response);

        authorizationToken = response.getBody();
    }
}
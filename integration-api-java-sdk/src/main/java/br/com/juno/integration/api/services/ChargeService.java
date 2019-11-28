package br.com.juno.integration.api.services;

import static br.com.juno.integration.api.services.JunoApiManager.X_RESOURCE_TOKEN;
import static br.com.juno.integration.api.utils.ResponseUtils.validateSuccess;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import br.com.juno.integration.api.base.exception.JunoApiException;
import br.com.juno.integration.api.model.Charge;
import br.com.juno.integration.api.model.LabeledEnum;
import br.com.juno.integration.api.services.request.charge.ChargeCreateRequest;
import br.com.juno.integration.api.services.request.charge.ChargeListRequest;
import br.com.juno.integration.api.services.response.Responses;
import br.com.juno.integration.api.utils.JacksonUtils;
import kong.unirest.GenericType;
import kong.unirest.GetRequest;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class ChargeService extends BaseService {

    ChargeService() {
        // NTD
    }

    public List<Charge> create(ChargeCreateRequest request) {
        String requestBody;
        try {
            requestBody = JacksonUtils.getObjectMapper().writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new JunoApiException(e);
        }

        System.out.println(requestBody);

        HttpResponse<Resources<Resource<br.com.juno.integration.api.model.Charge>>> httpResponse = Unirest.post( //
                JunoApiManager.config().getEnvironmentUrl() + "/api-integration/charges") //
                .headers(JunoApiManager.getAuthorizationService().getAuthorizationHeader()) //
                .header(X_RESOURCE_TOKEN, request.getResourceToken()) //
                .body(requestBody) //
                .asObject(new GenericType<Resources<Resource<br.com.juno.integration.api.model.Charge>>>() { //
                    // NTD
                });//

        validateSuccess(httpResponse);

        return new Responses<>(httpResponse.getBody()).getAbsoluteContent();
    }

    public Responses<Charge> list(ChargeListRequest request) {
        List<Responses<Charge>> responsePages = new LinkedList<>();

        Responses<Charge> currentPage = getFirstPage(request.getResourceToken());

        if (request.getTotalPages() > 1) {
            for (int pageIndex = 1; pageIndex < request.getTotalPages(); pageIndex++) {
                responsePages.add(currentPage);
                currentPage = listNextPage(currentPage);
            }
        }

        responsePages.add(currentPage);

        return new Responses<>(responsePages, request.getResourceToken());
    }

    public Responses<Charge> listNextPage(Responses<Charge> currentPage) {
        Responses<Charge> responses = null;

        if (StringUtils.isNotBlank(currentPage.getHrefNext())) {
            responses = getPage(currentPage.getResourceToken(), currentPage.getHrefNext());
        }

        return responses != null ? responses : new Responses<>(new LinkedList<>(), currentPage.getResourceToken());
    }

    private Responses<Charge> getPage(String resourceToken, String hateoasLink) {
        GetRequest httpRequest = Unirest.get(hateoasLink) //
                .headers(JunoApiManager.getAuthorizationService().getAuthorizationHeader()) //
                .header(X_RESOURCE_TOKEN, resourceToken);

        return exchange(httpRequest);
    }

    private Responses<Charge> getPage(String resourceToken) {
        GetRequest httpRequest = Unirest.get(JunoApiManager.config().getEnvironmentUrl() + "/api-integration/charges") //
                .headers(JunoApiManager.getAuthorizationService().getAuthorizationHeader()) //
                .header(X_RESOURCE_TOKEN, resourceToken);

        populateParameters(httpRequest);

        return exchange(httpRequest);
    }

    private Responses<Charge> getFirstPage(String resourceToken) {
        return getPage(resourceToken);
    }

    private Responses<Charge> exchange(GetRequest httpRequest) {
        HttpResponse<Resources<Resource<Charge>>> httpResponse = httpRequest.asObject(new GenericType<Resources<Resource<Charge>>>() {
            // NTD
        });

        validateSuccess(httpResponse);

        return new Responses<>(httpResponse.getBody(), httpRequest.getHeaders().getFirst(X_RESOURCE_TOKEN));
    }

    private void populateParameters(HttpRequest<GetRequest> httpRequest) {
        try {
            Field[] fields = this.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.get(this) != null) {
                    if (field.getType().isEnum()) {
                        httpRequest.queryString(field.getName(), ((LabeledEnum)field.get(this)).getLabel());
                    } else {
                        httpRequest.queryString(field.getName(), field.get(this));
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new JunoApiException(e);
        }
    }

}

package br.com.juno.integration.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import br.com.juno.integration.api.model.response.Response;
import br.com.juno.integration.api.model.response.Responses;
import br.com.juno.test.AbstractTest;

public class BankTest extends AbstractTest {

    @Test
    public void jsonToObject() throws Exception {
        Response<Bank> res = new Response<>(getObjectMapper().readValue(findOne(), new TypeReference<Resource<Bank>>() {
            // NTD
        }));

        assertEquals(null, res.getHrefSelf());

        Bank bank = res.getContent();

        assertEquals(BANK_NUMBER, bank.getNumber());
        assertEquals(BANK_NAME, bank.getName());
    }

    @Test
    public void jsonToCollection() throws Exception {
        Responses<Bank> res = new Responses<>(getObjectMapper().readValue(findAll(), new TypeReference<Resources<Resource<Bank>>>() {
            // NTD
        }));

        assertEquals("https://sandbox.boletobancario.com/api-integration/data/banks", res.getHrefSelf());
        assertEquals(null, res.getHrefNext());
        assertEquals(null, res.getHrefPrevious());

        List<Response<Bank>> list = res.getContent();

        assertEquals(null, list.get(0).getHrefSelf());
        assertEquals("001", list.get(0).getContent().getNumber());
        assertEquals("Banco do Brasil", list.get(0).getContent().getName());

        assertEquals(null, list.get(1).getHrefSelf());
        assertEquals("104", list.get(1).getContent().getNumber());
        assertEquals("Caixa Econômica Federal", list.get(1).getContent().getName());
    }

    private String findOne() {
        return "{\"number\": \"104\",\"name\": \"Caixa Econômica Federal\"}";
    }

    private String findAll() {
        return "{\"_embedded\": {\"banks\": [{\"number\": \"001\",\"name\": \"Banco do Brasil\"},{\"number\": \"104\",\"name\": \"Caixa Econômica Federal\"}]},\"_links\": {\"self\": {\"href\": \"https://sandbox.boletobancario.com/api-integration/data/banks\"}}}";
    }
}
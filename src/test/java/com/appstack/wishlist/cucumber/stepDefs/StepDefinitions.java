package com.appstack.wishlist.cucumber.stepDefs;

import com.appstack.wishlist.adapter.web.controller.dto.WishlistDetailResponse;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.application.usecase.ViewWishlistUseCase;
import com.appstack.wishlist.cucumber.CucumberSpringConfiguration;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;

@CucumberContextConfiguration
public class StepDefinitions extends CucumberSpringConfiguration {

    @MockBean
    private ViewWishlistUseCase viewWishlistUseCase;
    private ResponseEntity<?> response;
    private ResponseEntity<Void> responseDelete;
    private String wishLisId;
    private String productId;
    private String customerId;
    String url = "/wishlists";

    @Before
    public void setup() {

    }

    @When("eu enviar uma solicitação para criar uma nova wishlist com os seguintes dados")
    public void euEnviarUmaSolicitacaoParaCriarUmaNovaWishlist(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().getFirst();
        String customerId = data.get("customerId");
        String listName = data.get("listName");
        String privacyStatus = data.get("privacyStatus");
        WishlistRequest request = new WishlistRequest(customerId, listName, PrivacyStatusEnum.valueOf(privacyStatus));
        response = testRestTemplate.postForEntity(url, request, WishlistResponse.class);
    }

    @Then("a resposta deve ter status 201 CREATED")
    public void aRespostaDeveTerStatusCreated() {
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @And("uma wishlist com customerId {string}, nome da lista {string} e status de privacidade {string} deve ser retornada")
    public void umaWishlistComCustomerIdENomeDaListaEDStatusDePrivacidadeDeveSerRetornada(String customerId, String listName, String privacyStatus) {
        WishlistResponse responseBody = (WishlistResponse) response.getBody();
        assert responseBody != null;
        Assertions.assertEquals(customerId, responseBody.customerId());
        Assertions.assertEquals(listName, responseBody.listName());
        Assertions.assertEquals(PrivacyStatusEnum.valueOf(privacyStatus), responseBody.privacyStatus());
        Assertions.assertNotNull(responseBody.createdAt());
        Assertions.assertNotNull(responseBody.updatedAt());
    }

    @Given("que eu tenho uma wishlist existente com ID {string} contendo o produto com ID {string}")
    public void queEuTenhoUmaWishlistExistenteComIDContendoOProdutoComID(String wishlistId, String productId) {
       this.wishLisId = wishlistId;
       this.productId = productId;
    }

    @When("eu enviar uma solicitação para remover o produto com ID {string} da wishlist {string}")
    public void euEnviarUmaSolicitacaoParaRemoverOProdutoComIDDaWishlist(String productId, String wishlistId) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        responseDelete = testRestTemplate.exchange(
                url + "/" + wishlistId + "/products/" + productId,
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
    }

    @Then("a resposta deve ter status {int} NO CONTENT")
    public void aRespostaDeveTerStatus204NoContent(int statusCode) {
        Assertions.assertEquals(statusCode, responseDelete.getStatusCode().value());
    }


    @Given("que eu tenho wishlists existentes para o customerId {string}")
    public void queEuTenhoWishlistsExistentesParaOCustomerId(String customerId) {
        this.customerId = customerId;

        WishlistDetailResponse wishlist1 = new WishlistDetailResponse("668cbee6bdb70c05d702f4e4",
                customerId,
                "My List 1", PrivacyStatusEnum.PRIVACY,
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now());

        Mockito.when(viewWishlistUseCase.execute(anyString()))
                .thenReturn(List.of(wishlist1));
    }

    @When("eu enviar uma solicitação para visualizar todas as minhas wishlists")
    public void euEnviarUmaSolicitacaoParaVisualizarTodasAsMinhasWishlists() {

        response = testRestTemplate.exchange(
                url + "/customers/" + customerId,
                HttpMethod.GET,
                null,
                ArrayList.class
        );
    }

    @Then("a resposta deve ter status {int} OK")
    public void aRespostaDeveTerStatus200Ok(int statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCode().value());
    }

    @And("todas as minhas wishlists devem ser retornadas")
    public void todasAsMinhasWishlistsDevemSerRetornadas() {
        Assertions.assertNotNull(response.getBody());
    }
}

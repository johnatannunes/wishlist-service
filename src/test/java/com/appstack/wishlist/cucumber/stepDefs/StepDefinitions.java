package com.appstack.wishlist.cucumber.stepDefs;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.cucumber.CucumberSpringConfiguration;
import com.appstack.wishlist.cucumber.context.WishlistFeatureContext;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

@CucumberContextConfiguration
public class StepDefinitions extends CucumberSpringConfiguration {

    private final WishlistFeatureContext testContext;
    private WishlistRequest wishlistRequest;
    private final String urlBase = "/wishlists";
    private ResponseEntity<?> response;
    private ProductRequest productRequest;

    public StepDefinitions(WishlistFeatureContext context) {
        this.testContext = context;
    }

    /*
       Scenario: Criar uma Wishlist para um cliente
     */

    @Dado("que o cliente com ID {string} ainda não possui uma wishlist criada")
    public void que_o_cliente_com_id_ainda_nao_possui_uma_wishlist_criada(String customerId) {
        testContext.setCustomerId(customerId);
    }

    @Quando("o cliente solicita a criação de uma nova wishlist")
    public void o_cliente_solicita_a_criacao_de_uma_nova_wishlist() {
        wishlistRequest = new WishlistRequest(testContext.getCustomerId(),"MyList", PrivacyStatusEnum.PRIVACY);
    }

    @Entao("uma nova wishlist deve ser criada para o cliente")
    public void uma_nova_wishlist_deve_ser_criada_para_o_cliente() {
        response = testRestTemplate.postForEntity(urlBase, wishlistRequest, WishlistResponse.class);
    }

    @E("a wishlist deve estar vazia")
    public void a_wishlist_deve_estar_vazia() {
        testContext.setWishlistResponse(parseResponseBodyToWishlistResponse());
        Assertions.assertNull(testContext.getWishlistResponse().products());
    }

    @E("a wishlist deve pertencer ao cliente com ID {string}")
    public void a_wishlist_deve_pertencer_ao_cliente_com_id(String customerId) {
        Assertions.assertEquals(testContext.getWishlistResponse().customerId(), customerId);
    }

    /*
       Scenario: Adicionar um produto na Wishlist do cliente
     */

    @Dado("que o cliente tem uma wishlist")
    public void que_o_cliente_tem_uma_wishlist() {
        Assertions.assertNotNull(testContext.getWishlistResponse());
    }

    @Quando("ele adicionar o produto {string} à sua wishlist")
    public void ele_adicionar_o_produto_a_sua_wishlist(String productId) {
        productRequest = new ProductRequest(productId);
    }

    @Entao("o produto deve ser adicionado com sucesso à wishlist")
    public void o_produto_deve_ser_adicionado_com_sucesso_a_wishlist() {
        final String url = urlBase
                .concat("/")
                .concat(testContext.getWishlistResponse().id())
                .concat("/")
                .concat("products");
        response = testRestTemplate.postForEntity(url, productRequest, WishlistResponse.class);
    }

    @E("a wishlist deve conter o produto {string}")
    public void a_wishlist_deve_conter_o_produto(String productId) {
        testContext.setWishlistResponse(parseResponseBodyToWishlistResponse());
        Assertions.assertNotNull(testContext.getWishlistResponse().products());
        Assertions.assertEquals(testContext.getWishlistResponse().products().getFirst().id(), productId);
    }

    private WishlistResponse parseResponseBodyToWishlistResponse() {
        assert response != null;
        WishlistResponse responseBody = (WishlistResponse) response.getBody();
        assert responseBody != null;
        return responseBody;
    }
}

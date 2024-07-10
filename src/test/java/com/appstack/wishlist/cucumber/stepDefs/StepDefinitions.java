package com.appstack.wishlist.cucumber.stepDefs;

import com.appstack.wishlist.adapter.web.controller.dto.ProductRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistDetailResponse;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistRequest;
import com.appstack.wishlist.adapter.web.controller.dto.WishlistResponse;
import com.appstack.wishlist.cucumber.CucumberSpringConfiguration;
import com.appstack.wishlist.cucumber.context.WishlistFeatureContext;
import com.appstack.wishlist.domain.enums.PrivacyStatusEnum;
import com.appstack.wishlist.exception.MessageExceptionResponse;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@CucumberContextConfiguration
public class StepDefinitions extends CucumberSpringConfiguration {

    private final WishlistFeatureContext testContext;
    private WishlistRequest wishlistRequest;
    private final String urlBase = "/wishlists";
    private ResponseEntity<?> response;
    private ProductRequest productRequest;
    private MessageExceptionResponse messageExceptionResponse;

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
                .concat("/products");
        response = testRestTemplate.postForEntity(url, productRequest, WishlistResponse.class);
    }

    @E("a wishlist contem o produto {string}")
    public void a_wishlist_contem_o_produto(String productId) {
        testContext.setWishlistResponse(parseResponseBodyToWishlistResponse());
        Assertions.assertNotNull(testContext.getWishlistResponse().products());
        Assertions.assertEquals(testContext.getWishlistResponse().products().getFirst().id(), productId);
    }

    /*
       Scenario: Consultar todos os produtos da Wishlist do cliente
     */

    @Dado("que o cliente tem uma wishlist com produtos adicionados")
    public void que_o_cliente_tem_uma_wishlist_com_produtos_adicionados() {
        Assertions.assertNotNull(testContext.getWishlistResponse().products());
    }

    @Quando("ele consultar todos os produtos da sua wishlist")
    public void ele_consultar_todos_os_produtos_da_sua_wishlist() {
        final String url = urlBase
                .concat("/")
                .concat(testContext.getWishlistResponse().id());
        response = testRestTemplate.getForEntity(url, WishlistDetailResponse.class);
    }

    @Entao("a resposta deve conter o produto {string} adicionado")
    public void a_resposta_deve_conter_o_produto_adicionado(String productId) {
        testContext.setWishlistDetailResponse(parseResponseBodyToWishlistDetailResponse());
        Assertions.assertEquals(testContext.getWishlistDetailResponse().products().getFirst().getId(), productId);
    }

    /*
       Scenario: Consultar todos os produtos da Wishlist do cliente
     */

    @Quando("o cliente consultar se o produto {string} está na sua wishlist")
    public void o_cliente_consultar_se_o_produto_esta_na_sua_wishlist(String productId) {
        final String url = urlBase
                .concat("/")
                .concat(testContext.getWishlistResponse().id())
                .concat("/products/")
                .concat(productId);
        response = testRestTemplate.getForEntity(url, WishlistDetailResponse.class);
    }

    @Entao("a resposta deve indicar que o produto {string} está na wishlist")
    public void a_resposta_deve_indicar_que_o_produto_esta_na_wishlist(String productId) {
        testContext.setWishlistDetailResponse(parseResponseBodyToWishlistDetailResponse());
        Assertions.assertEquals(testContext.getWishlistDetailResponse().products().getFirst().getId(), productId);
    }

    @Entao("apenas {int} produto foi retornado")
    public void apenas_foi_produto_retornado(Integer size) {
        Assertions.assertEquals(testContext.getWishlistResponse().products().size(), size);
    }

    @Quando("ele tentar adicionar o produto {string} novamente à sua wishlist")
    public void ele_tentar_adicionar_o_produto_novamente_a_sua_wishlist(String productId) {
        final String url = urlBase
                .concat("/")
                .concat(testContext.getWishlistResponse().id())
                .concat("/")
                .concat("products");
        response = testRestTemplate.postForEntity(url, new ProductRequest(productId), MessageExceptionResponse.class);

    }

    @Entao("a operação deve falhar devolvendo o http status: {int}")
    public void a_operacao_deve_falhar_devolvendo_o_http_status(Integer statusCode) {
        Assertions.assertEquals(response.getStatusCode().value(), statusCode);
        messageExceptionResponse = parseResponseBodyToMessageExceptionResponse();
    }

    @E("uma mensagem de validacao {string} deve ser retornada")
    public void uma_mensagem_de_validacao_deve_ser_retornada(String msg) {
        Assertions.assertEquals(messageExceptionResponse.getMessage(), msg);
    }

    @Quando("o cliente tentar adicionar um produto {string} a uma wishlist inexistente com ID {string}")
    public void o_cliente_tentar_adicionar_um_produto_a_uma_wishlist_inexistente_com_id(String productId,
                                                                                        String wishlistId) {

        final String url = urlBase
                .concat("/")
                .concat(wishlistId)
                .concat("/products");
        response = testRestTemplate.postForEntity(url, new ProductRequest(productId), MessageExceptionResponse.class);
    }

    @Quando("ele remover o produto {string} da sua wishlist")
    public void ele_remover_o_produto_da_sua_wishlist(String productId) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        response = testRestTemplate.exchange(
                urlBase.concat("/")
                        .concat(testContext.getWishlistResponse().id())
                        .concat("/products/")
                        .concat(productId),
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
    }

    @E("a wishlist não deve conter o produto {string}")
    public void a_wishlist_nao_deve_conter_o_produto(String productId) {
        final String url = urlBase
                .concat("/")
                .concat(testContext.getWishlistResponse().id())
                .concat("/products/")
                .concat(productId);
        response = testRestTemplate.getForEntity(url, WishlistDetailResponse.class);
        WishlistDetailResponse wishlistDetailResponse = parseResponseBodyToWishlistDetailResponse();
        Assertions.assertNull(wishlistDetailResponse.id());
    }

    @Entao("o http status deve ser {int}")
    public void o_http_status_deve_ser(Integer statusCode) {
        Assertions.assertEquals(response.getStatusCode().value(), statusCode);
    }

    private WishlistResponse parseResponseBodyToWishlistResponse() {
        assert response != null;
        WishlistResponse responseBody = (WishlistResponse) response.getBody();
        assert responseBody != null;
        return responseBody;
    }

    private WishlistDetailResponse parseResponseBodyToWishlistDetailResponse() {
        assert response != null;
        WishlistDetailResponse responseBody = (WishlistDetailResponse) response.getBody();
        assert responseBody != null;
        return responseBody;
    }

    private MessageExceptionResponse parseResponseBodyToMessageExceptionResponse() {
        assert response != null;
        MessageExceptionResponse responseBody = (MessageExceptionResponse) response.getBody();
        assert responseBody != null;
        return responseBody;
    }
}

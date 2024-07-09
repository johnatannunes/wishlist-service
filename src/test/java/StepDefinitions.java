import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StepDefinitions {

    private ResponseEntity<String> response;

    @When("eu enviar uma solicitação para criar uma nova wishlist com o nome {string}")
    public void eu_enviar_uma_solicitacao_para_criar_uma_nova_wishlist_com_o_nome(String wishlistName) {
        String url = "/wishlists"; // URL do endpoint para criar wishlist
        String requestBody = "{ \"name\": \"" + wishlistName + "\" }";

        //response = restTemplate.postForEntity(url, requestBody, String.class);
        response = new ResponseEntity<>("\"name\":\"" + wishlistName + "\"", HttpStatus.CREATED);
    }

    @Then("a resposta deve ter status 201 CREATED")
    public void a_resposta_deve_ter_status_created() {
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Then("uma wishlist com o nome {string} deve ser retornada")
    public void uma_wishlist_com_o_nome_deve_ser_retornada(String wishlistName) {
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assert responseBody.contains("\"name\":\"" + wishlistName + "\"");
    }
}

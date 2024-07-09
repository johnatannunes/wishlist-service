Feature: Gestão de Wishlists
  Como um cliente
  Quero gerenciar minhas wishlists
  Para poder organizar meus produtos desejados

  Scenario: Criar uma nova wishlist
    When eu enviar uma solicitação para criar uma nova wishlist com os seguintes dados
      | customerId               | listName       | privacyStatus |
      | 668a3cd79c5b9977c005c306 | Minha Wishlist | PUBLIC        |
    Then a resposta deve ter status 201 CREATED
    And uma wishlist com customerId "668a3cd79c5b9977c005c306", nome da lista "Minha Wishlist" e status de privacidade "PUBLIC" deve ser retornada

  Scenario: Remover um produto de uma wishlist
    Given que eu tenho uma wishlist existente com ID "668b104f02b83a53c4290fc8" contendo o produto com ID "668a3c139c5b9977c005c198"
    When eu enviar uma solicitação para remover o produto com ID "668a3c139c5b9977c005c198" da wishlist "668b104f02b83a53c4290fc8"
    Then a resposta deve ter status 204 NO CONTENT

  Scenario: Visualizar todas as wishlists de um cliente
    Given que eu tenho wishlists existentes para o customerId "668a3cd79c5b9977c005c306"
    When eu enviar uma solicitação para visualizar todas as minhas wishlists
    Then a resposta deve ter status 200 OK
    And todas as minhas wishlists devem ser retornadas
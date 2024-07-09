Feature: Gestão de Wishlists
  Como um cliente
  Quero gerenciar minhas wishlists
  Para poder organizar meus produtos desejados

  Scenario: Criar uma nova wishlist
    When eu enviar uma solicitação para criar uma nova wishlist com o nome "Minha Wishlist"
    Then a resposta deve ter status 201 CREATED
    And uma wishlist com o nome "Minha Wishlist" deve ser retornada

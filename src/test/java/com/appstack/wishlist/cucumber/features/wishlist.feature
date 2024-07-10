Feature: Wishlist do Cliente
  Como um cliente
  Quero gerenciar minhas wishlists
  Para poder organizar meus produtos desejados

  Scenario: Criar uma Wishlist para um cliente
  Given que o cliente com ID "668a3cd79c5b9977c005c306" ainda não possui uma wishlist criada
  When o cliente solicita a criação de uma nova wishlist
  Then uma nova wishlist deve ser criada para o cliente
  And a wishlist deve estar vazia
  And a wishlist deve pertencer ao cliente com ID "668a3cd79c5b9977c005c306"

  Scenario: Adicionar um produto na Wishlist do cliente
  Given que o cliente tem uma wishlist
  When ele adicionar o produto "668a3cd79c5b9977c005c310" à sua wishlist
  Then o produto deve ser adicionado com sucesso à wishlist
  And a wishlist deve conter o produto "668a3cd79c5b9977c005c310"

  Scenario: Consultar todos os produtos da Wishlist do cliente
  Given que o cliente tem uma wishlist com produtos adicionados
  When ele consultar todos os produtos da sua wishlist
  Then a resposta deve conter o produto "668a3cd79c5b9977c005c310" adicionado
# language: pt

Funcionalidade: Wishlist do Cliente
  Como um cliente
  Quero gerenciar minhas wishlists
  Para poder organizar meus produtos desejados

  Cenario: Criar uma Wishlist para um cliente
    Dado que o cliente com ID "668a3cd79c5b9977c005c306" ainda não possui uma wishlist criada
    Quando o cliente solicita a criação de uma nova wishlist
    Entao uma nova wishlist deve ser criada para o cliente
    E a wishlist deve estar vazia
    E a wishlist deve pertencer ao cliente com ID "668a3cd79c5b9977c005c306"

  Cenario: Adicionar um produto na Wishlist do cliente
    Dado que o cliente tem uma wishlist
    Quando ele adicionar o produto "668a3cd79c5b9977c005c310" à sua wishlist
    Entao o produto deve ser adicionado com sucesso à wishlist
    E a wishlist contem o produto "668a3cd79c5b9977c005c310"

  Cenario: Consultar todos os produtos da Wishlist do cliente
    Dado que o cliente tem uma wishlist com produtos adicionados
    Quando ele consultar todos os produtos da sua wishlist
    Entao a resposta deve conter o produto "668a3cd79c5b9977c005c310" adicionado

  Cenario: Consultar se um determinado produto está na Wishlist do cliente
    Quando o cliente consultar se o produto "668a3cd79c5b9977c005c310" está na sua wishlist
    Entao a resposta deve indicar que o produto "668a3cd79c5b9977c005c310" está na wishlist
    E apenas 1 produto foi retornado

  Cenario: Produto já existente na Wishlist do cliente
    Dado que o cliente tem uma wishlist com produtos adicionados
    Quando ele tentar adicionar o produto "668a3cd79c5b9977c005c310" novamente à sua wishlist
    Entao a operação deve falhar devolvendo o http status: 412
    E uma mensagem de validacao "Product already exists in wishlist" deve ser retornada

  Cenario: Wishlist inexistente para o cliente
    Quando o cliente tentar adicionar um produto "668a3cd79c5b9977c005c304" a uma wishlist inexistente com ID "668e0ce39aebc2bf0d02d9f4"
    Entao a operação deve falhar devolvendo o http status: 404
    E uma mensagem de validacao "Wishlist not found" deve ser retornada

  Cenario: Remover um produto da Wishlist do cliente
    Dado que o cliente tem uma wishlist
    Quando ele remover o produto "668a3cd79c5b9977c005c310" da sua wishlist
    Entao o http status deve ser 204
    E a wishlist não deve conter o produto "668a3cd79c5b9977c005c310"
    Entao o http status deve ser 404

Feature: Product - Store

  Background:
    Given estoy en la pagina de la tienda

  Scenario: Validacion del precio de un producto con credenciales validas
    And me logueo con mi usuario "USUARIO_VALIDO" y clave "CLAVE_VALIDA"
    When navego a la categoria "Clothes" y subcategoria "Men"
    And agrego 2 unidades del primer producto al carrito
    Then valido en el popup la confirmacion del producto agregado
    And valido en el popup que el monto total sea calculado correctamente
    When finalizo la compra
    Then valido el titulo de la pagina del carrito
    And vuelvo a validar el calculo de precios en el carrito

  Scenario: Login invalido no permite llegar a la pagina principal
    And me logueo con mi usuario "USUARIO_INVALIDO" y clave "CLAVE_INVALIDA"
    Then valido que no accedo a la pagina principal

  Scenario: Categoria inexistente no permite continuar
    And me logueo con mi usuario "USUARIO_VALIDO" y clave "CLAVE_VALIDA"
    When navego a la categoria "Autos"
    Then valido que la categoria no existe y no continuo el flujo


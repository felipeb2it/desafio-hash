## Avaliação técnica Hash

Tecnologias utilizadas: Micronaut, Java 11, e Junit 5.
O projeto já inclui um wrapper do Maven para realizar operações básicas da aplicação.

É necessário rodar o comando mvnw compile antes de iniciar a aplicação. Para iniciar os serviços por linha de comando e digitar mvnw mn:run

Os testes podem ser iniciados ao digitar mvnw test. Antes de iniciar os testes é necessário fazer o run da aplicação,
esta descrito no passo anterior.

O debug do projeto pode ser feito utilizando mvnw mn:run -Dmn.debug
Através da IDE Eclipse é possivel conectar por "Remote Java Application" na porta 5005.

Criei testes unitários apenas do endpoint principal que é o HashEndpoint, nele esta o micro serviço solicitado na avaliação.

Também criei testes utilizando o Postman, a coleção esta na raiz do projeto com o nome Hash.postman_collection.json.

Para fazer deploy no docker basta iniciar o daemon do docker e executar o comando: deploy -Dpackaging=docker

Achei a avaliação muito interessante. Agradeço a oportunidade e aguardo retorno!
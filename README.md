# shortest.path
Este é um projeto maven, utilizando Spring Boot, Spring Data JPA e H2 database.
Requisitos:
	- JDK 7
	- Maven 3.2.1

Para gerar o jar do projeto:
- mvn clean package

Para executar o jar:
- java -jar nome_do_arquivo

Para executar a partir do projeto
- mvn spring-boot:run

Para executar os testes
- mvn test

Para alterar o mode de execução da base de dados de modo que os dados não sejam persistidos:
- alterar a propriedade spring.jpa.hibernate.ddl_auto para create-drop no arquivo application.properties

Para inserir um novo mapa deve-se fazer uma requisição POST no formato JSON para a seguinte URL:
- http://nome_da_maquina:8080/map
Segue exemplo do formato a ser enviado:
{
    "name": "mapa",
    "arches": [
		{
			"begin":"A",
			"end":"B",
			"distance":1
		},
		{
			"begin":"B",
			"end":"C",
			"distance":2
		}
    ]
}

Para fazer obter o menor caminho entre dois pontos deve-se fazer uma requisição GET no seguinte formato, substituindo os valores entre []:
- http://nome_da_maquina:8080/shortestPath?origin=[no_de_origem]&destination=[no_de_destino]&autonomy=[valor_da_autonomia]&costOfLitre=[valor_do_custo_por_litro]

Observações:
	- O valor de distância é inteiro;
	- O valor de autonomia e custo por litro é double;
	- O nome do mapa e dos nós são String com tamanho máximo de 10 caracteres;
	- No caso de dois arcos com mesma origem e mesmo destino o algoritmo de menor caminho considera apenas o arco mais curto;
	- Os arcos são unidirecionais, isto é, um arco com origem A e destino B não implica em um arco de origem B e destino A.

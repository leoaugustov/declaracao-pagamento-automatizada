# Declaracao de Pagamento Automatizada - Una
Um simples projeto Java que automatiza o processo de solicitação e arquivamento da declaração de pagamento da mensalidade no [portal da faculdade Una](https://aluno.una.br/SOL/aluno/index.php/index/seguranca/dev/instituicao/3/acesso).

A ideia surgiu da minha necessidade de, todo mês alguns dias após realizar o pagamento da mensalidade, precisar acessar o sistema da faculdade, solicitar o envio da declaração de pagamento para o meu e-mail, realizar o download do documento e arquivá-lo no meu Google Drive.

A ideia inicial, que resolve o meu problema, é adicionar a execução dessa rotina na inicialização do meu computador. No entanto, o método que engatilha a execução do processo pode ser outro, como por exemplo uma tarefa agendada em um servidor.

## Para instalar e rodar o projeto na sua máquina
### Clone o repositório
    $ git clone https://github.com/leoaugustov/declaracao-pagamento-automatizada.git
    $ cd declaracao-pagamento-automatizada

### Prepare o ambiente
Realize o download do [ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/downloads), necessário para o funcionamento do Selenium.

### Habilite o uso da Google API
Para conseguir os tokens de acesso aos serviços do Google, siga esse [tutorial](https://medium.com/@pablo127/google-api-authentication-with-oauth-2-on-the-example-of-gmail-a103c897fd98). Lembre-se que as APIs utilizadas são a Gmail API e a Google Drive API e que os escopos necessários são:
- https://www.googleapis.com/auth/gmail.send
- https://www.googleapis.com/auth/gmail.readonly
- https://www.googleapis.com/auth/drive

> Para enviar mais de um escopo na requisição basta separá-los por um espaço.

### Informe os parâmetros do projeto
No arquivo [app.properties](https://github.com/leoaugustov/declaracao-pagamento-automatizada/blob/master/src/main/resources/app.properties) preencha as propriedades necessárias. O usuário do Gmail informado deve ser o mesmo e-mail para onde o sistema da faculdade vai enviar o documento. 

### Execute o projeto
Execute a classe [App](https://github.com/leoaugustov/declaracao-pagamento-automatizada/blob/master/src/main/java/leoaugustov/declaracaopagamentoautomatizada/App.java).

> Após a primeira execução um arquivo será criado no diretório do projeto (ou no diretório do artefato gerado) para armazenar a data da última parcela paga.

## Construído com
- [Selenium](https://www.selenium.dev/documentation/en/)
- [JSoup](https://jsoup.org/)
- [Gmail API](https://developers.google.com/gmail/api)
- [Google Drive API](https://developers.google.com/drive)

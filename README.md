dextranet
=========

Ambiente de Desenvolvimento: http://dev.dextra-dextranet.appspot.com/

Ambiente de Produção: http://prod.dextra-dextranet.appspot.com/

O Dextranet está configurado tanto para utilizar o mycontainer quanto o próprio servidor de desenvolvimento provido pelo GAE. O mycontainer não suporta alguns recursos do GAE como o upload de arquivos. O devserver (GAE), por sua vez, provê todos os recursos do GAE mas não tem hotdeploy para HTML, CSS e JS. Para subir o ambiente de desenvolvimento, execute:
```
mvn mycontainer:start
```
ou
```
mvn appengine:devserver
```


Caso queira atualizar a versão do ambiente de produção, basta executar o comando:
```
mvn appengine:update
```
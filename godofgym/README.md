![GitHub](https://img.shields.io/github/license/felipea1/Academia-SistemaOS?color=green)

# 🏋🏻 God of gym - Sistema OS
### Este programa é um sistema para gestão de ordem de serviços e estoque (**Sistema OS**) de uma assistência técnica de manutenção de aparelhos de academia.  Este projeto é muito popular e serve como modelo para outros projetos de gestão de serviços, como por exemplo assistências técnicas de smartphones, eletrodomésticos, oficinas mecânicas etc.
<br>

!["Sistema OS"](img/sistema0s.png)

<br>

### Autor
Felipe de Almeida Duarte
<hr>
<br>

## Instruções para instalação e uso do aplicativo
### Pré requisitos:
1) Ter o Java **versão 17** ou superior instalado. Testado com a versão openJDK 21 LTS que pode ser obtida no link indicado. Na instalação selecione todos os recursos conforme indicado na imagem.

[download openJDK)](https://adoptium.net/)

![openJDK](img/openjdk.png)

2) Ter um banco de dados local baseado no **MySQL 8** ou MariaDB compatível, no exemplo usei o XAMPP que pode ser obtido no link indicado.

[download xampp](https://www.apachefriends.org/)

### Instalação do banco
1) Iniciar os serviços Apache e MySQL no XAMPP, conforme indicado na imagem.

![xampp start](img/xampp.png)

2) No navegador de internet digite: **localhost/dashboard** e selecione no menu: **phpMyAdmin** conforme indicado na imagem.

![phpmyadmin](img/phpmyadmin.png)

3) Crie um novo banco de dados de nome **dbsistema** (sem usar acentuação) conforme indicado na imagem.

![dbcarometro](img/mysql.png)

4) Acesse o link abaixo e copie o código depois na aba SQL, copie e cole o código abaixo e execute. (Passos 1,2 e 3 indicados na imagem)

https://github.com/felipea1/Academia-SistemaOS/blob/main/godofgym/banco/SistemaOS_TargetGym.sql

<br>

![alunos](img/tutorial.png)

### Instalação do aplicativo
1) Em Releases faça o download do arquivo **godofgymos**
2) Execute e verifique no rodapé o ícone que representa o banco de dados conectado. Se estiver com erro (conforme indicado na figura) verifique o XAMPP e revise novamente os passos 1 a 4 da instalação do banco.

![app](img/offline.png)

3) Se tudo estiver OK você pode cadastrar novos usuários (1), fazer uma ordem de serviço (2), cadastrar fornecedores (3), ver a aba de relatórios (4), cadastrar clientes (5) e produtos (6).

![numbers](img/numeros.png)

## Tecnologias que são abordadas neste projeto:
- Criação de banco de dados e tabelas no MySQL
- CRUD (Create Read Update e Delete)
- IDE Eclipse
- Java SE
- Window Builder
- JDBC (Java Database Connectivity)
- Validação de dados
- Uso da biblioteca iTextpdf para gerar listagem de alunos com foto

<br>

## Bibliotecas usadas neste projeto
[mysql](https://dev.mysql.com/downloads/connector/j/)

[itextpdf](https://github.com/itext/itextpdf)

<hr>

<br>

### Sobre mim
Inscrever-se no canal do YouTube - [INSCREVA-SE!](https://www.youtube.com/channel/UC1e3Muh9VUtdEPL1kyIfcUw?sub_confirmation=1)

Seguir-me nas redes sociais:
<p align="left">
<a href="https://www.youtube.com/@yfelpsz2302" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/youtube.svg" alt="roboticapraticabr" height="30" width="40" /></a>
<a href="https://www.linkedin.com/in/felipe-duarte-5b12a4238/" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="professorjosedeassis" height="30" width="40" /></a>
</p>


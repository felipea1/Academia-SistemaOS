/**
 * Sistema para gestão de OS
 * @author Professor Felipe de Almeida Duarte
 */
 
 create database dbsistema;
 use dbsistema;
 
  -- unique (não permite valores duplicados no campo)
 create table usuarios (
	id int primary key auto_increment,
    nome varchar(50) not null,
    login varchar(15) not null,
    senha varchar(250) not null,
    perfil varchar(10) not null
);

drop table usuarios;

describe usuarios;
select * from usuarios where login = 'Felipe' ;

-- uso do md5() para criptografar uma senha
insert into usuarios(nome,login,senha,perfil)
values('Administrador','admin', md5('admin'),'admin');

select * from usuarios;
select * from usuarios where nome = "Administrador";
select * from usuarios where login = "admin";

-- login(autenticação)
select * from usuarios where login = 'admin' and senha = md5('admin');

-- Criação da tabela clientes----------------------------------------------------------------------------------------------------------------------
create table clientes (
	idcli int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    cep varchar(10),
    endereco varchar(50) not null,
    numero varchar(10) not null,
    complemento varchar(20),
    bairro varchar(30) not null,
    cidade varchar(30) not null,
    uf char(2) not null,
    email varchar(50) not null
);

select * from clientes;

-- busca avançada pelo nome (estilo google)
select nome from usuarios where nome like 'f%' order by nome;

-- Criação da tela de serviços----------------------------------------------------------------------------------

-- timestamp default current_timestamp (data e hora automatica) ------------------ 
-- decimal (numeros não inteiros)
-- foreign key(FK) chave estrangeiro
-- 1 (FK) ----------------------- (FK) 
create table servicos (
	os int primary key auto_increment,
    dataOS timestamp default current_timestamp,
	equipamento varchar(200) not null,
    defeito varchar(200) not null,
    valor decimal(10,2),
    idcli int not null,
    foreign key (idcli) references clientes(idcli)
);

insert into servicos (equipamento,defeito,valor,idcli)
values ('Notebook Lenovo G90','Troca da fonte',250,5);

select * from servicos;
-- selecionando o conteudo de 2 ou mais tabelas
select * from servicos
inner join clientes
on servicos.idcli = clientes.idcli;

-- deletar a os do cliente
delete from clientes where idcli = 5;

/** Relatórios **/ 
-- clientes
select nome,fone,email from clientes order by nome;

-- serviços 
select 
servicos.os,servicos.dataOS,servicos.equipamento,servicos.defeito,
servicos.valor,
clientes.nome
from servicos
inner join clientes
on servicos.idcli = clientes.idcli;

-- fornecedores
create table fornecedores (
	idfor int primary key auto_increment,
    razaosocial varchar(50) not null,
    fone varchar(15) not null,
    cep varchar(10),
    endereco varchar(50) not null,
    numero varchar(10) not null,
    complemento varchar(20),
    bairro varchar(30) not null,
    cidade varchar(30) not null,
    uf char(2) not null,
    cnpj varchar(30) not null
);

-- produtos 
create table produtos (
codigo int primary key auto_increment,
barcode varchar (20) not null,
descricao text, 
foto longblob not null, 
estoque int not null,
estoquemin int not null,
valor decimal(10,2),
unidade varchar(50) not null,
localarm varchar(50) not null,
nome varchar(50) not null
);

drop table produtos;

select * from produtos;

select * from fornecedores;
-- relacionando produtos e fornecedores 
select * from produtos
inner join fornecedores
on produtos.codigo = fornecedores.idfor;
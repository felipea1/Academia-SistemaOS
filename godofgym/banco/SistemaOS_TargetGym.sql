
 -- Sistema para gestão de OS
 -- @author Professor Felipe de Almeida Duarte
 
 create database dbsistema;
 use dbsistema;
 
 create table usuarios (
	id int primary key auto_increment,
    nome varchar(50) not null,
    login varchar(15) not null,
    senha varchar(250) not null,
    perfil varchar(10) not null
);

insert into usuarios(nome,login,senha,perfil)
values('Administrador','admin', md5('admin'),'admin');

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
    email varchar(50) not null,
    cpfecnpj varchar(25)
);

create table servicos (
	os int primary key auto_increment,
    dataOS timestamp default current_timestamp,
	equipamento varchar(200) not null,
    defeito varchar(200) not null,
    valor decimal(10,2) not null,
    descricao text not null,
    substituido varchar(200),
    responsavel varchar(200) not null,
    numeroserie varchar (200),
    diagnostico text not null,
    idcli int not null,
    foreign key (idcli) references clientes(idcli)
);

create table fornecedores (
	idfor int primary key auto_increment,
    razao varchar(50) not null,
    fantasia varchar(50) not null,
    fone varchar(15) not null,
    vendedor varchar(20),
    email varchar(50) not null,
    site varchar(50),
    ie varchar (20),
    cep varchar(10),
    endereco varchar(50) not null,
    numero varchar(10) not null,
    complemento varchar(20),
    bairro varchar(30) not null,
    cidade varchar(30) not null,
    uf char(2) not null,
    cnpj varchar(20) not null unique
);

create table produtos (
codigo int primary key auto_increment,
barcode varchar (250) unique,
descricao varchar(250) not null, 
foto longblob, 
estoque int not null,
estoquemin int not null,
unidade varchar(50) not null,
localarm varchar(50),
nome varchar(50) not null,
lote varchar (20) not null,
fabricante varchar (50),
dataent timestamp default current_timestamp,
dataval date not null,
custo decimal(10,2) not null,
lucro decimal (10,2),
idfor int not null,
foreign key(idfor) references fornecedores (idfor)
);



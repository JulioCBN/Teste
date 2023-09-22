create table categoria (
	codigo bigint(20) auto_increment primary key,
	nome varchar(30) not null
) engine=InnoDB default charset=utf8;

INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Supermercado');
INSERT INTO categoria (nome) values ('Farmácia');
INSERT INTO categoria (nome) values ('Outros');
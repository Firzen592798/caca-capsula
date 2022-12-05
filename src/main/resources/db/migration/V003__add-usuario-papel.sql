create table usuario(
	id_usuario serial primary key,
	login varchar(255) not null,
	senha varchar(255) not null
);


create table papel(
	id_papel serial primary key,
	nome varchar(255) not null
);

create table permissao(
	id_usuario integer,
	id_papel integer
);

alter table permissao add constraint fk_usuario foreign key(id_usuario) references usuario(id_usuario);
alter table permissao add constraint fk_papel foreign key(id_papel) references papel(id_papel);


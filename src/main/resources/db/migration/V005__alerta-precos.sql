create table alerta_preco(
	id_alerta_preco serial primary key,
	id_usuario bigint not null,
	tipo varchar(255) not null,
	preco numeric not null	
);

alter table alerta_preco add constraint fk_alerta_preco_usuario foreign key(id_usuario) references usuario(id_usuario);
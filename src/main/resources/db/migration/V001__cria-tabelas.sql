create table item(
	id_item integer primary key,
	nome varchar(255) not null,
	tipo varchar(12) not null,
	qtd integer not null
);

create table registro_preco(
	id_registro_preco serial primary key,
	id_item bigint not null,
	preco numeric not null,
	preco_old numeric,
	data_criacao timestamp
);

alter table registro_preco add constraint fk_registro_preco_item foreign key(id_item) references item(id_item);

alter table item add constraint unique_con unique(id_item);

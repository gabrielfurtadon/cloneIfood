insert into cozinha (id,nome)values (1, 'brasileira');
insert into cozinha (id,nome)values (2, 'japosena');
insert into cozinha (id,nome)values (3, 'australiana');

insert into estado(nome) values ('São Paulo');
insert into estado(nome) values ('Rio de Janeiro');

insert into cidade(nome, estado_id) values ('Itapetininga', 1);
insert into cidade(nome, estado_id) values ('Barra do Pirai', 2);

insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Bamboo', 10, 1, utc_timestamp, utc_timestamp, 1, '86300-000', 'Rua Paraiba', '528', 'Centro');
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Yoishi', 10, 2, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Outback', 15, 3, utc_timestamp, utc_timestamp);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Pix');
insert into forma_pagamento (id, descricao) values (4, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar as cozinhas existentes');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar as cozinhas existentes');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2);

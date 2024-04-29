insert into cozinha (id,nome)values (1, 'brasileira');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Bamboo', 10, 1);

insert into estado(nome) values ('São Paulo');

insert into cidade(nome, estado_id) values ('Itapetininga', 1);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Pix');
insert into forma_pagamento (id, descricao) values (4, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar as cozinhas existentes');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar as cozinhas existentes');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2);

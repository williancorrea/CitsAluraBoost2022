# Usuarios (Senha = 123456)
INSERT INTO `alura-comex`.usuario (id, email, nome, senha) VALUES (1, 'willian.vag@gmail.com', 'Willian Corrêa', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');

# Perfil

# Categorias
INSERT INTO `alura-comex`.categorias (id, nome, status) VALUES (1, 'CATEGORIA 1', 'ATIVA');

# Clientes
INSERT INTO `alura-comex`.clientes (id, bairro, cidade, complemento, cpf, estado, nome, numero, rua, telefone) VALUES (1 , 'ZZZZ', 'Curitiba', null, '41537106015', 'PR', 'Willian Correa', '1', 'AAA', '');

# Produtos
INSERT INTO `alura-comex`.produtos (id, descricao, nome, preco_unitario, quantidade_estoque, categoria_id) VALUES (1, '', 'PRODUTO 1', 2.50, 50, 1);

# Pedidos
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (1, '2022-10-30', 0.00, 'NENHUM', 1);
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (2, '2022-10-30', 0.00, 'NENHUM', 1);
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (3, '2022-10-30', 0.00, 'NENHUM', 1);
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (4, '2022-10-30', 0.00, 'NENHUM', 1);
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (5, '2022-10-30', 0.00, 'NENHUM', 1);
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (6, '2022-10-30', 0.05, 'FIDELIDADE', 1);
INSERT INTO `alura-comex`.pedidos (id, data, desconto, tipo_desconto, cliente_id) VALUES (7, '2022-10-30', 0.05, 'FIDELIDADE', 1);

# Item Pedido
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 1, 1);
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 2, 1);
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 3, 1);
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 4, 1);
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 5, 1);
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 6, 1);
INSERT INTO `alura-comex`.itens_pedido (desconto, preco_unitario, quantidade, tipo_desconto, pedido_id, produto_id) VALUES (0.10, 2.50, 50, 'QUANTIDADE', 7, 1);

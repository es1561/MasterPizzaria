--Cliente
INSERT INTO public.cliente(cli_cod, cli_cpf, cli_nome, cli_fone) VALUES(1, 'Luis', '123.456.789-12', '12345-1234');
INSERT INTO public.cliente(cli_cod, cli_cpf, cli_nome, cli_fone) VALUES(2, 'Rafael', '123.456.789-12', '12345-1234');
--Fornecedor
INSERT INTO public.fornecedor(for_cod, for_nome, for_fone) VALUES(1, 'Chico', '12345-1234');
--Catgoria
INSERT INTO public.categoria(cat_cod, cat_desc) VALUES(1, 'Pizza');
INSERT INTO public.categoria(cat_cod, cat_desc) VALUES(2, 'Bebida');
--Produto
INSERT INTO public.produto(prod_cod, prod_nome, prod_peso, pord_valor, cat_cod) VALUES(1, 'Pizza Catufrango', 0.8, 39.9, 1);
INSERT INTO public.produto(prod_cod, prod_nome, prod_peso, pord_valor, cat_cod) VALUES(2, 'Coca-Cola 2L', 0.5, 8.9, 2);
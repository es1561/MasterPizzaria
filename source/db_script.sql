
CREATE TABLE public.Cliente (
                cli_cod INTEGER NOT NULL,
                cli_cpf CHAR(15) NOT NULL,
                cli_nome VARCHAR(50) NOT NULL,
                cli_fone CHAR(13) NOT NULL,
                CONSTRAINT cli_cod PRIMARY KEY (cli_cod)
);


CREATE TABLE public.Pedido (
                ped_cod INTEGER NOT NULL,
                cli_cod INTEGER NOT NULL,
                CONSTRAINT ped_cod PRIMARY KEY (ped_cod)
);


CREATE TABLE public.Categoria (
                cat_cod INTEGER NOT NULL,
                cat_desc VARCHAR(50) NOT NULL,
                CONSTRAINT cat_cod PRIMARY KEY (cat_cod)
);


CREATE TABLE public.Menssagem (
                men_cod INTEGER NOT NULL,
                cat_cod INTEGER NOT NULL,
                cli_cod INTEGER NOT NULL,
                men_desc VARCHAR(50) NOT NULL,
                CONSTRAINT men_pk PRIMARY KEY (men_cod)
);


CREATE TABLE public.Interesse (
                cat_cod INTEGER NOT NULL,
                cli_cod INTEGER NOT NULL,
                CONSTRAINT inte_cod PRIMARY KEY (cat_cod, cli_cod)
);


CREATE TABLE public.Produto (
                prod_cod INTEGER NOT NULL,
                prod_nome VARCHAR(50) NOT NULL,
                prod_valor NUMERIC(10,2) NOT NULL,
                cat_cod INTEGER NOT NULL,
                CONSTRAINT prod_cod PRIMARY KEY (prod_cod)
);


CREATE TABLE public.ItemPedido (
                ped_cod INTEGER NOT NULL,
                prod_cod INTEGER NOT NULL,
                itemp_quant INTEGER NOT NULL,
                CONSTRAINT itemp_cod PRIMARY KEY (ped_cod, prod_cod)
);


ALTER TABLE public.Interesse ADD CONSTRAINT cliente_interesse_fk
FOREIGN KEY (cli_cod)
REFERENCES public.Cliente (cli_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Pedido ADD CONSTRAINT cliente_pedido_fk
FOREIGN KEY (cli_cod)
REFERENCES public.Cliente (cli_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Menssagem ADD CONSTRAINT cliente_menssagem_fk
FOREIGN KEY (cli_cod)
REFERENCES public.Cliente (cli_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ItemPedido ADD CONSTRAINT pedido_itempedido_fk
FOREIGN KEY (ped_cod)
REFERENCES public.Pedido (ped_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Interesse ADD CONSTRAINT categoria_interesse_fk
FOREIGN KEY (cat_cod)
REFERENCES public.Categoria (cat_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Produto ADD CONSTRAINT categoria_produto_fk
FOREIGN KEY (cat_cod)
REFERENCES public.Categoria (cat_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Menssagem ADD CONSTRAINT categoria_menssagem_fk
FOREIGN KEY (cat_cod)
REFERENCES public.Categoria (cat_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ItemPedido ADD CONSTRAINT produto_itempedido_fk
FOREIGN KEY (prod_cod)
REFERENCES public.Produto (prod_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

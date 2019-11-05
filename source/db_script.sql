
CREATE TABLE public.Fornecedor (
                for_cod INTEGER NOT NULL,
                for_nome VARCHAR(30) NOT NULL,
                for_fone CHAR(20) NOT NULL,
                CONSTRAINT pk_fornecedor PRIMARY KEY (for_cod)
);


CREATE TABLE public.Compra (
                comp_cod INTEGER NOT NULL,
                for_cod INTEGER NOT NULL,
                CONSTRAINT pk_compra PRIMARY KEY (comp_cod)
);


CREATE TABLE public.Caixa (
                caixa_data DATE NOT NULL,
                caixa_data_a DATE NOT NULL,
                caixa_data_f DATE,
                caixa_valor_a NUMERIC(10,2) NOT NULL,
                caixa_entrada NUMERIC(10,2),
                caixa_saida NUMERIC(10,2) NOT NULL,
                CONSTRAINT pk_caixa PRIMARY KEY (caixa_data)
);


CREATE TABLE public.Categoria (
                cat_cod INTEGER NOT NULL,
                cat_desc VARCHAR(30) NOT NULL,
                CONSTRAINT pk_categoria PRIMARY KEY (cat_cod)
);


CREATE TABLE public.Cliente (
                cli_cod INTEGER NOT NULL,
                cli_cpf CHAR(15) NOT NULL,
                cli_nome VARCHAR(30) NOT NULL,
                cli_fone CHAR(20) NOT NULL,
                CONSTRAINT pk_cliente PRIMARY KEY (cli_cod)
);


CREATE TABLE public.Interesse (
                cli_cod INTEGER NOT NULL,
                cat_cod INTEGER NOT NULL,
                CONSTRAINT pk_interesse PRIMARY KEY (cli_cod, cat_cod)
);


CREATE TABLE public.Menssagem (
                men_cod INTEGER NOT NULL,
                cli_cod INTEGER NOT NULL,
                cat_cod INTEGER NOT NULL,
                men_desc VARCHAR(30) NOT NULL,
                CONSTRAINT pk_menssagem PRIMARY KEY (men_cod, cli_cod, cat_cod)
);


CREATE TABLE public.Pedido (
                ped_cod INTEGER NOT NULL,
                ped_data DATE NOT NULL,
                cli_cod INTEGER NOT NULL,
                CONSTRAINT pk_pedido PRIMARY KEY (ped_cod)
);


CREATE TABLE public.Movimento (
                mov_cod INTEGER NOT NULL,
                caixa_data DATE NOT NULL,
                mov_tipo INTEGER NOT NULL,
                mov_data DATE NOT NULL,
                mov_valor NUMERIC(6,2) NOT NULL,
                ped_cod INTEGER,
                comp_cod INTEGER,
                CONSTRAINT pk_movimento PRIMARY KEY (mov_cod, caixa_data)
);


CREATE TABLE public.Produto (
                prod_cod INTEGER NOT NULL,
                prod_valor NUMERIC(10,2) NOT NULL,
                prod_nome VARCHAR(30) NOT NULL,
                cat_cod INTEGER NOT NULL,
                CONSTRAINT pk_produto PRIMARY KEY (prod_cod)
);


CREATE TABLE public.ItemCompra (
                prod_cod INTEGER NOT NULL,
                comp_cod INTEGER NOT NULL,
                itemc_quant INTEGER NOT NULL,
                CONSTRAINT pk_itemcompra PRIMARY KEY (prod_cod, comp_cod)
);


CREATE TABLE public.ItemPedido (
                prod_cod INTEGER NOT NULL,
                itemp_quant INTEGER NOT NULL,
                ped_cod INTEGER NOT NULL,
                CONSTRAINT pk_itempedido PRIMARY KEY (prod_cod)
);


ALTER TABLE public.Compra ADD CONSTRAINT fornecedor_compra_fk
FOREIGN KEY (for_cod)
REFERENCES public.Fornecedor (for_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ItemCompra ADD CONSTRAINT compra_itemcompra_fk
FOREIGN KEY (comp_cod)
REFERENCES public.Compra (comp_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Movimento ADD CONSTRAINT compra_movimento_fk
FOREIGN KEY (comp_cod)
REFERENCES public.Compra (comp_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Movimento ADD CONSTRAINT caixa_movimento_fk
FOREIGN KEY (caixa_data)
REFERENCES public.Caixa (caixa_data)
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

ALTER TABLE public.Menssagem ADD CONSTRAINT interesse_menssagem_fk
FOREIGN KEY (cli_cod, cat_cod)
REFERENCES public.Interesse (cli_cod, cat_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ItemPedido ADD CONSTRAINT pedido_itempedido_fk
FOREIGN KEY (ped_cod)
REFERENCES public.Pedido (ped_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Movimento ADD CONSTRAINT pedido_movimento_fk
FOREIGN KEY (ped_cod)
REFERENCES public.Pedido (ped_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ItemPedido ADD CONSTRAINT produto_itempedido_fk
FOREIGN KEY (prod_cod)
REFERENCES public.Produto (prod_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ItemCompra ADD CONSTRAINT produto_itemcompra_fk
FOREIGN KEY (prod_cod)
REFERENCES public.Produto (prod_cod)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

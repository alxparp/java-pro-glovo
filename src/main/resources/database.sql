--Generate by pgAdmin

-- Table: public.order

-- DROP TABLE IF EXISTS public."order";

CREATE TABLE IF NOT EXISTS public."order" (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    date date NOT NULL,
    cost double precision NOT NULL,
    CONSTRAINT order_pkey PRIMARY KEY (id)
)


-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(16) COLLATE pg_catalog."default" NOT NULL,
    cost double precision NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)


-- Table: public.order_product

-- DROP TABLE IF EXISTS public.order_product;

CREATE TABLE IF NOT EXISTS public.order_product
(
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT order_product_pkey PRIMARY KEY (order_id, product_id),
    CONSTRAINT order_fk FOREIGN KEY (order_id)
    REFERENCES public."order" (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT VALID,
    CONSTRAINT product_fk FOREIGN KEY (product_id)
    REFERENCES public.product (id) MATCH SIMPLE
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT VALID
    )
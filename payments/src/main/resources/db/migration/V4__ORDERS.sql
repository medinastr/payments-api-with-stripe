CREATE TABLE public.orders(
    id serial NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT fk_order_product FOREIGN KEY (product_id) REFERENCES public.products (id)
);
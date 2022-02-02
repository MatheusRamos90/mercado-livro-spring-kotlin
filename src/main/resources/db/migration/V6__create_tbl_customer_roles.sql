CREATE TABLE customer_roles (
    customer_id int not null,
    roles varchar(50) not null
);

ALTER TABLE customer_roles ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);
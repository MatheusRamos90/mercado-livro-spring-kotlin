CREATE TABLE book (
    id int not null primary key,
    name varchar(255) not null,
    price decimal(10,2) not null,
    status varchar(20) not null,
    customer_id int not null
);

CREATE SEQUENCE book_id_seq;
ALTER TABLE book ALTER id SET DEFAULT NEXTVAL('book_id_seq');
ALTER TABLE book ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);
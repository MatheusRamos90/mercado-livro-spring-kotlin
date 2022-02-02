CREATE TABLE purchase(
    id int not null PRIMARY KEY,
    customer_id int not null,
    nfe VARCHAR(255),
    price DECIMAL(15,2) not null,
    created_at timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

CREATE SEQUENCE purchase_id_seq;
ALTER TABLE purchase ALTER id SET DEFAULT NEXTVAL('purchase_id_seq');
ALTER TABLE purchase ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);

create table purchase_book(
    purchase_id int not null,
    book_id int not null,
    PRIMARY KEY (purchase_id, book_id)
);

ALTER TABLE purchase_book ADD CONSTRAINT fk_purchase_id FOREIGN KEY (purchase_id) REFERENCES purchase(id);
ALTER TABLE purchase_book ADD CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES book(id);
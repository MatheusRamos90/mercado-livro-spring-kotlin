CREATE TABLE customer (
    id int not null primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    UNIQUE(email)
);

CREATE SEQUENCE cst_id_seq;
ALTER TABLE customer ALTER id SET DEFAULT NEXTVAL('cst_id_seq');
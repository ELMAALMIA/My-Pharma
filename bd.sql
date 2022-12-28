
CREATE TABLE users (
  username varchar(20) NOT NULL,
  password varchar(20) NOT NULL
)



INSERT INTO users (username, password) VALUES
('admin', 'admin');

CREATE TABLE medicine (
  medicine_id varchar(11) NOT NULL,
  company_name varchar(20) DEFAULT NULL,
  medicine_name varchar(20) DEFAULT NULL,
  type_product varchar(20) DEFAULT NULL,
  price double DEFAULT NULL,
  Image varchar(500) DEFAULT NULL,
  Description varchar(100) NOT NULL
);
ALTER TABLE medicine
  ADD PRIMARY KEY (medicine_id);



CREATE TABLE SALLES
(
  ID int NOT NULL PRIMARY KEY AUTO_INCREMENT
, CUSTUMER VARCHAR(20) NOT NULL
, PRICE int NOT NULL
, DATE_PAYER DATE NOT NULL
);






CREATE TABLE ITEMCOMMANDES
(
  IDMEDECINE VARCHAR(20) NOT NULL
, QUANTITEDEMANDER int NOT NULL
);

ALTER TABLE ITEMCOMMANDES
ADD FOREIGN KEY (IDMEDECINE)
REFERENCES medicine(medicine_id);


CREATE TABLE stock (
  medicine_id varchar(20) NOT NULL,
  Quantity int(11) NOT NULL,
  Date date NOT NULL
);

ALTER TABLE stock
  ADD  FOREIGN KEY (medicine_id) REFERENCES
 medicine(medicine_id);


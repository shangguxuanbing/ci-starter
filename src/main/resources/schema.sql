DROP TABLE IF EXISTS CUSTOMERS;
CREATE TABLE CUSTOMERS(
  ID int PRIMARY KEY,
  NAME VARCHAR(20) NOT NULL,
  EMAIL VARCHAR(100)
);
DELETE FROM CUSTOMERS;
INSERT INTO CUSTOMERS(ID, NAME, EMAIL) VALUES(1, 'myan', 'test@a.com');
INSERT INTO CUSTOMERS(ID, NAME, EMAIL) VALUES(2, 'michael', 'test@b.com');
CREATE TABLE instrument (
  id INT NOT NULL,
  cusip VARCHAR(50) NULL,
  shortname VARCHAR(50) NULL,
  isin VARCHAR(50) NULL,
  description VARCHAR(50) NULL
);


insert into instrument values(1, 'CUSIP1234','cusip1234','ISIN1234','isin1234');
insert into instrument values(2, 'CUSIP5678','cusip5678','ISIN5678','isin5678');
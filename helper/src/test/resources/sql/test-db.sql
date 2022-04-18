CREATE TABLE test_t(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    text       VARCHAR(255),
    int_number INT
);

CREATE TABLE test_t2(
    id INT PRIMARY KEY AUTO_INCREMENT
);

INSERT INTO test_t(text, int_number) VALUES ('text1', 1);

INSERT INTO test_t(text, int_number) VALUES ('text2', 2);

INSERT INTO test_t(text, int_number) VALUES ('text3', 3);


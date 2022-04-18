execute wrong command;

CREATE TABLE test_t(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    text       VARCHAR(255),
    int_number INT
);

INSERT INTO test_t(text, int_number) VALUES ('text1', 1);

INSERT INTO test_t(text, int_number) VALUES ('text2', 2);

INSERT INTO test_t(text, int_number) VALUES ('text3', 3);


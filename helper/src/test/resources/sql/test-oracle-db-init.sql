TRUNCATE TABLE TEST_T;

INSERT INTO TEST_T(id, text, int_number) VALUES (1, 'text1', 1);

INSERT INTO TEST_T(id, text, int_number) VALUES (2, 'text2', 2);

INSERT INTO TEST_T(id, text, int_number) VALUES (3, 'text3', 3);

DELETE FROM TEST_T WHERE ID = 1;

UPDATE TEST_T SET TEXT = 'text2_updated' WHERE ID = 2;
INSERT INTO USUARIOS (NOME_USUARIO, EMAIL, CPF, TELEFONE, DATANASCIMENTO, PERMISSAO)
VALUES ('RodrigoBarbosa', 'rodrigo@gmail.com', '12345678900', '83912345678', '1990-01-01', 'ADMIN'),
('Fulano', 'fulano@gmail.com', '22233355545', '83988558855', '1991-02-02', 'SECRETARIO'),
('Cicrano', 'cidrano@gmail.com', '11133355544', '83955664455', '1980-03-03', 'PACIENTE');


INSERT INTO CONSULTAS(DATA_CONSULTA, PROFISSIONAL, ESPECIALIDADE, DESCRICAO, ID_USUARIO)
VALUES ('2024-10-28', 'Dr João','Cardiologista', 'Exemplo de descricao', 3),
('2024-10-29', 'Dra. Maria', 'Nutricionista', 'Exemplo descricao', 3);

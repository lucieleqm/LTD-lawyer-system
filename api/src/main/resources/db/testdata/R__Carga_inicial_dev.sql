-- ---------------------------------------------------------
-- 1. ADVOGADO (ID 100)
-- ---------------------------------------------------------

-- Inserindo Endereço do Advogado
INSERT IGNORE INTO tbl_endereco (id, cep, logradouro, numero, bairro, cidade, complemento)
VALUES (100, '65000000', 'Rua da Paz', '10', 'Centro', 'São Luís', 'MA');

-- Inserindo Pessoa do Advogado
INSERT IGNORE INTO tbl_pessoa (id, nome, email, telefone, data_nascimento, endereco_id, criado_em)
VALUES (100, 'Carlos Mendes', 'advogado@gmail.com', '98999999999', '1985-08-15', 100, NOW());

-- Inserindo Papel de Advogado (herança)
INSERT IGNORE INTO tbl_advogado (pessoa_id)
VALUES (100);

-- Inserindo Usuário do Advogado (Senha: 1234)
INSERT IGNORE INTO tbl_usuario (id, pessoa_id, login, password, role, status, precisa_trocar_senha, criado_em)
VALUES (100, 100, 'advogado@gmail.com', '$2a$12$3P6zw6bmw/7v9X9Bu4cEpOOeIOgiizJM7sHIAyPf8yWIAGmmzzYsa', 'ADVOGADO', 'ATIVO', b'0', NOW());


-- ---------------------------------------------------------
-- 2. PROFESSOR (ID 101)
-- ---------------------------------------------------------

-- Inserindo Pessoa do Professor (Sem endereço no seu DTO)
INSERT IGNORE INTO tbl_pessoa (id, nome, email, telefone, data_nascimento, endereco_id, criado_em)
VALUES (101, 'Mariana Silva', 'professor@gmail.com', '98988888888', NULL, NULL, NOW());

-- Inserindo Papel de Professor
INSERT IGNORE INTO tbl_professor (pessoa_id)
VALUES (101);

-- Inserindo Usuário do Professor (Senha: 1234)
INSERT IGNORE INTO tbl_usuario (id, pessoa_id, login, password, role, status, precisa_trocar_senha, criado_em)
VALUES (101, 101, 'professor@gmail.com', '$2a$12$3P6zw6bmw/7v9X9Bu4cEpOOeIOgiizJM7sHIAyPf8yWIAGmmzzYsa', 'PROFESSOR', 'ATIVO', b'0', NOW());


-- ---------------------------------------------------------
-- 3. ESTAGIÁRIO (ID 102)
-- ---------------------------------------------------------

-- Inserindo Pessoa do Estagiário
INSERT IGNORE INTO tbl_pessoa (id, nome, email, telefone, data_nascimento, endereco_id, criado_em)
VALUES (102, 'João Pedro', 'estagiario@gmail.com', '98977777777', NULL, NULL, NOW());

-- Inserindo Papel de Estagiário (com Matrícula e Período)
INSERT IGNORE INTO tbl_estagiario (pessoa_id, matricula, periodo)
VALUES (102, '2024010123', 'ESTAGIO_II');

-- Inserindo Usuário do Estagiário (Senha: 1234)
INSERT IGNORE INTO tbl_usuario (id, pessoa_id, login, password, role, status, precisa_trocar_senha, criado_em)
VALUES (102, 102, 'estagiario@gmail.com', '$2a$12$3P6zw6bmw/7v9X9Bu4cEpOOeIOgiizJM7sHIAyPf8yWIAGmmzzYsa', 'ESTAGIARIO', 'ATIVO', b'0', NOW());
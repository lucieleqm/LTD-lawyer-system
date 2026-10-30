    create table tbl_endereco (
        id bigint not null auto_increment,
        cep varchar(9),
        numero varchar(12),
        bairro varchar(100),
        cidade varchar(100),
        complemento varchar(150),
        logradouro varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table tbl_pessoa (
        data_nascimento date,
        criado_em datetime(6),
        endereco_id bigint,
        id bigint not null auto_increment,
        telefone varchar(20),
        email varchar(255),
        nome varchar(255) not null,
        primary key (id),
        constraint uk_pessoa_email unique (email),
        constraint fk_pessoa_endereco foreign key (endereco_id) references tbl_endereco (id),
        index idx_pessoa_nome (nome)
    ) engine=InnoDB;

    create table tbl_usuario (
        precisa_trocar_senha bit not null,
        bloqueado_em datetime(6),
        criado_em datetime(6),
        desativado_em datetime(6),
        id bigint not null auto_increment,
        pessoa_id bigint,
        login varchar(100) not null,
        password varchar(255) not null,
        role varchar(50) not null,
        status varchar(50) not null,
        primary key (id),
        constraint uk_usuario_pessoa unique (pessoa_id),
        constraint uk_usuario_login unique (login),
        constraint fk_usuario_pessoa foreign key (pessoa_id) references tbl_pessoa (id)
    ) engine=InnoDB;

    create table tbl_advogado (
        pessoa_id bigint not null,
        primary key (pessoa_id),
        constraint fk_advogado_pessoa foreign key (pessoa_id) references tbl_pessoa (id)
    ) engine=InnoDB;

    create table tbl_assistido (
        pessoa_id bigint not null,
        matricula varchar(50),
        nacionalidade varchar(50),
        naturalidade varchar(50),
        profissao varchar(100),
        estado_civil varchar(50),
        primary key (pessoa_id),
        constraint fk_assistido_pessoa foreign key (pessoa_id) references tbl_pessoa (id)
    ) engine=InnoDB;

    create table tbl_estagiario (
        pessoa_id bigint not null,
        matricula varchar(50) not null,
        periodo varchar(50),
        primary key (pessoa_id),
        constraint uk_estagiario_matricula unique (matricula),
        constraint fk_estagiario_pessoa foreign key (pessoa_id) references tbl_pessoa (id)
    ) engine=InnoDB;

    create table tbl_professor (
        pessoa_id bigint not null,
        primary key (pessoa_id),
        constraint fk_professor_pessoa foreign key (pessoa_id) references tbl_pessoa (id)
    ) engine=InnoDB;

    create table tbl_demanda (
        prazo_documentos date,
        prazo_final date not null,
        advogado_id bigint,
        data_abertura datetime(6),
        estagiario_id bigint,
        id bigint not null auto_increment,
        professor_id bigint,
        ultima_atualizacao datetime(6),
        descricao TEXT not null,
        etapa_atual varchar(50),
        tempestividade varchar(50),

        primary key (id),
        constraint fk_demanda_advogado foreign key (advogado_id) references tbl_advogado (pessoa_id),
        constraint fk_demanda_estagiario foreign key (estagiario_id) references tbl_estagiario (pessoa_id),
        constraint fk_demanda_professor foreign key (professor_id) references tbl_professor (pessoa_id)
    ) engine=InnoDB;

    create table tbl_demanda_tramitacao (
        criado_em datetime(6),
        demanda_id bigint not null,
        id bigint not null auto_increment,
        responsavel_id bigint not null,
        link_anexo varchar(255),
        observacoes TEXT,
        tipo_tramitacao varchar(50) not null,
        primary key (id),

        constraint fk_tramitacao_demanda foreign key (demanda_id) references tbl_demanda (id),
        constraint fk_tramitacao_responsavel foreign key (responsavel_id) references tbl_pessoa (id)
    ) engine=InnoDB;

    create table tbl_configuracao_sistema (
        fim_ferias_coletivas date,
        inicio_ferias_coletivas date,
        id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    CREATE SEQUENCE seq_numero_processo START WITH 1 INCREMENT BY 1;

    create table tbl_processo (
        id bigint not null auto_increment,
        numero_processo varchar(50) not null,
        numero_processo_pje varchar(50),
        area_direito varchar(50),
        tribunal varchar(50),
        assunto varchar(255),
        vara varchar(100),
        status varchar(50),
        prazo_final date,
        criado_em datetime(6),
        atualizado_em datetime(6),
        responsavel varchar(100),
        advogado_id bigint,
        estagiario_id bigint,
        assistido_id bigint,

        primary key (id),
        constraint uk_processo_numero unique (numero_processo),
        constraint uk_processo_numero_pje unique (numero_processo_pje),
        constraint fk_processo_advogado foreign key (advogado_id) references tbl_advogado (pessoa_id),
        constraint fk_processo_estagiario foreign key (estagiario_id) references tbl_estagiario (pessoa_id),
        constraint fk_processo_assistido foreign key (assistido_id) references tbl_assistido (pessoa_id)
    ) engine=InnoDB;

    create table tbl_parte_envolvida (
        id bigint not null auto_increment,
        processo_id bigint not null,
        nome varchar(255) not null,
        papel varchar(50) not null,
        especificacao_papel varchar(50),

        primary key (id),
        constraint fk_parte_processo foreign key (processo_id) references tbl_processo (id)
    ) engine=InnoDB;

--     create table tbl_movimento (
--         advogado_id bigint,
--         criacao datetime(6),
--         id bigint not null auto_increment,
--         processo_id bigint not null,
--         primary key (id),
--         constraint fk_movimento_advogado foreign key (advogado_id) references tbl_advogado (pessoa_id),
--         constraint fk_movimento_processo foreign key (processo_id) references tbl_processo (id)
--     ) engine=InnoDB;
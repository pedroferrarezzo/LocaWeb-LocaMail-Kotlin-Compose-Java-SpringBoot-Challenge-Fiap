-- Criação da tabela T_LCW_USUARIO
CREATE TABLE T_LCW_USUARIO (
    id_usuario NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR2(255) UNIQUE NOT NULL,
    nome VARCHAR2(255),
    senha VARCHAR2(255),
    autenticado NUMBER,
    profile_image BLOB,
    selected_user NUMBER
);

-- Criação da tabela T_LCW_EMAIL
CREATE TABLE T_LCW_EMAIL (
    id_email NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    remetente VARCHAR2(255),
    destinatario VARCHAR2(255),
    cc VARCHAR2(255),
    cco VARCHAR2(255),
    assunto VARCHAR2(255),
    corpo CLOB,
    editavel NUMBER,
    enviado NUMBER,
    horario VARCHAR2(10),
    data VARCHAR2(10),
    agenda_atrelada NUMBER,
    CONSTRAINT fk_T_LCW_EMAIL_T_LCW_USUARIO FOREIGN KEY (id_usuario) REFERENCES T_LCW_USUARIO (id_usuario)
);

-- Criação da tabela T_LCW_ANEXO
CREATE TABLE T_LCW_ANEXO (
    id_anexo NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_email NUMBER NOT NULL,
    anexo BLOB,
    CONSTRAINT fk_T_LCW_ANEXO_T_LCW_EMAIL FOREIGN KEY (id_email) REFERENCES T_LCW_EMAIL (id_email)
);

-- Criação da tabela T_LCW_RESPOSTA_EMAIL
CREATE TABLE T_LCW_RESPOSTA_EMAIL (
    id_resposta_email NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_email NUMBER NOT NULL,
    id_usuario NUMBER NOT NULL,
    remetente VARCHAR2(255),
    destinatario VARCHAR2(255),
    cc VARCHAR2(255),
    cco VARCHAR2(255),
    assunto VARCHAR2(255),
    corpo CLOB,
    editavel NUMBER,
    enviado NUMBER,
    horario VARCHAR2(10),
    data VARCHAR2(10),
    CONSTRAINT fk_T_LCW_RESPOSTA_EMAIL_T_LCW_EMAIL FOREIGN KEY (id_email) REFERENCES T_LCW_EMAIL (id_email),
    CONSTRAINT fk_T_LCW_RESPOSTA_EMAIL_T_LCW_USUARIO FOREIGN KEY (id_usuario) REFERENCES T_LCW_USUARIO (id_usuario)
);

-- Criação da tabela T_LCW_ANEXO_RESPOSTA_EMAIL
CREATE TABLE T_LCW_ANEXO_RESPOSTA_EMAIL (
    id_anexo_resposta_email NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_resposta_email NUMBER NOT NULL,
    anexo BLOB,
    CONSTRAINT fk_T_LCW_ANEXO_RESPOSTA_EMAIL_T_LCW_RESPOSTA_EMAIL FOREIGN KEY (id_resposta_email) REFERENCES T_LCW_RESPOSTA_EMAIL (id_resposta_email)
);

-- Criação da tabela T_LCW_PASTA
CREATE TABLE T_LCW_PASTA (
    id_pasta NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    nome VARCHAR2(255),
    CONSTRAINT fk_T_LCW_PASTA_T_LCW_USUARIO FOREIGN KEY (id_usuario) REFERENCES T_LCW_USUARIO (id_usuario)
);

-- Criação da tabela T_LCW_ALTERACAO
CREATE TABLE T_LCW_ALTERACAO (
    id_alteracao NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    id_email NUMBER,
    id_pasta NUMBER,
    importante NUMBER,
    lido NUMBER,
    arquivado NUMBER,
    excluido NUMBER,
    spam NUMBER,
    CONSTRAINT fk_T_LCW_ALTERACAO_T_LCW_EMAIL FOREIGN KEY (id_email) REFERENCES T_LCW_EMAIL (id_email),
    CONSTRAINT fk_T_LCW_ALTERACAO_T_LCW_PASTA FOREIGN KEY (id_pasta) REFERENCES T_LCW_PASTA (id_pasta),
    CONSTRAINT fk_T_LCW_ALTERACAO_T_LCW_USUARIO FOREIGN KEY (id_usuario) REFERENCES T_LCW_USUARIO (id_usuario)
);

-- Criação da tabela T_LCW_AGENDA
CREATE TABLE T_LCW_AGENDA (
    id_agenda NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR2(255),
    descritivo VARCHAR2(255),
    id_usuario NUMBER NOT NULL,
    cor NUMBER,
    localizacao VARCHAR2(255),
    notificacao NUMBER,
    horario VARCHAR2(10),
    data VARCHAR2(10),
    proprietario VARCHAR2(255),
    evento NUMBER,
    tarefa NUMBER,
    repeticao NUMBER,
    grupo_repeticao NUMBER,
    visivel NUMBER,
    id_email NUMBER,
    CONSTRAINT fk_T_LCW_AGENDA_T_LCW_USUARIO FOREIGN KEY (id_usuario) REFERENCES T_LCW_USUARIO (id_usuario),
    CONSTRAINT fk_T_LCW_AGENDA_T_LCW_EMAIL FOREIGN KEY (id_email) REFERENCES T_LCW_EMAIL (id_email)
);

-- Criação da tabela T_LCW_CONVIDADO
CREATE TABLE T_LCW_CONVIDADO (
    id_convidado NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR2(255)
);

-- Criação da tabela T_LCW_AGENDA_CONVIDADO
CREATE TABLE T_LCW_AGENDA_CONVIDADO (
    id_agenda_convidado NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_agenda NUMBER NOT NULL,
    id_convidado NUMBER NOT NULL,
    grupo_repeticao NUMBER,
    CONSTRAINT fk_T_LCW_AGENDA_CONVIDADO_T_LCW_AGENDA FOREIGN KEY (id_agenda) REFERENCES T_LCW_AGENDA (id_agenda),
    CONSTRAINT fk_T_LCW_AGENDA_CONVIDADO_T_LCW_CONVIDADO FOREIGN KEY (id_convidado) REFERENCES T_LCW_CONVIDADO (id_convidado)
);

-- Criação da tabela T_LCW_AI_QUESTION
CREATE TABLE T_LCW_AI_QUESTION (
    id_question NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_email NUMBER NOT NULL,
    pergunta CLOB,
    CONSTRAINT fk_T_LCW_AI_QUESTION_T_LCW_EMAIL FOREIGN KEY (id_email) REFERENCES T_LCW_EMAIL (id_email)
);
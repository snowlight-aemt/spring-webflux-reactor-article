CREATE TABLE IF NOT EXISTS TB_ARTICLE (
    id bigint auto_increment primary key,
    title VARCHAR(255),
    body VARCHAR(2000),
    author_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
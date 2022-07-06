CREATE TABLE users
(
    id                   BIGSERIAL PRIMARY KEY,
    name                 VARCHAR(150) UNIQUE NOT NULL,
    password             VARCHAR(255) NOT NULL,
    role                 VARCHAR(50) NOT NULL,
    attempt              INTEGER DEFAULT(0),
    time_count_start     INTEGER DEFAULT(0),
    time_count_end       INTEGER DEFAULT(0)
);

INSERT INTO users (name, password, role) VALUES
('Alisa', '$2a$12$O6IacyAoBYbfdygb08ZLOOkcHpXeOPQDwAvqPyFzE.WJ6ljWEix2.', 'ADMIN');

CREATE TABLE sex
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL
);

INSERT INTO sex(name) VALUES
('male'),
('female');

CREATE TABLE type_of_animal
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL
);

INSERT INTO type_of_animal(name) VALUES
('dog'),
('cat'),
('duck'),
('horse');

CREATE TABLE animals
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) UNIQUE NOT NULL,
    birthday    DATE,
    type_id     BIGSERIAL REFERENCES type_of_animal(id),
    sex_id      BIGSERIAL REFERENCES sex(id)
);

INSERT INTO animals(name, birthday, type_id, sex_id) VALUES
('Kill', '2002-05-12', 2, 1),
('Tinkie','2004-03-03', 4, 2),
('Fill','2007-11-22', 1, 1);

CREATE TABLE animals_of_users
(
    user_id     BIGSERIAL REFERENCES users(id),
    animal_id   BIGSERIAL REFERENCES animals(id)
);

INSERT INTO animals_of_users(user_id, animal_id) VALUES
(1, 1),
(1, 2);

CREATE TABLE errors_and_info
(   id          BIGSERIAL PRIMARY KEY,
    info        VARCHAR(255)
);

INSERT INTO errors_and_info(info) VALUES
('Неверный пароль!'),
('Имя свободно'),
('Пользователь с таким именем уже существует!'),
('Успешный вход'),
('Превышен лимит попыток'),
('Пользователь уже авторизирован');

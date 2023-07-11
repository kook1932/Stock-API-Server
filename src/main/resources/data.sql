insert into "user" (username, password, birth_day) values ('admin', '$2a$10$g3h1dpG5l/DvWFRMvlTPnOsRhLMUXqY5R6sgEuEy9LWez9PZ5FGPq', '19940606');
insert into "user" (username, password, birth_day) values ('user', '$2a$10$K7k28XApvsMUkOg4hRuZ1.fiPFLLSVuoqXES.ENyIQiwrE/eiFGJy', '19940606');

insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values (1, 'ROLE_USER');
insert into user_authority (user_id, authority_name) values (1, 'ROLE_ADMIN');
insert into user_authority (user_id, authority_name) values (2, 'ROLE_USER');
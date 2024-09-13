-- Consultas básicas
select * from user;
select * from user_roles;
select * from role;
select * from refresh_token;

-- Usuarios logueados
select 
	user.id, 
    user.username, 
    refresh_token.expiry_date, 
    refresh_token.token 
from user
inner join refresh_token on refresh_token.user_id = user.id;

-- Roles de usuarios
select 
	user.username,
    role.name
from user
inner join user_roles on user_roles.user_id = user.id
inner join role on user_roles.role_id = role.id;

-- Reiniciar base de datos
drop database matias_project;
create database matias_project;
use matias_project;
insert into role values (1, 'ROLE_USER');
insert into role values (2, 'ROLE_MODERATOR');
insert into role values (3, 'ROLE_ADMIN');
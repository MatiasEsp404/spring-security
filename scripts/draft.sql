-- Consultas básicas
select * from auth_user;
select * from auth_user_roles;
select * from auth_role;
select * from auth_refresh_token;

-- Usuarios logueados
select 
	u.id, 
    u.username, 
    t.expiry_date, 
    t.token 
from auth_user u
inner join auth_refresh_token t on t.user_id = u.id;

-- Roles de usuarios
select 
	u.username,
    r.name
from auth_user u
inner join auth_user_roles ur on ur.user_id = u.id
inner join auth_role r on ur.role_id = r.id;

-- Reiniciar base de datos
drop database matias_project;
create database matias_project;
use matias_project;
insert into auth_role values (1, 'ROLE_USER');
insert into auth_role values (2, 'ROLE_MODERATOR');
insert into auth_role values (3, 'ROLE_ADMIN');
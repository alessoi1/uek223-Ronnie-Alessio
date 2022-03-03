insert into public.authority (id, name) values ('7420e40b-7dc8-49f9-9a83-a897205622f6', 'CAN_READ_MYLISTENTRY');
insert into public.authority (id, name) values ('19bbf652-f5ee-4d10-9c69-ae4d09c0f66a', 'CAN_EDIT_MYLISTENTRY');
insert into public.authority (id, name) values ('9fffbc65-3dc6-478c-b924-0b9f079e2261', 'ADMIN');

insert into public.role (id, name) values ('c51dbbd6-6263-404a-93c4-668721ad2411', 'ADMIN');
insert into public.role (id, name) values ('558a4d28-6b63-4c57-a4f7-7ac3a793307d', 'USER');

insert into public.role_authorities (role_id, authority_id) values ('c51dbbd6-6263-404a-93c4-668721ad2411', '9fffbc65-3dc6-478c-b924-0b9f079e2261');
insert into public.role_authorities (role_id, authority_id) values ('558a4d28-6b63-4c57-a4f7-7ac3a793307d', '7420e40b-7dc8-49f9-9a83-a897205622f6');
insert into public.role_authorities (role_id, authority_id) values ('558a4d28-6b63-4c57-a4f7-7ac3a793307d', '19bbf652-f5ee-4d10-9c69-ae4d09c0f66a');

insert into public.users (id, email, password, username) values ('b13318dd-685f-4844-af86-5d9a2b77be77', 'admin@admin.admin', '$2a$10$Df6ERvMBzNHH9PmQPUCkAuF23JyYnhxF5GZMG0v/HPzlAAMLKsNiO', 'admin');
insert into public.users (id, email, password, username) values ('8b5351c9-1fe1-475e-bb77-3ac4c3732e6e', 'silvan.egger@mi6.com', '$2a$10$7sm3SnGbetDgRqL4Wgd17eisU8T0vEeZdEbFOnBr1izyeVTyZjJLS', 'silvan');
insert into public.users (id, email, password, username) values ('53ce8409-638a-4de0-bc37-493f3bf9f8f8', 'markus.stein@mi6.com', '$2a$10$EBj.SuoaeUWCDohxNdHc.eS02ijIc0kLpaz.Y7/VGyKh7pKT2xnf6', 'markus');

insert into public.users_roles (user_id, role_id) values ('b13318dd-685f-4844-af86-5d9a2b77be77', 'c51dbbd6-6263-404a-93c4-668721ad2411');
insert into public.users_roles (user_id, role_id) values ('8b5351c9-1fe1-475e-bb77-3ac4c3732e6e', '558a4d28-6b63-4c57-a4f7-7ac3a793307d');
insert into public.users_roles (user_id, role_id) values ('53ce8409-638a-4de0-bc37-493f3bf9f8f8', '558a4d28-6b63-4c57-a4f7-7ac3a793307d');

insert into public.mylistentry (id, erstellungsdatum, text, titel, wichtigkeit) values ('9f743bf2-c319-49ea-a3f1-8ed886b5034e', '2021-12-08 13:33:55.167000', 'Markus Text', 'Markus Blog', 5);
insert into public.mylistentry (id, erstellungsdatum, text, titel, wichtigkeit) values ('b2141b00-d437-4309-92b2-5e27c00ed387', '2022-03-01 13:33:55.144000', 'Ich bin ein Admin', 'Admins Blog', 2);
insert into public.mylistentry (id, erstellungsdatum, text, titel, wichtigkeit) values ('3a1d51a9-f509-439b-b0a4-51725c990083', '2022-01-05 13:33:55.155000', 'Hallo. Ich bin Silvan.', 'Mein 1. Blog', 8);
insert into public.mylistentry (id, erstellungsdatum, text, titel, wichtigkeit) values ('6da70499-e0e9-4c97-a0f6-bdac943b3bdb', '2021-08-06 13:33:55.163000', 'blablablabla blabla blabla', 'Hello World!', 3);
insert into public.mylistentry (id, erstellungsdatum, text, titel, wichtigkeit) values ('b91e7f23-bacb-45fa-b5e4-332b86471f27', '2022-02-27 13:33:55.159000', 'Ich bin immer noch Silvan', 'Mein zweiter Blog', 3);
insert into public.mylistentry (id, erstellungsdatum, text, titel, wichtigkeit) values ('fe8bfcc8-0950-45dc-a82d-d07529233a4f', '2022-03-03 13:38:03.500000', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr.', 'Blogpost ADMIN', 4);

insert into public.mylistentry_user (user_id, mylistentry_id) values ('b13318dd-685f-4844-af86-5d9a2b77be77', 'b2141b00-d437-4309-92b2-5e27c00ed387');
insert into public.mylistentry_user (user_id, mylistentry_id) values ('8b5351c9-1fe1-475e-bb77-3ac4c3732e6e', '3a1d51a9-f509-439b-b0a4-51725c990083');
insert into public.mylistentry_user (user_id, mylistentry_id) values ('8b5351c9-1fe1-475e-bb77-3ac4c3732e6e', 'b91e7f23-bacb-45fa-b5e4-332b86471f27');
insert into public.mylistentry_user (user_id, mylistentry_id) values ('8b5351c9-1fe1-475e-bb77-3ac4c3732e6e', '6da70499-e0e9-4c97-a0f6-bdac943b3bdb');
insert into public.mylistentry_user (user_id, mylistentry_id) values ('53ce8409-638a-4de0-bc37-493f3bf9f8f8', '9f743bf2-c319-49ea-a3f1-8ed886b5034e');
insert into public.mylistentry_user (user_id, mylistentry_id) values ('b13318dd-685f-4844-af86-5d9a2b77be77', 'fe8bfcc8-0950-45dc-a82d-d07529233a4f');

create table Motorista(nome char(30) not null, NCartaCond char(15) not null, DataNasc date not null, Nbi integer unique not null check(Nbi>0),primary key(Nbi));

create table Telefone(Nbi integer not null check(Nbi>0),telefone integer unique not null check(telefone>0),primary key(telefone),foreign key(Nbi) references motorista on delete restrict);

create table Modelo(marca char(15) not null,modelo char(30) unique not null,Nlugares integer not null check(Nlugares>0),Consumo float not null check(consumo>0.0),primary key(modelo));

create table Taxi(modelo char(30) not null,ano integer not null check(ano>0),kms integer not null check(kms>0),matricula char(10) unique not null,primary key(matricula),foreign key(modelo) references modelo on delete restrict);

create table Servico(datainicio timestamp unique not null, datafim timestamp not null, kms float not null check(kms>0.0), valor float not null check(valor>0.0),matricula char(10) not null,primary key(matricula,datainicio),foreign key(matricula) references taxi on delete restrict);

create table Turno(datainicio timestamp not null,datafim timestamp not null,kminicio integer not null check(kminicio>0),kmfim integer not null check(kmfim>0),matricula char(10) not null,Nbi integer not null check(Nbi>0),primary key(matricula,Nbi,datainicio),foreign key(matricula) references taxi on delete restrict,foreign key(Nbi)references motorista on delete restrict);

create table Cliente(nome char(30) not null,morada char(40) not null, codigopostal char(30) not null,nif bigserial unique not null check(Nif>0),primary key(nif));

create table Pedido(nif bigserial not null check(Nif>0),moradainicio char(30) not null,codigopostalinicio char(30) not null,datapedido timestamp not null,matricula char(10) not null,datainicio timestamp not null,primary key(nif,moradainicio,Datainicio),foreign key(nif) references cliente on delete restrict,foreign key(matricula,datainicio) references servico on delete restrict);


insert into modelo values('Renault','Espace',7,7);

insert into modelo values('Mercedes','CLK',7,9);

insert into modelo values('Honda','Civic',5,5);

insert into modelo values('Mercedes','Mercedes-Benz Classe S',5,6.5);

insert into taxi values('Espace',2005,123098,'22-AA-22');

insert into taxi values('CLK',2004,234554,'21-AA-22');

insert into taxi values('Civic',2006,89764,'20-AA-22');

insert into taxi values('Mercedes-Benz Classe S',2005,79744,'19-AA-22');

insert into motorista values('Manuel Duarte','L-123','14-1-1966',1234);

insert into motorista values('Fernando Nobre','L-124','14-1-1967',1235);

insert into motorista values('Anibal Silva','L-125','14-1-1968',1236);

insert into motorista values('Francisco Lopes','L-126','14-1-1968',1237);

insert into telefone values(1234,266262626);

insert into telefone values(1234,939393939);

insert into telefone values(1235,266262627);

insert into telefone values(1235,939393940);

insert into telefone values(1236,266262628);

insert into telefone values(1236,939393941);

insert into telefone values(1237,266262629);

insert into cliente values('Jose Silva','Rua Antonio Silva 23','7100-131 Evora', 600700800900);

insert into cliente values('Francisco Passos','Rua Manuel Passos 12','7100-131 Evora',600700800901);

insert into cliente values('Pedro Sousa','Rua Joaquim Sousa 21','7500-313 Evora',600700800902);

insert into turno values('2-1-2010 8:00','2-1-2010 17:00',79744,79944,'19-AA-22',1234);

insert into turno values('2-1-2010 8:00','2-1-2010 17:00',89764,89964,'20-AA-22',1235);

insert into turno values('3-1-2010 8:00','3-1-2010 17:00',234554,234954,'21-AA-22',1236);

insert into turno values('3-1-2010 8:00','3-1-2010 17:00',123098,123498,'22-AA-22',1237);

insert into turno values('3-2-2010 08:00:00','3-2-2010 17:00:00',99764, 100050,'20-AA-22',1235);

insert into turno values('2-1-2010 8:00:00', '2-1-2010 17:00:00',122050,123498,'22-AA-22',1237);

insert into servico values('2-1-2010 8:12','2-1-2010 8:32',12,5.25,'19-AA-22');

insert into servico values('2-1-2010 8:43','2-1-2010 8:52',7,3.25,'19-AA-22');

insert into servico values('2-1-2010 8:53','2-1-2010 9:59',98,53.25,'19-AA-22');

insert into servico values('2-1-2010 10:13','2-1-2010 10:29',18,19.25,'19-AA-22');

insert into servico values('2-1-2010 11:10','2-1-2010 11:39',23,22.25,'19-AA-22');

insert into servico values('2-1-2010 12:00','2-1-2010 13:39',21,42.25,'19-AA-22');

insert into servico values('2-1-2010 15:20','2-1-2010 15:39',9,12.25,'19-AA-22');

insert into servico values('2-1-2010 9:00','2-1-2010 10:30:00',15,13.50,'19-AA-22');

insert into servico values('2-1-2010 11:00:00','2-1-2010 14:00:00',90,65.90,'20-AA-22');

insert into servico values('3-1-2010 9:00:00','3-1-2010 11:00:00',80,60,'22-AA-22');

insert into servico values('2-1-2010 14:00:00','2-1-2010 15:00:00',40,30,'22-AA-22');

insert into servico values('2-1-2010 10:00:00','2-1-2010 11:00:00',15,14,'19-AA-22');

insert into pedido values(600700800900,'Rua Silva Pais 33','7120-212 Evora','2-1-2010 9:00:00','19-AA-22','2-1-2010 8:43:00');

insert into pedido values(600700800901,'Rua Nova 44','7050-123 Montemor','2-1-2010 9:30:00','19-AA-22','2-1-2010 10:00:00');

insert into pedido values(600700800901,'Rua Vasco da Gama 15','7050-345 Montemor','2-1-2010 10:50:00','20-AA-22','2-1-2010 11:00:00');

insert into pedido values(600700800902,'Rua Alvares Pereira 7','7100-131 Evora','3-1-2010 8:45:00','22-AA-22','3-1-2010 9:00:00');

insert into pedido values(600700800900,'Rua Serpa Pinto 10','7100-132 Evora','2-1-2010 13:45:00','22-AA-22','2-1-2010 14:00:00'); 

insert into pedido values(600700800900,'Rua Alfredo Dinis 8','7050-500 Silveiras','2-1-2010 8:00:00','19-AA-22','2-1-2010 08:12:00');

insert into pedido values(600700800902,'Rua Machado dos Santos 9','7000-456 Reguengos','2-1-2010 08:00:00','19-AA-22','2-1-2010 09:00:00');

insert into pedido values(600700800901,'Rua Almirante Reis 45','7000-212 Evora','2-1-2010 08:40:00','19-AA-22','2-1-2010 08:53:00');

insert into pedido values(600700800902,'Rua da Moagem 35','7200-123 Vendas Novas','2-1-2010 10:00:00','19-AA-22','2-1-2010 10:13:00');

insert into pedido values(600700800900,'Rua Fernao de Magalhaes 78','7000-321 Evora','2-1-2010 10:50:00','19-AA-22','2-1-2010 11:10:00');

insert into pedido values(600700800900,'Rua Marechal Gomes da Costa 23','7000-456 Evora','2-1-2010 11:40:00','19-AA-22','2-1-2010 12:00:00');

insert into pedido values(600700800901,'Rua Fontes Pereira de Melo 6','7600-678 Beja','2-1-2010 15:00:00','19-AA-22','2-1-2010 15:20:00');
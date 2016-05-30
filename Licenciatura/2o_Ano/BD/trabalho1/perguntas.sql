select matricula from taxi natural inner join modelo where marca='Mercedes';

select nome from motorista natural inner join turno natural inner join taxi natural inner join modelo where marca = 'Mercedes';

select distinct telefone from telefone natural inner join turno natural inner join (select matricula from pedido natural inner join servico where nif=600700800900) as M  where turno.nbi = telefone.nbi;

select matricula from motorista natural inner join turno where nome = 'Anibal Silva';

(select nome from motorista) except (select nome from motorista natural inner join turno natural inner join (select matricula from cliente natural inner join pedido natural inner join servico where nome ='Jose Silva' and pedido.nif = cliente.nif) as M  where turno.nbi = motorista.nbi);

(select nome from motorista natural inner join turno natural inner join taxi natural inner join modelo where turno.matricula = taxi.matricula and motorista.nbi = turno.nbi) except (select nome from motorista natural inner join turno natural inner join taxi natural inner join modelo where marca = 'Mercedes' and turno.matricula = taxi.matricula and motorista.nbi = turno.nbi);

select nome, count(nbi) as num_servicos from motorista natural inner join turno natural inner join (select matricula from servico)as SM group by nome;

select nome,sum(valor) as lucro from motorista natural inner join turno natural inner join (select matricula,valor from servico) as SVM group by nome;

select nome, receita from (select nome,sum(valor)as receita,datainicio from motorista natural inner join  turno natural inner join (select matricula,valor,datainicio as D from servico)as S where turno.matricula = s.matricula and S.d between turno.datainicio and turno.datafim and motorista.nbi = turno.nbi  group by nome, datainicio)as L  where receita = (select max(receita)as mais_lucro from (select nome,sum(valor) as receita,datainicio from motorista natural inner join turno natural inner join (select matricula,valor,datainicio as D from servico)as S where turno.matricula = S.matricula and S.D between turno.datainicio and turno.datafim and motorista.nbi=turno.nbi group by nome,datainicio)as ML); 

select matricula,marca,modelo from (select matricula,marca,modelo, max(kmfim-kminicio) as maxKm from modelo natural inner join taxi natural inner join turno group by matricula,marca,modelo)as MK where maxkm = (select max(maxkm) as themax from (select matricula,marca,modelo,max(kmfim-kminicio) as maxKm from modelo natural inner join taxi natural inner join turno group by matricula,marca,modelo) as maxk);

select avg(tempo_medio_espera)as tempo_medio_espera_companhia  from (select all nif,pedido.datapedido,servico.datainicio as inicio_servico, matricula,avg(servico.datainicio - pedido.datapedido)as tempo_medio_espera from servico natural inner join pedido group by nif,pedido.datapedido,servico.datainicio,matricula)as tempo_medio;

select nome, max(num_pedidos)as maxpedidos from cliente natural inner join (select nif,count(pedido.datapedido)as num_pedidos from pedido group by nif) as NP where cliente.nif = NP.nif and num_pedidos = (select max(num_pedidos) as MaxP from (select nif,count(pedido.datapedido) as num_pedidos from pedido group by nif) as NP) group by nome;

select marca,modelo,matricula,(total_valor - gastokm)as ganhos from modelo natural inner join taxi natural inner join (select matricula,(total_kms * precokm)as gastokm from (select matricula,((consumo/100)*1.3)as precokm from modelo natural inner join taxi)as PKm
natural inner join (select matricula,sum(kms)as total_kms from servico group by matricula)as Tkm where Pkm.matricula = Tkm.matricula) as matprecokm natural inner join (select matricula,sum(valor)as total_valor from servico group by matricula)as K where k.matricula = matprecokm.matricula and (total_valor - gastokm) = (select max(ganhos)as maislucrativo 
 from (select matricula,(total_valor - gastokm)as ganhos from (select matricula,(total_kms * precokm)as gastokm from (select matricula,((consumo/100)*1.3)as precokm from modelo natural inner join taxi)as PKm natural inner join (select matricula,sum(kms)as total_kms from servico group by matricula)as Tkm where Pkm.matricula = Tkm.matricula) as matprecokm
 natural inner join (select matricula,sum(valor)as total_valor from servico group by matricula)as K where k.matricula = matprecokm.matricula)as ganhos_por_taxi) and taxi.modelo = modelo.modelo;

select distinct nome,km_fora_serv from motorista natural inner join turno natural inner join (select matricula, sum(total - kmsserv) as km_fora_serv from (select matricula,(kmfim - kminicio) as total from turno)as kmt natural inner join (select matricula,sum(kms)as kmsserv from servico group by matricula) as kms where kmt.matricula = kms.matricula group by matricula)as foradeservico where motorista.nbi = turno.nbi and turno.matricula = foradeservico.matricula and km_fora_serv>0;

select nome from motorista natural inner join (select min,max,matricula from (select matricula,max,min,td,kd from (select max(kms),matricula,datainicio as kd from servico group by matricula,kd)as K natural inner join (select min(datafim-datainicio),matricula,datainicio as td from servico group by matricula,td)as T where t.td = k.kd and k.matricula = t.matricula group by matricula,max,min,td,kd)as MaxMin natural inner join pedido where pedido.matricula = MaxMin.matricula and pedido.datainicio = MaxMin.td and pedido.datainicio = MaxMin.kd and max = (select max(kms) from servico)  group by matricula,min,max) as maxkm_mintime natural inner join turno where turno.matricula = maxkm_mintime.matricula and motorista.nbi = turno.nbi;

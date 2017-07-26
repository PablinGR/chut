/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     26/07/2017 10:54:13                          */
/*==============================================================*/


drop table if exists ARBITROS;

drop table if exists CAMPEONATOS;

drop table if exists CANCHAS;

drop table if exists CANCHASXPROPIETARIO;

drop table if exists EQUIPOS;

drop table if exists HORARIOS;

drop table if exists JUGADORES;

drop table if exists PARTIDOS;

drop table if exists PATROCINADORES;

drop table if exists PROPIETARIOS;

drop table if exists RESERVAS;

/*==============================================================*/
/* Table: ARBITROS                                              */
/*==============================================================*/
create table ARBITROS
(
   CEDULAARBITRO        char(10) not null,
   IDCAMPEONATO         int not null,
   NOMBREARBITRO        varchar(50) not null,
   primary key (CEDULAARBITRO)
);

/*==============================================================*/
/* Table: CAMPEONATOS                                           */
/*==============================================================*/
create table CAMPEONATOS
(
   IDCAMPEONATO         int not null,
   IDRESERVA            int,
   RUCPATROCINADOR      char(13) not null,
   DESCRIPCIONCAMPEONATO varchar(30) not null,
   MAILCAMPEONATO       varchar(50) not null,
   CELULARCAMPEONATO    bigint not null,
   PREMIOCAMPEONATO     float(8,2) not null,
   primary key (IDCAMPEONATO)
);

/*==============================================================*/
/* Table: CANCHAS                                               */
/*==============================================================*/
create table CANCHAS
(
   IDCANCHA             int not null,
   DESCRIPCIONCANCHA    varchar(50) not null,
   SECTORCANCHA         varchar(30) not null,
   primary key (IDCANCHA)
);

/*==============================================================*/
/* Table: CANCHASXPROPIETARIO                                   */
/*==============================================================*/
create table CANCHASXPROPIETARIO
(
   CEDULAPROPIETARIOS   varchar(13) not null,
   IDCANCHA             int not null,
   primary key (CEDULAPROPIETARIOS, IDCANCHA)
);

/*==============================================================*/
/* Table: EQUIPOS                                               */
/*==============================================================*/
create table EQUIPOS
(
   IDEQUIPO             int not null,
   CEDULAJUGADOR        char(10) not null,
   DESCRIPCIONEQUIPO    varchar(40) not null,
   primary key (IDEQUIPO)
);

/*==============================================================*/
/* Table: HORARIOS                                              */
/*==============================================================*/
create table HORARIOS
(
   IDHORARIO            int not null,
   IDRESERVA            int not null,
   DESCRIPCIONHORARIO   varchar(50) not null,
   INICIOHORARIO        date not null,
   FINHORARIO           date not null,
   primary key (IDHORARIO)
);

/*==============================================================*/
/* Table: JUGADORES                                             */
/*==============================================================*/
create table JUGADORES
(
   CEDULAJUGADOR        char(10) not null,
   NOMBREJUGADOR        varchar(50) not null,
   CELULARJUGADOR       bigint not null,
   MAILJUGADOR          varchar(50) not null,
   CAPITAN              bool,
   primary key (CEDULAJUGADOR)
);

/*==============================================================*/
/* Table: PARTIDOS                                              */
/*==============================================================*/
create table PARTIDOS
(
   IDPARTIDO            int not null,
   IDEQUIPO             int not null,
   IDCANCHA             int,
   FECHAPARTIDO         date not null,
   primary key (IDPARTIDO)
);

/*==============================================================*/
/* Table: PATROCINADORES                                        */
/*==============================================================*/
create table PATROCINADORES
(
   RUCPATROCINADOR      char(13) not null,
   DESCRIPCIONPATROCINADOR varchar(50) not null,
   primary key (RUCPATROCINADOR)
);

/*==============================================================*/
/* Table: PROPIETARIOS                                          */
/*==============================================================*/
create table PROPIETARIOS
(
   CEDULAPROPIETARIOS   varchar(13) not null,
   NOMBREPROPIETARIOS   varchar(50) not null,
   CELULARPROPIETARIOS  bigint not null,
   MAILPROPIETARIOS     varchar(50) not null,
   primary key (CEDULAPROPIETARIOS)
);

/*==============================================================*/
/* Table: RESERVAS                                              */
/*==============================================================*/
create table RESERVAS
(
   IDRESERVA            int not null,
   CEDULAJUGADOR        char(10),
   IDPARTIDO            int not null,
   primary key (IDRESERVA)
);

alter table ARBITROS add constraint FK_CONTROLA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO) on delete restrict on update restrict;

alter table CAMPEONATOS add constraint FK_ASOCIA foreign key (IDRESERVA)
      references RESERVAS (IDRESERVA) on delete restrict on update restrict;

alter table CAMPEONATOS add constraint FK_ORGANIZAN foreign key (RUCPATROCINADOR)
      references PATROCINADORES (RUCPATROCINADOR) on delete restrict on update restrict;

alter table CANCHASXPROPIETARIO add constraint FK_CANCHASXPROPIETARIO foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA) on delete restrict on update restrict;

alter table CANCHASXPROPIETARIO add constraint FK_CANCHASXPROPIETARIO2 foreign key (CEDULAPROPIETARIOS)
      references PROPIETARIOS (CEDULAPROPIETARIOS) on delete restrict on update restrict;

alter table EQUIPOS add constraint FK_CONFORMAN foreign key (CEDULAJUGADOR)
      references JUGADORES (CEDULAJUGADOR) on delete restrict on update restrict;

alter table HORARIOS add constraint FK_REGISTRAN foreign key (IDRESERVA)
      references RESERVAS (IDRESERVA) on delete restrict on update restrict;

alter table PARTIDOS add constraint FK_JUEGAN foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO) on delete restrict on update restrict;

alter table PARTIDOS add constraint FK_UTILIZAN foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA) on delete restrict on update restrict;

alter table RESERVAS add constraint FK_ORGANIZA foreign key (CEDULAJUGADOR)
      references JUGADORES (CEDULAJUGADOR) on delete restrict on update restrict;

alter table RESERVAS add constraint FK_TIENE foreign key (IDPARTIDO)
      references PARTIDOS (IDPARTIDO) on delete restrict on update restrict;


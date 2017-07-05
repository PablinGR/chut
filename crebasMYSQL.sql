/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     5/7/2017 8:53:30                             */
/*==============================================================*/


drop table if exists ARBITROS;

drop table if exists CAMPEONATOS;

drop table if exists CANCHAS;

drop table if exists CANCHASXPROPIETARIO;

drop table if exists EQUIPOS;

drop table if exists HORARIOS;

drop table if exists JUGADORES;

drop table if exists JUGADORXEQUIPO;

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
   IDPARTIDO            int,
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
   DESCRIPCIONEQUIPO    varchar(40) not null,
   primary key (IDEQUIPO)
);

/*==============================================================*/
/* Table: HORARIOS                                              */
/*==============================================================*/
create table HORARIOS
(
   IDHORARIO            int not null,
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
   primary key (CEDULAJUGADOR)
);

/*==============================================================*/
/* Table: JUGADORXEQUIPO                                        */
/*==============================================================*/
create table JUGADORXEQUIPO
(
   IDEQUIPO             int not null,
   CEDULAJUGADOR        char(10) not null,
   primary key (IDEQUIPO, CEDULAJUGADOR)
);

/*==============================================================*/
/* Table: PARTIDOS                                              */
/*==============================================================*/
create table PARTIDOS
(
   IDPARTIDO            int not null,
   IDEQUIPO             int not null,
   IDCANCHA             int,
   IDRESERVA            int,
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
   IDPARTIDO            int not null,
   IDHORARIO            int not null,
   IDCAMPEONATO         int not null,
   CAPITANRESERVA       varchar(50) not null,
   primary key (IDRESERVA)
);

alter table ARBITROS add constraint FK_CONTROLA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO) on delete restrict on update restrict;

alter table CAMPEONATOS add constraint FK_CAMPEONATOSXPATROCINADORES foreign key (RUCPATROCINADOR)
      references PATROCINADORES (RUCPATROCINADOR) on delete restrict on update restrict;

alter table CANCHAS add constraint FK_UTILIZAN3 foreign key (IDPARTIDO)
      references PARTIDOS (IDPARTIDO) on delete restrict on update restrict;

alter table CANCHASXPROPIETARIO add constraint FK_CANCHASXPROPIETARIO foreign key (CEDULAPROPIETARIOS)
      references PROPIETARIOS (CEDULAPROPIETARIOS) on delete restrict on update restrict;

alter table CANCHASXPROPIETARIO add constraint FK_CANCHASXPROPIETARIO2 foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA) on delete restrict on update restrict;

alter table JUGADORXEQUIPO add constraint FK_JUGADORXEQUIPO foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO) on delete restrict on update restrict;

alter table JUGADORXEQUIPO add constraint FK_JUGADORXEQUIPO2 foreign key (CEDULAJUGADOR)
      references JUGADORES (CEDULAJUGADOR) on delete restrict on update restrict;

alter table PARTIDOS add constraint FK_JUEGAN foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO) on delete restrict on update restrict;

alter table PARTIDOS add constraint FK_TIENE2 foreign key (IDRESERVA)
      references RESERVAS (IDRESERVA) on delete restrict on update restrict;

alter table PARTIDOS add constraint FK_UTILIZAN2 foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA) on delete restrict on update restrict;

alter table RESERVAS add constraint FK_ASOCIA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO) on delete restrict on update restrict;

alter table RESERVAS add constraint FK_REGISTRAN foreign key (IDHORARIO)
      references HORARIOS (IDHORARIO) on delete restrict on update restrict;

alter table RESERVAS add constraint FK_TIENE3 foreign key (IDPARTIDO)
      references PARTIDOS (IDPARTIDO) on delete restrict on update restrict;


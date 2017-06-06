/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     28/05/2017 21:00:06                          */
/*==============================================================*/


alter table ARBITROS
   drop constraint FK_ARBITROS_CONTROLA_CAMPEONA;

alter table CAMPEONATOSXPATROCINADORES
   drop constraint FK_CAMPEONA_CAMPEONAT_PATROCIN;

alter table CAMPEONATOSXPATROCINADORES
   drop constraint FK_CAMPEONA_CAMPEONAT_CAMPEONA;

alter table CANCHASXPROPIETARIO
   drop constraint FK_CANCHASX_CANCHASXP_PROPIETA;

alter table CANCHASXPROPIETARIO
   drop constraint FK_CANCHASX_CANCHASXP_CANCHAS;

alter table JUGADORES
   drop constraint FK_JUGADORE_CONFORMAD_EQUIPOS;

alter table PARTIDOS
   drop constraint FK_PARTIDOS_JUEGAN_EQUIPOS;

alter table PARTIDOS
   drop constraint FK_PARTIDOS_UTILIZAN_CANCHAS;

alter table RESERVAS
   drop constraint FK_RESERVAS_ASOCIA_CAMPEONA;

alter table RESERVAS
   drop constraint FK_RESERVAS_REGISTRAN_HORARIOS;

alter table RESERVAS
   drop constraint FK_RESERVAS_TIENE_PARTIDOS;

drop index CONTROLA_FK;

drop table ARBITROS cascade constraints;

drop table CAMPEONATOS cascade constraints;

drop index CAMPEONATOSXPATROCINADORES_FK;

drop index CAMPEONATOSXPATROCINADORES2_FK;

drop table CAMPEONATOSXPATROCINADORES cascade constraints;

drop table CANCHAS cascade constraints;

drop index CANCHASXPROPIETARIO_FK;

drop index CANCHASXPROPIETARIO2_FK;

drop table CANCHASXPROPIETARIO cascade constraints;

drop table EQUIPOS cascade constraints;

drop table HORARIOS cascade constraints;

drop index CONFORMADO_FK;

drop table JUGADORES cascade constraints;

drop index JUEGAN_FK;

drop table PARTIDOS cascade constraints;

drop table PATROCINADORES cascade constraints;

drop table PROPIETARIOS cascade constraints;

drop index ASOCIA_FK;

drop index REGISTRAN_FK;

drop index TIENE2_FK;

drop table RESERVAS cascade constraints;

/*==============================================================*/
/* Table: ARBITROS                                              */
/*==============================================================*/
create table ARBITROS 
(
   CEDULAARBITRO        CHAR(10)             not null,
   IDCAMPEONATO         INTEGER              not null,
   NOMBREARBITRO        VARCHAR2(50)         not null,
   constraint PK_ARBITROS primary key (CEDULAARBITRO)
);

/*==============================================================*/
/* Index: CONTROLA_FK                                           */
/*==============================================================*/
create index CONTROLA_FK on ARBITROS (
   IDCAMPEONATO ASC
);

/*==============================================================*/
/* Table: CAMPEONATOS                                           */
/*==============================================================*/
create table CAMPEONATOS 
(
   IDCAMPEONATO         INTEGER              not null,
   DESCRIPCIONCAMPEONATO VARCHAR2(30)         not null,
   MAILCAMPEONATO       VARCHAR2(50)         not null,
   CELULARCAMPEONATO    INTEGER              not null,
   PREMIOCAMPEONATO     NUMBER(8,2)          not null,
   constraint PK_CAMPEONATOS primary key (IDCAMPEONATO)
);

/*==============================================================*/
/* Table: CAMPEONATOSXPATROCINADORES                            */
/*==============================================================*/
create table CAMPEONATOSXPATROCINADORES 
(
   IDCAMPEONATO         INTEGER              not null,
   RUCPATROCINADOR      CHAR(13)             not null,
   constraint PK_CAMPEONATOSXPATROCINADORES primary key (IDCAMPEONATO, RUCPATROCINADOR)
);

/*==============================================================*/
/* Index: CAMPEONATOSXPATROCINADORES2_FK                        */
/*==============================================================*/
create index CAMPEONATOSXPATROCINADORES2_FK on CAMPEONATOSXPATROCINADORES (
   IDCAMPEONATO ASC
);

/*==============================================================*/
/* Index: CAMPEONATOSXPATROCINADORES_FK                         */
/*==============================================================*/
create index CAMPEONATOSXPATROCINADORES_FK on CAMPEONATOSXPATROCINADORES (
   RUCPATROCINADOR ASC
);

/*==============================================================*/
/* Table: CANCHAS                                               */
/*==============================================================*/
create table CANCHAS 
(
   IDCANCHA             INTEGER              not null,
   DESCRIPCIONCANCHA    VARCHAR2(50)         not null,
   SECTORCANCHA         VARCHAR2(30)         not null,
   constraint PK_CANCHAS primary key (IDCANCHA)
);

/*==============================================================*/
/* Table: CANCHASXPROPIETARIO                                   */
/*==============================================================*/
create table CANCHASXPROPIETARIO 
(
   IDCANCHA             INTEGER              not null,
   CEDULAPROPIETARIOS   VARCHAR2(13)         not null,
   constraint PK_CANCHASXPROPIETARIO primary key (IDCANCHA, CEDULAPROPIETARIOS)
);

/*==============================================================*/
/* Index: CANCHASXPROPIETARIO2_FK                               */
/*==============================================================*/
create index CANCHASXPROPIETARIO2_FK on CANCHASXPROPIETARIO (
   IDCANCHA ASC
);

/*==============================================================*/
/* Index: CANCHASXPROPIETARIO_FK                                */
/*==============================================================*/
create index CANCHASXPROPIETARIO_FK on CANCHASXPROPIETARIO (
   CEDULAPROPIETARIOS ASC
);

/*==============================================================*/
/* Table: EQUIPOS                                               */
/*==============================================================*/
create table EQUIPOS 
(
   IDEQUIPO             INTEGER              not null,
   DESCRIPCIONEQUIPO    VARCHAR2(40)         not null,
   constraint PK_EQUIPOS primary key (IDEQUIPO)
);

/*==============================================================*/
/* Table: HORARIOS                                              */
/*==============================================================*/
create table HORARIOS 
(
   IDHORARIO            INTEGER              not null,
   DESCRIPCIONHORARIO   VARCHAR2(50)         not null,
   INICIOHORARIO        DATE                 not null,
   FINHORARIO           DATE                 not null,
   constraint PK_HORARIOS primary key (IDHORARIO)
);

/*==============================================================*/
/* Table: JUGADORES                                             */
/*==============================================================*/
create table JUGADORES 
(
   CEDULAJUGADOR        CHAR(10)             not null,
   IDEQUIPO             INTEGER              not null,
   NOMBREJUGADOR        VARCHAR2(50)         not null,
   CELULARJUGADOR       INTEGER              not null,
   MAILJUGADOR          VARCHAR2(50)         not null,
   constraint PK_JUGADORES primary key (CEDULAJUGADOR)
);

/*==============================================================*/
/* Index: CONFORMADO_FK                                         */
/*==============================================================*/
create index CONFORMADO_FK on JUGADORES (
   IDEQUIPO ASC
);

/*==============================================================*/
/* Table: PARTIDOS                                              */
/*==============================================================*/
create table PARTIDOS 
(
   IDPARTIDO            INTEGER              not null,
   IDEQUIPO             INTEGER              not null,
   IDCANCHA             INTEGER              not null,
   FECHAPARTIDO         DATE                 not null,
   constraint PK_PARTIDOS primary key (IDPARTIDO)
);

/*==============================================================*/
/* Index: JUEGAN_FK                                             */
/*==============================================================*/
create index JUEGAN_FK on PARTIDOS (
   IDEQUIPO ASC
);

/*==============================================================*/
/* Table: PATROCINADORES                                        */
/*==============================================================*/
create table PATROCINADORES 
(
   RUCPATROCINADOR      CHAR(13)             not null,
   DESCRIPCIONPATROCINADOR VARCHAR2(50)         not null,
   constraint PK_PATROCINADORES primary key (RUCPATROCINADOR)
);

/*==============================================================*/
/* Table: PROPIETARIOS                                          */
/*==============================================================*/
create table PROPIETARIOS 
(
   CEDULAPROPIETARIOS   VARCHAR2(13)         not null,
   NOMBREPROPIETARIOS   VARCHAR2(50)         not null,
   CELULARPROPIETARIOS  INTEGER              not null,
   MAILPROPIETARIOS     VARCHAR2(50)         not null,
   constraint PK_PROPIETARIOS primary key (CEDULAPROPIETARIOS)
);

/*==============================================================*/
/* Table: RESERVAS                                              */
/*==============================================================*/
create table RESERVAS 
(
   IDRESERVA            INTEGER              not null,
   IDPARTIDO            INTEGER              not null,
   IDHORARIO            INTEGER              not null,
   IDCAMPEONATO         INTEGER              not null,
   CAPITANRESERVA       VARCHAR2(50)         not null,
   constraint PK_RESERVAS primary key (IDRESERVA)
);

/*==============================================================*/
/* Index: TIENE2_FK                                             */
/*==============================================================*/
create index TIENE2_FK on RESERVAS (
   IDPARTIDO ASC
);

/*==============================================================*/
/* Index: REGISTRAN_FK                                          */
/*==============================================================*/
create index REGISTRAN_FK on RESERVAS (
   IDHORARIO ASC
);

/*==============================================================*/
/* Index: ASOCIA_FK                                             */
/*==============================================================*/
create index ASOCIA_FK on RESERVAS (
   IDCAMPEONATO ASC
);

alter table ARBITROS
   add constraint FK_ARBITROS_CONTROLA_CAMPEONA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO);

alter table CAMPEONATOSXPATROCINADORES
   add constraint FK_CAMPEONA_CAMPEONAT_PATROCIN foreign key (RUCPATROCINADOR)
      references PATROCINADORES (RUCPATROCINADOR);

alter table CAMPEONATOSXPATROCINADORES
   add constraint FK_CAMPEONA_CAMPEONAT_CAMPEONA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO);

alter table CANCHASXPROPIETARIO
   add constraint FK_CANCHASX_CANCHASXP_PROPIETA foreign key (CEDULAPROPIETARIOS)
      references PROPIETARIOS (CEDULAPROPIETARIOS);

alter table CANCHASXPROPIETARIO
   add constraint FK_CANCHASX_CANCHASXP_CANCHAS foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA);

alter table JUGADORES
   add constraint FK_JUGADORE_CONFORMAD_EQUIPOS foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO);

alter table PARTIDOS
   add constraint FK_PARTIDOS_JUEGAN_EQUIPOS foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO);

alter table PARTIDOS
   add constraint FK_PARTIDOS_UTILIZAN_CANCHAS foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA);

alter table RESERVAS
   add constraint FK_RESERVAS_ASOCIA_CAMPEONA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO);

alter table RESERVAS
   add constraint FK_RESERVAS_REGISTRAN_HORARIOS foreign key (IDHORARIO)
      references HORARIOS (IDHORARIO);

alter table RESERVAS
   add constraint FK_RESERVAS_TIENE_PARTIDOS foreign key (IDPARTIDO)
      references PARTIDOS (IDPARTIDO);


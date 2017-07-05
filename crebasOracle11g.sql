/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     5/7/2017 14:03:20                            */
/*==============================================================*/


alter table ARBITROS
   drop constraint FK_ARBITROS_CONTROLA_CAMPEONA;

alter table CAMPEONATOS
   drop constraint FK_CAMPEONA_CAMPEONAT_PATROCIN;

alter table CANCHAS
   drop constraint FK_CANCHAS_UTILIZAN3_PARTIDOS;

alter table CANCHASXPROPIETARIO
   drop constraint FK_CANCHASX_CANCHASXP_CANCHAS;

alter table CANCHASXPROPIETARIO
   drop constraint FK_CANCHASX_CANCHASXP_PROPIETA;

alter table JUGADORXEQUIPO
   drop constraint FK_JUGADORX_JUGADORXE_JUGADORE;

alter table JUGADORXEQUIPO
   drop constraint FK_JUGADORX_JUGADORXE_EQUIPOS;

alter table PARTIDOS
   drop constraint FK_PARTIDOS_JUEGAN_EQUIPOS;

alter table PARTIDOS
   drop constraint FK_PARTIDOS_TIENE2_RESERVAS;

alter table PARTIDOS
   drop constraint FK_PARTIDOS_UTILIZAN2_CANCHAS;

alter table RESERVAS
   drop constraint FK_RESERVAS_ASOCIA_CAMPEONA;

alter table RESERVAS
   drop constraint FK_RESERVAS_REGISTRAN_HORARIOS;

alter table RESERVAS
   drop constraint FK_RESERVAS_TIENE3_PARTIDOS;

drop index CONTROLA_FK;

drop table ARBITROS cascade constraints;

drop index CAMPEONATOSXPATROCINADORES_FK;

drop table CAMPEONATOS cascade constraints;

drop index UTILIZAN3_FK;

drop table CANCHAS cascade constraints;

drop index CANCHASXPROPIETARIO2_FK;

drop index CANCHASXPROPIETARIO_FK;

drop table CANCHASXPROPIETARIO cascade constraints;

drop table EQUIPOS cascade constraints;

drop table HORARIOS cascade constraints;

drop table JUGADORES cascade constraints;

drop index JUGADORXEQUIPO2_FK;

drop index JUGADORXEQUIPO_FK;

drop table JUGADORXEQUIPO cascade constraints;

drop index TIENE2_FK;

drop index UTILIZAN2_FK;

drop index JUEGAN_FK;

drop table PARTIDOS cascade constraints;

drop table PATROCINADORES cascade constraints;

drop table PROPIETARIOS cascade constraints;

drop index ASOCIA_FK;

drop index REGISTRAN_FK;

drop index TIENE3_FK;

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
   RUCPATROCINADOR      CHAR(13)             not null,
   DESCRIPCIONCAMPEONATO VARCHAR2(30)         not null,
   MAILCAMPEONATO       VARCHAR2(50)         not null,
   CELULARCAMPEONATO    INTEGER              not null,
   PREMIOCAMPEONATO     NUMBER(8,2)          not null,
   constraint PK_CAMPEONATOS primary key (IDCAMPEONATO)
);

/*==============================================================*/
/* Index: CAMPEONATOSXPATROCINADORES_FK                         */
/*==============================================================*/
create index CAMPEONATOSXPATROCINADORES_FK on CAMPEONATOS (
   RUCPATROCINADOR ASC
);

/*==============================================================*/
/* Table: CANCHAS                                               */
/*==============================================================*/
create table CANCHAS 
(
   IDCANCHA             INTEGER              not null,
   IDPARTIDO            INTEGER,
   DESCRIPCIONCANCHA    VARCHAR2(50)         not null,
   SECTORCANCHA         VARCHAR2(30)         not null,
   constraint PK_CANCHAS primary key (IDCANCHA)
);

/*==============================================================*/
/* Index: UTILIZAN3_FK                                          */
/*==============================================================*/
create index UTILIZAN3_FK on CANCHAS (
   IDPARTIDO ASC
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
/* Index: CANCHASXPROPIETARIO_FK                                */
/*==============================================================*/
create index CANCHASXPROPIETARIO_FK on CANCHASXPROPIETARIO (
   IDCANCHA ASC
);

/*==============================================================*/
/* Index: CANCHASXPROPIETARIO2_FK                               */
/*==============================================================*/
create index CANCHASXPROPIETARIO2_FK on CANCHASXPROPIETARIO (
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
   NOMBREJUGADOR        VARCHAR2(50)         not null,
   CELULARJUGADOR       INTEGER              not null,
   MAILJUGADOR          VARCHAR2(50)         not null,
   constraint PK_JUGADORES primary key (CEDULAJUGADOR)
);

/*==============================================================*/
/* Table: JUGADORXEQUIPO                                        */
/*==============================================================*/
create table JUGADORXEQUIPO 
(
   CEDULAJUGADOR        CHAR(10)             not null,
   IDEQUIPO             INTEGER              not null,
   constraint PK_JUGADORXEQUIPO primary key (CEDULAJUGADOR, IDEQUIPO)
);

/*==============================================================*/
/* Index: JUGADORXEQUIPO_FK                                     */
/*==============================================================*/
create index JUGADORXEQUIPO_FK on JUGADORXEQUIPO (
   CEDULAJUGADOR ASC
);

/*==============================================================*/
/* Index: JUGADORXEQUIPO2_FK                                    */
/*==============================================================*/
create index JUGADORXEQUIPO2_FK on JUGADORXEQUIPO (
   IDEQUIPO ASC
);

/*==============================================================*/
/* Table: PARTIDOS                                              */
/*==============================================================*/
create table PARTIDOS 
(
   IDPARTIDO            INTEGER              not null,
   IDRESERVA            INTEGER,
   IDEQUIPO             INTEGER              not null,
   IDCANCHA             INTEGER,
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
/* Index: UTILIZAN2_FK                                          */
/*==============================================================*/
create index UTILIZAN2_FK on PARTIDOS (
   IDCANCHA ASC
);

/*==============================================================*/
/* Index: TIENE2_FK                                             */
/*==============================================================*/
create index TIENE2_FK on PARTIDOS (
   IDRESERVA ASC
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
   IDHORARIO            INTEGER              not null,
   IDCAMPEONATO         INTEGER              not null,
   IDPARTIDO            INTEGER              not null,
   CAPITANRESERVA       VARCHAR2(50)         not null,
   constraint PK_RESERVAS primary key (IDRESERVA)
);

/*==============================================================*/
/* Index: TIENE3_FK                                             */
/*==============================================================*/
create index TIENE3_FK on RESERVAS (
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

alter table CAMPEONATOS
   add constraint FK_CAMPEONA_CAMPEONAT_PATROCIN foreign key (RUCPATROCINADOR)
      references PATROCINADORES (RUCPATROCINADOR);

alter table CANCHAS
   add constraint FK_CANCHAS_UTILIZAN3_PARTIDOS foreign key (IDPARTIDO)
      references PARTIDOS (IDPARTIDO);

alter table CANCHASXPROPIETARIO
   add constraint FK_CANCHASX_CANCHASXP_CANCHAS foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA);

alter table CANCHASXPROPIETARIO
   add constraint FK_CANCHASX_CANCHASXP_PROPIETA foreign key (CEDULAPROPIETARIOS)
      references PROPIETARIOS (CEDULAPROPIETARIOS);

alter table JUGADORXEQUIPO
   add constraint FK_JUGADORX_JUGADORXE_JUGADORE foreign key (CEDULAJUGADOR)
      references JUGADORES (CEDULAJUGADOR);

alter table JUGADORXEQUIPO
   add constraint FK_JUGADORX_JUGADORXE_EQUIPOS foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO);

alter table PARTIDOS
   add constraint FK_PARTIDOS_JUEGAN_EQUIPOS foreign key (IDEQUIPO)
      references EQUIPOS (IDEQUIPO);

alter table PARTIDOS
   add constraint FK_PARTIDOS_TIENE2_RESERVAS foreign key (IDRESERVA)
      references RESERVAS (IDRESERVA);

alter table PARTIDOS
   add constraint FK_PARTIDOS_UTILIZAN2_CANCHAS foreign key (IDCANCHA)
      references CANCHAS (IDCANCHA);

alter table RESERVAS
   add constraint FK_RESERVAS_ASOCIA_CAMPEONA foreign key (IDCAMPEONATO)
      references CAMPEONATOS (IDCAMPEONATO);

alter table RESERVAS
   add constraint FK_RESERVAS_REGISTRAN_HORARIOS foreign key (IDHORARIO)
      references HORARIOS (IDHORARIO);

alter table RESERVAS
   add constraint FK_RESERVAS_TIENE3_PARTIDOS foreign key (IDPARTIDO)
      references PARTIDOS (IDPARTIDO);


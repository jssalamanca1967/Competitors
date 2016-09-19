# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table competencia (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  created                       datetime(6),
  updated                       datetime(6),
  constraint uq_competencia_nombre unique (nombre),
  constraint pk_competencia primary key (id)
);

create table competidor (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  email                         varchar(255),
  created                       datetime(6),
  updated                       datetime(6),
  constraint uq_competidor_nombre unique (nombre),
  constraint pk_competidor primary key (id)
);

create table inscripcion (
  id                            bigint auto_increment not null,
  nombre                        varchar(255),
  descripcion                   varchar(255),
  ruta_imagen                   varchar(255),
  competencia_id                bigint,
  competidor_id                 bigint,
  created                       datetime(6),
  updated                       datetime(6),
  constraint pk_inscripcion primary key (id)
);

alter table inscripcion add constraint fk_inscripcion_competencia_id foreign key (competencia_id) references competencia (id) on delete restrict on update restrict;
create index ix_inscripcion_competencia_id on inscripcion (competencia_id);

alter table inscripcion add constraint fk_inscripcion_competidor_id foreign key (competidor_id) references competidor (id) on delete restrict on update restrict;
create index ix_inscripcion_competidor_id on inscripcion (competidor_id);


# --- !Downs

alter table inscripcion drop foreign key fk_inscripcion_competencia_id;
drop index ix_inscripcion_competencia_id on inscripcion;

alter table inscripcion drop foreign key fk_inscripcion_competidor_id;
drop index ix_inscripcion_competidor_id on inscripcion;

drop table if exists competencia;

drop table if exists competidor;

drop table if exists inscripcion;


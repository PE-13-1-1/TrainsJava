/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     20.10.2014 22:37:35                          */
/*==============================================================*/

use KharkovTrain
go

if exists (select 1
          from sysobjects
          where id = object_id('"CLR Trigger_train"')
          and type = 'TR')
   drop trigger "CLR Trigger_train"
go

if exists (select 1
          from sysobjects
          where id = object_id('ti_train')
          and type = 'TR')
   drop trigger ti_train
go

if exists (select 1
          from sysobjects
          where id = object_id('tu_train')
          and type = 'TR')
   drop trigger tu_train
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Stop') and o.name = 'FK_STOP_REFERENCE_STATIONL')
alter table Stop
   drop constraint FK_STOP_REFERENCE_STATIONL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Stop') and o.name = 'FK_STOP_STATIONTO_STATION')
alter table Stop
   drop constraint FK_STOP_STATIONTO_STATION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('Train') and o.name = 'FK_TRAIN_REFERENCE_TRAINSCH')
alter table Train
   drop constraint FK_TRAIN_REFERENCE_TRAINSCH
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TrainSchedule') and o.name = 'FK_TRAINSCH_REFERENCE_STATIONL')
alter table TrainSchedule
   drop constraint FK_TRAINSCH_REFERENCE_STATIONL
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Station')
            and   type = 'U')
   drop table Station
go

if exists (select 1
            from  sysobjects
           where  id = object_id('StationList')
            and   type = 'U')
   drop table StationList
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Stop')
            and   type = 'U')
   drop table Stop
go

if exists (select 1
            from  sysobjects
           where  id = object_id('Train')
            and   type = 'U')
   drop table Train
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TrainSchedule')
            and   type = 'U')
   drop table TrainSchedule
go

/*==============================================================*/
/* Table: Station                                               */
/*==============================================================*/
create table Station (
   stationId            int                  identity not null,
   stationName          nvarchar(Max)        not null,
   stationURL           nvarchar(Max)        not null,
   constraint PK_STATION primary key (stationId)
)
go

/*==============================================================*/
/* Table: Train                                                 */
/*==============================================================*/
create table Train (
   trainId              int                  identity not null,
   startPoint           nvarchar(Max)        not null,
   finalPoint           nvarchar(Max)        not null,
   status               nvarchar(Max)		 not null,
   trainNumber          int                  not null,
   trainURL             nvarchar(Max)        not null,
   constraint PK_TRAIN primary key (trainId),
)
go

/*==============================================================*/
/* Table: Stop                                                  */
/*==============================================================*/
create table Stop (
   stationId            int                  not null,
   timeArrival          datetime                 null,
   timeDeparture        datetime                 null,
   staying              int                  null,
   stopId               int                  identity  not null,
   trainId              int                  not null,
   constraint PK_STOP primary key (stopId),
   FOREIGN KEY (stationId)
   REFERENCES "Station" (stationId),
    FOREIGN KEY (trainId)
   REFERENCES "Train" (trainId),
)
go


/*
alter table Stop
   add constraint FK_STOP_REFERENCE_STATIONL foreign key (listId)
      references StationList (listId)
go

alter table Stop
   add constraint FK_STOP_STATIONTO_STATION foreign key (stationId)
      references Station (stationId)
go

alter table Train
   add constraint FK_TRAIN_REFERENCE_TRAINSCH foreign key (scheduleId)
      references TrainSchedule (scheduleId)
go

alter table TrainSchedule
   add constraint FK_TRAINSCH_REFERENCE_STATIONL foreign key (listId)
      references StationList (listId)
go
*/

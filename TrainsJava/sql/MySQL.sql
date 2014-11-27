drop table if exists Station;

drop table if exists Stop;

drop table if exists Train;

/*==============================================================*/
/* Table: Station                                               */
/*==============================================================*/
create table Station
(
   stationId            int not null AUTO_INCREMENT,
   stationName          varchar(256),
   stationURL           varchar(256),
   primary key (stationId)
);

/*==============================================================*/
/* Table: Stop                                                  */
/*==============================================================*/
create table Stop
(
   stopId               int not null AUTO_INCREMENT,
   timeArrival          time,
   timeDeparture        time,
   staying              int,
   stationId            int not null,
   trainId              int not null,
   primary key (stopId)
);

/*==============================================================*/
/* Table: Train                                                 */
/*==============================================================*/
create table Train
(
   trainId              int not null AUTO_INCREMENT,
   startPoint           varchar(256) not null,
   finalPoint           varchar(256) not null,
   status               varchar(256) not null,
   trainNumber          int not null,
   trainURL             varchar(256) not null,
   primary key (trainId)
);

alter table Stop add constraint FK_Reference_1 foreign key (trainId)
      references Train (trainId) on delete restrict on update restrict;

alter table Stop add constraint FK_Reference_2 foreign key (stationId)
      references Station (stationId) on delete restrict on update restrict;

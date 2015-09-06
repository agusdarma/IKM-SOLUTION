/*
Created: 17-Aug-15
Modified: 06-Sep-15
Model: IKM
Database: MySQL 5.1
*/



-- Create tables section -------------------------------------------------

-- Table User

CREATE TABLE User
(
  id Int NOT NULL AUTO_INCREMENT,
  kode_sekolah Varchar(64),
  nomor_induk Varchar(64),
  name Varchar(50) NOT NULL,
  password Varchar(80),
  user_type Varchar(32),
  status_user Int NOT NULL,
  created_on Datetime NOT NULL,
  updated_on Timestamp NOT NULL,
 PRIMARY KEY (id)
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table Sekolah

CREATE TABLE Sekolah
(
  id Int NOT NULL AUTO_INCREMENT,
  kode_sekolah Varchar(64),
  nama_sekolah Varchar(64),
  created_on Datetime NOT NULL,
  updated_on Timestamp NOT NULL,
 PRIMARY KEY (id)
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table Kelas

CREATE TABLE Kelas
(
  id Int NOT NULL AUTO_INCREMENT,
  kode_kelas Varchar(64),
  nama_kelas Varchar(64),
  created_on Datetime NOT NULL,
  updated_on Timestamp NOT NULL,
 PRIMARY KEY (id)
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table user_sekolah_kelas

CREATE TABLE user_sekolah_kelas
(
  user_id Int NOT NULL,
  sekolah_id Int NOT NULL,
  kelas_id Int NOT NULL,
  subject_id Int
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table agenda

CREATE TABLE agenda
(
  kode_kelas Varchar(64),
  nama_kelas Varchar(64),
  kode_sekolah Varchar(64),
  nama_sekolah Varchar(64),
  tanggal_agenda Datetime,
  isi_agenda Varchar(512),
  agenda_type Int,
  created_on Datetime NOT NULL,
  created_by Varchar(50) NOT NULL,
  updated_on Timestamp NOT NULL,
  updated_by Varchar(50) NOT NULL
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table kalender_akademis

CREATE TABLE kalender_akademis
(
  kode_sekolah Varchar(64),
  tanggal_agenda Datetime,
  isi_agenda Varchar(512)
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table message

CREATE TABLE message
(
  from_user_id Int,
  to_user_id Int,
  isi_message Varchar(512),
  is_read Int,
  created_on Datetime,
  updated_on Timestamp
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table nilai

CREATE TABLE nilai
(
  id Int NOT NULL AUTO_INCREMENT,
  user_id Int,
  nama_test Varchar(512),
  nilai Bigint,
  created_on Datetime,
  updated_on Timestamp,
 PRIMARY KEY (id)
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;

-- Table subject

CREATE TABLE subject
(
  id Int NOT NULL AUTO_INCREMENT,
  subject_name Varchar(64),
  created_on Datetime NOT NULL,
  updated_on Timestamp NOT NULL,
 PRIMARY KEY (id)
)
  COMMENT = 'status: 
0 pending
1 active
2 blocked

activate key : digunakan pada saat registration user, key activate di cocokan dengan yg di kirim dari email

session key: digunakan pada saat device req ke server '
;



ALTER TABLE parkingonline.mall
 ADD mall_image VARCHAR(64) AFTER mall_phone;


INSERT INTO mall(
   mall_code
  ,mall_name
  ,mall_address
  ,mall_phone
  ,mall_image
  ,created_on
  ,created_by
  ,updated_on
  ,updated_by
) VALUES (
  'KOKAS-001'
  ,'Kota Casablanka'  -- mall_name - IN varchar(128)
  ,'Jalan Raya Tebet No 100'  -- mall_address - IN varchar(128)
  ,'021-55934854'  -- mall_phone - IN varchar(64)
  ,'kota_kasablanka'  -- mall_phone - IN varchar(64)
  ,CURRENT_TIMESTAMP -- created_on - IN timestamp
  ,'SYS'  -- created_by - IN varchar(128)
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
  ,'SYS'  -- updated_by - IN varchar(128)
);

INSERT INTO mall_slots(
   mall_id
  ,slots_name
  ,slots_price_idr
  ,slots_status
  ,created_on
  ,created_by
  ,updated_on
  ,updated_by
) VALUES (
   1,'B1-A27'  -- slots_name - IN varchar(128)
  ,10000   -- slots_price_idr - IN bigint(20)
  ,0   -- slots_status - IN int(11)
  ,CURRENT_TIMESTAMP -- created_on - IN timestamp
  ,'SYS'  -- created_by - IN varchar(128)
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
  ,'SYS'  -- updated_by - IN varchar(128)
);

INSERT INTO mall_slots(
   mall_id
  ,slots_name
  ,slots_price_idr
  ,slots_status
  ,created_on
  ,created_by
  ,updated_on
  ,updated_by
) VALUES (
   1,'B1-A28'  -- slots_name - IN varchar(128)
  ,10000   -- slots_price_idr - IN bigint(20)
  ,0   -- slots_status - IN int(11)
  ,CURRENT_TIMESTAMP -- created_on - IN timestamp
  ,'SYS'  -- created_by - IN varchar(128)
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
  ,'SYS'  -- updated_by - IN varchar(128)
);

INSERT INTO mall_slots(
   mall_id
  ,slots_name
  ,slots_price_idr
  ,slots_status
  ,created_on
  ,created_by
  ,updated_on
  ,updated_by
) VALUES (
   1,'B1-A29'  -- slots_name - IN varchar(128)
  ,10000   -- slots_price_idr - IN bigint(20)
  ,0   -- slots_status - IN int(11)
  ,CURRENT_TIMESTAMP -- created_on - IN timestamp
  ,'SYS'  -- created_by - IN varchar(128)
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
  ,'SYS'  -- updated_by - IN varchar(128)
);

INSERT INTO mall_slots(
   mall_id
  ,slots_name
  ,slots_price_idr
  ,slots_status
  ,created_on
  ,created_by
  ,updated_on
  ,updated_by
) VALUES (
   1,'B1-A30'  -- slots_name - IN varchar(128)
  ,10000   -- slots_price_idr - IN bigint(20)
  ,0   -- slots_status - IN int(11)
  ,CURRENT_TIMESTAMP -- created_on - IN timestamp
  ,'SYS'  -- created_by - IN varchar(128)
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
  ,'SYS'  -- updated_by - IN varchar(128)
);

/*
Created: 17-Aug-15
Modified: 08-Sep-15
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
;

-- Table Kelas

CREATE TABLE Kelas
(
  id Int NOT NULL AUTO_INCREMENT,
  kode_kelas Varchar(64),
  nama_kelas Varchar(64),
  wali_kelas_id Int,
  created_on Datetime NOT NULL,
  updated_on Timestamp NOT NULL,
 PRIMARY KEY (id)
)
;

-- Table user_sekolah_kelas

CREATE TABLE user_sekolah_kelas
(
  user_id Int NOT NULL,
  sekolah_id Int NOT NULL,
  kelas_id Int NOT NULL,
  subject_id Int
)
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
;

-- Table kalender_akademis

CREATE TABLE kalender_akademis
(
  kode_sekolah Varchar(64),
  tanggal_agenda Datetime,
  isi_agenda Varchar(512)
)
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
;



INSERT INTO sekolah(
  kode_sekolah
  ,nama_sekolah
  ,created_on
  ,updated_on
) VALUES (
   'DIAN-001'  -- kode_sekolah - IN varchar(64)
  ,'Dian Harapan'  -- nama_sekolah - IN varchar(64)
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);

INSERT INTO sekolah(
  kode_sekolah
  ,nama_sekolah
  ,created_on
  ,updated_on
) VALUES (
   'CITRA-001'  -- kode_sekolah - IN varchar(64)
  ,'Citra Kasih'  -- nama_sekolah - IN varchar(64)
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);


INSERT INTO kelas(
  kode_kelas
  ,nama_kelas
  ,wali_kelas_id
  ,created_on
  ,updated_on
) VALUES (
  '6A'  -- kode_kelas - IN varchar(64)
  ,'Kelas 6A'  -- nama_kelas - IN varchar(64)  
  ,1
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);

INSERT INTO kelas(
  kode_kelas
  ,nama_kelas 
  ,wali_kelas_id
  ,created_on
  ,updated_on
) VALUES (
  '6B'  -- kode_kelas - IN varchar(64)
  ,'Kelas 6B'  -- nama_kelas - IN varchar(64)   
  ,1
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);

INSERT INTO subject(
   subject_name
  ,created_on
  ,updated_on
) VALUES (
   'SCIENCE'  -- subject_name - IN varchar(64)
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);

INSERT INTO subject(
   subject_name
  ,created_on
  ,updated_on
) VALUES (
   'SOCIAL'  -- subject_name - IN varchar(64)
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);

INSERT INTO user(
   kode_sekolah
  ,nomor_induk
  ,name
  ,password
  ,user_type
  ,status_user
  ,created_on
  ,updated_on
) VALUES (
   'DIAN-001'  -- kode_sekolah - IN varchar(64)
  ,'1'  -- nomor_induk - IN varchar(64)
  ,'Sutejo' -- name - IN varchar(50)
  ,'admin'  -- password - IN varchar(80)
  ,'1'  -- user_type - IN varchar(32)
  ,1 -- status_user - IN int(11)
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);

INSERT INTO user(
   kode_sekolah
  ,nomor_induk
  ,name
  ,password
  ,user_type
  ,status_user
  ,created_on
  ,updated_on
) VALUES (
   'DIAN-001'  -- kode_sekolah - IN varchar(64)
  ,'1'  -- nomor_induk - IN varchar(64)
  ,'parent' -- name - IN varchar(50)
  ,''  -- password - IN varchar(80)
  ,'2'  -- user_type - IN varchar(32)
  ,1 -- status_user - IN int(11)
  ,CURRENT_TIMESTAMP -- created_on - IN datetime
  ,CURRENT_TIMESTAMP -- updated_on - IN timestamp
);


INSERT INTO user_sekolah_kelas(
   user_id
  ,sekolah_id
  ,kelas_id
  ,subject_id
) VALUES (
   1 -- user_id - IN int(11)
  ,1 -- sekolah_id - IN int(11)
  ,1 -- kelas_id - IN int(11)
  ,1   -- subject_id - IN int(11)
);

INSERT INTO user_sekolah_kelas(
   user_id
  ,sekolah_id
  ,kelas_id
  ,subject_id
) VALUES (
   1 -- user_id - IN int(11)
  ,1 -- sekolah_id - IN int(11)
  ,2 -- kelas_id - IN int(11)
  ,1   -- subject_id - IN int(11)
);

INSERT INTO user_sekolah_kelas(
   user_id
  ,sekolah_id
  ,kelas_id
  ,subject_id
) VALUES (
   2 -- user_id - IN int(11)
  ,1 -- sekolah_id - IN int(11)
  ,1 -- kelas_id - IN int(11)
  ,1   -- subject_id - IN int(11)
);



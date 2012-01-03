/*
Script generated by Aqua Data Studio 10.0.4 on Jan-03-2012 03:11:07 PM
Database: null
Schema: <All Schemas>
*/

ALTER TABLE "collect"."data"
	DROP CONSTRAINT "FK_data_parent" CASCADE 
GO
ALTER TABLE "collect"."data"
	DROP CONSTRAINT "FK_data_record" CASCADE 
GO
ALTER TABLE "collect"."data"
	DROP CONSTRAINT "FK_data_schema_definition" CASCADE 
GO
ALTER TABLE "collect"."record"
	DROP CONSTRAINT "FK_record_root_entity" CASCADE 
GO
ALTER TABLE "collect"."schema_definition"
	DROP CONSTRAINT "FK_schema_definition_survey" CASCADE 
GO
ALTER TABLE "collect"."survey"
	DROP CONSTRAINT "UK_survey_name" CASCADE 
GO
ALTER TABLE "collect"."survey"
	DROP CONSTRAINT "UK_survey_uri" CASCADE 
GO
DROP TABLE IF EXISTS "collect"."data"
GO
DROP TABLE IF EXISTS "collect"."record"
GO
DROP TABLE IF EXISTS "collect"."schema_definition"
GO
DROP TABLE IF EXISTS "collect"."survey"
GO
CREATE TABLE "collect"."data"  ( 
	"id"           	integer NOT NULL,
	"record_id"    	integer NOT NULL,
	"definition_id"	integer NOT NULL,
	"number1"      	float NULL,
	"number2"      	float NULL,
	"number3"      	float NULL,
	"text1"        	varchar(2048) NULL,
	"text2"        	varchar(2048) NULL,
	"text3"        	varchar(2048) NULL,
	"remarks"      	varchar(2048) NULL,
	"symbol"       	char(1) NULL,
	"state"        	char(1) NULL,
	"parent_id"    	integer NULL,
	"idx"          	integer NOT NULL,
	PRIMARY KEY("id")
)
GO
CREATE TABLE "collect"."record"  ( 
	"id"            	integer NOT NULL,
	"root_entity_id"	integer NOT NULL,
	"date_created"  	timestamp NULL,
	"created_by"    	varchar(255) NULL,
	"date_modified" 	timestamp NULL,
	"modified_by"   	varchar(255) NULL,
	"model_version" 	varchar(255) NOT NULL,
	"step"          	integer NULL,
	"state"         	char(1) NULL,
	PRIMARY KEY("id")
)
GO
CREATE TABLE "collect"."schema_definition"  ( 
	"id"       	integer NOT NULL,
	"survey_id"	integer NOT NULL,
	"path"     	varchar(255) NOT NULL,
	PRIMARY KEY("id")
)
GO
CREATE TABLE "collect"."survey"  ( 
	"id"  	integer NOT NULL,
	"name"	varchar(255) NOT NULL,
	"uri" 	varchar(255) NULL,
	"idml"	text NOT NULL,
	PRIMARY KEY("id")
)
GO
ALTER TABLE "collect"."survey"
	ADD CONSTRAINT "UK_survey_name"
	UNIQUE ("name")
GO
ALTER TABLE "collect"."survey"
	ADD CONSTRAINT "UK_survey_uri"
	UNIQUE ("uri")
GO
ALTER TABLE "collect"."data"
	ADD CONSTRAINT "FK_data_parent"
	FOREIGN KEY("parent_id")
	REFERENCES "collect"."data"("id")
GO
ALTER TABLE "collect"."data"
	ADD CONSTRAINT "FK_data_record"
	FOREIGN KEY("record_id")
	REFERENCES "collect"."record"("id")
GO
ALTER TABLE "collect"."data"
	ADD CONSTRAINT "FK_data_schema_definition"
	FOREIGN KEY("definition_id")
	REFERENCES "collect"."schema_definition"("id")
GO
ALTER TABLE "collect"."record"
	ADD CONSTRAINT "FK_record_root_entity"
	FOREIGN KEY("root_entity_id")
	REFERENCES "collect"."schema_definition"("id")
GO
ALTER TABLE "collect"."schema_definition"
	ADD CONSTRAINT "FK_schema_definition_survey"
	FOREIGN KEY("survey_id")
	REFERENCES "collect"."survey"("id")
GO

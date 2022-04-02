--------------------------------------------------------
--  DDL for Table EMAIL_TB
-- Stores all emails managed by Java Microservice
--------------------------------------------------------

CREATE TABLE EMAIL_TB 
(
  EMAIL_ID RAW(16)  CONSTRAINT EMAIL_TB PRIMARY KEY
, EMAIL_TO VARCHAR2(50 CHAR) 
, EMAIL_FROM VARCHAR2(50 CHAR) 
, SUBJECT VARCHAR2(255 CHAR) 
, SEND_DATE_EMAIL TIMESTAMP(6) 
, STATUS_EMAIL VARCHAR2(50 CHAR) 
, TEXT_EMAIL VARCHAR2(300 CHAR) 

);
/

 --------------------------------------------------------
--  DDL for Package PKG_MS_EMAIL
--  Insert new Email into table and return the new ID
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE PKG_MS_EMAIL AS 

      PROCEDURE P_SAVE_EMAIL(
      P_EMAIL_FROM    IN EMAIL_TB.EMAIL_FROM%TYPE,
      P_EMAIL_TO         IN EMAIL_TB.EMAIL_TO%TYPE,
      P_SUBJECT            IN EMAIL_TB.SUBJECT%TYPE,
      P_STATUS_EMAIL  IN EMAIL_TB.STATUS_EMAIL%TYPE,
      P_DATE_SEND       IN EMAIL_TB.SEND_DATE_EMAIL%TYPE,
      P_TEXT_EMAIL      IN EMAIL_TB.TEXT_EMAIL%TYPE,
      P_RES OUT RAW
    );
    
END PKG_MS_EMAIL;

/

--------------------------------------------------------
--  DDL for Package Body PKG_MS_EMAIL
--  Insert new Email into table and return the new ID
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY PKG_MS_EMAIL AS

    PROCEDURE P_SAVE_EMAIL(
      P_EMAIL_FROM    IN EMAIL_TB.EMAIL_FROM%TYPE,
      P_EMAIL_TO         IN EMAIL_TB.EMAIL_TO%TYPE,
      P_SUBJECT            IN EMAIL_TB.SUBJECT%TYPE,
      P_STATUS_EMAIL  IN EMAIL_TB.STATUS_EMAIL%TYPE,
      P_DATE_SEND      IN EMAIL_TB.SEND_DATE_EMAIL%TYPE,
      P_TEXT_EMAIL      IN EMAIL_TB.TEXT_EMAIL%TYPE,
      P_RES OUT RAW
    )
    AS
       
    BEGIN
    
    
      INSERT INTO EMAIL_TB ( 
           EMAIL_FROM, EMAIL_TO, 
            SUBJECT , STATUS_EMAIL,  
            TEXT_EMAIL,  SEND_DATE_EMAIL ,
            EMAIL_ID
      )
      VALUES(  
            P_EMAIL_FROM, P_EMAIL_TO, 
            P_SUBJECT, P_STATUS_EMAIL,  
            P_TEXT_EMAIL , P_DATE_SEND ,
             sys_guid()
      ) 
      RETURNING email_id  INTO P_RES;
           
    EXCEPTION
    WHEN OTHERS THEN
        RAISE;
     END P_SAVE_EMAIL;
 
END PKG_MS_EMAIL;
/

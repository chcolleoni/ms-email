--------------------------------------------------------
--  DDL for Table USER_TB
--------------------------------------------------------

  CREATE TABLE USER_TB 
   (	USER_ID NUMBER(19,0) GENERATED ALWAYS AS IDENTITY , 
	    USER_NAME VARCHAR2(50 CHAR) ,
        PRIMARY KEY (USER_ID)
   ) 
 
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
      P_RES OUT PLS_INTEGER
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
      P_RES OUT PLS_INTEGER
    )
    AS
       
    BEGIN
    
    
      INSERT INTO EMAIL_TB ( 
           EMAIL_FROM, EMAIL_TO, 
            SUBJECT , STATUS_EMAIL,  
            TEXT_EMAIL,  SEND_DATE_EMAIL 
      )
      VALUES(  
            P_EMAIL_FROM, P_EMAIL_TO, 
            P_SUBJECT, P_STATUS_EMAIL,  
            P_TEXT_EMAIL , P_DATE_SEND 
      ) 
      RETURNING email_id  INTO P_RES;
           
    EXCEPTION
    WHEN OTHERS THEN
        RAISE;
     END P_SAVE_EMAIL;
 
END PKG_MS_EMAIL;

/

CREATE TABLE BANK_USER_DETAILS(
    ID                      NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    VERSION                 NUMBER,
    USER_NAME               VARCHAR2(30) NOT NULL UNIQUE,
    ENCODED_PASSWORD        VARCHAR2(100) NOT NULL,
    PHONE_NUMBER            VARCHAR2(9) NOT NULL,
    EMAIL                   VARCHAR2(100) NOT NULL UNIQUE,
    NOTIFICATION_CHANNEL    VARCHAR2(10) NOT NULL
);

CREATE TABLE ACCOUNT(
    ID                      NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    VERSION                 NUMBER,
    ACCOUNT_NUMBER          VARCHAR2(20) NOT NULL UNIQUE,
    BALANCE                 NUMBER(14,2) NOT NULL,
    BANK_USER_DETAILS_ID    NUMBER NOT NULL UNIQUE
);

CREATE TABLE TRANSFER(
    ID                              NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    VERSION                         NUMBER,
    TRANSFER_TIMESTAMP              TIMESTAMP NOT NULL,
    AMOUNT                          NUMBER(14,2) NOT NULL,
    FROM_ACCOUNT_NUMBER             VARCHAR2(20) NOT NULL,
    FROM_ACCOUNT_BALANCE_BEFORE     NUMBER(14,2) NOT NULL,
    FROM_ACCOUNT_BALANCE_AFTER      NUMBER(14,2) NOT NULL,
    TO_ACCOUNT_NUMBER               VARCHAR2(20) NOT NULL,
    TO_ACCOUNT_BALANCE_BEFORE       NUMBER(14,2) NOT NULL,
    TO_ACCOUNT_BALANCE_AFTER        NUMBER(14,2) NOT NULL
);
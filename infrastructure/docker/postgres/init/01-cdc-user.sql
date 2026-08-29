CREATE ROLE cdc_user
    WITH LOGIN
    REPLICATION
    PASSWORD 'cdc_user_dev';

GRANT CONNECT ON DATABASE customerdb TO cdc_user;
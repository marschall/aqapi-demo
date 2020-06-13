CREATE TYPE forum_post_type AS OBJECT(
        id NUMBER(18),
        title VARCHAR2(30),
        text VARCHAR2(2000) );

EXEC DBMS_AQADM.CREATE_QUEUE_TABLE(
        queue_table => 'forum_post_table',
        queue_payload_type => 'forum_post_type');

EXEC DBMS_AQADM.CREATE_QUEUE(
    queue_name => 'forum_post_queue',
    queue_table => 'forum_post_table');

EXEC DBMS_AQADM.START_QUEUE(
    queue_name => 'forum_post_queue');
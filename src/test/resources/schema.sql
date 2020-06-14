CREATE TYPE forum_post_type AS OBJECT(
        id NUMBER(18),
        title VARCHAR2(30),
        text VARCHAR2(2000) );

EXEC DBMS_AQADM.CREATE_QUEUE_TABLE(
        queue_table => 'T_FORUM_POST',
        queue_payload_type => 'forum_post_type');

EXEC DBMS_AQADM.CREATE_QUEUE(
    queue_name => 'Q_FORUM_POST',
    queue_table => 'T_FORUM_POST');

EXEC DBMS_AQADM.START_QUEUE(
    queue_name => 'Q_FORUM_POST');
    
    
-- FIXME as SYS
EXEC DBMS_AQADM.CREATE_QUEUE_TABLE(queue_table => 'jdbc.T_FORUM_POST', queue_payload_type => 'jdbc.forum_post_type');

EXEC DBMS_AQADM.CREATE_QUEUE(queue_name => 'jdbc.Q_FORUM_POST', queue_table => 'jdbc.T_FORUM_POST');

EXEC DBMS_AQADM.START_QUEUE(queue_name => 'jdbc.Q_FORUM_POST');


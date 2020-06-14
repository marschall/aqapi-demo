EXEC DBMS_AQADM.GRANT_SYSTEM_PRIVILEGE(
        privilege => 'MANAGE_ANY',
        grantee => 'jdbc',
        admin_option => TRUE);

EXEC DBMS_AQADM.GRANT_QUEUE_PRIVILEGE(
        privilege => 'ALL',
        queue_name => 'Q_FORUM_POST',
        grantee => 'jdbc');
        
        

-- ???
GRANT EXECUTE ON DBMS_AQIN to jdbc;
AQ_USER_ROLE


-- only thing that should be needed

GRANT EXECUTE ON DBMS_AQADM TO jdbc;
GRANT EXECUTE ON DBMS_AQ TO jdbc;


-- should not be needed
GRANT aq_user_role TO jdbc;
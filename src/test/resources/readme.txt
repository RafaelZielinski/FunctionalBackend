--  Hi, if you want to create test database
    use this commands in docker container the
    same where is main database.

    docker ps
    docker exec -it <name_of_docker_postgres> bash
    psql -d <main_database> -U <username> -W
    <password>

--  then create database

    create database <name_of_new_test_database>;
    create user <name_of_test_user> WITH PASSWORD '<password>';
    alter user rafaeltest with superuser;

-- logout and log in as a <test_user>









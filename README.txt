- run kafka, akhq, mariadb
    $ docker-compose -f demo-env.yaml up -d

- initialize database
    $ docker exec -i mariadb sh -c 'exec mysql -uroot -p"root"' < demo-database.sql

- for shutdown,
    $ docker-compose -f demo-env.yaml down
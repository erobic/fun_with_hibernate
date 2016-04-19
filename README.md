## The project demonstrates basic/advanced concepts of Hibernate ORM. ##

### Concepts covered: ###
1. CRUD against Relational database
2. CRUD against NoSQL
3. Bulk loads
4. Transaction management
5. Entity Locking
6. Caching strategies
7. Performance tests

### Instructions ###

#### Environment variable ####
1. Define an environment variable: EROBIC_HOME and set its value to a directory where you want to store the project files
eg: EROBIC_HOME=/home/erobic/my_projects

#### log.properties ####
This project uses logback and it depends on an external log.properties file.
Copy the log.properties file from resources folder of this project to ${EROBIC_HOME}

#### db.properties ####
1. Fill parameters in: db.properties.template 
2. Rename the file to db.properties

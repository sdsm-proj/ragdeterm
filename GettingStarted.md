# Getting Started

## RAGdata

The auxiliary RAGdata project should be built using the command:

```bash
mvn clean package
```

This operation should result in a JAR file:
```text
ragdeterm\RAGdata\target\RAGdata-1.0.0.jar 
```

## RAGdeterm

### Initial configuration

The configuration of the RAGdeterm application should begin with the preparation of an appropriate PostgreSQL database. 
To do this, use the following script:

```sql
CREATE USER ragdeterm WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION;

ALTER USER ragdeterm WITH ENCRYPTED PASSWORD 'ragdeterm';

CREATE DATABASE ragdeterm
    WITH
    OWNER = ragdeterm
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    LC_COLLATE = 'Polish_Poland.1250'   -- use own language
    LC_CTYPE = 'Polish_Poland.1250'     -- use own language
    CONNECTION LIMIT = -1;

CREATE SCHEMA rag
  AUTHORIZATION ragdeterm;

DROP SCHEMA public;
```

After creating the database, use the liquibase script (`liquibase-changelog.xml` file) to create the tables structure. 
The liquibase plugin configuration is located in the `liquibase.properties` file:

```text
url=jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag
username=ragdeterm
password=ragdeterm
changeLogFile=src/main/resources/sql/liquibase-changelog.xml
```

To create or update the database structure, use the liquibase command: 

```bash
liquibase:update
```

Subsequent operations will require access to the OpenAI API, so you must insert your own key into 
the `spring.ai.openai.api-key` variable in the `application.properties` file.

### Populating the database

The RAGdeterm database is loaded by running the `_InitScenario` test. 
The set of required configuration parameters is defined by the `Consta` class constants. 
These are: database connection configuration, location of the RAGdata project JAR file, and 
location of sources.
The `LOAD_JDK` option is also important, as it allows us to insert all Java JDK types into the database.

### Calling REST services

After launching the appropriate RAGdeterm application, you can call prompts with embedded functions. 
An example of such a call can be found in the `test\http\rag.http` file:

```text
###
http://localhost:8884/rag?query=For the following set of classes in Java
    ```java
    [*RG ClassContext("pl.org.opi.vehicles.land.car.subtypes.Hatchback",
    LONG_NAME, "\n", "\n", "\n") *RG]
    ```java
    create the corresponding structures in GraphQL.
    Use GraphQL structures of type: (interface, type, input).

```


## RAGvector

The RAGvector application operates in two modes. 
When the value of the `create.store` parameter in the `application.properties` file is set to true, 
the contents of the classes defined in the
`source.folder.1` and `source.folder.2` variables will be loaded into the vector database. 
Setting the `create.store` variable to false causes the application to work in the correct mode, 
i.e. it offers a REST service that responds to queries.
Here, too, remember to insert the key into the variable
`spring.ai.openai.api-key`.
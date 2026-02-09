## Library Architecture and Functionality

The project is a library, but it can also operate as a command-line application.
The responsibility of the module is to load information about code contained in a specified JAR file into the RAGdeterm database.

By *code information* we mean data about all types defined in a given JAR library (primarily classes, but also interfaces, enums, etc.), as well as the relationships between these types. These include inheritance relationships, structural relationships, and cooperation relationships resulting from method types and parameters.
There is also a special operating mode in which information retrieved from the JDK—rather than from the specified JAR library—is stored in the database.

The process of loading code information is supervised by the `Loader` class. It can be invoked with multiple parameters. Below is the simplest case, i.e., loading the JDK (classes only, without relationships):

```java
var loader = new Loader();
loader.setConnUrl(CONN_URL);
loader.setConnUser(CONN_USER);
loader.setConnPsw(CONN_PSW);
loader.setConnDriver(CONN_DRIVER);
loader.setMode(Loader.Mode.jdk);
loader.exec();
```

Depending on the mode, the `Loader` class delegates control to either `LoaderJdk` or `LoaderJar`.

```java
switch(mode) {
    case jdk -> new LoaderJdk(deleteBefore, resolveInheritance, resolveStructure, resolveCooperation).exec();
    case jar -> new LoaderJar(jarFullPath, jarClassPath,
            deleteBefore,
            withSrcCode, srcCodeFullPath,
            resolveInheritance, resolveStructure, resolveCooperation).exec();
}
```

Each of these classes further uses `LoadKlazzDepot` and `SaveKlazzDepot`, which are responsible for searching and analyzing code and for persisting the discovered information in the database (via the `db-lib` library).

The actual loading of classes is performed (depending on the mode) by one of the methods of the `LoadKlazzDepot` class.
Saving data to the database and resolving relationships (optionally) is handled by `SaveKlazzDepot` and the `ResolveInheritance`, `ResolveStructure`, and `ResolveCooperation` classes.

The `SaveKlazzDepot` class is also responsible for the optional retrieval and storage of the source code of the discovered type.

## Typical Usage Scenario

Below are example usages of the library. First, however, a description of all parameters.

`connUrl`, `connUser`, `connPsw`, `connDriver`: database access parameters, for example:

```text
connUrl = "jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag";
connUser = "ragdeterm";
connPsw = "ragdeterm";
connDriver = "org.postgresql.ds.PGSimpleDataSource";
```

`mode`:

* `jdk` — loads JDK classes (and other types); the JDK that runs the application is scanned
* `jar` — loads the specified JAR file

`jarFullPath`: full path to the JAR file to be loaded.

`jarClassPath`: a list of full paths (semicolon-separated) to JAR libraries that must be accessible in order to correctly open the file specified by `jarFullPath`. This is important when loading libraries that depend on other modules.

`deleteBefore`: a very important parameter when the RAGdeterm database stores data about multiple interdependent projects.
If set to `true`, loading is more efficient, but rows in the `klazz` table are removed and reinserted. As a result, relationships from other projects that reference these classes are lost.
If set to `false`, data is intelligently updated without changing identifiers, which are the basis of relationships. However, the process takes longer.

The choice of `deleteBefore` depends on the adopted RAGdeterm update strategy.
If many projects are updated each time, `true` should be used.
When updating individual projects that have dependencies on other projects, it is better to use `false`.

`withSrcCode`, `srcCodeFullPath`: if you want to attach source code to classes, set `withSrcCode` to `true` and provide the full path in `srcCodeFullPath`. This does not work for `jdk` mode, where source code is not loaded by design; only relationships between types are of interest.

`resolveInheritance`, `resolveStructure`, `resolveCooperation`: parameters that enable searching for and storing specific types of relationships. Three separate parameters exist because in many cases storing all relationships is unnecessary and increases update time.

Loading JDK type data together with relationships:

```java
var loader = new Loader();
loader.setConnUrl(CONN_URL);
loader.setConnUser(CONN_USER);
loader.setConnPsw(CONN_PSW);
loader.setConnDriver(CONN_DRIVER);
loader.setMode(Loader.Mode.jdk);
loader.setDeleteBefore(false);
loader.setResolveInheritance(true);
loader.setResolveStructure(true);
loader.setResolveCooperation(true);
loader.exec();
```

Loading the `person-lib` library together with source code and relationships:

```java
var loader = new Loader();
loader.setConnUrl(CONN_URL);
loader.setConnUser(CONN_USER);
loader.setConnPsw(CONN_PSW);
loader.setConnDriver(CONN_DRIVER);
loader.setMode(Loader.Mode.jar);
loader.setJarFullPath("c:\\ragdeterm\\person-lib\\target\\person-lib-1.0.0.jar");
loader.setWithSrcCode(true);
loader.setSrcCodeFullPath("c:\\ragdeterm\\person-lib\\src\\main\\java");
loader.setResolveInheritance(true);
loader.setResolveStructure(true);
loader.setResolveCooperation(true);
loader.exec();
```

Loading can be performed programmatically; invoking the application from the command line produces an equivalent effect.
Below is an example of loading interdependent libraries. These dependencies make it necessary to precisely define the value of the `jarClassPath` parameter.

```text
java -jar 
c:\ragdeterm\loader-java\target\loader-java-1.0.0-jar-with-dependencies.jar  
--connUrl="jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag" 
--connUser=ragdeterm  
--connPsw=ragdeterm  
--connDriver=org.postgresql.ds.PGSimpleDataSource  
--mode=jar 
--deleteBefore=false 
--jarFullPath="c:\ragdeterm\depend-a-lib\target\depend-a-lib-1.0.0.jar" 
--withSrcCode=true 
--srcCodeFullPath="c:\ragdeterm\depend-a-lib\src\main\java"
--resolveInheritance=true 
--resolveStructure=true 
--resolveCooperation=true 
```

```text
java -jar 
c:\ragdeterm\loader-java\target\loader-java-1.0.0-jar-with-dependencies.jar
--connUrl="jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag" 
--connUser=ragdeterm  
--connPsw=ragdeterm  
--connDriver=org.postgresql.ds.PGSimpleDataSource  
--mode=jar 
--deleteBefore=false 
--jarFullPath="c:\ragdeterm\depend-b-lib\target\depend-b-lib-1.0.0.jar" 
--withSrcCode=true 
--srcCodeFullPath="c:\ragdeterm\depend-b-lib\src\main\java" 
--jarClassPath="c:\ragdeterm\depend-a-lib\target\depend-a-lib-1.0.0.jar" 
--resolveInheritance=true 
--resolveStructure=true 
--resolveCooperation=true 
```

```text
java -jar 
c:\ragdeterm\loader-java\target\loader-java-1.0.0-jar-with-dependencies.jar  
--connUrl="jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag" 
--connUser=ragdeterm  
--connPsw=ragdeterm  
--connDriver=org.postgresql.ds.PGSimpleDataSource  
--mode=jar 
--deleteBefore=false 
--jarFullPath="c:\ragdeterm\depend-c-lib\target\depend-c-lib-1.0.0.jar" 
--withSrcCode=true 
--srcCodeFullPath="c:\ragdeterm\depend-c-lib\src\main\java" 
--jarClassPath="c:\ragdeterm\depend-a-lib\target\depend-a-lib-1.0.0.jar,c:\ragdeterm\depend-b-lib\target\depend-b-lib-1.0.0.jar" 
--resolveInheritance=true 
--resolveStructure=true 
--resolveCooperation=true 
```

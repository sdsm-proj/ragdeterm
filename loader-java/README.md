The **loader-java** library is one of the components of the **RAGdeterm** solution, 
which are presented below:

![Image description](docs/figure/02-soft-arch.png)

The project **loader-java** functions both as a command-line 
application and a Java library. 
The purpose of **loader-java** is to scan a specified JAR module and store 
information about all found types and the relationships between them in 
the database. Code analysis is currently performed using Java reflection, 
earlier attempts relied on source code parsing. The **loader-java** 
application is also responsible for inserting the original source code 
of each class into the database. A module uses the **db-lib** library.

The library description can be found in this document:

[Library architecture and functionality](docs/library-architecture-and-functionality.md)

[Performance tests](docs/performance-tests.md)



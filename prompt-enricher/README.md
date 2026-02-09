The **prompt-enricher** library is one of the components of the **RAGdeterm** solution, 
which are presented below:

![Image description](docs/figure/02-soft-arch.png)

The **prompt-enricher** is the most critical 
component of the entire solution. 
This project operates both as a command-line application 
and as a Java library. The role of **prompt-enricher** 
is to enrich prompts: the input prompt is analyzed to identify 
invocations of commands expressed in the dedicated **RAGdeterm** 
query language. Each command is then parsed, the corresponding 
service is invoked, and the value returned by that service is 
inserted into the resulting prompt. A module uses the **db-lib** library.

[Library architecture and functionality](docs/library-architecture-and-functionality.md)

[Performance tests](docs/performance-tests.md)

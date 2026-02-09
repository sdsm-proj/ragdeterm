The **ai-client-naive** application is one of the components of the **RAGdeterm** solution, 
which are presented below:

![Image description](docs/figure/02-soft-arch.png)

The **ai-client-naive** application serves as a reference, it loads source code
into a vector database and implements a traditional embedding-based RAG
approach. The project is built using the Spring Boot and Spring AI frameworks, 
relies on the standard prompt enrichment mechanism (class `QuestionAnswerAdvisor`), 
and uses the default vector database implementation
(class `SimpleVectorStore`).

The application description can be found in this document:

[Application architecture and functionality](docs/application-architecture-and-functionality.md)

Description of experiments conducted using **ai-client-naive** are in two subsequent documents:

[Experiment 1](docs/experiment-1.md)

[Experiment 2](docs/experiment-2.md)


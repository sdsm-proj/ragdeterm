## Performance Tests: loader-java

The **RAGdeterm** solution was tested using six class libraries: **hierarchy-lib**, **vehicle-lib**, **person-lib**, **depend-a-lib**, **depend-b-lib**, and **depend-c-lib**.

Experiments were conducted using these libraries to examine various aspects of the solution, primarily the quality of **RAGdeterm** suggestions compared to traditional RAG. The results of the technical experiments are described in the documents
[Experiment 1](../../ai-client-naive/docs/experiment-1.md) and
[Experiment 2](../../ai-client-naive/docs/experiment-2.md).
The results concerning the speed of context construction using the **prompt-enricher** library are presented in the document
[Performance Tests: prompt-enricher](../../prompt-enricher/docs/performance-tests.md).

The size of each library (measured by the number of classes) is presented below.

| Library   | Number of classes |
| --------- | ----------------: |
| hierarchy |               705 |
| vehicle   |                32 |
| person    |                 7 |
| depend-a  |                 3 |
| depend-b  |                 3 |
| depend-c  |                 3 |

Below we present the class loading times into the RAGdeterm database using the **loader-java** library.
In all cases, the parameters `withSrcCode`, `resolveInheritance`, `resolveStructure`, and `resolveCooperation` are set to `true`. This means that classes, source code, and all relationships are loaded. This full configuration provides the greatest capabilities for context construction and, unless there are special reasons otherwise, can be considered the default.

Depending on the database update strategy, an appropriate value of the `deleteBefore` parameter must be selected (this is described in detail in the document
[Library Architecture and Functionality](./library-architecture-and-functionality.md)).
Loading times were measured for both possible values of `deleteBefore`, i.e., `true` and `false`.

The results are as follows:

| Library       | deleteBefore | trial 1, time [ms] | trial 2, time [ms] | trial 3, time [ms] |
| ------------- | :----------: | -----------------: | -----------------: | -----------------: |
| hierarchy-lib |     false    |               1712 |               1782 |               1840 |
| hierarchy-lib |     true     |               1685 |               1650 |               1717 |
| person-lib    |     false    |                629 |                608 |                676 |
| person-lib    |     true     |                611 |                627 |                585 |
| vehicle-lib   |     false    |                775 |                688 |                642 |
| vehicle-lib   |     true     |                690 |                676 |                714 |
| person-lib    |     false    |                600 |                620 |                657 |
| person-lib    |     true     |                573 |                551 |                592 |
| person-lib    |     false    |                643 |                565 |                586 |
| person-lib    |     true     |                575 |                616 |                582 |
| person-lib    |     false    |                583 |                614 |                662 |
| person-lib    |     true     |                642 |                651 |                572 |

The conclusions are that for small projects, `deleteBefore` does not affect loading speed (therefore, it is better to use `false` to avoid losing existing relationships). The loading time itself is short enough that it is worth running the process after every commit.

Performance tests were also conducted for loading the **JDK** (version Adoptium_21.0.4+7-LTS).

| Database state before operation | deleteBefore | resolveInheritance | resolveStructure | resolveCooperation | trial 1, time [s] | trial 2, time [s] | trial 3, time [s] |
| ------------------------------- | ------------ | ------------------ | ---------------- | ------------------ | ----------------: | ----------------: | ----------------: |
| empty                           | false        | false              | false            | false              |                11 |                11 |                10 |
| full                            | false        | false              | false            | false              |                11 |                11 |                12 |
| full                            | true         | false              | false            | false              |                10 |                 9 |                10 |
| empty                           | false        | true               | true             | true               |                52 |                50 |                53 |
| full                            | true         | true               | true             | true               |               256 |               310 |               296 |
| full                            | false        | true               | true             | true               |                52 |                53 |                52 |

It is worth noting that after loading the JDK, the number of records in individual tables is as follows:

| Table       | Number of records |
| ----------- | ----------------: |
| klazz       |            25,578 |
| structure   |            24,351 |
| inheritance |            23,125 |
| cooperation |            58,223 |

In the case of the JDK, loading time is longer, especially when relationship resolution is enabled. The most time-consuming scenario is updating loaded information in the variant with deletion rather than overwriting. The recommended strategy is to load each JDK version once and not update it during the course of the project.

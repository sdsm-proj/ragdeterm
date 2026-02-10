## Performance Tests: prompt-enricher

The **RAGdeterm** solution was tested using six class libraries: **hierarchy-lib**,
**vehicle-lib**, **person-lib**, **depend-a-lib**, **depend-b-lib**, and **depend-c-lib** .

Experiments were conducted with these libraries to evaluate various aspects of the solution,
primarily the quality of **RAGdeterm** hints compared to traditional RAG.
The results of the technical experiments are described in the documents
[Experiment 1](../../ai-client-naive/docs/experiment-1.md) and
[Experiment 2](../../ai-client-naive/docs/experiment-2.md).
The results of class loading performance using the **loader-java** library are presented in
[Performance Tests: loader-java](../../loader-java/docs/performance-tests.md).

The size (measured by the number of classes) of each library is presented below.

| Library   | Number of classes |
| --------- | ----------------: |
| hierarchy |               705 |
| vehicle   |                32 |
| person    |                 7 |
| depend-a  |                 3 |
| depend-b  |                 3 |
| depend-c  |                 3 |

Below are the results of context construction performance using the **prompt-enricher** library.

For this purpose, a test was conducted in which 100 threads were launched, each enriching a given prompt 10 times.
In total, the enrichment operation was executed 1,000 times.

Below are the measurement results for several example queries.

```text
[*RG StruCoopContext("pl.org.opi.vehicle.land.car.subtypes.Hatchback", "", LONG_NAME, "", "", "\n") *RG]
```

|                          | trial 1 | trial 2 | trial 3 |
| ------------------------ | ------: | ------: | ------: |
| time for 1000 calls [ms] |    1508 |    1458 |    1637 |
| time per call [ms]       |    1.51 |    1.46 |    1.64 |

```text
[*RG StruCoopContext("pl.org.opi.vehicle.land.car.subtypes.Hatchback", "", SOURCE_CODE, "", "", "\n") *RG]
```

|                          | trial 1 | trial 2 | trial 3 |
| ------------------------ | ------: | ------: | ------: |
| time for 1000 calls [ms] |    1638 |    1610 |    1598 |
| time per call [ms]       |    1.64 |    1.61 |    1.60 |

```text
[*RG ClassesInheritedAnyLevel("pl.org.opi.vehicle.land.car.Car", "", LONG_NAME, "", "", "\n") *RG]
```

|                          | trial 1 | trial 2 | trial 3 |
| ------------------------ | ------: | ------: | ------: |
| time for 1000 calls [ms] |   11150 |   10773 |   11002 |
| time per call [ms]       |   11.15 |   10.77 |   11.00 |

```text
[*RG ClassesInheritedAnyLevel("pl.org.opi.vehicle.land.car.Car", "", SOURCE_CODE, "", "", "\n") *RG]
```

|                          | trial 1 | trial 2 | trial 3 |
| ------------------------ | ------: | ------: | ------: |
| time for 1000 calls [ms] |   11327 |   11382 |   11412 |
| time per call [ms]       |   11.33 |   11.38 |   11.41 |

```text
[*RG IfaceImplementationsAnyLevel("pl.org.opi.hierarchy.TheSameLetters", "", LONG_NAME, "", "", "\n") *RG]
```

|                          | trial 1 | trial 2 | trial 3 |
| ------------------------ | ------: | ------: | ------: |
| time for 1000 calls [ms] |   19375 |   18727 |   18653 |
| time per call [ms]       |   19.38 |   18.73 |   18.65 |

```text
[*RG IfaceImplementationsAnyLevel("pl.org.opi.hierarchy.TheSameLetters", "", SOURCE_CODE, "", "", "\n") *RG]
```

|                          | trial 1 | trial 2 | trial 3 |
| ------------------------ | ------: | ------: | ------: |
| time for 1000 calls [ms] |   19080 |   18831 |   19070 |
| time per call [ms]       |   19.08 |   18.83 |   19.07 |

In the conducted experiments, the shortest prompt enrichment time was **1.46 ms**, and the longest was **19.38 ms**.
This occurred when the **RAGdeterm** database was populated with data about classes and relationships from all projects
as well as the JDK (nearly **26,000 classes** in total). Even the highest reported values are fully acceptable.
It is also evident that retrieving the full source code does not affect performance.

---

_All tests were performed on a computer with an AMD Ryzen 5, 3.2GHz processor, Samsung 980 SSD, and 32 GB of RAM._
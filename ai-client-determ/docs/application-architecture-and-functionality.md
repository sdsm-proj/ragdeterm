## Application Architecture and Functionality

The **ai-client-determ** application is an example built using the Spring Boot framework and the Spring AI library. 
Its design is very simple. It consists of a single REST controller named `RagDetermController`, 
which is presented below:

```java
@Slf4j
@RestController
public class RagDetermController {

    private final ChatClient chatClient;

    public RagDetermController(ChatClient.Builder builder, Environment env) {
        chatClient = builder
                .build();
        DbConnContainer.addDbConn(new DbConnConfig(env.getProperty("conn.id"),
                env.getProperty("conn.url"), env.getProperty("conn.user"),
                env.getProperty("conn.psw"), env.getProperty("conn.driver"),
                false, 20, 180000
        ));
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/ragdeterm")
    public String chat(@RequestParam(defaultValue = "Hello, how are you?") String query) {
        String preprocessedPrompt = PromptProcessor.process(query);
        log.info("\n\n" + preprocessedPrompt);
        String rslt = chatClient
                .prompt()
                .messages(List.of(new UserMessage(preprocessedPrompt)))
                .call()
                .content();
        log.info("\n\n" + rslt);
        return rslt;
    }

}
```

The `/ragdeterm` service forwards the prompt to the LLM system, but before sending it, 
the prompt is enriched using RAGdeterm. This enrichment is handled by an object of type `PromptProcessor`. 
Proper operation requires a database connection, which is established in the 
constructor of the `RagDetermController` class.

## Configuration and Initial Startup

Before running the application for the first time, the application parameters must be 
verified and adjusted in the `application.properties` file:

```text
spring.application.name=ai-client-determ
server.port=8886
spring.ai.openai.api-key=<KEY>
spring.ai.openai.chat.options.temperature=0.1
spring.ai.openai.chat.options.model=gpt-4o-mini-2024-07-18
spring.ai.openai.embedding.options.model=text-embedding-3-large

conn.id=Default
conn.url=jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag
conn.user=ragdeterm
conn.psw=ragdeterm
conn.driver=org.postgresql.ds.PGSimpleDataSource
```

It is necessary to assign a valid OpenAI API key to the `spring.ai.openai.api-key` parameter.

## Typical Usage Scenario

After starting the application, prompts enriched by RAGdeterm can be sent to the LLM system.

````text
###
http://localhost:8886/ragdeterm?query=
    For the following set of types in Java
    ```java
    [*RG StructureContext("pl.org.opi.vehicle.land.car.subtypes.Hatchback",
    "", SOURCE_CODE, "\n", "\n", "\n") *RG]
    ```
    create their equivalents in TypeScript.
````

package pl.org.opi.ragdeterm.enricher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static pl.org.opi.ragdeterm.enricher.Consta.*;

@Slf4j
public class EnricherTest {

    //@Test
    public void testCar() {
        String prompt = """
                ClassesInheritedAnyLevel
                [*RG ClassesInheritedAnyLevel("pl.org.opi.vehicle.land.car.Car", "", LONG_NAME, "", "", "\n") *RG]
                """;
        var enricher = new Enricher();
        enricher.setConnUrl(CONN_URL);
        enricher.setConnUser(CONN_USER);
        enricher.setConnPsw(CONN_PSW);
        enricher.setConnDriver(CONN_DRIVER);
        String rslt = enricher.exec(prompt);
        log.info(rslt);
    }

    private static final AtomicInteger cnt = new AtomicInteger();

    //@Test
    public void testMultithreaded() throws InterruptedException {
        String prompt = """
                [*RG IfaceImplementationsAnyLevel("pl.org.opi.hierarchy.TheSameLetters", "", SOURCE_CODE, "", "", "\\n") *RG]
                """;

        var enricher = new Enricher();
        enricher.setConnUrl(CONN_URL);
        enricher.setConnUser(CONN_USER);
        enricher.setConnPsw(CONN_PSW);
        enricher.setConnDriver(CONN_DRIVER);
        enricher.exec(prompt);

        int threads = 100;
        int executionsPerThread = 10;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        long time1 = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < executionsPerThread; j++) {
                        String result = enricher.exec(prompt);
                        cnt.incrementAndGet();
                    }
                } catch (Exception e) {
                    log.error("Error in thread {}", threadId, e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        log.info("" + cnt.get());

        long time2 = System.currentTimeMillis();
        long timediff = time2 - time1;
        double timeOfOne = timediff / (double) (threads * executionsPerThread);

        log.info("timediff: " + timediff);
        log.info("1 enrichment time: " + timeOfOne);
    }

}

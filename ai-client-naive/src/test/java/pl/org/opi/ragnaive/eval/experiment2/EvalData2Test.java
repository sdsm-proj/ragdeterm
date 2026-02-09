package pl.org.opi.ragnaive.eval.experiment2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class EvalData2Test {

    //@Test
    public void startEval() throws IOException {
        new EvalData2All(new File("C:\\ragdeterm\\ai-client-naive\\docs\\experiment-2-data\\embedding-3-large\\similarityThreshold-00")).exec();
    }

}

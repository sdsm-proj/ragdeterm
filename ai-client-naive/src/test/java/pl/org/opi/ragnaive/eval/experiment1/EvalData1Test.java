package pl.org.opi.ragnaive.eval.experiment1;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class EvalData1Test {

    //@Test
    public void startEval() throws IOException {
        new EvalData1All(new File("C:\\ragdeterm\\ai-client-naive\\docs\\experiment-1-data\\embedding-3-large\\similarityThreshold-00")).exec();
    }

}

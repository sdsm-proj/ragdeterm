package pl.org.opi.ragnaive.eval.experiment2;

import java.io.File;
import java.io.IOException;

public class EvalData2All {

    private final File folder;

    public EvalData2All(File folder) {
        if (folder == null || !folder.isDirectory()) {
            throw new IllegalArgumentException("File is not directory");
        }
        this.folder = folder;
    }

    public void exec() throws IOException {
        File[] txtFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        for (File file : txtFiles) {
            EvalData2OneFile evaluator = new EvalData2OneFile(file);
            evaluator.exec();
        }
    }

}

package pl.org.opi.ragnaive.eval.experiment1;

import java.io.File;
import java.io.IOException;

public class EvalData1All {

    private final File folder;

    public EvalData1All(File folder) {
        if (folder == null || !folder.isDirectory()) {
            throw new IllegalArgumentException("File is not directory");
        }
        this.folder = folder;
    }

    public void exec() throws IOException {
        File[] txtFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        for (File file : txtFiles) {
            EvalData1OneFile evaluator = new EvalData1OneFile(file);
            evaluator.exec();
        }
    }

}

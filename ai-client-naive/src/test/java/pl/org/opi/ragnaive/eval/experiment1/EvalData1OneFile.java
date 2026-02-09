package pl.org.opi.ragnaive.eval.experiment1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class EvalData1OneFile {

    private static final Pattern CLASS_PATTERN = Pattern.compile(
            "\\b(?:public|protected|private)?\\s*" +
                    "(?:abstract|final)?\\s*" +
                    "class\\s+([A-Z][A-Za-z0-9_]*)"
    );

    private static final Set<String> sameLettersClasses = Set.of(
            "SubClassAa", "SubClassBb", "SubClassCc", "SubClassDd", "SubClassEe",
            "SubClassFf", "SubClassGg", "SubClassHh", "SubClassIi", "SubClassJj",
            "SubClassKk", "SubClassLl", "SubClassMm", "SubClassNn", "SubClassOo",
            "SubClassPp", "SubClassQq", "SubClassRr", "SubClassSs", "SubClassTt",
            "SubClassUu", "SubClassVv", "SubClassWw", "SubClassXx", "SubClassYy",
            "SubClassZz");

    private final File file;

    public void exec() throws IOException {
        Set<String> all = new HashSet<>();
        Set<String> correct = new TreeSet<>();
        Set<String> incorrect = new TreeSet<>();


        log.info(file.getAbsolutePath());
        String src = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        Matcher matcher = CLASS_PATTERN.matcher(src);
        while (matcher.find()) {
            String className = matcher.group(1);
            all.add(className);
            if (sameLettersClasses.contains(className)) {
                correct.add(className);
            } else {
                incorrect.add(className);
            }
        }

        log.info("all count: " + all.size());
        log.info("correct count: " + correct.size());
        log.info("incorrect count: " + incorrect.size());
//        log.info("correct: " + String.join("\n ", correct));
//        log.info("incorrect: " + String.join("\n ", incorrect));

    }

}

package pl.org.opi.ragdeterm.loader;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
public class Start {

    public static void main(String[] args) {
        new CommandLine(new Loader()).execute(args);
    }

}

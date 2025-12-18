package pl.org.opi.ragdeterm.codeanalyser.classdepot;

import java.io.IOException;

public interface KlazzDepot {
    void loadJarKlazzes2Table(String jarFullPath, String sourceFullPath, boolean loadSource) throws IOException;
    void loadJdk2Table() throws IOException;
}

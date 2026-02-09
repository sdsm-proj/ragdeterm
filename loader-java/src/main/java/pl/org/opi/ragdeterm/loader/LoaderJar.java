package pl.org.opi.ragdeterm.loader;

import lombok.RequiredArgsConstructor;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotData;
import pl.org.opi.ragdeterm.klazzdepot.LoadKlazzDepot;
import pl.org.opi.ragdeterm.klazzdepot.SaveKlazzDepot;

import java.io.IOException;

@RequiredArgsConstructor
public class LoaderJar {

    private final String jarFullPath;

    private final String jarClassPath;

    private final boolean deleteBefore;

    private final boolean withSrcCode;

    private final String srcCodeFullPath;

    private final boolean resolveInheritance;

    private final boolean resolveStructure;

    private final boolean resolveCooperation;

    public void exec() throws IOException {
        LoadKlazzDepot loadKlazzDepot = new LoadKlazzDepot();
        KlazzDepotData klazzDepotData = loadKlazzDepot.loadJar(jarFullPath, jarClassPath);
        SaveKlazzDepot saveKlazzDepot = new SaveKlazzDepot(false, klazzDepotData, deleteBefore,
                withSrcCode, srcCodeFullPath,
                resolveInheritance, resolveStructure, resolveCooperation);
        saveKlazzDepot.save();
    }

}

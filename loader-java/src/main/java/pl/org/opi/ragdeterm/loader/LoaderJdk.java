package pl.org.opi.ragdeterm.loader;

import lombok.RequiredArgsConstructor;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotData;
import pl.org.opi.ragdeterm.klazzdepot.LoadKlazzDepot;
import pl.org.opi.ragdeterm.klazzdepot.SaveKlazzDepot;

import java.io.IOException;

@RequiredArgsConstructor
public class LoaderJdk {

    private final boolean deleteBefore;

    private final boolean resolveInheritance;

    private final boolean resolveStructure;

    private final boolean resolveCooperation;

    public void exec() throws IOException {
        LoadKlazzDepot loadKlazzDepot = new LoadKlazzDepot();
        KlazzDepotData klazzDepotData = loadKlazzDepot.loadJdk();
        SaveKlazzDepot saveKlazzDepot = new SaveKlazzDepot(true, klazzDepotData, deleteBefore,
                false, null,
                resolveInheritance, resolveStructure, resolveCooperation);
        saveKlazzDepot.save();
    }

}

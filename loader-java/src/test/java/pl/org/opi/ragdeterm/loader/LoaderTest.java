package pl.org.opi.ragdeterm.loader;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

import static pl.org.opi.ragdeterm.loader.Consta.*;

@Slf4j
public class LoaderTest {

    //@Test
    public void testJdk() {
        var loader = new Loader();
        loader.setConnUrl(CONN_URL);
        loader.setConnUser(CONN_USER);
        loader.setConnPsw(CONN_PSW);
        loader.setConnDriver(CONN_DRIVER);
        loader.setMode(Loader.Mode.jdk);
        loader.setDeleteBefore(false);
        loader.setResolveInheritance(false);
        loader.setResolveStructure(false);
        loader.setResolveCooperation(false);
        loader.exec();
    }

    //@Test
    public void loadHierarchy() {
        var loader = new Loader();
        loader.setConnUrl(CONN_URL);
        loader.setConnUser(CONN_USER);
        loader.setConnPsw(CONN_PSW);
        loader.setConnDriver(CONN_DRIVER);
        loader.setMode(Loader.Mode.jar);
        loader.setDeleteBefore(true);
        loader.setJarFullPath("c:\\ragdeterm\\hierarchy-lib\\target\\hierarchy-lib-1.0.0.jar");
        loader.setWithSrcCode(true);
        loader.setSrcCodeFullPath("c:\\ragdeterm\\hierarchy-lib\\src\\main\\java");
        loader.setResolveInheritance(true);
        loader.setResolveStructure(true);
        loader.setResolveCooperation(true);
        loader.exec();
    }

}

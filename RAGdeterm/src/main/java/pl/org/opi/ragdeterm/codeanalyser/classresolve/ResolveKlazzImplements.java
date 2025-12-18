package pl.org.opi.ragdeterm.codeanalyser.classresolve;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.db.util.GUID;
import pl.org.opi.ragdeterm.codeanalyser.classdepot.UrlClassLoaderHelper;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendEntity;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendService;

import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ResolveKlazzImplements {

    private final List<String> jarFullPathList;
    private final List<String> priorityJarSimpleNameList;
    private final List<String> fullTypeNameList;

    private URLClassLoader currentClassLoader;
    private UrlClassLoaderHelper urlClassLoaderHelper;

    public ResolveKlazzImplements(List<String> jarFullPathList, List<String> priorityJarSimpleNameList, List<String> fullTypeNameList) {
        this.jarFullPathList = jarFullPathList;
        if (priorityJarSimpleNameList != null) {
            this.priorityJarSimpleNameList = priorityJarSimpleNameList;
        } else {
            this.priorityJarSimpleNameList = new ArrayList<>();
        }
        this.fullTypeNameList = fullTypeNameList;
    }

    public void exec() {
        try {
            execCore();
        } catch (Exception ex) {
            throw new DbException(ex);
        }
    }

    private void execCore() throws MalformedURLException, ClassNotFoundException {
        initClassLoader();
        processAll();
    }

    private void initClassLoader() throws MalformedURLException {
        urlClassLoaderHelper = new UrlClassLoaderHelper(jarFullPathList);
        currentClassLoader = urlClassLoaderHelper.getOrderedClassLoader(priorityJarSimpleNameList);
    }

    private void processAll() throws ClassNotFoundException {
        for(String fullClassName: fullTypeNameList) {
            processOne(fullClassName);
        }
    }

    private void processOne(String fullClassName) throws ClassNotFoundException {
        KlazzService klazzService = new KlazzService();

        Class<?> klazzFrom = currentClassLoader.loadClass(fullClassName);
        Class<?>[] ifacesTo = klazzFrom.getInterfaces();

        if (ifacesTo.length > 0) {
            for (var ifaceTo : ifacesTo) {

                KlazzEntity klazzFromEntity = klazzService.findType(klazzFrom.getCanonicalName(), currentClassLoader.getURLs());
                KlazzEntity klazzToEntity = klazzService.findType(ifaceTo.getCanonicalName(), currentClassLoader.getURLs());

                if (klazzFromEntity == null) {
                    return;
                }
                if (klazzToEntity == null) {
                    return;
                }

                KlazzExtendEntity en = new KlazzExtendEntity();

                en.setIdUid(GUID.gen());
                en.setRelType(KlazzExtendEntity.EnumRelType.IMPL.toString());
                en.setIdFrom(klazzFromEntity.getIdUid());
                en.setSimpleNameFrom(klazzFromEntity.getSimpleName());
                en.setIdTo(klazzToEntity.getIdUid());
                en.setSimpleNameTo(klazzToEntity.getSimpleName());

                KlazzExtendService service = new KlazzExtendService();
                service.addExtend(en);
            }
        }
    }

}

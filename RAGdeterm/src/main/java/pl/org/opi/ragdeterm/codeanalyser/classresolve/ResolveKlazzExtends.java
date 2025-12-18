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
public class ResolveKlazzExtends {

    private final List<String> jarFullPathList;
    private final List<String> priorityJarSimpleNameList;
    private final List<String> fullTypeNameList;
    private URLClassLoader currentClassLoader;
    private UrlClassLoaderHelper urlClassLoaderHelper;

    public ResolveKlazzExtends(List<String> jarFullPathList, List<String> priorityJarSimpleNameList, List<String> fullTypeNameList) {
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
        for(String fullTypeName: fullTypeNameList) {
            processOne(fullTypeName);
        }
    }

    private void processOne(String fullTypeName) throws ClassNotFoundException {
        KlazzService klazzService = new KlazzService();

        Class<?> klazzFrom = currentClassLoader.loadClass(fullTypeName);
        Class<?> klazzTo = klazzFrom.getSuperclass();

        if (klazzFrom == null) {
            return;
        }
        if (klazzFrom.getCanonicalName() == null) {
            return;
        }

        if (klazzTo == null) {
            return;
        }
        if (klazzTo.getCanonicalName().equals("java.lang.Object")) {
            return;
        }
        if (klazzTo.getCanonicalName().equals("java.lang.Enum")) {
            return;
        }

        KlazzEntity klazzFromEntity = klazzService.findType(klazzFrom.getCanonicalName(), currentClassLoader.getURLs());
        KlazzEntity klazzToEntity = klazzService.findType(klazzTo.getCanonicalName(), currentClassLoader.getURLs());

        if (klazzFromEntity == null) {
            //log.error("Entity for CanonicalName not found: " + klazzFrom.getCanonicalName() + "klazzId: " + klazzFrom.toString());
            return;
        }
        if (klazzToEntity == null) {
            //log.error("Entity for CanonicalName not found: " + klazzTo.getCanonicalName() + "klazzId: " + klazzTo.toString());
            return;
        }

        KlazzExtendEntity en = new KlazzExtendEntity();

        en.setIdUid(GUID.gen());
        en.setRelType(KlazzExtendEntity.EnumRelType.EXTEND.toString());
        en.setIdFrom(klazzFromEntity.getIdUid());
        en.setSimpleNameFrom(klazzFromEntity.getSimpleName());
        en.setIdTo(klazzToEntity.getIdUid());
        en.setSimpleNameTo(klazzToEntity.getSimpleName());

        KlazzExtendService service = new KlazzExtendService();
        service.addExtend(en);
    }

}

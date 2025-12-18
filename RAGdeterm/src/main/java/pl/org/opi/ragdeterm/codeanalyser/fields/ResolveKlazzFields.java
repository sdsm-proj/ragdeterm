package pl.org.opi.ragdeterm.codeanalyser.fields;

import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.db.util.GUID;
import pl.org.opi.ragdeterm.codeanalyser.classdepot.UrlClassLoaderHelper;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazzField.KlazzFieldEntity;
import pl.org.opi.ragdeterm.repository.klazzField.KlazzFieldService;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ResolveKlazzFields {

    private final List<String> jarFullPathList;
    private final List<String> priorityJarSimpleNameList;
    private final List<KlazzEntity> klazzList;

    private URLClassLoader currentClassLoader;
    private UrlClassLoaderHelper urlClassLoaderHelper;

    public ResolveKlazzFields(List<String> jarFullPathList, List<String> priorityJarSimpleNameList, List<KlazzEntity> klazzList) {
        this.jarFullPathList = jarFullPathList;
        if (priorityJarSimpleNameList != null) {
            this.priorityJarSimpleNameList = priorityJarSimpleNameList;
        } else {
            this.priorityJarSimpleNameList = new ArrayList<>();
        }
        this.klazzList = klazzList;
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
        for (var KlazzEntity : klazzList) {
            processOne(KlazzEntity);
        }
    }

    private void processOne(KlazzEntity klazzEntity) throws ClassNotFoundException {
        Class<?> k = currentClassLoader.loadClass(klazzEntity.getFullTypeName());
        Set<String> fieldTypeSet = new TreeSet<String>();

        Field[] declaredFields = k.getDeclaredFields();
        for (var f : declaredFields) {
            fieldTypeSet.add(f.getType().getCanonicalName());

            if (f.getType().isArray()) {
                fieldTypeSet.add(f.getType().getComponentType().getCanonicalName());
            }

            if (f.getGenericType() instanceof ParameterizedType pt) {
                var params = pt.getActualTypeArguments();
                for (var p : params) {
                    Class<?> pc = (Class<?>) p;
                    fieldTypeSet.add(pc.getCanonicalName());
                }
            }
        }

        for (var fld : fieldTypeSet.toArray()) {
            findAndSave(klazzEntity, (String) fld);
        }
    }

    private void findAndSave(KlazzEntity parentKlazzEntity, String fieldCanonicalName) {
        KlazzService klazzService = new KlazzService();
        KlazzEntity foundFieldEntity = klazzService.findType(fieldCanonicalName, priorityJarSimpleNameList);
        if (foundFieldEntity != null) {
            if (!foundFieldEntity.getJarSimpleName().equalsIgnoreCase("JDK")) {
                if (parentKlazzEntity.getIdUid().equalsIgnoreCase(foundFieldEntity.getIdUid())) {
                    return; // seems pointless, but important for ENUM
                }
                KlazzFieldEntity field = new KlazzFieldEntity();
                field.setIdUid(GUID.gen());
                field.setIdKlazz(parentKlazzEntity.getIdUid());
                field.setKlazzSimpleName(parentKlazzEntity.getSimpleName());
                field.setIdKlazzField(foundFieldEntity.getIdUid());
                field.setKlazzFieldSimpleName(foundFieldEntity.getSimpleName());
                KlazzFieldService klazzFieldService = new KlazzFieldService();
                klazzFieldService.addField(field);
            }
        }
    }

}

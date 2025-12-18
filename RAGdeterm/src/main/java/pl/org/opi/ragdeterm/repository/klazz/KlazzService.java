package pl.org.opi.ragdeterm.repository.klazz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendStrings;

import java.net.URL;
import java.util.List;

@Slf4j
public class KlazzService {

    public void addKlazz(KlazzEntity en) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepo(trx);
            repo.create(en);
            trx.commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public KlazzEntity findById(String id) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            return repo.findByKey(id);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public List<KlazzEntity> findAll() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            List<KlazzEntity> klazzList =  repo.findAll();
            return klazzList;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public void deleteAll() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            repo.deleteAll();
            trx.commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public KlazzEntity findType(String fullCanonicalName) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            List<KlazzEntity> klazzList =  repo.findType(fullCanonicalName);
            if (klazzList.size() == 1) {
                return klazzList.getFirst();
            }
            if (klazzList.size() > 1) {
                throw new DbException("More than 1 class: " + fullCanonicalName);
            }
            return null;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public KlazzEntity findType(String fullCanonicalName, URL[] urls) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            List<KlazzEntity> klazzList =  repo.findType(fullCanonicalName);
            if (klazzList.size() == 1) {
                return klazzList.getFirst();
            }
            if (klazzList.size() > 1) {
                for(var url: urls) {
                    for(var klazzEn: klazzList) {
                        String baseUrlFileName = FilenameUtils.getBaseName(url.getFile());
                        if (baseUrlFileName.equals(klazzEn.getJarSimpleName())) {
                            return klazzEn;
                        }
                    }
                }
            }
            return null;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public KlazzEntity findType(String fullCanonicalName, List<String> priorityJarSimpleNameList) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            List<KlazzEntity> klazzList =  repo.findType(fullCanonicalName);
            if (klazzList.size() == 1) {
                return klazzList.getFirst();
            }
            if (klazzList.size() > 1) {
                for(var priorityJarSimpleName: priorityJarSimpleNameList) {
                    for(var klazzEn: klazzList) {
                        String baseUrlFileName = priorityJarSimpleName;
                        if (baseUrlFileName.equals(klazzEn.getJarSimpleName())) {
                            return klazzEn;
                        }
                    }
                }
            }
            return null;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public List<String> findAllSimpleJarNames() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            return repo.findAllSimpleJarNames();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public List<KlazzEntity> findAll4Jar(String simpleJarName) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzRepox(trx);
            return repo.findAll4Jar(simpleJarName);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public void updateKlazzExtendStrings(List<KlazzExtendStrings> klazzExtendStrings) {
        for(var kes: klazzExtendStrings) {
            try (var trx = DbConnContainer.newTrx()) {
                var repo = new KlazzRepo(trx);
                var en = repo.findByKey(kes.getKlazzId());
                en.setExtendSimpleNames(kes.getExtendSimpleNames());
                en.setExtendFullNames(kes.getExtendFullNames());
                en.setExtendIds(kes.getExtendIds());
                en.setImplSimpleNames(kes.getImplSimpleNames());
                en.setImplFullNames(kes.getImplFullNames());
                en.setImplIds(kes.getImplIds());
                repo.update(en);
                trx.commit();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                throw new DbException(ex.getMessage(), ex);
            }
        }
    }

}

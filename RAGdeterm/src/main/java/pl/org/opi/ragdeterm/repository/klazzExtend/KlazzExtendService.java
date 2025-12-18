package pl.org.opi.ragdeterm.repository.klazzExtend;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.db.exception.DbException;

import java.util.List;

@Slf4j
public class KlazzExtendService {

    public void addExtend(KlazzExtendEntity en) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzExtendRepo(trx);
            repo.create(en);
            trx.commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public List<KlazzExtendEntity> findAll() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzExtendRepo(trx);
            List<KlazzExtendEntity> enList =  repo.findAll();
            return enList;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public void deleteAll() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzExtendRepo(trx);
            repo.deleteAll();
            trx.commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

}

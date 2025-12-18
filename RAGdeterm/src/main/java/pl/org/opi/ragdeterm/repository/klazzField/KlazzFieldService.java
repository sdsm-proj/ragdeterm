package pl.org.opi.ragdeterm.repository.klazzField;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.db.exception.DbException;

import java.util.List;

@Slf4j
public class KlazzFieldService {

    public void addField(KlazzFieldEntity en) {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzFieldRepo(trx);
            repo.create(en);
            trx.commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public List<KlazzFieldEntity> findAll() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzFieldRepo(trx);
            List<KlazzFieldEntity> enList =  repo.findAll();
            return enList;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public void deleteAll() {
        try(var trx = DbConnContainer.newTrx()) {
            var repo = new KlazzFieldRepo(trx);
            repo.deleteAll();
            trx.commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

}

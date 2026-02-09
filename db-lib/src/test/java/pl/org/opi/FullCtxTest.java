package pl.org.opi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.org.opi.dbaccess.conn.DbConnConfig;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.dbaccess.exception.DbException;
import pl.org.opi.ragdeterm.repo.ctx.FullCtxRepox;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;

import java.util.LinkedHashSet;

@Slf4j
public class FullCtxTest {

    @Test
    public void test() {
        DbConnContainer.addDbConn(new DbConnConfig("Default",
                Consta.CONN_URL, Consta.CONN_USER, Consta.CONN_PSW, Consta.CONN_DRIVER,
                false, 20, 180000
        ));

        try (var trx = DbConnContainer.newTrx()) {
            var fullCtxRepox = new FullCtxRepox(trx);
            LinkedHashSet<String> structureIds = fullCtxRepox
                    .findFullCtxOfType("pl.org.opi.vehicle.land.car.subtypes.Hatchback", "");

            var klazzRepox = new KlazzRepox(trx);
            var types = klazzRepox.findTypesByIdList(structureIds.stream().toList());
            System.out.println("-------------------------------------");
            for(var t: types) {
                System.out.println(t.getFullCanonicalName());
            }
            System.out.println("-------------------------------------");

        } catch (Exception ex) {
            throw new DbException(ex.getMessage(), ex);
        }
    }

}

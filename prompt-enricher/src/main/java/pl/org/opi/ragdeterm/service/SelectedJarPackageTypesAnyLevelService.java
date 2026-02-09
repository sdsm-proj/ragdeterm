package pl.org.opi.ragdeterm.service;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.dbaccess.exception.DbException;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;
import pl.org.opi.ragdeterm.service.util.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SelectedJarPackageTypesAnyLevelService {

    public String exec(String jarSimpleName, String pckgName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        try {
            return execCore(jarSimpleName, pckgName, answerType, prefix, suffix, separator);
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }

    private String execCore(String jarSimpleName, String pckgName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        List<KlazzEntity> rslt;

        try (var trx = DbConnContainer.newTrx()) {
            var klazzRepox = new KlazzRepox(trx);
            rslt = new ArrayList<>(klazzRepox.findJarPackageStartsWith(jarSimpleName, pckgName));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }

        return switch (answerType) {
            case SHORT_NAME -> new RsltShortName(rslt, prefix, suffix, separator).exec();
            case LONG_NAME -> new RsltLongName(rslt, prefix, suffix, separator).exec();
            case ID -> new RsltId(rslt, prefix, suffix, separator).exec();
            case SOURCE_CODE -> new RsltSourceCode(rslt, prefix, suffix, separator).exec();
        };
    }

}

package pl.org.opi.ragdeterm.service;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.dbaccess.exception.DbException;
import pl.org.opi.ragdeterm.repo.ctx.StructureCtxRepox;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;
import pl.org.opi.ragdeterm.service.util.*;

import java.util.LinkedHashSet;

@Slf4j
public class StructureContextService {

    public String exec(String canonicalBaseClassName, String preferredJarSimpleName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        try (var trx = DbConnContainer.newTrx()) {
            var structureCtxRepox = new StructureCtxRepox(trx);
            LinkedHashSet<String> structureIds = structureCtxRepox.findStructureOfType(canonicalBaseClassName, preferredJarSimpleName);
            var klazzRepox = new KlazzRepox(trx);
            var types = klazzRepox.findTypesByIdList(structureIds.stream().toList());
            return switch (answerType) {
                case SHORT_NAME -> new RsltShortName(types, prefix, suffix, separator).exec();
                case LONG_NAME -> new RsltLongName(types, prefix, suffix, separator).exec();
                case ID -> new RsltId(types, prefix, suffix, separator).exec();
                case SOURCE_CODE -> new RsltSourceCode(types, prefix, suffix, separator).exec();
            };
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

}

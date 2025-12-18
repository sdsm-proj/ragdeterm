package pl.org.opi.ragdeterm.codeanalyser.inMemKlazzContext;

import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.service.universal.KlazzBase;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazz.KlazzSimpleVo;
import pl.org.opi.ragdeterm.repository.klazzField.KlazzFieldEntity;
import pl.org.opi.ragdeterm.repository.klazzField.KlazzFieldService;

import java.util.ArrayList;
import java.util.List;

public class KlazzContextMemGraph {

    private KlazzCtx ctx;
    private final List<KlazzFieldEntity> klazzFieldList = new ArrayList<>();

    public KlazzCtx createCtx(List<String> klazzIdList) {
       return execCore(klazzIdList);
    }

    private KlazzCtx execCore(List<String> klazzIdList) {
        loadKlazzField();

        KlazzService klazzService = new KlazzService();
        List<KlazzEntity> base = new KlazzBase().exec(true, klazzService.findById(klazzIdList.getFirst()).getFullCanonicalName());
        List<String> _newKlazzIdList = new ArrayList<>();
        _newKlazzIdList.addAll(klazzIdList);
        for(var b: base) {
            _newKlazzIdList.add(b.getIdUid());
        }
        var topKlazzez = KlazzFieldEntity.findAllByIdKlazzezDistinct(klazzFieldList, _newKlazzIdList);
        if (topKlazzez.isEmpty()) {
            throw new DbException("No classes found for KlazzCtx");
        }
        List<KlazzSimpleVo> klazzSimpleVos = new ArrayList<>();
        for (var __id: _newKlazzIdList) {
            var __en = klazzService.findById(__id);
            if (__en != null) {
                klazzSimpleVos.add(new KlazzSimpleVo(__en.getIdUid(), __en.getSimpleName()));
            }
        }
        ctx = new KlazzCtx(klazzSimpleVos);
        fillAll();
        return ctx;
    }

    private void loadKlazzField() {
        KlazzFieldService service = new KlazzFieldService();
        klazzFieldList.clear();
        klazzFieldList.addAll(service.findAll());
    }

    private void fillAll() {
        for(var topSimple: ctx.getRootKlazzList()) {
            KlazzCtxNode topNode = new KlazzCtxNode(topSimple.getId(), topSimple.getSimpleName(), 1);
            ctx.getTopNodes().add(topNode);
            appendToFlat(topNode);
            fillNodeRcr(topNode);
        }
    }

    private void fillNodeRcr(KlazzCtxNode parentNode) {
        var fields = KlazzFieldEntity.findAllByIdKlazz(klazzFieldList, parentNode.getId());
        for(var fld: fields) {
            KlazzCtxNode newNode = new KlazzCtxNode(fld.getIdKlazzField(), fld.getKlazzFieldSimpleName(), parentNode.getLevel() + 1);
            parentNode.getSubs().add(newNode);
            appendToFlat(newNode);

            fillNodeRcr(newNode);
        }
    }

    private void appendToFlat(KlazzCtxNode newNode) {
        if (ctx.getFlatNodes().get(newNode.getId()) == null) {
            ctx.getFlatNodes().put(newNode.getId(), newNode);
        } else {
            var fn = ctx.getFlatNodes().get(newNode.getId());
            if (newNode.getLevel() > fn.getLevel()) {
                ctx.getFlatNodes().put(newNode.getId(), newNode);
            }
        }
    }

}

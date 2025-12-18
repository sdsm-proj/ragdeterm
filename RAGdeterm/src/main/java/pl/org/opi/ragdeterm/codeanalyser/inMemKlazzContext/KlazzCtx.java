package pl.org.opi.ragdeterm.codeanalyser.inMemKlazzContext;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazz.KlazzSimpleVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class KlazzCtx {
    private final List<KlazzSimpleVo> rootKlazzList;
    private final List<KlazzCtxNode> topNodes = new ArrayList<>();
    private final LinkedHashMap<String, KlazzCtxNode> flatNodes = new LinkedHashMap<>();
    private final List<KlazzEntity> klazzEntityList = new ArrayList<>();

    public List<KlazzEntity> getKlazzEntityList() {
        if (!klazzEntityList.isEmpty()) {
            return klazzEntityList;
        } else {
            List<KlazzCtxNode> nodes = new ArrayList<>();
            nodes.addAll(flatNodes.values());
            Collections.sort(nodes);
            KlazzService klazzService = new KlazzService();
            for(var fn: nodes) {
                klazzEntityList.add(klazzService.findById(fn.getId()));
            }
        }
        return klazzEntityList;
    }

    public String print() {
        StringBuilder sb = new StringBuilder("");

        Collections.sort(topNodes);

        sb.append("------------------------------------------------------------------------------\n");
        for(var topNode: topNodes) {
            printNodeRqr(topNode, sb);
        }
        sb.append("------------------------------------------------------------------------------\n");
        printFlat(sb);
        sb.append("------------------------------------------------------------------------------");
        return sb.toString();
    }

    private void printNodeRqr(KlazzCtxNode node, StringBuilder sb) {
        String s = StringUtils.repeat("  ", node.getLevel());
        s += node.getSimpleName();
        s += "\n";
        sb.append(s);

        var _subs = node.getSubs();
        Collections.sort(_subs);
        for(var sub: _subs) {
            printNodeRqr(sub, sb);
        }
    }

    private void printFlat(StringBuilder sb) {
        List<KlazzCtxNode> nodes = new ArrayList<>();
        nodes.addAll(flatNodes.values());
        Collections.sort(nodes);

        KlazzService klazzService = new KlazzService();

        for(var fn: nodes) {
            String s = klazzService.findById(fn.getId()).getFullCanonicalName();
            s += "\n";
            sb.append(s);
        }
    }

}

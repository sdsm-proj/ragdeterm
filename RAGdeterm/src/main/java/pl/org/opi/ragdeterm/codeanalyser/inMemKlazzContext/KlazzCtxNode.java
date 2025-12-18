package pl.org.opi.ragdeterm.codeanalyser.inMemKlazzContext;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
public class KlazzCtxNode implements Comparable {
    private final String id;
    private final String simpleName;
    private final int level;
    private final List<KlazzCtxNode> subs = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        return simpleName.compareTo(((KlazzCtxNode)o).simpleName);
    }

}

package pl.org.opi.ragdeterm.codeanalyser.inMemKlazzExtend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class KlazzNode {
    private final String klazzId;
    private final String jarSimpleName;
    private final String simpleName;
    private final String fullCanonicalName;
    private final Map<String, KlazzSubNode> subs = new HashMap<>();

}

package pl.org.opi.ragdeterm.codeanalyser.inMemKlazzExtend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KlazzSubNode {
    private final String relType;
    private final KlazzNode klazzNode;
}

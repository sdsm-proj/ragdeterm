package pl.org.opi.ragdeterm.klazzdepot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;

@RequiredArgsConstructor
public class KlazzDepotElem {
    @Getter
    private final Class<?> k;
    @Getter
    private final KlazzEntity klazzEntity;
}

package pl.org.opi.vehicles.component.level2;

import pl.org.opi.vehicles.component.level2.level3.Door;
import pl.org.opi.vehicles.component.level2.level3.Window;

import java.util.List;

public abstract class BodyStructure {
    private List<Door> doors;
    private Window[] windows;
}
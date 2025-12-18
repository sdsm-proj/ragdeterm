package pl.org.opi.vehicles.land.other;

import pl.org.opi.vehicles.LandVehicle;
import pl.org.opi.vehicles.component.level2.EngineSystem;

public abstract class Truck extends LandVehicle {
    private EngineSystem engineSystem;
    private double maxLoadCapacity;
}
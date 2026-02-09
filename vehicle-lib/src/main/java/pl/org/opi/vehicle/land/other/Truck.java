package pl.org.opi.vehicle.land.other;

import pl.org.opi.vehicle.LandVehicle;
import pl.org.opi.vehicle.component.level2.EngineSystem;

public abstract class Truck extends LandVehicle {
    private EngineSystem engineSystem;
    private double maxLoadCapacity;
}
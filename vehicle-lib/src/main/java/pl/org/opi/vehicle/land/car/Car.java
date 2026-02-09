package pl.org.opi.vehicle.land.car;

import pl.org.opi.vehicle.LandVehicle;
import pl.org.opi.vehicle.component.level2.*;
import pl.org.opi.vehicle.utility.*;

public abstract class Car extends LandVehicle {
    private EngineSystem engineSystem;
    private TransmissionSystem transmissionSystem;
    private ElectricalSystem electricalSystem;
    private SuspensionSystem suspensionSystem;
    private BodyStructure bodyStructure;
    private InteriorSystem interiorSystem;
    private Tire[] tires;
    private FuelType fuelType;
    private NavigationSystem navigationSystem;
    private SafetySystem safetySystem;
    private AirCondition airCondition;
    private InfotainmentSystem infotainmentSystem;
}
package pl.org.opi.vehicles.land.car;

import pl.org.opi.vehicles.LandVehicle;
import pl.org.opi.vehicles.component.level2.*;
import pl.org.opi.vehicles.utility.*;

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
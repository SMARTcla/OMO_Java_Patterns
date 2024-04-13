package cz.cvut.omo.sp.tech.auto;

import cz.cvut.omo.sp.alive.person.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A builder class for creating instances of the Transport class.
 *
 * This class provides a convenient way to construct Transport objects with various attributes.
 */
public class AutoBuilder {

    private static final Logger LOGGER = LogManager.getLogger(AutoBuilder.class.getName());

    /** The name of the transport. */
    private String name;

    /** The type of the transport. */
    private TransportType type;

    /** The current user of the transport (a person). */
    private Person currentUser;

    /**
     * Creates a new instance of AutoBuilder.
     */
    public AutoBuilder() {
    }

    /**
     * Sets the name of the transport.
     *
     * @param name The name of the transport.
     * @return This AutoBuilder instance.
     */
    public AutoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the type of the transport based on a string representation.
     *
     * @param type The string representation of the transport type.
     * @return This AutoBuilder instance.
     */
    public AutoBuilder withType(String type) {
        this.type = getTypeTransport(type);
        return this;
    }

    /**
     * Sets the current user of the transport (a person).
     *
     * @param person The current user of the transport.
     * @return This AutoBuilder instance.
     */
    public AutoBuilder withCurrentUser(Person person) {
        this.currentUser = person;
        return this;
    }

    /**
     * Converts a string representation of the transport type to a TransportType enum value.
     *
     * @param type The string representation of the transport type.
     * @return The corresponding TransportType enum value, or null if the type is not recognized.
     */
    private TransportType getTypeTransport(String type) {
        return switch (type) {
            case "BICYCLE" -> TransportType.BICYCLE;
            case "SKIS" -> TransportType.SKIS;
            case "CAR" -> TransportType.CAR;
            case "SCOOTER" -> TransportType.SCOOTER;
            case "MOTORBIKE" -> TransportType.MOTORBIKE;
            default -> null;
        };
    }

    /**
     * Builds a Transport object with the specified attributes.
     *
     * @return The constructed Transport object, or null if required arguments are not provided.
     */
    public Transport build() {
        if (this.name == null || this.type == null) {
            LOGGER.warn("Some required arguments are not provided: name, type!");
            return null;
        }
        Transport transport = new Transport(this.name, this.type);
        transport.setCurrentUser(this.currentUser);
        return transport;
    }
}

package cz.cvut.omo.sp.places;

import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.facade.Facade;

import java.time.LocalDateTime;

public interface Observer {

    /**
     * This method is called by the observable subject when it undergoes a change.
     *
     * @param type     The type of event or change that occurred.
     * @param dateTime The timestamp indicating when the event occurred.
     * @param facade   The `Facade` object that provides a simplified interface to the underlying system.
     */
    void update(TypeEvent type, LocalDateTime dateTime, Facade facade);
}
package cz.cvut.omo.sp.tech.sencor;

import cz.cvut.omo.sp.events.TypeEvent;
import cz.cvut.omo.sp.facade.Facade;
import cz.cvut.omo.sp.places.Observer;

import java.time.LocalDateTime;

/**
 * The `Subject` interface defines the contract for objects that act as subjects in the observer pattern.
 * Subjects can have multiple observers subscribed to them, and they notify observers when changes occur.
 */
public interface Subject {

    /**
     * Adds an observer to the list of subscribers for this subject.
     *
     * @param observer The observer to be added.
     */
    void addSubscriber(Observer observer);

    /**
     * Notifies all subscribers (observers) of this subject about a change or event.
     *
     * @param type     The type of event or change that occurred.
     * @param dateTime The timestamp indicating when the event occurred.
     * @param facade   The `Facade` object that provides a simplified interface to the underlying system.
     */
    void notifySubscribers(TypeEvent type, LocalDateTime dateTime, Facade facade);
}

package cz.cvut.omo.sp.events;

import cz.cvut.omo.sp.Entity;

import java.time.LocalDateTime;

/**
 * Class representing an event in a simulation.
 *
 * This class contains information about an event, including its context, source entity, start and end date and time,
 * target entity, and type of event.
 */
public class Event {
    /** The context or description of the event. */
    private String context;

    /** The date and time when the event ends. */
    private LocalDateTime endDateTime;

    /** The source entity associated with the event. */
    private Entity source;

    /** The date and time when the event starts. */
    private LocalDateTime startDateTime;

    /** The target entity associated with the event. */
    private Entity target;

    /** The type of event (e.g., TypeEvent.DANGER, TypeEvent.SCHEDULED, etc.). */
    private TypeEvent type;

    /**
     * Creates a new event with the specified context, source entity, start date and time, target entity, and event type.
     *
     * @param context       The context or description of the event.
     * @param source        The source entity associated with the event.
     * @param startDateTime The date and time when the event starts.
     * @param target        The target entity associated with the event.
     * @param type          The type of event (e.g., TypeEvent.DANGER, TypeEvent.SCHEDULED, etc.).
     */
    public Event(String context, Entity source, LocalDateTime startDateTime, Entity target, TypeEvent type) {
        this.context = context;
        this.source = source;
        this.startDateTime = startDateTime;
        this.target = target;
        this.type = type;
    }
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Entity getSource() {
        return source;
    }

    public void setSource(Entity source) {
        this.source = source;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public TypeEvent getType() {
        return type;
    }

    public void setType(TypeEvent type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", startTime=" + startDateTime +
                ", endTime=" + endDateTime +
                ", context='" + context + '\'' +
                '}';
    }

}

package hu.kleatech.jigsaw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class EventGroup implements Serializable{
    @OneToMany(mappedBy = "eventGroup")
    private List<Event> events;

    @Column
    private String name;

    @Column
    @Lob
    private Properties infos;

    @Id
    @GeneratedValue
    private Long id;

    protected EventGroup(){} //For serialization and prototype creation only
    public EventGroup(List<Event> events, String name, Properties infos) {
        this.events = events;
        this.name = name;
        this.infos = infos;
    }

    public List<Event> getEvents() { return events; }
    public String getName() { return name; }
    public Long getId() { return id; }
    public Properties getInfos() { return infos==null?new Properties():infos; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventGroup)) return false;
        EventGroup that = (EventGroup) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public String toString() {
        return "EventGroup{" + "events=" + events.size() + ", name='" + name + '\'' + ", infos=" + infos.size() + '}'; }

    public void overwrite(EventGroup newEventGroup) {
        this.infos = newEventGroup.infos;
        this.events = newEventGroup.events;
        this.name = newEventGroup.name;
    }


}

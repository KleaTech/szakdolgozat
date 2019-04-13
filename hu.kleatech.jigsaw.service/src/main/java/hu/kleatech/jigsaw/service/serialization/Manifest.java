package hu.kleatech.jigsaw.service.serialization;

public final class Manifest {
        private String eventGroup;
        private String template;
        private Events[] events;
        public String getEventGroup () { return eventGroup; }
        public void setEventGroup (String eventGroup) { this.eventGroup = eventGroup; }
        public String getTemplate () { return template; }
        public void setTemplate (String template) { this.template = template; }
        public Events[] getEvents () { return events; }
        public void setEvents (Events[] events) { this.events = events; }
        @Override public String toString() { return "ClassPojo [eventGroup = "+eventGroup+", template = " +template+", events = "+events+"]"; }
}

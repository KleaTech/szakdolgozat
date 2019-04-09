package hu.kleatech.jigsaw.service.serialization;

public final class Manifest {
        private String teamFragment;
        private String eventGroup;
        private String template;
        private String participantFragment;
        private Events[] events;
        public String getTeamFragment () { return teamFragment; }
        public void setTeamFragment (String teamFragment) { this.teamFragment = teamFragment; }
        public String getEventGroup () { return eventGroup; }
        public void setEventGroup (String eventGroup) { this.eventGroup = eventGroup; }
        public String getTemplate () { return template; }
        public void setTemplate (String template) { this.template = template; }
        public String getParticipantFragment () { return participantFragment; }
        public void setParticipantFragment (String participantFragment) { this.participantFragment = participantFragment; }
        public Events[] getEvents () { return events; }
        public void setEvents (Events[] events) { this.events = events; }
        @Override public String toString() { return "ClassPojo [teamFragment = "+teamFragment+", eventGroup = "+eventGroup+", template = " +template+", participantFragment = "+participantFragment+", events = "+events+"]"; }
}

package hu.kleatech.jigsaw.service.serialization;

public final class Events {
    private String template;
    private String name;
    private Competitions[] competitions;
    public String getTemplate () { return template; }
    public void setTemplate (String template) { this.template = template; }
    public String getName () { return name; }
    public void setName (String name) { this.name = name; }
    public Competitions[] getCompetitions () { return competitions; }
    public void setCompetitions (Competitions[] competitions) { this.competitions = competitions; }
    @Override public String toString() { return "ClassPojo [template = "+template+", name = "+name+", competitions = "+competitions+"]"; }
}

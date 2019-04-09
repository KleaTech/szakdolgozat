package hu.kleatech.jigsaw.service.serialization;

public final class Competitions {
    private String template;
    private String name;
    public String getTemplate () { return template; }
    public void setTemplate (String template) { this.template = template; }
    public String getName () { return name; }
    public void setName (String name) { this.name = name; }
    @Override public String toString() { return "ClassPojo [template = "+template+", name = "+name+"]"; }
}

package hu.kleatech.jigsaw.controller;

public final class JavaScript {
    public static final JavaScript JAVASCRIPT = new JavaScript();
    private JavaScript(){}
    
    public Then queryById(String id) { return new Then("$('#" + id + "'"); }
    public String quot(String str) { return '"' + str + '"'; }
    public String apos(String str) { return "'" + str + "'"; }
    public String quot() { return "\""; }
    public String apos() { return "'"; }
    public Then func(String name) { return new Then(name + '('); }
    public Then queryById(Then id) { return new Then("$('#" + id.toString() + "'"); }
    public String quot(Then str) { return '"' + str.toString() + '"'; }
    public String apos(Then str) { return "'" + str.toString() + "'"; }
    public Then func(Then name) { return new Then(name.toString() + '('); }
    
    public static final class Then {
        private String soFar;
        Then(String soFar) { this.soFar = soFar; }
        
        public Then func(String name) { 
            if (soFar.endsWith(",")) soFar = soFar.substring(0, soFar.lastIndexOf(','));
            return new Then(soFar + ")." + name + '('); 
        }
        public Then argStr(String arg) { return new Then(soFar + "'" + arg + "',"); }
        public Then argObj(String arg) { return new Then(soFar + arg + ','); }
        public Then func(Then name) { 
            if (soFar.endsWith(",")) soFar = soFar.substring(0, soFar.lastIndexOf(','));
            return new Then(soFar + ")." + name.toString() + '('); 
        }
        public Then argStr(Then arg) { return new Then(soFar + "'" + arg.toString() + "',"); }
        public Then argObj(Then arg) { return new Then(soFar + arg.toString() + ','); }
        
        @Override
        public String toString() {
            if (soFar.endsWith("(")) return soFar + ')';
            if (soFar.endsWith(",")) return soFar.substring(0, soFar.lastIndexOf(',')) + ')';
            return null;
        }
    }
}

module hu.kleatech.jigsaw.scripting {
    requires java.base; //This is not necessary, it's implicitly included
    requires java.scripting;
    requires hu.kleatech.jigsaw.api;
    
    exports hu.kleatech.jigsaw.scripting;
}
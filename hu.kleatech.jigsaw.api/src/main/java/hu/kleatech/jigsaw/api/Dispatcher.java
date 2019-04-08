package hu.kleatech.jigsaw.api;

public final class Dispatcher {
    private Dispatcher(){}
    
    private static EngineProvider engineProvider = null;
    private static Statistics statistics = null;
    
    public static EngineProvider getEngineProvider() {
        return engineProvider;
    }
    public static void setEngineProvider(EngineProvider engineProvider) {
        if (Dispatcher.engineProvider != null) throw new IllegalStateException("EngineProvider can only be set once.");
        Dispatcher.engineProvider = engineProvider;
        System.out.println("EngineProvider loaded");
    }
    
    public static Statistics getStatistics() {
        return statistics;
    }
    public static void setStatistics(Statistics statistics) {
        if (Dispatcher.statistics != null) throw new IllegalStateException("Statistics can only be set once.");
        Dispatcher.statistics = statistics;
        System.out.println("Statistics loaded");
    }
}

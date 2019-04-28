package hu.kleatech.jigsaw.service.summarization;

import hu.kleatech.jigsaw.api.Dispatcher;
import hu.kleatech.jigsaw.model.*;
import hu.kleatech.jigsaw.utils.*;
import static hu.kleatech.jigsaw.utils.Utils.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service("summarizationService")
class SummarizationServiceImpl implements hu.kleatech.jigsaw.service.summarization.SummarizationService {    
    @Override
    public SummarizationResults summarize(EventGroup eventGroup, Order order) throws FileNotFoundException {
        var results = new SummarizationResults();
        var byParticipant = new ListMap<Participant, Double>();
        var byTeam = new ListMap<Team, Double>();
        
        var engineProvider = Dispatcher.getEngineProvider();
        var path = relativePath(Constants.MODULES_DIR_NAME, eventGroup.getName());
        var engine = engineProvider.getEngine(path);
        var functionTable = new HashMap<Competition, Function<List<Double>, Double>>(10);
        var competitions = eventGroup.getEvents().stream().flatMap(e -> e.getCompetitions().stream()).collect(Collectors.toList());
                
        for(var competition : competitions) {
            var scriptName = Arrays.stream(path.toFile().listFiles())
                    .map(f -> f.getName())
                    .filter(f -> f.startsWith(competition.getTemplate()))
                    .filter(f -> !f.endsWith("html"))
                    .filter(f -> !f.replaceFirst("[.][^.]+$", "").endsWith("_pre"))
                    .findFirst();
            if (!scriptName.isPresent()) continue;
            var func = engine.result(scriptName.get());
            functionTable.put(competition, func);
        }
        
        var participants = competitions.stream().flatMap(c -> c.getRounds().stream()).map(r -> r.getParticipant()).distinct().collect(Collectors.toList());
        var unsortedParticipantMap = new HashMap<Participant, Double>(50);
        participants.forEach(participant -> {
            var point = 0d;
            for(var round : participant.getRounds()) {
                point += round.result(functionTable.get(round.getCompetition()));
            }
            unsortedParticipantMap.put(participant, point);
        });
        
        unsortedParticipantMap.entrySet().stream().sorted((a,b) -> order == Order.ASCENDING ? a.getValue().compareTo(b.getValue()) : b.getValue().compareTo(a.getValue()))
                                                  .forEachOrdered(e -> byParticipant.put(e.getKey(), e.getValue()));
        results.setByParticipant(byParticipant.unmodifiable());
        
        var unsortedTeamMap = unsortedParticipantMap.entrySet().stream().collect(Collectors.groupingBy(e -> e.getKey().getTeam(), Collectors.summingDouble(Map.Entry::getValue)));
        unsortedTeamMap.entrySet().stream().sorted((a,b) -> order == Order.ASCENDING ? a.getValue().compareTo(b.getValue()) : b.getValue().compareTo(a.getValue()))
                                           .forEachOrdered(e -> byTeam.put(e.getKey(), e.getValue()));
        results.setByTeam(byTeam);
        
        return results;
    }
}

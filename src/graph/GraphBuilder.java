package graph;

import framework.Action;
import java.util.*;

public class GraphBuilder {

    public static void loadGraph(String data, Map<String, List<Action>> graph) {
        // Split by lines
        String[] lines = data.split("\\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Pattern: "S -> A : 2"
            String[] parts = line.split("->|:");
            String source = parts[0].trim();
            String target = parts[1].trim();
            int cost = Integer.parseInt(parts[2].trim());

            // Add to the adjacency list
            graph.computeIfAbsent(source, k -> new ArrayList<>())
                 .add(new GraphAction(source + " to " + target, target, cost));
        }
    }

    public static void loadHeuristics(String data, Map<String, Integer> heuristics) {
        String[] lines = data.split("\\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Pattern: "S : 7"
            String[] parts = line.split(":");
            heuristics.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
    }
}
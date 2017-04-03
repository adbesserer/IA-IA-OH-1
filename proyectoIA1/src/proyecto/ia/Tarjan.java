package proyecto.ia;

import java.util.HashMap;
import java.util.*;

/**
 * Created by idhrenniel on 2/04/17.
 */
public class Tarjan {
    private int V;
    private int preCount;
    private int[] low;
    private boolean[] visited;
    private Stack<Integer> stack;
    private List<List<Integer>> sccComp;
    private HashMap<Integer, Integer> graph;


    public boolean findSCC(HashMap<Integer, Integer> graph){
        V = graph.size();
        this.graph = graph;
        low = new int[V];
        visited = new boolean[V];
        stack = new Stack<>();
        sccComp = new ArrayList<>();

        for(int v = 0; v < V; ++v){
            if(!visited[v]) dfs(v);
        }

        if(sccComp.size() > 0) return true;
        return false;
    }

    public void dfs(int v){
        low[v] = preCount++;
        visited[v] = true;
        stack.push(v);
        int min = low[v];

        int w = graph.get(v);
        if(!visited[w]) dfs(w);
        if(low[w] < min) min = low[w];

        if(min < low[v]){
            low[v] = min;
            return;
        }

        List<Integer> component = new ArrayList<>();
        int ww;
        do{
            ww = stack.pop();
            component.add(ww);
            low[ww] = V;
        } while(ww != v);
        sccComp.add(component);
    }
}

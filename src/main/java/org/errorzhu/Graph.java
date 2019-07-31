package org.errorzhu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {

    // node to which point to this node , reversed neibor matrix
    private Map<Node, List<Node>> matrix = new HashMap<Node, List<Node>>();


    public Map<Node, List<Node>> getMatrix() {
        return matrix;
    }

    public void addEdge(Node source, Node target) {

        if (!this.matrix.containsKey(source)) {
            this.matrix.put(source, new LinkedList<Node>());
        }
        if (this.matrix.containsKey(target)) {
            this.matrix.get(target).add(source);
        } else {
            List<Node> nodes = new LinkedList<Node>();
            nodes.add(source);
            this.matrix.put(target, nodes);
        }

    }


    public boolean isDependencyFinished(Node node) {
        for (Node n : this.matrix.get(node)) {
            if (!n.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public boolean allNodesFinished() {
        for (Node n : this.matrix.keySet()) {
            if (!n.isFinished()) {
                return false;
            }
        }
        return true;
    }

}

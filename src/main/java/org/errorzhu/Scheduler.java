package org.errorzhu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler {

    private Graph graph = new Graph();

    //record node in edge whether created
    private Map<String, Node> nodeMap = new HashMap<String, Node>();

    private ObjectMapper om = new ObjectMapper();

    public Graph getGraph() {
        return graph;
    }

    private Object deepCopy(Object input) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(input);
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        Object output = oi.readObject();
        bo.close();
        oo.close();
        bi.close();
        oi.close();
        return output;

    }

    @SuppressWarnings("unchecked")
    public List<Node> topologicalSort() throws IOException, ClassNotFoundException {
        Map<Node, List<Node>> tmpMatrix = (Map<Node, List<Node>>) deepCopy(graph.getMatrix());
        Queue<Node> queue = new LinkedList<>();
        List<Node> result = new LinkedList<>();
        for (Node node : tmpMatrix.keySet()) {
            if (tmpMatrix.get(node).isEmpty()) {
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            Node tmpNode = queue.poll();
            result.add(tmpNode);

            for (Node node : tmpMatrix.keySet()) {
                if (tmpMatrix.get(node).contains(tmpNode)) {
                    tmpMatrix.get(node).remove(tmpNode);
                    if (tmpMatrix.get(node).isEmpty()) {
                        queue.add(node);
                    }

                }
            }

        }
        return result;

    }


    public String saveGraph(List<Edge> edges) throws JsonProcessingException {

        return om.writeValueAsString(edges);
    }

    public List<Edge> loadGraph(String input) throws IOException {

        //model need no-parameter constructor
        JavaType type = om.getTypeFactory().constructParametrizedType(LinkedList.class, LinkedList.class, Edge.class);
        return om.readValue(input, type);

    }

    @SuppressWarnings("unchecked")
    public void createWorkFlowFromConfig(List<Edge> edges) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        for (Edge edge : edges) {
            Node sourceNode = null;
            String sourceId = edge.getSource().getId();
            if (nodeMap.containsKey(sourceId)) {
                sourceNode = nodeMap.get(sourceId);
            } else {
                Class sourceClazz = Class.forName(edge.getSource().getClassName());
                IWork sourceWork = (IWork) sourceClazz.getConstructor().newInstance();
                sourceNode = new Node(sourceWork, sourceId);
                nodeMap.put(sourceId, sourceNode);
            }


            Node targetNode = null;
            String targetId = edge.getTarget().getId();
            if (nodeMap.containsKey(targetId)) {
                targetNode = nodeMap.get(targetId);
            } else {
                Class targetClazz = Class.forName(edge.getTarget().getClassName());
                IWork targeteWork = (IWork) targetClazz.getConstructor().newInstance();
                targetNode = new Node(targeteWork, targetId);
                nodeMap.put(targetId, targetNode);
            }

            graph.addEdge(sourceNode, targetNode);
        }


    }

    public void runWorkFlow() {

        Queue<Node> queue = new ConcurrentLinkedQueue<Node>();
        for (Node node : graph.getMatrix().keySet()) {
            queue.add(node);
        }
        while (!graph.allNodesFinished()) {
            for (Node node : queue) {
                if (graph.isDependencyFinished(node)) {
                    node.call();
                    queue.remove(node);
                }
            }
        }


    }


}

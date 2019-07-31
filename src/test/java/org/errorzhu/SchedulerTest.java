package org.errorzhu;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class SchedulerTest {

    private Scheduler scheduler = new Scheduler();

    public void init() {
        Step1 s1 = new Step1();
        Step2 s2 = new Step2();


    }

    @Test
    public void testSaveLoadGraph() throws IOException {

        Point p1 = new Point("n1", "org.errorzhu.Step1", "xxx");
        Point p2 = new Point("n2", "org.errorzhu.Step2", "xxx");
        Point p3 = new Point("n3", "org.errorzhu.Step1", "xxx");
        Point p4 = new Point("n4", "org.errorzhu.Step1", "xxx");

        Edge e1 = new Edge(p1, p3);
        Edge e2 = new Edge(p2, p3);
        Edge e3 = new Edge(p3, p4);

        LinkedList<Edge> edges = new LinkedList<Edge>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);

        String expect = "[{\"source\":{\"id\":\"n1\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"},\"target\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}},{\"source\":{\"id\":\"n2\",\"className\":\"org.errorzhu.Step2\",\"config\":\"xxx\"},\"target\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}},{\"source\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"},\"target\":{\"id\":\"n4\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}}]";
        String graphConfigStr = scheduler.saveGraph(edges);
        Assert.assertEquals(graphConfigStr, expect);
        List<Edge> actual = scheduler.loadGraph(graphConfigStr);
        Assert.assertEquals(actual.get(0).getSource().getClassName(), edges.get(0).getSource().getClassName());
        Assert.assertEquals(actual.get(0).getSource().getId(), edges.get(0).getSource().getId());
        Assert.assertEquals(actual.get(0).getSource().getConfig(), edges.get(0).getSource().getConfig());

        Assert.assertEquals(actual.get(0).getTarget().getClassName(), edges.get(0).getTarget().getClassName());
        Assert.assertEquals(actual.get(0).getTarget().getId(), edges.get(0).getTarget().getId());
        Assert.assertEquals(actual.get(0).getTarget().getConfig(), edges.get(0).getTarget().getConfig());


        Assert.assertEquals(actual.get(1).getSource().getClassName(), edges.get(1).getSource().getClassName());
        Assert.assertEquals(actual.get(1).getSource().getId(), edges.get(1).getSource().getId());
        Assert.assertEquals(actual.get(1).getSource().getConfig(), edges.get(1).getSource().getConfig());

        Assert.assertEquals(actual.get(1).getTarget().getClassName(), edges.get(1).getTarget().getClassName());
        Assert.assertEquals(actual.get(1).getTarget().getId(), edges.get(1).getTarget().getId());
        Assert.assertEquals(actual.get(1).getTarget().getConfig(), edges.get(1).getTarget().getConfig());


        Assert.assertEquals(actual.get(2).getSource().getClassName(), edges.get(2).getSource().getClassName());
        Assert.assertEquals(actual.get(2).getSource().getId(), edges.get(2).getSource().getId());
        Assert.assertEquals(actual.get(2).getSource().getConfig(), edges.get(2).getSource().getConfig());

        Assert.assertEquals(actual.get(2).getTarget().getClassName(), edges.get(2).getTarget().getClassName());
        Assert.assertEquals(actual.get(2).getTarget().getId(), edges.get(2).getTarget().getId());
        Assert.assertEquals(actual.get(2).getTarget().getConfig(), edges.get(2).getTarget().getConfig());

    }


    @Test
    public  void  testTopologicalSort() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String graphConfigStr = "[{\"source\":{\"id\":\"n1\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"},\"target\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}},{\"source\":{\"id\":\"n2\",\"className\":\"org.errorzhu.Step2\",\"config\":\"xxx\"},\"target\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}},{\"source\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"},\"target\":{\"id\":\"n4\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}}]";

        List<Edge> config = scheduler.loadGraph(graphConfigStr);

        scheduler.createWorkFlowFromConfig(config);

        List<Node> nodes = scheduler.topologicalSort();
        Assert.assertEquals(nodes.get(3).getId(),"n4");

    }
    @Test
    public void useDemo(){

        // your own class need implements IWork
        Step1 s1 = new Step1();
        Step2 s2 = new Step2();

        Node n1 = new Node(s1,"n1");
        Node n2 = new Node(s2,"n2");
        Node n3 = new Node(s2,"n3");
        Node n4 = new Node(s1,"n4");
        Graph graph = scheduler.getGraph();
        graph.addEdge(n1,n3);
        graph.addEdge(n2,n3);
        graph.addEdge(n3,n4);

        scheduler.runWorkFlow();
        System.out.println("--------------------------------");


    }

    @Test
    public void useDemo2() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //support run from config by reflect,but need point to class name
        String graphConfigStr = "[{\"source\":{\"id\":\"n1\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"},\"target\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}},{\"source\":{\"id\":\"n2\",\"className\":\"org.errorzhu.Step2\",\"config\":\"xxx\"},\"target\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}},{\"source\":{\"id\":\"n3\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"},\"target\":{\"id\":\"n4\",\"className\":\"org.errorzhu.Step1\",\"config\":\"xxx\"}}]";

        List<Edge> config = scheduler.loadGraph(graphConfigStr);

        scheduler.createWorkFlowFromConfig(config);

        scheduler.runWorkFlow();
        System.out.println("--------------------------------");

    }
}

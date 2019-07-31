package org.errorzhu;

public class Edge {

    private  Point source;

    private  Point target;

    public Edge() {
    }

    public Edge(Point source, Point target) {
        this.source = source;
        this.target = target;
    }

    public Point getSource() {
        return source;
    }

    public void setSource(Point source) {
        this.source = source;
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
    }


    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}

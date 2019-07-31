package org.errorzhu;

public class Point {

    private String id;
    private String className;
    private String config;

    public Point() {
    }

    public Point(String id, String className, String config) {
        this.id = id;
        this.className = className;
        this.config = config;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", config='" + config + '\'' +
                '}';
    }
}

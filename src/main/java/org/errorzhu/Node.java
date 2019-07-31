package org.errorzhu;

public class Node implements INode {

    private IWork work;

    //need unique in one graph
    private String id;


    private boolean finished = false;


    public String getId() {
        return id;
    }

    public Node(IWork work, String id) {

        if (work instanceof IWork) {
            this.work = work;
            this.id = id;
        } else {
            throw new IllegalArgumentException("work need implements IWork");
        }


    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void call() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work.execute(getId());
                    finished = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        if (!finished) {
            thread.start();
        }

    }
}

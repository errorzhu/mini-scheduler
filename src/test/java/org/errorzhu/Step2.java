package org.errorzhu;

public class Step2 implements IWork {
    @Override
    public void execute(String input) throws InterruptedException {
        Thread.sleep(1 * 1000);
        System.out.println(input + " : step2 done");
    }
}

package org.errorzhu;

public class Step1 implements IWork {
    @Override
    public void execute(String input) throws InterruptedException {
        Thread.sleep(1);
        System.out.println(input + ": step1 done");

    }
}

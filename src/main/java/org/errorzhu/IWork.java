package org.errorzhu;

import java.io.Serializable;

public interface IWork extends Serializable {
    void execute(String input) throws InterruptedException;
}

package org.errorzhu;

import java.io.Serializable;

public interface INode extends Serializable {

    boolean isFinished();

    void call();
}

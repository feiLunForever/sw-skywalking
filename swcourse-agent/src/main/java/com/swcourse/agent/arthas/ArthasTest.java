package com.swcourse.agent.arthas;

import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-05-12 22:42
 **/
public class ArthasTest {

    @Test
    public void addTransform() throws Exception {
        VirtualMachine vmObj = null;
        try {
            vmObj = VirtualMachine.attach("62770");
            if (vmObj != null) {
                vmObj.loadAgent("/Users/zyq/project/study-project/swcourse/swcourse-agent" +
                        "/target/swcourse-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar=retransformClasses," +
                        "/Users/zyq/project/study-project/swcourse/swcourse-agent/src/main/doc/RandomUtil.class", null);
            }
        } finally {
            if (null != vmObj) {
                vmObj.detach();
            }
        }
    }

    @Test
    public void removeTransform() throws Exception {
        VirtualMachine vmObj = null;
        try {
            vmObj = VirtualMachine.attach("3780");
            if (vmObj != null) {
                vmObj.loadAgent("/Users/zyq/project/study-project/swcourse/swcourse-agent" +
                        "/target/swcourse-agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar=removeTransformer," +
                        "/Users/zyq/project/study-project/swcourse/swcourse-agent/src/main/doc/RandomUtil.class", null);
            }
        } finally {
            if (null != vmObj) {
                vmObj.detach();
            }
        }
    }
}

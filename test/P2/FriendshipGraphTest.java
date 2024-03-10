package P2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.AccessControlException;
import java.security.Permission;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-03-06
 * Time: 20:26
 */
public class FriendshipGraphTest {
    //public final SystemOutRule systemOutRule=new SystemOutRule().enableLog();
    @Before
    public void setUp() {
        System.out.println("init");
        final SecurityManager securityManager = new SecurityManager() {
            public void checkPermission(Permission permission) {
                /**
                 * 这里permission.getName()会返回exitVM. + status的形式
                 * 例如System.exit(0) 这里会是exitVM.0
                 * System.exit(1) 这里会是exitVM.1
                 * 这里不关系怎么退出的，只需要捕捉到，并不让他退出，继续运行我的测试代码就好了
                 */
                if (permission.getName().startsWith("exitVM")) {
                    throw new AccessControlException("");
                }
            }
        };
        System.setSecurityManager(securityManager);
    }
    @Test
    public void addVertex() {
        FriendshipGraph g=new FriendshipGraph();
        Person a=new Person("a");
        g.addVertex(a);
        assertEquals(true,g.vertices().contains(a));
        assertEquals(1,g.vertices().size());//检查图中元素个数是否为1
        Person b=new Person("b");
        Person c=new Person("b");
        g.addVertex(b);
        System.out.println("start check repetition");
        try {
            // 模拟待测试的功能方法中的退出
            g.addVertex(c);
        } catch (RuntimeException e) {
            // do nothing
        } finally {
            // 屏蔽好之后，重新设置setSecurityManager为Null,防止junit退出的时候报错
            System.setSecurityManager(null);
        }
        //查看退出了，是否会执行下面的代码
        Assert.assertEquals(true, true);
        System.out.println("end");
        assertEquals(false,g.vertices().contains(c));
        assertEquals(2,g.vertices().size());//检查图中元素个数是否为2
        assertEquals(true,g.vertices().contains(b));
    }

    @Test
    public void addEdge() {
        FriendshipGraph g=new FriendshipGraph();
        Person a=new Person("a");
        Person b=new Person("b");
        Person c=new Person("c");
        g.addVertex(a);
        g.addVertex(b);
        g.addEdge(a, b);
        g.addEdge(b,a);
        System.out.println("start check exist");
        try {
            // 模拟待测试的功能方法中的退出
            g.addEdge(c,a);
        } catch (RuntimeException e) {
            // do nothing
        } finally {
            // 屏蔽好之后，重新设置setSecurityManager为Null,防止junit退出的时候报错
            System.setSecurityManager(null);
        }
        //查看退出了，是否会执行下面的代码
        Assert.assertEquals(true, true);
        System.out.println("end");
        assertEquals(true,g.targets(a).containsKey(b));
        assertEquals(true,g.sources(b).containsKey(a));
    }

    @Test
    public void getDistance() {
        FriendshipGraph graph=new FriendshipGraph();
        Person a=new Person("a");
        Person b=new Person("b");
        Person c=new Person("c");
        Person d=new Person("d");
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addEdge(a, b);
        graph.addEdge(b, a);
        graph.addEdge(b, c);
        graph.addEdge(c, b);
        assertEquals(1,graph.getDistance(a, b));
        assertEquals(2,graph.getDistance(a, c));
        assertEquals(0,graph.getDistance(a, a));
        assertEquals(-1,graph.getDistance(a, d));
    }


}

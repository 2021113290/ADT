package P2;

import P1.graph.ConcreteEdgesGraph;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-03-06
 * Time: 20:25
 */
public class FriendshipGraph extends ConcreteEdgesGraph<Person>{
    // Abstraction function:
    //   AF(vertexList)=a person list
    // Representation invariant:
    //   verList can't include two same names
    // Safety from rep exposure:
    //    All fields are private;
    private void checkRep(){
        //已在addVertex里写好了逻辑，和与之关联exit的测试
    }
    private final ArrayList<String> vertexList=new ArrayList<String>();
    public void addVertex(Person person) {
        if (vertexList.contains(person.getName())) {
            System.out.println(person.getName() + "添加的person姓名重复");
            System.exit(0);
        } else {
            this.add(person);
            vertexList.add(person.getName());
        }
    }
    public void addEdge(Person person1, Person person2){
        if(this.vertices().contains(person1)){
            this.set(person1,person2,1);
        }else {
            System.out.println(person1.getName()+"友谊图中没有该人");//给一个不在图中的人添加边会报错
            System.exit(0);
        }
    }
    public int getDistance(Person person1,Person person2){
        //广度优先算法
        if(!this.vertices().contains(person1)||!this.vertices().contains(person2))return -1;
        if(person1.getName().equals(person2.getName()))return 0;

        Person start=person1;//初始时起始点和终止点都置为同一个点
        int i,dis=0;
        Queue<Person> friend=new LinkedList<Person>();//记录朋友队列
        ArrayList<Person> visited=new ArrayList<Person>();//记录已经访问过的人
        if(person1==person2)return 0;//这里不是指名字相同，而是指引用相同，即求自身到自身的距离，默认为0
        friend.add(person1);//队列添加第一个人
        visited.add(person1);//标记第一个人为已访问

        while (!friend.isEmpty()) {//当队列为空时，即为循环终止条件
            start = friend.poll();//弹出队列中的队首元素
            dis++;//距离加1
            for (Person end : this.targets(start).keySet()
            ) {
                if (end == person2) {
                    return dis;
                }
                if (!visited.contains(end)) {
                    friend.add(end);//将第i个朋友加入到朋友队列和已访问列表中
                    visited.add(end);
                }
            }
        }
        return -1;
    }
    public static void main(String[] args){
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        System.out.println(graph.getDistance(rachel, ben));
        System.out.println(graph.getDistance(rachel, rachel));
        System.out.println(graph.getDistance(rachel, kramer));
    }
}


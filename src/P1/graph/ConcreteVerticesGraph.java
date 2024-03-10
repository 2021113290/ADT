/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices)=a direct graph
    // Representation invariant:
    //   weight>=0
    //   vertexName!=null
    // Safety from rep exposure:
    //    All fields are private;
    //  Vertex is guaranteed mutable,
    //  so make defensive copies to avoid sharing the rep's object with operations;
    // TODO constructor

    public ConcreteVerticesGraph() {
    }

    // TODO checkRep
    private void checkRep(){
        assert vertices!=null;
        for (Vertex<L> vertex:vertices
             ) {
            assert vertex.getVertexName()!=null;
            //先获取vertexSource集合的所有键的set集合
            Set<L> sourceKey = vertex.getVertexSource().keySet();
            //有了set集合，就可以获取迭代器
            Iterator<L> iterator=sourceKey.iterator();
            while (iterator.hasNext()){
                L key=iterator.next();
                //有了键可以通过map集合的get方法获取其对应的值
                int value=vertex.getVertexSource().get(key);
                assert value>0;
            }
            //先获取vertexTarget集合的所有键的set集合
            Set<L> TargetKey = vertex.getVertexTarget().keySet();
            //有了set集合，就可以获取迭代器
            Iterator<L> it=TargetKey.iterator();
            while (it.hasNext()){
                L key=it.next();
                //有了键可以通过map集合的get方法获取其对应的值
                int value=vertex.getVertexTarget().get(key);
                assert value>0;
            }
        }
    }
    //vertex是加入顶点的名字
    @Override public boolean add(L vertex) {
        checkRep();
        for (Vertex<L> ver : vertices
        ) {//检查顶点集合里面是否有顶点的名字和传入的顶点的名字相同
            if (ver.getVertexName().equals(vertex)) {
                System.out.println("该顶点已存在！");
                return false;
            }
        }
        vertices.add(new Vertex<>(vertex));//加入新构造的点
        return true;
    }

    @Override public int set(L source, L target, int weight) {
        checkRep();
        Vertex<L> sourceSame=null;
        Vertex<L> targetSame=null;
        for (Vertex<L> v:vertices
             ) {
            if(v.getVertexName().equals(source)){
               //说明有和source名字相同的点
                sourceSame=v;
            }
            if(v.getVertexName().equals(target)){
                //说明有和target名字相同的点
                targetSame=v;
            }
        }
        //没有名字相同的就可以把顶点名字加入到顶点集合中
        if(sourceSame==null){
            vertices.add(new Vertex<>(source));
        }
        if(targetSame==null){
            vertices.add(new Vertex<>(target));
        }
        int weight1=0;
        for (Vertex<L> v1:vertices
        ) {
            //找到了和参数源点相同的顶点
            if (v1.getVertexName().equals(source)) {
//                v1.addTarget(target,weight);
                if(v1.getVertexTarget().containsKey(target)){
                    weight1=v1.getTargetWeight(target);
                    v1.removeTarget(target);
                }
                v1.addTarget(target,weight);
                break;
            }
        }
        for (Vertex<L> v2:vertices
        ) {
            //找到了和参数终点相同的顶点
            if (v2.getVertexName().equals(target)) {
//                v1.addTarget(target,weight);
                if(v2.getVertexSource().containsKey(source)){
                    weight1=v2.getSourceWeight(source);
                    v2.removeSource(source);
                }
                v2.addSource(source,weight);
                break;
            }
        }
        return weight;
    }
    
    @Override public boolean remove(L vertex) {
        checkRep();
       int flag=0;
       Iterator<Vertex<L>> vr=vertices.iterator();
       while (vr.hasNext()){
           Vertex<L> re=vr.next();
           if(re.getVertexName().equals(vertex)){
               vr.remove();
               flag=1;
           }
           if(re.getVertexSource().containsKey(vertex)||re.getVertexTarget().containsKey(vertex)){
               vr.remove();
               flag=1;
           }
       }
       return flag!=0;
    }
    //返回的是顶点集合例如：{“a”,"b","c"}
    @Override public Set<L> vertices() {
        checkRep();
        Set<L> set=new HashSet<>();
        set.clear();
        for (Vertex<L> v:vertices
             ) {
            set.add(v.getVertexName());
        }
        return  set;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        checkRep();
        Map<L, Integer> map = new HashMap<L, Integer>();
        map.clear();
        for (Vertex<L> v:vertices
             ) {
            if(v.getVertexName().equals(target)){
                map= new HashMap<L, Integer>(v.getVertexSource());
            }
        }
        return map;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        checkRep();
        Map<L, Integer> map = new HashMap<L, Integer>();
        map.clear();
        for (Vertex<L> v:vertices
        ) {
            if(v.getVertexName().equals(source)){
                map= new HashMap<L, Integer>(v.getVertexTarget()); ;
            }
        }
        return map;
    }
    
    // TODO toString()


    @Override
    public String toString() {
        checkRep();
        StringBuilder sb=new StringBuilder();
        sb.append("ConcreteVerticesGraph{vertices=").append(vertices).append('}');
//        return "ConcreteVerticesGraph{" +
//                "vertices=" + vertices +
//                '}';
        return sb.toString();
    }
}

/**
 * Represents a vertex and its all edge with weight
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex <L>{
    
    // TODO fields
    private final L vertexName;//顶点的名字
    private final Map<L,Integer> vertexSource;//顶点的所有源点和相连的边的权重
    private final Map<L,Integer> vertexTarget;//顶点的所有终点和相连的边的权重


    // Abstraction function:
    //   AF(vertexName,vertexSource,vertexTarget)=a vertex and its all edge with weight
    // Representation invariant:
    //   weight>=0
    //   vertexName!=null
    // Safety from rep exposure:、
    //   All fields are private;
    //  Vertex is guaranteed mutable,
    //  so make defensive copies to avoid sharing the rep's object with operations;
    
    // TODO constructor

    public Vertex(L vertexName) {
        this.vertexName = vertexName;
        vertexSource = new HashMap<>();
        vertexTarget = new HashMap<>();
        checkRep();
    }

    // TODO checkRep
    private void checkRep(){
        assert vertexName!=null;
        //先获取vertexSource集合的所有键的set集合
        Set<L> sourceKey = vertexSource.keySet();
        //有了set集合，就可以获取迭代器
        Iterator<L> iterator=sourceKey.iterator();
        while (iterator.hasNext()){
            L key=iterator.next();
            //有了键可以通过map集合的get方法获取其对应的值
            int value=vertexSource.get(key);
            assert value>0;
        }
        //先获取vertexTarget集合的所有键的set集合
        Set<L> TargetKey = vertexTarget.keySet();
        //有了set集合，就可以获取迭代器
        Iterator<L> it=TargetKey.iterator();
        while (it.hasNext()){
            L key=it.next();
            //有了键可以通过map集合的get方法获取其对应的值
            int value=vertexTarget.get(key);
            assert value>0;
        }
    }
    // TODO methods

    /**
     *
     * @return 顶点的名字
     */
    public L getVertexName() {
        checkRep();
        return vertexName;
    }

    /**
     *
     * @return 引用该方法的顶点的所有源点
     */
    public Map<L, Integer> getVertexSource() {
        checkRep();
        return new HashMap<>(vertexSource);
    }

    /**
     *
     * @return 引用该方法的顶点的所有终点
     */
    public Map<L, Integer> getVertexTarget() {
        checkRep();
        return new HashMap<>(vertexTarget);
    }

    /**
     * 为引用该方法的顶点增加一个终点及其边
     * @param targetName 增加的终点的名字
     * @param weight 要增加的边的权值
     * @return 增加的边的权值
     */
    public int addTarget(L targetName , int weight){
        checkRep();
        if(weight==0){
            return 0;
        }else {
            this.vertexTarget.put(targetName,weight);
            return weight;
        }
    }

    /**
     * 为引用该方法的顶点增加一个源点及其边
     * @param sourceName 增加的源点的名字
     * @param weight 增加的边的权重
     * @return 边的权重
     */
    public int addSource(L sourceName , int weight){
        checkRep();
        if(weight==0){
            return 0;
        }else {
            this.vertexSource.put(sourceName,weight);
            return weight;
        }
    }

    /**
     * 将引用该方法的终点集合中的一个终点删去
     * @param target 要删除的终点
     * @return 边的权重
     */
    public int removeTarget(L target){
        checkRep();
        int weight=vertexTarget.remove(target);
        return weight;
    }
    /**
     * 将引用该方法的源点集合中的一个源点删去
     * @param source 要删除的源点
     * @return 边的权重
     */
    public int removeSource(L source){
        checkRep();
        //verSource 是集合，返回的是remove的key相应的value
        int weight=vertexSource.remove(source);
        return weight;
    }
    /**
     * 获取引用该方法的顶点与传入的其源点的权重，如果传入的点并不是对象的源点，返回0，否则，返回权值
     * @param source 对象的源点
     * @return 边的权重
     */
    public int getSourceWeight(L source){
        if(this.vertexSource.containsKey(source)){
            return this.vertexSource.get(source);
        }else {
            return 0;
        }
    }

    /**
     * 获取引用该方法的顶点与传入的其终点的权重，如果传入的点并不是对象的终点，返回0，否则，返回权值
     * @param target 对象的终点
     * @return 边的权重
     */
    public int getTargetWeight(L target){
        if(this.vertexTarget.containsKey(target)){
            return this.vertexTarget.get(target);
        }else {
            return 0;
        }
    }
    // TODO toString()


    @Override
    public String toString() {
        checkRep();
        StringBuilder sb=new StringBuilder();
        sb.append("Vertex{vertexName=").append(vertexName).append(", vertexSource=").append(vertexSource).append(", vertexTarget=").append(vertexTarget).append('}');
        return sb.toString();
    }
}

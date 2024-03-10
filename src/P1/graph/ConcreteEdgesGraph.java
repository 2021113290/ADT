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
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices,edges)=a directed graph
    // Representation invariant:
    //   source and target is not null
    //   weight>=0
    //    a vertex cannot be either source or target
    // Safety from rep exposure:
    //    All fields are private;
    //   Edge is guaranteed immutable,
    //    but Set and List is mutable ,
    //    so make defensive copies to avoid sharing the rep's object with operations;

    // TODO constructor

    public ConcreteEdgesGraph() {
    }

    // TODO checkRep
    private void checkRep() {
        for (Edge<L> edge : edges
        ) {
            assert edge.getSource()!=null;
            assert edge.getTarget()!=null;
            assert edge.getWeight() > 0||edge.getWeight()==0;
        }
    }

    @Override
    public boolean add(L vertex) {
        checkRep();
        if (vertices.contains(vertex)) {
            return false;
        } else {
            vertices.add(vertex);
            return true;
        }
    }

    @Override
    public int set(L source, L target, int weight) {
        checkRep();
        vertices.add(source);
        vertices.add(target);
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge<L> edge = iterator.next();
            //如果图中有一条边的源点和终点和参数源点和终点相同
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                //删除边
                iterator.remove();
            }
        }
        //检查边的权重：>0,替换；=0，删除
        if (weight > 0) {
            edges.add(new Edge<L>(source, target, weight));
        }
        //否则，边的权重为0，已经在前面删完了
        return weight;
    }

    @Override
    public boolean remove(L vertex) {
        checkRep();
        if (vertices.contains(vertex)) {
            vertices.remove(vertex);
            Iterator<Edge<L>> iterator = edges.iterator();
            while (iterator.hasNext()) {
                Edge<L> edge = iterator.next();
                if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
                    //删除边
//                    edges.remove(edge);
                    iterator.remove();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<L> vertices() {
//        checkRep();
        return new HashSet<>(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {
        checkRep();
        Map<L, Integer> map = new HashMap<L, Integer>();
        map.clear();

        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge<L> edge = iterator.next();
            //如果边集合里面的边的终点是传过来的target，就加入该边
            if (edge.getTarget().equals(target)) {
                map.put( edge.getSource(), edge.getWeight());
            }
        }
        return map;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        checkRep();
        Map<L, Integer> map = new HashMap<L, Integer>();
        map.clear();

        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge<L> edge = iterator.next();
            //如果边集合里面的边的终点是传过来的target，就加入该边
            if (edge.getSource().equals(source)) {
                map.put(edge.getTarget(), edge.getWeight());
            }
        }
        return map;
    }

    // TODO toString()


    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("ConcreteEdgesGraph{");
        sb.append("vertices=");
        sb.append(vertices);
        sb.append(", edges=");
        sb.append(edges);
        sb.append('}');
        return sb.toString();
    }
}

/**
 * Represents an edge in a graph
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 *
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {


    private final L source;
    private final L target;
    private final int weight;

    // Abstraction function:
    //  AF(source,target,weight) = a directed edge
    // Representation invariant:
    //   source is not null,target is not null, weight>=0
    // Safety from rep exposure:
    //   All fields are private;
    //   source and target are Strings,weight is int,so are guaranteed immutable;
    // TODO constructor

    /**
     * @param source 是有向边的源点
     * @param target 是有向边的终点
     * @param weight 是有向边的权重
     */
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // TODO checkRep

    /**
     * Check that the rep invariant is true
     */
    private void checkRep() {
        assert weight > 0||weight==0;
        assert source != null && target != null;
    }
    // TODO methods

    /**
     * get a source of an edge
     *
     * @return a source
     */
    public L getSource() {
        checkRep();
        return source;
    }

    /**
     * get a target of an edge
     *
     * @return a target
     */
    public L getTarget() {
        checkRep();
        return target;
    }

    /**
     * get weight of an edge
     *
     * @return weight
     */
    public int getWeight() {
        checkRep();
        return weight;
    }

    /**
     * @param obj 和obj这个对象进行比较
     * @return 相同对象返回真，否则返回假
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) obj;
        return this.target.equals(edge.target) && this.source.equals(edge.source) && this.weight == edge.weight;
    }

    /**
     * @return the address of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, target, weight);
    }

    // TODO toString()

    /**
     * @return a string representation of this edge
     */

    @Override
    public String toString() {
        checkRep();
        return "Edge{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                '}';

    }

}

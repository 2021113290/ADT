/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.Map;
import java.util.Set;

/**
 * A mutable weighted directed graph with labeled vertices.具有标记顶点的可变加权有向图。
 * Vertices have distinct labels of an immutable type {@code L} when compared进行比较时，顶点具有不可变类型的不同标签｛@code L｝
 * using the {@link Object#equals(Object) equals} method.使用equals方法？
 * Edges are directed and have a positive weight of type {@code int}.边是有向的，并且具有类型为{@code-int}的正权重。
 * 
 * <p>PS2 instructions: this is a required ADT interface.
 * You MUST NOT change the specifications or add additional methods.
 * 
 * @param <L> type of vertex labels in this graph, must be immutable不可变
 */
public interface Graph<L> {
    
    /**
     * Create an empty graph.
     * 
     * @param <L> type of vertex labels in the graph, must be immutable
     * @return a new empty weighted directed graph
     */
    public static <L> Graph<L> empty() {
//        throw new RuntimeException("not implemented");
        return new ConcreteEdgesGraph<>();
    }
    
    /**
     * Add a vertex to this graph. 向图中增加一个顶点
     * 
     * @param vertex label for the new vertex 新顶点的顶点类型
     * @return true if this graph did not already include a vertex with the返回真如果这个图不包含提供的顶点（？这里包不包含是不是就会用到equals方法了呢）
     *         given label; otherwise false (and this graph is not modified)否则返回假（？？？？）
     */
    public boolean add(L vertex);
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.添加/更改/删除图中的一条带权有向边
     * If weight is nonzero, add an edge or update the weight of that edge;如果权重非0，增加这条边或者更新这条边的权重
     * vertices with the given labels are added to the graph if they do not带有被给定的标签的顶点要添加到图中如果他们不存在于图中
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not如果权重是0，删掉存在于图中的该边，否则图不发生变化
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge返回边的权重，如果没有这样的边就返回0
     */
    public int set(L source, L target, int weight);
    
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are删掉图中的该顶点，任何和该顶点相连的边都要被删除
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)如果包含这样的顶点就返回真，否则返回假
     */
    public boolean remove(L vertex);
    
    /**
     * Get all the vertices in this graph.得到图中的所有顶点
     * 
     * @return the set of labels of vertices in this graph返回图中的顶点集合
     */
    public Set<L> vertices();
    
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.获取具有指向目标顶点的边的源顶点和这些边的权重。
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target一个映射，其中关键点集是顶点的标签集，使得该图包括从该顶点到目标的边，并且每个关键点的值是从关键点到目标的边的（非零）权重
     */
    public Map<L, Integer> sources(L target);
    
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.获取目标顶点和权重
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key一个映射，其中关键点集是顶点的标签集，使得该图包括从该顶点到目标的边，并且每个关键点的值是从源点到关键点的边的（非零）权重
     */
    public Map<L, Integer> targets(L source);
    
}

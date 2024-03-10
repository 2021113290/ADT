/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a GraphPoet是用一个文本语料库初始化的，它用这个语料库来派生一个单词亲和图。
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * *图中的顶点是单词。单词被定义为非空格非换行字符的不区分大小写的非空字符串。它们在语料库中由空格、换行符或文件末尾分隔。
 * *图中的边计数邻接：语料库中“w1”后面跟着“w2”的次数是从w1到w2的边的权重。
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive 其中顶点表示不区分大小写 {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 *
 给定一个输入字符串，GraphPoet通过尝试在输入中的每对相邻单词之间插入一个桥接单词。
 *输入单词“w1”和“w2”之间的桥接词将是一些“b”，这样w1->b->w2是一条两边长路径，在所有路径中具有最大权重亲和度图中从w1到w2的两条边长路径。
 *如果没有这样的路径，就不会插入桥接字。在输出诗中，输入词保留原来的大小写，而bridge单词是小写的。诗中每个单词之间的空格是单个空间。
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 * 说明：这是一个必需的ADT类，你不能削弱要求的规格。但是，您可以加强规范,您可以添加其他方法。
 * 您必须在您的表示中使用Graph，但除此之外,类的实现由你决定。
 *大致理解：生成的诗歌图：每一个顶点是语料文件中的单词，有边代表两个单词紧挨着，边的权重是前一个单词紧挨着后一个单词的次数
 *        检查输入的字符串语句，输入的字符串语句的相邻的两个顶点中加单词，加的一个单词是在生成的诗歌图中位于检查的两个单词中间的，且加完后的两条边的权值最大*/
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   AF(graph)=a graph whose Vertices in the graph are words from a corpus of text,Edges in the graph count adjacencies
    // Representation invariant:
    //   vertices are non-empty case-insensitive strings of non-space non-newline characters.
    // Safety from rep exposure:
    //  field is private;
    //  String is guaranteed immutable;
    /**
     * Create a new poet with the graph from corpus文集 (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {

        checkRep();
        FileReader fileReader=new FileReader(corpus);
        BufferedReader bufferedReader=new BufferedReader(fileReader);
        String s;
        List<String> list=new ArrayList<>();
        Map<String,Integer> map=new HashMap<>();
        //读字符串文件,把文本用空格分割之后放到数组里面,数组的每一个元素都是顶点，是单词
        while ((s=bufferedReader.readLine())!=null){
            for (String s1:s.split(" ")
                 ) {
                    list.add(s1);
            }
        }
        bufferedReader.close();

            for (int i = 0; i < list.size()-1; i++) {
                //这样取保证v1,v2两个点的顺序永远是v1在v2前面
                String v1=list.get(i).toLowerCase();
                String v2=list.get(i+1).toLowerCase();
                int weight=0;
                //map中存在v1,v2这一顺序（紧挨着）的组合
                if(map.containsKey(v1+v2)){
                    weight=map.get(v1+v2);
                }
                map.put(v1+v2,weight+1);//v1,v2是相邻顶点，放入map中，并将这两个相邻节点的权重+1，放入map的原因是为了下一次迭代更新weight
                graph.set(v1,v2,weight+1);//v1,v2是相邻顶点，放入诗歌图中，并将这两个相邻节点的权重+1
            }
            //循环走完，诗歌图就写好了，写出来的诗歌图十元句子中每个相邻单词都放在了图中，且每一相邻的单词权重为1
        }

    
    // TODO checkRep
    public void checkRep(){
        for (String v:graph.vertices()
             ) {
            assert v!=null;
            assert v.equals(v.toLowerCase());
            assert !v.contains(" ")&&!v.contains("\n");
        }
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        checkRep();
        if(input.length()==0)return "";
        List<String> myPoem=new ArrayList<>();
        Map<String,Integer> sourceMap=null;
        Map<String,Integer> targetMap=null;
        for (String s:input.split(" ")
             ) {
            if(s.length()!=0){
                myPoem.add(s.replaceAll("\n",""));//把输入的文本中的所有换行符删去，把输入的句子切分为一个一个的单词放入myPoem列表中
            }
        }
        StringBuilder sb=new StringBuilder();

        for (int i = 0; i < myPoem.size()-1 ; i++) {
            sb.append(myPoem.get(i)).append(" ");
            //列表中的相邻两个单词
            String v1=myPoem.get(i).toLowerCase();
            String v2=myPoem.get(i+1).toLowerCase();
            sourceMap=graph.sources(v2);//后一个单词在诗歌图中的所有源点集合
            targetMap=graph.targets(v1);//前一个单词在诗歌图中的所有终点集合
            int maxWeight=0;
            String bridgeWord="";//找到桥接词
            for (String j:targetMap.keySet()) {
                if(sourceMap.containsKey(j)&&sourceMap.get(j)+targetMap.get(j)>maxWeight){
                    maxWeight=sourceMap.get(j)+targetMap.get(j);
                    bridgeWord=j;
                }
            }
            if (maxWeight>0)
                sb.append(bridgeWord+" ");
        }
        sb.append(myPoem.get(myPoem.size()-1));
        return sb.toString();
    }
    
    // TODO toString()

    @Override
    public String toString() {
        return "GraphPoet{" +
                "graph=" + graph +
                '}';
    }
}

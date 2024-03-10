/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;
import javafx.util.Pair;

import java.util.*;

/**
 * An implementation of graph.Graph.
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
    //  graph.Vertex is guaranteed mutable,
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
                double value=vertex.getVertexSource().get(key);
                assert value>0;
            }
            //先获取vertexTarget集合的所有键的set集合
            Set<L> TargetKey = vertex.getVertexTarget().keySet();
            //有了set集合，就可以获取迭代器
            Iterator<L> it=TargetKey.iterator();
            while (it.hasNext()){
                L key=it.next();
                //有了键可以通过map集合的get方法获取其对应的值
                double value=vertex.getVertexTarget().get(key);
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

    @Override

    public double set(L source, L target, double weight) {
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
        double weight1=0;
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
    
    @Override public Map<L, Double> sources(L target) {
        checkRep();
        Map<L, Double> map = new HashMap<L, Double>();
        map.clear();
        for (Vertex<L> v:vertices
             ) {
            if(v.getVertexName().equals(target)){
                map= new HashMap<L, Double>(v.getVertexSource());
            }
        }
        return map;
    }
    
    @Override public Map<L, Double> targets(L source) {
        checkRep();
        Map<L, Double> map = new HashMap<L, Double>();
        map.clear();
        for (Vertex<L> v:vertices
        ) {
            if(v.getVertexName().equals(source)){
                map= new HashMap<L, Double>(v.getVertexTarget()); ;
            }
        }
        return map;
    }
    public Map<L, Pair<Integer, Set<L>>> BFS(L start){//该函数最核心的就是计算中心物体和轨道物体的距离，不过返回值较lab2更加复杂
        Map<L, Pair<Integer, Set<L>>> reM=new HashMap<>();
        Map<L, Integer> vis=new HashMap<>();
        for(Vertex eachVer:vertices){
            vis.put((L)eachVer.getVertexName(), (Integer)0);
        }
        Queue<Pair<L, Integer>> Q=new LinkedList<>();
        Q.add(new Pair<>(start, 0));
        vis.put(start, 1);//
        while(!Q.isEmpty()){
            Pair<L, Integer> head=Q.poll();//出队列第一个元素
            Set<L> s=this.targets(head.getKey()).keySet();//第一个元素所有终点的集合
            //将第一个元素，第一个元素对应的深度？，第一个元素所有终点的集合放入要返回的集合中
            reM.put(head.getKey(), new Pair<>(head.getValue(),s));
            //          start                       0       start.targets
            for(L eachL:s) {//对于第一个元素的所有终点的集合中的点来说
                if(vis.get(eachL)==0){
                    Q.add(new Pair<>(eachL, head.getValue()+1));
                    //第一个元素的所有终点放入队列中，value变成1
                    vis.put(eachL,1);//map自然覆盖
                }
            }
        }
        return reM;
    }

    // TODO toString()


    @Override
    public String toString() {
        checkRep();
        StringBuilder sb=new StringBuilder();
        sb.append("graph.ConcreteVerticesGraph{vertices=").append(vertices).append('}');
//        return "graph.ConcreteVerticesGraph{" +
//                "vertices=" + vertices +
//                '}';
        return sb.toString();
    }
}

/**
 * Represents a vertex and its all edge with weight
 * Mutable.
 * This class is internal to the rep of graph.ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex <L>{
    
    // TODO fields
    private final L vertexName;//顶点的名字
    private final Map<L,Double> vertexSource;//顶点的所有源点和相连的边的权重
    private final Map<L,Double> vertexTarget;//顶点的所有终点和相连的边的权重


    // Abstraction function:
    //   AF(vertexName,vertexSource,vertexTarget)=a vertex and its all edge with weight
    // Representation invariant:
    //   weight>=0
    //   vertexName!=null
    // Safety from rep exposure:、
    //   All fields are private;
    //  graph.Vertex is guaranteed mutable,
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
       // assert vertexName!=null;
        //先获取vertexSource集合的所有键的set集合
        Set<L> sourceKey = vertexSource.keySet();
        //有了set集合，就可以获取迭代器
        Iterator<L> iterator=sourceKey.iterator();
        while (iterator.hasNext()){
            L key=iterator.next();
            //有了键可以通过map集合的get方法获取其对应的值
            double value=vertexSource.get(key);
            assert value>0;
        }
        //先获取vertexTarget集合的所有键的set集合
        Set<L> TargetKey = vertexTarget.keySet();
        //有了set集合，就可以获取迭代器
        Iterator<L> it=TargetKey.iterator();
        while (it.hasNext()){
            L key=it.next();
            //有了键可以通过map集合的get方法获取其对应的值
            double value=vertexTarget.get(key);
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
    public Map<L, Double> getVertexSource() {
        checkRep();
        return new HashMap<>(vertexSource);
    }

    /**
     *
     * @return 引用该方法的顶点的所有终点
     */
    public Map<L, Double> getVertexTarget() {
        checkRep();
        return new HashMap<>(vertexTarget);
    }

    /**
     * 为引用该方法的顶点增加一个终点及其边
     *
     * @param targetName 增加的终点的名字
     * @param weight     要增加的边的权值
     * @return 增加的边的权值
     */
    public double addTarget(L targetName , double weight){
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
    public double addSource(L sourceName , double weight){
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
    public double removeTarget(L target){
        checkRep();
        double weight=vertexTarget.remove(target);
        return weight;
    }
    /**
     * 将引用该方法的源点集合中的一个源点删去
     * @param source 要删除的源点
     * @return 边的权重
     */
    public double removeSource(L source){
        checkRep();
        //verSource 是集合，返回的是remove的key相应的value
        double weight=vertexSource.remove(source);
        return weight;
    }
    /**
     * 获取引用该方法的顶点与传入的其源点的权重，如果传入的点并不是对象的源点，返回0，否则，返回权值
     * @param source 对象的源点
     * @return 边的权重
     */
    public double getSourceWeight(L source){
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
    public double getTargetWeight(L target){
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
        sb.append("graph.Vertex{vertexName=").append(vertexName).append(", vertexSource=").append(vertexSource).append(", vertexTarget=").append(vertexTarget).append('}');
        return sb.toString();
    }
}

# graph-connectedness-scala
Map-Reduce program that finds the connected components of any undirected graph and prints the size of these connected components using Scala and Spark.

Project done as a part of CSE-6331 Cloud Computing Course at UTA.

<a href="https://lambda.uta.edu/cse6331/spring20/project5.html">Project Description</a>

<p>An undirected graph is represented in the input text file using one line per graph vertex. For example, the line</p>

<p>1,2,3,4,5,6,7</p>
</p>represents the vertex with ID 1, which is connected to the vertices with IDs 2, 3, 4, 5, 6, and 7. For example, the following graph:</p>

<img src="https://raw.githubusercontent.com/c-deshpande/graph-connectedness/master/img/p2.png"/>

<p>is represented in the input file as follows:</p>
3,2,1
<br>
2,4,3
<br>
1,3,4,6
<br>
5,6
<br>
6,5,7,1
<br>
0,8,9
<br>
4,2,1
<br>
8,0
<br>
9,0
<br>
7,6
<br>
<br>
<p align=justify>
Your task is to write a Map-Reduce program that finds the connected components of any undirected graph and prints the size of these connected components. A connected component of a graph is a subgraph of the graph in which there is a path from any two vertices in the subgraph. For the above graph, there are two connected components: one 0,8,9 and another 1,2,3,4,5,6,7. Your program should print the sizes of these connected components: 3 and 7.
</p>
<br>
<p align=justify>
The following pseudo-code finds the connected components. It assigns a unique group number to each vertex (we are using the vertex ID as the group number), and for each graph edge between Vi and Vj, it changes the group number of these vertices to the minimum group number of Vi and Vj. That way, vertices connected together will eventually get the same minimum group number, which is the minimum vertex ID among all vertices in the connected component. First you need a class to represent a vertex:
</p>

<p align=justify>Re-implementing Project #3 (Graph Processing) as described above using Spark and Scala without using Map-Reduce. That is, the program finds the connected components of any undirected graph and prints the size of these connected components. A connected component of a graph is a subgraph of the graph in which there is a path from any two vertices in the subgraph.</p>

<p align=justify>The graph can be represented as RDD[ ( Long, Long, List[Long] ) ], where the first Long is the graph node ID, the second Long is the group that this vertex belongs to (initially, equal to the node ID), and the List[Long] is the adjacent list (the IDs of the neighbors).</p>

<p>The psuedo-code:</p>

```
var graph = /* read the graph from args(0); the group of a graph node is set to the node ID */

for (i <- 1 to 5)
   graph = graph.flatMap{ /* associate each adjacent neighbor with the node group number + the node itself with its group number*/ }
                .reduceByKey( /* get the min group of each node */ )
                .join( /* join with the original graph */ )
                .map{ /* reconstruct the graph topology */ }

/* finally, print the group sizes */
```
<p align=justify>For example, for the node (20,6,List(22,23,24)), the flatMap must return the sequence Seq((20,6),(22,6),(23,6),(24,6)). The output (group sizes) must be sent to the output, not to a file.</p>

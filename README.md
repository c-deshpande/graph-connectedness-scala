# graph-connectedness-scala
Map-Reduce program that finds the connected components of any undirected graph and prints the size of these connected components using Scala and Spark.

Project done as a part of CSE-6331 Cloud Computing Course at UTA.

<a href="https://lambda.uta.edu/cse6331/spring20/project5.html">Project Description</a>

<p>You are asked to re-implement Project #3 (Graph Processing) using Spark and Scala. Do not use Map-Reduce. That is, your program must find the connected components of any undirected graph and prints the size of these connected components. A connected component of a graph is a subgraph of the graph in which there is a path from any two vertices in the subgraph.</p>

<p>The graph can be represented as RDD[ ( Long, Long, List[Long] ) ], where the first Long is the graph node ID, the second Long is the group that this vertex belongs to (initially, equal to the node ID), and the List[Long] is the adjacent list (the IDs of the neighbors).</p>

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
<p>For example, for the node (20,6,List(22,23,24)), the flatMap must return the sequence Seq((20,6),(22,6),(23,6),(24,6)). The output (group sizes) must be sent to the output, not to a file.</p>

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/*Creating a custom class of Vertex, as it would be convenient*/
case class Vertex(nodeID: Long, group: Long, adjacent: List[Long])

object Graph {
  def main(args: Array[String]): Unit = {

    /*Creating Spark configuration and setting up the app name*/
    val conf = new SparkConf().setAppName("Connectedness of Graph")
    conf.setMaster("local[2]")

    /*Initializing the SparkContext*/
    val sc = new SparkContext(conf)

    /*Reading the file line by line and mapping to custom vertex class*/
    var graph = sc.textFile(args(0)).map(line => {

      /*Splitting each line using a comma as a delimiter*/
      val a = line.split(",")

      /*Initially, the group of each node is set to the node id*/
      (a(0).toLong, a(0).toLong, a.slice(1, a.length).toList.map(_.toLong))
    })

    //(nodeid, group, adjacent)


    /*Repeating the job 5 times, for the entire graph*/
    for (i <- 1 to 5) {

      graph = graph.flatMap(v => v._3.flatMap(a => Seq((a, v._2))) ++ Seq((v._1, v._2))) /*associate each adjacent neighbor with the node group number + the node itself with its group number, we achieve this by concatenation of two Seqs*/
        .reduceByKey((x, y) => x min y) /*Getting minimum group of each node*/
        .join(graph.map(v => (v._1, v))) /*Joining the graph with the original graph*/
        .map { case (nodeID, (min, vertex)) => (nodeID, min, vertex._3) } /*Reconstructing the graph topology*/
    }

    /*Finally, printing the group sizes*/
    val reducedResult = graph.map(v => (v._2, 1)).reduceByKey((x, y) => (x + y))

    /*Mapping the result in such a way that we get the required format*/
    reducedResult.map(x => {

      x._1 + "\t" + x._2
    }).collect().foreach(println)

    /*Stopping the SparkContext*/
    sc.stop()
  }
}

import java.util.Set;

public class Graph {
    private int totalVertex;
    public Course[] node;

    public Graph(int vertex)
    {
        this.totalVertex = vertex;

        node = new Course[vertex+1];
    }

    public void addVertex(int cid,int numStud)
    {
        Course tempCourse = new Course(cid,numStud);
        node[cid] = tempCourse;
    }

    public void addVertex(Course course)
    {
        node[course.getCourse_id()] = course;
    }

    public void addEdge(int i,int j)
    {
        node[i].addEdge(node[j]);
        node[j].addEdge(node[i]);
    }

    public Course getVertex(int cid)
    {
        return node[cid];
    }

    public Set<Course> getNeighbour(int vertex)
    {
        return node[vertex].getEdges();
    }

    public int getTotalVertex()
    {
        return totalVertex;
    }

    public boolean hasEdge(int to,int from)
    {
        return (node[to].getEdges().contains(node[from]) || node[from].getEdges().contains(node[to]));

    }


    @Override
    public String toString() {
        System.out.println("Graph: ");
        for(int i = 1; i<= totalVertex; i++){
            System.out.println(i + " has edge with: ");
            for (Course course : node[i].getEdges())
            {
                System.out.print(course.getCourse_id()+" ");
            }
            System.out.println();
        }

        return "";
    }
}

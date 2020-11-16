import java.util.*;

public class CSPsolver {

    private  Graph graph;
    private  int[] assignment;
    private  int numofSlot;
    private  List<Course> sortedList;

    public CSPsolver(Graph g,int m)
    {
        this.graph = g;

        int totalV = graph.getTotalVertex();

        this.assignment = new int[totalV+1];

        Arrays.fill(assignment,-1);

        this.sortedList = new ArrayList<>();

        for(int i = 1; i<=totalV; i++){
            sortedList.add(graph.getVertex(i));
        }

        Collections.sort(sortedList,Course.totalDegreeOrder);

        this.numofSlot = m;


        for(int i=1;i<=graph.getTotalVertex();i++)
        {
            graph.node[i].initPosstibleSlot(m);
        }
    }


    public boolean isSafe(Course vertex,int current_value)
    {
        for(int i = 1; i<=this.graph.getTotalVertex(); i++){
            //System.out.println("Cur: "+vertex.getCourse_id()+" N: "+i+" Has: "+graph.hasEdge(vertex.getCourse_id(),i)+"\tcur clr: "+current_value+" n clr: "+assignment[i]);
            boolean cond2 = (current_value == this.assignment[i]);
            boolean cond1 = this.graph.hasEdge(vertex.getCourse_id(),i);
            if( cond1 && cond2 ){
                return false;
            }
        }

        return true;
    }


    public boolean backTrackSolveFC(int index)
    {
        if (index == graph.getTotalVertex()) return true;

        Course cur = sortedList.get(index);
//        System.out.println(cur.getCourse_id());

        if(cur.possilbeSlot.isEmpty()) return false;

        for(int i=0;i<cur.possilbeSlot.size();i++)
        {
            int slot = cur.possilbeSlot.get(i);
            if(isSafe(cur,slot)){
                this.assignment[cur.getCourse_id()] = slot;
                //System.out.println(slot);
                for(int k=1;k<=graph.getTotalVertex();k++)
                {
                    if(graph.hasEdge(cur.getCourse_id(),k))
                    {
                        Course c = graph.getVertex(k);
                        if(c.possilbeSlot.contains(slot))
                            c.possilbeSlot.remove((Object) slot);
                    }
                }

                if(backTrackSolveFC(index+1)) return true;

                this.assignment[cur.getCourse_id()] = -1;

                for(int j=1;j<=graph.getTotalVertex();j++)
                {
                    if(graph.hasEdge(cur.getCourse_id(),j))
                    {
                        graph.getVertex(j).possilbeSlot.add(slot);
                    }
                }
            }
        }

        return false;
    }

    public boolean backTrackSolve(int index)
    {
        if (index == graph.getTotalVertex()) return true;

        Course cur = sortedList.get(index);
        //System.out.println(cur.getCourse_id());

        for(int i=0;i<this.numofSlot;i++){
            if(isSafe(cur,i)){
                this.assignment[cur.getCourse_id()] = i;

                if(backTrackSolve(index+1)) return true;

                this.assignment[cur.getCourse_id()] = -1;
            }
        }

        return false;
    }

    public boolean solve(){
        if(!backTrackSolveFC(0)){
            //System.out.println("No solution exist");
            return false;
        }

        return true;
    }

    public void printSolution() {
        for(int i=1;i<=this.graph.getTotalVertex();i++){
            System.out.println(i+" "+assignment[i]);
        }
    }

}

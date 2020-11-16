import java.util.*;
import java.util.function.BinaryOperator;

public class GreedySolver
{

    private  Graph graph;
    private  List<Student> students;
//    private  int[] assignment;
//    private  boolean[] available;
    private  List<Course> sortedList;
    private  List<Course> courseList;
    private  int maxSlot;

    public GreedySolver(Graph g,List<Student>ss)
    {
        this.graph = g;
        this.students = ss;

        int totalV = graph.getTotalVertex();

        this.sortedList = new ArrayList<>();
        this.courseList = new ArrayList<>();

        for(int i = 1; i<=totalV; i++){
            sortedList.add(graph.getVertex(i));
            courseList.add(graph.getVertex(i));
        }

//        Collections.sort(sortedList,Course.totalDegreeOrder);
//        Collections.sort(sortedList,Course.largestEnrollmentOrder);

//        try {
//            Collections.sort(sortedList,Course.randomOrder);
//        } catch (Exception e){
//            Collections.sort(sortedList,Course.randomOrder);
//        }


    }

    public int[] solve(Comparator order)
    {
//        this.assignment = new int[graph.getTotalVertex()+1];
//        Arrays.fill(assignment,-1);

        List<Course> sortedList = new ArrayList<>();
        sortedList.addAll(courseList);
        Collections.sort(sortedList,order);

        int[] solution = new int[graph.getTotalVertex()+1];
        Arrays.fill(solution,-1);

        boolean[] available = new boolean[graph.getTotalVertex()];
        Arrays.fill(available,true);

        Course first = sortedList.get(0);

        solution[first.getCourse_id()] = 0;

        for(int v=1;v<sortedList.size();v++)
        {
            Course cur = sortedList.get(v);
//            System.out.println("cur: " + cur.getCourse_id());
            Set<Course> neighbours = graph.getNeighbour(cur.getCourse_id());

            for(Course n: neighbours)
            {
                if(solution[n.getCourse_id()] != -1)
                {
                    available[solution[n.getCourse_id()]] = false;
                }
            }

            int slot;
            for(slot = 0; slot<graph.getTotalVertex();slot++)
            {
                if(available[slot]) break;
            }

            solution[cur.getCourse_id()] = slot;

            Arrays.fill(available,true);
        }

        for(int i=1;i<=this.graph.getTotalVertex();i++)
        {
            graph.getVertex(i).setTimeSlot(solution[i]);
        }

        return solution;
        //return this.printSolution(assignment);
    }

    public int[] solveZeroPenalty(Comparator order)
    {
        List<Course> sortedList = new ArrayList<>();
        sortedList.addAll(courseList);
        Collections.sort(sortedList,order);
//        this.assignment = new int[graph.getTotalVertex()+1];
//        Arrays.fill(assignment,-1);
        int[] solution = new int[graph.getTotalVertex()+1];
        Arrays.fill(solution,-1);

        boolean[] available = new boolean[graph.getTotalVertex()*10];
        Arrays.fill(available,true);

        boolean[] isColored = new boolean[graph.getTotalVertex()+1];
        Arrays.fill(isColored,false);

//        Collections.sort(courseList,Course.totalDegreeOrder);
        Course first = sortedList.get(0);

        solution[first.getCourse_id()] = 0;
        isColored[first.getCourse_id()] = true;

        for(int v=1;v<courseList.size();v++)
        {
            List<Integer>nSlots = new ArrayList<>();
            Course cur = sortedList.get(v);
//            System.out.println("cur: " + cur.getCourse_id());
            Set<Course> neighbours = graph.getNeighbour(cur.getCourse_id());

            for(Course n: neighbours)
            {
                if(solution[n.getCourse_id()] != -1)
                {
                    available[solution[n.getCourse_id()]] = false;
                }

                if(isColored[n.getCourse_id()]) nSlots.add(solution[n.getCourse_id()]);
            }

            int slot;
            for(slot = 0; slot<graph.getTotalVertex();slot++)
            {
                if(available[slot]) break;
            }

            Collections.sort(nSlots);
            for (int idx=0;idx<nSlots.size();idx++)
            {
                int nSlotValue = nSlots.get(idx);
                if(slot<nSlotValue && nSlotValue-slot>5) break;

                else if(Math.abs(slot-nSlots.get(idx))<=5)
                {
                    slot = nSlots.get(idx)+6;
                    while (!available[slot]) slot++;
                }
            }

            if(solution[cur.getCourse_id()] == -1)
            {
                solution[cur.getCourse_id()] = slot;
                isColored[cur.getCourse_id()] = true;
            }

            Arrays.fill(available,true);
        }

        for(int i=1;i<=this.graph.getTotalVertex();i++)
        {
            graph.getVertex(i).setTimeSlot(solution[i]);
        }

        return solution;
        //return this.printSolution(assignment);
    }

    public int[] dSaturZeroPenalty()
    {
        int[] solution = new int[graph.getTotalVertex()+1];
        Arrays.fill(solution,-1);

        boolean[] available = new boolean[graph.getTotalVertex()*10];
        Arrays.fill(available,true);

        boolean[] isColored = new boolean[graph.getTotalVertex()+1];
        Arrays.fill(isColored,false);

        List<Course> sortedList = new ArrayList<>();
        sortedList.addAll(courseList);

        for(int v=0;v<graph.getTotalVertex();v++)
        {
            List<Integer> nSlots = new ArrayList<>();


            Collections.sort(sortedList,Course.dSaturOrder);
//            for(int i=0;i<sortedList.size();i++)
//            {
//                Course c = sortedList.get(i);
////                System.out.println("Cid: "+c.getCourse_id()+"\t\t\tSatur: "+c.getSaturDegree()+"\ttotal: "+c.getTotalDegree());
//            }

            Course cur = sortedList.get(0);
//            System.out.println("selected: "+ cur.getCourse_id());
            sortedList.remove(cur);

            Set<Course> neighbours = graph.getNeighbour(cur.getCourse_id());

            for(Course n: neighbours)
            {
                if(solution[n.getCourse_id()] != -1)
                {
                    available[solution[n.getCourse_id()]] = false;
                }

                if(isColored[n.getCourse_id()]) nSlots.add(solution[n.getCourse_id()]);
            }

            int slot;
            for(slot = 0; slot<graph.getTotalVertex();slot++)
            {
                if(available[slot]) break;
            }

            Collections.sort(nSlots);
            for (int idx=0;idx<nSlots.size();idx++)
            {
                int nSlotValue = nSlots.get(idx);
                if(slot<nSlotValue && nSlotValue-slot>5) break;

                else if(Math.abs(slot-nSlots.get(idx))<=5)
                {
                    slot = nSlots.get(idx)+6;
                    while (!available[slot]) slot++;
                }
            }

            if(solution[cur.getCourse_id()] == -1)
            {
                solution[cur.getCourse_id()] = slot;
                isColored[cur.getCourse_id()] = true;
            }
//            System.out.println("Cur: "+cur.getCourse_id()+"\tslot: "+slot);

            for(Course n: neighbours)
            {
                n.incSaturDegree(slot);
            }

            Arrays.fill(available,true);
        }

        for(int i=1;i<=this.graph.getTotalVertex();i++)
        {
            graph.getVertex(i).setTimeSlot(solution[i]);
        }

//        return this.printSolution(solution);
        return solution;
    }

    public int[] dSaturSolve()
    {
        int[] solution = new int[graph.getTotalVertex()+1];
        Arrays.fill(solution,-1);

        boolean[] available = new boolean[graph.getTotalVertex()];
        Arrays.fill(available,true);

        List<Course> sortedList = new ArrayList<>();
        sortedList.addAll(courseList);

        for(int v=0;v<graph.getTotalVertex();v++)
        {
            Collections.sort(sortedList,Course.dSaturOrder);
            for(int i=0;i<sortedList.size();i++)
            {
                Course c = sortedList.get(i);
//                System.out.println("Cid: "+c.getCourse_id()+"\t\t\tSatur: "+c.getSaturDegree()+"\ttotal: "+c.getTotalDegree());
            }

            Course cur = sortedList.get(0);
//            System.out.println("selected: "+ cur.getCourse_id());
            sortedList.remove(cur);

            Set<Course> neighbours = graph.getNeighbour(cur.getCourse_id());

            for(Course n: neighbours)
            {
                if(solution[n.getCourse_id()] != -1)
                {
                    available[solution[n.getCourse_id()]] = false;
                }
            }

            int slot;
            for(slot = 0; slot<graph.getTotalVertex();slot++)
            {
                if(available[slot]) break;
            }

            solution[cur.getCourse_id()] = slot;
//            System.out.println("Cur: "+cur.getCourse_id()+"\tslot: "+slot);

            for(Course n: neighbours)
            {
                n.incSaturDegree(slot);
            }

            Arrays.fill(available,true);
        }

        for(int i=1;i<=this.graph.getTotalVertex();i++)
        {
            graph.getVertex(i).setTimeSlot(solution[i]);
        }

//        return this.printSolution(solution);
        return solution;
    }


    public int[] kempeChain(int rootIdx,int[] fSolution)
    {
        int searchSlot;
        Course root = graph.getVertex(rootIdx);

        List<Course> listU = new ArrayList<>();
        List<Course> listV = new ArrayList<>();

        Queue<Course> queue = new LinkedList<>();

        boolean[] visited = new boolean[graph.getTotalVertex()+1];

//        double minPenalty = this.getPenalty(assignment);
//        int [] minAssignment = assignment.clone();
        double minPenalty = this.getPenalty(fSolution);
        int [] minAssignment = fSolution.clone();
//        int [] minAssignment = new int[assignment.length];
//        for(int i=1;i<assignment.length;i++){
//            minAssignment[i] = this.assignment[i];
//        }

        Set<Course> rootEdge = root.getEdges();
        for(Course n: rootEdge)
        {
            Arrays.fill(visited,false);

            int slotU = root.getTimeSlot();

            queue.clear();
            queue.add(root);
            int slotV = n.getTimeSlot();

            while (!queue.isEmpty())
            {
                Course current = queue.poll();

                if(!visited[current.getCourse_id()])
                {
                    visited[current.getCourse_id()] = true;

                    if(current.getTimeSlot() == slotU)  listU.add(current);
                    else if(current.getTimeSlot() == slotV)  listV.add(current);

                    Set<Course> curEdges = current.getEdges();

                    for(Course adj: curEdges)
                    {
                        if((!visited[adj.getCourse_id()]) && (adj.getTimeSlot() == slotU || adj.getTimeSlot() == slotV))
                        {
                            queue.add(adj);
                        }
                    }
                }
            }

//            int[] tempAssignment = assignment.clone();
            int[] tempAssignment = fSolution.clone();

//            int [] tempAssignment = new int[assignment.length];
//            for(int i=1;i<assignment.length;i++){
//                tempAssignment[i] = assignment[i];
//            }


            for(int i=0;i<listU.size();i++)
            {
                tempAssignment[listU.get(i).getCourse_id()] = slotV;
            }
            for (int i=0;i<listV.size();i++)
            {
                tempAssignment[listV.get(i).getCourse_id()] = slotU;
            }

            double curPenalty = this.getPenalty(tempAssignment);

            if(curPenalty<minPenalty)
            {
                minPenalty = curPenalty;
                minAssignment = tempAssignment.clone();
//                for(int i=1;i<assignment.length;i++){
//                    minAssignment[i] = tempAssignment[i];
//                }
            }


            listU.clear();
            listV.clear();
        }

//        System.out.print("Root: " + root.getCourse_id());
//        System.out.println("\tKC penalty : " + minPenalty);
//        this.assignment = minAssignment.clone();
        fSolution = minAssignment.clone();

//        for(int i=1;i<assignment.length;i++){
//            this.assignment[i] = minAssignment[i];
//        }
        for(int i=1;i<=graph.getTotalVertex();i++)
        {
//            graph.getVertex(i).setTimeSlot(this.assignment[i]);
            graph.getVertex(i).setTimeSlot(fSolution[i]);
        }

        return fSolution;
    }

    public void KCsolver(int[] fessibleSolution)
    {
        Collections.sort(courseList,Course.totalDegreeOrder);

        for(int i=0;i<courseList.size();i++)
        {
            fessibleSolution = this.kempeChain(courseList.get(i).getCourse_id(),fessibleSolution);
        }
        System.out.println("KC penalty: "+this.getPenalty(fessibleSolution));

    }

    public void psoSolver(int[] fessibleSolution)
    {
        for(int i=1;i<=graph.getTotalVertex();i++)
        {
            for(int j=1;j<=graph.getTotalVertex();j++)
            {
                boolean flagU = false;
                boolean flagV = false;

                int slotU = fessibleSolution[i];
                int slotV = fessibleSolution[j];
                System.out.println("i: "+i+"\tj: "+j+"\tU: "+slotU+"\tV: "+slotV);

                if(i==j || slotU == slotV || graph.hasEdge(i,j)) continue;

                Course courseU = graph.getVertex(i);
                Set<Course> uNeighbour = courseU.getEdges();
                for(Course n: uNeighbour)
                {
                    if(fessibleSolution[n.getCourse_id()] == slotV)
                    {
                        System.out.println("u: "+i+"\tn: "+n.getCourse_id()+"\tun: "+fessibleSolution[n.getCourse_id()]);
                        flagU = true;
                        break;
                    }
                }

                if(flagU) continue;


                Course courseV = graph.getVertex(j);
                Set<Course> vNeighbour = courseV.getEdges();
                for(Course n: vNeighbour)
                {
                    if(fessibleSolution[n.getCourse_id()] == slotU)
                    {
                        System.out.println("v: "+j+"\tn: "+n.getCourse_id()+"\tvn: "+fessibleSolution[n.getCourse_id()]);
                        flagV = true;
                        break;
                    }
                }

                if(flagV) continue;

                int[] tempSolution = fessibleSolution.clone();

                //Pair Swap
                tempSolution[i] = slotV;
                tempSolution[j] = slotU;

                this.printSolution(tempSolution);
            }
        }
    }

    public int getMostCommon(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<Integer, Integer> max = null;

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        assert max != null;
        return max.getKey();
    }

    public int[] swoConstructor(int[] currentSolution,int targetSet,List<Course> nodes)
    {
        boolean[] available = new boolean[targetSet];
        List<Integer> neighbourPossibleSlot = new ArrayList<>();
        int extraSlot = targetSet-1;
        boolean[] isColored = new boolean[graph.getTotalVertex()+1];
        int[] newSolution = new int[currentSolution.length];
        Arrays.fill(newSolution,-1);

        for(int i=1;i<=graph.getTotalVertex();i++)
        {
            graph.getVertex(i).initPosstibleSlot(targetSet);
        }
        Arrays.fill(isColored,false);

//        System.out.println("First: "+ nodes.get(0).getCourse_id());

        for(int i=0;i<nodes.size();i++)
        {
            Arrays.fill(available,true);

            Course current = nodes.get(i);
            int currentAssigned = currentSolution[current.getCourse_id()];

            Set<Course> neighbour = current.getEdges();
            for(Course n: neighbour)
            {
//                int assignedSlot = currentSolution[n.getCourse_id()];
                int assignedSlot = newSolution[n.getCourse_id()];
                if(assignedSlot< targetSet && assignedSlot != -1)
                {
                    available[assignedSlot] = false;
                }
                if(!isColored[n.getCourse_id()]) neighbourPossibleSlot.addAll(n.possilbeSlot);
            }

            if((currentAssigned < targetSet) && available[currentAssigned])
            {
                newSolution[current.getCourse_id()] = currentAssigned;
                isColored[current.getCourse_id()] = true;
                for(Course n: neighbour)
                {
                    n.possilbeSlot.remove((Integer) currentAssigned);
                }
            }
            else
            {
//                System.out.println("c: "+current.getCourse_id());
                int LRV = -1;
                while (!neighbourPossibleSlot.isEmpty()){
                    LRV = this.getMostCommon(neighbourPossibleSlot);
                    if(LRV >= targetSet) neighbourPossibleSlot.removeAll(Collections.singleton((Integer) LRV));
                    else if(LRV != -1) break;
                }
//                System.out.println("LRV: "+LRV);

                if(LRV != -1 && available[LRV])
                {
                    newSolution[current.getCourse_id()] = LRV;
                    isColored[current.getCourse_id()] = true;
                    for(Course n: neighbour)
                    {
                        n.possilbeSlot.remove((Integer) LRV);
                    }
                }
                else
                {
                    extraSlot++;
                    newSolution[current.getCourse_id()] = extraSlot;
                    isColored[current.getCourse_id()] = true;
                }

            }

        }

//        System.out.println("SWO");
        this.printSolution(newSolution);
//        for(int i=1;i<newSolution.length;i++)
//        {
//            System.out.println(i+":"+newSolution[i]);
//        }
        return newSolution;
    }

    public List<Course> swoAnalyzer(int[] currentSolution,int targetSlot,double alpha)
    {
        List<Course> nodeSequence = new ArrayList<>();
        for(int i=1;i<=graph.getTotalVertex();i++)
        {
            Course vertex = graph.getVertex(i);
            if(currentSolution[i]>=targetSlot) vertex.setBlameValue(alpha*currentSolution[i]);
            else vertex.setBlameValue(0.0);
            nodeSequence.add(vertex);
        }
        return nodeSequence;
    }

    public List<Course> swoPrioritizer(List<Course> nodeSequence)
    {
        Collections.sort(nodeSequence,Course.blameOrder);
        return nodeSequence;
    }

    public void swoSolve(int targetSet,int[] solution)
    {
        System.out.println("here");
        List<Course> nodes = courseList;

        for(int i=0;i<1000;i++)
        {
            System.out.println("Itr: "+(i+1));
            solution = this.swoConstructor(solution,targetSet,nodes);
            nodes = this.swoAnalyzer(solution,targetSet,0.95);
            nodes = this.swoPrioritizer(nodes);
        }

    }

    public int printSolution(int[] solution)
    {
        maxSlot = -1;
        for(int i=1;i<=this.graph.getTotalVertex();i++){
//            System.out.println(i+" "+solution[i]);
            if(solution[i]>maxSlot) maxSlot = solution[i];
        }

//        System.out.println("Max slot: "+ (maxSlot+1));
        System.out.println("Penalty: "+ this.getPenalty(solution));
        return (maxSlot+1);
    }

    public double getPenalty(int[] timeTable)
    {
        int totalStudent = students.size();
        int slot1=-1;
        int slot2=-1;
        int diff;
        double sum=0.0;
        double penalty=0.0;
        double avgPenalty;

        for(int k=0;k<totalStudent;k++)
        {
            Student s = students.get(k);
            List<Integer> clist = s.getCourses();

            for(int i=0;i<clist.size();i++)
            {
                slot1 = timeTable[clist.get(i)];
                for(int j=i+1;j<clist.size();j++)
                {
//                    slot1 = graph.getVertex(clist.get(i)).getTimeSlot();
//                    slot2 = graph.getVertex(clist.get(j)).getTimeSlot();
                    slot2 = timeTable[clist.get(j)];

                    diff = Math.abs(slot1-slot2);
                    if(diff == 0){
                        System.out.println("WTF");
                        System.out.println("i: "+clist.get(i)+"\tj: "+clist.get(j));
                        break;
                    }

                    penalty = Math.pow(2,(5-diff));
                    if (penalty<1.0) penalty = 0;
                    sum += penalty;
                    //System.out.println("S1: "+slot1+"\tS2: "+slot2+"\tP: "+penalty);
                    //System.out.println("running sum: "+sum);
                }
            }
        }

        avgPenalty = sum / totalStudent;

        return avgPenalty;
    }
}
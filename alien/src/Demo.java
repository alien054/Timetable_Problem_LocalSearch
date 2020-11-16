import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Demo {

    public Graph static_graph()
    {
        int totalVertex = 8;
        Graph graph = new Graph(totalVertex);

        for(int i=1;i<=totalVertex;i++){
            graph.addVertex(i,0);
        }

        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
        graph.addEdge(2,1);
        graph.addEdge(2,5);
        graph.addEdge(3,1);
        graph.addEdge(3,4);
        graph.addEdge(3,6);
        graph.addEdge(3,7);
        graph.addEdge(4,1);
        graph.addEdge(4,3);
        graph.addEdge(4,5);
        graph.addEdge(4,6);
        graph.addEdge(4,7);
        graph.addEdge(5,2);
        graph.addEdge(5,4);
        graph.addEdge(5,8);
        graph.addEdge(6,3);
        graph.addEdge(6,4);
        graph.addEdge(6,7);
        graph.addEdge(7,3);
        graph.addEdge(7,4);
        graph.addEdge(7,6);
        graph.addEdge(7,8);
        graph.addEdge(8,5);
        graph.addEdge(8,7);


        return graph;

    }

    public void result(int choice)
    {
        String[] dataSets = {"car-s-91","car-f-92","kfu-s-93","tre-s-92","yor-f-83"};
        String currentSet = dataSets[choice];
        String f1 = currentSet+".crs";
        String f2 = currentSet+".stu";

        System.out.println("======================================================");
        System.out.println("Current Dataset: \n"+currentSet);
        File file = new File(f1);
        int count = 0;

        BufferedReader br = null;
        List<Course> vertex = new ArrayList<>();
        Graph g;
        List<Student> students = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                //System.out.println(st);
                String[] arrOfStr = st.split(" ");
                //System.out.println(arrOfStr[0]+" "+arrOfStr[1]);
                Course course = new Course(Integer.parseInt(arrOfStr[0]),Integer.parseInt(arrOfStr[1]));
                vertex.add(course);
                count++;
            }
            g = new Graph(count);
            for(Course c: vertex){
                g.addVertex(c);
            }
            //System.out.println(count);

            File file1 = new File(f2);
            br = new BufferedReader(new FileReader(file1));
            while ((st = br.readLine()) != null) {
                Student s = new Student();
                String[] arrOfStr = st.split(" ");

                for(int i=0;i<arrOfStr.length;i++){
                    int x = Integer.parseInt(arrOfStr[i]);
                    s.addCourse(x);
                    for(int j=0;j<arrOfStr.length;j++){
                        int y = Integer.parseInt(arrOfStr[j]);
                        if(x!=y) g.addEdge(x,y);
                    }
                }

                students.add(s);
            }

            GreedySolver gSolver = new GreedySolver(g,students);

            System.out.println("Largest Degree");
            int[] solution = gSolver.solve(Course.totalDegreeOrder);
            System.out.println("Total Slot: "+ gSolver.printSolution(solution));
            gSolver.KCsolver(solution);
            System.out.println("Zero Penalty Solution: ");
            solution = gSolver.solveZeroPenalty(Course.totalDegreeOrder);
            System.out.println("Total Slot: "+ gSolver.printSolution(solution));
            System.out.println("--------------------");
            System.out.println("Largest Enrollment");
            solution = gSolver.solve(Course.largestEnrollmentOrder);
            System.out.println("Total Slot: "+ gSolver.printSolution(solution));
            gSolver.KCsolver(solution);
            System.out.println("Zero Penalty Solution: ");
            solution = gSolver.solveZeroPenalty(Course.largestEnrollmentOrder);
            System.out.println("Total Slot: "+ gSolver.printSolution(solution));
            System.out.println("--------------------");
            System.out.println("Dsatur");
            solution = gSolver.dSaturSolve();
            System.out.println("Total Slot: "+ gSolver.printSolution(solution));
            gSolver.KCsolver(solution);
            System.out.println("Zero Penalty Solution: ");
            solution = gSolver.dSaturZeroPenalty();
            System.out.println("Total Slot: "+ gSolver.printSolution(solution));
            System.out.println("--------------------");
//            System.out.println("Random");
//            try{
//                solution = gSolver.solve(Course.randomOrder);
//                System.out.println("Total Slot: "+ gSolver.printSolution(solution));
//                gSolver.KCsolver(solution);
//            } catch (Exception e){
//                solution = gSolver.solve(Course.randomOrder);
//                System.out.println("Total Slot: "+ gSolver.printSolution(solution));
//                gSolver.KCsolver(solution);
//            }
//
//            System.out.println("Zero Penalty Solution: ");
//            try{
//                solution = gSolver.solveZeroPenalty(Course.randomOrder);
//                System.out.println("Total Slot: "+ gSolver.printSolution(solution));
//                System.out.println("--------------------");
//            } catch (Exception e){
//                solution = gSolver.solveZeroPenalty(Course.randomOrder);
//                System.out.println("Total Slot: "+ gSolver.printSolution(solution));
//                System.out.println("--------------------");
//            }


//            gSolver.psoSolver(solution);

//            gSolver.swoSolve(22,solution);
//            g.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Demo d = new Demo();

        //code starts form here
//        Graph g = d.static_graph();
//        List<Student> students = new ArrayList<>();
//        Student s = new Student();
//        students.add(s);
//
//        int[] solution = new int[8];
//        GreedySolver gSolver = new GreedySolver(g,students);
//        solution = gSolver.dSaturSolve();
//        System.out.println(gSolver.printSolution(solution));
////        System.out.println("Total Slot: "+ gSolver.dSaturSolve());
////        gSolver.KCsolver();
//        gSolver.psoSolver(solution);

        for(int i=0;i<5;i++){
            d.result(i);
        }




    }
}


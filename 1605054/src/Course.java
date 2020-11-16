import java.util.*;

public class Course
{
    private int course_id;
    private int total_student;
    private int timeSlot;
    private int saturDegree;
    private int toSearch;
    private double blameValue;
    public boolean isColored;
    private Set<Course> edges;
    private List<Integer> neighbourSlots;
    public List<Integer>possilbeSlot;


    public Course(int cid, int numStud)
    {
        Random random = new Random();
        this.course_id = cid;
        this.total_student = numStud;
        this.timeSlot = -1;
        this.saturDegree = 0;
        this.isColored = false;

        this.blameValue = 0;

        neighbourSlots = new ArrayList<>();

        edges = new HashSet<>();
        possilbeSlot = new ArrayList<>();
    }

    public void initPosstibleSlot(int m)
    {
        for(int i=0;i<m;i++){
            possilbeSlot.add(i);
        }
    }

    public Set<Course> getEdges()
    {
        return edges;
    }

    public void addEdge(Course n)
    {
        edges.add(n);
    }

    public int getTotalDegree()
    {
        return edges.size();
    }


    public int getCourse_id() {
        return course_id;
    }

    public int getTotal_student() {
        return total_student;
    }


    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }


    public int getSaturDegree()
    {
        return this.saturDegree;
    }

    public void incSaturDegree(int slot)
    {
        if(!neighbourSlots.contains(slot))
        {
            this.saturDegree++;
            neighbourSlots.add(slot);
        }
    }

    public double getBlameValue() {
        return blameValue;
    }

    public void setBlameValue(double blameValue) {
        this.blameValue = blameValue;
    }

    public static Comparator<Course> totalDegreeOrder = (o1, o2) -> {
        if(o1.getTotalDegree() < o2.getTotalDegree())  return 1;

        else if(o1.getTotalDegree() > o2.getTotalDegree()) return -1;

        return 0;
    };

    public static Comparator<Course> largestEnrollmentOrder = (o1, o2) -> {
        if(o1.getTotal_student() < o2.getTotal_student())  return 1;

        else if(o1.getTotal_student() > o2.getTotal_student()) return -1;

        return 0;

    };

    public static Comparator<Course> dSaturOrder = (o1, o2) -> {
        if(o1.getSaturDegree() < o2.getSaturDegree())  return 1;

        else if(o1.getSaturDegree() > o2.getSaturDegree()) return -1;

        else
        {
            if(o1.getTotalDegree() < o2.getTotalDegree())  return 1;

            else if(o1.getTotalDegree() > o2.getTotalDegree()) return -1;

            return 0;
        }

    };

    public static Comparator<Course> blameOrder = (o1, o2) -> {
        if(o1.getBlameValue() < o2.getBlameValue())  return 1;

        else if(o1.getBlameValue() > o2.getBlameValue()) return -1;

        else
        {
            if(o1.getTotalDegree() < o2.getTotalDegree())  return 1;

            else if(o1.getTotalDegree() > o2.getTotalDegree()) return -1;

            return 0;
        }

    };


    public static Comparator<Course> randomOrder = (o1, o2) -> {
        Random random = new Random();
        int rand = random.nextInt(3);
        rand = rand - 1;

        return rand;
    };

}

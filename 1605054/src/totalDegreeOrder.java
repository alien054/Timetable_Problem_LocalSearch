import java.util.Comparator;

public class totalDegreeOrder implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        if(o1.getTotalDegree() < o2.getTotalDegree())  return 1;

        else if(o1.getTotalDegree() > o2.getTotalDegree()) return -1;

        return 0;
    }
}

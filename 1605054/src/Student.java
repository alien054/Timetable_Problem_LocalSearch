import java.util.ArrayList;
import java.util.List;

public class Student {
    private List<Integer> courses;

    public Student()
    {
        courses = new ArrayList<>();
    }

    public void addCourse(int c)
    {
        courses.add(c);
    }

    public List<Integer> getCourses(){
        return courses;
    }
}

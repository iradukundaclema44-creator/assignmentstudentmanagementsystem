
import java.util.*;

public class student {
  private int id;
  private String studentName;
  private int age;
  private Map<String, Double> courses;
  private List<String> skills;
  private boolean bonusApplied = false;

  public student(int id, String studentName, int age,
      Map<String, Double> courses,
      List<String> skills) {
    this.id = id;
    this.studentName = studentName;
    this.age = age;
    this.courses = courses;
    this.skills = skills;
  }

  public int getId() {
    return id;
  }

  public String getstudentName() {
    return studentName;
  }

  public int getAge() {
    return age;
  }

  public Map<String, Double> getCourses() {
    return courses;
  }

  public List<String> getSkills() {
    return skills;
  }

  public boolean isBonusApplied() {
    return bonusApplied;
  }

  public void setBonusApplied(boolean bonusApplied) {
    this.bonusApplied = bonusApplied;
  }

  public double getAverage() {
    return courses.values().stream()
        .mapToDouble(Double::doubleValue)
        .average()
        .orElse(0.0);
  }
  
public void setId(int id) {
    this.id = id;
}

public void setStudentName(String studentName) {
    this.studentName = studentName;
}

public void setAge(int age) {
    this.age = age;
}

public void setCourses(Map<String, Double> courses) {
    this.courses = courses;
}

public void setSkills(List<String> skills) {
    this.skills = skills;
}


  @Override
  public String toString() {
    return "ID: " + id +
        ", Name: " + studentName +
        ", Age: " + age +
        ", Courses: " + courses +
        ", Skills: " + skills +
        ", Average: " + getAverage();
  }
}

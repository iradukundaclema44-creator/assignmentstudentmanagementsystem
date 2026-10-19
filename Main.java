
import java.util.*;
import java.util.stream.Collectors;

public class Main {
  static Scanner input = new Scanner(System.in);
  static List<student> students = new ArrayList<>();

  static int readInt(String message) {
    while (true) {
      try {
        System.out.print(message);
        return Integer.parseInt(input.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
      }
    }
  }

  static double readMark(String message) {
    while (true) {
      try {
        System.out.print(message);
        double mark = Double.parseDouble(input.nextLine());
        if (mark >= 0 && mark <= 100) {
          return mark;
        }
        System.out.println("Mark must be between 0 and 100.");
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid mark.");
      }
    }
  }

  static String readText(String message) {
    while (true) {
      System.out.print(message);
      String value = input.nextLine().trim();
      if (!value.isEmpty()) {
        return value;
      }
      System.out.println("This field cannot be empty.");
    }
  }

  static double average(student s) {
    return s.getAverage();
  }

  static void registerStudent() {
    int id = readInt("Student ID: ");

    if (id <= 0 || students.stream()
        .anyMatch(s -> s.getId() == id)) {
      System.out.println("Invalid or duplicate student ID.");
      return;
    }

    String name = readText("Student name: ");
    int age = readInt("Student age: ");

    if (age <= 0) {
      System.out.println("Age must be greater than zero.");
      return;
    }

    int number = readInt("Number of courses: ");
    if (number <= 0) {
      System.out.println("Enter at least one course.");
      return;
    }

    Map<String, Double> courses = new HashMap<>();

    for (int i = 0; i < number; i++) {
      String course = readText("Course name: ");

      if (courses.containsKey(course)) {
        System.out.println("Course already entered. Try again.");
        i--;
        continue;
      }

      double mark = readMark("Course mark (0-100): ");
      courses.put(course, mark);
    }

    List<String> skills = new ArrayList<>();
    int skillCount = readInt("Number of skills: ");

    for (int i = 0; i < skillCount; i++) {
      String skill = readText("Enter skill: ");
      skills.add(skill);
    }

    student student = new student(id, name, age,
        courses, skills);
    students.add(student);

    System.out.println("Student registered successfully!");
  }

  static boolean checkStudents() {
    if (students.isEmpty()) {
      System.out.println("No students registered yet.");
      return false;
    }
    return true;
  }

  static void displayStudents(List<student> list) {
    if (list.isEmpty()) {
      System.out.println("No matching students found.");
      return;
    }

    list.forEach(System.out::println);
  }

  static void searchById() {
    int id = readInt("Enter student ID: ");

    students.stream()
        .filter(s -> s.getId() == id)
        .findFirst()
        .ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Student not found."));
  }

  static void filterStudents(boolean passing) {
    students.stream()
        .filter(s -> passing
            ? average(s) >= 65
            : average(s) < 65)
        .forEach(System.out::println);
  }

  static void searchByName() {
    String name = readText("Enter student name: ");

    displayStudents(students.stream()
        .filter(s -> s.getstudentName()
            .toLowerCase()
            .contains(name.toLowerCase()))
        .collect(Collectors.toList()));
  }

  static void sortByName() {
    students.stream()
        .sorted(Comparator.comparing(
            student::getstudentName,
            String.CASE_INSENSITIVE_ORDER))
        .forEach(System.out::println);
  }

  static void sortByAverage() {
    students.stream()
        .sorted(Comparator.comparingDouble(
            Main::average).reversed())
        .forEach(System.out::println);
  }

  static void displayAllCourses() {
    students.stream()
        .flatMap(s -> s.getCourses().keySet().stream())
        .sorted()
        .forEach(System.out::println);
  }

  static void displayUniqueCourses() {
    students.stream()
        .flatMap(s -> s.getCourses().keySet().stream())
        .distinct()
        .sorted()
        .forEach(System.out::println);
  }

  static void searchByCourse() {
    String course = readText("Enter course name: ");

    displayStudents(students.stream()
        .filter(s -> s.getCourses().keySet().stream()
            .anyMatch(c -> c.equalsIgnoreCase(course)))
        .collect(Collectors.toList()));
  }

  static void displayUniqueSkills() {
    students.stream()
        .flatMap(s -> s.getSkills().stream())
        .map(String::trim)
        .distinct()
        .sorted()
        .forEach(System.out::println);
  }

  static void groupByCourse() {
    Map<String, List<student>> groups = students.stream()
        .flatMap(s -> s.getCourses().keySet().stream()
            .map(c -> new AbstractMap.SimpleEntry<>(c, s)))
        .collect(Collectors.groupingBy(
            Map.Entry::getKey,
            Collectors.mapping(Map.Entry::getValue,
                Collectors.toList())));

    groups.forEach((course, list) -> {
      System.out.println("\nCourse: " + course);
      list.forEach(s -> System.out.println(
          s.getstudentName()));
    });
  }

  static void performanceReport() {
    students.forEach(s -> {
      DoubleSummaryStatistics stats = s.getCourses().values().stream()
          .mapToDouble(Double::doubleValue)
          .summaryStatistics();

      System.out.println("\nStudent: " + s.getstudentName());
      System.out.println("Average: " + stats.getAverage());
      System.out.println("Highest mark: " + stats.getMax());
      System.out.println("Lowest mark: " + stats.getMin());
      System.out.println("Total marks: " + stats.getSum());
      System.out.println("Number of courses: " + stats.getCount());

      // ADD REDUCE CODE HERE
      double total = s.getCourses().values().stream()
          .reduce(0.0, Double::sum);

      System.out.println("Total using reduce: " + total);
    });

    long passed = students.stream()
        .filter(s -> s.getAverage() >= 65)
        .count();

    System.out.println(
        "\nStudents with average 65 or above: " + passed);
  }

  static void bestStudent() {
    students.stream()
        .max(Comparator.comparingDouble(Main::average))
        .ifPresentOrElse(
            s -> System.out.println("Best student: " + s),
            () -> System.out.println("No students available."));
  }

  static void topThree() {
    students.stream()
        .sorted(Comparator.comparingDouble(
            Main::average).reversed())
        .limit(3)
        .forEach(System.out::println);
  }

  static void applyBonus() {
    students.stream()
        .filter(s -> !s.isBonusApplied())
        .filter(s -> average(s) > 50)
        .forEach(s -> {
          double original = average(s);

          s.getCourses().replaceAll(
              (course, mark) -> Math.min(100, mark + 10));

          s.setBonusApplied(true);

          System.out.println("\nStudent: "
              + s.getstudentName());
          System.out.println("Original average: "
              + original);
          System.out.println("Final average: "
              + average(s));
        });

    System.out.println(
        "Bonus processing completed. Eligible students "
            + "received bonus marks.");
  }

  static void finalRanking() {
    students.stream()
        .sorted(Comparator.comparingDouble(
            Main::average).reversed())
        .forEach(s -> System.out.println(
            s.getstudentName() + " - Average: "
                + average(s)));
  }

  static void showMenu() {
    System.out.println("\n==========================================");
    System.out.println("       STUDENT MANAGEMENT SYSTEM");
    System.out.println("==========================================");
    System.out.println("1. Register New Student");
    System.out.println("2. Display All Students");
    System.out.println("3. Search Student by ID");
    System.out.println("4. Filter Passing Students");
    System.out.println("5. Filter Failing Students");
    System.out.println("6. Search Students by Name");
    System.out.println("7. Sort Students by Name");
    System.out.println("8. Sort Students by Average Marks");
    System.out.println("9. Display All Courses");
    System.out.println("10. Display Unique Courses");
    System.out.println("11. Search Students by Course");
    System.out.println("12. Display Unique Skills");
    System.out.println("13. Group Students by Course");
    System.out.println("14. View Student Performance Statistics");
    System.out.println("15. View Best Performing Student");
    System.out.println("16. View Top 3 Students");
    System.out.println("17. Apply Bonus Marks");
    System.out.println("18. View Final Student Ranking");
    System.out.println("0. Exit");
    System.out.println("==========================================");
  }

  public static void main(String[] args) {
    int choice;

    do {
      showMenu();
      choice = readInt("Enter your choice: ");

      if (choice != 1 && choice != 0 && !checkStudents()) {
        continue;
      }

      switch (choice) {
        case 1 -> registerStudent();
        case 2 -> displayStudents(students);
        case 3 -> searchById();
        case 4 -> filterStudents(true);
        case 5 -> filterStudents(false);
        case 6 -> searchByName();
        case 7 -> sortByName();
        case 8 -> sortByAverage();
        case 9 -> displayAllCourses();
        case 10 -> displayUniqueCourses();
        case 11 -> searchByCourse();
        case 12 -> displayUniqueSkills();
        case 13 -> groupByCourse();
        case 14 -> performanceReport();
        case 15 -> bestStudent();
        case 16 -> topThree();
        case 17 -> applyBonus();
        case 18 -> finalRanking();
        case 0 -> System.out.println("Thank you for using the system!");
        default -> System.out.println("Invalid choice. Try again.");
      }

    } while (choice != 0);

    input.close();
  }
}

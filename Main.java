import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    CourseManager courseSystem = new CourseManager();
    Scanner scanner = new Scanner(System.in);
    String filename = "courses.txt";
    boolean running = true;

    // try to load saved data on startup
    courseSystem.loadFromFile(filename);

    while (running) {
      System.out.println("\n=== Academic Planner ===");
      System.out.println("1. Add Course");
      System.out.println("2. View All Courses");
      System.out.println("3. Search Course by Code");
      System.out.println("4. Compute Total Units");
      System.out.println("5. Save to File");
      System.out.println("6. Load from File");
      System.out.println("7. Exit");
      System.out.print("Choose an option: ");

      int choice = -1;
      try {
        choice = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number.");
        scanner.nextLine(); // discard bad input
        continue;
      }

      // handle the chosen menu option
      switch (choice) {
        case 1:
          System.out.print("Enter Course Code (e.g. COS201): ");
          String courseCode = scanner.nextLine().toUpperCase().trim();
          System.out.print("Enter Course Title: ");
          String courseTitle = scanner.nextLine().trim();

          int units = 0;
          // keep asking until a valid integer is entered
          while (true) {
            System.out.print("Enter Course Units: ");
            try {
              units = scanner.nextInt();
              scanner.nextLine(); // consume leftover newline
              break;
            } catch (InputMismatchException e) {
              System.out.println("Invalid unit value. Please enter an integer.");
              scanner.nextLine(); // discard bad input
            }
          }
          courseSystem.addCourse(new Course(courseCode, courseTitle, units));
          break;
        case 2:
          courseSystem.viewAllCourses();
          break;
        case 3:
          System.out.print("Enter Course Code to search: ");
          String searchCode = scanner.nextLine();
          courseSystem.searchCourse(searchCode);
          break;
        case 4:
          // start the recursion at the first course
          int totalUnits = courseSystem.calculateTotalUnits(0);
          System.out.println("Total Units Enrolled: " + totalUnits);
          break;
        case 5:
          courseSystem.saveToFile(filename);
          break;
        case 6:
          courseSystem.loadFromFile(filename);
          break;
        case 7:
          System.out.println("Exiting program. Goodbye!");
          running = false;
          break;
        default:
          System.out.println("Invalid option. Choose between 1 and 7.");
      }
    }
    scanner.close();
  }
}

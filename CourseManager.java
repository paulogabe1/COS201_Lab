import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseManager {

  private final ArrayList<Course> courseList = new ArrayList<>();

  public void addCourse(Course course) {
    courseList.add(course);
    System.out.println("Course added successfully.");
  }

  public void viewAllCourses() {
    if (courseList.isEmpty()) {
      System.out.println("No courses recorded yet.");
      return;
    }
    System.out.println("\n--- Recorded Courses ---");
    for (Course course : courseList) {
      System.out.println(
        course.getCode() + " - " + course.getTitle() + " (" + course.getUnits() + " units)"
      );
    }
  }

  public void searchCourse(String code) {
    String searchCode = code.trim().toUpperCase();
    for (Course course : courseList) {
      if (course.getCode().toUpperCase().equals(searchCode)) {
        System.out.println(
          "Found: " +
            course.getCode() +
            " - " +
            course.getTitle() +
            " (" +
            course.getUnits() +
            " units)"
        );
        return;
      }
    }
    System.out.println("Course not found.");
  }

  public int calculateTotalUnits(int index) {
    return sumCreditUnitsRecursive(index);
  }

  public int sumCreditUnitsRecursive(int index) {
    if (index >= courseList.size()) {
      return 0;
    }
    return courseList.get(index).getUnits() + sumCreditUnitsRecursive(index + 1);
  }

  public void saveToFile(String filename) {
    try (PrintWriter writer = new PrintWriter(new File(filename))) {
      for (Course course : courseList) {
        writer.println(course.getCode() + "," + course.getTitle() + "," + course.getUnits());
      }
      System.out.println("Courses saved to " + filename);
    } catch (FileNotFoundException e) {
      System.out.println("Error saving file: " + e.getMessage());
    }
  }

  public void loadFromFile(String filename) {
    courseList.clear();
    try (Scanner fileScanner = new Scanner(new File(filename))) {
      while (fileScanner.hasNextLine()) {
        String line = fileScanner.nextLine();
        String[] parts = line.split(",");
        if (parts.length == 3) {
          courseList.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
        }
      }
      System.out.println("Courses loaded from " + filename);
    } catch (FileNotFoundException e) {
      System.out.println("No existing save file found. Starting fresh.");
    } catch (Exception e) {
      System.out.println("Error reading file data.");
    }
  }
}

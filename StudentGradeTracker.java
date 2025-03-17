import java.util.*;

class Student {
    private String name;
    private List<Integer> grades = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public double getAverage() {
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return name + " | Grades: " + grades + " | Average: " + String.format("%.2f", getAverage());
    }
}

public class StudentGradeTracker {
    private static Map<Integer, Student> students = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Student\n2. Add Grade\n3. Display Students\n4. Remove Student\n5. Find Student by ID\n6. Top Student\n7. Sort by Average\n8. Exit");
            System.out.print("Enter choice: ");
            
            int choice = getValidIntInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addGrade();
                    break;
                case 3:
                    displayStudents();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    findStudentById();
                    break;
                case 6:
                    findTopStudent();
                    break;
                case 7:
                    sortStudentsByAverage();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter student ID: ");
        int id = getValidIntInput();
        if (students.containsKey(id)) {
            System.out.println("Student ID already exists. Try again.");
            return;
        }
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        students.put(id, new Student(name));
        System.out.println("Student added successfully.");
    }

    private static void addGrade() {
        System.out.print("Enter student ID: ");
        int id = getValidIntInput();
        if (!students.containsKey(id)) {
            System.out.println("Student ID not found.");
            return;
        }
        System.out.print("Enter grade: ");
        int grade = getValidIntInput();
        students.get(id).addGrade(grade);
        System.out.println("Grade added successfully.");
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            students.forEach((id, student) -> System.out.println("ID: " + id + " | " + student));
        }
    }

    private static void removeStudent() {
        System.out.print("Enter student ID to remove: ");
        int id = getValidIntInput();
        if (students.remove(id) != null) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student ID not found.");
        }
    }

    private static void findStudentById() {
        System.out.print("Enter student ID: ");
        int id = getValidIntInput();
        if (students.containsKey(id)) {
            System.out.println("Student Found: ID: " + id + " | " + students.get(id));
        } else {
            System.out.println("Student ID not found.");
        }
    }

    private static void findTopStudent() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        Student topStudent = Collections.max(students.values(), Comparator.comparingDouble(Student::getAverage));
        System.out.println("Top Student: " + topStudent);
    }

    private static void sortStudentsByAverage() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        List<Map.Entry<Integer, Student>> sortedList = new ArrayList<>(students.entrySet());
        sortedList.sort((a, b) -> Double.compare(b.getValue().getAverage(), a.getValue().getAverage()));

        System.out.println("Students sorted by average grade:");
        sortedList.forEach(entry -> System.out.println("ID: " + entry.getKey() + " | " + entry.getValue()));
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}

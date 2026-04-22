import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WellnessAnalyzer {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Person> people = new ArrayList<>();
        //TODO - fill in the following, to read data from a file, use the data to construct
        // specific Person objects (Student, Teacher, and Staff), and store in a List of Person (polymorphism)
        File file = new File("score.csv");
        Scanner scan = new Scanner (file);  //construct Scanner object using appropriate filename (such as wellness_data.csv)
        scan.nextLine(); // skip header line.

        while (scan.hasNextLine()) { //add appropriate while condition here.
            String line = scan.nextLine(); // code to read the next line from the .csv file
            String[] parts = line.split(","); // code to split the line into an array of String, using commas as delimiters (separators)

            String role = parts[0]; // store the first item in a variable
            String name = parts[1]; // store the second item in a variable
            int steps = Integer.parseInt(parts[2]); // store the third item in a variable
            double sleep = Double.parseDouble(parts[3]); //store the fourth item in a variable - need to do math on this one, so parse as a double
            double water = Double.parseDouble(parts[4]); //store the fifth  item in a variable - need to do math on this one, so parse as a double

            if (role.equals("student")) {
                Student student = new Student(name, steps, sleep, water);
                people.add(student);
            } else if (role.equals("teacher")) {
                Teacher teacher = new Teacher(name, steps, sleep, water);
                people.add(teacher);
            } else if (role.equals("staff")) {
                Staff staff = new Staff(name, steps, sleep, water);
                people.add(staff);
            }
        }

        // TODO:
        // - compute statistics
        // - print results

        ArrayList<Double> wellnessScores = new ArrayList<>();
        for (Person p : people)
        {
            double score = p.calculateWellnessScore();
            wellnessScores.add(score);
        }

        for (int i = 0; i < wellnessScores.size(); i++)
        {
            for (int j = 0; j < wellnessScores.size() - 1; j++)
            {
                if (wellnessScores.get(j) < wellnessScores.get(j + 1))
                {
                    double temp = wellnessScores.get(j);
                    wellnessScores.set(j, wellnessScores.get(j + 1));
                    wellnessScores.set(j + 1, temp);
                }
            }
        }

        double avg = 0;
        for(double d : wellnessScores)
        {
            avg += d;
        }
        double totalAvg = avg/wellnessScores.size();

        double min = wellnessScores.get(wellnessScores.size() - 1);
        double max = wellnessScores.get(0);

        double studAvg = 0;
        double staffAvg = 0;
        double teachAvg = 0;
        int studCount = 0;
        int staffCount = 0;
        int teachCount = 0;
        for (Person p : people)
        {
            if (p.getRole().equals("Student"))
            {
                studAvg += p.calculateWellnessScore();
                studCount += 1;
            }
            else if (p.getRole().equals("Staff"))
            {
                staffAvg += p.calculateWellnessScore();
                staffCount += 1;
            }
            if (p.getRole().equals("Teacher")) {
                teachAvg += p.calculateWellnessScore();
                teachCount += 1;
            }
        }
        double studTotalAvg = studAvg/studCount;
        double staffTotalAvg = staffAvg/staffCount;
        double teachTotalAvg = teachAvg/teachCount;

        double median = 0;
        if (wellnessScores.size() % 2 == 0)
        {
            median = (wellnessScores.get((wellnessScores.size() / 2)) + wellnessScores.get(wellnessScores.size() / 2) + 1) / 2;
        }
        else
        {
            median = wellnessScores.get((wellnessScores.size() / 2) + 1);
        }

        double stdDev = 0;
        for(double d : wellnessScores)
        {
            stdDev += Math.pow(d - totalAvg, 2);
        }
        stdDev = Math.sqrt(stdDev / wellnessScores.size());

        for(Person p : people)
            System.out.println(p.getName() + " (" + p.getRole() + "): " + String.format("%.3f", p.calculateWellnessScore()));

        System.out.println("\nOverall Average: " + String.format("%.3f", totalAvg));
        System.out.println("Median: " + String.format("%.3f", median));
        System.out.println("Standard Deviation: " + String.format("%.3f", stdDev));
        System.out.println("Highest Score: " + String.format("%.3f", max));
        System.out.println("Lowest Score: " + String.format("%.3f", min) + "\n");
        System.out.println("Student Average: " + String.format("%.3f", studTotalAvg));
        System.out.println("Teacher Average: " + String.format("%.3f", teachTotalAvg));
        System.out.println("Staff Average: " + String.format("%.3f", staffTotalAvg));
    }
}


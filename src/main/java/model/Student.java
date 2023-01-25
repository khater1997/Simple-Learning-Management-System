package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Student {
    public static final String delimiter = (",");
    public Student(String id, String name, String grade, String email, String address, String region, String country) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.email = email;
        this.address = address;
        this.region = region;
        this.country = country;
    }

    public String id;
    public String name;
    public String grade;
    public String email;
    public String address;
    public String region;
    public String country;


    public void showStudentData() {
        System.out.println(id + " " + name + " " + grade + " " + email + " " + address + " " + region + " " + country);
    }

    public ArrayList<String> courses = new ArrayList<String>();

    public static Student getStudentByID(ArrayList<Student> students, String userId) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).id.equals(userId)) {
                return students.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Student> getStudent(String csvFile) {
        ArrayList<Student> students = new ArrayList<>();

        try {
            int studentCounter = 0;
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] newStudentData;
            while ((line = br.readLine()) != null) {
                newStudentData = line.split(delimiter);
                if (newStudentData[0].equals("id")) {
                    continue;
                }
                Student student = new Student(newStudentData[0], newStudentData[1], newStudentData[2],
                        newStudentData[3], newStudentData[4], newStudentData[5], newStudentData[6]);
                students.add(student);
                studentCounter++;
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return students;
    }

    public int replaceCourse(String toRemove, String toAdd) {             // to replace courses
        if (courses.contains(toRemove) && !courses.contains(toAdd)) {
            courses.remove(toRemove);
            courses.add(toAdd);
            return 3;                                                 // 3 --> success
        } else {
            return 2;                                                 // 2 --> not exist
        }
    }

    public int enrolledCourse(String enCourse) {                          // to enroll courses

        if (courses.size() < 6 && !courses.contains(enCourse)) {
            courses.add(enCourse);                                     // 2 --> exist
            return 3;
        } else if (courses.size() < 6)
            return 1;                                                  // 1 --> size > 6
        else {
            return 2;                                                 // 3 --> success
        }
    }

    public int unrolledCourse(String enCourse) {                            // to un enroll courses

        if (courses.contains(enCourse)) {
            courses.remove(enCourse);
            return 3;                                                  // 3 --> success
        } else {
            return 2;                                                  // 2 --> not exist
        }
    }
}

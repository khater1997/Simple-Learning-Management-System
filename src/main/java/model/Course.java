package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Course {
    public static final String delimiter = (",");

    public String courseId;
    public String courseName;
    public String instructor;
    public String courseDuration;
    public String courseTime;
    public String location;

    public Course(String courseId, String courseName, String instructor, String courseDuration, String courseTime, String location) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.courseDuration = courseDuration;
        this.courseTime = courseTime;
        this.location = location;
    }

    public void showCourseData() {
        System.out.println(courseId + " " + courseName + " " + instructor + " " + courseDuration + " " + courseTime + " " + location);
    }

    public static ArrayList<Course> getCourses(String courseFile) {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            int courseCounter = 0;
            File file = new File(courseFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            String[] newCourseData;
            while ((line = bufferedReader.readLine()) != null) {
                newCourseData = line.split(delimiter);
                if (newCourseData[0].equals("id")) {
                    continue;
                }
                Course course = new Course(newCourseData[0], newCourseData[1], newCourseData[2],
                        newCourseData[3], newCourseData[4], newCourseData[5]);
                courses.add(course);
                courseCounter++;
            }
            bufferedReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return courses;
    }

    public static void showCourses(ArrayList<Course> courses) {
        for (int i = 0; i < courses.size(); i++) {
            courses.get(i).toString();
        }

    }

    public static Course getCourseById(ArrayList<Course> courses, String courseId) {    // get course details by using (course id)
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).courseId.equals(courseId)) {
                return courses.get(i);
            }
        }
        return null;
    }

}

package view;

import convertor.ConvertTXTToCSV;
import convertor.ConvertXMLToCSV;
import convertor.ShowData;
import model.Course;
import model.Student;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HomePage {
    public static void main(String[] args) throws Exception {
        String studentCSVFile = "outPutResources//StudentData.csv";
        ConvertTXTToCSV.csvConvertor(new File("src/main/resources/StudentData.txt"));
        ArrayList<Student> students = Student.getStudent(studentCSVFile);

        String courseCSVFile = "outPutResources//CourseData.csv";
        ConvertXMLToCSV.csvConvertor("src/main/resources/CourseData.xml","outPutResources//CourseData.csv");
        ArrayList<Course> courses = Course.getCourses(courseCSVFile);

        students.get(0).enrolledCourse(courses.get(0).courseId);
        students.get(0).enrolledCourse(courses.get(1).courseId);
        students.get(0).enrolledCourse(courses.get(2).courseId);
        students.get(0).enrolledCourse(courses.get(3).courseId);
        students.get(1).enrolledCourse(courses.get(1).courseId);
        students.get(1).enrolledCourse(courses.get(3).courseId);
        students.get(1).enrolledCourse(courses.get(5).courseId);
        students.get(2).enrolledCourse(courses.get(0).courseId);
        students.get(2).enrolledCourse(courses.get(2).courseId);
        students.get(2).enrolledCourse(courses.get(4).courseId);
        FileOperation JsonFile = new FileOperation();
        JSONObject jsonObject = JsonFile.jsonFile(students);

        // Application GUI
        System.out.println("Welcome to LMS");
        System.out.println("created by: (Mohamed Khater) - Jan 23 , 2023 ");
        System.out.println("====================================================================================================");
        System.out.println("Home page");
        System.out.println("====================================================================================================");
        System.out.println("Student List:");

        ShowData.showStudentCSV("outPutResources//StudentData.csv");
        System.out.println("----------------------------------------------------------------------------------------------------");


        String studentId;
        Student currentStudent;
        while (true) {
            System.out.print("please select the required student: ");
            Scanner scanner = new Scanner(System.in);
            studentId = scanner.next();
            currentStudent = Student.getStudentByID(students, studentId);
            if (currentStudent == null) {
                System.out.println("Invalid Student ID !");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Home page");
            System.out.println("====================================================================================================");
            System.out.println("Student details page");
            System.out.println("====================================================================================================");
            System.out.print(currentStudent.name + "   ,   " + currentStudent.grade + "    ,    " + currentStudent.email);
            System.out.println();
            FileOperation.SearchByJson(studentId, students, courses);

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("Please choose from the following : ");
            System.out.println("enroll in a course : a");
            System.out.println("un enroll from an existing course : d");
            System.out.println("Replace an existing course : r");
            System.out.println("please select the required action:");
            Scanner sc = new Scanner(System.in);
            String Action = sc.next();
            switch (Action) {
                case "a": {
                    System.out.println("Enrollment page");
                    System.out.println("====================================================================================================");
                    FileOperation.SearchByJson(studentId, students, courses);
                    System.out.println("Please make one of the following:\n");
                    Scanner scanner2 = new Scanner(System.in);
                    String courseId;
                    boolean condition = true;
                    while (condition) {
                        System.out.println("Enter the course id that you want to enroll the student to : \n" +
                                "or Enter b to go back to the home page\n");
                        courseId = scanner2.next();
                        if (courseId.equals("b")) {
                            condition = false;
                        } else {
                            FileOperation.enrollCourse(studentId, courseId, students, courses, jsonObject);
                        }
                    }
                    break;
                }
                case "d": {
                    System.out.println("enroll page");
                    System.out.println("====================================================================================================");
                    FileOperation.SearchByJson(studentId, students, courses);
                    System.out.println("Please make one of the following:\n");
                    Scanner scanner2 = new Scanner(System.in);
                    String CourseId;
                    boolean condition = true;
                    while (condition) {
                        System.out.println("Enter the course id that you want to un enroll the student to : \n" +
                                "or Enter b to go back to the home page\n");
                        CourseId = scanner2.next();
                        if (CourseId.equals("b")) {
                            condition = false;
                        } else {
                            FileOperation.unEnrollCourse(studentId, CourseId, students, jsonObject);
                        }
                    }
                    break;
                }
                case "r": {
                    System.out.println("replacement page");
                    System.out.println("====================================================================================================");
                    FileOperation.SearchByJson(studentId, students, courses);
                    System.out.println("Please make one of the following :\n");
                    Scanner scanner2 = new Scanner(System.in);
                    String courseId1;
                    String courseId2;
                    boolean condition = true;
                    while (true) {
                        System.out.println("Enter the course id that you want to be replaced :");
                        System.out.println("or Enter b to go back to the home page\n");
                        courseId1 = scanner2.next();
                        while (true) {
                            if (courseId1.equals("b")) {
                                condition = false;
                                break;
                            } else if (!Student.getStudentByID(students, studentId).courses.contains(courseId1)) {
                                System.out.println("this student is not enrolled in this course");
                                System.out.println("Enter the course id that you want to be replaced : ");
                                System.out.println("or Enter b to go back to the home page\n");
                                courseId1 = scanner2.next();
                            } else {
                                //success
                                break;
                            }
                        }
                        if (!condition) {
                            break;
                        }
                        System.out.println("Available Courses");
                        Course.getCourseById(courses, courseId1).showCourseData();
                        System.out.println("====================================================================================================");

                        System.out.println("Please enter the required course id to replace :");
                        System.out.println("or Enter b to go back to the home page\n");
                        courseId2 = scanner2.next();
                        while (true) {
                            if (courseId2.equals("b")) {
                                condition = false;
                                break;
                            } else if (Course.getCourseById(courses, courseId2) == null) {
                                System.out.println("this course is not exist");
                                System.out.println("Enter the course id that you want to be replaced");
                                System.out.println("or Enter b to go back to the home page\n");
                                courseId2 = scanner2.next();
                            } else if (Student.getStudentByID(students,studentId).courses.contains(courseId2)) {

                                System.out.println("Available Courses");
                                Course.getCourseById(courses, courseId1).showCourseData();
                                System.out.println("====================================================================================================");

                                System.out.println("the student is already enrolled in this course !");
                                System.out.println("Please enter the required course id to replace :");
                                System.out.println("or Enter b to go back to the home page\n");
                                courseId2 = scanner2.next();

                            } else {
                                break;
                            }
                        }
                        if (!condition) {
                            break;
                        }
                        System.out.println("Available Courses");
                        Course.getCourseById(courses, courseId2).showCourseData();
                        System.out.println("====================================================================================================");

                        FileOperation.replaceCourse(studentId, courseId1, courseId2, students, jsonObject);
                        break;
                    }
                }
            }
        }
    }
}
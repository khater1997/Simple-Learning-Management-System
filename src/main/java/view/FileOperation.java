package view;

import model.Course;
import model.Student;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperation {
    public static JSONObject jsonFile(ArrayList<Student> students) {
        //Creating an object from JSONObject
        JSONObject jsonObject = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonObject.put(students.get(0).id, students.get(0).courses);
        jsonObject.put(students.get(1).id, students.get(1).courses);
        jsonObject.put(students.get(2).id, students.get(1).courses);

        try {
            FileWriter file = new FileWriter("E:/output.json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static void SearchByJson(String userId, ArrayList<Student> students, ArrayList<Course> courses) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try {
            int ID = Integer.parseInt(userId);
            Student current_student = Student.getStudentByID(students, userId);
            if (current_student == null) {
                System.out.println("Invalid Student ID");                    // when entering id out of scope list of id
            } else {
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("Student enrolled courses");
                System.out.println("====================================================================================================");
                try (FileReader reader = new FileReader("E:/output.json")) {     //Read JSON file
                    JSONObject obj = null;
                    try {
                        obj = (JSONObject) jsonParser.parse(reader);
                    } catch (org.json.simple.parser.ParseException e) {
                        throw new RuntimeException(e);
                    }
                    JSONArray studentCourses = (JSONArray) obj.get(userId);
                    if (studentCourses != null) {
                        System.out.println();

                        for (int i = 0; i < (courses.size()); i++) {
                            if (i == studentCourses.size()) {
                                break;
                            }
                            System.out.print(courses.get(Integer.parseInt(studentCourses.get(i).toString()) - 1).courseId + " ,");
                            System.out.print(courses.get(Integer.parseInt(studentCourses.get(i).toString()) - 1).courseName + " ,");
                            System.out.print(courses.get(Integer.parseInt(studentCourses.get(i).toString()) - 1).instructor + ", ");
                            System.out.print(courses.get(Integer.parseInt(studentCourses.get(i).toString()) - 1).courseDuration + ", ");
                            System.out.print(courses.get(Integer.parseInt(studentCourses.get(i).toString()) - 1).courseTime + " ,");
                            System.out.println(courses.get(Integer.parseInt(studentCourses.get(i).toString()) - 1).location);
                        }
                    } else {
                        System.out.println("This student hasn't enrolled in any courses");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid User ID");
        }
    }

    public static void enrollCourse(String studentId, String courseId, ArrayList<Student> students, ArrayList<Course> courses, JSONObject jsonObject) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("E:/output.json")) {     //Read JSON file

            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray studentCourses = (JSONArray) obj.get(studentId);

            if (studentCourses == null) {
                Student current = Student.getStudentByID(students, studentId);
                current.enrolledCourse(courseId);
                jsonObject.put(students.get(Integer.parseInt(studentId) - 1).id, current.courses);
                try {
                    FileWriter file = new FileWriter("E:/output.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (Course.getCourseById(courses, courseId) == null) {
                System.out.println("this course doesn't exist");
            } else if (!studentCourses.contains(courseId)) {
                if (studentCourses.size() > 6) {
                    System.out.println("the student has reached the maximum number of courses");
                } else {
                    Student current = Student.getStudentByID(students, studentId);
                    current.enrolledCourse(courseId);
                    jsonObject.put(students.get(Integer.parseInt(studentId) - 1).id, current.courses);

                    try {
                        FileWriter file = new FileWriter("E:/output.json");
                        file.write(jsonObject.toJSONString());
                        file.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("the student has already enrolled in this course");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unEnrollCourse(String Student_id, String courseId, ArrayList<Student> students, JSONObject jsonObject) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("E:/output.json")) {                 //Read JSON file

            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray studentCourses = (JSONArray) obj.get(Student_id);

            if (studentCourses == null) {
                System.out.println("this student isn't enrolled in any courses yet");
            } else if (studentCourses.contains(courseId)) {
                Student current = Student.getStudentByID(students, Student_id);
                current.unrolledCourse(courseId);
                jsonObject.put(students.get(Integer.parseInt(Student_id) - 1).id, current.courses);

                try {
                    FileWriter file = new FileWriter("E:/output.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println("the student is not enrolled in this course");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replaceCourse(String Student_id, String courseId1, String courseId2, ArrayList<Student> students, JSONObject jsonObject) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("E:/output.json")) {      //Read JSON file

            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray studentCourses = (JSONArray) obj.get(Student_id);             // print student courses
            if (studentCourses.size() == 0) {
                System.out.println("this student isn't enrolled in any courses yet");
            }
            if (studentCourses.contains(courseId1)) {
                Student current = Student.getStudentByID(students, Student_id);
                current.replaceCourse(courseId1, courseId2);
                jsonObject.put(students.get(Integer.parseInt(Student_id) - 1).id, current.courses);

                try {
                    FileWriter file = new FileWriter("E:/output.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println("the student is not enrolled in this course");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
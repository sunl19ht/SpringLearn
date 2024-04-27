package com.sunl19ht.spring6.iocxml.dimap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private Integer sID;
    private String sName;
    private List<Lesson> lessonsList;

    public List<Lesson> getLessonsList() {
        return lessonsList;
    }

    public void setLessonsList(List<Lesson> lessonsList) {
        this.lessonsList = lessonsList;
    }

    public void run() {
        System.out.println("学生编号：" + sID + " 学生姓名：" + sName);
        System.out.println(teacherMap);
        System.out.println(lessonsList);
    }

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    private Map<String, Teacher> teacherMap;

    public Integer getsID() {
        return sID;
    }

    public void setsID(Integer sID) {
        this.sID = sID;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}

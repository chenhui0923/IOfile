package com.example.demo.Iostart.ios5;

import java.io.Serializable;

//Serializable序列话接口
public class Student implements Serializable {

    private static final long serialVersionUID = 6241033478670167703L;
    private int id;
    private String name;//private transient临时的不参与序列化 persistance持久的
    private int age;
    private Double score;
    private Clazz clazz;

    public Student(){

    }
    public Student(int id,String name, int age,Double score){
        this.id=id;
        this.name=name;
        this.age= age;
        this.score=score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", clazz=" + clazz +
                '}';
    }
}

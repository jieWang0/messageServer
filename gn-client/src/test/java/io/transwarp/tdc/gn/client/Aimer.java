package io.transwarp.tdc.gn.client;

/**
 * 18-2-9 created by zado
 */
public class Aimer {
    private String name;

    private int age;

    public Aimer() {
    }

    public Aimer(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "Aimer{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}

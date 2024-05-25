package app;

public class Persona {
    String name;
    int age;
    String background;
    String needs;
    String goals;
    public Persona(String name, int age, String background, String needs, String goals){
        this.name = name;
        this.age = age;
        this.background = background;
        this.needs = needs;
        this.goals = goals;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getBackground() {
        return background;
    }
    public String getNeeds() {
        return needs;
    }
    public String getGoals() {
        return goals;
    }
}

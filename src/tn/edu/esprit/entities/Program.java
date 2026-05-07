package tn.edu.esprit.entities;





public class Program {

    private int id;
    private String name;
    private String level;

    public Program() {}

    public Program(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public Program(int id, String name, String level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    @Override
    public String toString() {
        return "Program{" + "name=" + name + ", level=" + level + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Program other = (Program) obj;
        return this.id == other.id;
    }
    
}
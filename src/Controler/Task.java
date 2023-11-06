package Controler;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean finished;

    public Task() {
    }

    public Task(String title, String description, boolean finished){
        this.title = title;
        this.description = description;
        this.finished = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getStatus() {
        return isFinished() ? "finished" : "not finished yet";
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", finished=" + finished +
                '}';
    }
}

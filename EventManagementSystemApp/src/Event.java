public class Event {
    private String title;
    private String description;
    private String date;
    private String time;
    private String code;

    public Event(String title, String description, String date, String time, String code) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.code=code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    public String getCode(){
      return code;
    }
}
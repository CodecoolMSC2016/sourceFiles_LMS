package curriculum;

/**
 * Created by mudzso on 2017.04.11..
 */
public abstract class CurrciculumData {

    private String title;
    private String text;
    private boolean published;

    public CurrciculumData(String title, String text, boolean published) {
        this.title = title;
        this.text = text;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(){
        published = !published;
    }
}

package curriculum;

import java.util.UUID;

/**
 * Created by mudzso on 2017.04.11..
 */
public abstract class CurrciculumData {

    private String title;
    private String text;
    private boolean published;
    private String id;

    public CurrciculumData(String title, String text, boolean published) {
        this.title = title;
        this.text = text;
        this.published = published;
        id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }

    public void setPublished(){
        published = !published;
    }
}

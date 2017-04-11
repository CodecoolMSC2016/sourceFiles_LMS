package curriculum;

/**
 * Created by mudzso on 2017.04.11..
 */
public class Assigment extends CurrciculumData {

    private int maxScore;

    public Assigment(String title, String text, boolean published, int maxScore) {
        super(title, text, published);
        this.maxScore = maxScore;
    }

    public int getMaxScore() {
        return maxScore;
    }
}

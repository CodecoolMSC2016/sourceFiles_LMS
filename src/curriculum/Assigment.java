package curriculum;

/**
 * Created by mudzso on 2017.04.11..
 */
public class Assigment extends CurrciculumData {

    private int maxScore;

    public Assigment(String title, String text, boolean published, int maxScore, int index,String id) {
        super(title, text, published, index,id);
        this.maxScore = maxScore;
    }

    public int getMaxScore() {
        return maxScore;
    }
}

public class Word {
    private String word_target;
    private String word_explain;
    //constructor

    public Word(String word_target, String word_explain) {
        //trim + toLowerCase cho de xu li'
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word() {
        this("", "");
    }

    //getter and setter
    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}

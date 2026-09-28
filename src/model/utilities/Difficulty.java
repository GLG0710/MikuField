package model.utilities;

public enum Difficulty {
    EASY(0.12345679),
    MEDIUM(0.15625),
    HARD(0.20625);

    public final double proportion;

    private Difficulty(double proportion) {
        this.proportion = proportion;
    }
}

package ai;

public enum RotationType {
    POSITIVE(1), NEGATIVE(-1), NONE(0);

    public final int signal;
    RotationType(int signal){
        this.signal = signal;
    }

    public static RotationType fromInteger(int x){
        switch (x) {
            case 1:
                return POSITIVE;
            case -1:
                return NEGATIVE;
            case 0:
                return NONE;
        }
        return null;
    }
}

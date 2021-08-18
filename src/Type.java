package cs2030.simulator;

public enum Type {
    ARRIVAL(0),
    WAIT(1),
    SERVE(2),
    LEAVE(3),
    DONE(4),
    REST(5),
    DONEREST(6);

    private final int id;

    Type(int id) {
        this.id = id;
    }
}

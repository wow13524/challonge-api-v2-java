package main.java;

public class ChallongeObject {
    public final String id, type;
    ChallongeObject(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ChallongeObject)) {
            return false;
        }
        ChallongeObject o = (ChallongeObject)other;
        return this.id.equals(o.id) && this.type.equals(o.type);
    }

    @Override
    public String toString() {
        return String.format(
            "ChallongeObject[id=%s, type=%s]",
            this.id,
            this.type
        );
    }
}

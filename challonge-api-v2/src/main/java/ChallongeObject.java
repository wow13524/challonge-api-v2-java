package main.java;

public class ChallongeObject {
    private final String id;
    private final ChallongeObjectType type;
    ChallongeObject(String id, String type) {
        this.id = id;
        this.type =
        EnumUtils.valueFromString(ChallongeObjectType.class, type);
    }

    public String getId() {
        return this.id;
    }

    public ChallongeObjectType getType() {
        return this.type;
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
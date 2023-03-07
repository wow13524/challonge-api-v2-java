package main.java;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

final class ImmutableMap<T, U> extends AbstractMap<T, U> {
    private final Set<Entry<T, U>> entrySet;

    @SafeVarargs
    public ImmutableMap(Entry<T, U>... entries) {
        this.entrySet = new HashSet<Entry<T, U>>(
            Arrays.stream(entries)
            .filter(Objects::nonNull)
            .toList()
        );
    }

    public Set<Entry<T, U>> entrySet() {
        return this.entrySet;
    }
}
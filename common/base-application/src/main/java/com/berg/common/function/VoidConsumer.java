package com.berg.common.function;

import java.util.Objects;

@FunctionalInterface
public interface VoidConsumer {

    void accept();

    default VoidConsumer andThen(VoidConsumer after) {
        Objects.requireNonNull(after);
        return () -> { accept(); after.accept(); };
    }
}

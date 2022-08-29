package com.anima.basic.boot.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pair<A, B> {
    private A first;
    private B second;

    public static <A, B> Pair<A, B> of() {
        return new Pair<>();
    }

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<>(first, second);
    }
}

package com.berg.vo.common;

import lombok.Data;

@Data
public class KeyValueVo<K,V> {

    K key;

    V value;
}

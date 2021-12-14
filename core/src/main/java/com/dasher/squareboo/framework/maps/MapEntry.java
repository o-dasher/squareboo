package com.dasher.squareboo.framework.maps;

import com.badlogic.gdx.utils.ObjectMap;

public class MapEntry<K, V> extends ObjectMap.Entry<K, V> {
    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

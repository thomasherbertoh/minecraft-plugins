package me.Herbert.Thomas.MinecraftPlugins.TreeFeller.Utils;

import org.bukkit.Location;

public class IntegerLocationPair implements Comparable<IntegerLocationPair> {
    
	Integer key;
	Location value;

    public IntegerLocationPair(Integer key, Location value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(IntegerLocationPair o) {
        return key - o.key;
    }

    public int getKey() {
        return this.key;
    }

    public Location getValue() {
        return this.value;
    }
}
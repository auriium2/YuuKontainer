package me.aurium.tick.full;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BullshitUtils {

    //shitty method i copied from deps, this exists until i replace testcontainers with docker
    public static void bullshit() {
        try {
            Class<?> clazz = Class.forName("java.lang.ApplicationShutdownHooks");
            Field field = clazz.getDeclaredField("hooks");
            field.setAccessible(true);
            Map<Thread, Thread> hooks = (Map<Thread, Thread>) field.get(null);
            // Need to create a new map or else we'll get CMEs
            Map<Thread, Thread> hookMap = new ConcurrentHashMap<>();
            hooks.forEach(hookMap::put);
            hookMap.forEach((thread, hook) -> {
                if (thread.getThreadGroup().getName().toLowerCase().contains("testcontainers")) {
                    Runtime.getRuntime().removeShutdownHook(hook);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("pwned", e);
        }
    }

}

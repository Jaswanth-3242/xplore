package com.example.shop;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.util.LinkedHashMap;
import java.util.Map;

@Component @SessionScope
public class Cart {
    private final Map<Long, Integer> items = new LinkedHashMap<>();
    public void add(Long id) { items.merge(id, 1, Integer::sum); }
    public void remove(Long id) { items.remove(id); }
    public void clear() { items.clear(); }
    public Map<Long, Integer> getItems() { return items; }
    public int count() { return items.values().stream().mapToInt(Integer::intValue).sum(); }
}

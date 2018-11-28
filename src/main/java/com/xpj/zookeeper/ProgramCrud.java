package com.xpj.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProgramCrud implements BaseCrud {

    Logger logger = LoggerFactory.getLogger(ProgramCrud.class);

    private volatile ConcurrentHashMap<String, String> properties = new ConcurrentHashMap<>();


    @Override
    public void init() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void add(String key, String value) {

    }

    @Override
    public void update(String key, String value) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public Map<String, String> getAll() {
        return null;
    }
}

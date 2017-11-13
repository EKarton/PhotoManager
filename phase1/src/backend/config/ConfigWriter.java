package backend.config;

import backend.MapRepository;

public interface ConfigWriter {
    public void write(MapRepository map);
}

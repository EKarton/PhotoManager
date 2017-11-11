package backend.config;

import backend.MapRepository;

public interface IConfigWriter {
    public void write(MapRepository map);
}

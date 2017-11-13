package backend.config;

import backend.MapRepository;

import java.io.Reader;

public class SerializableMapRepoReader implements ConfigReader {

    public SerializableMapRepoReader(Reader reader){

    }

    @Override
    public MapRepository getMappings() {
        return null;
    }
}

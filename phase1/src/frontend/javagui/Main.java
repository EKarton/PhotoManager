package frontend.javagui;

import backend.FastMapRepository;
import backend.MapRepository;
import backend.Picture;
import backend.Tag;

public class Main {
    public static void main(String[] args){
        MapRepository<Picture, Tag> repo = new FastMapRepository<>();
        Picture picture = new Picture("wasdwasd");
        Tag tag = new Tag("Kimmy");
        repo.addItemWithTag(picture, tag);
        repo.addTagToItem(picture, tag);
        System.out.println(repo);
    }
}

package tests;

import backend.BiDirectionalMap;
import backend.Picture;
import backend.PictureManager;
import backend.Tag;

import java.io.IOException;
import java.util.*;

public class MainTester {
    public static void main(String[] args) throws IOException {
        PictureManager manager = new PictureManager("C:\\Users\\Emilio K\\Desktop\\New folder");
        BiDirectionalMap<Picture, Tag> map = manager.getMap();

        List<Picture> pictures = map.getKeys();

        Picture kitten1 = pictures.get(1);
        Picture kittens2 = pictures.get(5);
        Tag tag = new Tag("Kittens");
        map.addValueToKey(kitten1, tag);
        map.addValueToKey(kittens2, tag);

        System.out.println(map);
    }
}

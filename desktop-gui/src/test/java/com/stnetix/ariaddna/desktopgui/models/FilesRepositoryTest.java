package com.stnetix.ariaddna.desktopgui.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Paths;

/**
 * @autor slonikmak on 12.04.2017.
 */
public class FilesRepositoryTest {
    private static FilesRepository repository;

    @BeforeAll
    public static void setUp(){
        repository = new FilesRepository();
    }

    @Test
    public void simpleTest(){
        repository = new FilesRepository();
        Assertions.assertEquals(Paths.get("/root"), repository.getCurrentParent().getPath());
    }

    @Test
    public void currentFilesTest(){
        repository.setCurrentRoot(Paths.get("/root/Docs"));
        Assertions.assertEquals(2, repository.getCurrentFiles().size());
    }

    @Test
    public void getCurrentFilesAndGetRootPathTest(){
        repository.setCurrentRoot(Paths.get("/root"));
        Assertions.assertEquals(Paths.get("/root"), repository.getCurrentFiles().get(0).getParentPath());
    }

    @Test
    public void getChildrenTest(){
        Assertions.assertEquals(3, repository.getChildren(repository.getFileByPath(Paths.get("/root")).get()).size());
    }

    @Test
    public void getFileByPathTest(){
        Assertions.assertTrue(repository.getFileByPath(Paths.get("/root")).isPresent());
        Assertions.assertFalse(repository.getFileByPath(Paths.get("/rooot")).isPresent());
    }


}
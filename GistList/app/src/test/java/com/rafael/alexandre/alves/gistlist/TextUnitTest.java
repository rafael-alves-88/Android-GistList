package com.rafael.alexandre.alves.gistlist;

import android.test.suitebuilder.annotation.SmallTest;

import com.rafael.alexandre.alves.gistlist.controller.GistController;
import com.rafael.alexandre.alves.gistlist.utils.Utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TextUnitTest {

    @Test
    @SmallTest
    public void testGetIDByURL() {
        String url = "";
        String expectedResult = "";

        GistController gistController = new GistController();
        assertEquals(expectedResult, gistController.getGistIDByURL(url));
    }

    @Test
    @SmallTest
    public void testCheckText() {
        // 1- Correct Text
        String text = "Text";
        String expectedResult = "Text";

        assertEquals(expectedResult, Utils.checkText(text));


        // 2- Empty Text
        text = "";
        expectedResult = "N/A";

        assertEquals(expectedResult, Utils.checkText(text));
    }
}

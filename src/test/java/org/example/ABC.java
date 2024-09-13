package org.example;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

import java.util.ArrayList;

public class ABC {
    @Test
    void test(){
        Playwright playwright = Playwright.create();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments).setSlowMo(50));
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page page = browserContext.newPage();
        page.navigate("http://playwright.dev");
        System.out.println(page.title());
        page.close();
        playwright.close();
    }
}
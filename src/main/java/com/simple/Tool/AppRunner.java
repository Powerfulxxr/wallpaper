package com.simple.Tool;

import com.simple.service.Wallpaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private Wallpaper wallpaper;
    @Override
    public void run(ApplicationArguments args) {
        wallpaper.load();
    }

}

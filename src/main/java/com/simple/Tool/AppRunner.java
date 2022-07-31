package com.simple.Tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Value("${wallpaper.img.dir}")
    private String imgDir;

    @Value("${wallpaper.img.switch.seconds}")
    private int switchSeconds;

    @Override
    public void run(ApplicationArguments args) {
        FrameUtils.callImageFrame(imgDir,switchSeconds);
    }
}

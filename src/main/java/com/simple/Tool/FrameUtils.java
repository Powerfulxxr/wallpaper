package com.simple.Tool;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import static com.sun.jna.platform.win32.WinUser.SMTO_NORMAL;
import static com.sun.jna.platform.win32.WinUser.SW_HIDE;

public class FrameUtils {

    private static WinDef.HWND hide;

    public static void callImageFrame(String imgDir, int switchSeconds) {
        System.out.println("**** 加载图片路径为:"+imgDir+",切换图片间隔时间为"+switchSeconds+"秒  ****");
        LinkedList<BufferedImage> bufferedImages = initImage(imgDir);
        ImageFrame imageFrame = new ImageFrame(bufferedImages, switchSeconds);
        CompletableFuture.runAsync(() -> imageFrame.run());
        sleep();
        callDLL();

    }

    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static LinkedList<BufferedImage> initImage(String imgDir) {
        LinkedList<BufferedImage> images = new LinkedList<>();
        try {
            Files.walkFileTree(Paths.get(imgDir), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    images.add(loadImage(file));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return images;
    }

    private static BufferedImage loadImage(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            BufferedImage image = ImageIO.read(is);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void callDLL() {
        User32 user32 = User32.INSTANCE;
        // 查找 图标 背景窗体
        WinDef.HWND windowHandle = user32.FindWindow("Progman", null);
        // 利用WIN10切换壁纸BUG 把图标与壁纸拆分开来
        user32.SendMessageTimeout(windowHandle, 0x052c, null, null, SMTO_NORMAL,
                0x3e8, null);
        // 寻找原来的壁纸层
        user32.EnumWindows((hWnd, data) -> {
            WinDef.HWND defview = user32.FindWindowEx(hWnd, null, "SHELLDLL_DefView", null);
            if (defview != null) {
                hide = user32.FindWindowEx(null, hWnd, "WorkerW", null);
            }
            return true;
        }, null);
        // 隐藏原来的壁纸层
        user32.ShowWindow(hide, SW_HIDE);
        // 查找 自己要作为壁纸的窗体
        WinDef.HWND hwnd = user32.FindWindow(null, "BG_FRAME");
        // 把他添加到图标层后面
        user32.SetParent(hwnd, windowHandle);
    }
}

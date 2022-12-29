package net.lipama.launch;

import java.net.MalformedURLException;
import java.io.IOException;
import java.util.Locale;
import javax.swing.*;
import java.io.File;
import java.net.URL;

@SuppressWarnings("all")
public class Launch {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        int option = JOptionPane.showOptionDialog(
                null,
                "To install Athens Client you need to put it in your mods folder and run Fabric for latest Minecraft version.",
                "Athens Client",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new String[]{"How To Install", "Open Mods Folder"},
                null
        );

        switch(option) {
            case 0 -> getOS().open("https://www.youtube.com/watch?v=9L0KKSfeG1g");
            case 1 -> {
                String path = switch(getOS()) {
                    case WINDOWS -> System.getenv("AppData") + "/.minecraft/mods";
                    case OSX -> System.getProperty("user.home") + "/Library/Application Support/minecraft/mods";
                    default -> System.getProperty("user.home") + "/.minecraft";
                };
                File mods = new File(path);
                if(!mods.exists()) mods.mkdirs();
                getOS().open(mods);
            }
        }
    }

    private static OS getOS() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if(os.contains("linux") || os.contains("unix")) return OS.LINUX;
        if(os.contains("mac")) return OS.OSX;
        if(os.contains("win")) return OS.WINDOWS;
        return OS.UNKNOWN;
    }

    enum OS {
        LINUX,
        WINDOWS {
            @Override
            protected String[] getURLOpenCommand(URL url) {
                return new String[] { "rundll32", "url.dll,FileProtocolHandler", url.toString() };
            }
        },
        OSX {
            @Override
            protected String[] getURLOpenCommand(URL url) {
                return new String[] { "open", url.toString() };
            }
        },
        UNKNOWN;
        public void open(URL url) {
            try {
                Runtime.getRuntime().exec(getURLOpenCommand(url));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        public void open(String url) {
            try {
                open(new URL(url));
            } catch(MalformedURLException e) {
                e.printStackTrace();
            }
        }
        public void open(File file) {
            try {
                open(file.toURI().toURL());
            } catch(MalformedURLException e) {
                e.printStackTrace();
            }
        }
        protected String[] getURLOpenCommand(URL url) {
            String string = url.toString();
            if("file".equals(url.getProtocol())) {
                string = string.replace("file:", "file://");
            }
            return new String[] { "xdg-open", string };
        }
    }
}

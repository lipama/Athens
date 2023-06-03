package net.lipama.athens.utils;

import net.lipama.athens.Athens;

import java.util.function.*;
import java.util.*;
import java.io.*;

public class SaveUtils {
    private static final File modulesFile = new File(Athens.FOLDER, "modules.config");
    private static final File globalFile = new File(Athens.FOLDER, "global.config");
    public static void saveState(SavableData data, byte[] save) { data.save(save); }

    public static Loader getLoader(SavableData data) {
        return data.load();
    }
    public interface StateParsable {
        void saveCurrentStateToStringViaBuilder(SaveBuilder saveBuilder);
        void updateInstanceSettingsViaLoader(Loader loader);
    }
    public interface StringParsable {
        String asString();
        StringParsable fromString(String string);
    }
    @SuppressWarnings("UnusedReturnValue")
    public static final class SaveBuilder {
        private final ArrayList<Byte> res;
        public SaveBuilder() {
            this.res = new ArrayList<>();
        }
        public <T extends StringParsable> SaveBuilder addParsableLine(String key, T value) {
            return addLine(key, value.asString());
        }
        public <T> SaveBuilder addLine$(String key, T value, Function<T, String> f) {
            return addLine(key, f.apply(value));
        }
        public <T> SaveBuilder addLine(String key, T value) {
            ArrayList<Byte> kvRes = new ArrayList<>();
            byte[] kv = "%s=%s\n".formatted(key,value).getBytes();
            for(byte bt : kv) {
                kvRes.add(bt);
            }
            res.addAll(kvRes);
            return this;
        }
        public byte[] build() {
            byte[] result = new byte[this.res.size()];
            for(int i = 0; i < this.res.size(); i++) {
                result[i] = this.res.get(i);
            }
            return result;
        }
    }
    public static final class SavableData {
        public static final SavableData GLOBAL = new SavableData(globalFile);
        public static final SavableData MODULES = new SavableData(modulesFile);
        public static SavableData MODULE(String id) {
            return new SavableData(
                new File(Athens.FOLDER, "modules."+id+".config")
            );
        }
        private final File saveFile;
        SavableData(File file){
            this.saveFile = file;
        }
        @SuppressWarnings("ResultOfMethodCallIgnored")
        public void save(byte[] data) {
            try {
                if(!this.saveFile.exists()) {
                    this.saveFile.createNewFile();
                }
                var fos = new FileOutputStream(this.saveFile, false);
                fos.write(data);
                fos.close();
            } catch(IOException e) {
                Athens.LOG.error("FAILED TO SAVE STATE FOR " + this.saveFile.getName());
            }
        }
        @SuppressWarnings("ResultOfMethodCallIgnored")
        public Loader load() {
            try {
                if(!this.saveFile.exists()) {
                    this.saveFile.createNewFile();
                }
                var fis = new FileInputStream(this.saveFile);
                var properties = new Properties();
                properties.load(fis);
                fis.close();
                return new Loader(properties);
            } catch(IOException e) {
                Athens.LOG.error("FAILED TO LOAD STATE FOR " + this.saveFile.getName());
                return new Loader(null);
            }
        }
    }
    @SuppressWarnings("unused")
    public static final class Loader {
        private final Properties properties;
        private Loader(Properties properties) {
            this.properties = properties;
        }
        public String String$(String id) {
            if(properties == null) return "";
            return properties.getProperty(id);
        }
        public boolean bool$(String id) {
            return Boolean.parseBoolean(String$(id));
        }
        public int int$(String id) {
            try {
                return Integer.parseInt(String$(id));
            } catch (Exception ignored) {
                return 0;
            }
        }
        public double double$(String id) {
            try {
                return Double.parseDouble(String$(id));
            } catch (Exception ignored) {
                return 0.0;
            }
        }
        public float float$(String id) {
            try {
                return Float.parseFloat(String$(id));
            } catch (Exception ignored) {
                return 0;
            }
        }
        public char char$(String id) {
            return String$(id).charAt(0);
        }
        public <T> T $(String id, Function<String, T> f) {
            return f.apply(id);
        }
    }
}

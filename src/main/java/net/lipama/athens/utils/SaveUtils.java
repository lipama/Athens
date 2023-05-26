package net.lipama.athens.utils;

import net.lipama.athens.Athens;

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
    public static final class Loader {
        private final Properties properties;
        private Loader(Properties properties) {
            this.properties = properties;
        }
        public String loadS(String id) {
            if(properties == null) return null;
            return properties.getProperty(id);
        }
        public boolean LoadB(String id) {
            return Boolean.parseBoolean(loadS(id));
        }
        public int loadI(String id) {
            return Integer.parseInt(loadS(id));
        }
    }
}

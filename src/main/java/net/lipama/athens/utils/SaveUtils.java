package net.lipama.athens.utils;

import net.lipama.athens.AthensClient;

import java.util.*;
import java.io.*;

@SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
public class SaveUtils {
    private static final File moduleFile = new File(AthensClient.FOLDER, "module.config");
    private static final File globalFile = new File(AthensClient.FOLDER, "global.config");
    public static void saveState(SavableData data, byte[] save) { data.save(save); }

    public static boolean loadState(SavableData data, String id) {
        return data.load(id);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static final class SaveBuilder {
        private final ArrayList<Byte> res;
        public SaveBuilder() { this.res = new ArrayList<>(); }
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

    public enum SavableData {
        GLOBAL(globalFile,"Global Config"),
        MODULE(moduleFile, "Modules");
        private final File saveFile;
        private final String name;
        SavableData(File file, String name){
            this.saveFile = file;
            this.name = name;
        }

        public void save(byte[] data) {
            try {
                var fos = new FileOutputStream(this.saveFile, false);
                switch(this) {
                    case MODULE -> {
                        fos.write("# Athens Module Settings\n".getBytes());
                    }
                    case GLOBAL -> {
                        fos.write("# Athens Global Settings\n".getBytes());
                    }
                }
                fos.write(data);
                fos.close();
            } catch(IOException e) {
                AthensClient.LOG.error("FAILED TO SAVE STATE FOR " + this.name);
            }
        }

        public boolean load(String id) {
            try {
                var fis = new FileInputStream(this.saveFile);
                var properties = new Properties();
                properties.load(fis);
                boolean enabled = Boolean.parseBoolean(properties.getProperty(id));
                fis.close();
                return enabled;
            } catch(IOException e) {
                AthensClient.LOG.error("FAILED TO LOAD STATE FOR " + this.name + ", KEY " + id);
                return false;
            }
        }
    }
}

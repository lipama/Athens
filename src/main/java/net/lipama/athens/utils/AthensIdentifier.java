package net.lipama.athens.utils;

import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import net.lipama.athens.Athens;

@SuppressWarnings("all")
public class AthensIdentifier extends Identifier {
    public AthensIdentifier(String path) {
        super(Athens.MOD_ID, path);
    }

    public static Identifier texture(String name) {
        return new AthensIdentifier("textures/"+ name);
    }

    public static Resource loadTexture(String name) {
        return Athens.MC.getResourceManager().getResource(texture(name)).get();
    }
}

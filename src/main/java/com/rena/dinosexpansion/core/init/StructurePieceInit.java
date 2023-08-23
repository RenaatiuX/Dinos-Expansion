package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.common.world.structure.structures.vulcano.VulcanoPiece;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.Locale;

public class StructurePieceInit {

    public static final IStructurePieceType VULCANO_PIECE = register(VulcanoPiece::new, "vulcano");
    public static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}

package com.rena.dinosexpansion.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class TameCommand {

    public static final String TAME_SUCCESS = "command." + DinosExpansion.MOD_ID + ".tame_success";
    public static final String TAME_NO_DINOSAUR = "command." + DinosExpansion.MOD_ID + ".tame_no_dinosaur";
    public static final String TAME_NO_HIT = "command." + DinosExpansion.MOD_ID + ".tame_no_hit";

    public TameCommand(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("tame").executes(TameCommand::tameRayTracedEntity).requires(s -> s.hasPermissionLevel(2)));
    }

    private static int tameRayTracedEntity(CommandContext<CommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = source.getSource().asPlayer();
        RayTraceResult result = Minecraft.getInstance().objectMouseOver;
        if (result != null && result.getType() == RayTraceResult.Type.ENTITY){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) Minecraft.getInstance().objectMouseOver;
            Entity traced = entityRayTraceResult.getEntity();
            if (traced instanceof Dinosaur){
                Dinosaur dinosaur = (Dinosaur) traced;
                dinosaur.setKnockedOutBy(player);
                dinosaur.onKnockoutTaming();
                System.out.println(dinosaur.isOwner(player));
                System.out.println(dinosaur.isTamed());
                source.getSource().sendFeedback(new TranslationTextComponent(TAME_SUCCESS, dinosaur.getType().getName()), true);
            }else
                source.getSource().sendFeedback(new TranslationTextComponent(TAME_NO_DINOSAUR, traced.getType().getName()), true);

        }else {
            source.getSource().sendFeedback(new TranslationTextComponent(TAME_NO_HIT), true);
        }

        return 0;
    }

}

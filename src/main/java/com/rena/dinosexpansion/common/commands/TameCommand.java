package com.rena.dinosexpansion.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class TameCommand {

    public static final String TAME_SUCCESS = "command." + DinosExpansion.MOD_ID + ".tame_success";
    public static final String TAME_NO_DINOSAUR = "command." + DinosExpansion.MOD_ID + ".tame_no_dinosaur";
    public static final String TAME_NO_HIT = "command." + DinosExpansion.MOD_ID + ".tame_no_hit";

    public TameCommand(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("tame").executes(TameCommand::tameRayTracedEntity).requires(s -> s.hasPermissionLevel(2)));
    }

    private static int tameRayTracedEntity(CommandContext<CommandSource> source) throws CommandSyntaxException {
        PlayerEntity player = source.getSource().asPlayer();
        EntityRayTraceResult result = rayTraceEntities(source.getSource());
        if (result != null && result.getType() == RayTraceResult.Type.ENTITY){
            Entity traced = result.getEntity();
            if (traced instanceof Dinosaur){
                Dinosaur dinosaur = (Dinosaur) traced;
                dinosaur.setKnockedOutBy(player);
                dinosaur.onKnockoutTaming();

                source.getSource().sendFeedback(new TranslationTextComponent(TAME_SUCCESS, dinosaur.getType().getName()), true);
            }else
                source.getSource().sendFeedback(new TranslationTextComponent(TAME_NO_DINOSAUR, traced.getType().getName()), true);

        }else {
            source.getSource().sendFeedback(new TranslationTextComponent(TAME_NO_HIT), true);
        }

        return 1;
    }

    protected static EntityRayTraceResult rayTraceEntities(CommandSource source) throws CommandSyntaxException {
        PlayerEntity player = source.asPlayer();
        Vector3d vector3d = player.getEyePosition(0f);
        Vector3d vector3d1 = player.getLook(1.0F);
        double d0 = 10D;
        Vector3d vector3d2 = vector3d.add(vector3d1.x * d0, vector3d1.y * d0, vector3d1.z * d0);
        AxisAlignedBB axisalignedbb = player.getBoundingBox().expand(vector3d1.scale(d0)).grow(1.0D, 1.0D, 1.0D);
        EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(player, vector3d, vector3d2, axisalignedbb, (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith(), d0);
        return entityraytraceresult;
    }

}

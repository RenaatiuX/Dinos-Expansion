package com.rena.dinosexpansion.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.entity.ParapuzosiaRenderer;
import com.rena.dinosexpansion.client.renderer.layer.DinosaurShoulderRidingLayer;
import com.rena.dinosexpansion.client.util.RenderingUtils;
import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.events.ServerForgeEvents;
import com.rena.dinosexpansion.common.item.ZoomItem;
import com.rena.dinosexpansion.core.init.EffectInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.command.CommandSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;
import java.security.PublicKey;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = DinosExpansion.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    public static final ResourceLocation FREEZE_OVERLAY = DinosExpansion.modLoc("textures/overlay/ice_overlay.png");
    public static final ResourceLocation FROZEN_HEARTS = DinosExpansion.modLoc("textures/overlay/frozen_heart.png");

    public static final ResourceLocation ZOOM_OVERLAY = DinosExpansion.modLoc("textures/overlay/scope_overlay.png");
    public static final Random rand = new Random();
    protected static boolean hasAddedLayer = false;
    private static boolean hasReleasedJumpKey;
    private static int jumpCount = 0;

    @SubscribeEvent
    public static final void renderEntityNameAndLevel(RenderNameplateEvent event) {
        if (event.getEntity() instanceof Dinosaur && DinosExpansionConfig.SHOW_LEVEL_ABOVE_HEAD.get()) {
            event.setResult(Event.Result.DENY);
            Dinosaur dino = (Dinosaur) event.getEntity();
            if (event.getEntityRenderer() instanceof ParapuzosiaRenderer) {
                event.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(180));
            }
            if (dino.hasCustomName()) {
                IFormattableTextComponent name = dino.getDisplayName().deepCopy().appendString("\n").appendString("" + dino.getLevel());
                if (Minecraft.getInstance().player != null && (dino.isKnockedOutBy(Minecraft.getInstance().player) || dino.isOwner(Minecraft.getInstance().player)))
                    name = name.mergeStyle(TextFormatting.GREEN);
                renderNameWithLevel(dino, name, event.getMatrixStack(),
                        event.getRenderTypeBuffer(), event.getPackedLight(), event.getEntityRenderer().getRenderManager());
            } else {
                IFormattableTextComponent name = new StringTextComponent("" + dino.getLevel());
                if (Minecraft.getInstance().player != null && (dino.isKnockedOutBy(Minecraft.getInstance().player) || dino.isOwner(Minecraft.getInstance().player)))
                    name = name.mergeStyle(TextFormatting.GREEN);
                renderNameWithLevel(dino, name, event.getMatrixStack(), event.getRenderTypeBuffer(), event.getPackedLight(), event.getEntityRenderer().getRenderManager());
            }
        }
    }

    private static void renderNameWithLevel(Dinosaur dino, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, EntityRendererManager manager) {
        double d0 = manager.squareDistanceTo(dino);
        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(dino, d0)) {
            boolean flag = !dino.isDiscrete();
            float f = dino.getHeight() + 0.5F;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double) f, 0.0D);
            matrixStackIn.rotate(manager.getCameraOrientation());
            matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            int j = (int) (f1 * 255.0F) << 24;
            FontRenderer fontrenderer = manager.getFontRenderer();
            float f2 = (float) (-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);
            fontrenderer.func_243247_a(displayNameIn, f2, (float) i, 553648127, false, matrix4f, bufferIn, flag, j, packedLightIn);
            if (flag) {
                fontrenderer.func_243247_a(displayNameIn, f2, (float) i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }

            matrixStackIn.pop();
        }
    }

    private static boolean canRenderName(Entity entity) {
        return entity.getAlwaysRenderNameTagForRender();
    }

    @SubscribeEvent
    public static void mouseInput(InputEvent.RawMouseEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.isPotionActive(EffectInit.PARALYSIS.get()) && Minecraft.getInstance().player.getActivePotionEffect(EffectInit.PARALYSIS.get()).getDuration() > 40) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void addCustomLayer(RenderPlayerEvent.Pre event) {
        if (!hasAddedLayer) {
            event.getRenderer().addLayer(new DinosaurShoulderRidingLayer<>(event.getRenderer()));
            hasAddedLayer = true;
        }
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
        //this ensures this is render with the potions overlay cause it caused by a potion
        if (event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) {
            //check if the player has the potion effect
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isPotionActive(EffectInit.FREEZE.get()) && Minecraft.getInstance().gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                //bin the texture
                Minecraft.getInstance().getTextureManager().bindTexture(FREEZE_OVERLAY);
                MainWindow res = event.getWindow();
                AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, res.getScaledWidth(), res.getScaledHeight(), res.getScaledWidth(), res.getScaledHeight());
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void renderOverlayPre(RenderGameOverlayEvent.Pre event) {
        //this is just there to cancel vanilla heart rendering, so I can render our own hearts
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isPotionActive(EffectInit.FREEZE.get())) {
                event.setCanceled(true);
                MainWindow window = event.getWindow();
                renderHealth(window.getScaledWidth(), window.getScaledHeight(), event.getMatrixStack());
                Minecraft.getInstance().textureManager.bindTexture(ForgeIngameGui.GUI_ICONS_LOCATION);
            }
        }
    }

    //this is the copied vanilla code that draws the hearts
    public static void renderHealth(int width, int height, MatrixStack mStack) {
        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bindTexture(FROZEN_HEARTS);
        int ticks = mc.ingameGUI.getTicks();
        RenderSystem.enableBlend();

        PlayerEntity player = (PlayerEntity) mc.getRenderViewEntity();
        int health = MathHelper.ceil(player.getHealth());
        boolean highlight = mc.ingameGUI.healthUpdateCounter > (long) ticks && (mc.ingameGUI.healthUpdateCounter - (long) ticks) / 3L % 2L == 1L;

        if (health < mc.ingameGUI.playerHealth && player.hurtResistantTime > 0) {
            mc.ingameGUI.lastSystemTime = Util.milliTime();
            mc.ingameGUI.healthUpdateCounter = (long) (ticks + 20);
        } else if (health > mc.ingameGUI.playerHealth && player.hurtResistantTime > 0) {
            mc.ingameGUI.lastSystemTime = Util.milliTime();
            mc.ingameGUI.healthUpdateCounter = (long) (ticks + 10);
        }

        if (Util.milliTime() - mc.ingameGUI.lastSystemTime > 1000L) {
            mc.ingameGUI.playerHealth = health;
            mc.ingameGUI.lastPlayerHealth = health;
            mc.ingameGUI.lastSystemTime = Util.milliTime();
        }

        mc.ingameGUI.playerHealth = health;
        int healthLast = mc.ingameGUI.lastPlayerHealth;

        ModifiableAttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float healthMax = (float) attrMaxHealth.getValue();
        float absorb = MathHelper.ceil(player.getAbsorptionAmount());

        int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        rand.setSeed(ticks * 312871L);

        int left = width / 2 - 91;
        int top = height - ForgeIngameGui.left_height;
        ForgeIngameGui.left_height += (healthRows * rowHeight);
        if (rowHeight != 10) ForgeIngameGui.left_height += 10 - rowHeight;


        final int BACKGROUND = (highlight ? 43 : 34);
        float absorbRemaining = absorb;

        for (int i = MathHelper.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
            //int b0 = (highlight ? 1 : 0);
            int row = MathHelper.ceil((float) (i + 1) / 10.0F) - 1;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;

            if (health <= 4) y += rand.nextInt(2);

            mc.ingameGUI.blit(mStack, x, y, BACKGROUND, 0, 9, 9);

            if (highlight) {
                if (i * 2 + 1 < healthLast)
                    mc.ingameGUI.blit(mStack, x, y, 53, 0, 9, 9); //6
                else if (i * 2 + 1 == healthLast)
                    mc.ingameGUI.blit(mStack, x, y, 62, 0, 9, 9); //7
            }

            if (absorbRemaining > 0.0F) {
                if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                    mc.ingameGUI.blit(mStack, x, y, 170, 0, 9, 9); //17
                    absorbRemaining -= 1.0F;
                } else {
                    mc.ingameGUI.blit(mStack, x, y, 161, 0, 9, 9); //16
                    absorbRemaining -= 2.0F;
                }
            } else {
                if (i * 2 + 1 < health)
                    mc.ingameGUI.blit(mStack, x + 1, y, 53, 0, 9, 9); //4
                else if (i * 2 + 1 == health)
                    mc.ingameGUI.blit(mStack, x + 1, y, 62, 0, 9, 9); //5
            }
        }

        RenderSystem.disableBlend();
    }

    @SubscribeEvent
    public static void onPlayerJump(TickEvent.ClientTickEvent event) {
        ClientPlayerEntity pl = Minecraft.getInstance().player;
        if (event.phase != TickEvent.Phase.END || pl == null) return;

        doJump(pl);
    }

    public static void doJump(ClientPlayerEntity player) {

        Vector3d pos = player.getPositionVec();
        Vector3d motion = player.getMotion();

        AxisAlignedBB box = new AxisAlignedBB(pos.x, pos.y + (player.getEyeHeight() * .8), pos.z, pos.x, pos.y + player.getHeight(), pos.z);
        if (player.isOnGround() || player.world.containsAnyLiquid(box) || player.isPassenger() || player.abilities.allowFlying) {
            jumpCount = appleAdditionalJumps(player);
        } else if (player.movementInput.jump) {
            if (!hasReleasedJumpKey && jumpCount > 0 && motion.y < 0.333 && player.getFoodStats().getFoodLevel() > 0) {
                player.jump();
                jumpCount--;
                player.fallDistance = 0.0F;
            }
            hasReleasedJumpKey = true;
        } else {
            hasReleasedJumpKey = false;
        }
    }

    private static int appleAdditionalJumps(PlayerEntity player) {
        int jumpCount = 0;
        ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (!stack.isEmpty()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            if (enchantments.containsKey(EnchantmentInit.BETTER_JUMP.get()))
                jumpCount += enchantments.get(EnchantmentInit.BETTER_JUMP.get());
        }
        return jumpCount;
    }

    @SubscribeEvent
    public static void changeFov(EntityViewRenderEvent.FOVModifier event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.currentScreen != null) {
            return;
        }
        boolean zooming = mc.player.getPersistentData().getBoolean(DinosExpansion.MOD_ID + "zooming");
        ItemStack zoomItem = mc.player.getActiveItemStack();
        if (zooming && zoomItem.getItem() instanceof ZoomItem) {
            ZoomItem zoomItem1 = (ZoomItem) zoomItem.getItem();
            double zoomSPeed = zoomItem1.getMaxZoom();
            double maxZoom = zoomItem1.getZoomSPeed();
            double fov = event.getFOV();
            double useDuration = mc.player.getItemInUseCount();
            double maxUse = mc.player.getActiveItemStack().getUseDuration();
            double increasingDuration = maxUse - useDuration;

            event.setFOV(fov / Math.max(1, Math.min(increasingDuration / zoomSPeed, maxZoom)));
        }

    }

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTag() && stack.getTag().contains(ServerForgeEvents.COOLDOWN_NAME)) {
            event.getToolTip().add(new TranslationTextComponent(DinosExpansion.MOD_ID + ".survival_instinct_cooldown", Math.floorDiv(stack.getTag().getInt(ServerForgeEvents.COOLDOWN_NAME), 20)));
        }
    }

    @SubscribeEvent
    public static void renderZoomOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.CHAT) {
            if (Minecraft.getInstance().player != null) {
                if (Minecraft.getInstance().player.getPersistentData().contains(DinosExpansion.MOD_ID + "zooming") && Minecraft.getInstance().player.getPersistentData().getBoolean(DinosExpansion.MOD_ID + "zooming")) {
                    MainWindow res = event.getWindow();
                    Minecraft.getInstance().textureManager.bindTexture(ZOOM_OVERLAY);
                    AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, res.getScaledWidth(), res.getScaledHeight(), res.getScaledWidth(), res.getScaledHeight());
                   makeDinoSpyglassTooltip(event);
                }


            }


        }
    }

    protected static void makeDinoSpyglassTooltip(RenderGameOverlayEvent.Post event){
        assert Minecraft.getInstance().player != null;
        EntityRayTraceResult result = rayTraceEntities(Minecraft.getInstance().player, 30, e -> e instanceof Dinosaur);
        if (result != null) {
            Dinosaur dino = (Dinosaur) result.getEntity();
            MainWindow res = event.getWindow();
            MatrixStack stack = event.getMatrixStack();
            stack.push();
            Color backgroundColor = new Color(255, 255, 255, 100).darker().darker();
            Color borderColor = backgroundColor.darker().darker().darker().darker();
            RenderingUtils.drawDinosaurInfo(stack, dino, (res.getScaledWidth()) / 2, (res.getScaledHeight()) / 2, res.getScaledWidth(), res.getScaledHeight(), Minecraft.getInstance().fontRenderer);
            stack.pop();
        }
    }


    public static EntityRayTraceResult rayTraceEntities(PlayerEntity player, double maxDistance, Predicate<Entity> filter) {
        Vector3d vector3d = player.getEyePosition(0f);
        Vector3d vector3d1 = player.getLook(1.0F);
        Vector3d vector3d2 = vector3d.add(vector3d1.x * maxDistance, vector3d1.y * maxDistance, vector3d1.z * maxDistance);
        AxisAlignedBB axisalignedbb = player.getBoundingBox().expand(vector3d1.scale(maxDistance)).grow(1.0D, 1.0D, 1.0D);
        Predicate<Entity> presetFilter = (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
        presetFilter = presetFilter.and(filter);
        EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(player.world, player, vector3d, vector3d2, axisalignedbb, presetFilter);
        return entityraytraceresult;
    }

}

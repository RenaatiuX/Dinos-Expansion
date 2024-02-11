package com.rena.dinosexpansion.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.*;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RenderingUtils {

    public static void drawGradientRect(Matrix4f mat, int zLevel, int left, int top, int right, int bottom, Color startColor, Color endColor) {
        float startAlpha = (float) startColor.getAlpha() / 255f;
        float startRed = (float) startColor.getRed() / 255f;
        float startGreen = (float) startColor.getGreen() / 255f;
        float startBlue = (float) startColor.getBlue() / 255f;
        float endAlpha = (float) endColor.getAlpha() / 255f;
        float endRed = (float) endColor.getRed() / 255f;
        float endGreen = (float) endColor.getGreen() / 255f;
        float endBlue = (float) endColor.getBlue() / 255f;

        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(mat, right, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, left, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, left, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(mat, right, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    public static void drawHoveringText(MatrixStack mStack, List<? extends ITextProperties> textLines, int mouseX, int mouseY, int screenWidth, int screenHeight,
                                        int maxTextWidth, Color backgroundColor, Color borderColorStart, Color borderColorEnd, FontRenderer font) {
        if (!textLines.isEmpty()) {
            RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(ItemStack.EMPTY, textLines, mStack, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
            if (MinecraftForge.EVENT_BUS.post(event))
                return;
            mouseX = event.getX();
            mouseY = event.getY();
            screenWidth = event.getScreenWidth();
            screenHeight = event.getScreenHeight();
            maxTextWidth = event.getMaxWidth();
            font = event.getFontRenderer();

            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int tooltipTextWidth = 0;

            for (ITextProperties textLine : textLines) {
                int textLineWidth = font.getStringPropertyWidth(textLine);
                if (textLineWidth > tooltipTextWidth)
                    tooltipTextWidth = textLineWidth;
            }

            boolean needsWrap = false;

            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
                tooltipX = mouseX - 16 - tooltipTextWidth;
                if (tooltipX < 4) {// if the tooltip doesn't fit on the screen{
                    if (mouseX > screenWidth / 2)
                        tooltipTextWidth = mouseX - 12 - 8;
                    else
                        tooltipTextWidth = screenWidth - 16 - mouseX;
                    needsWrap = true;
                }
            }

            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
                tooltipTextWidth = maxTextWidth;
                needsWrap = true;
            }

            if (needsWrap) {
                int wrappedTooltipWidth = 0;
                List<ITextProperties> wrappedTextLines = new ArrayList<>();
                for (int i = 0; i < textLines.size(); i++) {
                    ITextProperties textLine = textLines.get(i);
                    List<ITextProperties> wrappedLine = font.getCharacterManager().func_238362_b_(textLine, tooltipTextWidth, Style.EMPTY);
                    if (i == 0)
                        titleLinesCount = wrappedLine.size();

                    for (ITextProperties line : wrappedLine) {
                        int lineWidth = font.getStringPropertyWidth(line);
                        if (lineWidth > wrappedTooltipWidth)
                            wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                textLines = wrappedTextLines;

                if (mouseX > screenWidth / 2)
                    tooltipX = mouseX - 16 - tooltipTextWidth;
                else
                    tooltipX = mouseX + 12;
            }

            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;

            if (textLines.size() > 1) {
                tooltipHeight += (textLines.size() - 1) * 10;
                if (textLines.size() > titleLinesCount)
                    tooltipHeight += 2; // gap between title lines and next lines
            }

            if (tooltipY < 4)
                tooltipY = 4;
            else if (tooltipY + tooltipHeight + 4 > screenHeight)
                tooltipY = screenHeight - tooltipHeight - 4;

            final int zLevel = 400;
            RenderTooltipEvent.Color colorEvent = new RenderTooltipEvent.Color(ItemStack.EMPTY, textLines, mStack, tooltipX, tooltipY, font, backgroundColor.getRGB(), borderColorStart.getRGB(), borderColorEnd.getRGB());
            MinecraftForge.EVENT_BUS.post(colorEvent);
            backgroundColor = new Color(colorEvent.getBackground());
            borderColorStart = new Color(colorEvent.getBorderStart());
            borderColorEnd = new Color(colorEvent.getBorderEnd());

            mStack.push();
            Matrix4f mat = mStack.getLast().getMatrix();
            //TODO, lots of unnessesary GL calls here, we can buffer all these together.
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostBackground(ItemStack.EMPTY, textLines, mStack, tooltipX, tooltipY, font, tooltipTextWidth, tooltipHeight));

            IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            mStack.translate(0.0D, 0.0D, zLevel);

            int tooltipTop = tooltipY;

            for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
                ITextProperties line = textLines.get(lineNumber);
                if (line != null)
                    font.drawEntityText(LanguageMap.getInstance().func_241870_a(line), (float) tooltipX, (float) tooltipY, -1, true, mat, renderType, false, 0, 15728880);

                if (lineNumber + 1 == titleLinesCount)
                    tooltipY += 2;

                tooltipY += 10;
            }

            renderType.finish();
            mStack.pop();

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostText(ItemStack.EMPTY, textLines, mStack, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));

            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
        }
    }


    public static void drawDinosaurInfo(MatrixStack mStack, Dinosaur dino, int mouseX, int mouseY, int screenWidth, int screenHeight, FontRenderer font) {
        int maxWidth = 200;
        int height = 0;
        int tooltipX = mouseX + 12;
        int zLevel = 400;
        int tooltipY = mouseY + 12;
        int tooltipTextWidth = maxWidth;
        Color backgroundColor = new Color(255, 255, 255, 100).darker().darker();
        Color borderColor = backgroundColor.darker().darker();

        Dinosaur.DinosaurInfo info = dino.getInfo();
        List<ITextProperties> beforeGradientStatsLines = new ArrayList<>();
        //name
        beforeGradientStatsLines.addAll(wrap(new StringTextComponent("Name: " + info.getName()), maxWidth, font));
        beforeGradientStatsLines.addAll(wrap(new TranslationTextComponent(DinosExpansion.MOD_ID + ".gender", info.getGender().getDisplayName()), maxWidth, font));
        beforeGradientStatsLines.addAll(wrap(new TranslationTextComponent(DinosExpansion.MOD_ID + ".level", info.getLevel()), maxWidth, font));
        beforeGradientStatsLines.addAll(wrap(new TranslationTextComponent(DinosExpansion.MOD_ID + ".rarity", info.getRarity().getDisplayName()), maxWidth, font));
        beforeGradientStatsLines.addAll(wrap(new TranslationTextComponent(DinosExpansion.MOD_ID + ".hunger"), maxWidth, font));
        height += beforeGradientStatsLines.size() * 12;
        int textMaxWdith = 0;
        for (ITextProperties properties : beforeGradientStatsLines) {
            textMaxWdith = Math.max(font.getStringPropertyWidth(properties), textMaxWdith);
        }
        tooltipTextWidth = Math.min(tooltipTextWidth, textMaxWdith);
        height += 10;

        mStack.push();
        Matrix4f mat = mStack.getLast().getMatrix();
        //TODO, lots of unnessesary GL calls here, we can buffer all these together.
        drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + height + 3, tooltipX + tooltipTextWidth + 3, tooltipY + height + 4, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + height + 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + height + 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + height + 3, backgroundColor, backgroundColor);
        drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + height + 3 - 1, borderColor, borderColor);
        drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + height + 3 - 1, borderColor, borderColor);
        drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColor, borderColor);
        drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + height + 2, tooltipX + tooltipTextWidth + 3, tooltipY + height + 3, borderColor, borderColor);
        IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        mStack.translate(0.0D, 0.0D, zLevel);
        for (ITextProperties line : beforeGradientStatsLines) {
            font.drawEntityText(LanguageMap.getInstance().func_241870_a(line), (float) tooltipX, (float) tooltipY, -1, true, mat, renderType, false, 0, 15728880);
            tooltipY += 12;
        }
        renderType.finish();
        float hungerPercentage = MathHelper.clamp(dino.getHungerValue() / (float) dino.getMaxHunger(), 0, 1);
        drawGradient(mat, zLevel, hungerPercentage, tooltipX + 3, tooltipY, tooltipTextWidth - 6, 10, 1, new Color(0, 255, 0, 100), new Color(128, 128, 128, 100).darker().darker().darker());
        mStack.pop();


    }

    private static void drawGradient(Matrix4f mat, int zLevel, float percentage, int x, int y, int width, int height, int borderSize, Color gradientColor, Color borderColor) {
        //broder
        drawGradientRect(mat, zLevel, x, y, x + width, y + borderSize, borderColor, borderColor);
        drawGradientRect(mat, zLevel, x + width, y + height, x, y + height - borderSize, borderColor, borderColor);
        drawGradientRect(mat, zLevel, x, y, x + borderSize, y + height, borderColor, borderColor);
        drawGradientRect(mat, zLevel, x + width - borderSize, y, x + width, y + height, borderColor, borderColor);

        int gradientWidth =  width - 2*borderSize;
        int gradientHeight = y + height - borderSize;
        int gradientX = x + borderSize;
        int gradientY = y + borderSize;
        float gradientRight = gradientX + gradientWidth;
        System.out.println(percentage
        );
        int scaledRight = (int) (gradientRight * percentage);
        drawGradientRect(mat, zLevel, gradientX, gradientY, scaledRight, gradientHeight, gradientColor, gradientColor);
    }

    private static List<ITextProperties> wrap(ITextComponent comp, int maxWidth, FontRenderer font) {
        return font.getCharacterManager().func_238362_b_(comp, maxWidth, comp.getStyle());
    }

    private static List<ITextProperties> wrapProperties(ITextProperties comp, int maxWidth, FontRenderer font) {
        return font.getCharacterManager().func_238362_b_(comp, maxWidth, Style.EMPTY);
    }
}

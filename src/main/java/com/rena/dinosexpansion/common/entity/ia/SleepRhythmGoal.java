package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public class SleepRhythmGoal extends Goal {

    protected final Dinosaur dino;
    protected final SleepRhythm rhythm;
    protected final World world;
    protected boolean day;

    public SleepRhythmGoal(Dinosaur dino, SleepRhythm rhythm) {
        this.dino = dino;
        this.rhythm = rhythm;
        world = dino.getEntityWorld();
        day = world.isDaytime();
    }

    @Override
    public boolean shouldExecute() {
        return day != world.isDaytime();
    }

    @Override
    public void startExecuting() {
        day = world.isDaytime();
        this.rhythm.performAction(day, this.dino);
    }

    public enum SleepRhythm {
        DIURNAL(new TranslationTextComponent("sleep_rhythm." + DinosExpansion.MOD_ID + ".diurnal"), (day, dino) -> dino.setSleeping(!day)),
        NOCTURNAL(new TranslationTextComponent("sleep_rhythm." + DinosExpansion.MOD_ID + ".nocturnal"), (day, dino) -> dino.setSleeping(day));

        private final ITextComponent displayName;
        private final BiConsumer<Boolean, Dinosaur> action;
        SleepRhythm(ITextComponent diusplayName, BiConsumer<Boolean, Dinosaur> action) {
            this.displayName = diusplayName;
            this.action = action;
        }

        public void performAction(boolean day, Dinosaur dino){
            this.action.accept(day, dino);
        }

        public ITextComponent getDisplayName() {
            return displayName;
        }
    }
}

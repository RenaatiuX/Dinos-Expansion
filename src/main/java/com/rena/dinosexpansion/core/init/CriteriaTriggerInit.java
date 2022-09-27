package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.common.avancement_trigger.FeedDinosaurCriterion;
import com.rena.dinosexpansion.common.avancement_trigger.TameDinosaurCriterion;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;

import java.util.ArrayList;
import java.util.List;

public class CriteriaTriggerInit {

    public static final List<ICriterionTrigger<?>> REGISTRY = new ArrayList<>();

    public static final FeedDinosaurCriterion FEED_DINOSAUR = register(new FeedDinosaurCriterion());

    public static final <P extends ICriterionInstance, T extends ICriterionTrigger<P>> T register(T criterion){
        REGISTRY.add(criterion);
        return criterion;
    }

}

package com.rena.dinosexpansion.common.avancement_trigger;

import com.google.gson.JsonObject;
import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class FeedDinosaurCriterion extends AbstractCriterionTrigger<FeedDinosaurCriterion.Instance> {

    protected static final ResourceLocation ID = DinosExpansion.modLoc("feed_dinosaur");

    @Override
    protected Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        return new Instance(entityPredicate, ItemPredicate.deserialize(json.get("item")));
    }

   public void trigger(ServerPlayerEntity player, ItemStack item){
        this.triggerListeners(player, instance -> instance.test(item));
   }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    protected static class Instance extends CriterionInstance{

        private final ItemPredicate item;

        public Instance(EntityPredicate.AndPredicate player, ItemPredicate item) {
            super(ID, player);
            this.item = item;
        }

        public boolean test(ItemStack item){
            return this.item.test(item);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            jsonobject.add("item", this.item.serialize());
            return jsonobject;
        }
    }
}

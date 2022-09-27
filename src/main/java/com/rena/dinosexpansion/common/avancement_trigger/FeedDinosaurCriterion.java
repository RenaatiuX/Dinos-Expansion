package com.rena.dinosexpansion.common.avancement_trigger;

import com.google.gson.JsonObject;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class FeedDinosaurCriterion extends AbstractCriterionTrigger<FeedDinosaurCriterion.Instance> {

    protected static final ResourceLocation ID = DinosExpansion.modLoc("feed_dinosaur");

    @Override
    protected Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
        return new Instance(entityPredicate, ItemPredicate.deserialize(json.get("item")), EntityPredicate.AndPredicate.deserializeJSONObject(json, "dino", conditionsParser));
    }

   public void trigger(ServerPlayerEntity player, ItemStack item, Dinosaur dino){
        LootContext ctx = EntityPredicate.getLootContext(player, dino);
        this.triggerListeners(player, instance -> instance.test(item, ctx));
   }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    protected static class Instance extends CriterionInstance{

        private final ItemPredicate item;
        private final EntityPredicate.AndPredicate dino;

        public Instance(EntityPredicate.AndPredicate player, ItemPredicate item, EntityPredicate.AndPredicate dino) {
            super(ID, player);
            this.item = item;
            this.dino = dino;
        }

        public boolean test(ItemStack item, LootContext ctx){
            return this.item.test(item) && this.dino.testContext(ctx);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditions) {
            JsonObject jsonobject = super.serialize(conditions);
            jsonobject.add("item", this.item.serialize());
            jsonobject.add("dino", this.dino.serializeConditions(conditions));
            return jsonobject;
        }
    }
}

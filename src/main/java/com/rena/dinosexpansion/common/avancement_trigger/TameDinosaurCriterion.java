package com.rena.dinosexpansion.common.avancement_trigger;

import com.google.gson.JsonObject;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

public class TameDinosaurCriterion extends AbstractCriterionTrigger<TameDinosaurCriterion.Instance> {

	protected static final ResourceLocation ID = DinosExpansion.modLoc("tame_dinosaur");

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	protected Instance deserializeTrigger(JsonObject json, AndPredicate predicate,
			ConditionArrayParser conditionsParser) {
		AndPredicate entitypredicate$andpredicate = AndPredicate
				.deserializeJSONObject(json, "entity", conditionsParser);
		return new Instance(predicate, entitypredicate$andpredicate);
	}
	
	
	public void trigger(ServerPlayerEntity player, Dinosaur dino) {
	      LootContext lootcontext = EntityPredicate.getLootContext(player, dino);
	      this.triggerListeners(player, instance -> instance.test(lootcontext));
	   }

	protected static class Instance extends CriterionInstance {

		private final AndPredicate dino;

		public Instance(AndPredicate player, AndPredicate predicate) {
			super(ID, player);
			this.dino = predicate;
		}

		public boolean test(LootContext context) {
			return this.dino.testContext(context);
		}

		@Override
		public JsonObject serialize(ConditionArraySerializer conditions) {
			JsonObject jsonobject = super.serialize(conditions);
			jsonobject.add("entity", this.dino.serializeConditions(conditions));
			return jsonobject;
		}

	}
}

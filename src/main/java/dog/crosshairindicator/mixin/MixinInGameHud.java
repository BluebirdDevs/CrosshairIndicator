package dog.crosshairindicator.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;

@Mixin(InGameHud.class)
public class MixinInGameHud {
	@Shadow @Final private MinecraftClient client;
	@Unique Identifier CUSTOM_CROSSHAIR = Identifier.of("crosshairindicator", "crosshair");
	@Unique Identifier SHIELD_CROSSHAIR = Identifier.of("crosshairindicator", "shield_crosshair");

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
private void drawCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
    if (this.client.targetedEntity instanceof PlayerEntity player) {
        int size = 15;
        int x = (context.getScaledWindowWidth() - size) / 2;
        int y = (context.getScaledWindowHeight() - size) / 2;

        Identifier texture = player.isBlocking() ? SHIELD_CROSSHAIR : CUSTOM_CROSSHAIR;

        context.drawTexture(
            RenderPipeline::getGui,
            texture,
            x, y,
            0.0F, 0.0F,
            size, size,
            size, size
        );
    }
}
}

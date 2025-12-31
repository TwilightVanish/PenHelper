package lisa.penhelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector2i;

import java.util.List;

public final class IconButton extends Button {

    private final Item itemIcon;

    public IconButton(int x, int y, int w, int h, Runnable onPress, Item itemIcon) {
        super(
                x, y, w, h,
                Component.empty(),
                btn -> onPress.run(),
                DEFAULT_NARRATION
        );


        this.itemIcon = itemIcon;
    }

    @Override
    public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        super.renderWidget(gfx, mouseX, mouseY, delta);

        gfx.renderItem(
                itemIcon.getDefaultInstance(),
                getX() + 2,
                getY() + 2
        );
    }
}



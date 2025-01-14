package phanastrae.operation_starcleave.render.firmament;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import phanastrae.operation_starcleave.world.firmament.Firmament;
import phanastrae.operation_starcleave.world.firmament.FirmamentTilePos;

public class FirmamentOutlineRenderer {

    @Nullable
    public FirmamentTilePos hitTile = null;

    public static final VoxelShape TILE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 64.0, 4.0, 64.0);

    public void updateHitTile(float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity entity = client.cameraEntity;
        if(entity == null) return;
        World world = client.world;
        if(world == null) return;
        Firmament firmament = Firmament.fromWorld(world);
        if(firmament == null) return;

        Vec3d camPos = entity.getCameraPosVec(tickDelta);
        Vec3d lookVec = entity.getRotationVec(tickDelta);
        float skyHeight = firmament.getY();

        double t = (skyHeight - camPos.y) / lookVec.y;
        if(t <= 0) {
            // firmament is behind camera
            this.hitTile = null;
        } else {
            Vec3d target = camPos.add(lookVec.multiply(t));
            FirmamentTilePos tilePos = FirmamentTilePos.fromBlockCoords((int)Math.floor(target.x), (int)Math.floor(target.z), firmament);
            int damage = firmament.getDamage(tilePos.blockX, tilePos.blockZ);
            if(damage == 0) {
                // tile empty
                this.hitTile = null;
            } else {
                // damage present
                double distance = lookVec.length() * t;
                double reachDistance = client.player.getBlockInteractionRange();
                if(distance > reachDistance) {
                    // tile too far away
                    this.hitTile = null;
                } else {
                    // tile in range of getting hit
                    HitResult crosshairTarget = client.crosshairTarget;
                    if(crosshairTarget == null) {
                        // no interruptions, can hit tile
                        this.hitTile = tilePos;
                    } else {
                        // potential interruption
                        Vec3d hitPos = crosshairTarget.getPos();
                        double crosshairTargetDistance = hitPos.subtract(camPos).length();
                        if(distance < crosshairTargetDistance) {
                            // tile is closer than crosshair target
                            this.hitTile = tilePos;
                        } else {
                            // tile is behind crosshair target
                            this.hitTile = null;
                        }
                    }
                }
            }
        }
    }

    public void renderOutline(VertexConsumerProvider consumers, Camera camera, MatrixStack matrices) {
        FirmamentTilePos tile = hitTile;
        if(tile == null) return;

        VertexConsumer vertexConsumer = consumers.getBuffer(RenderLayer.getLines());
        MatrixStack.Entry entry = matrices.peek();

        float red = 0f;
        float green = 0f;
        float blue = 0f;
        float alpha = 0.4f;

        double offsetX = tile.blockX - camera.getPos().x;
        double offsetY = tile.y - camera.getPos().y;
        double offsetZ = tile.blockZ - camera.getPos().z;

        FirmamentOutlineRenderer.TILE_SHAPE.forEachEdge(
                (minX, minY, minZ, maxX, maxY, maxZ) -> {
                    float k = (float)(maxX - minX);
                    float l = (float)(maxY - minY);
                    float m = (float)(maxZ - minZ);
                    float n = MathHelper.sqrt(k * k + l * l + m * m);
                    k /= n;
                    l /= n;
                    m /= n;
                    vertexConsumer.vertex(entry.getPositionMatrix(), (float)(minX + offsetX), (float)(minY + offsetY), (float)(minZ + offsetZ))
                            .color(red, green, blue, alpha)
                            .normal(matrices.peek(), k, l, m);
                    vertexConsumer.vertex(entry.getPositionMatrix(), (float)(maxX + offsetX), (float)(maxY + offsetY), (float)(maxZ + offsetZ))
                            .color(red, green, blue, alpha)
                            .normal(matrices.peek(), k, l, m);
                }
        );
    }
}

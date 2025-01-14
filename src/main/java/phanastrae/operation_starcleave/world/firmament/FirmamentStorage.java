package phanastrae.operation_starcleave.world.firmament;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.StorageIoWorker;
import net.minecraft.world.storage.StorageKey;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class FirmamentStorage {

    private final StorageIoWorker worker;

    public FirmamentStorage(StorageKey storageKey, Path directory, boolean dsync) {
        this.worker = new FirmamentStorageIoWorker(storageKey, directory, dsync);
    }

    public CompletableFuture<Optional<NbtCompound>> getNbt(ChunkPos chunkPos) {
        return this.worker.readChunkData(chunkPos);
    }

    public void setNbt(ChunkPos chunkPos, NbtCompound nbt) {
        this.worker.setResult(chunkPos, nbt);
    }

    public void close() throws IOException {
        this.worker.close();
    }

    public static FirmamentStorage getFrom(ServerChunkLoadingManager threadedAnvilChunkStorage) {
        return ((FirmamentStorageHolder)threadedAnvilChunkStorage).operation_starcleave$getFirmamentStorage();
    }

    public static FirmamentStorage getFrom(ServerWorld serverWorld) {
         return FirmamentStorage.getFrom(serverWorld.getChunkManager().chunkLoadingManager);
    }
}

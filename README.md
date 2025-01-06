# RandomChunkDeleter

## Description

RandomChunkDeleter is a Minecraft plugin designed to randomly delete chunks from your world. It ensures that certain protected chunks remain untouched and runs the deletion process every hour.

## Features

- **Random Chunk Deletion**: Randomly selects and deletes chunks from the world.
- **Protected Chunks**: Specify chunks that should not be deleted.
- **Scheduled Deletion**: Automatically runs the deletion process every hour.

## Installation

1. Download the latest version of the plugin from the [releases page](https://github.com/kirilo987/ChunkDelete/releases).
2. Place the downloaded `.jar` file into your server's `plugins` folder.
3. Restart the server.

## Configuration

Edit the `plugin.yml` file to add or modify the list of protected chunks.

```yaml
    @Override
    public void onEnable() {
        getLogger().info("Плагін RandomChunkDeleter активовано!");

        // Додаємо захищені чанки
        protectedChunks.add(""); //кординати чанку
        protectedChunks.add(""); //кординати чанку

        // Запуск задачі, яка виконується раз на годину
        new BukkitRunnable() {
            @Override
            public void run() {
                deleteRandomChunk();
            }
        }.runTaskTimer(this, 0, 20 * 60 * 60); // Запуск кожні 3600 секунд (1 година)
    }

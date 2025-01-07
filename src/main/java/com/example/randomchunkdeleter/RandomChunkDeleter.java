package com.example.randomchunkdeleter;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomChunkDeleter extends JavaPlugin {

    private static final int RADIUS = 10000; // Радіус пошуку чанків
    private final Random random = new Random();

    // Список чанків, які не можна видаляти (у форматі "x,z")
    private final Set<String> protectedChunks = new HashSet<>();

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
        }.runTaskTimer(this, 0, 5 * 60); // Запуск кожні 3600 секунд (1 година)
    }

    @Override
    public void onDisable() {
        getLogger().info("Плагін RandomChunkDeleter деактивовано!");
    }

    private void deleteRandomChunk() {
        World world = Bukkit.getWorlds().get(0); // Вибір першого світу
        if (world == null) {
            getLogger().warning("Світ не знайдено!");
            return;
        }

        int chunkX, chunkZ;
        String chunkKey;

        // Генеруємо випадкові координати, доки не знайдемо чанк, який можна видалити
        do {
            chunkX = random.nextInt((RADIUS * 2) / 16) - (RADIUS / 16);
            chunkZ = random.nextInt((RADIUS * 2) / 16) - (RADIUS / 16);
            chunkKey = chunkX + "," + chunkZ;
        } while (protectedChunks.contains(chunkKey));

        Chunk chunk = world.getChunkAt(chunkX, chunkZ);

        getLogger().info("Видаляємо чанк: " + chunkKey);

        // Очищення чанка
        clearChunk(chunk);
    }

    private void clearChunk(Chunk chunk) {
        World world = chunk.getWorld();
        int startX = chunk.getX() * 16;
        int startZ = chunk.getZ() * 16;

        // Видалення всіх блоків у чанку
        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }

        // Вивантаження чанка для звільнення пам'яті
        chunk.unload(true); // Використовуємо метод з одним параметром
    }
}

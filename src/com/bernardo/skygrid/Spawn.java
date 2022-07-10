package com.bernardo.skygrid;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Spawn extends BlockPopulator {
    int[] spawnEgg = new int[]{61, 56, 62, 50, 58, 54, 51, 52, 57, 55, 90, 91, 93, 92, 96, 95, 120, 59, 98, 66};

    public Spawn() {
    }

    public void populate(World world, Random random, Chunk chunk) {
        int wH = world.getMaxHeight();

        for(int x = 0; x < 16; x += 4) {
            for(int y = 0; y < wH; y += 4) {
                for(int z = 0; z < 16; z += 4) {
                    Block blk = chunk.getBlock(x, y, z);
                    if (blk.getType() == Material.CHEST) {
                        Chest chest = (Chest)blk.getState();
                        this.populateChest(world, random, Main.allBlocksOneWorld, chest);
                    } else {
                        Random r;
                        if (blk.getType() == Material.GRASS) {
                            blk.getRelative(BlockFace.UP).setType(getGrassPop(), false);
                            if (blk.getRelative(BlockFace.UP).getType() == Material.RED_ROSE) {
                                r = new Random();
                                blk.getRelative(BlockFace.UP).setData((byte)r.nextInt(9));
                            }
                        } else {
                            int data;
                            if (blk.getType() == Material.DIRT) {
                                r = new Random();
                                data = r.nextInt(3);
                                blk.setData((byte)data);
                                if (data == 0) {
                                    blk.getRelative(BlockFace.UP).setType(getDirtPop());
                                    if (blk.getRelative(BlockFace.UP).getType() == Material.SAPLING) {
                                        blk.getRelative(BlockFace.UP).setData((byte)r.nextInt(6));
                                    }
                                }
                            } else if (blk.getType() == Material.WOOL) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(16));
                            } else if (blk.getType() == Material.PRISMARINE) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(3));
                            } else if (blk.getType() == Material.RED_SANDSTONE) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(3));
                            } else if (blk.getType() == Material.SMOOTH_BRICK) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(4));
                            } else if (blk.getType() == Material.STONE) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(7));
                            } else if (blk.getType() == Material.LOG) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(4));
                            } else if (blk.getType() == Material.LOG_2) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(2));
                            } else if (blk.getType() == Material.WOOD) {
                                r = new Random();
                                blk.setData((byte)r.nextInt(6));
                            } else if (blk.getType() == Material.SAND) {
                                r = new Random();
                                data = r.nextInt(2);
                                blk.setData((byte)data);
                                if (data == 0 && r.nextInt(10) < 1) {
                                    blk.getRelative(BlockFace.UP).setTypeId(Material.CACTUS.getId(), false);
                                }
                            } else if (blk.getType() == Material.SOUL_SAND) {
                                blk.getRelative(BlockFace.UP).setType(getSoulPop());
                            } else if (blk.getType() == Material.MOB_SPAWNER) {
                                CreatureSpawner spawner;
                                if (world.getEnvironment() != Environment.NETHER && world.getEnvironment() != Environment.THE_END) {
                                    if (!Main.allBlocksOneWorld) {
                                        spawner = (CreatureSpawner)blk.getState();
                                        spawner.setSpawnedType(this.getNormEntity());
                                    } else {
                                        spawner = (CreatureSpawner)blk.getState();
                                        spawner.setSpawnedType(getEntityType());
                                    }
                                } else if (world.getEnvironment() == Environment.NETHER) {
                                    spawner = (CreatureSpawner)blk.getState();
                                    spawner.setSpawnedType(this.getNetherEntity());
                                } else {
                                    spawner = (CreatureSpawner)blk.getState();
                                    spawner.setSpawnedType(EntityType.ENDERMAN);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public static EntityType getEntityType() {
        EntityType[] mobHosNorm = new EntityType[]{EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.PIG_ZOMBIE, EntityType.SLIME};
        EntityType[] mobHosRare = new EntityType[]{EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE, EntityType.CREEPER, EntityType.ENDERMAN};
        EntityType[] mobNorm = new EntityType[]{EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN};
        EntityType[] mobRare = new EntityType[]{EntityType.COW, EntityType.MUSHROOM_COW};
        Random random = new Random();
        int c = random.nextInt(100);
        EntityType entRet;
        if (c < 2) {
            entRet = mobHosRare[random.nextInt(mobHosRare.length)];
        } else if (c < 5) {
            entRet = mobRare[random.nextInt(mobRare.length)];
        } else if (c < 14) {
            entRet = mobHosNorm[random.nextInt(mobHosNorm.length)];
        } else {
            entRet = mobNorm[random.nextInt(mobNorm.length)];
        }

        return entRet;
    }

    public EntityType getNormEntity() {
        EntityType[] mobHosNorm = new EntityType[]{EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.SLIME};
        EntityType[] mobHosRare = new EntityType[]{EntityType.CREEPER, EntityType.ENDERMAN};
        EntityType[] mobNorm = new EntityType[]{EntityType.PIG, EntityType.SHEEP, EntityType.CHICKEN};
        EntityType[] mobRare = new EntityType[]{EntityType.COW, EntityType.MUSHROOM_COW};
        Random random = new Random();
        int c = random.nextInt(100);
        EntityType ent;
        if (c < 2) {
            ent = mobHosRare[random.nextInt(mobHosRare.length)];
        } else if (c < 5) {
            ent = mobRare[random.nextInt(mobRare.length)];
        } else if (c < 14) {
            ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        } else {
            ent = mobNorm[random.nextInt(mobNorm.length)];
        }

        return ent;
    }

    public EntityType getNetherEntity() {
        EntityType[] mobHosNorm = new EntityType[]{EntityType.PIG_ZOMBIE, EntityType.SKELETON};
        EntityType[] mobHosRare = new EntityType[]{EntityType.BLAZE, EntityType.GHAST, EntityType.MAGMA_CUBE};
        Random random = new Random();
        int c = random.nextInt(100);
        EntityType ent;
        if (c < 2) {
            ent = mobHosRare[random.nextInt(mobHosRare.length)];
        } else {
            ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        }

        return ent;
    }

    public static Material getGrassPop() {
        Random rand = new Random();
        int p = rand.nextInt(100);
        Material popMat;
        if (p < 5) {
            popMat = Material.RED_MUSHROOM;
            return popMat;
        } else if (p < 10) {
            popMat = Material.BROWN_MUSHROOM;
            return popMat;
        } else if (p < 18) {
            popMat = Material.RED_ROSE;
            return popMat;
        } else if (p < 20) {
            popMat = Material.YELLOW_FLOWER;
            return popMat;
        } else if (p < 25) {
            popMat = Material.SUGAR_CANE_BLOCK;
            return popMat;
        } else {
            return Material.AIR;
        }
    }

    public static Material getSoulPop() {
        Random random = new Random();
        int a = random.nextInt(10);
        return a < 2 ? Material.NETHER_WARTS : Material.AIR;
    }

    public static Material getDirtPop() {
        Random r = new Random();
        int p = r.nextInt(10);
        return p < 1 ? Material.SAPLING : Material.AIR;
    }

    public void populateChest(World world, Random random, boolean allBlocks, Chest chest) {
        World.Environment env = world.getEnvironment();
        int[] itemMythicID = new int[1];
        int[] itemMythicAmount = new int[1];
        int mythChance = 0;
        int maxI;
        int[] itemRareID;
        int[] itemRareAmount;
        int[] itemID;
        int[] itemAmount;
        int rareChance;
        int i;
        int preMax1;
        int quality;
        if (env != Environment.NETHER && env != Environment.THE_END) {
            if (allBlocks) {
                mythChance = Main.cChMythic;
                rareChance = Main.cChRare;
                itemMythicID = Main.iChMythic;
                itemMythicAmount = Main.iChMythicAmount;
                itemRareID = Main.iChRare;
                itemRareAmount = Main.iChRareAmount;
                itemID = Main.iChNormal;
                itemAmount = Main.iChNormalAmount;
                i = random.nextInt(10);
                preMax1 = random.nextInt(4);
                quality = random.nextInt(10);
                if (i < 2) {
                    maxI = 1 + quality;
                } else {
                    maxI = 1 + preMax1;
                }
            } else {
                mythChance = Main.cChMythic;
                rareChance = Main.cChRare;
                itemMythicID = Main.iNormChMythic;
                itemMythicAmount = Main.iNormChMythicAmount;
                itemRareID = Main.iNormChRare;
                itemRareAmount = Main.iNormChRareAmount;
                itemID = Main.iNormChNormal;
                itemAmount = Main.iNormChNormalAmount;
                i = random.nextInt(10);
                preMax1 = random.nextInt(4);
                quality = random.nextInt(10);
                if (i < 2) {
                    maxI = 1 + quality;
                } else {
                    maxI = 1 + preMax1;
                }
            }
        } else if (env == Environment.NETHER) {
            rareChance = Main.cNethChRare;
            itemRareID = Main.iNethChRare;
            itemRareAmount = Main.iNethChRareAmount;
            itemID = Main.iNethChNorm;
            itemAmount = Main.iNethChNormAmount;
            i = random.nextInt(10);
            preMax1 = random.nextInt(2);
            quality = random.nextInt(5);
            if (i < 2) {
                maxI = 1 + quality;
            } else {
                maxI = 1 + preMax1;
            }
        } else {
            rareChance = Main.cEndChRare;
            itemRareID = Main.iEndChRare;
            itemRareAmount = Main.iEndChRareAmount;
            itemID = Main.iEndChNorm;
            itemAmount = Main.iEndChNormAmount;
            i = random.nextInt(10);
            preMax1 = random.nextInt(2);
            quality = random.nextInt(5);
            if (i < 2) {
                maxI = 1 + quality;
            } else {
                maxI = 1 + preMax1;
            }
        }

        for(i = 0; i < maxI; ++i) {
            Inventory inv = chest.getInventory();
            quality = random.nextInt(100);
            int amount;
            int aPos;
            int ID;
            int maxAmount;
            ItemStack itm;
            if (quality < rareChance) {
                if (quality < mythChance && env == Environment.NORMAL) {
                    aPos = random.nextInt(itemMythicID.length);
                    ID = itemMythicID[aPos];
                    maxAmount = itemMythicAmount[aPos];
                    if (maxAmount == 1) {
                        amount = 1;
                    } else {
                        amount = random.nextInt(maxAmount) + 1;
                    }

                    itm = new ItemStack(ID, amount, (short)0);
                    if (itm.getType() == Material.MONSTER_EGG) {
                        Random rdm = new Random();
                        itm = new ItemStack(Material.MONSTER_EGG, 1, (short)this.spawnEgg[rdm.nextInt(this.spawnEgg.length)]);
                        inv.addItem(new ItemStack[]{itm});
                    } else {
                        inv.addItem(new ItemStack[]{itm});
                    }
                } else {
                    aPos = random.nextInt(itemRareID.length);
                    ID = itemRareID[aPos];
                    maxAmount = itemRareAmount[aPos];
                    if (maxAmount == 1) {
                        amount = 1;
                    } else {
                        amount = random.nextInt(maxAmount) + 1;
                    }

                    itm = new ItemStack(ID, amount, (short)0);
                    if (itm.getType() != Material.MONSTER_EGG && itm.getType() != Material.LOG && itm.getType() != Material.LOG_2) {
                        inv.addItem(new ItemStack[]{itm});
                    } else if (itm.getType() == Material.MONSTER_EGG) {
                        itm = new ItemStack(Material.MONSTER_EGG, amount, (short)this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
                        inv.addItem(new ItemStack[]{itm});
                    } else if (itm.getType() == Material.LOG) {
                        itm = new ItemStack(Material.LOG, amount, (short)random.nextInt(4));
                        inv.addItem(new ItemStack[]{itm});
                    } else if (itm.getType() == Material.LOG_2) {
                        itm = new ItemStack(Material.LOG_2, amount, (short)random.nextInt(2));
                        inv.addItem(new ItemStack[]{itm});
                    }
                }
            } else {
                aPos = random.nextInt(itemID.length);
                ID = itemID[aPos];
                maxAmount = itemAmount[aPos];
                if (maxAmount == 1) {
                    amount = 1;
                } else {
                    amount = random.nextInt(maxAmount) + 1;
                }

                itm = new ItemStack(ID, amount, (short)0);
                if (itm.getType() != Material.MONSTER_EGG && itm.getType() != Material.LOG) {
                    inv.addItem(new ItemStack[]{itm});
                } else if (itm.getType() == Material.MONSTER_EGG) {
                    itm = new ItemStack(Material.MONSTER_EGG, amount, (short)this.spawnEgg[random.nextInt(this.spawnEgg.length)]);
                    inv.addItem(new ItemStack[]{itm});
                } else if (itm.getType() == Material.LOG) {
                    itm = new ItemStack(Material.LOG, amount, (short)random.nextInt(4));
                    inv.addItem(new ItemStack[]{itm});
                } else if (itm.getType() == Material.LOG_2) {
                    itm = new ItemStack(Material.LOG_2, amount, (short)random.nextInt(2));
                    inv.addItem(new ItemStack[]{itm});
                }
            }
        }

    }
}


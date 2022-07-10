package com.bernardo.skygrid;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class Gerador extends ChunkGenerator {
    public int worldHeight ;

    public Gerador() {
    }

    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    static void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }

        result[y >> 4][(y & 15) << 8 | z << 4 | x] = blkid;
    }

    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid biomes) {
        if (world.getEnvironment() == Environment.NETHER) {
            this.worldHeight = Main.cNetherHeight;
        } else if (world.getEnvironment() == Environment.NORMAL) {
            this.worldHeight = Main.cHeight;
        } else {
            this.worldHeight = Main.cEndHeight;
        }

        byte[][] result = new byte[this.worldHeight / 16][];
        int x;
        int y;
        int z;
        int ID;
        if (Main.genGlass) {
            for(x = 0; x < 16; ++x) {
                for(y = 0; y < this.worldHeight; ++y) {
                    for(z = 0; z < 16; ++z) {
                        if (x % 4 == 0 && y % 4 == 0 && z % 4 == 0) {
                            ID = this.getRandBlockID(world, random, Main.allBlocksOneWorld);
                            setBlock(result, x, y, z, (byte)ID);
                        } else if (y < Main.cHeight - 3) {
                            ID = Material.GLASS.getId();
                            setBlock(result, x, y, z, (byte)ID);
                        }
                    }
                }
            }
        } else {
            for(x = 0; x < 16; x += 4) {
                for(y = 0; y < Main.cHeight; y += 4) {
                    for(z = 0; z < 16; z += 4) {
                        ID = this.getRandBlockID(world, random, Main.allBlocksOneWorld);
                        setBlock(result, x, y, z, (byte)ID);
                    }
                }
            }
        }

        return result;
    }

    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0.5, (double)this.worldHeight, 0.5);
    }

    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList(new Spawn());
    }

    public int getRandBlockID(World world, Random random, boolean allBlocks) {
        int randID;
        int r;
        if (world.getEnvironment() == Environment.NORMAL) {
            if (allBlocks) {
                r = random.nextInt(10000);
                if (r < Main.cMythic) {
                    randID = Main.iMythic[random.nextInt(Main.iMythic.length)];
                    return randID;
                } else if (r < Main.cUnique) {
                    randID = Main.iUnique[random.nextInt(Main.iUnique.length)];
                    return randID;
                } else if (r < Main.cRare) {
                    randID = Main.iRare[random.nextInt(Main.iRare.length)];
                    return randID;
                } else if (r < Main.cUncommon) {
                    randID = Main.iUncommon[random.nextInt(Main.iUncommon.length)];
                    return randID;
                } else {
                    randID = Main.iAbundant[random.nextInt(Main.iAbundant.length)];
                    return randID;
                }
            } else {
                r = random.nextInt(10000);
                if (r < Main.cMythic) {
                    randID = Main.iNormMythic[random.nextInt(Main.iNormMythic.length)];
                    return randID;
                } else if (r < Main.cUnique) {
                    randID = Main.iNormUnique[random.nextInt(Main.iNormUnique.length)];
                    return randID;
                } else if (r < Main.cRare) {
                    randID = Main.iNormRare[random.nextInt(Main.iNormRare.length)];
                    return randID;
                } else if (r < Main.cUncommon) {
                    randID = Main.iNormUncommon[random.nextInt(Main.iNormUncommon.length)];
                    return randID;
                } else {
                    randID = Main.iNormAbundant[random.nextInt(Main.iNormAbundant.length)];
                    return randID;
                }
            }
        } else if (world.getEnvironment() == Environment.NETHER) {
            r = random.nextInt(100);
            if (r < Main.cNethRare) {
                randID = Main.iNethBlkRare[random.nextInt(Main.iNethBlkRare.length)];
                return randID;
            } else {
                randID = Main.iNethBlkNorm[random.nextInt(Main.iNethBlkNorm.length)];
                return randID;
            }
        } else {
            r = random.nextInt(100);
            if (r < Main.cEndRare) {
                randID = Main.iEndBlkRare[random.nextInt(Main.iEndBlkRare.length)];
                return randID;
            } else {
                randID = Main.iEndBlkNorm[random.nextInt(Main.iEndBlkNorm.length)];
                return randID;
            }
        }
    }
}


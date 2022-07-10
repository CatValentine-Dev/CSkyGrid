package com.bernardo.skygrid;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main instance;
    public FileConfiguration configMain;
    public File dirPlayers;
    public File conFileMain;
    public File dataFolder;
    public static int cdelay;
    public static int cHeight;
    public static int cNetherHeight;
    public static int cEndHeight;
    public static int cMythic;
    public static int cUnique;
    public static int cRare;
    public static int cUncommon;
    public static int cMax;
    public static int cMin;
    public static int cChMythic;
    public static int cChRare;
    public static int cNethRare;
    public static int cNethChRare;
    public static int cEndChRare;
    public static int cEndRare;
    public static boolean genGlass;
    public static boolean allBlocksOneWorld;
    public static String cName;
    public static String cNetherName;
    public static String cEndName;
    public static int[] iMythic;
    public static int[] iUnique;
    public static int[] iRare;
    public static int[] iUncommon;
    public static int[] iAbundant;
    public static int[] iNormMythic;
    public static int[] iNormUnique;
    public static int[] iNormRare;
    public static int[] iNormUncommon;
    public static int[] iNormAbundant;
    public static int[] iChMythic;
    public static int[] iChMythicAmount;
    public static int[] iNormChMythic;
    public static int[] iNormChMythicAmount;
    public static int[] iChRare;
    public static int[] iChRareAmount;
    public static int[] iNormChRare;
    public static int[] iNormChRareAmount;
    public static int[] iChNormal;
    public static int[] iChNormalAmount;
    public static int[] iNormChNormal;
    public static int[] iNormChNormalAmount;
    public static int[] iNethBlkRare;
    public static int[] iNethBlkNorm;
    public static int[] iEndBlkRare;
    public static int[] iEndBlkNorm;
    public static int[] iNethChRare;
    public static int[] iNethChRareAmount;
    public static int[] iNethChNorm;
    public static int[] iNethChNormAmount;
    public static int[] iEndChRare;
    public static int[] iEndChRareAmount;
    public static int[] iEndChNorm;
    public static int[] iEndChNormAmount;



    public Main() {
    }

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a_-_-_-_-_-_-_-_-_-_-_-_-");
        Bukkit.getConsoleSender().sendMessage("§a§lCSyGrid ativado.");
        Bukkit.getConsoleSender().sendMessage("§a§l Feito por Dev Erick.");
        Bukkit.getConsoleSender().sendMessage("§a_-_-_-_-_-_-_-_-_-_-_-_-");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        File dFolder = this.getDataFolder();
        File drPlyrs = new File(this.getDataFolder(), "Jogadores");
        File cnFlMn = new File(this.getDataFolder(), "config.yml");
        this.dirPlayers = drPlyrs;
        this.conFileMain = cnFlMn;
        this.configMain = this.getConfig();
        this.dataFolder = dFolder;
        if (!this.dirPlayers.exists()) {
            this.dirPlayers.mkdir();
        }

        if (this.conFileMain.exists()) {
            this.configMain = YamlConfiguration.loadConfiguration(this.conFileMain);
        } else {
            copyConfig(this.conFileMain, this.getClass());
        }

        this.initConfig();
        this.getLogger().info(ChatColor.GREEN +"CSkyGrid Habilitado e Carregado");
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new Gerador();
    }

    private static void copyConfig(File config, Class<? extends Main> cl) {
        try {
            InputStream in = cl.getResourceAsStream("/" + config.getName());
            FileOutputStream out = new FileOutputStream(config);
            byte[] buffer = new byte[512];

            int i;
            while((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }

            out.close();
        } catch (FileNotFoundException var6) {
            Bukkit.getLogger().log(Level.SEVERE, "O jar do plug-in não parece ter o arquivo de configuração necessário para gravação", var6);
        } catch (IOException var7) {
            Bukkit.getLogger().log(Level.SEVERE, (String)null, var7);
        }

    }

    public void initConfig() {
        FileConfiguration config = this.configMain;
        cdelay = config.getInt("Delay", 0);
        cHeight = config.getInt("World_Height", 128);
        cMythic = config.getInt("Mythic_Block_Probability", 4);
        cNetherHeight = config.getInt("NetherConfig.Nether_World_Height", 128);
        cEndHeight = config.getInt("EndConfig.End_World_Height", 128);
        cUnique = config.getInt("Unique_Block_Probability", 181);
        cRare = config.getInt("Rare_Block_Probability", 1801);
        cUncommon = config.getInt("Uncommon_Block_Probability", 4001);
        cMax = config.getInt("Spawn_Max", 500);
        cMin = config.getInt("Spawn_Min", 0);
        cName = config.getString("World_Name", "Skygrid");
        cNetherName = config.getString("NetherConfig.Nether_World_Name", "NetherSkygrid");
        cEndName = config.getString("EndConfig.End_World_Name", "EndSkygrid");
        cChMythic = config.getInt("Chest_Prob_Mythic", 2);
        cChRare = config.getInt("Chest_Prob_Rare", 6);
        genGlass = config.getBoolean("ReplaceAirWithGlass", false);
        allBlocksOneWorld = config.getBoolean("AllBlocksOneWorld", true);
        cNethRare = config.getInt("NetherConfig.NetherBlocks.RareProb", 1);
        cNethChRare = config.getInt("NetherConfig.NetherChestItems.RareProb", 2);
        cEndRare = config.getInt("EndConfig.EndBlocks.RareProb", 1);
        cEndChRare = config.getInt("EndConfig.EndChestItems.RareProb", 1);
        String[] sMythic = config.getString("BlockGroups.Mythic").split(" ");
        String[] sUnique = config.getString("BlockGroups.Unique").split(" ");
        String[] sRare = config.getString("BlockGroups.Rare").split(" ");
        String[] sUncommon = config.getString("BlockGroups.Uncommon").split(" ");
        String[] sAbundant = config.getString("BlockGroups.Abundant").split(" ");
        String[] sNethBlkRare = config.getString("NetherConfig.NetherBlocks.Rare").split(" ");
        String[] sNethBlkNorm = config.getString("NetherConfig.NetherBlocks.Normal").split(" ");
        String[] sEndBlkRare = config.getString("EndConfig.EndBlocks.Rare").split(" ");
        String[] sEndBlkNorm = config.getString("EndConfig.EndBlocks.Normal").split(" ");
        String[] sChMythic = config.getString("ChestItems.Mythic").split(" ");
        String[] sChRare = config.getString("ChestItems.Rare").split(" ");
        String[] sChNormal = config.getString("ChestItems.Normal").split(" ");
        String[] sNethChRare = config.getString("NetherConfig.NetherChestItems.Rare").split(" ");
        String[] sNethChNorm = config.getString("NetherConfig.NetherChestItems.Normal").split(" ");
        String[] sEndChRare = config.getString("EndConfig.EndChestItems.Rare").split(" ");
        String[] sEndChNorm = config.getString("EndConfig.EndChestItems.Normal").split(" ");
        String[] sNormMythic = config.getString("NormalConfig.BlockGroups.Mythic").split(" ");
        String[] sNormUnique = config.getString("NormalConfig.BlockGroups.Unique").split(" ");
        String[] sNormRare = config.getString("NormalConfig.BlockGroups.Rare").split(" ");
        String[] sNormUncommon = config.getString("NormalConfig.BlockGroups.Uncommon").split(" ");
        String[] sNormAbundant = config.getString("NormalConfig.BlockGroups.Abundant").split(" ");
        String[] sNormChMythic = config.getString("NormalConfig.ChestItems.Mythic").split(" ");
        String[] sNormChRare = config.getString("NormalConfig.ChestItems.Rare").split(" ");
        String[] sNormChNormal = config.getString("NormalConfig.ChestItems.Normal").split(" ");
        iMythic = this.stringArrayToIntArray(sMythic);
        iUnique = this.stringArrayToIntArray(sUnique);
        iRare = this.stringArrayToIntArray(sRare);
        iUncommon = this.stringArrayToIntArray(sUncommon);
        iAbundant = this.stringArrayToIntArray(sAbundant);
        iNethBlkRare = this.stringArrayToIntArray(sNethBlkRare);
        iNethBlkNorm = this.stringArrayToIntArray(sNethBlkNorm);
        iEndBlkRare = this.stringArrayToIntArray(sEndBlkRare);
        iEndBlkNorm = this.stringArrayToIntArray(sEndBlkNorm);
        iNormMythic = this.stringArrayToIntArray(sNormMythic);
        iNormUnique = this.stringArrayToIntArray(sNormUnique);
        iNormRare = this.stringArrayToIntArray(sNormRare);
        iNormUncommon = this.stringArrayToIntArray(sNormUncommon);
        iNormAbundant = this.stringArrayToIntArray(sNormAbundant);
        iChMythic = this.positionalStringArrayToIntArray(sChMythic, 0);
        iChMythicAmount = this.positionalStringArrayToIntArray(sChMythic, 1);
        iChRare = this.positionalStringArrayToIntArray(sChRare, 0);
        iChRareAmount = this.positionalStringArrayToIntArray(sChRare, 1);
        iChNormal = this.positionalStringArrayToIntArray(sChNormal, 0);
        iChNormalAmount = this.positionalStringArrayToIntArray(sChNormal, 1);
        iNethChRare = this.positionalStringArrayToIntArray(sNethChRare, 0);
        iNethChRareAmount = this.positionalStringArrayToIntArray(sNethChRare, 1);
        iNethChNorm = this.positionalStringArrayToIntArray(sNethChNorm, 0);
        iNethChNormAmount = this.positionalStringArrayToIntArray(sNethChNorm, 1);
        iEndChRare = this.positionalStringArrayToIntArray(sEndChRare, 0);
        iEndChRareAmount = this.positionalStringArrayToIntArray(sEndChRare, 1);
        iEndChNorm = this.positionalStringArrayToIntArray(sEndChNorm, 0);
        iEndChNormAmount = this.positionalStringArrayToIntArray(sEndChNorm, 1);
        iNormChMythic = this.positionalStringArrayToIntArray(sNormChMythic, 0);
        iNormChMythicAmount = this.positionalStringArrayToIntArray(sNormChMythic, 1);
        iNormChRare = this.positionalStringArrayToIntArray(sNormChRare, 0);
        iNormChRareAmount = this.positionalStringArrayToIntArray(sNormChRare, 1);
        iNormChNormal = this.positionalStringArrayToIntArray(sNormChNormal, 0);
        iNormChNormalAmount = this.positionalStringArrayToIntArray(sNormChNormal, 1);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String cmdName = cmd.getName();
        if (!(sender instanceof Player) && cmdName.equalsIgnoreCase("csg")) {
            sender.sendMessage("Apenas comandos para jogadores.");
            return true;
        } else {
            Player player = (Player)sender;
            if (!cmdName.equalsIgnoreCase("csg")) {
                return false;
            } else {
                this.initPlayerConfigFile(player, this.getClass());
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "{!} O Uso correto e /csg setarcasa ou /csg casa ou /csg recarregar para um Admin recarregar as configuraçoes");
                    return true;
                } else if (args.length >= 2) {
                    player.sendMessage(ChatColor.RED + "{!} Muitos argumentos. O uso correto deste comando é /csg setarcasa ou /csg casa /csg recarregar para um Admin recarregar as configuraçoes");
                    return true;
                } else if (args[0].equalsIgnoreCase("recarregar")) {
                    if (!player.hasPermission("cskygrid.recarregar")) {
                        player.sendMessage(ChatColor.RED + "{!} Voce nao tem permissao para usar esse comando");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.BLUE + "{+} Recarregando configuraçoes dos arquivos...");
                        this.onEnable();
                        player.sendMessage(ChatColor.BLUE + "{+} Configuraçao Recarregada.");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("setarcasa")) {
                    if (!player.hasPermission("cskygrid.setarcasa")) {
                        player.sendMessage(ChatColor.RED + "{!} Voce nao tem permissao para usar esse comando");
                        return true;
                    } else {
                        this.setHome(player);
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("casa")) {
                    if (!player.hasPermission("cskygrid.casa")) {
                        player.sendMessage(ChatColor.RED + "{!} Voce nao tem permissao para usar esse comando");
                        return true;
                    } else {
                        File pFile = new File(this.dirPlayers, player.getName() + ".yml");
                        FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
                        String wName = cName;
                        String nWName = cNetherName;
                        String eWName = cEndName;
                        World skygrid = this.getServer().getWorld(wName);
                        World nSkygrid = this.getServer().getWorld(nWName);
                        World eSkygrid = this.getServer().getWorld(eWName);
                        World curWorld = player.getWorld();
                        World selWorld = this.getServer().getWorld(wName);
                        String selName = cName;
                        String cWorldName = "World_Name";
                        int w = cdelay;
                        int x = 0;
                        int y = 0;
                        int z = 0;
                        if (curWorld != nSkygrid || curWorld != eSkygrid) {
                            selName = wName;
                            selWorld = skygrid;
                            x = pConfig.getInt("homex");
                            y = pConfig.getInt("homey");
                            z = pConfig.getInt("homez");
                        }

                        if (curWorld == nSkygrid) {
                            cWorldName = "Nether_World_Name";
                            selName = nWName;
                            selWorld = nSkygrid;
                            x = pConfig.getInt("netherhomex");
                            y = pConfig.getInt("netherhomey");
                            z = pConfig.getInt("netherhomez");
                        }

                        if (curWorld == eSkygrid) {
                            cWorldName = "End_World_Name";
                            selName = eWName;
                            selWorld = eSkygrid;
                            x = pConfig.getInt("endhomex");
                            y = pConfig.getInt("endhomey");
                            z = pConfig.getInt("endhomez");
                        }

                        if (selWorld == null) {
                            this.getLogger().severe("Config value: " + cWorldName + ": = null");
                            this.getLogger().info(cWorldName + ": " + selName + " na configuração não existe. Certifique-se de que o nome da configuração corresponda ao nome do mundo real, diferenciando maiúsculas de minúsculas.");
                            player.sendMessage(ChatColor.RED + "{!} Os nomes dos mundos do CSkyGrid nao foram setadas. Fale com um Staff");
                            return true;
                        } else {
                            Block home = selWorld.getBlockAt(x, y, z);
                            Location homeLoc = home.getLocation().add(0.5, 0.0, 0.5);
                            if (w > 0) {
                                player.sendMessage(ChatColor.GREEN + "{+} Esperando " + ChatColor.BLUE + w + ChatColor.GREEN + " Alguns seconds para teletransporta...");
                                (new TpSkyGrid(player, homeLoc, selName)).runTaskLater(this, (long)(w * 20));
                                return true;
                            } else {
                                player.sendMessage(ChatColor.GREEN + "{+} Teletransportando para sua casa " + selName);
                                player.teleport(homeLoc);
                                return true;
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "{!} O Uso Correto e  /csg setarcasa ou /csg casa");
                    return false;
                }
            }
        }
    }

    public void initPlayerConfigFile(Player player, Class<? extends Main> cl) {
        File mFile = new File(this.getDataFolder(), "config.yml");
        FileConfiguration mConfig = YamlConfiguration.loadConfiguration(mFile);
        File pFile = new File(this.dirPlayers, player.getName() + ".yml");
        int wH = mConfig.getInt("World_Height");

        try {
            YamlConfiguration pConfig;
            if (pFile.exists()) {
                pConfig = YamlConfiguration.loadConfiguration(pFile);
                pConfig.save(pFile);
            } else {
                InputStream in = cl.getResourceAsStream("PGM.yml");
                FileOutputStream out = new FileOutputStream(pFile);
                byte[] buffer = new byte[512];

                int i;
                while((i = in.read(buffer)) != -1) {
                    out.write(buffer, 0, i);
                }

                out.close();
                pConfig = YamlConfiguration.loadConfiguration(pFile);
                pConfig.addDefault("homex", 0);
                pConfig.addDefault("homey", wH - 3);
                pConfig.addDefault("homez", 0);
                pConfig.addDefault("netherhomex", 0);
                pConfig.addDefault("netherhomey", wH - 3);
                pConfig.addDefault("netherhomez", 0);
                pConfig.addDefault("endhomex", 0);
                pConfig.addDefault("endhomey", wH - 3);
                pConfig.addDefault("endhomez", 0);
                pConfig.set("homex", this.randCoord());
                pConfig.set("homey", wH - 3);
                pConfig.set("homez", this.randCoord());
                pConfig.set("netherhomex", this.randCoord());
                pConfig.set("netherhomey", wH - 3);
                pConfig.set("netherhomez", this.randCoord());
                pConfig.set("endhomex", this.randCoord());
                pConfig.set("endhomey", wH - 3);
                pConfig.set("endhomez", this.randCoord());
                pConfig.save(pFile);
            }
        } catch (FileNotFoundException var12) {
            Bukkit.getLogger().log(Level.SEVERE, "PGM.yml está faltando no arquivo jar", var12);
        } catch (IOException var13) {
            Bukkit.getLogger().log(Level.SEVERE, (String)null, var13);
        }

    }

    public void setHome(Player player) {
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        World curWorld = player.getWorld();
        if (curWorld != this.getServer().getWorld(cName) && curWorld != this.getServer().getWorld(cNetherName) && curWorld != this.getServer().getWorld(cEndName)) {
            player.sendMessage(ChatColor.RED + "You cant set your skygrid home in this world.");
        } else {
            try {
                File pConFile = new File(this.dirPlayers, player.getName() + ".yml");
                if (pConFile.exists()) {
                    FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pConFile);
                    if (curWorld == this.getServer().getWorld(cName)) {
                        pConfig.set("homex", x);
                        pConfig.set("homey", y);
                        pConfig.set("homez", z);
                        pConfig.save(pConFile);
                        player.sendMessage(ChatColor.BLUE + "Sua Casa do SkyGrid foi Setada Agora para : " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                        return;
                    }

                    if (curWorld == this.getServer().getWorld(cNetherName)) {
                        pConfig.set("netherhomex", x);
                        pConfig.set("netherhomey", y);
                        pConfig.set("netherhomez", z);
                        pConfig.save(pConFile);
                        player.sendMessage(ChatColor.BLUE + "Sua NetherSkyGrid Casa foi setada para: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                        return;
                    }

                    pConfig.set("endhomex", x);
                    pConfig.set("endhomey", y);
                    pConfig.set("endhomez", z);
                    pConfig.save(pConFile);
                    player.sendMessage(ChatColor.BLUE + "Sua EndSkyGrid Casa Foi setada para: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                    return;
                }

                player.sendMessage(ChatColor.RED + "Seu jogador configou o arquivo mais nao iniciou corretamente. Fale com um Admin");
            } catch (FileNotFoundException var8) {
                Bukkit.getLogger().log(Level.SEVERE, (String)null, var8);
            } catch (IOException var9) {
                Bukkit.getLogger().log(Level.SEVERE, (String)null, var9);
            }

        }
    }

    public File getFolder() {
        return this.dataFolder;
    }

    public File getDefConfigFile() {
        return this.conFileMain;
    }

    public int randCoord() {
        Random r = new Random();
        Random r2 = new Random();
        int b = r2.nextInt(2);

        int a;
        for(a = r.nextInt(cMax - cMin + 1) + cMin; a % 4 != 0; ++a) {
        }

        if (b == 0) {
            a = -a;
            return a;
        } else {
            return a;
        }
    }

    public int[] stringArrayToIntArray(String[] stringArray) {
        int[] newArray = new int[stringArray.length];

        for(int i = 0; i < stringArray.length; ++i) {
            try {
                newArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException var5) {
                Bukkit.getLogger().log(Level.SEVERE, "{!} Inteiro inválido na matriz de strings!", var5);
            }
        }

        return newArray;
    }

    public int[] positionalStringArrayToIntArray(String[] stringArray, int pos) {
        int[] newArray = new int[stringArray.length];

        for(int i = 0; i < stringArray.length; ++i) {
            String sP = stringArray[i];
            String[] sPA = sP.split(":");

            try {
                newArray[i] = Integer.parseInt(sPA[pos]);
            } catch (NumberFormatException var8) {
                Bukkit.getLogger().log(Level.SEVERE, "{!} Inteiro inválido na matriz de strings ou seu formato está errado! <ID>:<AMOUNT>", var8);
            }
        }

        return newArray;
    }
}

package net.myplugin.lk.MoeRPG;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by iwar on 2015/8/27.
 * You can check the plugin on the dev.mineplugin.net
 */
//在本类中实现监听器与命令的相关操作
public final class MoeRPG extends JavaPlugin {



    @Override
    public void onLoad() {
        System.out.println("MoeRPG has been enabled!");//输出提示信息
    }


    @Override
    public void onEnable() {
        //检查数据文件是否存在
        //不存在则创建
        //if (!getDataFolder().exists()) {
        //    getDataFolder().mkdir();
        //}
        //保存默认的配置文件
        this.saveDefaultConfig();
        this.getCommand("mrpg").setExecutor(new MoeRPGCommandExecutor(this));
        //getServer().getPluginManager().registerEvents(new MoeRPGListener(this), this);
        //打印启用信息
        System.out.println("==========");
        System.out.println("MoeRPG插件开启成功");
        System.out.println("联系QQ 865331492");
        System.out.println("==========");

    }


    @Override
    public void onDisable() {
        //插件关闭时发送提示信息
        this.saveConfig();
        System.out.println("MoeRPG has been disabled!");
    }



}


package net.myplugin.moerpg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


/**
 * Created by iwar on 2015/8/31.
 */
public class MoeRPGCommandExecutor implements CommandExecutor {
    private final MoeRPG plugin;


    public MoeRPGCommandExecutor(MoeRPG plugin) {
        this.plugin=plugin;
        boolean isDebugOn = plugin.getConfig().getBoolean("debug");//从配置文件中获取debug参数
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;

        //判断参数是否合法
        if (args.length == 0) {
            sender.sendMessage("&c参数过少或过多");
            return false;
        }
        //判断命令并执行操作
        switch (args[0].toLowerCase()) {
            //重载配置文件
            case "reload":
                if (sender instanceof Player){
                    if (sender.hasPermission("moerpg.reload")) {
                        plugin.reloadConfig();
                        sender.sendMessage(plugin.getConfig().getString("message.reload.human")); //返回自定义提示信息
                    }
                }
                plugin.reloadConfig();
                p.sendMessage(plugin.getConfig().getString("message.reload.bot"));
                return true;
            //主职业选择
            case "select":
                if (sender instanceof Player) {
                    if (args[0]==null){
                        //输入/mrpg select则返回职业列表信息
                        List<String> info = plugin.getConfig().getStringList("list.mainjob");
                        p.sendMessage("&b即将展示职业列表，请稍作等待");
                        p.sendMessage("&b==========主职业列表==========");
                        p.sendMessage("&bxxx.职业名称,选择职业请输入select xxx");
                        for (String s : info){
                            p.sendMessage(s);
                        }
                        p.sendMessage("&b============END===============");
                    } else if (p.hasPermission("moerpg.select")){
                        String playerName = sender.getName();
                        //检测是否有权限
                        switch (args[1].toLowerCase()) {
                            case "warrior":
                                this.setJob(playerName,args);
                            case "archer":
                                this.setJob(playerName,args);
                            case "wizard":
                                this.setJob(playerName,args);
                            case "assassin":
                                this.setJob(playerName,args);
                            default:
                                sender.sendMessage(plugin.getConfig().getString("message.nojob"));
                        }
                    }

                }
                return true;
            //副职业选择
            case "choose":
                if (sender instanceof Player){
                    if (args[1]==null){
                        List<String> info = plugin.getConfig().getStringList("list.subjob");
                        sender.sendMessage("&b即将展示职业列表，请稍作等待");
                        sender.sendMessage("&b==========副职业列表==========");
                        sender.sendMessage("&bxxx.职业名称,选择职业请输入select xxx");
                        for (String s : info){
                            sender.sendMessage(s);
                        }
                        sender.sendMessage("&b============END===============");
                    } else if (sender.hasPermission("moerpg.choose")){
                        String playerName = sender.getName();
                        if (args[1].equalsIgnoreCase("cooker")){
                            this.setJob(playerName, args);
                        } else if (args[1].equalsIgnoreCase("blacksmith")){
                            this.setJob(playerName, args);
                        } else if (args[1].equalsIgnoreCase("farmer")){
                            this.setJob(playerName, args);
                        } else {
                            p.sendMessage(plugin.getConfig().getString("message.nojob"));
                        }

                    }
                }
                return true;
            case "version":
                if (sender instanceof Player){
                    if (!sender.hasPermission("moerpg.version")) {
                        sender.sendMessage(plugin.getConfig().getString("message.version"));
                    } else {
                        sender.sendMessage(plugin.getConfig().getString("info"));
                    }
                } else {
                    sender.sendMessage(plugin.getConfig().getString("info"));
                }
                return true;
            case "info":
                if (sender instanceof Player){
                    if (args[1]==null){
                        this.print(sender,p.getName());
                    } else {
                        this.print(sender,args[1]);
                    }
                }
                if (sender instanceof ConsoleCommandSender){
                    if (args[1]==null){
                        sender.sendMessage("You have to provide a player name.");
                    } else {
                        this.print(sender,args[1]);
                    }
                }
                return true;

        }
        return false;

    }
    /* 打印职业信息 */

    public void print(CommandSender er,String p){
        String t1 = plugin.getConfig().getString(p + ".main");
        String t2 = plugin.getConfig().getString(p + ".sub1");
        String t3 = plugin.getConfig().getString(p + ".sub2");
        er.sendMessage("==============当前职业信息===================");
        er.sendMessage("玩家ID:"+p);
        er.sendMessage("主职业:" + t1);
        er.sendMessage("副职业:" + t2);
        er.sendMessage("副职业:" + t3);
        er.sendMessage("=====================end====================");
    }

    //设置职业
    public void setJob(String name,String temp[]){
        if (temp[0].equalsIgnoreCase("select")){
            plugin.getConfig().set(name+".main",temp[1]);
        }
        if (temp[0].equalsIgnoreCase("choose")){
            if (plugin.getConfig().get(name + ".sub1") == null){
                plugin.getConfig().set(name+".sub1",temp[1]);
            }
            if (plugin.getConfig().get(name+".sub2") == null){
                plugin.getConfig().set(name+".sub2",temp[1]);
            }
        }
    }

}

package com.zixuan007.society.window.society.shop;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.item.Item;
import com.zixuan007.society.domain.ItemIDSunName;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.society.MessageWindow;

import java.util.ArrayList;
import java.util.Map;

public class CreateShopWindow extends CustomWindow {
    private transient Player player;

    public CreateShopWindow(Player player) {
        super("公会商店");
        addElement(new ElementInput("","售卖物品的价格"));
        this.player=player;
        ArrayList<String> itemName = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : player.getInventory().getContents().entrySet()) {
            Integer key = entry.getKey();
            Item value = entry.getValue();
            if(value.getCustomName() == null || value.getCustomName().equals("")){
                String idByName = ItemIDSunName.getIDByName(value);
                value.setCustomName(idByName);
            }
            itemName.add(key+"-"+value.getCustomName());
        }
        addElement(new ElementDropdown("背包物品",itemName));
        addElement(new ElementInput("","请写入物品数量"));
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String sellPriceStr = response.getInputResponse(0);
        if(!SocietyUtils.isNumeric(sellPriceStr) || sellPriceStr.equals("")){
            MessageWindow messageWindow = WindowManager.getMessageWindow("§c输入的价格不是数字" ,this, "返回上级");
            player.showFormWindow(messageWindow);
            return;
        }

        int sellPrice = Integer.parseInt(sellPriceStr);
        FormResponseData dropdownResponse = response.getDropdownResponse(1);
        String itemCountStr = response.getInputResponse(2);

        String[] split = dropdownResponse.getElementContent().split("-");
        String index = split[0];
        String itemCustomName = split[1];
        Item item = player.getInventory().getItem(Integer.parseInt(index));
        if(!SocietyUtils.isNumeric(itemCountStr) || itemCountStr.equals("")){
            MessageWindow messageWindow = WindowManager.getMessageWindow("§c输入的数量不是数字" ,this, "返回上级");
            player.showFormWindow(messageWindow);
            return;
        }
        int itemCount = Integer.parseInt(itemCountStr);
        if(item.count < itemCount){
            MessageWindow messageWindow = WindowManager.getMessageWindow("§c此物品数量不足" ,this, "返回上级");
            player.showFormWindow(messageWindow);
            return;
        }
        item.setCount(itemCount);
        player.getInventory().remove(item);
        SocietyUtils.onCreatePlayer.put(player.getName(),new ArrayList<Object>(){
            {
                add(sellPrice);
                add(item);
            }
        });
        player.sendMessage(">> §e请点击墙上的木牌");
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

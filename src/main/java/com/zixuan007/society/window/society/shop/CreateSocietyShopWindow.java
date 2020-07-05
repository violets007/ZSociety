package com.zixuan007.society.window.society.shop;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseData;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.item.Item;
import com.zixuan007.society.domain.ItemIDSunName;
import com.zixuan007.society.utils.PluginUtils;
import com.zixuan007.society.utils.SocietyUtils;
import com.zixuan007.society.window.CustomWindow;
import com.zixuan007.society.window.WindowLoader;
import com.zixuan007.society.window.WindowManager;
import com.zixuan007.society.window.WindowType;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zixuan007
 */
public class CreateSocietyShopWindow extends CustomWindow implements WindowLoader {
    private transient Player player;

    public CreateSocietyShopWindow() {
        super(PluginUtils.getWindowConfigInfo("createSocietyShopWindow.title"));

    }

    @Override
    public FormWindow init(Object... objects) {
        getElements().clear();
        addElement(new ElementInput("","售卖物品的价格"));
        Player player= (Player) objects[0];
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
        return this;
    }

    @Override
    public void onClick(FormResponseCustom response, Player player) {
        String sellPriceStr = response.getInputResponse(0);
        FormWindow createSocietyShopForm = WindowManager.getFormWindow(WindowType.CREATE_SOCIETY_SHOP_WINDOW);
        String backButtonName = PluginUtils.getWindowConfigInfo("messageWindow.back.button");
        String backButtonImage = PluginUtils.getWindowConfigInfo("messageWindow.back.button.imgPath");

        if(!SocietyUtils.isNumeric(sellPriceStr) || sellPriceStr.equals("")){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.createSocietyShopWindow.isNumber"), createSocietyShopForm, backButtonName, backButtonImage));
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

            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW,  PluginUtils.getLanguageInfo("message.createSocietyShopWindow.isNumber"), createSocietyShopForm, backButtonName, backButtonImage));
            return;
        }
        int itemCount = Integer.parseInt(itemCountStr);
        if(item.count < itemCount){
            player.showFormWindow(WindowManager.getFormWindow(WindowType.MESSAGE_WINDOW, PluginUtils.getLanguageInfo("message.createSocietyShopWindow.rarelyItemCount"), createSocietyShopForm, backButtonName, backButtonImage));
            return;
        }
        item.setCount(itemCount);
        player.getInventory().removeItem(item);
        SocietyUtils.onCreatePlayer.put(player.getName(),new ArrayList<Object>(){
            {
                add(sellPrice);
                add(item);
            }
        });
        player.sendMessage(PluginUtils.getLanguageInfo("message.createSocietyShopWindow.clickWallSign"));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}

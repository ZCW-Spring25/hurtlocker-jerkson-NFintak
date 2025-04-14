package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryReporter {
    private final String originalFileText;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }

    @Override
    public String toString() {
        String nameWS = "        ";
        String priceWS = "       ";
        String fullList = "";
        ItemParser itemParser = new ItemParser();
        int errors = itemParser.invalidItem;
        List<Item> groceryList = itemParser.parseItemList(this.originalFileText);
        HashMap<String, Integer> nameFreq = new HashMap<>();
        HashMap<String, Integer> priceFreq = new HashMap<>();
        for (Item items : groceryList) {
            if (!nameFreq.containsKey(items.getName())) {
                nameFreq.put(items.getName(), 1);
            } else {
                nameFreq.put(items.getName(), nameFreq.get(items.getName()) + 1);
            }
        }
        for (Item items : groceryList) {
            String nameAndPrice = items.getName() + items.getPrice();
            if (!priceFreq.containsKey(nameAndPrice)) {
                priceFreq.put(nameAndPrice, 1);
            } else {
                priceFreq.put(nameAndPrice, priceFreq.get(nameAndPrice) + 1);
            }
        }
        for (Item items : groceryList) {
            String itemName = items.getName().replace(items.getName().substring(0, 1), items.getName().substring(0, 1).toUpperCase());
            if (!fullList.contains(itemName)) {
                fullList += ("name:") + (nameWS.substring(0, nameWS.length() - items.getName().length()))
                        + (itemName)
                        + (" \t\t ") + ("seen: ") + ((nameFreq.get(items.getName())));
                if (nameFreq.get(items.getName()) < 2) {
                    fullList += " time\n";
                } else {
                    fullList += " times\n";
                }
                fullList += "============= \t\t =============\n";
                //still need to troubleshoot issue w milk only appearing 5 times instead of 6 -
                //and cookies only appearing 8 times.
                //have another loop to add the prices and number of appearances to the list
            }
        }
        //make an if statement that checks if the item's name is already in the stringbuilder,
        //if isn't, start adding the info to the stringbuilder. if it is, skip over it.

        fullList += ("Errors         \t \t seen: ") + (errors) + (" times\n");
        return fullList;
    }
}

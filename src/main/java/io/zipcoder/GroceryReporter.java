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
            if (!fullList.contains(items.getName())) {
                fullList += ("name:") + (nameWS.substring(0, nameWS.length() - items.getName().length()))
                        + (items.getName()) + ("\t \t ") + ("seen: ") + ((nameFreq.get(items.getName())));
            }
        }
        //make an if statement that checks if the item's name is already in the stringbuilder,
        //if isn't, start adding the info to the stringbuilder. if it is, skip over it.

        fullList += ("Errors         \t \t seen: ") + (errors) + (" times\n");
        return fullList;
    }
}

package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;

public class GroceryReporter {
    private final String originalFileText;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }

    @Override
    public String toString() {
        String nameWS = "        ";
        String priceWS = "       ";
        String finalList = "";
        ItemParser itemParser = new ItemParser();
        List<Item> groceryList = itemParser.parseItemList(this.originalFileText);
        HashMap<String, HashMap<Double, Integer>> nameCounter = new HashMap<>();
        HashMap<Double, Integer> priceFreq = new HashMap<>();
        int errors = itemParser.errorCounter;

        for (Item items : groceryList) {
            String itemName = items.getName().replace(items.getName().substring(0, 1), items.getName().substring(0, 1).toUpperCase());
            Double itemPrice = items.getPrice();
            if (itemName.matches("[0-9]+")) {
                itemName = itemName.replace("0", "o");
            }
            if (!nameCounter.containsKey(itemName)) {
                priceFreq.put(items.getPrice(), 1);
                nameCounter.put(itemName, priceFreq);
            } else {
                if (!nameCounter.get(itemName).containsKey(itemPrice)) {
                    priceFreq.put(itemPrice, 1);
                    nameCounter.put(itemName, priceFreq);
                } else {
                    priceFreq.put(itemPrice, priceFreq.get(itemPrice) + 1);
                }
            }
        }

        for (Item item : groceryList) {
            String itemName = item.getName().replace(item.getName().substring(0, 1), item.getName().substring(0, 1).toUpperCase());
            if (!finalList.contains(itemName) && nameCounter.containsKey(itemName)) {
                StringBuilder str = new StringBuilder();
                str.append("name:")
                        .append(nameWS.substring(0, nameWS.length() - itemName.length()))
                        .append(itemName)
                        .append(" \t\t seen: ");
                if (nameCounter.get(itemName).size() > 1) {
                    int totalNum = 0;
                    for (Map.Entry<Double, Integer> prices : nameCounter.get(itemName).entrySet()) {
                        if (item.getPrice() == prices.getKey()) {
                            totalNum += prices.getValue();
                        }
                    }
                    str.append(totalNum).append(" times\n");
                } else {
                    str.append("1 time\n");
                }
                str.append("============= \t \t =============\n");
                for (Map.Entry<Double, Integer> prices : nameCounter.get(itemName).entrySet()) {
                    if (nameCounter.containsValue(prices.getKey())) {
                        str.append("Price:")
                                .append(priceWS.substring(0, priceWS.length() - prices.getKey().toString().length()))
                                .append(prices.getKey())
                                .append(" \t\t seen: ");
                        if (prices.getValue() > 1) {
                            str.append(prices.getValue()).append(" times\n");
                        } else {
                            str.append("1 time\n");
                        }
                        str.append("-------------\t\t -------------\n");
                    }
                }
                str.append("\n");
                finalList += str.toString();
            }
        }
        finalList += ("Errors         \t \t seen: ") + (errors) + (" times\n");
        return finalList;
    }
}

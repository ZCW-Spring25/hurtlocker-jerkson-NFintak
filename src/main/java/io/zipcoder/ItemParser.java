package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

public class ItemParser {
    int invalidItem = 0;

    public List<Item> parseItemList(String valueToParse) {
        List<Item> groceryList = new ArrayList<>();
        String[] itemsUnparsed = valueToParse.split("##");
        for (String items : itemsUnparsed) {
            Item item = null;
            try {
                item = parseSingleItem(items);
            } catch (ItemParseException e) {
                invalidItem++;
            }
            if (item != null) {
                groceryList.add(item);
            }
        }
        return groceryList;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        String itemStr = singleItem.toLowerCase().replace("##", "");
        HashMap<String, String> itemInfo = new HashMap<>();
        Pattern specialChars = Pattern.compile("(?i)([a-z]+)[@:;^*%]([^@:;^*%]+)"); //make pattern for specialChars
        Matcher charMatcher = specialChars.matcher(itemStr); //make matcher for specialChars, put singleItem through matcher
        while (charMatcher.find()) { //use while loop, while matcher.find() is true...
            //store keys and values in hashmap (string, string)
            String key = charMatcher.group(1);
            String value = charMatcher.group(2);
            itemInfo.put(key, value);
        }
        if (itemInfo.get("name") == null || itemInfo.get("price") == null
                || itemInfo.get("type") == null || itemInfo.get("expiration") == null) {
            throw new ItemParseException();
        } else {
            return new Item(itemInfo.get("name"), Double.parseDouble(itemInfo.get("price")),
                    itemInfo.get("type"), itemInfo.get("expiration"));
        }
    }
}

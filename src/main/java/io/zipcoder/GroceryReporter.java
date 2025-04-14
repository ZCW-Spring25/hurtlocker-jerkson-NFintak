package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroceryReporter {
    private final String originalFileText;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }

    @Override
    public String toString() {
        String whiteSpace = "        ";
        StringBuilder str = new StringBuilder();
        ItemParser itemParser = new ItemParser();
        int errors = itemParser.invalidItem;
        List<Item> groceryList = itemParser.parseItemList(this.originalFileText);
        //need to take every item and append the name, name-appearances (\n),
        // price(s), and price-appearances (\n)
        //end with number of errors found
        for (Item items : groceryList) {
            str.append("name:").append(whiteSpace.substring(0, whiteSpace.length() - items.getName().length()))
                    .append(items.getName().replace(items.getName().substring(0, 1), items.getName().substring(0, 1).toUpperCase()))
                    .append("\t\tseen: ");
            //write if statement for num of times a price appears, if > 1 append "times\n", else append "time\n"
        }

        str.append("Errors         \t \t seen: ").append(errors).append(" times\n");
        return str.toString();
    }
}

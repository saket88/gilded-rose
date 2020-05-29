import com.gildedrose.GildedRose;
import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {


    @DisplayName("Should able to update quality")
    @ParameterizedTest(name = "{index} => name={0}, sellIn={1}" +
            ", expectedSellIN={2}, quality={3}, expectedQuality={4}")
    @CsvSource(value = {
            "foo| 0| -1|0|0",
            "foo| 0| -1|1|0",
            "foo| 0| -1|51|49",
            "Aged Brie|0|-1|0|2",
            "Aged Brie| 7| 6|49|50",
            "Aged Brie| -1| -2|49|50",
            "Aged Brie| 7| 6|51|51",
            "Backstage passes to a TAFKAL80ETC concert|0|-1|0|0",
            "Backstage passes to a TAFKAL80ETC concert|9|8|50|50",
            "Sulfuras, Hand of Ragnaros|0|0|0|0"
    },delimiter = '|')
    void testUpdateQuality( String name, int sellIn, int expectedSelln,int quality,int expectedQuality ) {
        GildedRose app = updateQuality( name, sellIn, quality );
        assertEquals( name, app.items[0].name);
        assertEquals( expectedSelln, app.items[0].sellIn);
        assertEquals( expectedQuality, app.items[0].quality);
    }

    private GildedRose updateQuality( String name, int sellIn, int quality ) {
        Item[] items = new Item[] { Item.createItem( name,  sellIn, quality ) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app;
    }
}
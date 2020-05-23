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
    @CsvSource({
            "foo, 0, -1,0,0",
            "Aged Brie,0,-1,0,2",
            "Backstage passes to a TAFKAL80ETC concert,0,0,0,0"
    })
    void testUpdateQuality( String name, int sellIn, int expectedSelln,int quality,int expectedQuality ) {
        GildedRose app = updateQuality( name, sellIn, quality );
        assertEquals( name, app.items[0].name);
        assertEquals( expectedSelln, app.items[0].sellIn);
        assertEquals( expectedQuality, app.items[0].quality);
    }

    private GildedRose updateQuality( String name, int sellIn, int quality ) {
        Item[] items = new Item[] { new Item( name,  sellIn, quality ) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app;
    }
}
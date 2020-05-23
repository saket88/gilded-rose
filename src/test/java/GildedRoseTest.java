import com.gildedrose.GildedRose;
import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {


    @DisplayName("Should able to update quality")
    @ParameterizedTest(name = "{index} => name={0}, sellIn={1}, quality={2}")
    @CsvSource({
            "foo, 0, 0"
    })
    void testUpdateQuality( String name, int sellIn, int quality ) {
        GildedRose app = updateQuality( name, sellIn, quality );
        assertEquals( name, app.items[0].name);
    }

    private GildedRose updateQuality( String name, int sellIn, int quality ) {
        Item[] items = new Item[] { new Item( name,  sellIn, quality ) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app;
    }
}
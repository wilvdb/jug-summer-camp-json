package legacy;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class InnTest {
  Inn inn = new Inn();

  @Test
  public void should_list_items() {
    assertThat(inn.getItems()).onProperty("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");
    assertThat(inn.getItems()).onProperty("quality").containsExactly(20, 0, 7, 80, 20, 6);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(10, 2, 5, 0, 15, 3);
  }

  @Test
  public void should_update_quality() {
    inn.updateQuality();

    assertThat(inn.getItems()).onProperty("quality").containsExactly(19, 1, 6, 80, 21, 5);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
  }

  @Test
  public void should_update_quality_twice() {
    inn.updateQuality();
    inn.updateQuality();

    assertThat(inn.getItems()).onProperty("quality").containsExactly(18, 2, 5, 80, 22, 4);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(8, 0, 3, 0, 13, 1);
  }

  @Test
  public void should_update_quality_100_times() {
    for (int day = 0; day < 100; day++) {
      inn.updateQuality();
    }

    assertThat(inn.getItems()).onProperty("quality").containsExactly(0, 50, 0, 80, 0, 0);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(-90, -98, -95, 0, -85, -97);
  }

  @Test
  public void should_test_against_legacy_code() {
    LegacyInn legacyInn = new LegacyInn();

    for (int day = 0; day < 100; day++) {
      inn.updateQuality();
      legacyInn.updateQuality();

      for (int i = 0; i < legacyInn.getItems().size(); i++) {
        assertThat(inn.getItems().get(i).getName()).isEqualTo(legacyInn.getItems().get(i).getName());
        assertThat(inn.getItems().get(i).getQuality()).isEqualTo(legacyInn.getItems().get(i).getQuality());
        assertThat(inn.getItems().get(i).getSellIn()).isEqualTo(legacyInn.getItems().get(i).getSellIn());
      }
    }
  }
}

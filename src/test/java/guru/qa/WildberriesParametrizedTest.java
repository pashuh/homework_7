package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Arrays.asList;

public class WildberriesParametrizedTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "00x00";
    }

    @ValueSource(strings = {"Bosch", "Acer"})
    @ParameterizedTest(name = "При пояске на WB по запросу - {0} отображаются электроприборы - {0}")
    void wbParamTest1(String testData) {
        Selenide.open("https://www.wildberries.ru/");
        $("#searchInput").setValue(testData);
        $("#applySearchBtn").click();
        $$(".brand-name").find(text(testData)).shouldBe(visible);
    }

    @CsvFileSource(resources = "/test_data/wbData.csv")

    @ParameterizedTest(name = "При поиске на WB по запросу - {0} отображаются электроприборы - {0}")
    void wbTestCsv(String searchData, String expectedResult) {
        Selenide.open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchData);
        $("#applySearchBtn").click();
        $$(".brand-name").find(text(expectedResult)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Bosch, Samsung",
            "Acer, Acer"
    })
    @ParameterizedTest(name = "При поиске на WB по запросу - {0} отображаются электроприборы - {0}")
    void wbTestCsv1(String searchData, String expectedResult) {
        Selenide.open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchData);
        $("#applySearchBtn").click();
        $$(".brand-name").find(text(expectedResult)).shouldBe(visible);
    }

    static Stream<Arguments> wbTestStream() {
        return Stream.of(
                Arguments.of("Bosch", asList("Bosch")),
                Arguments.of("Acer", asList("Bosch", "Iphone"))
        );

    }

    @MethodSource(value = "wbTestStream")
    @ParameterizedTest(name = "При поиске на WB по запросу - {0} отображаются электроприборы - {1}")
    void wbTestComplexStream(String searchData, List<String> expectedResult) {
        Selenide.open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchData);
        $("#applySearchBtn").click();
        $$(".brand-name").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(Brand.class)
    @ParameterizedTest
    void enumTest(Brand brand) {
        Selenide.open("https://www.wildberries.ru/");
        $("#searchInput").setValue(brand.desc);
        $("#applySearchBtn").click();
        $$(".brand-name").find(text(brand.desc)).shouldBe(visible);
    }
}




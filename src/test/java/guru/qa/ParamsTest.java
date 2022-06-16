package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static java.util.Arrays.asList;

public class ParamsTest {


    @ValueSource(strings = {"JUnit 5", "TestNG"})
    @ParameterizedTest (name = "При поиске в яндексе по запросу {0} в результатах отображается текст {0}")
    void yaTestCommon(String testData) {
        open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item").find(Condition.text(testData)).shouldBe(Condition.visible);
    }

    @CsvFileSource(resources = "/test_data/1.csv")
    //@CsvSource(value = {
            //"JUnit 5, Hello",
            //"TestNG, Bye"
    //})
    @ParameterizedTest (name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
    void yaTestComplex(String searchData, String expectedResult) {
        open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        $$("li.serp-item").find(Condition.text(expectedResult)).shouldBe(Condition.visible);

    }

    static Stream<Arguments> yaTestVeryComplexDataProvider() {
        return Stream.of(
                Arguments.of("JUnit 5", asList("JUnit 5", "framework")),
                Arguments.of("TestNG", asList("JUnit", "framework"))
        );
    }

    @MethodSource(value = "yaTestVeryComplexDataProvider")
    @ParameterizedTest (name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
    void yaTestVeryComplex(String searchData, List<String> expectedResult) {
        open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        $$("li.serp-item").shouldBe(CollectionCondition.texts(expectedResult));

    }

}

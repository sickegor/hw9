package hw9;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;


public class SelenideTest {

    @DisplayName("Скачивание файлов")
    @Test
    void downloadTest() throws Exception {
        Selenide.open("https://github.com/sickegor/hw9read/blob/main/README.md");
        File file = $("#raw-url").download();
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            assertThat(new String(fileContent, StandardCharsets.UTF_8)).contains("hw9read");
        }
    }

    @DisplayName("Загрузка файлов")
    @Test
    void uploadTest(){
        Selenide.open("https://the-internet.herokuapp.com/upload");
        $("input[type='file']").uploadFromClasspath("txt.txt");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("txt.txt"));
    }

}

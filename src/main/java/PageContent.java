import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PageContent {

    static String getDailyPage(String one, String two, String three, String log) {
        Path dir = Paths.get("");
        Path filePath = Path.of(dir.toAbsolutePath() + "/src/main/java/DailyPageTemplate.html");

        String content = null;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String DailyEntry1 = one == null ? "" : one;
        String DailyEntry2 = two == null ? "" : two;
        String DailyEntry3 = three == null ? "" : three;;
        String DailyLog = log == null ? "" : log;

        content = content.replace("$DailyEntry1", DailyEntry1);
        content = content.replace("$DailyEntry2", DailyEntry2);
        content = content.replace("$DailyEntry3", DailyEntry3);
        content = content.replace("$DailyLog", DailyLog);

        return content;
    }
}

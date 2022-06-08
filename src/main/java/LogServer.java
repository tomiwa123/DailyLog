import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static spark.Spark.*;

public class LogServer {

    // Hard coded but to be abstracted into tuples (DailyCategory, UpdateMessage)
    private static final String cat1 = "Work";
    private static final String cat2 = "Body";
    private static final String cat3 = "Personal";
    private static final String catLog = "Log";

    private static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    private static String readLog() {
        Path dir = Paths.get("");
        Path filePath = Path.of(dir.toAbsolutePath() + "/DailyLog.txt");

        String content = null;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private static void writeLog(String newContent) {
        Path dir = Paths.get("");
        Path filePath = Path.of(dir.toAbsolutePath() + "/DailyLog.txt");

        File file = new File(filePath.toString());
        file.delete();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDailyEntry(String cat) {

        String content = readLog();

        // Get the date position
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateString = formatter.format(date);
        int datePos = content.indexOf(dateString);

        if (datePos == -1) {
            return "";
        } else {
            int catTagStart = content.indexOf("#" + cat, datePos);
            int catInsertPos = content.indexOf("\n", catTagStart) + 1;
            return content.substring(catInsertPos, content.indexOf("#", catInsertPos)).replace("\n", "</p><p>");
        }

    }

    private static String updateLog(String one, String two, String three, String log) {

        String messageOne = one == "" ? "" : (one + "\r\n");
        String messageTwo = two == "" ? "" : (two + "\r\n");
        String messageThree = three == "" ? "" : (three + "\r\n");
        String messageLog = log == "" ? "" : (log + "\r\n");

//        String messageOne = one == "" ? "" : (getTime() + "\r\n" + one + "\r\n");
//        String messageTwo = two == "" ? "" : (getTime() + "\r\n" + two + "\r\n");
//        String messageThree = three == "" ? "" : (getTime() + "\r\n" + three + "\r\n");
//        String messageLog = log == "" ? "" : (getTime() + "\r\n" + log + "\r\n");

        // Read the current Log
        String content = readLog();

        // Get the date position
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateString = formatter.format(date);
        int datePos = content.indexOf(dateString);
        String newContent = "";

        if (datePos == -1) {
            // writeNewLog()
            newContent += dateString + "\r\n";
            newContent += "#START" + "\r\n";
            newContent += "#" + cat1 + "\r\n" + messageOne;
            newContent += "#" + cat2 + "\r\n" + messageTwo;
            newContent += "#" + cat3 + "\r\n" + messageThree;
            newContent += "#" + catLog + "\r\n" + messageLog;
            newContent += "#END" + "\r\n\r\n";
            newContent += content;
        } else {
            // Invariant: only create new categories on first entry of the day

            // get Cat 1 insertion point
            int catTagStart = content.indexOf("#" + cat1, datePos);
            int catInsertPos = content.indexOf("\n", catTagStart) + 1;
            // Insert
            newContent += content.substring(0, catInsertPos) + messageOne;
            int prevIndex = catInsertPos;

            // get Cat 2 insertion point
            catTagStart = content.indexOf("#" + cat2, datePos);
            catInsertPos = content.indexOf("\n", catTagStart) + 1;
            // Insert
            newContent += content.substring(prevIndex, catInsertPos) + messageTwo;
            prevIndex = catInsertPos;

            // get Cat 3 insertion point
            catTagStart = content.indexOf("#" + cat3, datePos);
            catInsertPos = content.indexOf("\n", catTagStart) + 1;
            // Insert
            newContent += content.substring(prevIndex, catInsertPos) + messageThree;
            prevIndex = catInsertPos;

            // get Cat Log insertion point
            catTagStart = content.indexOf("#" + catLog, datePos);
            catInsertPos = content.indexOf("\n", catTagStart) + 1;
            // Insert
            newContent += content.substring(prevIndex, catInsertPos) + messageLog;
            prevIndex = catInsertPos;

            // Add the rest of the log
            newContent += content.substring(catInsertPos);

        }

        writeLog(newContent);

        return newContent;
    }

    public static void main(String[] args) {
        port(3000);

        get("/", (req, res) -> {
            return PageContent.getDailyPage(getDailyEntry(cat1),
                    getDailyEntry(cat2), getDailyEntry(cat3), getDailyEntry(catLog));
        });

        post("submit", (req, res) -> {
            String DailyEntry1Update = req.queryParams("DailyEntry1");
            String DailyEntry2Update = req.queryParams("DailyEntry2");
            String DailyEntry3Update = req.queryParams("DailyEntry3");
            String DailyEntryLogUpdate = req.queryParams("DailyEntryLog");

            updateLog(DailyEntry1Update, DailyEntry2Update, DailyEntry3Update, DailyEntryLogUpdate);

            res.redirect("/");
            return "";
        });

    }
}

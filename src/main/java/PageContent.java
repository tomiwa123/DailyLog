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

    static String dailyPage =
            """
                <!DOCTYPE html>
                <html>
                <head>
                
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                <style>
                body {padding: 15px; background-color: black; color: white;}
                form {margin: 10px;}
                h2 {text-align: center; margin-bottom: 25px;}
                .container {padding: 10px;}
                </style>
                
                <title>Daily Log</title>
                </head>
                <body>
                                
                <form>  
                <div class="container">      
                <h2>Daily 3</h2>
                
                <div class="row form-group">
                
                <div class="col">
                <h4>Work</h4>
                <textarea type="text" class="form-control" id="DailyEntry1" rows="5" cols="30"></textarea>
                </div>
                
                <div class="col">
                <h4>Body</h4>
                <textarea type="text" class="form-control" id="DailyEntry2" rows="5" cols="30"></textarea>
                </div>
                
                <div class="col">
                <h4>Personal</h4>
                <textarea type="text" class="form-control" id="DailyEntry3" rows="5" cols="10"></textarea>
                </div>
                
                </div>
                
                <div class="row form-group">
                <div class="col">
                <h4>Log</h4>
                <textarea type="text" class="form-control" id="DailyLog" rows="5"></textarea>
                </div>
                </div>
                
                <br />
                <button type="submit" class="btn btn-primary">Submit</button>
                </div>
                </form>
                            
                
                <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>     
                </body>
                </html>                            
            """;
}

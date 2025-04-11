package issue;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenerateClasses {

    public static void main(String[] args) throws IOException {
        int count = args.length != 1 ? 100 : Integer.parseInt(args[0]);


        var dir = Paths.get("../src/main/java/issue/gen");
        System.out.println(dir.toAbsolutePath());
        Files.createDirectories(dir);
        for (int i = 0; i < count; i++) {
            String className = "Foo" + i;
            String classContent = """
                    package issue.gen;
                    public class %s {}
                    """.formatted(className);
            Path file = dir.resolve(className + ".java");
            try {
                Files.createFile(file);
                Files.writeString(file, classContent);
            } catch (FileAlreadyExistsException _) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("done generating " + count + " classes");
    }
}

package lambdasinaction.chap3;
// new line

import java.io.*;

public class ExecuteAround {

    static final String FILE_PATH = "/Users/acexy/Repository/github/tech-acexy/demo-java-jdk11/src/main/java/lambdasinaction/chap3/ExecuteAround.java";

    public static void main(String... args) throws IOException {

        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);

        String oneLine = processFile(BufferedReader::readLine);
        System.out.println(oneLine);

        String twoLines = processFile((BufferedReader b) -> b.readLine() + "\n" + b.readLine());
        System.out.println(twoLines);

    }

    public static String processFileLimited() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return br.readLine();
        }
    }


    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return p.process(br);
        }

    }

    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;

    }
}

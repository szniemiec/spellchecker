import java.io.IOException;
import java.io.PrintStream;

public class SpellCheck {
    public SpellCheck() {
    }

    public static void main(String[] arr) throws IOException {
        if (arr.length == 0) {
            showUsageMessage();
        } else {
            String var1 = arr[arr.length - 1];
            String wordFile = "wordlist.txt";
            StringHasher hasher = new LousyStringHasher();
            PrintStream outputStream = System.out;
            boolean isQuiet = false;

            for(int i = 0; i < arr.length - 1; ++i) {
                switch (arr[i]) {
                    case "-degenerate" -> hasher = new DegenerateStringHasher();
                    case "-lousy" -> hasher = new LousyStringHasher();
                    case "-better" -> hasher = new BetterStringHasher();
                    case "-quiet" -> {
                        outputStream = new PrintStream(new NullOutputStream());
                        isQuiet = true;
                    }
                    case "-wordlist" -> {
                        ++i;
                        if (i >= arr.length - 1) {
                            showUsageMessage();
                            return;
                        }
                        wordFile = arr[i];
                    }
                }
            }

            if (arr[arr.length - 1].charAt(0) == '-') {
                showUsageMessage();
            } else {
                try {
                    long var7 = System.currentTimeMillis();
                    (new Checker()).check(var1, wordFile, hasher, outputStream);
                    long var9 = System.currentTimeMillis();
                    if (isQuiet) {
                        System.out.println("Checker ran in " + (var9 - var7) + "ms");
                    }
                } catch (IOException var11) {
                    var11.printStackTrace();
                }

            }
        }
    }

    private static void showUsageMessage() {
        System.out.println("Usage: java SpellCheck [options] inputFilename");
        System.out.println();
        System.out.println("    options");
        System.out.println("    -------");
        System.out.println("    -degenerate");
        System.out.println("        runs the spell checker with the degenerate word hashing algorithm");
        System.out.println();
        System.out.println("    -lousy");
        System.out.println("        runs the spell checker with a lousy word hashing algorithm (default)");
        System.out.println();
        System.out.println("    -better");
        System.out.println("        runs the spell checker with a better word hashing algorithm");
        System.out.println();
        System.out.println("    -quiet");
        System.out.println("        runs the spell checker without any output, reporting the total time");
        System.out.println("        taken to load the dictionary and perform the spell check");
        System.out.println();
        System.out.println("    -wordlist wordlistFilename");
        System.out.println("        runs the spell checker using the wordlist specified, rather than");
        System.out.println("        the default (wordlist.txt)");
        System.out.println();
        System.out.println("    example");
        System.out.println("    -------");
        System.out.println("    java SpellCheck -wordlist biglist.txt -better -quiet big-input.txt");
        System.out.println("        executes the spell checker using the wordlist 'biglist.txt', the");
        System.out.println("        better word hashing algorithm, in quiet mode (i.e. no output),");
        System.out.println("        on the input file 'big-input.txt'");
    }
}

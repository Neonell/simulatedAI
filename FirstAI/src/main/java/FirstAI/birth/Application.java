package FirstAI.birth;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hadoop.util.ToolRunner;

import FirstAI.birth.questionAnswer.QuestionAnswerAnalyzer;

public class Application {
	public static void main(String[] args) throws Exception {
        final int rc;
        switch (getKey(args)) {
        case "birth":
            rc = ToolRunner.run(getConfiguration(), new QuestionAnswerAnalyzer(), getArgs(args));
            break;
        case "usage":
            printUsage(args);
            rc = 0;
            break;
        default:
            printUsage(args);
            rc = 1;
            break;
        }
        System.exit(rc);
    }
 
 private static Configuration getConfiguration() {
        Configuration conf = new Configuration(true);
        // Pick up our Avro libs first:
        conf.setBoolean(MRJobConfig.MAPREDUCE_JOB_USER_CLASSPATH_FIRST, true);
        conf.set("dfs.umaskmode", "000");
        return conf;
    }
 
 private static String getKey(String[] args) {
        if (args.length == 0) {
            return "";
        }
        return args[0].toLowerCase();
    }
 
 private static void printUsage(String[] args) {
        String basecmd = "   yarn -jar<this JAR> ";
        System.err.println("Invalid arguments. Usage: ");
        System.err.println(basecmd + "JobNAme [<args1>] [<Options1>] ... ");
        System.err.println();
        System.err.println("You entered: ");
        StringBuilder builder = new StringBuilder(basecmd);
        for (String s : args) {
            builder.append(s);
            builder.append(' ');
        }
        System.err.println(builder.toString());
    }

    /**
     * @return the array passed, without its first element
     */
    private static String[] getArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
}

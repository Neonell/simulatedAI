package FirstAI.birth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class AIConfiguration extends Configured{
	
    private static final String HDFS_OUTPUT_ROOT = "/tmp/AI/";
    private static final String HDFS_INPUT_ROOT = "/data/";
	
    protected Path[] findInputFiles(String path, String pattern) throws IOException {
        List<Path> results = new ArrayList<Path>(1); // Should always be 1

        final Pattern regexp;
        try {
            regexp = Pattern.compile(pattern);
        } catch (Exception e) {
            throw new IllegalArgumentException("File name pattern '" + pattern + "' is not a valid Regular Exp.");
        }
        
        // Navigate, throw an exception if a directory is missing
        FileSystem fs = FileSystem.get(getConf());

        String pathString ;

        if (path != null && !path.isEmpty()){
            pathString = path;
        } else{
            pathString = HDFS_INPUT_ROOT;
        }

        Path p = new Path(pathString);
       
        if (!fs.exists(p)) {
            throw new IllegalArgumentException("Root Directory '" + pathString + "' not found.");
        }

        // Find the corresponding files
        RemoteIterator<LocatedFileStatus> hdfsIterator = fs.listFiles(p, true);
        while (hdfsIterator.hasNext()) {
            Path nextPath = hdfsIterator.next().getPath();
            if (regexp.matcher(nextPath.getName()).matches()) {
                results.add(nextPath);
               	System.out.println("Found " + nextPath.toUri());
            }
        }

        return results.toArray(new Path[1]);
    }
    
    protected Path findOutputFile(String jobname, String outputPath, String... part) {
        String outputStringbuilder = (outputPath != null && !outputPath.isEmpty()) ? outputPath :  HDFS_OUTPUT_ROOT;
        StringBuilder sb = new StringBuilder(outputStringbuilder);

        sb.append(jobname);
        for (String string : part) {
            sb.append('-').append(string);
        }
        return new Path(sb.toString());
    }

}

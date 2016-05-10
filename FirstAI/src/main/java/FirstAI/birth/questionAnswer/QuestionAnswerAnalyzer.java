package FirstAI.birth.questionAnswer;

import java.io.IOException;

import org.apache.avro.mapreduce.AvroJob;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import FirstAI.birth.AIConfiguration;
import FirstAI.model.QuestionAnswer;

public class QuestionAnswerAnalyzer extends AIConfiguration implements Tool{

	@Override
	public int run(String[] arg0) throws Exception {
		String inputPath = rangeCheck(1, arg0.length) == false ? "/data/qa" : arg0[0];
		Path[] inputs = findInputFiles(inputPath + "/questionAnswerPairs", ".*.txt");

		String outputPath = rangeCheck(2, arg0.length) == false ? "" : arg0[1];
		Path output = findOutputFile("question-answer-pairs", outputPath);
		
		int rc = 0;
		
		final Job qaGraphBuilderJob = getQAGraphJob(getConf(), "Question-Answers-Pairs", output, inputs);
		rc += qaGraphBuilderJob.waitForCompletion(true) ? 0 : 1;
		
		return 0;
	}
	private Job getQAGraphJob(Configuration conf, String jobname, Path outputPath, Path[] inputPaths)
			throws IOException {
		// Create job
		Job job = Job.getInstance(conf, jobname);
		job.setJarByClass(QuestionAnswerAnalyzer.class);

		// Setup MapReduce
		job.setMapperClass(QuestionAnswerMapper.class);
		job.setReducerClass(QuestionAnswerReducer.class);
		job.setNumReduceTasks(4);

		// Setup Map Step: Text input, Avro output, sorted by Key
		job.setMapOutputKeyClass(Text.class);
		AvroJob.setMapOutputValueSchema(job, QuestionAnswer.getClassSchema());

		// Setup Reduce step: Simple Key/Value pairs to Textfile
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Input files
		for (Path inputPath : inputPaths) {
			FileInputFormat.addInputPath(job, inputPath);
		}
		// job.setInputFormatClass(TextInputFormat.class);

		// Output directory
		FileSystem hdfs = FileSystem.get(getConf());
		if (hdfs.exists(outputPath)) {
			hdfs.delete(outputPath, true);
		}
		FileOutputFormat.setOutputPath(job, outputPath);
		job.setOutputFormatClass(TextOutputFormat.class);

		return job;
	}

	private boolean rangeCheck(int index, int size) {
		if (index > size) {
			return false;
		}

		return true;
	}
}

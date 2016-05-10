package FirstAI.birth.questionAnswer;

import java.io.IOException;

import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import FirstAI.model.QuestionAnswer;
import FirstAI.model.parser.QuestionAnswerParser;

public class QuestionAnswerMapper extends Mapper<LongWritable, Text, Text, AvroValue<QuestionAnswer>> {

	private int receivedCount = 0;
	private int totalCount = 0;
	private int skipped = 0;
	private Text key;

	@Override
	protected void setup(Mapper<LongWritable, Text, Text, AvroValue<QuestionAnswer>>.Context context)
			throws IOException, InterruptedException {
		super.setup(context);
	}

	/**
	 * Parses a file and submits it as a value holder class.
	 * 
	 * @author fnell
	 */
	@Override
	public void map(LongWritable ikey, Text value, Context context) throws IOException, InterruptedException {
		receivedCount++;
		QuestionAnswer qa = new QuestionAnswerParser().parse(value.toString());
		if (qa == null) {
			skipped++;
			return;
		}
		key = new Text(qa.getSubject());
		context.write(key, new AvroValue<QuestionAnswer>(qa));
		totalCount++;
		return;
	}

	@Override
	protected void cleanup(Mapper<LongWritable, Text, Text, AvroValue<QuestionAnswer>>.Context context)
			throws IOException, InterruptedException {
		super.cleanup(context);
	}

}

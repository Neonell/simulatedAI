package FirstAI.birth.questionAnswer;

import java.io.IOException;

import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.tinkerpop.blueprints.Vertex;

import FirstAI.graph.GraphBuilder;
import FirstAI.model.QuestionAnswer;

public class QuestionAnswerReducer extends Reducer<Text, AvroValue<QuestionAnswer>, Text, Text> {

	private int countSuccess = 0;
	private int countError = 0;
	private GraphBuilder graph;

	@Override
	protected void setup(Reducer<Text, AvroValue<QuestionAnswer>, Text, Text>.Context context)
			throws IOException, InterruptedException {
		super.setup(context);
		graph = new GraphBuilder();
	}

	/**
	 * insert the question/answers into the graph
	 * 
	 * @author fnell
	 */
	@Override
	public void reduce(Text _key, Iterable<AvroValue<QuestionAnswer>> values, Context context)
			throws IOException, InterruptedException {
		boolean first = true;
		Vertex subject = null;
		for (AvroValue<QuestionAnswer> value : values) {
			QuestionAnswer strecke = new QuestionAnswer(value.datum().getSubject(), value.datum().getQuestion(), value.datum().getAnswer());
			// insert Strecke into Graph
			if(first){
				subject = graph.insertOrFindSubject(strecke.getSubject());
			}
			graph.processNewQuestion(strecke, subject);
			countSuccess++;
		}

		Text result = new Text();
		result.set("Inserted " + countSuccess + ", " + countError + " errors.");
		Text key = new Text();
		key.set('[' + _key.toString() + ']');
		context.write(key, result);
	}

	@Override
	protected void cleanup(Reducer<Text, AvroValue<QuestionAnswer>, Text, Text>.Context context)
			throws IOException, InterruptedException {
		super.cleanup(context);
	}
}

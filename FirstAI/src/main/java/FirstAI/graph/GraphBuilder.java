package FirstAI.graph;

import org.apache.tools.ant.filters.TokenFilter;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.attribute.Text;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.gremlin.Tokens;

public class GraphBuilder {
	private static final String BACKEND = "berkeleyje";
	private static final String DIRECTORY = "../database";

	public TitanGraph graph;
	private TitanFactory.Builder config;
	private Vertex currentConversation = null;

	/**
	 * Constructor - start a new transaction for concurrency access
	 */
	public GraphBuilder() {
		config = TitanFactory.build();
		config.set("storage.backend", BACKEND);
		config.set("storage.directory", DIRECTORY);
		graph = config.open();
	}

	public String searchInput(String input) {
		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("input", input));
		if (pipe.hasNext()) {
			Vertex v = (Vertex) pipe.next();
			currentConversation = v;
			return v.getVertices(Direction.OUT, "answer").iterator().next().getProperty("output");
		} else {
			return null;
		}
	}

	public String searchPartialInput(String input) {
		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices()).has("input", Text.REGEX, ".*" + input + ".*");
		if (pipe.hasNext()) {
			Vertex v = (Vertex) pipe.next();
			currentConversation = v;
			return v.getVertices(Direction.OUT, "answer").iterator().next().getProperty("output");
		} else {
			return null;
		}
	}

	public void learn(String input, String output) {
		Vertex newInput;
		Vertex newAnswer;

		newInput = graph.addVertex(null);
		newInput.setProperty("input", input);

		newAnswer = graph.addVertex(null);
		newAnswer.setProperty("output", output);

		Edge connection = newInput.addEdge("answer", newAnswer);

		graph.commit();
	}

	public void learnNewQuestion(String input, String answer) {
		Vertex newInput;
		Vertex oldAnswer = null;

		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("output", answer));
		if (pipe.hasNext()) {
			oldAnswer = (Vertex) pipe.next();
		}

		if (oldAnswer != null) {
			newInput = graph.addVertex(null);
			newInput.setProperty("input", input);
			Edge connection = newInput.addEdge("answer", oldAnswer);

			graph.commit();
		}

	}

}

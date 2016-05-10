package FirstAI.graph;

import org.apache.tools.ant.filters.TokenFilter;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.attribute.Text;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.thinkaurelius.titan.core.util.TitanCleanup;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Predicate;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;

import FirstAI.model.QuestionAnswer;

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
		initGraph();
	}

	public void initGraph() {
		config = TitanFactory.build();
		config.set("storage.backend", BACKEND);
		config.set("storage.directory", DIRECTORY);
		graph = config.open();
	}

	public void resetGraph() {
		// delete old graph
		graph.shutdown();
		TitanCleanup.clear(graph);
		graph = null;
		initGraph();
		buildShemaAndIndex();
	}

	private void buildShemaAndIndex() {
		// TODO Auto-generated method stub
		TitanManagement mgmt = graph.getManagementSystem();
		// input
		PropertyKey input = mgmt.makePropertyKey("input").dataType(String.class).make();
		mgmt.buildIndex("input" + "Idx", Vertex.class).addKey(input).buildCompositeIndex();
		// output
		PropertyKey output = mgmt.makePropertyKey("output").dataType(String.class).make();
		mgmt.buildIndex("output" + "Idx", Vertex.class).addKey(output).buildCompositeIndex();
		// subject
		PropertyKey subject = mgmt.makePropertyKey("subject").dataType(String.class).make();
		mgmt.buildIndex("subject" + "Idx", Vertex.class).addKey(subject).buildCompositeIndex();

		mgmt.commit();
	}

	public String searchInput(String input) {
		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("input", input.toLowerCase()));
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
		pipe.start(graph.getVertices()).has("input", Text.REGEX, ".*" + input.toLowerCase() + ".*");
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
		newInput.setProperty("input", input.toLowerCase());
		
		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("output", output.toLowerCase()));
		if (pipe.hasNext()) {
			newAnswer = (Vertex) pipe.next();
		}
		else{
			newAnswer = graph.addVertex(null);
			newAnswer.setProperty("output", output);
		}
		Edge connection = newInput.addEdge("answer", newAnswer);

		graph.commit();
	}

	public void learnNewQuestion(String input, String answer) {
		Vertex newInput;
		Vertex oldAnswer = null;

		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("output", answer.toLowerCase()));
		if (pipe.hasNext()) {
			oldAnswer = (Vertex) pipe.next();
		}

		if (oldAnswer != null) {
			newInput = graph.addVertex(null);
			newInput.setProperty("input", input.toLowerCase());
			Edge connection = newInput.addEdge("answer", oldAnswer);

			graph.commit();
		}

	}

	public void processNewQuestion(QuestionAnswer strecke, Vertex subject) {
		// TODO Auto-generated method stub
		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("input", strecke.getQuestion()));
		if (pipe.hasNext()) {
			// if question already exist we don't insert it twice
			return;
		} else {
			TitanTransaction t = graph.newTransaction();
			TitanVertex newInput = (TitanVertex) t.addVertex(null);
			newInput.setProperty("input", strecke.getQuestion());

			TitanVertex newAnswer = (TitanVertex) t.addVertex(null);
			newAnswer.setProperty("output", strecke.getAnswer());

			// create link between question and answer
			t.addEdge(newInput, newAnswer, "answer");

			// create link with subject
			if (subject != null) {
				TitanVertex subVertex = (TitanVertex) t.getVertex(subject.getId());
				t.addEdge(subVertex, newInput, "conversation");
				t.addEdge(subVertex, newAnswer, "conversation");
			}
			t.commit();
		}

	}

	public Vertex insertOrFindSubject(String subject) {
		// TODO Auto-generated method stub
		Vertex subVertex;
		GremlinPipeline pipe = new GremlinPipeline();
		pipe.start(graph.getVertices("subject", subject));
		if (pipe.hasNext()) {
			subVertex = (Vertex) pipe.next();
			return subVertex;
		} else {
			TitanTransaction t = graph.newTransaction();
			subVertex = t.addVertex();
			subVertex.setProperty("subject", subject);
			t.commit();
			return subVertex;
		}
	}
}

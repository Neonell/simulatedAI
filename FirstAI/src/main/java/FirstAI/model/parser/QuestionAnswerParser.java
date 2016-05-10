package FirstAI.model.parser;

import FirstAI.model.QuestionAnswer;
import FirstAI.model.QuestionAnswer.Builder;

public class QuestionAnswerParser extends AbstractCSVParser<QuestionAnswer> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public QuestionAnswer parse(String[] fields) {
		try {
			Builder b = QuestionAnswer.newBuilder();
			b = b.setSubject(toString(fields[0]));
			b = b.setQuestion(toString(fields[1]));
			b = b.setAnswer(toString(fields[2]));
			return b.build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

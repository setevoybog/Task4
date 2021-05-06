package by.shyshaliaksey.task4.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.shyshaliaksey.task4.entity.AbstractComponent;
import by.shyshaliaksey.task4.entity.ComponentName;
import by.shyshaliaksey.task4.entity.TextComposite;
import by.shyshaliaksey.task4.exception.TextException;

public class SentenceParser implements Chain {

	private static final String ELEMENT = "\\t* *([\\w()<>|&^.\\\\,'~-]+)( |\\n|[.!?])+";
	private Chain nextChain;
	
	@Override
	public void setNextChain(Chain nextChain) {
		this.nextChain = nextChain;
	}

	@Override
	public void parse(AbstractComponent abstractComponent, String content) throws TextException {
		if (abstractComponent.getComponentName() == ComponentName.SENTENCE) {
			Pattern pattern = Pattern.compile(ELEMENT);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String element = content.substring(matcher.start(), matcher.end());
				// paragraphs.add(paragraph);
				TextComposite elementComposite = new TextComposite(ComponentName.ELEMENT);
				nextChain.parse(elementComposite, element);
				abstractComponent.add(elementComposite);
			}
		} else {
			nextChain.parse(abstractComponent, content);
		}
	}

}

Simulated Artificial intelligence project:

Introduction:

The purpose of this project is based on an idea, a concept and of course a little bit a dream. To be able to communicate, make request and get help in your daily routine with the help of an AI.
There's plenty of system who are really good in those fields. But I wanted to create my own, and I quickly realized that it was a big work to achieve this. Therefore I want to open this project to the community, in order to improve it with new ideas and better processes.

The main idea is the next:

This application is based on a text Analyzer who parse the input from the "human" and try to find pattern in order to give back the appropriated response or to entertain the "human" with a conversation.
This AI is like a child, his database is like his brain, internet and the power of a computer his body capacity. As a child when it grows he has to learn new word, new sentences and learn how to respond to them, how to behave....

It's based on a graphical database "Titan" who allow us to create node with relation. This graphical database will be the brain of the AI. The AI has the capacity to modify or add new relation, new node, it's the learning process.
Has with a child you can teach him how to behave or you can teach him how to be a monster. This AI will reflect you :)

For now I've just set the bases:
1. little Java program who allow us through the console to discuss with the AI
2. inputAnalyzer:
	Identify if it's a question or not
		if it's a question then try to find if it's something to compute or to search on the internet or if it's a question where the answer is known
		if the question is unknow the AI try to learn how to answer
	If it's not a question check if it's a well know conversation
3. A training class who allow us to learn new conversation to the AI

The Idea is that the AI can be trained just by talking with him.

The next steps that I've in mind are the next one:
- Implement the search functionnality
- Implement an unlearn mechanism when a know response doesn't suit us anymore
- Implement a conversation history in order to be able to follow a flow or to give back more input as just an answer in particular cases
- Implement a timer functionality which will, based on the conversation history, propose sentences or ask question to the "human" when he says nothing.
- Add a voice analyzer in order to be able to talk with the AI

And of course improve the existing functionality with better parsing, granularity, ...

So if you want to contribute you are welcome :)

Technology used:
java, maven, titan

How to import project into eclipse:
go to project directory and type: mvn eclipse:eclipse


PS: I'm brand new to github so be nice with me
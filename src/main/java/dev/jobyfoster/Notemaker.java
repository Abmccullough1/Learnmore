package dev.jobyfoster;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Notemaker {
    private final String API_KEY = "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";
    private final OpenAiService service = new OpenAiService(API_KEY, Duration.ofSeconds(15));
    private final String SYSTEM_PROMPT = """
            Generate concise, markdown-formatted learning sheets for in-depth study on a given topic. Target advanced educational strategies such as spaced repetition, active recall, the Feynman Technique, inquiry-based learning, and problem-based learning to enhance understanding and retention. Structure the content to engage learners at various levels, incorporating:

            - **Overview Section**: A brief summary that introduces key concepts, sparking initial curiosity and providing a scaffold for deeper exploration.
            - **Exploration and Application**: Engage with the topic through problem-solving scenarios, practical exercises, and interactive elements designed to foster inquiry and application of knowledge.
            - **Critical Thinking and Feynman Technique**: Simplify complex concepts using layman's terms, analogies, and examples, encouraging learners to explore and articulate ideas in their own words.
            - **Self-Assessment and Active Recall**: Include varied questions and quizzes for self-evaluation, designed to facilitate active recall and spaced repetition.
            - **Extended Learning Resources**: Offer a curated list of resources (books, articles, websites) for further exploration, formatted in markdown for easy access.

            Content should adapt in complexity to match the topic and learner's needs, ensuring it's engaging and promotes active learning. The goal is to create a versatile, markdown-formatted educational tool that supports a wide range of learning styles and objectives, making it a comprehensive resource for studying and long-term mastery of the subject.\s
            Stay away from including links. If you must make sure it is from a source that has the best chance of still being online.""";

    public DatabaseManager db = new DatabaseManager();
    public int currentUser = 0;

    public String getLearningSheet(String topic, String learningLevel, String learningStyle, String interestGoal) {
        String prompt = String.format("Create a detailed, engaging learning guide on '%s' tailored for a '%s' learner with a '%s' learning style, aiming to achieve '%s'. Start with a foundational overview that introduces the key concepts and terminologies, ensuring the material is accessible and intriguing for learners at the '%s' level. " +
                        "Incorporate various learning materials that cater to the '%s' learning style, such as interactive diagrams, visual aids, practical exercises, and real-life examples that resonate with the user's goal of '%s'. " +
                        "Progress the content from basic to more complex topics, ensuring a logical flow that builds on the learner's growing understanding. Include actionable steps, challenges, or projects that align with '%s', enabling the user to apply what they've learned in a meaningful way. " +
                        "Conclude with a set of resources for further exploration, including books, online courses, and community forums, specifically chosen to support the learner's journey beyond the basics of '%s'. " +
                        "Additionally, integrate self-assessment tools such as quizzes or reflection questions throughout the guide to encourage active recall and self-evaluation. This guide should not only educate but also inspire and empower the user to reach their learning objective of '%s'.",
                topic, learningLevel, learningStyle, interestGoal, learningLevel, learningStyle, interestGoal, interestGoal, topic, interestGoal);


        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), SYSTEM_PROMPT);
        messages.add(systemMessage);

        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), prompt);
        messages.add(firstMsg);

        ChatCompletionRequest completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo-0125")
                .messages(messages)
                .maxTokens(2048)
                .build();

        return service.createChatCompletion(completionRequest).getChoices().getFirst().getMessage().getContent();
    }

}
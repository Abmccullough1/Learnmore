package dev.jobyfoster;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Notemaker {
    private final String API_KEY = "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";
    private final OpenAiService service = new OpenAiService(API_KEY);
    private final String SYSTEM_PROMPT = """
            Using the OpenAI API, generate detailed, markdown-formatted notes on a specified topic to aid in studying and learning. The request should be processed with the aim of producing content that aligns with advanced educational methodologies, incorporating elements such as spaced repetition, active recall, the Feynman Technique, inquiry-based learning, and problem-based learning. The output should be tailored to facilitate both comprehensive understanding and long-term retention of the subject matter. Ensure the generated notes are well-structured, engaging, and provide a rich resource for both review and deeper exploration of the topic.\s
                        
            1. For the studying sheet: Include an overview, detailed breakdowns with visual aids, self-assessment questions, explanations in layman's terms, and additional resources.
            2. For the learning sheet: Begin with an introduction that piques curiosity, followed by sections that encourage exploration and application through scenarios and problems, interactive elements, critical thinking prompts, and a list of extended learning resources.
                        
            Both sheets should be formatted in markdown to support easy navigation and reference. Adjust the complexity and depth of the content based on the topic's nature, ensuring it's accessible yet challenging enough to promote active engagement. The final output should serve as a versatile educational tool, adaptable to various learning styles and objectives.""";
    public DatabaseManager db = new DatabaseManager();
    public int currentUser = 0;

    public String getLearningSheet(String topic) {
        String prompt = String.format("Generate a comprehensive markdown-styled studying sheet on %s. Structure the content to facilitate spaced repetition and active recall. Begin with an overview section that succinctly summarizes the key concepts and definitions. Follow this with a detailed breakdown of each main idea, incorporating bullet points, diagrams, and code snippets (where applicable) for clarity. " +
                "Next, include a section with potential questions and quizzes designed for self-assessment, emphasizing the application of concepts in various contexts. Ensure these questions are tiered from basic to advanced to cater to progressive learning difficulty. " +
                "Incorporate a section explaining complex ideas using the Feynman Technique, simplifying them into layman's terms. This should include analogies, examples, and thought experiments where relevant. " +
                "Finally, add a list of additional resources for further exploration, such as key papers, books, and websites, formatted in markdown links. This studying sheet should serve as a comprehensive resource for deepening understanding and facilitating long-term retention of %s.", topic, topic);

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

package com.example.triviavirsion2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MediumQuestions {
    private static final List<Question> questions = new ArrayList<>();

    static {
        questions.add(new Question("What is the capital of Canada?",
                new String[]{"Toronto", "Ottawa", "Vancouver", "Montreal"}, 1));
        questions.add(new Question("In what year did the Titanic sink?",
                new String[]{"1905", "1912", "1920", "1898"}, 1));
        questions.add(new Question("What is the smallest prime number?",
                new String[]{"1", "3", "2", "5"}, 2));
        questions.add(new Question("Which famous scientist developed the theory of relativity?",
                new String[]{"Isaac Newton", "Albert Einstein", "Nikola Tesla", "Stephen Hawking"}, 1));
        questions.add(new Question("What is the powerhouse of the cell?",
                new String[]{"Nucleus", "Ribosome", "Mitochondria", "Chloroplast"}, 2));
        questions.add(new Question("Which planet is known as the 'Red Planet'?",
                new String[]{"Venus", "Jupiter", "Saturn", "Mars"}, 3));
        questions.add(new Question("Who is the author of the Harry Potter series?",
                new String[]{"Suzanne Collins", "J.K. Rowling", "J.R.R. Tolkien", "Stephenie Meyer"}, 1));
        questions.add(new Question("What is the largest mammal in the world?",
                new String[]{"African Elephant", "Blue Whale", "Giraffe", "Orca"}, 1));
        questions.add(new Question("Which country is known as the Land of the Rising Sun?",
                new String[]{"China", "Japan", "Vietnam", "South Korea"}, 1));
        questions.add(new Question("Who was the first woman to win a Nobel Prize?",
                new String[]{"Rosalind Franklin", "Marie Curie", "Ada Lovelace", "Florence Nightingale"}, 1));
        questions.add(new Question("What is the capital of Spain?",
                new String[]{"Barcelona", "Seville", "Valencia", "Madrid"}, 3));
        questions.add(new Question("Who painted the ceiling of the Sistine Chapel?",
                new String[]{"Raphael", "Michelangelo", "Leonardo da Vinci", "Donatello"}, 1));
        questions.add(new Question("Which element has the chemical symbol 'O'?",
                new String[]{"Oxygen", "Gold", "Osmium", "Iron"}, 0));
        questions.add(new Question("What is the largest planet in our solar system?",
                new String[]{"Earth", "Saturn", "Neptune", "Jupiter"}, 3));
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?",
                new String[]{"Harper Lee", "Mark Twain", "F. Scott Fitzgerald", "Ernest Hemingway"}, 0));
        questions.add(new Question("What is the currency used in the European Union?",
                new String[]{"Pound", "Dollar", "Euro", "Franc"}, 2));
        questions.add(new Question("What is the tallest waterfall in the world?",
                new String[]{"Victoria Falls", "Niagara Falls", "Angel Falls", "Iguazu Falls"}, 2));
        questions.add(new Question("Who invented the printing press?",
                new String[]{"Thomas Edison", "Leonardo da Vinci", "Johannes Gutenberg", "Benjamin Franklin"}, 2));
        questions.add(new Question("What is the main ingredient in a traditional Italian risotto?",
                new String[]{"Basmati rice", "Brown rice", "Arborio rice", "Jasmine rice"}, 2));
        questions.add(new Question("Which gas is most abundant in the Earth's atmosphere?",
                new String[]{"Oxygen", "Nitrogen", "Carbon dioxide", "Hydrogen"}, 1));
    }

    public static Question getRandomQuestion() {
        Random rand = new Random();
        return questions.get(rand.nextInt(questions.size()));
    }

    public static List<Question> getRandomQuestions(int count) {
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }
}

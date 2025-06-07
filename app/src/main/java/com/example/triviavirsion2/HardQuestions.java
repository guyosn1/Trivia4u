package com.example.triviavirsion2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HardQuestions {
    private static final List<Question> questions = new ArrayList<>();

    static {
        questions.add(new Question("Who was the first person to reach the South Pole?",
                new String[]{"Ernest Shackleton", "Roald Amundsen", "Robert Falcon Scott", "Edmund Hillary"}, 1));
        questions.add(new Question("What is the capital of Greece?",
                new String[]{"Thessaloniki", "Athens", "Patras", "Heraklion"}, 1));
        questions.add(new Question("What is the smallest bone in the human body?",
                new String[]{"Ulna", "Stapes", "Fibula", "Hyoid"}, 1));
        questions.add(new Question("Who is known for the theory of relativity?",
                new String[]{"Albert Einstein", "Isaac Newton", "Stephen Hawking", "Niels Bohr"}, 0));
        questions.add(new Question("What is the longest river in Asia?",
                new String[]{"Mekong", "Indus", "Yangtze River", "Ganges"}, 2));
        questions.add(new Question("Which empire was ruled by Emperor Nero?",
                new String[]{"Byzantine Empire", "Roman Empire", "Persian Empire", "Ottoman Empire"}, 1));
        questions.add(new Question("What's the only vegetable that is also classified as a flower?",
                new String[]{"Broccoli", "Carrot", "Lettuce", "Potato"}, 0));
        questions.add(new Question("What is the study of fossils called?",
                new String[]{"Archaeology", "Paleontology", "Geology", "Anthropology"}, 1));
        questions.add(new Question("Who painted the ceiling of the Sistine Chapel?",
                new String[]{"Raphael", "Donatello", "Michelangelo", "Leonardo da Vinci"}, 2));
        questions.add(new Question("What is the name of the central protagonist in J.D. Salinger's 'The Catcher in the Rye'?",
                new String[]{"Jay Gatsby", "Holden Caulfield", "Huckleberry Finn", "Atticus Finch"}, 1));
        questions.add(new Question("What is the capital of Australia?",
                new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2));
        questions.add(new Question("Which country won the 2018 FIFA World Cup?",
                new String[]{"Brazil", "Germany", "France", "Argentina"}, 2));
        questions.add(new Question("Who was the first woman to fly solo across the Atlantic Ocean?",
                new String[]{"Bessie Coleman", "Amelia Earhart", "Valentina Tereshkova", "Harriet Quimby"}, 1));
        questions.add(new Question("What is the chemical symbol for gold?",
                new String[]{"Gd", "Go", "Au", "Ag"}, 2));
        questions.add(new Question("Which country is known as the Land of the Rising Sun?",
                new String[]{"China", "Thailand", "Japan", "South Korea"}, 2));
        questions.add(new Question("Who invented the telephone?",
                new String[]{"Nikola Tesla", "Alexander Graham Bell", "Thomas Edison", "Michael Faraday"}, 1));
        questions.add(new Question("What is the capital of Italy?",
                new String[]{"Venice", "Rome", "Milan", "Naples"}, 1));
        questions.add(new Question("Which planet is known as the 'Giant Planet'?",
                new String[]{"Uranus", "Saturn", "Neptune", "Jupiter"}, 3));
        questions.add(new Question("Who is credited with developing the theory of evolution by natural selection?",
                new String[]{"Gregor Mendel", "Charles Darwin", "Louis Pasteur", "Alfred Wallace"}, 1));
        questions.add(new Question("What is the capital of Norway?",
                new String[]{"Bergen", "Oslo", "Stavanger", "Trondheim"}, 1));
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

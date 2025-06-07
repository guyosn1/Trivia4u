package com.example.triviavirsion2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProfessionalQuestions {
    private static final List<Question> questions = new ArrayList<>();

    static {
        questions.add(new Question("What is the capital city of Kazakhstan?",
                new String[]{"Almaty", "Tashkent", "Astana", "Bishkek"}, 2));
        questions.add(new Question("Which country has the most volcanoes?",
                new String[]{"Japan", "Indonesia", "Chile", "Iceland"}, 1));
        questions.add(new Question("What is the capital of New Zealand?",
                new String[]{"Auckland", "Christchurch", "Wellington", "Dunedin"}, 2));
        questions.add(new Question("Which country is the world's largest producer of coffee?",
                new String[]{"Vietnam", "Colombia", "Ethiopia", "Brazil"}, 3));
        questions.add(new Question("What is the capital of Peru?",
                new String[]{"Cusco", "Arequipa", "Lima", "Trujillo"}, 2));
        questions.add(new Question("Who developed the theory of evolution by natural selection?",
                new String[]{"Gregor Mendel", "Charles Darwin", "Louis Pasteur", "Jean-Baptiste Lamarck"}, 1));
        questions.add(new Question("What is the SI unit of temperature?",
                new String[]{"Celsius", "Fahrenheit", "Kelvin", "Joule"}, 2));
        questions.add(new Question("What is the only metal that is liquid at room temperature?",
                new String[]{"Mercury", "Lead", "Gallium", "Bromine"}, 0));
        questions.add(new Question("Who was the first female aviator to fly solo across the Atlantic Ocean?",
                new String[]{"Amelia Earhart", "Jacqueline Cochran", "Bessie Coleman", "Amy Johnson"}, 0));
        questions.add(new Question("Which gas is used to inflate balloons that float?",
                new String[]{"Hydrogen", "Helium", "Nitrogen", "Carbon dioxide"}, 1));
        questions.add(new Question("Which organ in the human body produces insulin?",
                new String[]{"Liver", "Pancreas", "Kidney", "Stomach"}, 1));
        questions.add(new Question("What is the hardest known natural material?",
                new String[]{"Quartz", "Graphite", "Diamond", "Obsidian"}, 2));
        questions.add(new Question("Which scientist proposed the three laws of motion?",
                new String[]{"Galileo Galilei", "Isaac Newton", "Johannes Kepler", "Albert Einstein"}, 1));
        questions.add(new Question("Which element has the atomic number 1?",
                new String[]{"Hydrogen", "Oxygen", "Helium", "Lithium"}, 0));
        questions.add(new Question("What is the longest bone in the human body?",
                new String[]{"Tibia", "Spine", "Femur", "Humerus"}, 2));
        questions.add(new Question("Which Renaissance figure is known for both art and scientific inventions?",
                new String[]{"Michelangelo", "Leonardo da Vinci", "Raphael", "Galileo"}, 1));
        questions.add(new Question("What is the name of the deepest part of the world’s oceans?",
                new String[]{"Tonga Trench", "Java Trench", "Puerto Rico Trench", "Mariana Trench"}, 3));
        questions.add(new Question("Which disease did Jonas Salk develop a vaccine for?",
                new String[]{"Measles", "Polio", "Tuberculosis", "Smallpox"}, 1));
        questions.add(new Question("Which blood type is known as the universal donor?",
                new String[]{"AB positive", "O negative", "A positive", "B negative"}, 1));
        questions.add(new Question("Who discovered penicillin?",
                new String[]{"Edward Jenner", "Louis Pasteur", "Alexander Fleming", "Joseph Lister"}, 2));
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

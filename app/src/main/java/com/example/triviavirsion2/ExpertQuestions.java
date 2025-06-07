package com.example.triviavirsion2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExpertQuestions {
    private static final List<Question> questions = new ArrayList<>();

    static {
        questions.add(new Question("Which physicist won Nobel Prizes in both Physics and Chemistry?",
                new String[]{"Albert Einstein", "Marie Curie", "Richard Feynman", "Niels Bohr"}, 1));
        questions.add(new Question("What year did the Chernobyl disaster occur?",
                new String[]{"1984", "1986", "1988", "1990"}, 1));
        questions.add(new Question("What is the name of the particle that gives mass to other particles?",
                new String[]{"Proton", "Higgs boson", "Neutrino", "Gluon"}, 1));
        questions.add(new Question("What is the name of the theorem that states energy cannot be created or destroyed?",
                new String[]{"Law of Inertia", "Newton’s Third Law", "Law of Conservation of Energy", "Theory of Relativity"}, 2));
        questions.add(new Question("Which planet has the most moons?",
                new String[]{"Jupiter", "Neptune", "Uranus", "Saturn"}, 3));
        questions.add(new Question("What is Schrödinger’s cat a metaphor for?",
                new String[]{"Time travel", "Quantum superposition", "Gravitational waves", "Relativity"}, 1));
        questions.add(new Question("What is the rarest blood type in humans?",
                new String[]{"AB negative", "B negative", "Rh-null", "O negative"}, 2));
        questions.add(new Question("In what year was the United Nations founded?",
                new String[]{"1939", "1945", "1950", "1941"}, 1));
        questions.add(new Question("Which mathematician is known for the Last Theorem solved in 1994?",
                new String[]{"Carl Gauss", "Isaac Newton", "Euclid", "Pierre de Fermat"}, 3));
        questions.add(new Question("What is the chemical name for table salt?",
                new String[]{"Potassium chloride", "Sodium chloride", "Calcium carbonate", "Magnesium sulfate"}, 1));
        questions.add(new Question("Which element is named after the creator of the periodic table?",
                new String[]{"Curium", "Bohrium", "Mendelevium", "Rutherfordium"}, 2));
        questions.add(new Question("What is the term for animals that are only active at night?",
                new String[]{"Diurnal", "Nocturnal", "Crepuscular", "Arboreal"}, 1));
        questions.add(new Question("What does DNA stand for?",
                new String[]{"Deoxyribonucleic acid", "Dinucleotide array", "Diatomic nitrogen acid", "Deoxynitric acid"}, 0));
        questions.add(new Question("What famous equation is attributed to Einstein?",
                new String[]{"F = ma", "E = mc²", "a² + b² = c²", "V = IR"}, 1));
        questions.add(new Question("What is the most abundant element in the Earth's crust?",
                new String[]{"Silicon", "Iron", "Oxygen", "Aluminum"}, 2));
        questions.add(new Question("What’s the term for a word that is spelled the same backward as forward?",
                new String[]{"Synonym", "Anagram", "Homophone", "Palindrome"}, 3));
        questions.add(new Question("Which language is considered the oldest written language?",
                new String[]{"Sanskrit", "Greek", "Sumerian", "Latin"}, 2));
        questions.add(new Question("Who created the first mechanical computer?",
                new String[]{"Blaise Pascal", "Alan Turing", "Charles Babbage", "Ada Lovelace"}, 2));
        questions.add(new Question("What is the term for an animal that eats both plants and meat?",
                new String[]{"Herbivore", "Carnivore", "Omnivore", "Insectivore"}, 2));
        questions.add(new Question("Which theory explains the origin of the universe?",
                new String[]{"String Theory", "Steady State Theory", "Big Bang Theory", "Quantum Loop Theory"}, 2));
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
